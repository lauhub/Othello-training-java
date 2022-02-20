package fr.aezi.othello.app;

import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class OthelloBoard extends Application {
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH;
	public static final int DISC_DIAMETER = 9 * WIDTH / 88;
	public static final int DISC_THICKNESS = 12;
	
	private Group racine ;
	private Damier damier = new Damier();
	
	public static void main(String[] args) {
		System.out.println("Méthode main");
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("On est dans la méthode Start");
		//1 On initialise la fenêtre
		stage.setTitle("Première Application");
		
		racine = new Group();
		
		Scene scene = new Scene(racine, 900,700,true);
		scene.setFill(Color.DARKGREY);
		
		Group othellier = new Board3D(WIDTH, HEIGHT);
		othellier.setTranslateX(WIDTH / 2);
		othellier.setTranslateY(HEIGHT / 2);
		
		racine.getChildren().add(othellier);

		addDisc(Couleur.BLANC, "D4");
		addDisc(Couleur.BLANC, "E5");
		addDisc(Couleur.NOIR, "E4");
		addDisc(Couleur.NOIR, "D5");

		
		stage.setScene(scene);
		stage.show();
		
		PerspectiveCamera camera = new PerspectiveCamera();
		camera.setTranslateY(00);
		camera.setTranslateY(500);
		camera.setTranslateZ(-500);
		camera.setRotationAxis(Rotate.X_AXIS);
		camera.setRotate(45);
		scene.setCamera(camera);
		
		PointLight light = new PointLight();
		light.setColor(Color.WHITE);
		
		Group lightGroup = new Group();
		lightGroup.getChildren().add(light);
		racine.getChildren().add(lightGroup);
		lightGroup.setTranslateZ(-1100);
		lightGroup.setTranslateY(-900);
		//light.setRotate(45);
	}
	
	
	
	
	public void addDisc(Couleur color, String coord) {
		Disc disc = new Disc(DISC_DIAMETER, DISC_THICKNESS, color.equals(Couleur.BLANC));
		
		Point2D place = damier.getCoord(coord, WIDTH, HEIGHT);
		disc.setTranslateX(place.getX());
		disc.setTranslateY(place.getY());
		
		racine.getChildren().add(disc);
		
	}
	
	

}
