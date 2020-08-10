package projets.java.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String libelle;

    @Column
    private int quantite;

    @Column
    private int quantite_min;

    @Column
    private int quantite_crit;

    @Column
    private int quantite_max;

    @Column
    private int prixUnitaire;

    @Column(length = 100)
    private String modele;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categorie")
    private Categorie categorie;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_marque")
    private Marque marque;

    @OneToMany(mappedBy = "produit", cascade = CascadeType.ALL)
    private List<Produit_Commande> produitCommandes;



    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public int getQuantite_min() {
        return quantite_min;
    }

    public void setQuantite_min(int quantite_min) {
        this.quantite_min = quantite_min;
    }

    public int getQuantite_crit() {
        return quantite_crit;
    }

    public void setQuantite_crit(int quantite_crit) {
        this.quantite_crit = quantite_crit;
    }

    public int getQuantite_max() {
        return quantite_max;
    }

    public void setQuantite_max(int quantite_max) {
        this.quantite_max = quantite_max;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(int prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Marque getMarque() {
        return marque;
    }

    public void setMarque(Marque marque) {
        this.marque = marque;
    }

    public List<Produit_Commande> getProduitCommandes() {
        return produitCommandes;
    }

    public void setProduitCommandes(List<Produit_Commande> produitCommandes) {
        this.produitCommandes = produitCommandes;
    }
}
