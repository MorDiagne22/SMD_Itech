package smd.controller.login;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.commons.codec.digest.DigestUtils;
import projets.java.model.User;
import smd.utils.Fabrique;
import smd.utils.LoadView;
import smd.utils.Utils;

import java.net.URL;
import java.util.ResourceBundle;

public class loginController implements Initializable {
    public TextField tfdEmail;
    public Button btnConnecter;
    public PasswordField tfdPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void login(ActionEvent actionEvent) throws Exception {
        if(!isVide()){
            String password = DigestUtils.sha1Hex(tfdPassword.getText().trim());
            String email = tfdEmail.getText().trim();
            User user = Fabrique.getiUser().getEmail(email);
            if(user != null){
                if(user.getPassword().equals(password)){

                    windows("/produit/produit.fxml", "Point of Sales");

                    //Utils.showMessage("Alert", "Login","Email et Password valide ");
                }else {
                    Utils.showMessage("Alert", "Login","Mot de passe incorrecte ");
                }


            }else{
                Utils.showMessage("Alert", "Login","Email nom valide ");
            }
            //Utils.showMessage("Alert", "Login","Password: "+DigestUtils.sha1Hex(tfdPassword.getText().trim()));
        }else{
            Utils.showMessage("Alert", "Login","Erreur");
        }
    }

    public boolean isVide(){
        if(tfdEmail.getText().trim().isEmpty() || tfdPassword.getText().trim().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    private void windows(String path, String title) throws Exception {

        LoadView.showView(title,path,1);
    }

}
