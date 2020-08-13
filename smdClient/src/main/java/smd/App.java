package smd;

import smd.utils.LoadView;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Hello world!
 *
 */
public class App extends Application
{
/*
    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view/FrmAccueil.fxml"));
        primaryStage.setTitle("Connexion");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
*/

    @Override
    public void start(Stage primaryStage) {
        try {
            //LoadView.showView("Categorie","/produit/addproduit.fxml",1);
            //LoadView.showView("Produit","/produit/produit.fxml",1);
            //LoadView.showView("Users","/users/users.fxml",1);
            //LoadView.showView("Commande","/commande/addCommande.fxml",1);
            LoadView.showView("Commande","/commande/commande.fxml",1);
            //LoadView.showView("Categorie","/categorie/addCategorie.fxml",1);
            //LoadView.showView("Client","/client/client.fxml",1);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
