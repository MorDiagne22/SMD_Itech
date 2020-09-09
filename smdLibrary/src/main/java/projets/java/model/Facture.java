package projets.java.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Facture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 100, nullable = false)
    private String numero;

    @Column(length = 200)
    private String date;

    @Column
    private Double montant;

    @Column
    private Double montantNetPayer;

    public Double getMontantNetPayer() {
        return montantNetPayer;
    }

    public void setMontantNetPayer(Double montantNetPayer) {
        this.montantNetPayer = montantNetPayer;
    }

    @Column
    private Double tva;

    @Column
    private Double montant_paye;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_commande")
    private Commande commande;

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

    public Double getTva() {
        return tva;
    }

    public void setTva(Double tva) {
        this.tva = tva;
    }

    public Double getMontant_paye() {
        return montant_paye;
    }

    public void setMontant_paye(Double montant_paye) {
        this.montant_paye = montant_paye;
    }

    public Commande getCommande() {
        return commande;
    }

    public void setCommande(Commande commande) {
        this.commande = commande;
    }
}
