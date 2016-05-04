package modeles.actions;

import controle.Jeu;

/**
 * Classe qui modelise une action
 * 
 * @author Gwenole Lecorve
 */
public abstract class Action {
    
    @Override
    public abstract Object clone();
    
    /**
     * Applique l'action sur une partie en cours
     * @param partie Partie a modifier
     */
    public abstract void appliquer(Jeu etat);
    
    @Override
    public abstract String toString();
    
}
