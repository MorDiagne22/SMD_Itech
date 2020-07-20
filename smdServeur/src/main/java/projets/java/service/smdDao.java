package projets.java.service;

import projets.java.service.ISmd;
import projets.java.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class smdDao extends UnicastRemoteObject implements ISmd {
    private Session session;
    public smdDao() throws RemoteException {
        session = HibernateUtil.getSession();
    }
    @Override
    public void hello() throws RemoteException {
        System.out.println("BOnjour Serigne Mor");
    }
}
