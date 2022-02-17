package fr.aezi.othello;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Othello extends Application {
	public static void main(String[] args) {
		System.out.println("Méthode main");
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("On est dans la méthode Start");
		//1 On initialise la fenêtre
		stage.setWidth(1000);
		stage.setHeight(800);
		stage.setTitle("Othello");
		
		//3 on initialise le groupe racine:
		Group racine = new Group();
		
		Scene scene = new Scene(racine);
		
		Group othellier = new Group();
		
		Rectangle fond = new Rectangle(800, 800);
		fond.setFill(Color.GREEN);
		Canvas trame = new Canvas(800,800);
		othellier.getChildren().add(fond);
		othellier.getChildren().add(trame);
		
		//Group controle = new Group();
		dessinerDamier(trame, Color.BLACK);
		
		racine.getChildren().add(othellier);
		//racine.getChildren().add(controle);
		
		
		//6 on affecte la scene à la fenêtre
		stage.setScene(scene);
		
		//2 on montre la fenêtre
		stage.show();
	}
    private void dessinerDamier(Canvas canvas, Color color) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(color);
        
        
        
        double incrementX = canvas.getWidth()/8.0;
        double incrementY = canvas.getHeight()/8.0;
        
        for (double x = 0.0; x <= canvas.getWidth(); x += incrementX) {
        	gc.strokeLine(x, 0, x, canvas.getHeight());
        }
        for (double y = 0.0; y <= canvas.getHeight(); y += incrementY) {
        	gc.strokeLine(0, y, canvas.getWidth(), y);
        }
    }
}
