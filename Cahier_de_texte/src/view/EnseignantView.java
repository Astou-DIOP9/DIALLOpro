package view;

import models.Utilisateur;
import models.Cours;
import models.Seance;
import dao.CoursDAO;
import dao.SeanceDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SuppressWarnings("serial")
public class EnseignantView extends JFrame {
    private Utilisateur utilisateur;
    private CoursDAO coursDAO;
    private SeanceDAO seanceDAO;
    
    public EnseignantView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        coursDAO = new CoursDAO();
        seanceDAO = new SeanceDAO();
        initUI();
    }
    
    private void initUI() {
        setTitle("Espace Enseignant - " + utilisateur.getNom());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Onglet Mes cours
        JPanel coursPanel = new JPanel(new BorderLayout());
        List<Cours> mesCours = coursDAO.getCoursByEnseignant(utilisateur.getId());
        DefaultListModel<Cours> coursModel = new DefaultListModel<>();
        for (Cours cours : mesCours) {
            coursModel.addElement(cours);
        }
        JList<Cours> coursList = new JList<>(coursModel);
        coursPanel.add(new JScrollPane(coursList), BorderLayout.CENTER);
        
        tabbedPane.addTab("Mes cours", coursPanel);
        
        // Onglet Ajouter une séance
        JPanel ajoutSeancePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cours
        gbc.gridx = 0;
        gbc.gridy = 0;
        ajoutSeancePanel.add(new JLabel("Cours:"), gbc);
        
        gbc.gridx = 1;
        JComboBox<Cours> coursCombo = new JComboBox<>(mesCours.toArray(new Cours[0]));
        ajoutSeancePanel.add(coursCombo, gbc);
        
        // Date
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Date:"), gbc);
        
        gbc.gridx = 1;
        JTextField dateField = new JTextField(LocalDate.now().toString(), 15);
        ajoutSeancePanel.add(dateField, gbc);
        
        // Heure début
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Heure début:"), gbc);
        
        gbc.gridx = 1;
        JTextField heureDebutField = new JTextField("08:00", 5);
        ajoutSeancePanel.add(heureDebutField, gbc);
        
        // Heure fin
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Heure fin:"), gbc);
        
        gbc.gridx = 1;
        JTextField heureFinField = new JTextField("10:00", 5);
        ajoutSeancePanel.add(heureFinField, gbc);
        
        // Objectif
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Objectif:"), gbc);
        
        gbc.gridx = 1;
        JTextArea objectifArea = new JTextArea(3, 20);
        ajoutSeancePanel.add(new JScrollPane(objectifArea), gbc);
        
        // Contenu
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Contenu:"), gbc);
        
        gbc.gridx = 1;
        JTextArea contenuArea = new JTextArea(5, 20);
        ajoutSeancePanel.add(new JScrollPane(contenuArea), gbc);
        
        // Matériel
        gbc.gridx = 0;
        gbc.gridy++;
        ajoutSeancePanel.add(new JLabel("Matériel:"), gbc);
        
        gbc.gridx = 1;
        JTextArea materielArea = new JTextArea(2, 20);
        ajoutSeancePanel.add(new JScrollPane(materielArea), gbc);
        
        // Bouton d'ajout
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton ajouterButton = new JButton("Ajouter la séance");
        ajouterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Seance seance = new Seance();
                    seance.setCoursId(((Cours)coursCombo.getSelectedItem()).getId());
                    seance.setDate(LocalDate.parse(dateField.getText()));
                    seance.setHeureDebut(LocalTime.parse(heureDebutField.getText()));
                    seance.setHeureFin(LocalTime.parse(heureFinField.getText()));
                    seance.setObjectif(objectifArea.getText());
                    seance.setContenu(contenuArea.getText());
                    seance.setMateriel(materielArea.getText());
                    
                    if (seanceDAO.ajouterSeance(seance)) {
                        JOptionPane.showMessageDialog(EnseignantView.this, 
                            "Séance ajoutée avec succès", 
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                        // Réinitialiser les champs
                        objectifArea.setText("");
                        contenuArea.setText("");
                        materielArea.setText("");
                    } else {
                        JOptionPane.showMessageDialog(EnseignantView.this, 
                            "Erreur lors de l'ajout de la séance", 
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(EnseignantView.this, 
                        "Format de date ou heure invalide", 
                        "Erreur de format", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        ajoutSeancePanel.add(ajouterButton, gbc);
        
        tabbedPane.addTab("Ajouter une séance", ajoutSeancePanel);
        
        add(tabbedPane);
    }
}