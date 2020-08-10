package projets.java.service;

import org.hibernate.Session;
import projets.java.model.Client;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientDao extends UnicastRemoteObject implements IClient {
    private Session session;
    public ClientDao() throws RemoteException{
        session = HibernateUtil.getSession();
    }

    @Override
    public Client getClientByName(String s) throws RemoteException {
        return (Client) session.createQuery("select c from Client c where nomComplet =:nomComplet").setParameter("nomComplet", s).uniqueResult();
    }

    @Override
    public void addClient(Client client) throws RemoteException {

    }

    @Override
    public void updateClient(Client client) throws RemoteException {

    }

    @Override
    public void deleteClient(long l) throws RemoteException {

    }
}
