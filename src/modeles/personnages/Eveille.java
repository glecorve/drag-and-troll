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
	private int nombreToursRestants = 0;
	
	/**
	 * No modifications in the constructor
	 */
	public Eveille() {
	}

	/**
	 * Constructor
	 * @param toursInitiaux Initial number of rounds
	 */
	public Eveille(int toursInitiaux) {
		nombreToursRestants = toursInitiaux;
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
				dragon.getPosition().supprimerEntite(dragon);
				newCase.ajouterEntite(dragon);
				dragon.setPosition(newCase);
				dragon.deplacementActuel++;
			}
	}

	/**
	 * nombreToursRestants getter
	 * @return nombreToursRestants
	 */
	public int getNombreToursRestants() {
		return nombreToursRestants;
	}

	/**
	 * nombreToursRestants setter
	 * @param nombreToursRestants
	 */
	public void setNombreToursRestants(int nbTurn) {
		this.nombreToursRestants = nbTurn;
	}
	
	/**
	 * Decrement the number of remaining rounds
	 */
	public void diminuerToursRestants() {
		if (nombreToursRestants > 0) {
			nombreToursRestants--;
		}
	}
	
	@Override
	public String toString() {
		return "Eveille pour " + nombreToursRestants + " tours";
	}

}
