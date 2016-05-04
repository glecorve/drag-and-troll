/**
 * 
 */
package modeles.personnages;

import modeles.Case;

/**
 * This is the awaken state of the dragon
 * @author brahim, prukev
 */
public class Eveille implements EtatDragon {
	
	/**
	 * This number will take the actual turn of game when the dragon wakes up
	 */
	private int nbTurn = 0;
	
	/**
	 * No modifications in the constructor
	 */
	public Eveille() {
	}
	
	@Override
	public Object clone() {
		return new Eveille();
	}
	
	
	/**
	 * This function will be called only if the dragon is awake
	 */
	public void deplacer(Case newCase, Dragon dragon){
			if (dragon.deplacementActuel < dragon.deplacementMax) {
				dragon.previousPosition = dragon.getPosition();
				dragon.getPosition().deleteEntite(dragon);
				newCase.addEntite(dragon);
				dragon.setPosition(newCase);
				dragon.deplacementActuel++;
			}
	}

	/**
	 * Nbturn getter
	 * @return nbTurn
	 */
	public int getNbTurn() {
		return nbTurn;
	}

	/**
	 * NbTurn setter
	 * @param nbTurn
	 */
	public void setNbTurn(int nbTurn) {
		this.nbTurn = nbTurn;
	}
	
	

}
