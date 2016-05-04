package modeles;

import java.util.ArrayList;

import modeles.collectables.*;
import modeles.personnages.*;
import modeles.obstacles.*;



/**
 * This class represent a kind of position for the entity. It's on this the players
 * can play on the plateau game.
 * @author prukev, Brahim
 *
 */
public class Case {

	/**
	 * This data represents the line in the table
	 */
	private int abscisse;
	
	/**
	 * This data represent the column in the table
	 */
	private int ordonnee;
	
	/**
	 * For each case, there is a possibility to have one or more entities
	 */
	private ArrayList<Entite> entites;
	
	private boolean beingCloned = false;
	
	/**
	 * This is the case constructor. It takes a position as an argument
	 * @param x
	 * @param y
	 */
	public Case(int x, int y) {
		this.abscisse = x;
		this.ordonnee = y;
		this.entites = new ArrayList<Entite>();
	}
	
	public synchronized Object clone() {
		if (!beingCloned) {
			Case c = new Case(this.abscisse, this.ordonnee);
			this.beingCloned = true;
			for (Entite e : this.entites) {
				c.addEntite((Entite) e.clone());
			}
			this.beingCloned = false;
			return c;
		}
		else {
			return this;
		}
	}
	
	/**
	 * This is the getter of the list
	 * @return this.entity
	 */
	public ArrayList<Entite> getEntites() {
		return this.entites;
	}

	/**
	 * This function returns the first entity of the list
	 * @return this.entity.get(0)
	 */
	public Entite getFirstEntite() {
		return this.entites.get(0);
	}
	
	/**
	 * This function add the entity in argument to the list
	 * @param e
	 */
	public void addEntite(Entite e) {
		this.entites.add(e);
	}
	
	/**
	 * This function remove the entity in argument from the list
	 * @param e
	 */
	public void deleteEntite(Entite e) {
		Entite et=null;
		if (getFirstEntite() == e && !getFirstEntite().equals(e) && !(getFirstEntite() instanceof Dragon)) {
				Entite tempEntite = this.entites.get(0);
				this.entites.add(tempEntite);
				this.entites.remove(0);
				this.entites.remove(e);
		} else {
			for(Entite entite : this.entites){
				if(entite.equals(e)){
					 et=entite;
				}
			}
				this.entites.remove(et);
		}
		
	}
	
	/**
	 * Remove all the items on the current Case
	 */
	public void deleteAllEntities() {
		this.entites.clear();
	}
	
	/**
	 * Getter of the line
	 * @return this.abscisse
	 */
	public int getAbscisse() {
		return this.abscisse;
	}
	
	/**
	 * Getter of the column
	 * @return this.ordonnee
	 */
	public int getOrdonnee() {
		return this.ordonnee;
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
		if (!(obj instanceof Case)) {
			return false;
		}
		Case other = (Case) obj;
		if (abscisse != other.abscisse) {
			return false;
		}
		if (entites == null) {
			if (other.entites != null) {
				return false;
			}
		} else if (!entites.equals(other.entites)) {
			return false;
		}
		if (ordonnee != other.ordonnee) {
			return false;
		}
		return true;
	}

	/**
	 * ToString function
	 */
	@Override
	public String toString() {
		String str = "Case [x=" + abscisse + ", y=" + ordonnee + ", ";
		for (Entite e : entites) {
			if (e instanceof Bouclier) { str += "b"; }
			else if (e instanceof Bourse) { str += "3"; }
			else if (e instanceof Coeur) { str += "v"; }
			else if (e instanceof Cristal) { str += "m"; }
			else if (e instanceof Piece) { str += "1"; }
			else if (e instanceof Arbre) { str += "A"; }
			else if (e instanceof Eau) { str += "E"; }
			else if (e instanceof Rocher) { str += "R"; }
			else if (e instanceof Dragon) { str += "D"; }
			else if (e instanceof Troll) { str += "T"; }
			else { str += "?"; }
		}
		str += "]";
		return str;
	}
	
}
