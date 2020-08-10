package projets.java.model;

import java.io.Serializable;

public class ProduitAjouter {

    private int id;

    private String libelle;

    private int prix;

    private int quantite;

    private int total;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getPrix() {
        return prix;
    }

    public void setPrix(int prix) {
        this.prix = prix;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ProduitAjouter(){

    }

    public ProduitAjouter(String libelle, int quantite, int prix){
        this.libelle = libelle;
        this.quantite = quantite;
        this.prix = prix;
        this.total = quantite * prix;
    }
}
