package projets.java.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String numero;

    @Column(length = 100, nullable = false)
    private String date;

    @Column(length = 100, nullable = false)
    private String dateLivraison;

    @Column
    private Double montant;
    
    @Column
    private Double paye;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_Etat")
    private Etat etat;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_client")
    private Client client;
    /*
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_user")
    private User user;
    */

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<Produit_Commande> produitCommandes;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<Facture> factures;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Double getPaye() {
        return paye;
    }

    public void setPaye(Double paye) {
        this.paye = paye;
    }

    public List<Produit_Commande> getProduitCommandes() {
        return produitCommandes;
    }

    public void setProduitCommandes(List<Produit_Commande> produitCommandes) {
        this.produitCommandes = produitCommandes;
    }

    public Etat getEtat() {
        return etat;
    }

    public void setEtat(Etat etat) {
        this.etat = etat;
    }

    public List<Facture> getFactures() {
        return factures;
    }

    public void setFactures(List<Facture> factures) {
        this.factures = factures;
    }

    public String getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(String dateLivraison) {
        this.dateLivraison = dateLivraison;
    }
}
