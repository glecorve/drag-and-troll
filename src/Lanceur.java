import ia.IAAleatoire;
import ia.IAAleatoireVenale;
import vues.JeuIHM;
import controle.Jeu;


public class Lanceur {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jeu jeu = new Jeu(9);
//		Jeu jeu = new Jeu(11);
		jeu.setJoueur(1, new IAAleatoire());
		jeu.setJoueur(0, new IAAleatoireVenale());
		JeuIHM.getInstance(jeu);
		jeu.demarrerTour();
	}

}
