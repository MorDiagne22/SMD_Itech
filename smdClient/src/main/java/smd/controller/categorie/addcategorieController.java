package smd.controller.categorie;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import projets.java.model.Categorie;
import smd.utils.Fabrique;
import smd.utils.Utils;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class addcategorieController implements Initializable {

    public Button btnActualiser;
    @FXML
    private TextField tfdNouvCategorie;

    @FXML
    private Button btnAjouter;

    @FXML
    private TableView<Categorie> tbCategorie;

    @FXML
    private TableColumn<Categorie, Long> colId;

    @FXML
    private TableColumn<Categorie, String> colLibelle;

    @FXML
    private TextField tfdLibelle;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    long idCRUD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            chargerTable();

            tbCategorie.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCategorie(newValue));

        }catch (Exception e){

        }

    }

    public void chargerTable() {
        colId.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getId()));
        colLibelle.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        try {
            ObservableList<Categorie> data = FXCollections.observableArrayList(Fabrique.getiCategorie().getAllCategorie());
            tbCategorie.setItems(data);
            //tbCategorie.setItems(FXCollections.observableArrayList(Fabrique.getiCategorie().getAllCategorie()));
        }
        catch(Exception e){
            Utils.showMessage("Cours RMI","Gestion des Personne","Erreur : "+e.getMessage());
        }
    }

    public void addCategorie(ActionEvent actionEvent) {
        if(!tfdNouvCategorie.getText().trim().isEmpty()){
            Categorie cat = new Categorie();
            cat.setLibelle(tfdNouvCategorie.getText().trim());
            try {
                Fabrique.getiCategorie().add_Cat(cat);
                Utils.showMessage("SMD_Itech","Gestion des catègorie","Catégorie ajouté avec succés !");
                chargerTable();
            }
            catch(Exception e){
                Utils.showMessage("SMD_Itech","Gestion des catègorie","Erreur : "+e.getMessage());
            }

        }else{
            Utils.showMessage("SMD_Itech","Gestion des catègorie","Veuillez saisir le libellé du catègorie!");
        }
    }

    private void showCategorie(Categorie c){
        if(c != null){
            idCRUD = c.getId();
            tfdLibelle.setText(c.getLibelle());
            btnModifier.setDisable(false);
            btnSupprimer.setDisable(false);
        }
    }

    public void updateCategorie(ActionEvent actionEvent) throws Exception {
        Categorie c = new Categorie();
        c.setId(idCRUD);
        c.setLibelle(tfdLibelle.getText());
        try {
            Fabrique.getiCategorie().update_Cat(c);
            Utils.showMessage("SMD_Itech","Gestion des catègorie","Catègorie modifié avec succés!");
            vider();
            chargerTable();
        }catch (Exception e){

        }

    }

    public void deleteCategorie(ActionEvent actionEvent) throws Exception {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Suppression");
        alert.setHeaderText("Supprimer Catègorie");
        alert.setContentText("Voulez-vous vraiment supprimer le Catègorie");

        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() == ButtonType.OK) {
            Fabrique.getiCategorie().delete_Cat(idCRUD);
            vider();
            chargerTable();
        }
        tbCategorie.getSelectionModel().clearSelection();
    }

    public void vider(){
        tfdNouvCategorie.setText("");
        tfdLibelle.setText("");
        btnModifier.setDisable(true);
        btnSupprimer.setDisable(true);
    }

    public void actualiser(ActionEvent actionEvent) {
        tbCategorie.getSelectionModel().clearSelection();
        tfdLibelle.setText("");
    }
}
