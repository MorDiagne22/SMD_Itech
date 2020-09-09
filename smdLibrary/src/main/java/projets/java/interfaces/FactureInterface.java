package projets.java.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projets.java.model.Commande;
import projets.java.model.Facture;

public interface FactureInterface {
    public ObservableList<Facture> LIST_FACTURE = FXCollections.observableArrayList();
}
