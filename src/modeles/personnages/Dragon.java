package modeles.personnages;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import controle.Jeu;

import modeles.Case;
import modeles.obstacles.Rocher;


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
	private static final int DEPLACEMENT_MAX_DRAGON = 3;
	
	/**
	 * Number of rounds during which the dragon remains awaken
	 */
	private static final int DUREE_EVEIL = 15;
	
	/**
	 * Number of rounds during which the dragon remains awaken
	 */
	private static final int PORTEE_FEU = 4;
	
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
	private ImageIcon awake;
	
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
		this.awake = awake;
		this.deplacementMax = DEPLACEMENT_MAX_DRAGON;
		this.id = "Dragon_" + this.nbDragon;
		this.nbDragon++;
		//this.awake = false;
		this.etat = new Endormi();
	}
	
	@Override
	public Object clone() {
		Dragon d = new Dragon(this.sleeping, this.awake);
		d.copy(this);
		d.etat = (EtatDragon) this.etat.clone();
		return d;
	}
	
	@Override
	public Object clone(Case[][] plateau) {
		Dragon d = new Dragon(this.sleeping, this.awake);
		d.copy(this, plateau);
		d.etat = (EtatDragon) this.etat.clone();
		return d;
	}
	
	/**
	 * Check if the current dragon is sleeping
	 * @return True if the dragon is sleeping
	 */
	public boolean estEndormi() {
		return (etat instanceof Endormi);
	}
	
	/**
	 * Check if the current dragon is awake
	 * @return True if the dragon is awake
	 */
	public boolean estEveille() {
		return (etat instanceof Eveille);
	}
	
	/**
	 * Test if the fire of the current dragon can reach a give board square
	 * @param cible The target board square
	 * @param jeu The game to be inspected
	 * @return True if the dragon can fire onto the position
	 */
	public boolean peutAtteindre(Case cible, Jeu jeu) {
		// Same X coordinate
		if (position.getAbscisse() == cible.getAbscisse()) {
			int distance = Math.abs(position.getOrdonnee() - cible.getOrdonnee());
			boolean atteignable = (distance > 0 && distance <= Dragon.PORTEE_FEU);
			// Look at the bottom
			if (position.getOrdonnee() < cible.getOrdonnee()) {
				for (int i = position.getOrdonnee() + 1; atteignable
						&& i <= cible.getOrdonnee(); i++) {
					Case c = jeu.getCase(position.getAbscisse(), i);
					if (!c.getEntites().isEmpty()
							&& (c.getPremiereEntite() instanceof Rocher)) {
						atteignable = false;
					}
				}
			}
			// Look on top
			else {
				for (int i = position.getOrdonnee() - 1; atteignable
						&& i >= cible.getOrdonnee() ; i--) {
					Case c = jeu.getCase(position.getAbscisse(), i);
					if (!c.getEntites().isEmpty()
							&& (c.getPremiereEntite() instanceof Rocher)) {
						atteignable = false;
					}
				}
			}
			return atteignable;
		}
		// Same Y coordinate
		else if (position.getOrdonnee() == cible.getOrdonnee()) {
			int distance = Math.abs(position.getAbscisse()
					- cible.getAbscisse());
			boolean atteignable = (distance > 0 && distance <= Dragon.PORTEE_FEU);
			// Look on the right
			if (position.getOrdonnee() < cible.getOrdonnee()) {
				for (int i = position.getAbscisse() + 1; atteignable
						&& i <= cible.getAbscisse(); i++) {
					Case c = jeu.getCase(i, position.getOrdonnee());
					if (!c.getEntites().isEmpty()
							&& (c.getPremiereEntite() instanceof Rocher)) {
						atteignable = false;
					}
				}
			}
			// Look on the left
			else {
				for (int i = position.getAbscisse() - 1; atteignable
						&& i >= cible.getAbscisse(); i--) {
					Case c = jeu.getCase(i, position.getOrdonnee());
					if (!c.getEntites().isEmpty()
							&& (c.getPremiereEntite() instanceof Rocher)) {
						atteignable = false;
					}
				}
			}
			return atteignable;
		}
		// Otherwise
		else {
			return false;
		}
	}
	
	/**
	 * This function returns the fire zone when the dragon must fire at the end of the turn.
	 * @param taillePlateau
	 * @param jeu
	 * @return zoneFeu
	 */
	public List<Case[]> getZoneFeu(int taillePlateau, Jeu jeu) {
		//Creation of the list which will contains some tables of Case
		ArrayList<Case[]> zoneFeu = new ArrayList<Case[]>();	
		
		//The dragon fires in a cross. There will be 4 zones around him.
		Case[] feuDroite = new Case[Dragon.PORTEE_FEU];	//Right zone
		Case[] feuHaut = new Case[Dragon.PORTEE_FEU];	//Left zone
		Case[] feuGauche = new Case[Dragon.PORTEE_FEU];	//Up zone
		Case[] feuBas = new Case[Dragon.PORTEE_FEU];	//Down zone
		
		/*	For each way, we add the case in the corresponding table following this schema :
		 * 										x
		 * 										x
		 * 										x
		 * 								  x x x o x x x
		 * 										x
		 * 										x
		 * 										x
		 */
		for (int i = 1; i <= Dragon.PORTEE_FEU; ++i) {
			if ((this.getPosition().getOrdonnee() + i) < taillePlateau) {
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
														
														
			if ((this.getPosition().getAbscisse() + i) < taillePlateau) {
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
		return "Dragon [id="+id+", etat=" + etat +", prevPros=" + previousPosition + "]";
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
		if (!this.id.equals(other.id)) {
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
		if (estEveille()) {
			super.deplacer(newCase);
		}
	}
	
	/**
	 * Function used in order to wake up the dragon
	 */
	public void reveiller() {
		this.etat = new Eveille(DUREE_EVEIL);
		this.myPicture = this.awake;
	}
	
	/**
	 * Function used when the dragon comes back to sleep
	 */
	public void endormir() {
		this.etat = new Endormi();
		this.myPicture = this.sleeping;
	}

}
