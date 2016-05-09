package modeles.personnages;

import java.util.ArrayList;
import java.util.List;

import modeles.Case;
import modeles.Entite;
import modeles.obstacles.Obstacle;

/**
 * This class is the base of all characters there can be in the game.
 * The two kind of character there can be in the game are a Troll and a Dragon
 * @author prukev, Brahim
 *
 */
public abstract class Personnage extends Entite {

	/**
	 * This data represents the maximum move the character can have.
	 * It's 2 for a dragon and 1 for a troll
	 */
	protected int deplacementMax;
	
	/**
	 * This data will be used in order to see if a player can move a character or not.
	 * At the beginning of each turn and when the game starts, this data is at 0.
	 */
	protected int deplacementActuel = 0;
	protected Case previousPosition = null;
	
	protected void copy(Personnage p) {
		super.copy(p);
		this.deplacementMax = p.deplacementMax;
		this.deplacementActuel = p.deplacementActuel;
		this.previousPosition = (Case) (p.previousPosition == null?null:p.previousPosition.clone());
	}
	
	protected void copy(Personnage p, Case[][] plateau) {
		super.copy(p, plateau);
		this.deplacementMax = p.deplacementMax;
		this.deplacementActuel = p.deplacementActuel;
		this.previousPosition = (p.previousPosition == null?null:plateau[p.previousPosition.getAbscisse()][p.previousPosition.getOrdonnee()]);
	}
	
	/**
	 * This function is the moving function of a character. It works by removing a character
	 * from his position and adding it to the new position
	 * @param newCase
	 */
	public void deplacer(Case newCase) {
		if (this.deplacementActuel < this.deplacementMax) {
			this.previousPosition = this.position;
			this.position.supprimerEntite(this);
			newCase.ajouterEntite(this);
			this.position = newCase;
			this.deplacementActuel++;
		}
	}
	
	/**
	 * This function reset a move of a character.
	 */
	public void resetDeplacement() {
		this.deplacementActuel = 0;
	}
	
	/**
	 * This function allows the dragon to go back to his previous position when the turn finishes
	 * on an Obstacle.
	 */
	public void revenirEnArriere() {
		this.position.supprimerEntite(this);
		this.previousPosition.ajouterEntite(this);
		this.position = this.previousPosition;
	}
	
	/**
	 * This function allows the character to detect if there is an Obstacle on the new
	 * targeted position. The collision is not the same for the Troll and the Dragon because
	 * the dragon can move two times.
	 * @param c
	 * @return true/false
	 */
	public boolean detecteCollision(Case c) {
		if (this instanceof Dragon) {
			return (this.deplacementActuel == 1 && 
					!c.getEntites().isEmpty() && c.getPremiereEntite() instanceof Obstacle );
		} else {
			return (!c.getEntites().isEmpty() && c.getPremiereEntite() instanceof Obstacle );
		}
		
	}
	
	/**
	 * List of board square which can be reached by the current character
	 * @param plateau
	 * @return
	 */
	public List<Case> listerCasesAtteignables(Case[][] plateau) {
		List<Case> casesAtteignables = new ArrayList<Case>();
		int xInit = getPosition().getAbscisse();
		int yInit = getPosition().getOrdonnee();
		int dep = deplacementMax - deplacementActuel;
		for (int x = Math.max(0, xInit - dep); x <= Math.min(plateau.length-1, xInit + dep); x++) {
			for (int y = Math.max(0, yInit - dep); y <= Math.min(plateau.length-1, yInit + dep); y++) {
				if ((x != xInit || y != yInit)
						&& (Math.abs(x - xInit)+Math.abs(y - yInit)) <= dep) {
					if (!detecteCollision(plateau[x][y])) {
						casesAtteignables.add(plateau[x][y]);
					}
				}
			}
		}
		return casesAtteignables;
	}

	/**
	 * ToString function. Generated by eclipse and customed
	 */
	@Override
	public String toString() {
		return super.toString()+"Character [deplacementMax=" + deplacementMax + ", deplacementActuel=" + deplacementActuel
				+ ", previousPosition=" + previousPosition + "]";
	}

	/**
	 * HashCode function. Generated by eclipse
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + deplacementActuel;
		result = prime * result + deplacementMax;
		result = prime * result + ((previousPosition == null) ? 0 : previousPosition.hashCode());
		return result;
	}

	/**
	 * Equals function. Generated by eclipse
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Personnage other = (Personnage) obj;
		if (deplacementActuel != other.deplacementActuel)
			return false;
		if (deplacementMax != other.deplacementMax)
			return false;
		if (previousPosition == null) {
			if (other.previousPosition != null)
				return false;
		} else if (!previousPosition.equals(other.previousPosition))
			return false;
		return true;
	}

	/**
	 * Getter of max move
	 * @return deplacementMax
	 */
	public int getDeplacementMax() {
		return deplacementMax;
	}

	/**
	 * Setter of max movement. Use when the dice option is active
	 * @param deplacementMax
	 */
	public void setDeplacementMax(int deplacementMax) {
		this.deplacementMax = deplacementMax;
	}

	/**
	 * Getter of actual move
	 * @return deplacementActuel
	 */
	public int getDeplacementActuel() {
		return deplacementActuel;
	}

	/**
	 * Setter of actual move.
	 * @param deplacementActuel
	 */
	public void setDeplacementActuel(int deplacementActuel) {
		this.deplacementActuel = deplacementActuel;
	}
	
}
