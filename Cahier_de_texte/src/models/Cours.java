package models;

public class Cours {
    private int id;
    private String code;
    private String nom;
    private String description;
    private int credit;
    
    public Cours() {}
    
    public Cours(int id, String code, String nom, String description, int credit) {
        this.id = id;
        this.code = code;
        this.nom = nom;
        this.description = description;
        this.credit = credit;
    }
    
    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public int getCredit() { return credit; }
    public void setCredit(int credit) { this.credit = credit; }

	@Override
	public String toString() {
		return  id +": "+  code + "  "+  nom ;
	}
}