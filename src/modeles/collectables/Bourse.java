package modeles.collectables;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This is the Bourse object which gives to the player three points
 * @author prukev, Brahim
 *
 */
public class Bourse extends Objet {

	/**
	 * This data can give us the number of Bourse there is in the game. It's also use for the id.
	 */
	private static int nbBourse = 1;
	
	/**
	 * This is the Bourse's constructor. It takes an Image in argument. In the minimal version of
	 * the game, the image is null
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Bourse(ImageIcon img) {
		this.myPicture = img;
		this.setBonus(3);
		this.id = "Bourse_" + this.nbBourse;
		this.nbBourse++;
	}

	/**
	 * ToString function. Customized and not improved
	 */
	@Override
	public String toString() {
		return "Bourse";
	}

	@Override
	public Object clone() {
		Bourse b = new Bourse(this.myPicture);
		b.copy(this);
		return b;
	}

	@Override
	public Object clone(Case[][] plateau) {
		Bourse b = new Bourse(this.myPicture);
		b.copy(this, plateau);
		return b;
	}
	
}
