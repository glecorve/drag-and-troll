package modeles.collectables;

import javax.swing.ImageIcon;

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

	@Override
	public Object clone() {
		Bouclier b = new Bouclier(this.myPicture);
		b.copy(this);
		return b;
	}

}
