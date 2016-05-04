package modeles.personnages;

import java.util.ArrayList;

import javax.swing.ImageIcon;

import controle.Jeu;

import modeles.Case;


/**
 * This class represents the object Dragon.
 * @author prukev
 * @author Brahim
 *
 */
public class Dragon extends Personnage {

	/**
	 * Maximum number of moves for any dragon
	 */
	private static final int DEPLACEMENT_MAX_DRAGON = 2;
	
	/**
	 * This data can give us the number of Dragon there is in the game. It's also use for the id.
	 */
	private static int nbDragon = 1;
	
	/**
	 * This image will be use if the dragon is sleeping
	 */
	private ImageIcon sleeping;
	
	/**
	 * This image will be use if the dragon is awake
	 */
	private ImageIcon awaken;
	
	/**
	 * The state of a dragon is a boolean. True if it's awake, false if it sleeps
	 */
	private EtatDragon etat;
	
	
	/**
	 * The dragon's constructor. It has an image associate with it and a max move.
	 * @param img
	 */
	@SuppressWarnings("static-access")
	public Dragon(ImageIcon img, ImageIcon awake) {
		
		this.myPicture = img;
		this.sleeping = img;
		this.awaken = awake;
		this.deplacementMax = DEPLACEMENT_MAX_DRAGON;
		this.id = "Dragon_" + this.nbDragon;
		this.nbDragon++;
		//this.awake = false;
		this.etat = new Endormi();
	}
	
	@Override
	public Object clone() {
		Dragon d = new Dragon(this.sleeping, this.awaken);
		d.copy(this);
		d.etat = (EtatDragon) this.etat.clone();
		return d;
	}
	
	/**
	 * This function returns the fire zone when the dragon must fire at the end of the turn.
	 * @param limite
	 * @param jeu
	 * @return zoneFeu
	 */
	public ArrayList<Case[]> getZoneFeu(int limite, Jeu jeu) {
		//Creation of the list which will contains some tables of Case
		ArrayList<Case[]> zoneFeu = new ArrayList<Case[]>();	
		
		//The dragon fires in a cross. There will be 4 zones around him.
		Case[] feuDroite = new Case[3];	//Right zone
		Case[] feuHaut = new Case[3];	//Left zone
		Case[] feuGauche = new Case[3];	//Up zone
		Case[] feuBas = new Case[3];	//Down zone
		
		/*	For each way, we add the case in the corresponding table following this schema :
		 * 										x
		 * 										x
		 * 										x
		 * 								  x x x o x x x
		 * 										x
		 * 										x
		 * 										x
		 */
		for (int i = 1; i < 4; ++i) {
			if ((this.getPosition().getOrdonnee() + i) < limite) {
				feuDroite[i-1] = jeu.getPlateau()[this.getPosition().getAbscisse()]
						   [this.getPosition().getOrdonnee()+i];
			}
																									
			if ((this.getPosition().getOrdonnee() - i) >= 0) {
				feuGauche[i-1] = jeu.getPlateau()[this.getPosition().getAbscisse()]
															   [this.getPosition().getOrdonnee()-i];
			}
														
														
			if ((this.getPosition().getAbscisse() - i) >= 0) {
				feuHaut[i-1] = jeu.getPlateau()[this.getPosition().getAbscisse()-i]
															 [this.getPosition().getOrdonnee()];
			}
														
														
			if ((this.getPosition().getAbscisse() + i) < limite) {
				feuBas[i-1] = jeu.getPlateau()[this.getPosition().getAbscisse()+i]
															[this.getPosition().getOrdonnee()];
			}
																												
		}
		
		//We add each table in the list
		zoneFeu.add(feuDroite);
		zoneFeu.add(feuGauche);
		zoneFeu.add(feuBas);
		zoneFeu.add(feuHaut);
		
		return zoneFeu;
	}

	/**
	 * ToString function. Customized during testing and not improved yet
	 */
    @Override
	public String toString() {
		return "Dragon [sleeping=" + sleeping + ", awaken=" + awaken + ", etat=" + etat + "]";
	}

	
	/**
	 * Equals function. Generated by eclipse
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Dragon)) {
			return false;
		}
		Dragon other = (Dragon) obj;
		if (this.id != other.id) {
			return false;
		}
		return true;
	}

	/**
     *This function returns the state of the dragon
     * @return  etat
     */
	public EtatDragon getEtat() {
		return etat;
	}
    /**
     * This function will set the state of the dragon
     * @param etat
     */
	public void setEtat(EtatDragon etat) {
		this.etat = etat;
	}
	
	/**
	 * Move action redefined because the dragon can only move if it's in an awaken state.
	 */
	public void deplacer(Case newCase) {
		this.etat.deplacer(newCase, this);
	}
	
	/**
	 * Function used in order to wake up the dragon
	 */
	public void wakeUp() {
		this.etat = new Eveille();
		this.myPicture = this.awaken;
	}
	
	/**
	 * Function used when the dragon comes back to sleep
	 */
	public void sleep() {
		this.etat = new Endormi();
		this.myPicture = this.sleeping;
	}

}