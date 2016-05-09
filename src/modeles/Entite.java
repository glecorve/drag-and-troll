package modeles;

import java.awt.Color;

import javax.swing.ImageIcon;

/**
 * This class, which is abstract, is the base of all the objects of our game
 * and contains all the common data and operations.
 * @author prukev, Brahim
 *
 */
public abstract class Entite {

	/**
	 * All the entities have an id in order to recognize them
	 */
	protected String id;
	
	/**
	 * All the entities have a position
	 */
	protected Case position;
	
	/**
	 * Icon used for the icon of each jButton
	 */
	protected ImageIcon myPicture;
	
	/**
	 * Color used to draw square backgrounds
	 */
	public static Color COLOR = new Color(153, 204, 153);
	
	@Override
	public abstract Object clone();
	
	/**
	 * Clone the object except the board squares. The latter are referenced from a given board.
	 * @param plateau Board
	 * @return The clone
	 */
	public abstract Object clone(Case[][] plateau);

	/**
	 * Copy an object onto the current instance.
	 * This method is useful to factor cloning instructions in abstract classes (no available constructor).
	 * @param e Object to be copied
	 * @param plateau Board from which Case instances should be taken (no clone)
	 */
	protected void copy(Entite e) {
		this.id = new String(e.id);
		this.position = (Case) e.position.clone();
		this.myPicture = e.myPicture;
	}
	
	/**
	 * Copy an object onto the current instance.
	 * This method is useful to factor cloning instructions in abstract classes (no available constructor).
	 * @param e Object to be copied
	 * @param plateau Board from which Case instances should be taken (no clone)
	 */
	protected void copy(Entite e, Case[][] plateau) {
		this.id = new String(e.id);
		this.position = plateau[e.position.getAbscisse()][e.position.getOrdonnee()];
		this.myPicture = e.myPicture;
	}
	
	/**
	 * MyPicture setter
	 * @param myPicture
	 */
	public void setMyPicture(ImageIcon myPicture) {
		this.myPicture = myPicture;
	}

	/**
	 * This operation returns the position of the object
	 * @return this.position
	 */
	public Case getPosition() {
		return this.position;
	}
	
	/**
	 * This operation set the actual position to new position in argument
	 * @param newPosition
	 */
	public void setPosition(Case newPosition) {
		this.position = newPosition;
	}
	
	/**
	 * Getter id
	 * @return this.id
	 */
	public String getId() {
		return this.id;
	}
	
	/**
	 * This function is in order to do what is suppose to happen with the dragon
	 * when he fires
	 */
	public void dragonEffet() {
		
	}
	
	/**
	 * Return the color used for the background of board squares
	 * @return a color
	 */
	public Color getColor() {
		return COLOR;
	}

	/**
	 * MyPicture getter
	 * @return myPicture
	 */
	public ImageIcon getMyPicture() {
		return this.myPicture;
	}
	
	/**
	 * ToString function
	 */
	@Override
	public String toString() {
		return "Entity [id=" + id + ", position=" + position + ", myPicture=" + myPicture + "]";
	}

	/**
	 * HashCode function
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((myPicture == null) ? 0 : myPicture.hashCode());
		result = prime * result + ((position == null) ? 0 : position.hashCode());
		return result;
	}

	/**
	 * Equals function
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entite)) {
			return false;
		}
		Entite other = (Entite) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		if (myPicture == null) {
			if (other.myPicture != null) {
				return false;
			}
		} else if (!myPicture.equals(other.myPicture)) {
			return false;
		}
		if (position == null) {
			if (other.position != null) {
				return false;
			}
		} else if (!position.equals(other.position)) {
			return false;
		}
		return true;
	}
	
}