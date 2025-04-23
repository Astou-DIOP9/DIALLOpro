package dao;

import models.FichePedagogique;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.time.LocalDate;

public class FichePedagogiqueDAO {
	private Connection connexion;

	public FichePedagogiqueDAO() {
		this.connexion = ConnexionBD.getConnexion();
	}

	public boolean create(FichePedagogique fiche) {
		String sql = "INSERT INTO fiche_pedagogique (seance_id, date_generation, chemin_fichier, format) VALUES (?, ?, ?, ?)";

		try (PreparedStatement statement = connexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			statement.setInt(1, fiche.getSeanceId());
			statement.setDate(2, fiche.getDateGeneration());
			statement.setString(3, fiche.getCheminFichier());
			statement.setString(4, fiche.getFormat());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Échec de la création");
			}

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next()) {
					fiche.setId(generatedKeys.getInt(1));
				}
			}
			return true;
		} catch (SQLException e) {
			System.err.println("Erreur SQL: " + e.getMessage());
			return false;
		}
	}

	public List<FichePedagogique> getBySeanceId(int seanceId) {
		List<FichePedagogique> fiches = new ArrayList<>();
		String sql = "SELECT * FROM fiche_pedagogique WHERE seance_id = ?";

		try (PreparedStatement statement = connexion.prepareStatement(sql)) {
			statement.setInt(1, seanceId);

			try (ResultSet result = statement.executeQuery()) {
				while (result.next()) {
					FichePedagogique fiche = new FichePedagogique();
					fiche.setId(result.getInt("id"));
					fiche.setSeanceId(result.getInt("seance_id"));
					fiche.setDateGeneration(result.getDate("date_generation"));
					fiche.setCheminFichier(result.getString("chemin_fichier"));
					fiche.setFormat(result.getString("format"));
					fiches.add(fiche);
				}
			}
		} catch (SQLException e) {
			System.err.println("Erreur SQL: " + e.getMessage());
		}
		return fiches;
	}

	public boolean genererFichePedagogique(int seanceId, String format) {
		// Vérification du format
		if (!"pdf".equals(format) && !"excel".equals(format) && !"word".equals(format)) {
			throw new IllegalArgumentException("Format non valide : " + format);
		}

		// Construction du chemin
		String chemin = "fiches/fiche_seance" + seanceId + "." + format;

		// Création de l'objet fiche
		FichePedagogique fiche = new FichePedagogique(seanceId, Date.valueOf(LocalDate.now()), chemin, format);

		// Appel à la méthode create déjà existante
		return this.create(fiche);
	}

	public List<FichePedagogique> getByCoursId(int id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}