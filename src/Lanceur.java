import ia.IAAleatoire;
import vues.JeuIHM;
import controle.Jeu;


public class Lanceur {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Jeu jeu = new Jeu(9);
		jeu.setJoueur(1, new IAAleatoire());
		JeuIHM ihm = JeuIHM.getInstance(jeu);

	}

}
