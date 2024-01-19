package com.fst.gestiondevente.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name="t_fournisseur")
public class Fournisseur {
@Id	
@GeneratedValue(strategy=GenerationType.AUTO)

private long id;
private String nom;
private String email;
private String tel;

@OneToMany
List<Produit>produits;

@Override
public String toString() {
	return "Fournisseur [id=" + id + ", nom=" + nom + ", email=" + email + ", tel=" + tel + "]";
}

public Fournisseur() {
	super();
}

public Fournisseur(List<Produit> produits, String nom, String email, String tel) {
	super();
	this.produits = produits;
	this.nom = nom;
	this.email = email;
	this.tel = tel;
}

public List<Produit> getProduits() {
	return produits;
}
public void setProduits(List<Produit> produits) {
	this.produits = produits;
}

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}

public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
    this.email = email;
}

public String getTel() {
	return tel;
}
public void setTel(String tel) {
    this.tel = tel;
}


}
