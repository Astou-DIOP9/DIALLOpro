package models;

import java.sql.Date;
import java.util.Objects;

public class FichePedagogique {
    private int id;
    private int seance_id;
    private Date dateGeneration;
    private String cheminFichier;
    private String format;

    public FichePedagogique() {}

    public FichePedagogique(int seance_id, Date dateGeneration, String cheminFichier, String format) {
        this.seance_id = seance_id;
        this.dateGeneration = dateGeneration;
        this.cheminFichier = cheminFichier;
        this.format = format;
    }

    // Getters
    public int getId() { return id; }
    public int getSeanceId() { return seance_id; }
    public Date getDateGeneration() { return dateGeneration; }
    public String getCheminFichier() { return cheminFichier; }
    public String getFormat() { return format; }

    // Setters
    public void setId(int id) { this.id = id; }
    public void setSeanceId(int seance_id) { this.seance_id = seance_id; }
    public void setDateGeneration(Date dateGeneration) { this.dateGeneration = dateGeneration; }
    public void setCheminFichier(String cheminFichier) { this.cheminFichier = cheminFichier; }
    public void setFormat(String format) { this.format = format; }

    @Override
    public String toString() {
        return "FichePedagogique{" +
               "id=" + id +
               ", seance_id=" + seance_id +
               ", dateGeneration=" + dateGeneration +
               ", format='" + format + '\'' +
               '}';
    }
}