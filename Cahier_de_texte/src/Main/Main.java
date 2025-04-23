package Main;
import javax.swing.SwingUtilities;


import view.AuthView;

public class Main {
    public static void main(String[] args) {
        // Utiliser SwingUtilities.invokeLater pour assurer que l'interface graphique
        // est créée et mise à jour sur le thread de dispatch d'événements (EDT)
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AuthView().setVisible(true);
            }
        });
    }
}