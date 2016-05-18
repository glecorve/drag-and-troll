package modeles.personnages;

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
	
	@Override
	public String toString() {
		return "Endormi";
	}

}
