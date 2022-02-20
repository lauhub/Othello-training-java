package fr.aezi.othello.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class OthelloBoard extends Application {
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH;
	public static final int DISC_DIAMETER = 9 * WIDTH / 88;
	public static final int DISC_THICKNESS = 12;
	
	private Group racine ;
	private Damier damier = new Damier();
	private Map<String, Disc> myDiscs = new HashMap<>();
	private Map<String, Box> myPlaces = new HashMap<>();
	private Board3D othellier ;
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		initializeBoard(stage);
		putInitialDiscsOnBoard();
		putDiscsForTurn();
	}
	private void initializeBoard(Stage stage) throws Exception {
		stage.setTitle("Othello - v0");
		
		racine = new Group();
		
		Scene scene = new Scene(racine, 900,700,true);
		scene.setFill(Color.DARKGREY);
		
		othellier = new Board3D(WIDTH, HEIGHT);
		othellier.setTranslateX(WIDTH / 2);
		othellier.setTranslateY(HEIGHT / 2);
		
		racine.getChildren().add(othellier);

		
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
	
	private void putInitialDiscsOnBoard() {
		addDisc(Couleur.BLANC, "D4");
		addDisc(Couleur.BLANC, "E5");
		addDisc(Couleur.NOIR, "E4");
		addDisc(Couleur.NOIR, "D5");
		addPlayablePlace("C4");
		addPlayablePlace("C3");
		addPlayablePlace("D3");
		addPlayablePlace("E6");
		addPlayablePlace("F5");
		addPlayablePlace("F6");
		myPlaces.get("F6").setVisible(false);
	}
	
	private List<String> putDiscsForTurn() {
		List<String> discsToTurn = new ArrayList<>();
		addDiscToTurn(discsToTurn, Couleur.BLANC, "F6");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "G7");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H8");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H7");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H6");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H5");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H4");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H3");
		addDiscToTurn(discsToTurn, Couleur.BLANC, "H2");
		return discsToTurn;
	}
	
	private void addDiscToTurn(List<String> list, Couleur color, String coord) {
		Disc disc = addDisc(color, coord);
		list.add(coord);
		disc.addEventHandler(MouseEvent.MOUSE_CLICKED, (e)-> {
			if(e.getButton() == MouseButton.PRIMARY) {
				turnDiscs(list);
			}
		});
	}
	
	public Disc addDisc(Couleur color, String coord) {
		Disc disc = new Disc(DISC_DIAMETER, DISC_THICKNESS, color.equals(Couleur.BLANC));
		
		Point2D place = damier.getCoord(coord, WIDTH, HEIGHT);
		disc.setTranslateX(place.getX());
		disc.setTranslateY(place.getY());
		
		racine.getChildren().add(disc);
		myDiscs.put(coord, disc);
		return disc;
	}
	public void addPlayablePlace(String coord) {
		Point2D place = damier.getCoord(coord, WIDTH, HEIGHT);
		Box box = othellier.createPlayableBox(place.getX(), place.getY());
		
		myPlaces.put(coord, box);
	}
	
	public void turnDiscs(List<String> coords) {
		double delay = 0.0;
		for(String coord : coords) {
			if(myDiscs.containsKey(coord)) {
				myDiscs.get(coord).setDiscToBeTurned(delay);
				delay += 0.05;
			}
		}
		
		for(String coord : coords) {
			if(myDiscs.containsKey(coord)) {
				myDiscs.get(coord).runItNow();
			}
		}
		
	}
}
