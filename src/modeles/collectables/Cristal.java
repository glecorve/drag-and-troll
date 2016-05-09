package modeles.collectables;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This class represent the cristal object
 * @author prukev, Brahim
 *
 */
public class Cristal extends Objet {

	/**
	 * This data can give us the number of Cristal there is in the game. It's also use for the id.
	 */
	private static int nbCristaux = 1;
	
	/**
	 * This is the Cristal's constructor. It takes an Image in argument. In the minimal version of
	 * the game, the image is null
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Cristal(ImageIcon img) {
		this.myPicture = img;
		this.setBonus(1);
		this.id = "Cristal_" + this.nbCristaux;
		this.nbCristaux++;
		
	}
	
	@Override
	public Object clone() {
		Cristal c = new Cristal(this.myPicture);
		c.copy(this);
		return c;
	}
	
	@Override
	public Object clone(Case[][] plateau) {
		Cristal c = new Cristal(this.myPicture);
		c.copy(this, plateau);
		return c;
	}

	/**
	 * To string function. 
	 */
	@Override
	public String toString() {
		return "Cristal";
	}

}
