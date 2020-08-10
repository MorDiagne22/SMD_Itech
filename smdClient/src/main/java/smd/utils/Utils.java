package smd.utils;


import javafx.scene.control.Alert;



public class Utils {
/*
    private static IMedecin iMedecin;
    private static IService iService;
    private static ISpecialite iSpecialite;

    public static IMedecin getiMedecin() {
        if(iMedecin == null)
            init();
        return iMedecin;
    }
*/
    /*
    public static void init() {
        try {
            System.setSecurityManager(new SecurityManager());
            Registry registry = LocateRegistry.getRegistry("localhost", 5001);
            iMedecin = (IMedecin) registry.lookup("medecinRemote");
            iService = (IService) registry.lookup("serviceRemote");
            iSpecialite = (ISpecialite) registry.lookup("specialiteRemote");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<SpecialiteModel> convertToUSpecialiteModel(List<Specialite> lusers) {
        try {
            return lusers.stream().map(x -> new SpecialiteModel(x)).collect(Collectors.toList());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }*/


    public static void showMessage(String title, String header, String content) {
        Alert a = new Alert(Alert.AlertType.INFORMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        a.showAndWait();
    }
    /*
    public static boolean confirmMessage(String title, String header, String content) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setTitle(title);
        a.setHeaderText(header);
        a.setContentText(content);
        Optional<ButtonType> result = a.showAndWait();
        return (result.get() == ButtonType.OK);
    }*/
}
