package smd.controller.facture;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfDocument;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import projets.java.interfaces.FactureInterface;
import projets.java.model.Facture;
import projets.java.model.Produit;
import projets.java.model.ProduitAjouter;
import projets.java.model.Produit_Commande;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import java.awt.print.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Stream;

public class factureController implements Initializable, FactureInterface {
    public Button btnProduit;
    public Button btnAccueil;
    public Button btnCategorie;
    public Button btnCommande;
    public Button btnFacture;
    public Button btnUsers;
    public TableView<Facture> tableFacture;
    public TableColumn<Facture, String> colNumero;
    public TableColumn<Facture, String> colDate;
    public TableColumn<Facture, Double> colMontant;
    public TableColumn<Facture, Double> colMttVerser;
    public TableColumn<Facture, Double> colTva;
    public TableColumn<Facture, Double> colNetAPayer;
    public TableColumn<Facture, String> colNumCommande;
    public Button btnImprimer;
    public Button btnActualiser;
    public TextField tfdNumero;
    private List<Produit_Commande> produit_commandes;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chargerTableFacture();

        filterData();

        tableFacture.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showfacture(newValue));

    }

    public void chargerTableFacture(){
        colNumero.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getNumero()));
        colDate.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDate()));
        colMontant.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontant()));
        colMttVerser.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontant_paye()));
        colNetAPayer.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getMontantNetPayer()));
        colTva.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getTva()));
        colNumCommande.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getCommande().getNumero()));

        try {
            loadData();

            tableFacture.setItems(LIST_FACTURE);
        }
        catch(Exception e){
            Utils.showMessage("Cours RMI","Gestion des Personne","Erreur : "+e.getMessage());
        }

    }

    private void loadData() throws Exception {

        if (!LIST_FACTURE.isEmpty()) {
            LIST_FACTURE.clear();
        }
        LIST_FACTURE.addAll(Fabrique.getiFacture().getAllFacture());
    }

    public void showfacture(Facture facture){
        btnImprimer.setDisable(false);
    }

    public void ImprimerAction(ActionEvent actionEvent) throws Exception {

        Facture fac = tableFacture.getSelectionModel().getSelectedItem();
        if(fac != null){

            Document document = new Document(PageSize.A4, 50, 50, 50, 50);

            PdfWriter.getInstance(document, new FileOutputStream("src/main/java/publics/pdf/facture.pdf"));

            document.open();
            Font font = FontFactory.getFont(FontFactory.COURIER, 14, BaseColor.BLACK);
            Font fontNum = FontFactory.getFont(FontFactory.COURIER, 12, BaseColor.BLACK);
            Font font1 = FontFactory.getFont(FontFactory.COURIER, 10, BaseColor.BLACK);
            Font font2 = FontFactory.getFont(FontFactory.COURIER, 8, BaseColor.BLACK);
            Chunk chunk = new Chunk("                          Facture\n\n", font);
            document.add(chunk);
            document.add(new Paragraph("Numéro Facture: "+fac.getNumero()+"\n\n",fontNum));
            document.add(new Paragraph("Nom de l'entreprise: SMDItech                       Nom Complet(Client/Entreprise): " +fac.getCommande().getClient().getNomComplet()+"\n"+
                                              "Adresse: Tivaouane                                  Adresse: " +fac.getCommande().getClient().getAdresse()+"\n"+
                                              "Télèphone: 78 433 31 40                             Télephone: " +fac.getCommande().getClient().getTelephone()+"\n"+
                                              "Email: smdItech@smd.com                             Email: " +fac.getCommande().getClient().getEmail()+"\n\n\n",font2));


            PdfPTable table = new PdfPTable(5);

            addTableHeader(table);

            produit_commandes = Fabrique.getiProduit_commande().getProduitCommande(fac.getCommande().getId());

            if(produit_commandes != null){
                Iterator<Produit_Commande> itr = produit_commandes.iterator();
                while (itr.hasNext()) {
                    Produit_Commande p = itr.next();

                    addRows(table,p);
                }

            }
            //addCustomRows(table);

            document.add(table);

            document.add(new Paragraph("\n\nMontant Total:    "+fac.getMontant()+" Fcfa\n",font1));
            document.add(new Paragraph("TVA:              "+fac.getTva()+" Fcfa",font1));
            document.add(new Paragraph("Montant Net Payé: "+fac.getMontantNetPayer()+" Fcfa\n",font1));
            document.add(new Paragraph("Montant Versé:    "+fac.getMontant_paye()+" Fcfa\n",font1));

            document.close();

        }else{
            Utils.showMessage("Alert","Erreur","Veuillez selectionné une facture");
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

    public void ActualiserAction(ActionEvent actionEvent) {
        tableFacture.getSelectionModel().clearSelection();
        btnImprimer.setDisable(true);
        tfdNumero.setText("");
    }

    private void filterData() {
        FilteredList<Facture> searchedData = new FilteredList<>(LIST_FACTURE, e -> true);

        tfdNumero.setOnKeyReleased(e -> {
            tfdNumero.textProperty().addListener((observable, oldValue, newValue) -> {
                searchedData.setPredicate(facture -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    if (facture.getNumero().toLowerCase().contains(lowerCaseFilter)) {
                        return true;
                    }
                    return false;
                });
            });

            SortedList<Facture> sortedData = new SortedList<>(searchedData);
            sortedData.comparatorProperty().bind(tableFacture.comparatorProperty());
            tableFacture.setItems(sortedData);
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
