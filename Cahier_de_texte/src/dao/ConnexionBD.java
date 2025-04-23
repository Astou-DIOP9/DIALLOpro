package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/cahiertexte";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    private static Connection connexion = null;
    
    private ConnexionBD() { 
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion établie avec succès");
        } catch (ClassNotFoundException | SQLException e) {
            System.err.println("Erreur de connexion : " + e.getMessage());
        }
    }
    
    public static Connection getConnexion() {
        if (connexion == null) {
            new ConnexionBD();
        }
        return connexion;
    }
    
    public static void closeConnexion() {
        if (connexion != null) {
            try {
                connexion.close();
                System.out.println("Connexion fermée");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture : " + e.getMessage());
            }
        }
    }
}