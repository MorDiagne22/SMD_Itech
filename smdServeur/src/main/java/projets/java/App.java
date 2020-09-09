package projets.java;

import projets.java.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        try {
            System.setSecurityManager(new SecurityManager());
            Registry registry = LocateRegistry.createRegistry(5003);

            ICategorie iCategorie = new CategorieDao();
            registry.bind("categorieRemote", iCategorie);

            IProduit iProduit = new ProduitDao();
            registry.bind("produitRemote", iProduit);

            IMarque iMarque = new MarqueDao();
            registry.bind("marqueRemote", iMarque);

            IUser iUser = new UserDao();
            registry.bind("userRemote", iUser);

            IClient iClient = new ClientDao();
            registry.bind("clientRemote", iClient);

            ITypeClient iTypeClient = new TypeClientDao();
            registry.bind("typeclientRemote", iTypeClient);

            ICommande iCommande = new CommandeDao();
            registry.bind("commandeRemote", iCommande);

            IProduit_Commande iProduit_commande = new Produit_CommandeDao();
            registry.bind("produitcommandeRemote", iProduit_commande);

            IEtat iEtat = new EtatDao();
            registry.bind("etatRemote", iEtat);

            IFacture iFacture = new FactureDao();
            registry.bind("factureRemote", iFacture);

            System.out.println("Serveur lance sur le port 5003");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
