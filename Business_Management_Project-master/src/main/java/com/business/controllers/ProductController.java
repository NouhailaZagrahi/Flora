package com.business.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.business.entities.Product;
import com.business.services.ProductServices;

@Controller
public class ProductController 
{
	@Autowired
	private ProductServices productServices;

	//	AddProduct
	@PostMapping("/addingProduct")
	public String addProduct(@ModelAttribute Product product)
	{

		this.productServices.addProduct(product);
		return "redirect:/admin/services";
	}

	//	UpdateProduct
	/*@GetMapping("/updatingProduct/{productId}")
	public String updateProduct(@ModelAttribute Product product,@PathVariable("productId") int id)
	{

		this.productServices.updateproduct(product, id);
		return "redirect:/admin/services";
	}*/
	//DeleteProduct
	@GetMapping("/deleteProduct/{productId}")
	public String delete(@PathVariable("productId") int id)
	{
		this.productServices.deleteProduct(id);
		return "redirect:/admin/services";
	}


	//salma

	// Page de mise à jour du produit (GET)
@GetMapping("/updatingProduct/{productId}")
public String updateProductPage(@PathVariable("productId") int id, Model model)
{
    // Récupérer le produit à partir de la base de données
    Product product = this.productServices.getProduct(id);
    
    // Vérifier si le produit existe
    if (product != null) {
        model.addAttribute("product", product);
        return "Update_Product"; // Page où l'utilisateur pourra modifier le produit
    } else {
        model.addAttribute("error", "Produit non trouvé");
        return "redirect:/admin/services"; // Si le produit n'existe pas, rediriger vers la page d'administration
    }
}

@PostMapping("/updatingProduct/{productId}")
public String updateProduct(@ModelAttribute Product product, 
                             @PathVariable("productId") int id, 
                             @RequestParam("image") MultipartFile file, 
                             Model model) {
    // Récupérer le produit existant
    Product existingProduct = this.productServices.getProduct(id);
    
    if (existingProduct != null) {
        try {
            // Vérifiez si un fichier a été téléchargé
            if (!file.isEmpty()) {
                // Sauvegarder l'image et récupérer son chemin
                String imagePath = saveImage(file);
                existingProduct.setImageUrl(imagePath); // Mettre à jour l'URL de l'image
            }

            // Mettre à jour les autres champs du produit
            existingProduct.setPname(product.getPname());
            existingProduct.setPprice(product.getPprice());
            existingProduct.setPdescription(product.getPdescription());
            
            // Sauvegarder les modifications dans la base de données
            this.productServices.updateproduct(existingProduct, id);
            model.addAttribute("success", "Produit mis à jour avec succès");
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "Erreur lors de la mise à jour du produit");
            return "Update_Product"; // Retourne la vue en cas d'erreur
        }
    } else {
        model.addAttribute("error", "Produit non trouvé");
        return "Update_Product"; // Retourne la vue si le produit n'est pas trouvé
    }

    return "redirect:/admin/services"; // Redirige vers la page d'administration après mise à jour
}

// Méthode pour sauvegarder l'image dans un sous-dossier local
private String saveImage(MultipartFile file) throws IOException {
    // Répertoire local de sauvegarde (dans le dossier du projet)
    String directory = "uploaded-images/"; // Le dossier sera créé dans le répertoire de ton projet
    
    // Crée le répertoire s'il n'existe pas
    File dir = new File(directory);
    if (!dir.exists()) {
        dir.mkdirs(); // Crée le répertoire si nécessaire
        System.out.println("Répertoire créé : " + directory);
    }

    // Générer un nom unique pour l'image
    String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();
    Path path = Paths.get(directory + fileName);

    // Copier l'image dans le répertoire
    Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
    System.out.println("Image sauvegardée dans : " + path);

    // Retourner le chemin relatif
    return directory + fileName; // Ce chemin sera sauvegardé dans la base de données
}
	
}
