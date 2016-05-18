package ia;

import controle.Jeu;
import modeles.Joueur;
import modeles.actions.Action;

/**
 *
 * @author Gwenole Lecorve
 */
public abstract class AbstractIA extends Joueur {

    /**
     * Delai de reflexion (c.-a-d. de calcul) autorise pour tous les joueurs artificiels
     */
    public static int DELAI_DE_REFLEXION = 2000;

    private Action actionMemorisee;

    /**
     * Constructeur
     * @param nom Nom du joueur artificiel
     */
    public AbstractIA(String nom) {
        super(nom);
        actionMemorisee = null;
    }

    /**
     * Renvoie le dernier coup memorise
     *
     * @return un coup
     */
    public final Action getActionMemorisee() {
        return actionMemorisee;
    }

    /**
     * Memorise un coup
     *
     * @param coup Le coup a memoriser
     */
    public final void memoriserAction(Action action) {
        if (action != null) {
            actionMemorisee = (Action) action.clone();
//            System.out.println("##############################");
//            System.out.println("Mémorisation de " + actionMemorisee.toString());
//            System.out.println("##############################");
        }
        else {
            actionMemorisee = action;
        }
    }

    /**
     * Recherche le coup a jouer
     *
     * @param p Partie en cours
     * @return une action
     */
    public abstract Action choisirAction(Jeu j);

}
