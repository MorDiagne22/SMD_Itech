package smd.controller.user;

import javafx.fxml.Initializable;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.security.DigestInputStream;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.commons.codec.digest.DigestUtils;
import projets.java.interfaces.UsersInterface;
import projets.java.model.User;
import smd.utils.Fabrique;
import smd.utils.Utils;

public class addUserController implements Initializable, UsersInterface {

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

    public boolean isVide(){
        if(tfdNom.getText().trim().isEmpty() || tfdAdresse.getText().trim().isEmpty() || tfdEmail.getText().trim().isEmpty() || tfdPrenom.getText().trim().isEmpty()
                || tfdTelephone.getText().trim().isEmpty() || dpDatenaiss.getValue() == null)
        {

            return true;
        }else{
            return false;
        }
    }

    public void saveHandler(javafx.event.ActionEvent actionEvent) throws Exception {
        if(!isVide()){
            User u = new User();
            long id = Fabrique.getiUser().getLastId();

            u.setCode("U-000"+id);
            u.setAdresse(tfdAdresse.getText());
            u.setDate_naissance(String.valueOf(dpDatenaiss.getValue()));
            u.setEmail(tfdEmail.getText());
            u.setNom(tfdNom.getText());
            u.setPrenom(tfdPrenom.getText());
            u.setTelephone(tfdTelephone.getText());
            u.setPassword(DigestUtils.sha1Hex("passer"));
            //DigestUtils.sha1Hex("passer");
            Fabrique.getiUser().add_User(u);
            LIST_USERS.clear();
            LIST_USERS.addAll(Fabrique.getiUser().getAllUsers());
            Utils.showMessage("Alert","Ajout Utilisateur","Utilisateur ajouté avec succès");
            vider();
        }else{
            Utils.showMessage("Alert","Erreur","Veuillez remplir les champs SVP!!!");
        }
    }

    public void Annuler(javafx.event.ActionEvent actionEvent) {

    }

    public void close(javafx.event.ActionEvent actionEvent) {
        ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
    }

    public void vider(){
        tfdTelephone.setText("");
        tfdPrenom.setText("");
        tfdEmail.setText("");
        tfdAdresse.setText("");
        tfdNom.setText("");
        dpDatenaiss.setValue(null);
    }
}
