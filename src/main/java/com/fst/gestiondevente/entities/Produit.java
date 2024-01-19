package com.fst.gestiondevente.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "t_produit")
public class Produit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String nom;
    private String description;
    private String image;
    double prix;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "fournisseur")
    private Fournisseur fournisseur;

    @Override
    public String toString() {
        return "Produit [id=" + id + ", nom=" + nom + ", description=" + description + ", prix=" + prix + ", image=" + image + "]";
    }

    public Produit(String nom, String description, double prix) {
        super();
        this.nom = nom;
        this.description = description;
        this.prix = prix;
    }

    public Produit() {
        super();
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCategory(Category category) {
        this.category = category;

    }

    public void setFournisseur(Fournisseur fournisseur) {
        this.fournisseur = fournisseur;

    }

    public Category getCategory() {
        return category;
    }

    public Fournisseur getFournisseur() {
        return fournisseur;
    }


}