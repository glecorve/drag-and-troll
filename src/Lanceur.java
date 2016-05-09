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
//		jeu.setJoueur(0, new IAAleatoire());
//		jeu.setJoueur(1, new IAAleatoire());
		jeu.setJoueur(1, new IAAleatoireVenale());
		JeuIHM ihm = JeuIHM.getInstance(jeu);
		jeu.demarrerTour();
	}

}
