package projets.java.service;

import org.hibernate.Session;
import projets.java.model.Client;
import projets.java.model.TypeClient;
import projets.java.utils.HibernateUtil;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class TypeClientDao extends UnicastRemoteObject implements ITypeClient {

    private Session session;

    public TypeClientDao() throws RemoteException{
        session = HibernateUtil.getSession();
    }

    @Override
    public List<TypeClient> getAllTypeClient() throws RemoteException {
        return session.createQuery("select c from TypeClient c", TypeClient.class).list();
    }
}
