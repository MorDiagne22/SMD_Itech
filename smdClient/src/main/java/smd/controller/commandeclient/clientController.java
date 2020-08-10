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
import projets.java.model.Client;
import projets.java.model.ProduitAjouter;
import projets.java.model.TypeClient;
import smd.utils.Fabrique;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class clientController implements Initializable {
    public ComboBox cbxClient;
    public TextField tfdNomComplet;
    public TextField tfdEmail;
    public TextField tfdTelephone;
    public TextField tfdAdresse;
    public TextField tfdRechercheClient;
    public Button btnValider;
    public Button btnAnnuler;
    public Label lblRecherche;
    public Button btnNew;
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
        Client cli = Fabrique.getiClient().getClientByName(nomComplet);
        if(cli!=null){
            tfdNomComplet.setText(cli.getNomComplet());
            tfdEmail.setText(cli.getEmail());
            tfdAdresse.setText(cli.getAdresse());
            tfdTelephone.setText(cli.getTelephone());
            cbxClient.getSelectionModel().select(((int) cli.getTypeClient().getId()) - 1);
            lblRecherche.setText("Le client existe !!!");
            btnValider.setDisable(false);

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
    }

    public void valider(ActionEvent actionEvent) {

        if(tfdNomComplet.isDisable()){
            System.out.println("On ajoute la commandeclient");
        }else{
            System.out.println("On ajoute le Client après la commandeclient");
        }

    }

    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void setListe(ObservableList<ProduitAjouter> liste){
        ITEMLIST = liste;
    }
}
