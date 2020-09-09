package smd.controller.accueil;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import smd.utils.LoadView;

public class accueilController {
    public Button btnProduit;
    public Button btnAccueil;
    public Button btnCategorie;
    public Button btnCommande;
    public Button btnFacture;
    public Button btnUsers;

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
