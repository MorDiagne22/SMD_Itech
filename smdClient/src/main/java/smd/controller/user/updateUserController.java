package smd.controller.user;

import javafx.collections.FXCollections;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import projets.java.interfaces.UsersInterface;
import projets.java.model.Categorie;
import projets.java.model.Marque;
import projets.java.model.Produit;
import projets.java.model.User;
import smd.utils.Fabrique;
import smd.utils.Utils;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class updateUserController implements Initializable, UsersInterface {
    private User user;
    private long userIdSelectionner;
    @FXML
    private Label lbl_Add_Upd;

    @FXML
    private TextField tfdNom;

    @FXML
    private TextField tfdAdresse;

    @FXML
    private TextField tfdPrenom;

    @FXML
    private TextField tfdTelephone;

    @FXML
    private TextField tfdEmail;

    @FXML
    private Button btnEnregistrer;

    @FXML
    private Button btnAnnuler;

    @FXML
    private DatePicker dpDatenaiss;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void saveHandler(javafx.event.ActionEvent actionEvent) throws Exception {
        if (!isVide()){
            User u = new User();
            u.setId(userIdSelectionner);
            u.setCode("C-0002");
            u.setAdresse(tfdAdresse.getText());
            u.setDate_naissance(String.valueOf(dpDatenaiss.getValue()));
            u.setEmail(tfdEmail.getText());
            u.setNom(tfdNom.getText());
            u.setPrenom(tfdPrenom.getText());
            u.setTelephone(tfdTelephone.getText());
            u.setPassword(user.getPassword());
            Fabrique.getiUser().update_User(u);
            LIST_USERS.clear();
            LIST_USERS.addAll(Fabrique.getiUser().getAllUsers());
            Utils.showMessage("Alert","Modification Utilisateur","Utilisateur modifier avec succès");
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();

        }else{
            Utils.showMessage("Alert","Erreur","Veuillez remplir les champs SVP!!!");
        }
    }

    public void vider() throws Exception {
        tfdTelephone.setText("");
        tfdPrenom.setText("");
        tfdEmail.setText("");
        tfdAdresse.setText("");
        tfdNom.setText("");
        dpDatenaiss.setValue(null);
    }

    public void Annuler(javafx.event.ActionEvent actionEvent) {

    }

    public boolean isVide(){
        if(tfdNom.getText().trim().isEmpty() || tfdAdresse.getText().trim().isEmpty() || tfdEmail.getText().trim().isEmpty() || tfdPrenom.getText().trim().isEmpty()
                || tfdTelephone.getText().trim().isEmpty() || dpDatenaiss.getValue() == null)
        {

            return true;
        }else{
            return false;
        }
    }

    public void setUser(User user, long userIdSelectionner) {
        this.user = user;
        this.userIdSelectionner = userIdSelectionner;
        setData();
    }



    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    private void setData() {
        tfdAdresse.setText(user.getAdresse());
        tfdEmail.setText(user.getEmail());
        tfdNom.setText(user.getNom());
        tfdPrenom.setText(user.getPrenom());
        tfdTelephone.setText(user.getTelephone());
        dpDatenaiss.setValue(LocalDate.parse(user.getDate_naissance()));
    }
}
