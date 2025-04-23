package view;

import javax.swing.*;
import models.Utilisateur;
import dao.UtilisateurDAO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AuthView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JCheckBox showPasswordCheckBox; //  Ajout d'une case à cocher pour voir le mot de passe
    private UtilisateurDAO utilisateurDAO;

    public AuthView() {
        utilisateurDAO = new UtilisateurDAO();
        initUI();
    }

    private void initUI() {
        setTitle("Authentification - Cahier de Texte");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        //  En-tête
        JPanel header = new JPanel();
        header.setBackground(new Color(50, 50, 50));
        JLabel titre = new JLabel("Gestion de Cahier de Texte");
        titre.setForeground(Color.WHITE);
        titre.setFont(new Font("Arial", Font.BOLD, 16));
        header.add(titre);
        add(header, BorderLayout.NORTH);

        //  Formulaire de connexion
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(77, 108, 146));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(new JLabel("Email:"), gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Mot de passe
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Mot de passe:"), gbc);

        gbc.gridx = 1;
        passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);

        //  Case à cocher pour afficher le mot de passe
        gbc.gridy++;
        showPasswordCheckBox = new JCheckBox("Afficher le mot de passe");
        showPasswordCheckBox.addActionListener(e -> {
            if (showPasswordCheckBox.isSelected()) {
                passwordField.setEchoChar((char) 0); // Affiche le texte en clair
            } else {
                passwordField.setEchoChar('●'); // Masque le texte
            }
        });
        panel.add(showPasswordCheckBox, gbc);

        //  Bouton de connexion
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton loginButton = new JButton("Se connecter");
        loginButton.setBackground(new Color(76, 175, 80));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText().trim();
                String password = new String(passwordField.getPassword());

                if (email.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(AuthView.this,
                            "Veuillez remplir tous les champs.",
                            "Champs vides", JOptionPane.WARNING_MESSAGE);
                    return; // Ne continue pas si les champs sont vides
                }

                Utilisateur utilisateur = utilisateurDAO.authentifier(email, password);

                if (utilisateur != null) {
                    String role = utilisateur.getRole();
                    String message = String.format(
                            "🎉 Bienvenue %s %s !\n\nVous êtes connecté en tant que %s.\nPrêt à gérer votre cahier de texte ? 🚀",
                            utilisateur.getPrenom(),
                            utilisateur.getNom(),
                            role.substring(0, 1).toUpperCase() + role.substring(1) // Mettre la première lettre en majuscule
                    );

                    JOptionPane.showMessageDialog(AuthView.this, message, "Connexion réussie ✅", JOptionPane.INFORMATION_MESSAGE);

                    // Redirection selon le rôle
                    switch (role) {
                        case "chef":
                            new ChefView(utilisateur).setVisible(true);
                            break;
                        case "enseignant":
                            new EnseignantView(utilisateur).setVisible(true);
                            break;
                        case "responsable":
                            new ResponsableView(utilisateur).setVisible(true);
                            break;
                    }

                    dispose();
                } else {
                    JOptionPane.showMessageDialog(AuthView.this,
                            "Email ou mot de passe incorrect",
                            "Erreur d'authentification", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        panel.add(loginButton, gbc);

        add(panel, BorderLayout.CENTER);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuthView().setVisible(true));
    }
}