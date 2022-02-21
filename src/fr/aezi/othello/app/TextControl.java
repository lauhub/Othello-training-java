package fr.aezi.othello.app;

import java.util.Arrays;

import fr.aezi.othello.modele.GameEvent;
import fr.aezi.othello.modele.GameListener;
import fr.aezi.othello.modele.Jeu;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
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
		
		
		VBox vbox = new VBox(3);
		Text text = new Text("Saisir coordonnées:");
		//text.setLayoutY(text.getBaselineOffset());
		vbox.getChildren().add(text);
		
		TextField champDeSaisie = new TextField("Type a coordinate (e.g. D3)");
		//tf.setLayoutX(50);
		champDeSaisie.setOnAction(new EventHandler<ActionEvent>() {
			
			@Override
			public void handle(ActionEvent event) {
				System.out.println(event);
				jeu.jouer(
						jeu.getDamier().getCoord(champDeSaisie.getText()),
								jeu.getProchainJoueur());
			}
		});
		vbox.getChildren().add(champDeSaisie);
		racine.getChildren().add(vbox);
		
		Text info = new Text(jeu.toString());
		System.out.println(Arrays.toString(Font.getFamilies().toArray()));
		Font font  = new Font("Courier New", 14);
		info.setFont(font);
		vbox.getChildren().add(info);
		
		Text nextPlayer = new Text(jeu.getProchainJoueur().toString());
		vbox.getChildren().add(nextPlayer);
		
		jeu.addGameListener(new GameListener() {
			
			@Override
			public void handle(GameEvent e) {
				info.setText(jeu.toString());
				nextPlayer.setText(jeu.getProchainJoueur().toString());
				champDeSaisie.selectAll();
			}
		});
		
		
		return stage;
	}
	
	
}
