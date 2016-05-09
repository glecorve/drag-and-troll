package ia;

import java.util.List;

import modeles.Entite;
import modeles.actions.Action;
import modeles.actions.DeplacerTroll;
import modeles.collectables.*;

import controle.Jeu;

/**
 * Joueur artificiel qui choisit chaque coup au hasard
 * @author Gwenole Lecorve
 */
public class IAAleatoireVenale extends AbstractIA {

    /**
     * Constructeur par defaut
     */
    public IAAleatoireVenale() {
        super("IA aleatoire venale");
    }
    
    /**
     * Constructeur
     * @param nom Nom du joueur artificiel
     */
    public IAAleatoireVenale(String nom) {
        super(nom);
    }
    
    private boolean contientBourse(DeplacerTroll action) {
    	List<Entite> entites = action.getDestination().getEntites();
    	for (Entite e : entites) {
    		if (e instanceof Bourse) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private boolean contientPiece(DeplacerTroll action) {
    	List<Entite> entites = action.getDestination().getEntites();
    	for (Entite e : entites) {
    		if (e instanceof Piece) {
    			return true;
    		}
    	}
    	return false;
    }
    
    @Override
    public Action choisirAction(Jeu j) {
        List<Action> actions = j.listerActionsPossibles();
        for (Action a : actions) {
        	if (a instanceof DeplacerTroll && contientBourse((DeplacerTroll) a)) {
        		return a;
        	}
        }
        for (Action a : actions) {
        	if (a instanceof DeplacerTroll && contientPiece((DeplacerTroll) a)) {
        		return a;
        	}
        }
        Action resultat = actions.get((int) (Math.random() * (actions.size() - 0.01)));
        return resultat;
    }

}
