package com.business.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Product
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int pid;
	private String pname;
	private double pprice;
	private String image;
	private String image2;
	private String pdescription;
	private String imageUrl;
	public int getPid() {
		return pid;
	}
	public void setPid(int pid) {
		this.pid = pid;
	}
	public String getPname() {
		return pname;
	}
	public void setPname(String pname) {
		this.pname = pname;
	}
	public double getPprice() {
		return pprice;
	}
	public void setPprice(double pprice) {
		this.pprice = pprice;
	}
	public String getPdescription() {
		return pdescription;
	}
	public void setPdescription(String pdescription) {
		this.pdescription = pdescription;
	}

    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }

	public String getImage2() {
        return image2;
    }
    public void setImage2(String image2) {
        this.image = image2;
    }
	public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
	@Override
	public String toString() {
		return "Product [pid=" + pid + ", pname=" + pname + ", imageUrl=" + imageUrl +", pprice=" + pprice + ", pdescription=" + pdescription + ", image=" + image + ", image2=" + image2  
				+ "]";
	}



}