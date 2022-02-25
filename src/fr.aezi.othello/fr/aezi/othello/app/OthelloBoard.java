package fr.aezi.othello.app;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import fr.aezi.othello.modele.Case;
import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Damier;
import fr.aezi.othello.modele.GameEvent;
import fr.aezi.othello.modele.Jeu;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Shape3D;
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
	private Map<String, Shape3D> mySquares = new HashMap<>();
	private Board3D othellier ;
	private Group labels ;
	private Font labelsFont = new Font(30);
	private StatusBar statusBar = new StatusBar(400, 100, 20);
	private StatusBar whiteScore = new StatusBar(80, 80, 20);
	private StatusBar blackScore = new StatusBar(80, 80, 20);
	private StatusBar messagePane = new StatusBar(400, 100, 20);
	private Disc lastPlayedDisc = null;

	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		initializeBoard(stage);
		//putInitialDiscsOnBoard();
		//putDiscsForTurn();
		setJeu(new Jeu(damier));
		
		TextControl tc = new TextControl();
		tc.setJeu(jeu);
		Stage control = tc.createStage();
		control.setX(stage.getX() + stage.getWidth());
		control.setY(stage.getY());
		control.show();
		
		stage.setOnCloseRequest((e)->Platform.exit());
		control.setOnCloseRequest((e)->Platform.exit());
	}
	private void initializeBoard(Stage stage) throws Exception {
		stage.setTitle("Othello - v0");
		
		racine = new Group();
		
		Scene scene = new Scene(racine, 900,700,true);
		scene.setFill(Color.DARKGREY);
		
		othellier = new Board3D(WIDTH, HEIGHT);
		othellier.setTranslateX(WIDTH / 2);
		othellier.setTranslateY(HEIGHT / 2);
		
		statusBar.setTranslateX(200);
		statusBar.setTranslateY(900);
		
		messagePane.setRotationAxis(Rotate.X_AXIS);
		messagePane.setRotate(45);
		messagePane.setTranslateZ(-100);
		messagePane.setTranslateX(WIDTH / 2);
		messagePane.setTranslateY(HEIGHT);
		messagePane.setPlayer(Couleur.BLANC);
		messagePane.setInformation("");
		messagePane.setMainText("Hello !");
		messagePane.addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->messagePane.setVisible(false));
		
		whiteScore.setRotationAxis(Rotate.X_AXIS);
		blackScore.setRotationAxis(Rotate.X_AXIS);
		whiteScore.setRotate(45);
		blackScore.setRotate(45);

		blackScore.setTranslateX(WIDTH - 80);
		whiteScore.setTranslateX(WIDTH + 10);
		blackScore.setTranslateY(HEIGHT + 90);
		whiteScore.setTranslateY(HEIGHT + 90);
		whiteScore.setPlayer(Couleur.BLANC);
		whiteScore.setMainText("2");
		blackScore.setMainText("2");
		whiteScore.setInformation("");
		blackScore.setInformation("");
		
		racine.getChildren().add(othellier);
		racine.getChildren().add(blackScore);
		racine.getChildren().add(whiteScore);
		racine.getChildren().add(statusBar);

		
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
		lightGroup.setTranslateY(900);
		
		light = new PointLight();
		light.setColor(Color.WHITE);
		light.setTranslateX(WIDTH * 2);
		light.setTranslateY(HEIGHT * 2);
		racine.getChildren().add(light);
		
		labels = new Group();
		labels.setTranslateZ(-1.01);
		racine.getChildren().add(labels);
		addLabels();
		
		for (String coord : damier.getCoordSet()) {
			Node square = addPlayableSquare(coord);
			square.addEventHandler(MouseEvent.MOUSE_CLICKED, this::squareClicked);
		}
		
		racine.getChildren().add(messagePane);
		stage.setScene(scene);
		stage.show();
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
	
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
		for(Case square: jeu.getCasesOccupees()) {
			addDisc(jeu.getPion(square).getCouleur(), square.getEmplacement());
		}
		
		for(Case caseJouable: jeu.getCasesJouables()) {
			setSquareVisible(mySquares.get(caseJouable.getEmplacement()), true);
		}
		statusBar.setPlayer(jeu.getProchainJoueur());
		jeu.addGameListener(this::gameModified);
	}
	
	private void setSquareVisible(Shape3D square, boolean b) {
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
		if(e.hasProperty(GameEvent.PropKeys.DISCS_TO_TURN)) {
			//Resets all discs' state
			myDiscs.values().stream().forEach((d)->d.setState(DiscState.PLACED));
			
			Case playedSquare = (Case)e.getSource();
			Couleur playedColor = (Couleur)e.getProperty(GameEvent.PropKeys.PLAYED_COLOR);
			Disc addedDisc = addDisc(playedColor, playedSquare.getEmplacement());
			if(lastPlayedDisc != null) {
				lastPlayedDisc.setState(DiscState.PLACED);
			}
			addedDisc.setState(DiscState.LAST_PLAYED);
			lastPlayedDisc = addedDisc;
			
			
			@SuppressWarnings("unchecked")
			Set<Case> discsToTurn = (Set<Case>) e.getProperty(GameEvent.PropKeys.DISCS_TO_TURN);
			
			class DelayManager {
				double delai = 0.0;
				double valeur() { delai += 0.05 ; return delai; }
			};
			DelayManager inc = new DelayManager();
			discsToTurn.stream().map(c -> c.getEmplacement())
			.filter((d)->myDiscs.containsKey(d))
			.forEach((coord) -> myDiscs.get(coord).setDiscToBeTurned(inc.valeur()));
			
			mySquares.keySet().stream().forEach((coord) -> setSquareVisible(mySquares.get(coord), false));
			
			discsToTurn.stream().map(c -> c.getEmplacement())
			.filter((d)->myDiscs.containsKey(d)).forEach((coord) -> myDiscs.get(coord).runItNow());
			System.out.println("========= Case Jouée: " + playedSquare.getEmplacement()+ "========");
			System.out.println(jeu);
			System.out.println("============="+jeu.getProchainJoueur()+"================");
		}
		if(e.hasProperty(GameEvent.PropKeys.NEXT_PLAYER)) {
			Couleur nextPlayer = (Couleur) e.getProperty(GameEvent.PropKeys.NEXT_PLAYER);
			jeu.getCasesJouables(nextPlayer).stream()
			.forEach((c) -> setSquareVisible(mySquares.get(c.getEmplacement()), true));
			statusBar.setPlayer(nextPlayer);
		}
		if(e.hasProperty(GameEvent.PropKeys.PLAYER_MUST_PASS)) {
			Couleur whoPasses = (Couleur) e.getProperty(GameEvent.PropKeys.PLAYER_MUST_PASS);
			messagePane.setMainText(whoPasses + " must pass ! " + whoPasses.getOpposant() + "'s turn");
			messagePane.setVisible(true);
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					messagePane.setVisible(false);
				}
			}, 5000);
		}
		if(e.hasProperty(GameEvent.PropKeys.GAME_OVER)) {
			
			if(e.getProperty(GameEvent.PropKeys.GAME_OVER) instanceof Couleur) {
				messagePane.setMainText(e.getProperty(GameEvent.PropKeys.GAME_OVER) + " wins !!!");
			}
			else {
				messagePane.setMainText(e.getProperty(GameEvent.PropKeys.GAME_OVER).toString());
			}
			messagePane.setVisible(true);
		}
		whiteScore.setMainText(Integer.toString(jeu.getColorScore(Couleur.BLANC)));
		blackScore.setMainText(Integer.toString(jeu.getColorScore(Couleur.NOIR)));
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
					jeu.jouer(gameSquare, colorToPlay);
				}
			}
			else {
				System.err.println(e.getSource()+ " is not a Node");
			}
		}
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
		Shape3D square = othellier.createSquare(location.getX(), location.getY());
		square.setAccessibleText(coord);
		setSquareVisible(square, false);
		mySquares.put(coord, square);
		
		
		square.addEventHandler(MouseEvent.MOUSE_ENTERED, (e)->{
			statusBar.setInformation(coord + " is " + square.getAccessibleHelp());
			Case place = damier.getCoord(coord);
			jeu.retournementsPossibles(place, jeu.getProchainJoueur()).stream().forEach((c)-> {
				myDiscs.get(c.getEmplacement()).setState(DiscState.TO_BE_TURNED);
			});
		});
		square.addEventHandler(MouseEvent.MOUSE_EXITED, (e)->{
			Case place = damier.getCoord(coord);
			jeu.retournementsPossibles(place, jeu.getProchainJoueur()).stream().forEach((c)-> {
				myDiscs.get(c.getEmplacement()).restoreState();
			});
		});
		return square;
	}
}
