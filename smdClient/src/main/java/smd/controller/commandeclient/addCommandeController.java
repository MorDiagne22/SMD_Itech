package smd.controller.commandeclient;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projets.java.interfaces.ProduitInterface;
import projets.java.model.Produit;
import projets.java.model.ProduitAjouter;
import smd.utils.Fabrique;
import smd.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.Optional;
import java.util.ResourceBundle;

public class addCommandeController implements Initializable , ProduitInterface {

    public TableColumn<Produit, String> colProduit;
    public TextField tfdQteStock;
    public TableColumn<ProduitAjouter, String> colLibelle;
    public TableColumn<ProduitAjouter, Integer> colQuantite;
    public TableColumn<ProduitAjouter, Integer> colPrix;
    public TableColumn<ProduitAjouter, Integer> colTotal;
    public Label lblSomme;
    @FXML
    private TableView<ProduitAjouter> tableProduitCommande;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnAnnulerCom;

    @FXML
    private TextField tfdLibelle;

    @FXML
    private TextField tfdQuantite;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnAnnulerProd;

    @FXML
    private TextField tfdPrixUnitaire;

    @FXML
    private TableView<Produit> tableProduits;

    @FXML
    private TextField tfdRecherche;

    private ObservableList<ProduitAjouter> ITEMLIST;
    private int somme=0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ITEMLIST = FXCollections.observableArrayList();
        try {
            chargerTableProduit();
            filterData();

            tableProduits.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProduit(newValue));
            tableProduitCommande.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProduitAjouter(newValue));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chargerTableProduit() throws Exception {

        colProduit.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));

        loadData();

        tableProduits.setItems(LIST_PRODUITS);
    }

    private void loadData() throws Exception {

        if (!LIST_PRODUITS.isEmpty()) {
            LIST_PRODUITS.clear();
        }
        LIST_PRODUITS.addAll(Fabrique.getiProduit().getAllProduit());
    }

    private void filterData() {
        FilteredList<Produit> searchedData = new FilteredList<>(LIST_PRODUITS, e -> true);

        tfdRecherche.setOnKeyReleased(e -> {
            tfdRecherche.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(produit -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (produit.getLibelle().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Produit> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tableProduits.comparatorProperty());
            tableProduits.setItems(sortedData);
        });
    }

    public void showProduit(Produit p){
        if(p != null){
            tfdLibelle.setText(p.getLibelle());
            tfdPrixUnitaire.setText(String.valueOf(p.getPrixUnitaire()));
            tfdQteStock.setText(String.valueOf(p.getQuantite()));
            btnAjouter.setDisable(false);
            btnAnnulerProd.setDisable(false);
        }
    }

    public void showProduitAjouter(ProduitAjouter p){
        if(p != null){
            tfdLibelle.setText(p.getLibelle());
            tfdPrixUnitaire.setText(String.valueOf(p.getPrix()));
            //tfdQteStock.setText(String.valueOf(p.getQuantite()));
            tfdQuantite.setText(String.valueOf(p.getQuantite()));
            btnEdit.setDisable(false);
            btnAnnulerProd.setDisable(false);
            btnDelete.setDisable(false);
        }
    }

    public void Ajouter(ActionEvent actionEvent) throws Exception {
        if (!validateInput()) {
            ProduitAjouter p = new ProduitAjouter();
            p.setLibelle(tfdLibelle.getText().trim());
            p.setQuantite(Integer.parseInt(tfdQuantite.getText().trim()));
            p.setPrix(Integer.parseInt(tfdPrixUnitaire.getText().trim()));
            p.setTotal(Integer.parseInt(tfdQuantite.getText().trim()) * Integer.parseInt(tfdPrixUnitaire.getText().trim()));
            ITEMLIST.add(p);
            tableProduits.getSelectionModel().clearSelection();
            somme = somme+p.getTotal();
            chargerTableProduitAjouter();
            lblSomme.setText(String.valueOf(somme));
            if (btnEnregistrer.isDisable()) {
                btnEnregistrer.setDisable(false);
                btnAnnulerCom.setDisable(false);
            }
            vider_prod();
        }
    }

    private boolean validateInput(){
        if(tfdLibelle.getText().trim().isEmpty() || tfdPrixUnitaire.getText().trim().isEmpty() || tfdQuantite.getText().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void chargerTableProduitAjouter() throws Exception {

        colLibelle.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        colPrix.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrix()));
        colQuantite.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite()));
        colTotal.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTotal()));

        //loadData();

        tableProduitCommande.setItems(ITEMLIST);
    }

    public void prod_annuler(ActionEvent actionEvent) {
        tableProduitCommande.getSelectionModel().clearSelection();
        tableProduits.getSelectionModel().clearSelection();
        vider_prod();
    }

    public void delete_prod(ActionEvent actionEvent) throws Exception {
        if(tableProduitCommande.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Supprimer Catègorie");
            alert.setContentText("Voulez-vous vraiment supprimer le Produit");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Iterator<ProduitAjouter> itr = ITEMLIST.iterator();
                while (itr.hasNext()) {
                    ProduitAjouter p = itr.next();
                    if (p.getId() == tableProduitCommande.getSelectionModel().getSelectedItem().getId()) {
                        itr.remove();
                        vider_prod();
                        somme = somme - p.getTotal();
                        lblSomme.setText(String.valueOf(somme));
                        break;
                    }
                }
                try {
                    chargerTableProduitAjouter();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            tableProduitCommande.getSelectionModel().clearSelection();
        }else {
            Utils.showMessage("Alert", "Suppression","Selectionne un Produit SVP!!!");
        }
    }

    public void vider_prod(){
        tfdLibelle.setText("");
        tfdPrixUnitaire.setText("");
        tfdQteStock.setText("");
        tfdQuantite.setText("");
        btnAnnulerProd.setDisable(true);
        btnAjouter.setDisable(true);
        btnEdit.setDisable(true);
        btnDelete.setDisable(true);
    }

    public void AnnulerCommande(ActionEvent actionEvent) {
        tableProduitCommande.getItems().clear();
        somme = 0;
        lblSomme.setText(String.valueOf(somme));
        btnAnnulerCom.setDisable(true);
        btnEnregistrer.setDisable(true);
        tableProduits.getSelectionModel().clearSelection();
    }

    public void saveHandler(ActionEvent actionEvent) throws IOException {

        FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/client/client.fxml")));
        clientController controller = new clientController();
        loaderUpdate.setController(controller);
        Parent root = loaderUpdate.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Product");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setListe(ITEMLIST);

    }


}

