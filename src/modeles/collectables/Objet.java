package modeles.collectables;

import modeles.Case;
import modeles.Entite;
import modeles.personnages.Troll;

/**
 * This class is the base of all kind of item there will be in the game
 * @author prukev, Brahim
 *
 */
public abstract class Objet extends Entite {
	
	/**
	 * Each item will have a bonus.
	 */
    protected int bonus;
    
    protected void copy(Objet o) {
    	super.copy(o);
    	bonus = o.bonus;
    }
    
    protected void copy(Objet o, Case[][] plateau) {
    	super.copy(o, plateau);
    	bonus = o.bonus;
    }
    
    /**
     * This function applies the item's bonus. Depending the object, the bonus will not
     * act on the same data of the troll.
     * @param troll
     */
    public void effet(Troll troll){
    	if (this instanceof Bourse || this instanceof Piece) {
    		troll.augmenterScore(this.bonus);
    	}
    	if (this instanceof Coeur) {
    		troll.augmenterVies(this.bonus);
    	}
    	if (this instanceof Cristal) {
    		troll.augmenterMagie(this.bonus);
    	}
    	if (this instanceof Bouclier) {
    		troll.ajouterBouclier(this.bonus);
    	}
    }
    
    /**
     * Bonus getter
     * @return bonus
     */
	public int getBonus() {
		return this.bonus;
	}
	
	/**
	 * Bonus setter
	 * @param bonus
	 */
	public void setBonus(int bonus) {
		this.bonus = bonus;
	}
	
	/**
	 * ToString function
	 */
	@Override
	public String toString() {
		return super.toString()+ "Objet [bonus=" + bonus + "]";
	}
	
}
