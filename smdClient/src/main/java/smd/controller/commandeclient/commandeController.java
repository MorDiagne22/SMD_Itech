package smd.controller.commandeclient;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import projets.java.interfaces.CommandeInterface;
import projets.java.model.*;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import javax.management.Notification;
import java.net.URL;
import java.time.Duration;
import java.util.Iterator;
import java.util.ResourceBundle;

public class commandeController implements Initializable , CommandeInterface {
    @FXML
    private TableView<Commande> tableCommande;

    @FXML
    private TableColumn<Commande, String> colNumeo;

    @FXML
    private TableColumn<Commande, String> colDate;

    @FXML
    private TableColumn<Commande, Double> colMontant;

    @FXML
    private TableColumn<Commande, Client> colClient;

    @FXML
    private Button btnAjouter;

    @FXML
    private Button btnModifier;

    @FXML
    private Button btnAnnuler;

    @FXML
    private Button btnImprimer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            chargerTableCommande();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chargerTableCommande() throws Exception {
        colNumeo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        colDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        colMontant.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontant()));
        colClient.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getClient()));

        loadData();

        tableCommande.setItems(LIST_COMMANDE);

    }

    private void loadData() throws Exception {

        if (!LIST_COMMANDE.isEmpty()) {
            LIST_COMMANDE.clear();
        }
        LIST_COMMANDE.addAll(Fabrique.getiCommande().getAllCommande());
    }

    public void AddCommande(ActionEvent actionEvent) throws Exception {
        windows("/commande/addcommande.fxml", "Commande");
    }

    private void windows(String path, String title) throws Exception {
        LoadView.showView(title, path, 1);
    }


    public void AnnulerCommande(ActionEvent actionEvent) throws Exception {
        Commande  com = tableCommande.getSelectionModel().getSelectedItem();

        if(com!=null){
            if(com.getEtat() == 1){
                ObservableList<Produit_Commande> listeProdCom = FXCollections.observableArrayList(Fabrique.getiProduit_commande().getProduitCommande(com.getId()));
                if(listeProdCom != null){

                    Iterator<Produit_Commande> itr = listeProdCom.iterator();

                    while (itr.hasNext()) {
                        Produit_Commande prodCom = itr.next();

                        Produit prod = prodCom.getProduit();
                        prod.setQuantite(prod.getQuantite() + prodCom.getNombre());

                        Fabrique.getiProduit().update_Prod(prod);
                        com.setEtat(Integer.parseInt(String.valueOf(0)));
                        Fabrique.getiCommande().updateCommande(com);
                        Utils.showMessage("Alert", "Commande","commande annulé avec succés");

                        tableCommande.getSelectionModel().clearSelection();

                    }
                }
            }else{
                Utils.showMessage("Alert", "Commande","Commande est déja annulé !!!");
            }
        }
    }
}
