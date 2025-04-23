package view;

import models.Utilisateur;
import models.Seance;
import dao.SeanceDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.stream.Collectors;

public class ResponsableView extends JFrame {
    private Utilisateur utilisateur;
    private SeanceDAO seanceDAO;
    private DefaultListModel<Seance> seancesModel;
    private JList<Seance> seancesList;
    private JTextField searchField;

    public ResponsableView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        seanceDAO = new SeanceDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Espace Responsable de Classe - " + utilisateur.getNom());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Champ de recherche
        searchField = new JTextField();
        searchField.setToolTipText("Rechercher une séance par mot-clé...");
        searchField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void removeUpdate(javax.swing.event.DocumentEvent e) { filter(); }
            public void changedUpdate(javax.swing.event.DocumentEvent e) { filter(); }
        });

        // Liste des séances
        seancesModel = new DefaultListModel<>();
        seancesList = new JList<>(seancesModel);
        seancesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        seancesList.setFont(new Font("Monospaced", Font.PLAIN, 12));
        actualiserListe();

        // Bouton de validation
        JButton validerButton = new JButton("Valider la séance sélectionnée");
        validerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Seance selectedSeance = seancesList.getSelectedValue();
                if (selectedSeance == null) {
                    JOptionPane.showMessageDialog(ResponsableView.this, 
                        "Veuillez sélectionner une séance à valider", 
                        "Aucune séance sélectionnée", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int confirm = JOptionPane.showConfirmDialog(
                    ResponsableView.this,
                    "Confirmer la validation de cette séance ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION
                );
                if (confirm == JOptionPane.YES_OPTION) {
                    if (seanceDAO.validerSeance(selectedSeance.getId(), utilisateur.getId())) {
                        JOptionPane.showMessageDialog(ResponsableView.this, 
                            "Séance validée avec succès", 
                            "Succès", JOptionPane.INFORMATION_MESSAGE);
                        seancesModel.removeElement(selectedSeance);
                    } else {
                        JOptionPane.showMessageDialog(ResponsableView.this, 
                            "Erreur lors de la validation", 
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        // Bouton actualiser
        JButton actualiserButton = new JButton("Actualiser");
        actualiserButton.addActionListener(e -> actualiserListe());

        // Panel supérieur
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(new JLabel("Recherche : "), BorderLayout.WEST);
        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(actualiserButton, BorderLayout.EAST);

        // Panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(seancesList), BorderLayout.CENTER);
        mainPanel.add(validerButton, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void actualiserListe() {
        seancesModel.clear();
        List<Seance> seances = seanceDAO.getSeancesAValider();
        for (Seance seance : seances) {
            seancesModel.addElement(seance);
        }
    }

    private void filter() {
        String keyword = searchField.getText().toLowerCase();
        List<Seance> allSeances = seanceDAO.getSeancesAValider();
        seancesModel.clear();
        for (Seance s : allSeances) {
            String fullText = s.toString().toLowerCase();
            if (fullText.contains(keyword)) {
                seancesModel.addElement(s);
            }
        }
    }
}
