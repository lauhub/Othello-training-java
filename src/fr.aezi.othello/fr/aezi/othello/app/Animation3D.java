package fr.aezi.othello.app;

import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.event.EventHandler;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.PerspectiveCamera;
import javafx.scene.PointLight;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Animation3D extends Application {
	public static void main(String[] args) {
		System.out.println("Méthode main");
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("On est dans la méthode Start");
		//1 On initialise la fenêtre
		stage.setTitle("Première Application");
		
		//3 on initialise le groupe racine:
		Group racine = new Group();
		racine = new Group();
		
		Scene scene = new Scene(racine, 900,700,true);
		
		Group othellier = new Group();
		
		Rectangle fond = new Rectangle(800, 800);
		fond.setFill(Color.GREEN);
		Canvas trame = new Canvas(800,800);
		othellier.getChildren().add(fond);
		othellier.getChildren().add(trame);
		
		dessinerDamier(trame, Color.BLACK);
		racine.getChildren().add(othellier);

		
		racine.getChildren().add(creerPion(100,100, false));
		racine.getChildren().add(creerPion(180,180, false));
		racine.getChildren().add(creerPion(100,180, true));
		racine.getChildren().add(creerPion(180,100, true));
		racine.getChildren().add(creerPion(260,100, true));
		racine.getChildren().add(creerPion(260,260, true));
		racine.getChildren().add(creerPion(340,340, true));
		racine.getChildren().add(creerPion(420,420, true));

		
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
		lightGroup.setTranslateZ(-500);
		lightGroup.setTranslateY(500);
		light.setRotate(90);
	}
	
	private Node creerPion(double x, double y, boolean white) {
		Group assemblage = new Group();
		Cylinder faceNoire = new Cylinder(30, 4);
		//pion1.setTranslateZ(-14);
		PhongMaterial material = new PhongMaterial();
		material.setDiffuseColor(Color.BLACK);
		//material.setSpecularColor(Color.DARKGRAY);
		faceNoire.setMaterial(material);
		//pion1.getTransforms().setAll(rotationX, rotationZ);
		faceNoire.setRotationAxis(Rotate.X_AXIS);
		faceNoire.setRotate(90);
		faceNoire.setAccessibleText("FACE NOIRE");
		
		Cylinder faceBlanche = new Cylinder(30, 4);
		material = new PhongMaterial();
		material.setDiffuseColor(Color.WHITESMOKE);
		//material.setSpecularColor(Color.WHITESMOKE);
		faceBlanche.setMaterial(material);
		faceBlanche.setRotationAxis(Rotate.X_AXIS);
		faceBlanche.setRotate(90);
		faceBlanche.setAccessibleText("FACE BLANCHE");

		assemblage.getChildren().addAll(faceNoire, faceBlanche);
		
		assemblage.setTranslateX(x+100);
		assemblage.setTranslateY(y);
		faceBlanche.setTranslateZ(4);
		assemblage.setTranslateZ(-15);
		if(white) {
			assemblage.setRotationAxis(new Point3D(1, 1, 0));
			assemblage.setRotate(180);
		}
		
		EventHandler<MouseEvent> handler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent t) {            
                    tournerPion(assemblage);
            }
        };
		
		assemblage.addEventHandler(MouseEvent.MOUSE_CLICKED, handler);
		return assemblage;
	}
	
	private void tournerPion(Node pion) {
		System.out.println("Accessible Text:"+ pion.getAccessibleText());
		pion.setRotationAxis(new Point3D(1, 1, 0));
		DoubleProperty rotateProperty = pion.rotateProperty();
		double value = rotateProperty.doubleValue();
		value = (value + 180) % 360;
		System.out.println(pion.rotateProperty());
		DoubleProperty translateProp = pion.translateZProperty();
		System.out.println(translateProp);
		
		KeyValue plusTranslate = new KeyValue(pion.translateZProperty(), -50, Interpolator.EASE_OUT);
		KeyValue minusTranslate = new KeyValue(pion.translateZProperty(), -15, Interpolator.EASE_IN);
		KeyValue kvAxis = new KeyValue(pion.rotationAxisProperty(), new Point3D(1, 0, 0));
		Timeline tl = new Timeline();
		tl.setRate(1.0);
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.3), plusTranslate));

		KeyValue kvValue = new KeyValue(pion.rotateProperty(), value);
		Timeline tlTurn = new Timeline();
		tlTurn.setRate(1.0);
		tlTurn.getKeyFrames().add(new KeyFrame(Duration.seconds(0.2), kvValue, kvAxis));
		Timeline tlFall = new Timeline();
		tlFall.setRate(4.0);
		tlFall.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), minusTranslate));

		tl.play();
		tlTurn.setDelay(Duration.seconds(0.1));
		tlTurn.play();
		tl.setOnFinished((event)->{
			tlFall.play();
		});
	}
	public void tournerPion0(Node pion) {
		System.out.println("Accessible Text:"+ pion.getAccessibleText());
		pion.setRotationAxis(new Point3D(1, 1, 0));
		DoubleProperty rotateProperty = pion.rotateProperty();
		double value = rotateProperty.doubleValue();
		value = (value + 180) % 360;
		System.out.println(pion.rotateProperty());
		DoubleProperty translateProp = pion.translateZProperty();
		System.out.println(translateProp);
		
		KeyValue plusTranslate = new KeyValue(pion.translateZProperty(), -100, Interpolator.EASE_OUT);
		KeyValue minusTranslate = new KeyValue(pion.translateZProperty(), -15, Interpolator.EASE_IN);
		KeyValue kvAxis = new KeyValue(pion.rotationAxisProperty(), new Point3D(1, 0, 0));
		KeyValue kvValue = new KeyValue(pion.rotateProperty(), value);
		//KeyValue kvMove = new KeyValue(pion.translateZProperty(), - translateProp.doubleValue());
		Timeline tl = new Timeline();
		tl.setRate(1.0);
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(0.5), plusTranslate));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), kvValue, kvAxis));
		tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1.0), minusTranslate));
		//tl.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new KeyValue(pion.rotationAxisProperty(), new Point3D(1, 1, 0)), new KeyValue(pion.rotateProperty(), value) ));
		tl.play();
		tl.setOnFinished((event)->{
			System.out.println(pion.rotateProperty());
		});
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
