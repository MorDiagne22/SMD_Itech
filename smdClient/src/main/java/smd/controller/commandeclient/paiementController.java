package smd.controller.commandeclient;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import projets.java.interfaces.CommandeInterface;
import projets.java.model.Commande;
import projets.java.model.Facture;
import smd.utils.Fabrique;
import smd.utils.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;



public class paiementController implements Initializable ,CommandeInterface {
    public TextField tfdMtt30;
    public TextField tfdMtt70;
    public Button btnValider;
    public Label lblNumeroCommande;
    public Label lblMontant;
    public Label lblTva;
    public Label lblDate;
    public Label lblNetAPaye;
    public TextField tfdMontant100;

    private Commande commande;
    private Double montant30pourc;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblDate.setText(String.valueOf(LocalDate.now()));
    }

    public void charger(){
        Double montant = commande.getMontant();
        Double mttTva = (montant * 18) / 100;
        Double netApayer = montant + mttTva;

        montant30pourc = (netApayer * 30) / 100;
        lblNumeroCommande.setText(commande.getNumero());
        lblMontant.setText(String.valueOf(commande.getMontant()));
        lblTva.setText(String.valueOf(mttTva));
        lblNetAPaye.setText(String.valueOf(netApayer));
        if (commande.getClient().getTypeClient().getNom().equals("Entreprise")){
            if (commande.getFactures() ==null && commande.getEtat().getLibelle().equals("en attente")){
                tfdMtt30.setText(String.valueOf(montant30pourc));
                tfdMtt30.setEditable(false);
            }
            else{
                tfdMtt70.setText(String.valueOf(netApayer - montant30pourc));
                tfdMtt30.setText(String.valueOf(montant30pourc));
                tfdMtt70.setEditable(false);
            }
        }
        else{
            tfdMontant100.setText(String.valueOf(netApayer));
            tfdMontant100.setEditable(false);
        }
    }

    public void validerPaiement(ActionEvent actionEvent) throws Exception {
            Facture fact = new Facture();
            int updateFacture=0;
            if(tfdMtt30.isDisable() && tfdMtt70.isDisable()){
                // Paiment cas d'un client
                    if(!tfdMontant100.getText().trim().isEmpty()){
                        fact.setCommande(commande);
                        fact.setDate(lblDate.getText());
                        fact.setMontant(Double.valueOf(lblMontant.getText().trim()));
                        fact.setMontant_paye(Double.valueOf(tfdMontant100.getText().trim()));
                        fact.setMontantNetPayer(Double.valueOf(lblNetAPaye.getText().trim()));
                        fact.setTva(18.0);
                        long idMax = Fabrique.getiFacture().getLastIdFacture() + 1;
                        fact.setNumero("Fact-00"+idMax);
                        commande.setEtat(Fabrique.getiEtat().getEtat("payé"));
                    }else{
                        alert("Entrer le montant des 100% a payé");
                    }

            }else{
                //Paiement cas d'une entreprise
                if (commande.getFactures()==null && commande.getEtat().getLibelle().equals("en attente")){

                    //Paiement des 30% du montant total
                    if(!tfdMtt30.getText().trim().isEmpty()){
                        fact.setCommande(commande);
                        fact.setDate(lblDate.getText());
                        fact.setMontant(Double.valueOf(lblMontant.getText().trim()));
                        fact.setMontant_paye(Double.valueOf(tfdMtt30.getText().trim()));
                        fact.setMontantNetPayer(Double.valueOf(lblNetAPaye.getText().trim()));
                        fact.setTva(18.0);
                        long idMax = Fabrique.getiFacture().getLastIdFacture() + 1;
                        fact.setNumero("Fact-00"+idMax);
                        commande.setEtat(Fabrique.getiEtat().getEtat("en cours"));
                    }else{
                        alert("Entrer le montant des 30% a payé svp!!!");
                    }

                }else{

                    //paiement des 70% du reste apres livraison
                    if (!tfdMtt70.getText().trim().isEmpty()){
                        fact.setCommande(commande);
                        fact.setDate(lblDate.getText());
                        fact.setMontant(Double.valueOf(lblMontant.getText().trim()));
                        fact.setMontant_paye(Double.valueOf(lblNetAPaye.getText().trim()));
                        fact.setMontantNetPayer(Double.valueOf(lblNetAPaye.getText().trim()));
                        fact.setTva(18.0);
                        long idMax = Fabrique.getiFacture().getLastIdFacture() + 1;
                        fact.setNumero("Fact-00"+idMax);
                        commande.setEtat(Fabrique.getiEtat().getEtat("payé"));
                        updateFacture = 1;
                    }else{
                        alert("Entrer le montant des 70% a payé svp !!!");
                    }

                }
            }

            if(fact != null){
                if(updateFacture == 0){
                    Fabrique.getiFacture().addFacture(fact);
                }else{
                    Fabrique.getiFacture().updateFacture(fact);
                }
                Fabrique.getiCommande().updateCommande(commande);
                Utils.showMessage("Paiement","Paiement de la commande","Paiement effectuer avec succés");
                ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

                loadData();

            }

    }

    public void setCommande(Commande commande) {
        this.commande = commande;
        charger();
    }
    private void loadData() throws Exception {

        if (!LIST_COMMANDE.isEmpty()) {
            LIST_COMMANDE.clear();
        }
        LIST_COMMANDE.addAll(Fabrique.getiCommande().getAllCommande());
    }

    public void alert(String libelle){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText("Champ de saisie non valide!!");
        alert.setContentText(libelle);
        alert.showAndWait();
    }

    public boolean isVide(){
        if (commande.getClient().getTypeClient().getNom().equals("Entreprise")){
            if(tfdMtt70.getText().trim().isEmpty() || tfdMtt30.getText().trim().isEmpty()){
                return true;
            }else{
                return false;
            }
        }else{
            if(tfdMontant100.getText().trim().isEmpty()){
                return true;
            }else{
                return false;
            }

        }
    }

    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

}
