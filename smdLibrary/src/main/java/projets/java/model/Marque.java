package projets.java.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class Marque implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100)
    private String libelle;

    @OneToMany(mappedBy = "marque", cascade = CascadeType.ALL)
    private List<Produit> produits;

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

    public List<Produit> getProduits() {
        return produits;
    }

    public void setProduits(List<Produit> produits) {
        this.produits = produits;
    }

    @Override
    public String toString() {
        return libelle;
    }
}
