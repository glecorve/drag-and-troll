package modeles.obstacles;

import javax.swing.ImageIcon;

/**
 * This class represents the forest object which will be a simple obstacle
 * @author prukev, Brahim
 *
 */
public class Arbre extends Obstacle {

	/**
	 * This data can give us the number of trees there is in the game. It's also used for the id.
	 */
	private static int nbArbres = 1;
	
	/**
	 * This is the forest's constructor. It has an image and a difficulty
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Arbre(ImageIcon img) {
		this.myPicture = img;
		this.difficulty = 1;
		this.id = "Arbre_" + this.nbArbres;
		this.nbArbres++;
	}
	
	@Override
	public Object clone() {
		Arbre a = new Arbre(this.myPicture);
		a.copy(this);
		return a;
	}

	/**
	 * When the dragon's fire touches a forest, it will be deleted.
	 */
	@Override
	public void dragonEffet() {
		this.myPicture = null;
		this.position.deleteEntite(this);
	}

	/**
	 * ToString function.
	 */
	@Override
	public String toString() {
		return "Arbre";
	}
	
	
	
}
