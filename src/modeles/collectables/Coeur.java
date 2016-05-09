package modeles.collectables;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This class represent the heart object
 * @author prukev, Brahim
 *
 */
public class Coeur extends Objet {

	/**
	 * This data can give us the number of Coeur there is in the game. It's also use for the id.
	 */
	private static int nbCoeur = 1;
	
	/**
	 * This is the heart constructor. It will be developed when we will be
	 * in the advanced version of the game
	 */
	@SuppressWarnings("static-access")
	public Coeur(ImageIcon img) {
		this.myPicture = img;
		this.setBonus(1);
		this.id = "Coeur_" + this.nbCoeur;
		this.nbCoeur++;
	}
	
	@Override
	public Object clone() {
		Coeur c = new Coeur(this.myPicture);
		c.copy(this);
		return c;
	}
	
	@Override
	public Object clone(Case[][] plateau) {
		Coeur c = new Coeur(this.myPicture);
		c.copy(this, plateau);
		return c;
	}

	/**
	 * To string function. 
	 */
	@Override
	public String toString() {
		return "Coeur";
	}

}
