package modeles.obstacles;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * This class represents the water object which will be a little stronger than a forest
 * @author prukev, Brahim
 *
 */
public class Eau extends Obstacle {

	/**
	 * This data can give us the number of Water there is in the game. It's also use for the id.
	 */
	private static int nbEaux = 1;
	
	/**
	 * This is the water's constructor. It has an image and a difficulty
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Eau(ImageIcon img) {
		this.myPicture = img;
		this.difficulty = 2;
		this.id = "Eau_" + this.nbEaux;
		this.nbEaux++;
	}
	
	@Override
	public Object clone() {
		Eau e = new Eau(this.myPicture);
		e.copy(this);
		return e;
	}
	
	public Color getColor() {
		return new Color(153, 204, 255);
	}

	/**
	 * ToString function
	 */
	@Override
	public String toString() {
		return "Eau";
	}
	
	
	
}
