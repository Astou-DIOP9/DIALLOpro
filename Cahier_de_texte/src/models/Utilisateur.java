package models;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Utilisateur {
    private int id;
    private StringProperty nom;
    private StringProperty prenom;
    private StringProperty email;
    private StringProperty password;
    private StringProperty role;

    // Constructeur
    public Utilisateur() {
        this.nom = new SimpleStringProperty();
        this.prenom = new SimpleStringProperty();
        this.email = new SimpleStringProperty();
        this.password = new SimpleStringProperty();
        this.role = new SimpleStringProperty();
    }
    
    public Utilisateur(int id, String nom, String prenom, String email, String password, String role) {
        this.id = id;
        this.nom = new SimpleStringProperty(nom);
        this.prenom = new SimpleStringProperty(prenom);
        this.email = new SimpleStringProperty(email);
        this.password = new SimpleStringProperty(password);
        this.role = new SimpleStringProperty(role);
    }

    // Getters et Setters pour les propriétés
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom.get(); }
    public void setNom(String nom) { this.nom.set(nom); }
    @SuppressWarnings("exports")
	public StringProperty nomProperty() { return nom; }

    public String getPrenom() { return prenom.get(); }
    public void setPrenom(String prenom) { this.prenom.set(prenom); }
    @SuppressWarnings("exports")
	public StringProperty prenomProperty() { return prenom; }

    public String getEmail() { return email.get(); }
    public void setEmail(String email) { this.email.set(email); }
    @SuppressWarnings("exports")
	public StringProperty emailProperty() { return email; }

    public String getPassword() { return password.get(); }
    public void setPassword(String password) { this.password.set(password); }
    @SuppressWarnings("exports")
	public StringProperty passwordProperty() { return password; }

    public String getRole() { return role.get(); }
    public void setRole(String role) { this.role.set(role); }
    @SuppressWarnings("exports")
	public StringProperty roleProperty() { return role; }

    @Override
    public String toString() {
        return prenom.get() +" "+nom.get();
    }
}
