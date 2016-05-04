package ia;

import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

import modeles.actions.Action;

import controle.Jeu;

/**
 * Classe qui execute la recherche d'un coup par une intelligence artificielle
 *
 * @author Gwenole Lecorve
 */
public class IAThread extends Thread {

    /**
     * Joueur artificiel
     */
    private final AbstractIA ia;

    /**
     * Partie en cours
     */
    private final Jeu jeu;
    
    /**
     * Service d'execution du thread
     */
    private final ExecutorService executor;

    /**
     * Coup choisi a l'issu de la recherche
     */
    private Action actionChoisie;

    /**
     * Constructeur
     * @param ia Joueur artificiel
     * @param jeu Partie en cours
     * @param executor Service d'execution du thread
     */
    public IAThread(AbstractIA ia, Jeu jeu, ExecutorService executor) {
        this.ia = ia;
        this.jeu = jeu;
        this.executor = executor;
        this.actionChoisie = null;
    }
    
    public Action getCoupChoisi() {
        return actionChoisie;
    }

    /**
     * Lance la recherche d'un nouveau coup dans un thread separe
     */
    @Override
    public void run() {
        try {
        	actionChoisie = ia.choisirAction((Jeu) jeu.clone());
//            System.out.println(Thread.currentThread().getName()+": "+"Coup memorise");
        }
        catch (Exception ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println(Thread.currentThread().getName()+": "+"Interrompu");
        }
        finally {
            executor.shutdown();
//            System.out.println(Thread.currentThread().getName()+": "+"Fin de l'executor");
        }
    }

}
