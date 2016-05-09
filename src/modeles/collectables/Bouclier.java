package modeles.collectables;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This class represent the shield object
 * @author prukev, Brahim
 *
 */
public class Bouclier extends Objet {

	/**
	 * This is the shield constructor. It will be developed when we will be
	 * in the advanced version of the game
	 */
	private static int nBouclier= 1;
	
	/**
	 * Shield constructor with an image as argument
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Bouclier(ImageIcon img) {
		this.myPicture = img;
		this.setBonus(1);
		this.id = "Bouclier_" + this.nBouclier;
		this.nBouclier++;
	}

	/**
	 * To string function. 
	 */
	@Override
	public String toString() {
		return "Bouclier";
	}
	
	@Override
	public Object clone() {
		Bouclier b = new Bouclier(this.myPicture);
		b.copy(this);
		return b;
	}

	@Override
	public Object clone(Case[][] plateau) {
		Bouclier b = new Bouclier(this.myPicture);
		b.copy(this, plateau);
		return b;
	}

}
