package smd.controller.commandeclient;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import projets.java.model.*;
import smd.utils.Fabrique;
import smd.utils.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class clientController implements Initializable {
    public ComboBox<TypeClient> cbxClient;
    public TextField tfdNomComplet;
    public TextField tfdEmail;
    public TextField tfdTelephone;
    public TextField tfdAdresse;
    public TextField tfdRechercheClient;
    public Button btnValider;
    public Button btnAnnuler;
    public Label lblRecherche;
    private long idClient, idCommande;
    public Button btnNew;
    private Client cli = null;
    private int somme;
    private String numero;
    private ObservableList<ProduitAjouter> ITEMLIST = FXCollections.observableArrayList();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        List<TypeClient> listeCategorie = null;
        try {
            listeCategorie = Fabrique.getiTypeClient().getAllTypeClient();
            cbxClient.setItems(FXCollections.observableArrayList(listeCategorie));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void searchClient(KeyEvent keyEvent) throws Exception {

        String nomComplet = tfdRechercheClient.getText().trim();
         cli = Fabrique.getiClient().getClientByName(nomComplet);
        if(cli!=null){
            tfdNomComplet.setText(cli.getNomComplet());
            tfdEmail.setText(cli.getEmail());
            tfdAdresse.setText(cli.getAdresse());
            tfdTelephone.setText(cli.getTelephone());
            cbxClient.getSelectionModel().select(((int) cli.getTypeClient().getId()) - 1);
            lblRecherche.setText("Le client existe !!!");
            btnValider.setDisable(false);
            idClient = cli.getId();

        }else{
            tfdNomComplet.setText("");
            tfdEmail.setText("");
            tfdAdresse.setText("");
            tfdTelephone.setText("");
            lblRecherche.setText("Ce Client n'existe pas. Cliquez sur le boutton New pour l'ajouté !!!");

        }
    }

    public void NewClient(ActionEvent actionEvent) {
        tfdTelephone.setDisable(false);
        tfdAdresse.setDisable(false);
        tfdEmail.setDisable(false);
        tfdNomComplet.setDisable(false);
        cbxClient.setDisable(false);
        btnValider.setDisable(false);
        tfdRechercheClient.setText("");
    }

    public void AnnulerAction(ActionEvent actionEvent) {
        viderChamps();
    }

    public void valider(ActionEvent actionEvent) throws Exception {

        if(tfdNomComplet.isDisable() && !tfdNomComplet.getText().trim().isEmpty()){

            try{

                cli = Fabrique.getiClient().getClientByName(tfdNomComplet.getText().trim());
                //Recuperation de l'id du nouveau client
                idClient = cli.getId();
                // Ajout de la commande
                Commande c = new Commande();
                c.setDate(String.valueOf(LocalDate.now()));
                c.setDateLivraison(String.valueOf(LocalDate.now()));
                c.setClient(cli);
                c.setMontant((double) somme);
                numero = genererNumeroCommande();
                c.setNumero(numero);
                //Etat de la commande est validé par défaut et prend la valeur 1
                c.setEtat(Integer.parseInt(String.valueOf(1)));

                Fabrique.getiCommande().addCommande(c);
                //Recuperatio de l'id commande
                idCommande = Fabrique.getiCommande().getLastIdCommande();
                c.setId(idCommande);
                Produit_Commande pc = new Produit_Commande();

                //Ajout de produit commande

                Iterator<ProduitAjouter> itr = ITEMLIST.iterator();

                while (itr.hasNext()) {
                    ProduitAjouter p = itr.next();
                    pc.setCommande(c);
                    pc.setNombre(p.getQuantite());
                    Produit prod = Fabrique.getiProduit().getProduitByName(p.getLibelle());
                    pc.setProduit(prod);

                    Fabrique.getiProduit_commande().addProduitCommande(pc);

                    //Modification de stock
                    int nouveauQte = prod.getQuantite()-p.getQuantite();

                    prod.setQuantite(nouveauQte);

                    Fabrique.getiProduit().update_Prod(prod);
                }
                Utils.showMessage("Alert","Commande","La Commande a été ajouter avec succés!!!");

                viderChamps();

            }catch (Exception e){
                Utils.showMessage("Alert","Erreur","Une erreur est survenue lors du traitment des informations!!!");
                //viderChamps();
            }

        }else{

            if(!verifierChamp()) {
                try{
                    cli = new Client();
                    //Chargements des information du client
                    cli.setNomComplet(tfdNomComplet.getText().trim());
                    cli.setAdresse(tfdAdresse.getText().trim());
                    cli.setEmail(tfdEmail.getText().trim());
                    cli.setTelephone(tfdTelephone.getText().trim());
                    cli.setTypeClient(cbxClient.getValue());

                    //Ajout du nouveau client
                    Fabrique.getiClient().addClient(cli);
                    //Recuperation de l'id du nouveau client
                    idClient = Fabrique.getiClient().getIdMax();
                    cli.setId(idClient);
                    // Ajout de la commande
                    Commande c = new Commande();
                    c.setDate(String.valueOf(LocalDate.now()));
                    c.setDateLivraison(String.valueOf(LocalDate.now()));
                    c.setClient(cli);
                    c.setMontant((double) somme);
                    numero = genererNumeroCommande();
                    c.setNumero(numero);

                    c.setEtat(Integer.parseInt(String.valueOf(1)));

                    Fabrique.getiCommande().addCommande(c);
                    //Recuperatio de l'id commande
                    idCommande = Fabrique.getiCommande().getLastIdCommande();
                    c.setId(idCommande);
                    Produit_Commande pc = new Produit_Commande();

                    //Ajout de produit commande

                    Iterator<ProduitAjouter> itr = ITEMLIST.iterator();

                    while (itr.hasNext()) {
                        ProduitAjouter p = itr.next();
                        pc.setCommande(c);
                        pc.setNombre(p.getQuantite());
                        pc.setProduit(Fabrique.getiProduit().getProduitByName(p.getLibelle()));

                        Fabrique.getiProduit_commande().addProduitCommande(pc);


                    }

                    Utils.showMessage("Alert","Commande","La Commande a été ajouter avec succés!!!");
                    viderChamps();

                }catch (Exception e){
                    Utils.showMessage("Alert","Erreur","Une erreur est survenue lors du traitment des informations!!!");
                    //viderChamps();
                }

            }
            else{

                Utils.showMessage("Erreur","Commande","Veuillez remplir toutes les champs");

            }

        }

    }

    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void setListe(ObservableList<ProduitAjouter> liste){
        ITEMLIST = liste;
    }

    public void setSomme(int som){
        somme = som;
    }

    public  boolean verifierChamp(){
        if(tfdNomComplet.getText().trim().isEmpty() || tfdAdresse.getText().trim().isEmpty() || tfdEmail.getText().isEmpty()
                || tfdTelephone.getText().trim().isEmpty() || cbxClient.getSelectionModel().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public String genererNumeroCommande() throws Exception {
        long id = Fabrique.getiCommande().getLastIdCommande()+1;
        return "COM-00"+id;
    }

    public void viderChamps(){
        tfdTelephone.setDisable(true);
        tfdAdresse.setDisable(true);
        tfdEmail.setDisable(true);
        tfdNomComplet.setDisable(true);
        cbxClient.setDisable(true);
        btnValider.setDisable(true);
        tfdNomComplet.setText("");
        tfdEmail.setText("");
        tfdAdresse.setText("");
        tfdTelephone.setText("");
        lblRecherche.setText("");
        tfdRechercheClient.setText("");
        cbxClient.getItems().clear();
    }
}
