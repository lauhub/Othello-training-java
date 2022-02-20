package fr.aezi.othello.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.aezi.othello.modele.Case;
import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import fr.aezi.othello.modele.GameEvent;
import fr.aezi.othello.modele.Jeu;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class OthelloBoard extends Application {
	public static final int WIDTH = 800;
	public static final int HEIGHT = WIDTH;
	public static final int DISC_DIAMETER = 9 * WIDTH / 88;
	public static final int DISC_THICKNESS = 12;
	
	private Group racine ;
	private Damier damier = new Damier();
	private Jeu jeu = null;
	private Map<String, Disc> myDiscs = new HashMap<>();
	private Map<String, Box> mySquares = new HashMap<>();
	private Board3D othellier ;
	private Group labels ;
	private Font labelsFont = new Font(30);

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		initializeBoard(stage);
		//putInitialDiscsOnBoard();
		//putDiscsForTurn();
		setJeu(new Jeu(damier));
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

		labels = new Group();
		labels.setTranslateZ(-2);
		racine.getChildren().add(labels);
		addLabels();
		
		for (String coord : damier.getCoordSet()) {
			Node square = addPlayableSquare(coord);
			square.addEventHandler(MouseEvent.MOUSE_CLICKED, this::squareClicked);
			
			labels.getChildren().add(createSquareLabel(coord, square.getTranslateX()+400, square.getTranslateY()+400));
		}
		
	}
	
	private void addLabels() {
		String[] coords = new String[] {"A1", "B2", "C3", "D4", "E5", "F6", "G7", "H8"}; 
		for (String coord : coords) {
			Point2D location = damier.getCoord(coord, WIDTH, HEIGHT);
			Text lineLabel = new Text(coord.substring(0, 1));
			lineLabel.setFont(labelsFont);
			lineLabel.setTranslateX(location.getX());
			lineLabel.setTranslateY(-20);
			labels.getChildren().add(lineLabel);
			
			Text colLabel = new Text(coord.substring(1, 2));
			colLabel.setFont(labelsFont);
			colLabel.setTranslateX(-30);
			colLabel.setTranslateY(location.getY());
			labels.getChildren().add(colLabel);
			
		}

	}
	
	private Text createSquareLabel(String coord, double x, double y) {
		Text label = new Text(coord);
		label.setFont(labelsFont);
		label.setTranslateX(x);
		label.setTranslateY(y);
		return label;
	}
	
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
		for(Case square: jeu.getCasesOccupees()) {
			addDisc(jeu.getPion(square).getCouleur(), square.getEmplacement());
		}
		
		for(Case caseJouable: jeu.getCasesJouables()) {
			setSquareVisible(mySquares.get(caseJouable.getEmplacement()), true);
		}
		jeu.addGameListener(this::gameModified);
	}
	
	private void setSquareVisible(Box square, boolean b) {
		PhongMaterial material = (PhongMaterial)square.getMaterial();
		if(b) {
			square.setAccessibleHelp("playable");
			material.setDiffuseColor(Board3D.PLAYABLE_COLOR);
		}
		else {
			square.setAccessibleHelp("non-playable");
			material.setDiffuseColor(Board3D.NON_PLAYABLE_COLOR);
			
		}
	}
	
	
	private void gameModified(GameEvent e) {
		//Game was modified
		Case playedSquare = (Case)e.getSource();
		addDisc((Couleur)e.getProperty(GameEvent.PLAYED_COLOR), playedSquare.getEmplacement());
		
		Set<Case> discsToTurn = (Set<Case>) e.getProperty(GameEvent.DISCS_TO_TURN);
		double delay = 0.0;
		for(Case c : discsToTurn) {
			String coord = c.getEmplacement();
			if(myDiscs.containsKey(coord)) {
				myDiscs.get(coord).setDiscToBeTurned(delay);
				delay += 0.05;
			}
			else {
				throw new IllegalStateException("Cannot turn a disc that is not displayed :" + c.getEmplacement());
			}
		}
		// Et une fois cela fait, on met à jour les cases jouables
		for (String coord : mySquares.keySet()) {
			System.out.println("update playable at: " + coord);
			setSquareVisible(mySquares.get(coord), false);
		}
		
		// Afficheles cases jouables
		for(Case squareModel: jeu.getCasesJouables()) {
			System.out.println("Case jouable (affichage): " + squareModel.getEmplacement());
			setSquareVisible(mySquares.get(squareModel.getEmplacement()), true);
		}
		
		/*
		 * TODO: montrer comment éviter cette redondance de code... (visiteur ?)
		 */
		for(Case c : discsToTurn) {
			String coord = c.getEmplacement();
			if(myDiscs.containsKey(coord)) {
				myDiscs.get(coord).runItNow();
			}
		}
		System.out.println("========= Case Jouée: " + playedSquare.getEmplacement()+ "========");
		System.out.println(jeu);
		System.out.println("=============================");
		
	}
	
	private void squareClicked(MouseEvent e) {
		if(e.getButton() == MouseButton.PRIMARY) {
			if(e.getSource() instanceof Node) {
				Node square = (Node)e.getSource();
				System.out.println("squareClicked: "+square.getAccessibleText()+ " is "+ square.getAccessibleHelp() );
				if("playable".equals(square.getAccessibleHelp())){
					String coord = square.getAccessibleText();
					if(coord == null) {
						throw new IllegalStateException("Not a known square");
					}
					Case gameSquare = damier.getCoord(coord);
					Couleur colorToPlay = jeu.getProchainJoueur();
					jeu.changerProchainJoueur();
					jeu.jouer(gameSquare, colorToPlay);
				}
			}
			else {
				System.err.println(e.getSource()+ " is not a Node");
			}
		}
	}
	
	public List<String> putDiscsForTurn() {
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
	
	private Node addPlayableSquare(String coord) {
		Point2D location = damier.getCoord(coord, WIDTH, HEIGHT);
		Box square = othellier.createSquare(location.getX(), location.getY());
		square.setAccessibleText(coord);
		setSquareVisible(square, false);
		mySquares.put(coord, square);
		System.out.println("addPlayableSquare("+ coord+ " "+ location);
		return square;
	}
	

	/**
	 * Turns the discs from the given coordinates
	 * @param coords a list of coordinates
	 */
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
