package smd.controller.produit;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import projets.java.interfaces.ProduitInterface;
import projets.java.interfaces.UsersInterface;
import projets.java.model.Categorie;
import projets.java.model.Marque;
import projets.java.model.Produit;
import smd.utils.Fabrique;
import smd.utils.Utils;

import javax.xml.ws.Action;
import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class addProduitController implements Initializable, ProduitInterface {


    @FXML
    private Label lbl_Add_Upd;

    @FXML
    private TextField tfdLibelle;

    @FXML
    private TextField tfdQte;

    @FXML
    private TextField tfdModele;

    @FXML
    private TextField tfdQteMin;

    @FXML
    private TextField tfdQteCrit;

    @FXML
    private TextField tfdQteMax;

    @FXML
    private ComboBox<Marque> cbxMarque;

    @FXML
    private TextField tfdPU;

    @FXML
    private ComboBox<Categorie> cbxCategorie;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnAnnuler;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try{
            chargerCombox();
        }catch (Exception e){

        }
    }

    public void saveHandler(javafx.event.ActionEvent actionEvent) throws Exception {
        if (!isVide()){
            Produit p = new Produit();
            p.setLibelle(tfdLibelle.getText());
            p.setModele(tfdModele.getText());
            p.setPrixUnitaire(Integer.parseInt(tfdPU.getText()));
            p.setQuantite(Integer.parseInt(tfdQte.getText()));
            p.setQuantite_crit(Integer.parseInt(tfdQteCrit.getText()));
            p.setQuantite_max(Integer.parseInt(tfdQteMax.getText()));
            p.setQuantite_min(Integer.parseInt(tfdQteMin.getText()));
            p.setCategorie(cbxCategorie.getValue());
            p.setMarque(cbxMarque.getValue());
            Fabrique.getiProduit().add_Prod(p);
            LIST_PRODUITS.clear();
            LIST_PRODUITS.addAll(Fabrique.getiProduit().getAllProduit());
            Utils.showMessage("Alert","AJOUT","Le produit a été ajouté avec succès!!!");
            vider();

        }else{
            Utils.showMessage("Alert","Erreur","Veuillez remplir les champs SVP!!!");
        }
    }

    public void vider() throws Exception {
        tfdQteMin.setText("");
        tfdQte.setText("");
        tfdQteMax.setText("");
        tfdQteCrit.setText("");
        tfdPU.setText("");
        tfdModele.setText("");
        tfdLibelle.setText("");
        cbxMarque.getItems().clear();
        cbxCategorie.getItems().clear();
        chargerCombox();
    }

    public void Annuler(javafx.event.ActionEvent actionEvent) {

    }

    public boolean isVide(){
        if(tfdLibelle.getText().trim().isEmpty() || tfdModele.getText().trim().isEmpty() || tfdQte.getText().trim().isEmpty() || tfdPU.getText().trim().isEmpty()
                || tfdQteCrit.getText().trim().isEmpty() || tfdQteMax.getText().trim().isEmpty() || tfdQteMin.getText().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void chargerCombox() throws Exception {
        List<Categorie> listeCategorie = Fabrique.getiCategorie().getAllCategorie();
        cbxCategorie.setItems(FXCollections.observableArrayList(listeCategorie));

        List<Marque> listeMarque = Fabrique.getiMarque().getAllMarque();
        cbxMarque.setItems(FXCollections.observableArrayList(listeMarque));
    }


    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }
}

