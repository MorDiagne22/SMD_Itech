package projets.java.service;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ISmd extends Remote {
    public void hello() throws RemoteException;
}
