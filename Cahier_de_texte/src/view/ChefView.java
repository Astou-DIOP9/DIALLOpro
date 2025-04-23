package view;
import javax.swing.*;
import java.awt.*;
import models.Utilisateur;
import models.Cours;
import models.FichePedagogique;
import dao.UtilisateurDAO;
import dao.CoursDAO;
import dao.FichePedagogiqueDAO;
import java.util.List;

@SuppressWarnings("serial")
public class ChefView extends JFrame {
    private Utilisateur utilisateur;
    private UtilisateurDAO utilisateurDAO;
    private CoursDAO coursDAO;
    private FichePedagogiqueDAO fichePedagogiqueDAO;
    
    public ChefView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        this.utilisateurDAO = new UtilisateurDAO();
        this.coursDAO = new CoursDAO();
        this.fichePedagogiqueDAO = new FichePedagogiqueDAO();
        initUI();
    }
    
    private void initUI() {
        setTitle("Espace Chef de Département - " + utilisateur.getNom());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
          JTabbedPane tabbedPane = new JTabbedPane();
//        try {
//            UIManager.setLookAndFeel(new FlatLightLaf()); // Thème moderne
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        JTabbedPane tabbedPane1 = new JTabbedPane();
        tabbedPane1.setFont(new Font("Arial", Font.BOLD, 14));
        //  Ajout Onglet 
        JPanel createEnseignantsPanel= new JPanel();
        createEnseignantsPanel.setBackground(Color.black);
        JButton addButton = new JButton("Ajouter", UIManager.getIcon("FileView.fileIcon"));
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        tabbedPane.addTab("Enseignants", createEnseignantsPanel());
         // Onglet 2: Gestion des responsables
          tabbedPane.addTab("Responsables", createResponsablesPanel());        
        // Onglet 3: Assignation des cours
        tabbedPane.addTab("Assignation Cours", createAssignationPanel());
        // Onglet 4: Fiches pédagogiques
        tabbedPane.addTab("Fiches Pédagogiques", createFichesPedagogiquesPanel());        
        add(tabbedPane);
        // onglet 5 : ajout des cours
        tabbedPane.addTab("Ajouter Cours", createGestionCoursPanel());
    }
    private JPanel createEnseignantsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        // Liste des enseignants
        List<Utilisateur> enseignants = utilisateurDAO.getByRole("enseignant");
        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        enseignants.forEach(model::addElement);
        JList<Utilisateur> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(76, 175, 80)); // Vert pour indiquer l'action positive
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(e -> showAddUserDialog(model, "enseignant", "Nouvel enseignant"));
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(255, 87, 34)); // Orange pour supprimer
        deleteButton.setForeground(Color.WHITE);
         deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> {
            Utilisateur selected = list.getSelectedValue();
            if (selected != null) {
                deleteUser(selected, model);
            }
        });
        
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
	private JPanel createResponsablesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des responsables
        List<Utilisateur> responsables = utilisateurDAO.getByRole("responsable");
        DefaultListModel<Utilisateur> model = new DefaultListModel<>();
        responsables.forEach(model::addElement);
        
        JList<Utilisateur> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Ajouter");
        addButton.setBackground(new Color(76, 175, 80)); // Vert pour indiquer l'action positive
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        //ajouter un button supprimer un responsable
        JButton deleteButton = new JButton("Supprimer");
        deleteButton.setBackground(new Color(255, 87, 34)); // Orange pour la supprimer
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
       deleteButton.addActionListener(e -> {
           Utilisateur selected = list.getSelectedValue();
           if (selected != null) {
               deleteUser(selected, model);
           }
       });
        addButton.addActionListener(e -> showAddUserDialog(model, "responsable", "Nouveau responsable"));
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    
    @SuppressWarnings("unused")
	private JPanel createAssignationPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des enseignants
        List<Utilisateur> enseignants = utilisateurDAO.getByRole("enseignant");
        JComboBox<Utilisateur> enseignantCombo = new JComboBox<>(enseignants.toArray(new Utilisateur[0]));
        enseignantCombo.setToolTipText("Sélectionnez un enseignant");
        
        // Liste des cours
        List<Cours> cours = coursDAO.getAll();
        JComboBox<Cours> coursCombo = new JComboBox<>(cours.toArray(new Cours[0]));
        coursCombo.setToolTipText("Sélectionnez un cours");
        
        // Panel de formulaire avec GridBagLayout pour un meilleur alignement
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espacement entre les composants
        
        // Label et ComboBox pour l'enseignant
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Enseignant:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(enseignantCombo, gbc);
        
        // Label et ComboBox pour le cours
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Cours à assigner:"), gbc);
        
        gbc.gridx = 1;
        formPanel.add(coursCombo, gbc);
        
        // Ajouter un bouton de réinitialisation
        JButton resetButton = new JButton("Réinitialiser");
        resetButton.setBackground(new Color(255, 87, 34)); // Orange pour la réinitialisation
        resetButton.setForeground(Color.WHITE);
        resetButton.setFont(new Font("Arial", Font.BOLD, 14));
        resetButton.addActionListener(e -> {
            enseignantCombo.setSelectedIndex(-1);
            coursCombo.setSelectedIndex(-1);
        });
        
        // Bouton d'assignation
        JButton assignButton = new JButton("Assigner");
        assignButton.setBackground(new Color(76, 175, 80)); // Vert pour l'action positive
        assignButton.setForeground(Color.WHITE);
        assignButton.setFont(new Font("Arial", Font.BOLD, 14));
        assignButton.addActionListener(e -> {
            Utilisateur enseignant = (Utilisateur) enseignantCombo.getSelectedItem();
            Cours coursSelectionne = (Cours) coursCombo.getSelectedItem();
            
            if (enseignant == null || coursSelectionne == null) {
                JOptionPane.showMessageDialog(this, 
                    "Veuillez sélectionner un enseignant et un cours", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (coursDAO.assignerCours(enseignant.getId(), coursSelectionne.getId())) {
                JOptionPane.showMessageDialog(this, 
                    "Cours assigné avec succès à " + enseignant.getNom());
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'assignation", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        // Panel de boutons en bas
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(assignButton);
        buttonPanel.add(resetButton);
        
        // Ajouter tout le contenu au panel principal
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    @SuppressWarnings("unused")
	private JPanel createFichesPedagogiquesPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Liste des cours pour génération de fiches
        List<Cours> cours = coursDAO.getAll();
        DefaultListModel<Cours> model = new DefaultListModel<>();
        cours.forEach(model::addElement);
        
        JList<Cours> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);
        
        // Boutons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        JButton generateButton = new JButton("Générer fiche");
        generateButton.setBackground(new Color(76, 175, 80)); // Vert pour indiquer l'action positive
        generateButton.setForeground(Color.WHITE);
        generateButton.setFont(new Font("Arial", Font.BOLD, 14));
        generateButton.addActionListener(e -> {
            Cours selected = list.getSelectedValue();
            if (selected != null) {
                generateFichePedagogique(selected);
            }
        });
        buttonPanel.add(generateButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }
    private JPanel createGestionCoursPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        // Liste des cours
        List<Cours> cours = coursDAO.getAll();
        DefaultListModel<Cours> model = new DefaultListModel<>();
        cours.forEach(model::addElement);
        JList<Cours> list = new JList<>(model);
        panel.add(new JScrollPane(list), BorderLayout.CENTER);

        // Boutons en bas
        JPanel buttonPanel = new JPanel();

        // Bouton Ajouter
        JButton addButton = new JButton("Ajouter un cours");
        addButton.setBackground(new Color(76, 175, 80));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(e -> showAddCoursDialog(model));
        buttonPanel.add(addButton);

        // === Nouveau bouton Supprimer ===
        JButton deleteButton = new JButton("Supprimer le cours");
        deleteButton.setBackground(new Color(255, 87, 34));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(e -> {
            Cours selected = list.getSelectedValue();
            if (selected != null) {
                int confirm = JOptionPane.showConfirmDialog(panel,
                    "Voulez-vous vraiment supprimer le cours : " + selected.getNom() + " ?",
                    "Confirmation de suppression",
                    JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    if (coursDAO.delete(selected.getId())) {
                        model.removeElement(selected);
                        JOptionPane.showMessageDialog(panel,
                            "Cours supprimé avec succès.",
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(panel,
                            "Erreur lors de la suppression du cours.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(panel,
                    "Veuillez sélectionner un cours à supprimer.",
                    "Avertissement", JOptionPane.WARNING_MESSAGE);
            }
        });
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }
 
    // Méthodes utilitaires
    private void showAddUserDialog(DefaultListModel<Utilisateur> model, String role, String title) {
        JTextField nomField = new JTextField();
        JTextField prenomField = new JTextField();
        JTextField emailField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Nom:"));
        panel.add(nomField);
        panel.add(new JLabel("Prénom:"));
        panel.add(prenomField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);
        panel.add(new JLabel("Mot de passe:"));
        panel.add(passwordField);
        int result = JOptionPane.showConfirmDialog(this, panel, title, 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            if (nomField.getText().isEmpty() || prenomField.getText().isEmpty() || 
                emailField.getText().isEmpty() || passwordField.getPassword().length == 0) {
                JOptionPane.showMessageDialog(this, 
                    "Tous les champs doivent être remplis", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Utilisateur nouvelUtilisateur = new Utilisateur();
            nouvelUtilisateur.setNom(nomField.getText());
            nouvelUtilisateur.setPrenom(prenomField.getText());
            nouvelUtilisateur.setEmail(emailField.getText());
            nouvelUtilisateur.setPassword(new String(passwordField.getPassword()));
            nouvelUtilisateur.setRole(role);
            
            if (utilisateurDAO.create(nouvelUtilisateur)) {
                model.addElement(nouvelUtilisateur);
                JOptionPane.showMessageDialog(this, 
                    role.equals("enseignant") ? "Enseignant ajouté" : "Responsable ajouté");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de l'ajout", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    

    private void showAddCoursDialog(DefaultListModel<Cours> model) {
        JTextField codeField = new JTextField(15);
        JTextField nomField = new JTextField(15);
        JTextField creditField = new JTextField(5);
        JTextArea descriptionArea = new JTextArea(4, 20);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        JScrollPane descriptionScroll = new JScrollPane(descriptionArea);
        descriptionScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // marge autour des composants
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Code du Cours:"), gbc);
        gbc.gridx = 1;
        panel.add(codeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Nom du Cours:"), gbc);
        gbc.gridx = 1;
        panel.add(nomField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Crédit:"), gbc);
        gbc.gridx = 1;
        panel.add(creditField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        panel.add(descriptionScroll, gbc);

        int result = JOptionPane.showConfirmDialog(this, panel, "Ajouter un Nouveau Cours",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            String code = codeField.getText().trim();
            String nom = nomField.getText().trim();
            String creditText = creditField.getText().trim();
            String description = descriptionArea.getText().trim();

            if (code.isEmpty() || nom.isEmpty() || creditText.isEmpty() || description.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                        "Tous les champs doivent être remplis.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int credit;
            try {
                credit = Integer.parseInt(creditText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "Le crédit doit être un nombre entier.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Cours nouveauCours = new Cours();
            nouveauCours.setCode(code);
            nouveauCours.setNom(nom);
            nouveauCours.setCredit(credit);
            nouveauCours.setDescription(description);

            if (coursDAO.create(nouveauCours)) {
                model.addElement(nouveauCours);
                JOptionPane.showMessageDialog(this,
                        "Cours ajouté avec succès.",
                        "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Erreur lors de l'ajout du cours.",
                        "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteUser(Utilisateur user, DefaultListModel<Utilisateur> model) {
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Voulez-vous vraiment supprimer " + user.getNom() + "?", 
            "Confirmation", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (utilisateurDAO.delete(user.getId())) {
                model.removeElement(user);
                JOptionPane.showMessageDialog(this, "Utilisateur supprimé");
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Erreur lors de la suppression", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void generateFichePedagogique(Cours selected) {
	    JPanel fichesPanel = (JPanel) ((JTabbedPane) getContentPane().getComponent(0)).getComponentAt(3);
	    JScrollPane scrollPane = (JScrollPane) fichesPanel.getComponent(0);
	    JList<?> list = (JList<?>) scrollPane.getViewport().getView();

	    Object selectedValue = list.getSelectedValue();

	    if (selectedValue instanceof Cours selectedCours) {
	        // Demander le format de la fiche
	        String[] options = {"PDF", "Excel", "Word"};
	        int choice = JOptionPane.showOptionDialog(this, 
	            "Choisissez le format de la fiche", 
	            "Format de sortie",
	            JOptionPane.DEFAULT_OPTION, 
	            JOptionPane.QUESTION_MESSAGE,
	            null, 
	            options, 
	            options[0]);

	        if (choice == -1) return; // Annulation

	        String format = options[choice].toLowerCase();
	        
	        // Générer la fiche
	        if (fichePedagogiqueDAO.genererFichePedagogique(
	            selectedCours.getId(), // Ici vous devriez utiliser l'ID de séance, pas de cours!
	            format)) {
	            
	            JOptionPane.showMessageDialog(this, 
	                "Fiche générée avec succès au format " + format, 
	                "Succès",
	                JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            JOptionPane.showMessageDialog(this, 
	                "Erreur lors de la génération", 
	                "Erreur", 
	                JOptionPane.ERROR_MESSAGE);
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, 
	            "Veuillez sélectionner un cours", 
	            "Erreur", 
	            JOptionPane.ERROR_MESSAGE);
	    }
	}
    }