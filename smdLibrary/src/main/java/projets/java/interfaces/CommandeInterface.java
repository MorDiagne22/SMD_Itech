package projets.java.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projets.java.model.Commande;

public interface CommandeInterface {
    public ObservableList<Commande> LIST_COMMANDE = FXCollections.observableArrayList();
}
