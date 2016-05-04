package modeles.actions;

import modeles.Case;
import modeles.personnages.Personnage;
import controle.Jeu;

/**
 * Classe qui modelise un deplacement d'un troll
 * @author Gwenole Lecorve
 */
public abstract class DeplacerPersonnage extends Action {
	private final Personnage personnage;
    private final Case destination;
    
    public DeplacerPersonnage(Personnage p, Case cible) {
    	super();
    	this.personnage = p;
        this.destination = cible;
    }

	public Personnage getPersonnage() {
		return personnage;
	}
    
    public Case getDestination() {
        return destination;
    }

    @Override
    public void appliquer(Jeu etat) {
        etat.setPersonnageSelectionne(personnage);
        etat.deplacer(destination);
    }

}
