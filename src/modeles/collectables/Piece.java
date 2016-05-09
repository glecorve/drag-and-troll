package modeles.collectables;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This is the Coin object which gives to the player one point
 * @author prukev, Brahim
 *
 */
public class Piece extends Objet {

	/**
	 * This data can give us the number of Coin there is in the game. It's also use for the id.
	 */
	private static int nbCoin = 1;
	
	/**
	 * This is the Coin's constructor. It takes an Image in argument. In the minimal version of
	 * the game, the image is null
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Piece(ImageIcon img) {
		this.myPicture = img;
		this.setBonus(1);
		this.id = "Piece_" + this.nbCoin;
		this.nbCoin++;
	}
	
	@Override
	public Object clone() {
		Piece p = new Piece(this.myPicture);
		p.copy(this);
		return p;
	}
	
	@Override
	public Object clone(Case[][] plateau) {
		Piece p = new Piece(this.myPicture);
		p.copy(this, plateau);
		return p;
	}

	/**
	 * To string function. 
	 */
	@Override
	public String toString() {
		return "Piece";
	}
	
}
