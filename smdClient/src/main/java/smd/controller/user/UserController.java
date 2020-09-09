package smd.controller.user;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.commons.codec.digest.DigestUtils;
import projets.java.interfaces.UsersInterface;
import projets.java.model.User;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class UserController implements Initializable, UsersInterface {
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
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, String> colCode;

    @FXML
    private TableColumn<User, String> colPrenom;

    @FXML
    private TableColumn<User, String > colNom;

    @FXML
    private TableColumn<User, String> colAdresse;

    @FXML
    private TableColumn<User, String> colDatenaiss;

    @FXML
    private TableColumn<User, String> colTelephone;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TextField tfdLibelle;

    @FXML
    private Button btnRecherche;

    //FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/users/updateUsers.fxml")));

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            //loadData();
            chargerTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addUser(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader((getClass().getResource("/smd/view/users/addUsers.fxml")));
        //EditController controller = new EditController();
        //loader.setController(controller);
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Ajouter Users");
        //stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public void updateUser(ActionEvent actionEvent) throws IOException {
        if(tableUsers.getSelectionModel().getSelectedItem() != null){
            User selectedUser = tableUsers.getSelectionModel().getSelectedItem();
            //System.out.println("Password: "+DigestUtils.(selectedUser.getPassword()));
            int selectedUserId = tableUsers.getSelectionModel().getSelectedIndex();
            FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/users/updateUsers.fxml")));
            updateUserController controller = new updateUserController();
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
            controller.setUser(selectedUser, selectedUser.getId());
            tableUsers.getSelectionModel().clearSelection();
        }else{
            Utils.showMessage("Alert", "Suppression","Selectionne un utilisateur SVP!!!");
        }
    }



    public void deleteUser(ActionEvent actionEvent) throws Exception {
        if(tableUsers.getSelectionModel().getSelectedItem() != null){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Suppression");
            alert.setHeaderText("Supprimer Catègorie");
            alert.setContentText("Voulez-vous vraiment supprimer l'Utilisateur");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK) {
                Fabrique.getiUser().delete_User(tableUsers.getSelectionModel().getSelectedItem().getId());
                chargerTable();
            }
            tableUsers.getSelectionModel().clearSelection();
        }else {
            Utils.showMessage("Alert", "Suppression","Selectionne un utilisateur SVP!!!");
        }
    }

    public void actualiser(ActionEvent actionEvent) {
        tableUsers.getSelectionModel().clearSelection();
    }

    public void chargerTable() {
        colCode.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCode()));
        colAdresse.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getAdresse()));
        colDatenaiss.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate_naissance()));
        colEmail.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEmail()));
        colNom.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNom()));
        colPrenom.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getPrenom()));
        colTelephone.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTelephone()));
        try {
            //ObservableList<User> data = FXCollections.observableArrayList(Fabrique.getiUser().getAllUsers());
            loadData();
            tableUsers.setItems(LIST_USERS);
            //tbCategorie.setItems(FXCollections.observableArrayList(Fabrique.getiCategorie().getAllCategorie()));
        }
        catch(Exception e){
            Utils.showMessage("Cours RMI","Gestion des Personne","Erreur : "+e.getMessage());
        }
    }

    private void loadData() throws Exception {

        if (!LIST_USERS.isEmpty()) {
            LIST_USERS.clear();
        }
        LIST_USERS.addAll(Fabrique.getiUser().getAllUsers());
    }

    public void rechercherUser(ActionEvent actionEvent) {
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
