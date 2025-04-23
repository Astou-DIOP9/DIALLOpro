
package dao;

import models.Cours;
import models.Seance;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CoursDAO {
    private Connection connexion;
    
    public CoursDAO() {
        connexion = ConnexionBD.getConnexion();
    }
    
    public List<Cours> getAllCours() {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT * FROM cours";
        
        try (Statement statement = connexion.createStatement();
             ResultSet result = statement.executeQuery(sql)) {
            
            while (result.next()) {
                Cours cours = new Cours();
                cours.setId(result.getInt("id"));
                cours.setCode(result.getString("code"));
                cours.setNom(result.getString("nom"));
                cours.setDescription(result.getString("description"));
                cours.setCredit(result.getInt("credit"));
                
                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours: " + e.getMessage());
        }
        
        return coursList;
    }
    public List<Cours> getCoursByEnseignant(int enseignantId) {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT c.* FROM cours c " +
                     "JOIN enseignant_cours ec ON c.id = ec.cours_id " +
                     "WHERE ec.enseignant_id = ?";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, enseignantId);
            
            ResultSet result = statement.executeQuery();
            
            while (result.next()) {
                Cours cours = new Cours();
                cours.setId(result.getInt("id"));
                cours.setCode(result.getString("code"));
                cours.setNom(result.getString("nom"));
                cours.setDescription(result.getString("description"));
                cours.setCredit(result.getInt("credit"));
                
                coursList.add(cours);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des cours par enseignant: " + e.getMessage());
        }
        
        return coursList;
    }
    public boolean ajouterSeance(Seance seance) {
        String sql = "INSERT INTO seances (cours_id, date, heure_debut, heure_fin, objectif, contenu, materiel, created_by) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, seance.getCoursId());
            statement.setDate(2, Date.valueOf(seance.getDate()));
            statement.setTime(3, Time.valueOf(seance.getHeureDebut()));
            statement.setTime(4, Time.valueOf(seance.getHeureFin()));
            statement.setString(5, seance.getObjectif());
            statement.setString(6, seance.getContenu());
            statement.setString(7, seance.getMateriel());
            statement.setInt(8, seance.getCreatedBy()); // Ajout de l'ID de l'utilisateur qui crée
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur d'ajout de séance : " + e.getMessage());
            return false;
        }
    }
    public boolean create(Cours cours) {
        String query = "INSERT INTO cours (code, nom, description, credit) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connexion.prepareStatement(query)) {
            statement.setString(1, cours.getCode());
            statement.setString(2, cours.getNom());
            statement.setString(3, cours.getDescription());
            statement.setInt(4, cours.getCredit());
            
            int rowsInserted = statement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Cours> getAll() {
        List<Cours> coursList = new ArrayList<>();
        String sql = "SELECT id, code, nom FROM cours";
        try (Statement stmt = connexion.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                Cours c = new Cours();
                c.setId(rs.getInt("id"));
                c.setCode(rs.getString("code"));
                c.setNom(rs.getString("nom"));
                coursList.add(c);
            }
        } catch (SQLException e) {
            System.err.println("Erreur getAll cours: " + e.getMessage());
        }
        return coursList;
    }

    public boolean assignerCours(int enseignantId, int coursId) {
        String sql = "INSERT INTO enseignant_cours (enseignant_id, cours_id, date_assignation) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = connexion.prepareStatement(sql)) {
            stmt.setInt(1, enseignantId);
            stmt.setInt(2, coursId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur assignation cours : " + e.getMessage());
            return false;
        }
    }


    public boolean delete(int id) {
        String sql = "DELETE FROM cours WHERE id = ?";
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du cours : " + e.getMessage());
            return false;
        }
    }

    
}