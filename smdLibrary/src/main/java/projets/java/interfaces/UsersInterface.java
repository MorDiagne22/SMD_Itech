package projets.java.interfaces;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import projets.java.model.User;

public interface UsersInterface {

    public ObservableList<User> LIST_USERS = FXCollections.observableArrayList();
}
