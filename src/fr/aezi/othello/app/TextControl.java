package fr.aezi.othello.app;

import fr.aezi.othello.modele.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextControl{
	private Jeu jeu ;
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public Stage createStage() {
		Stage stage = new Stage();
		
		Group racine = new Group();
		Scene scene = new Scene(racine, 200,300,true);
		stage.setScene(scene);
		
		Text text = new Text("Saisir coordonnées:");
		
		racine.getChildren().add(text);
		
		var tf = new TextField("");
		tf.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event);
				jeu.jouer(
						jeu.getDamier().getCoord(tf.getText()),
								jeu.getProchainJoueur());
				
				jeu.changerProchainJoueur();		
			}
		});
		racine.getChildren().add(tf);

		
		return stage;
	}
	
	
}
