package modeles.personnages;

import modeles.Case;

/**
 * This is the sleeping state of the dragon
 * @author brahim, prukev
 */
public class Endormi implements EtatDragon {
	
	/**
	 * Nothing in the constructor
	 */
	public Endormi() {
	}
	
	@Override
	public Object clone() {
		return new Endormi();
	}
	
	/**
	 * 
	 * No effect if the dragon is sleeping
	 */
	public void deplacer(Case newCase, Dragon dragon){
		
	}
	
	@Override
	public String toString() {
		return "Endormi";
	}

}
