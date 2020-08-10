package smd.utils;

import projets.java.service.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Fabrique {
    private static ICategorie iCategorie;
    private static IProduit iProduit;
    private static IMarque iMarque;
    private static IUser iUser;
    private static IClient iClient;
    private static ITypeClient iTypeClient;

    private static void init() throws Exception{
        try {
            Registry registry = LocateRegistry.getRegistry(5003);
            iCategorie = (ICategorie) registry.lookup("categorieRemote");
            iProduit = (IProduit) registry.lookup("produitRemote");
            iMarque = (IMarque) registry.lookup("marqueRemote");
            iUser = (IUser) registry.lookup("userRemote");
            iClient = (IClient) registry.lookup("clientRemote");
            iTypeClient = (ITypeClient) registry.lookup("typeclientRemote");
        }
        catch(Exception e){
            throw e;
        }
    }


    public static ICategorie getiCategorie() throws  Exception{
        try {
            if(iCategorie == null) {
                init();
            }
            return iCategorie;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IProduit getiProduit() throws  Exception{
        try {
            if(iProduit == null) {
                init();
            }
            return iProduit;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IMarque getiMarque() throws  Exception{
        try {
            if(iMarque == null) {
                init();
            }
            return iMarque;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IUser getiUser() throws  Exception{
        try {
            if(iUser == null) {
                init();
            }
            return iUser;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static IClient getiClient() throws  Exception{
        try {
            if(iClient == null) {
                init();
            }
            return iClient;
        }
        catch(Exception e){
            throw e;
        }

    }

    public static ITypeClient getiTypeClient() throws  Exception{
        try {
            if(iTypeClient == null) {
                init();
            }
            return iTypeClient;
        }
        catch(Exception e){
            throw e;
        }

    }


}
