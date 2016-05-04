
package modeles.obstacles;

import modeles.Entite;

/**
 * This class represents the base of all obstacles which will follow this base 
 * @author prukev, Brahim
 *
 */
public abstract class Obstacle extends Entite {

	/**
	 * Every obstacle has a difficulty which will be important depending the game environment
	 */
	protected int difficulty;
	
	protected void copy(Obstacle o) {
		super.copy(o);
		this.difficulty = o.difficulty;
	}
	
	/**
	 * ToString function
	 */
	@Override
	public String toString() {
		return "Obstacle [difficulty=" + difficulty + "]";
	}
	
	
	
}
