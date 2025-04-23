package dao;

import models.Utilisateur;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilisateurDAO {
    private Connection connexion;
 // URL de la base de données, utilisateur et mot de passe
    private static final String DB_URL = "jdbc:mysql://localhost:3306/cahierdetexte"; // Remplacez par l'URL de votre base
    private static final String DB_USER = "root"; // Remplacez par votre utilisateur
    private static final String DB_PASSWORD = "votre_mot_de_passe"; // Remplacez par votre mot de passe

    
    public UtilisateurDAO() {
        connexion = ConnexionBD.getConnexion();
    }
    
    public Utilisateur authentifier(String email, String password) {
        Utilisateur utilisateur = null;
        String sql = "SELECT * FROM utilisateurs WHERE email = ? AND password = ?";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setString(1, email);
            statement.setString(2, password);
            
            ResultSet result = statement.executeQuery();
            
            if (result.next()) {
                utilisateur = new Utilisateur(
                    result.getInt("id"),
                    result.getString("nom"),
                    result.getString("prenom"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("role")
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur d'authentification : " + e.getMessage());
        }
        
        return utilisateur;
    }
    
    public List<Utilisateur> getEnseignants() {
        List<Utilisateur> enseignants = new ArrayList<>();
        String sql = "SELECT * FROM utilisateurs WHERE role = 'enseignant'";
        
        try (Statement statement = connexion.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            
            while (result.next()) {
                enseignants.add(new Utilisateur(
                    result.getInt("id"),
                    result.getString("nom"),
                    result.getString("prenom"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getString("role")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erreur de récupération des enseignants : " + e.getMessage());
        }
        
        return enseignants;
    }
    
    public List<Utilisateur> getByRole(String role) {
        List<Utilisateur> users = new ArrayList<>();
        String sql = "SELECT id, nom, prenom, email FROM utilisateurs WHERE role = ?";
        
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, role);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Utilisateur u = new Utilisateur();
                u.setId(rs.getInt("id"));
                u.setNom(rs.getString("nom"));
                u.setPrenom(rs.getString("prenom"));
                u.setEmail(rs.getString("email"));
                users.add(u);
            }
        } catch (SQLException e) {
            System.err.println("Erreur getByRole: " + e.getMessage());
        }
        return users;
    }

    public boolean create(Utilisateur user) {
        String sql = "INSERT INTO utilisateurs (nom, prenom, email, password, role) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setString(1, user.getNom());
            stmt.setString(2, user.getPrenom());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getPassword());
            stmt.setString(5, user.getRole());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur create user: " + e.getMessage());
            return false;
        }
    }

    public boolean delete(int userId) {
        // Exemple de code pour supprimer un utilisateur par son ID dans une base de données
        String query = "DELETE FROM utilisateurs WHERE id = ?";
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, userId);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0; // Si au moins une ligne est affectée, la suppression a réussi
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

   
}