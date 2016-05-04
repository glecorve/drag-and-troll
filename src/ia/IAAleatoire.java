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
//        System.out.println(Thread.currentThread().getName()+": "+"-------- choisirAction ---------");
        List<Action> actions = j.listerActionsPossibles();
        for (Action a : actions) {
//            System.out.println(Thread.currentThread().getName()+": "+"Action possible : " + a.toString());
        }
        Action resultat = actions.get((int) (Math.random() * (actions.size() - 1)));
//        System.out.println(Thread.currentThread().getName()+": "+"Action calculee = " + resultat.toString());
        return resultat;
    }

}
