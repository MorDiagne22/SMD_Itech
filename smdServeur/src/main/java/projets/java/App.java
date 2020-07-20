package projets.java;

import projets.java.service.smdDao;
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

            //IPersonne iPersonne = new PersonneDao();
            //registry.bind("smdRemote", iPersonne);
            smdDao smd = new smdDao();
            smd.hello();


            System.out.println("Serveur lance sur le port 5003");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
}
