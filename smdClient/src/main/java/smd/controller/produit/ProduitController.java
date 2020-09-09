package smd.controller.produit;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projets.java.interfaces.ProduitInterface;
import projets.java.model.Produit;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ProduitController implements Initializable, ProduitInterface {
    public Button btnProduit;
    public Button btnAccueil;
    public Button btnCategorie;
    public Button btnCommande;
    public Button btnFacture;
    public Button btnUsers;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnSupprimer;

    @FXML
    private Button btnActualiser;

    @FXML
    private TableView<Produit> tableProduit;

    @FXML
    private TableColumn<Produit, String> colLibelle;

    @FXML
    private TableColumn<Produit, Integer> colQuantite;

    @FXML
    private TableColumn<Produit, Integer> colQteMin;

    @FXML
    private TableColumn<Produit, Integer> colQteMax;

    @FXML
    private TableColumn<Produit, Integer> colQteCrit;

    @FXML
    private TableColumn<Produit, Integer> colPrixU;

    @FXML
    private TextField tfdLibelle;

    @FXML
    private Button btnRecherche;
   // FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/produit/updateProduit.fxml")));

    @FXML
    public void actualiser(ActionEvent event) {
        tableProduit.getSelectionModel().clearSelection();
    }

    @FXML
    public void addProduit(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/smd/view/produit/addProduit.fxml")));
        //EditController controller = new EditController();
        //loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Edit Product");
        //stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void deleteProduit(ActionEvent event) throws Exception {
        if(tableProduit.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Supprimer Catègorie");
            alert.setContentText("Voulez-vous vraiment supprimer le Produit");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Fabrique.getiProduit().delete_Prod(tableProduit.getSelectionModel().getSelectedItem().getId());
                chargerTable();
            }
            tableProduit.getSelectionModel().clearSelection();
        }else {
            Utils.showMessage("Alert", "Suppression","Selectionne un Produit SVP!!!");
        }

    }

    @FXML
    public void updateProduit(ActionEvent event) throws IOException {
        if(tableProduit.getSelectionModel().getSelectedItem() != null){
            Produit selectedProduct = tableProduit.getSelectionModel().getSelectedItem();
            int selectedProductId = tableProduit.getSelectionModel().getSelectedIndex();
            FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/produit/updateProduit.fxml")));
            updateProduitController controller = new updateProduitController();
            loaderUpdate.setController(controller);
            Parent root = loaderUpdate.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Edit Product");
            //stage.getIcons().add(new Image("/images/logo.png"));
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);
            stage.show();
            controller.setProduct(selectedProduct, selectedProduct.getId());
            tableProduit.getSelectionModel().clearSelection();
        }else{
            Utils.showMessage("Alert", "Suppression","Selectionne un Produit SVP!!!");
        }


    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerTable();

        filterData();


    }

    public void rechercherProduit(ActionEvent actionEvent) {
    }

    public void chargerTable() {
        colLibelle.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getLibelle()));
        colQuantite.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite()));
        colQteCrit.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite_crit()));
        colQteMin.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite_min()));
        colQteMax.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getQuantite_max()));
        colPrixU.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrixUnitaire()));
        try {
            //ObservableList<Produit> data = FXCollections.observableArrayList(Fabrique.getiProduit().getAllProduit());

            loadData();

            tableProduit.setItems(LIST_PRODUITS);
            //tbCategorie.setItems(FXCollections.observableArrayList(Fabrique.getiCategorie().getAllCategorie()));
        }
        catch(Exception e){
            Utils.showMessage("Cours RMI","Gestion des Personne","Erreur : "+e.getMessage());
        }
    }

    private void loadData() throws Exception {

        if (!LIST_PRODUITS.isEmpty()) {
            LIST_PRODUITS.clear();
        }
        LIST_PRODUITS.addAll(Fabrique.getiProduit().getAllProduit());
    }
    private void filterData() {
        FilteredList<Produit> searchedData = new FilteredList<>(LIST_PRODUITS, e -> true);

        tfdLibelle.setOnKeyReleased(e -> {
            tfdLibelle.textProperty().addListener((observable, oldValue, newValue) -> {
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
            sortedData.comparatorProperty().bind(tableProduit.comparatorProperty());
            tableProduit.setItems(sortedData);
        });
    }
    public void ProduitAction(ActionEvent actionEvent) throws Exception {
        windows("produit/produit.fxml", "Produit");
    }

    public void AccueilAction(ActionEvent actionEvent) throws Exception {
        windows("accueil/accueil.fxml", "Accueil");
    }

    public void CategorieAction(ActionEvent actionEvent) throws Exception {
        windows("categorie/categorie.fxml", "Catégorie");
    }

    public void CommandeAction(ActionEvent actionEvent) throws Exception {
        windows("commande/commande.fxml", "Commande");
    }

    public void FactureAction(ActionEvent actionEvent) throws Exception {
        windows("facture/facture.fxml", "Facture");
    }

    public void UsersAction(ActionEvent actionEvent) throws Exception {
        windows("users/users.fxml", "Users");
    }

    private void windows(String path, String title) throws Exception {

        LoadView.showView(title,path,1);
    }

}
