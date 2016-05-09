package ia;

import java.util.List;

import modeles.actions.Action;

import controle.Jeu;

/**
 * Joueur artificiel qui choisit chaque coup au hasard
 * @author Gwenole Lecorve
 */
public class IAAleatoire extends AbstractIA {

    /**
     * Constructeur par defaut
     */
    public IAAleatoire() {
        super("IA aleatoire");
    }
    
    /**
     * Constructeur
     * @param nom Nom du joueur artificiel
     */
    public IAAleatoire(String nom) {
        super(nom);
    }
    
    @Override
    public Action choisirAction(Jeu j) {
        List<Action> actions = j.listerActionsPossibles();
        Action resultat = actions.get((int) (Math.random() * (actions.size() - 0.01)));
        return resultat;
    }

}
