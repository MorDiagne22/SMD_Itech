package smd.controller.commandeclient;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.transform.Scale;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import projets.java.interfaces.CommandeInterface;
import projets.java.model.*;
import smd.controller.produit.updateProduitController;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import javax.management.Notification;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.util.*;
import java.util.List;
import java.util.stream.Stream;

public class commandeController implements Initializable , CommandeInterface {
    public Button btnProduit;
    public Button btnAccueil;
    public Button btnCategorie;
    public Button btnCommande;
    public Button btnFacture;
    public Button btnUsers;
    public Button btnActualiser;
    public TableColumn<Commande, Etat> colEtat;
    public Button btnReglement;
    public DatePicker dpDebut;
    public DatePicker dpFin;
    public TextField tfdNomComplet;
    public CategoryAxis x;
    public NumberAxis y;
    public BarChart<String, Double> commandeChart;
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
    private Button btnAnnuler;

    @FXML
    private Button btnImprimer;

    List<Produit_Commande> produit_commandes = new ArrayList<Produit_Commande>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            chargerTableCommande();
            filterData();
            loadCommandeChart();
            tableCommande.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> desactiveButton(newValue));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void chargerTableCommande() throws Exception {
        colNumeo.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        colDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        colMontant.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontant()));
        colClient.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getClient()));
        colEtat.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getEtat()));

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
        windows("/commande/addCommande.fxml", "Commande");
    }




    public void AnnulerCommande(ActionEvent actionEvent) throws Exception {
        Commande  com = tableCommande.getSelectionModel().getSelectedItem();

        if(com!=null){
            if(com.getEtat().getLibelle().equals("en attente")){
                ObservableList<Produit_Commande> listeProdCom = FXCollections.observableArrayList(Fabrique.getiProduit_commande().getProduitCommande(com.getId()));
                if(listeProdCom != null){

                    Iterator<Produit_Commande> itr = listeProdCom.iterator();

                    while (itr.hasNext()) {
                        Produit_Commande prodCom = itr.next();
                        Produit prod = Fabrique.getiProduit().getProduitByName(prodCom.getProduit().getLibelle());
                        System.out.println("Nombre : "+prodCom.getNombre());
                        System.out.println("Libelle: "+prod.getLibelle()+"Quantite prod: "+prod.getQuantite());
                        int qte = prod.getQuantite() + prodCom.getNombre();
                        prod.setQuantite(qte);
                        Fabrique.getiProduit().update_Prod(prod);
                        com.setEtat(Fabrique.getiEtat().getEtat("annulé"));
                        Fabrique.getiCommande().updateCommande(com);
                        vider();

                        chargerTableCommande();
                    }
                    Utils.showMessage("Alert", "Commande","commande annulé avec succés");
                }
            }else{
                Utils.showMessage("Alert", "Commande","Commande est déja annulé !!!");
            }
        }
    }

    public void ActualiserAction(ActionEvent actionEvent) throws Exception {
        tableCommande.getSelectionModel().clearSelection();
        btnAnnuler.setDisable(true);
        btnImprimer.setDisable(true);
        btnAjouter.setDisable(false);
        btnReglement.setDisable(true);
        dpDebut.setValue(null);
        dpFin.setValue(null);
        loadData();
        tableCommande.setItems(LIST_COMMANDE);
        commandeChart.getData().clear();
        loadCommandeChart();

    }

    public void desactiveButton(Commande commande){
        if(commande != null){
            btnAnnuler.setDisable(false);
            btnImprimer.setDisable(false);
            btnAjouter.setDisable(true);
            if(commande.getEtat().getLibelle().equals("en attente") || commande.getEtat().getLibelle().equals("en cours") ){
                btnReglement.setDisable(false);
            }else{
                btnReglement.setDisable(true);
            }

            loadOneCommandeChart(commande);
        }

    }

    public void vider(){
        tableCommande.getSelectionModel().clearSelection();
        btnAnnuler.setDisable(true);
        btnImprimer.setDisable(true);
        btnAjouter.setDisable(false);
        btnReglement.setDisable(true);
    }

    public void Imprimer(ActionEvent actionEvent) throws Exception {

        Commande com = tableCommande.getSelectionModel().getSelectedItem();
        if(com != null){

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            PdfWriter.getInstance(document, new FileOutputStream("src/main/java/publics/pdf/commande.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Font fontNum = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Font fontDate = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Font font1 = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            document.add(new Paragraph("                                                                                Fait le, "+com.getDate(),fontDate));
            Chunk chunk = new Chunk("                        Bon de Commande\n", font);
            document.add(chunk);
            document.add(new Paragraph("Numéro Commande: "+com.getNumero()+"\n\n",fontNum));

                document.add(new Paragraph("    Information de l'entreprise                     Information Client",fontNum));
            document.add(new Paragraph("Nom de l'entreprise: SMDItech                       Nom Complet(Client/Entreprise): " +com.getClient().getNomComplet()+"\n"+
                                              "Adresse: Tivaouane                                  Adresse: "+com.getClient().getAdresse()+"\n"+
                                              "Télèphone: 78 433 31 40                             Télèphone: "+com.getClient().getTelephone()+"\n"+
                                              "Email: smdItech@smd.com                             Email: "+com.getClient().getEmail()+"\n\n\n",font1));

            PdfPTable table = new PdfPTable(5);

            document.add(new Paragraph("                                Produits commandés\n\n",fontNum));

            addTableHeader(table);

            produit_commandes = Fabrique.getiProduit_commande().getProduitCommande(com.getId());

            if(produit_commandes != null){

                Iterator<Produit_Commande> itr = produit_commandes.iterator();
                while (itr.hasNext()) {

                    Produit_Commande p = itr.next();

                    addRows(table,p);
                }
            }

            //addCustomRows(table);
            document.add(table);

            document.add(new Paragraph("\n\nMontant Total:    "+com.getMontant()+" Fcfa\n",font1));

            document.close();

        }else{
            Utils.showMessage("Alert","Erreur","Veuillez selectionné une commande");
        }
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("Numéro", "Libelle", "Quantité","Prix Unitaire", "Total")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table, Produit_Commande p) {
        table.addCell("0");
        table.addCell(p.getProduit().getLibelle());
        table.addCell(String.valueOf(p.getNombre()));
        table.addCell(String.valueOf(p.getProduit().getPrixUnitaire()));
        table.addCell(String.valueOf(p.getNombre() * p.getProduit().getPrixUnitaire()));
    }

    public void faireReglement(ActionEvent actionEvent) throws Exception {
        Commande selectCommande = tableCommande.getSelectionModel().getSelectedItem();
        FXMLLoader loaderUpdate = new FXMLLoader((getClass().getResource("/smd/view/paiement/paiement.fxml")));
        paiementController controller = new paiementController();
        loaderUpdate.setController(controller);
        Parent root = loaderUpdate.load();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle("Paiement");
        //stage.getIcons().add(new Image("/images/logo.png"));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
        controller.setCommande(selectCommande);
        tableCommande.getSelectionModel().clearSelection();

        if (selectCommande.getClient().getTypeClient().getNom().equals("Entreprise")){
            controller.tfdMontant100.setDisable(true);
            if (selectCommande.getFactures() == null && selectCommande.getEtat().getLibelle().equals("en attente")){
                controller.tfdMtt30.setDisable(false);
                controller.tfdMtt70.setDisable(true);
            }else{
                controller.tfdMtt30.setDisable(true);
                controller.tfdMtt70.setDisable(false);
            }


        }else {
            controller.tfdMtt70.setDisable(true);
            controller.tfdMtt30.setDisable(true);
            controller.tfdMontant100.setDisable(false);
        }

        try {
            actualiser();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void actualiser() throws Exception {
        tableCommande.getSelectionModel().clearSelection();
        btnAnnuler.setDisable(true);
        btnImprimer.setDisable(true);
        btnAjouter.setDisable(false);
        btnReglement.setDisable(true);
    }

    public void rechercherIntervalle(){

        /*

        */
    }

    public void searchDpFinAction(ActionEvent actionEvent) throws Exception {
        ObservableList<Commande> listeCommande = FXCollections.observableArrayList();
        LocalDate dateFin = dpFin.getValue();
        LocalDate dateDebut = dpDebut.getValue();
        if (dateFin != null && dateDebut != null){

            if(dateFin.isAfter(dateDebut)){

                String date_Fin = String.valueOf(dpFin.getValue());
                String date_debut = String.valueOf(dpDebut.getValue());

                if (!listeCommande.isEmpty()) {
                    listeCommande.clear();
                }

                listeCommande.addAll(Fabrique.getiCommande().getCommandeByIntervalle(date_debut, date_Fin));

                if(tableCommande != null){
                    tableCommande.getItems().clear();
                    tableCommande.setItems(listeCommande);
                }
            }
        }
    }

    public void searchDpDebut(ActionEvent actionEvent) throws Exception {
        ObservableList<Commande> listeCommande = FXCollections.observableArrayList();
        LocalDate dateFin = dpFin.getValue();
        LocalDate dateDebut = dpDebut.getValue();
        if (dateFin != null && dateDebut != null){

            if(dateFin.isAfter(dateDebut)){

                String date_Fin = String.valueOf(dpFin.getValue());
                String date_debut = String.valueOf(dpDebut.getValue());

                if (!listeCommande.isEmpty()) {
                    listeCommande.clear();
                }

                listeCommande.addAll(Fabrique.getiCommande().getCommandeByIntervalle(date_debut, date_Fin));

                if(tableCommande != null){
                    tableCommande.getItems().clear();
                    tableCommande.setItems(listeCommande);
                }
            }
        }
    }

    private void filterData() {
        FilteredList<Commande> searchedData = new FilteredList<>(LIST_COMMANDE, e -> true);

        tfdNomComplet.setOnKeyReleased(e -> {
            tfdNomComplet.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(commande -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (commande.getClient().getNomComplet().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Commande> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tableCommande.comparatorProperty());
            tableCommande.setItems(sortedData);
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
        LoadView.showView(title, path, 1);
    }

    private void loadCommandeChart() {

        ObservableList lists = FXCollections.observableArrayList();
        XYChart.Series<String, Double> series = new XYChart.Series<>();

        for (Commande c : LIST_COMMANDE) {
            series.getData().add(new XYChart.Data(c.getNumero(), c.getMontant()));
            lists.add(c.getNumero());
        }

        series.setName("Commande");
        //x.setCategories(lists);
        commandeChart.getData().add(series);
    }

    public void loadOneCommandeChart(Commande c) {

        if (c != null) {
            ObservableList lists = FXCollections.observableArrayList();
            XYChart.Series<String, Double> series = new XYChart.Series<>();

            commandeChart.getData().clear();

                series.getData().add(new XYChart.Data(c.getNumero(), c.getMontant()));
                lists.add(c.getNumero());


            series.setName("Commande");
            //x.setCategories(lists);
            commandeChart.getData().add(series);
        }

    }



}
