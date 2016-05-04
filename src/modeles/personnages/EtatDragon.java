package modeles.personnages;

import modeles.Case;

/**
 * This Interface will be implemented by DragonAwake and DragonSleept 
 * @author prukev, Brahim
 */
public interface EtatDragon {
	
	/**
	 * Clone the current instance
	 */
	public Object clone();
	
	/**
	 * Interface actual for the two states
	 * @param newCase
	 * @param dragon
	 */
	public void deplacer(Case newCase, Dragon dragon);

}
