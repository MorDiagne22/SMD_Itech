package projets.java.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
public class TypeClient implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(length = 200)
    private String nom;

    @OneToMany(mappedBy = "typeClient", cascade = CascadeType.ALL)
    private List<Client> clients;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public String toString() {
        return nom;
    }
}
