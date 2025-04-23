package dao;

import models.Seance;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class SeanceDAO {
	
    private Connection connexion;
    
    public SeanceDAO() {
        connexion = ConnexionBD.getConnexion();
    }
    
    public boolean ajouterSeance(Seance seance) {
        String sql = "INSERT INTO seances (cours_id, date, heure_debut, heure_fin, objectif, contenu, materiel) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            // Vérification et conversion des dates/heures
            if (seance.getDate() == null || seance.getHeureDebut() == null || seance.getHeureFin() == null) {
                throw new IllegalArgumentException("Date ou heure non fournie");
            }
            
            statement.setInt(1, seance.getCoursId());
            statement.setDate(2, Date.valueOf(seance.getDate()));
            statement.setTime(3, Time.valueOf(seance.getHeureDebut().withSecond(0))); // Normalisation des secondes
            statement.setTime(4, Time.valueOf(seance.getHeureFin().withSecond(0)));  // Normalisation des secondes
            statement.setString(5, seance.getObjectif());
            statement.setString(6, seance.getContenu());
            statement.setString(7, seance.getMateriel());
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur SQL d'ajout de séance : " + e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            System.err.println("Format de date/heure invalide : " + e.getMessage());
            return false;
        }
    }
    
    public List<Seance> getSeancesParCours(int coursId) {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT * FROM seances WHERE cours_id = ?";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, coursId);
            
            ResultSet result = statement.executeQuery();
            
            while (result.next()) {
                Seance seance = new Seance();
                seance.setId(result.getInt("id"));
                seance.setCoursId(result.getInt("cours_id"));
                seance.setDate(result.getDate("date").toLocalDate());
                seance.setHeureDebut(result.getTime("heure_debut").toLocalTime());
                seance.setHeureFin(result.getTime("heure_fin").toLocalTime());
                seance.setObjectif(result.getString("objectif"));
                seance.setContenu(result.getString("contenu"));
                seance.setMateriel(result.getString("materiel"));
                seance.setValide(result.getBoolean("valide"));
                seance.setValidePar(result.getInt("valide_par"));
                
                seances.add(seance);
            }
        } catch (SQLException e) {
            System.err.println("Erreur de récupération des séances : " + e.getMessage());
        }
        
        return seances;
    }
    
    public boolean validerSeance(int seanceId, int responsableId) {
        String sql = "UPDATE seances SET valide = TRUE, valide_par = ?, date_validation = NOW() WHERE id = ?";
        
        try (PreparedStatement statement = connexion.prepareStatement(sql)) {
            statement.setInt(1, responsableId);
            statement.setInt(2, seanceId);
            
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Erreur de validation : " + e.getMessage());
            return false;
        }
    }
    
    public List<Seance> getSeancesAValider() {
        List<Seance> seances = new ArrayList<>();
        String sql = "SELECT * FROM seances WHERE valide = FALSE"; // Ou votre condition de validation
        
        try (PreparedStatement stmt = connexion.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Seance seance = new Seance();
                seance.setId(rs.getInt("id"));
                seance.setDate(rs.getDate("date").toLocalDate());
                seance.setHeureDebut(rs.getTime("heure_debut").toLocalTime());
                // ... initialisez tous les autres champs ...
                seances.add(seance);
            }
        } catch (SQLException e) {
            System.err.println("Erreur de récupération des séances à valider: " + e.getMessage());
        }
      
      

        return seances;
    }
   

    // Autres méthodes CRUD pour Seance
}