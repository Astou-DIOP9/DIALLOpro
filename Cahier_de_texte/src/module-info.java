/**
 * 
 */
/**
 * 
 */
module Cahier_de_texte {
	requires javafx.controls;
	requires javafx.fxml;
    // autres requires éventuels
	requires java.sql;
	requires java.desktop;
	requires javafx.graphics;
    exports dao;
    exports view;
    exports models;
    exports Main; // <-- ajoute ceci pour rendre le package accessible
    opens Main to javafx.fxml; 
}