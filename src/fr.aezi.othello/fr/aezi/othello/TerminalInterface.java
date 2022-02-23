package fr.aezi.othello;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import fr.aezi.othello.modele.Case;
import fr.aezi.othello.modele.Couleur;
import fr.aezi.othello.modele.Jeu;

public class TerminalInterface {
	private Jeu jeu ;
	
	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}
	
	public void jouer() {
		while(true) {
	        try {
	        	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	            String reponse = null;

	            System.out.println("Choisis ta case :");
	            reponse = bufferRead.readLine();
                try {
		            Case c = jeu.getDamier().getCoord(reponse);
		            Couleur couleurAJouer = jeu.getProchainJoueur();
		            jeu.changerProchainJoueur();
					jeu.jouer(c, couleurAJouer);
		            
				} catch (RuntimeException e) {
					System.out.println(e.getMessage());
				}
                bufferRead.close();
	        }
	        catch(IOException ex) {
	        	System.err.println(ex.getMessage());
	        }
        }
	}
	
	
}
