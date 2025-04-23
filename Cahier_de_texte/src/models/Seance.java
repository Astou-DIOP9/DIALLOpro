package models;

import java.time.LocalDate;
import java.time.LocalTime;

public class Seance {
    private int id;
    private int coursId;
    private LocalDate date;
    private LocalTime heureDebut;
    private LocalTime heureFin;
    private String objectif;
    private String contenu;
    private String materiel;
    private boolean valide;
    private Integer validePar;
    private int createdBy; // Ajout du champ pour l'utilisateur créateur
    
    public Seance() {}
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int getCoursId() { return coursId; }
    public void setCoursId(int coursId) { this.coursId = coursId; }
    
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public LocalTime getHeureDebut() { return heureDebut; }
    public void setHeureDebut(LocalTime heureDebut) { this.heureDebut = heureDebut; }
    
    public LocalTime getHeureFin() { return heureFin; }
    public void setHeureFin(LocalTime heureFin) { this.heureFin = heureFin; }
    
    public String getObjectif() { return objectif; }
    public void setObjectif(String objectif) { this.objectif = objectif; }
    
    public String getContenu() { return contenu; }
    public void setContenu(String contenu) { this.contenu = contenu; }
    
    public String getMateriel() { return materiel; }
    public void setMateriel(String materiel) { this.materiel = materiel; }
    
    public boolean isValide() { return valide; }
    public void setValide(boolean valide) { this.valide = valide; }
    
    public Integer getValidePar() { return validePar; }
    public void setValidePar(Integer validePar) { this.validePar = validePar; }
    
    // Méthodes pour createdBy
    public int getCreatedBy() {
        return createdBy;
    }
    
    public void setCreatedBy(int createdBy) {
        this.createdBy = createdBy;
    }
    
    @Override
    public String toString() {
        return "Séance #" + id + " - " + date + " de " + heureDebut + " à " + heureFin +
               " | Objectif : " + objectif;
    }



}