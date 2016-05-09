package modeles.obstacles;

import java.awt.Color;

import javax.swing.ImageIcon;

import modeles.Case;

/**
 * This class represents the rock object which will be the strongest obstacle
 * @author prukev, Brahim
 *
 */
public class Rocher extends Obstacle {

	/**
	 * This data can give us the number of Rock there is in the game. It's also use for the id.
	 */
	private static int nbRochers = 1;
	
	/**
	 * This is the rock's constructor. It has an image and a difficulty
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Rocher(ImageIcon img) {
		this.myPicture = img;
		this.difficulty = 3;
		this.id = "Rocher_" + this.nbRochers;
		this.nbRochers++;
	}
	
	@Override
	public Object clone() {
		Rocher r = new Rocher(this.myPicture);
		r.copy(this);
		return r;
	}
	
	@Override
	public Object clone(Case[][] plateau) {
		Rocher r = new Rocher(this.myPicture);
		r.copy(this, plateau);
		return r;
	}
	
	public Color getColor() {
		return new Color(204, 204, 204);
	}

	@Override
	public String toString() {
		return "Rocher";
	}
	
}
