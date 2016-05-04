package controle;

import java.awt.Point;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.ImageIcon;

import vues.JeuIHM;

import ia.AbstractIA;
import ia.IAAleatoire;
import ia.IAThread;

import modeles.Case;
import modeles.Entite;
import modeles.Joueur;
import modeles.actions.*;
import modeles.collectables.*;
import modeles.obstacles.*;
import modeles.personnages.*;

import images.Images;

/**
 * This is the controller of the game.
 * 
 * @author brahim, prukev
 * 
 */
public class Jeu {

	/**
	 * Number of rounds during which the dragons remains awaken
	 */
	private static final int DUREE_DRAGON = 10;

	/**
	 * Game icons images
	 */
	Images images = new Images();
	
	/**
	 * List of all players
	 */
	List<Joueur> joueurs;

	/**
	 * This list contains all the entities there will be in the game
	 */
	private Set<Entite> listeEntite = new HashSet<Entite>();

	/**
	 * This list is used for the dragon fire function
	 */
	private Set<Entite> tabRemoveEntite = new HashSet<Entite>();

	/**
	 * This list contains all the dragons
	 */
	private Set<Dragon> dragonList = new HashSet<Dragon>();

	/**
	 * This list contains all the trolls
	 */
	private List<Troll> trollList = new ArrayList<Troll>();

	/**
	 * This table represents the board/map of the game
	 */
	private Case[][] plateau;

	/**
	 * This case is important because it will represent the start case of
	 * the trolls
	 */
	private Case maison;

	/**
	 * This contains the score max possible
	 */
	public int scoreMax = 0;

	/**
	 * In order to play, the player must select a character. The game's actions
	 * are used in this variable which is the selected character
	 */
	private Personnage personnageSelectionne = null;

	/**
	 * This number represents the size of the board
	 */
	private static int taillePlateau = 0;

	/**
	 * This number represents the actual of players.
	 */
	private int indiceJoueurCourant = 0;

	/**
	 * This number represents the number of players.
	 */
	private static int nombreJoueurs = 2;

	/**
	 * This list contains all the losing players
	 */
	private List<Troll> deletedPlayers;

	/**
	 * This number represents the number of dragons
	 */
	private static int nombreDragons = 1;

	/**
	 * This boolean stops the game if it's true
	 */
	protected boolean termine = false;

	/**
	 * This variable displays the actual turn
	 */
	private int numeroTour = 1;

	/**
	 * Flag for the backPackMode
	 */
	private static boolean backPackMode = false;

	/**
	 * This boolean is useful when a player wants to play the game with a
	 * dice.
	 */
	private static boolean diceMode = false;

	/**
	 * This table will contain all the icons of the trolls
	 */
	private ImageIcon[] trollIcon = { images.getTroll1Icon(),
			images.getTroll2Icon(), images.getTroll3Icon(),
			images.getTroll4Icon(), images.getTroll1ShieldIcon(),
			images.getTroll2ShieldIcon(), images.getTroll3ShieldIcon(),
			images.getTroll4ShieldIcon() };

	/**
	 * This is the constructor.
	 * 
	 * @param nbCases Size of the board
	 */
	public Jeu(int nbCases) {
		Jeu.taillePlateau = nbCases;// We register the number max of the table
		
		// The table will be a table of table size of the argument
		this.plateau = new Case[nbCases][nbCases];
		for (int i = 0; i < nbCases; ++i) {
			for (int j = 0; j < nbCases; ++j) {
				// We create each square of the board
				this.plateau[i][j] = new Case(i, j);
			}
		}
		
		this.joueurs = new ArrayList<Joueur>(nombreJoueurs);
		for (int i = 0; i < nombreJoueurs; i++) {
			this.joueurs.add(new Joueur("Joueur "+i));
		}

		if (nombreJoueurs > 3 || Jeu.taillePlateau > 9) {
			for (int i = 0; i < 8; i++) {
				ajouterObjetAleatoire();
			}
		}
		
		this.deletedPlayers = new ArrayList<Troll>(4);
	}

	/**
	 * Copy constructor
	 * @param aCopier Instance to be copied 
	 */
	public Jeu(Jeu aCopier) {
		
		this.images = aCopier.images;
		listeEntite = new HashSet<Entite>();
		for (Entite e : aCopier.listeEntite) {
			listeEntite.add((Entite) e.clone());
		}
		
		// The table will be a table of table size of the argument
		this.plateau = new Case[taillePlateau][taillePlateau];
		this.tabRemoveEntite = new HashSet<Entite>();
		this.dragonList = new HashSet<Dragon>();
		this.trollList = new ArrayList<Troll>();
		this.deletedPlayers = new ArrayList<Troll>(aCopier.deletedPlayers);
		for (int i = 0; i < taillePlateau; ++i) {
			for (int j = 0; j < taillePlateau; ++j) {
				// We create each Case in the table
				this.plateau[i][j] = (Case) aCopier.plateau[i][j].clone();
				for (Entite e : this.plateau[i][j].getEntites()) {
					if (aCopier.tabRemoveEntite.contains(e)) { tabRemoveEntite.add(e); }
					if (e instanceof Dragon && aCopier.dragonList.contains(e)) { dragonList.add((Dragon) e); }
					if (e instanceof Troll && aCopier.trollList.contains(e)) { trollList.add((Troll) e); }
					if (e instanceof Troll && aCopier.deletedPlayers.contains(e)) { deletedPlayers.add((Troll) e); }
				}
			}
		}
		
		maison = plateau[aCopier.maison.getAbscisse()][aCopier.maison.getOrdonnee()];
		scoreMax = aCopier.scoreMax;
		
		// Find the current character
		boolean trouve = false;
		Iterator<Dragon> itd = dragonList.iterator();
		while (itd.hasNext() && !trouve) {
			Personnage p = (Personnage) itd.next();
			if (p.equals(aCopier.personnageSelectionne)) {
				personnageSelectionne = p;
				trouve = true;
			}
		}
		Iterator<Troll> itt = trollList.iterator();
		while (itt.hasNext() && !trouve) {
			Personnage p = (Personnage) itt.next();
			if (p.equals(aCopier.personnageSelectionne)) {
				personnageSelectionne = p;
				trouve = true;
			}
		}
		
		this.joueurs = new ArrayList<Joueur>(nombreJoueurs);
		for (int i = 0; i < nombreJoueurs; i++) {
			this.joueurs.add(aCopier.joueurs.get(i).clone());
			// Find the troll of player i
			for (Troll t : trollList) {
				if (t.equals(aCopier.joueurs.get(i).getTroll())) {
					this.joueurs.get(i).setTroll(t);
					break;
				}
			}
		}
		
		indiceJoueurCourant = aCopier.indiceJoueurCourant;
		
		termine = aCopier.termine;
		numeroTour = aCopier.numeroTour;
	}
	
	
	public Object clone() {
		return new Jeu(this);
	}
	
	/**
	 * Get the current character
	 * 
	 * @return personnageActuel
	 */
	public Personnage getPersonnageSelectionne() {
		return this.personnageSelectionne;
	}

	/**
	 * Set the current character. Used for the selection.
	 * 
	 * @param p
	 */
	public void setPersonnageSelectionne(Personnage p) {
		this.personnageSelectionne = p;
	}

	/**
	 * Game table getter
	 * 
	 * @return plateau
	 */
	public Case[][] getPlateau() {
		return this.plateau;
	}

	/**
	 * This operation add an entity to the game
	 * 
	 * @param e
	 * @param x
	 * @param y
	 */
	public void ajouterEntite(Entite e, int x, int y) {
		this.plateau[x][y].addEntite(e);
		this.listeEntite.add(e);
		e.setPosition(this.plateau[x][y]);
		if (e instanceof Troll) {
			((Troll) e).setDepart(this.plateau[x][y]);
			this.maison = this.plateau[x][y];
			this.trollList.add((Troll) e);
		}
		if (e instanceof Dragon)
			this.dragonList.add((Dragon) e);
		if (e instanceof Piece || e instanceof Bourse)
			this.scoreMax += ((Objet) e).getBonus();
	}
	
	/**
	 * Change the i-th player
	 * @param index Position of the player
	 * @param joueur Player
	 */
	public void setJoueur(int index, Joueur joueur) {
		if (index >= 0 && index < joueurs.size()) {
			joueurs.set(index, joueur);
		}
	}

	/**
	 * listeEntity getter
	 * 
	 * @return listeEntity
	 */
	public Set<Entite> getListeEntite() {
		return listeEntite;
	}

	/**
	 * Setter of the listEntity
	 * 
	 * @param listeEntite
	 */
	public void setListeEntite(HashSet<Entite> listeEntite) {
		this.listeEntite = listeEntite;
	}

	/**
	 * This function represents the fire function of the dragon executed at each
	 * end turn
	 * 
	 * @param limite
	 */
	public void cracherFeu(int limite) {
		// We execute the operation only if the dragon was the selected
		// character
		if (this.personnageSelectionne instanceof Dragon) {

			Dragon drag = (Dragon) this.personnageSelectionne;
			if (drag.getEtat() instanceof Eveille) {
				// We get all the Cases around the dragon
				ArrayList<Case[]> zone = ((Dragon) this.personnageSelectionne)
						.getZoneFeu(limite, this);

				// We execute successifs checking on each case
				for (Case[] lacase : zone) {

					for (int i = 0; i < 3; ++i) {

						/*
						 * We look if there is a rock in the order of
						 * verification. If there is, we stop the analysis.
						 */
						if (lacase[i] != null
								&& !lacase[i].getEntites().isEmpty()
								&& (lacase[i].getFirstEntite() instanceof Rocher)) {
							break;// F
						}

						// If there is an entity and we can apply the dragon's
						// effect, we do it
						if (lacase[i] != null && lacase[i].getEntites() != null) {

							for (Entite entite : lacase[i].getEntites()) {
								// But we first save it in an intermediary list
								tabRemoveEntite.add(entite);
							}

						}
					}

				}

				// This part represents the application of the dragon's fire on
				// the dragon's position
				for (Entite entite : this.personnageSelectionne.getPosition()
						.getEntites()) {
					tabRemoveEntite.add(entite);
				}

				// Since we can't delete something from a list on reading mode,
				// we delete it from an other list
				for (Entite entite : tabRemoveEntite) {

					// Her we search if tabRemoveEntity contains a troll
					if (entite instanceof Troll) {
						Troll t = (Troll) entite;

						// Her we make sure that this troll don't has shield
						if (t.getBouclier() == 0) {
							reposerButin(t);
						}
					}
					entite.dragonEffet();
				}
				tabRemoveEntite = new HashSet<Entite>();
			}
		}
	}

	/**
	 * This function adds new item when there is a lot of players.
	 */
	public void ajouterObjetAleatoire() {
		Objet i = null;
		int c = (int) (Math.random() * taillePlateau - 1);
		int l = (int) (Math.random() * taillePlateau - 1);
		if (plateau[l][c].getEntites().isEmpty()) {
			if (l == taillePlateau % 2) {
				i = new Bourse(images.getBourseIcon());
				i.setPosition(new Case(l, c));
				this.scoreMax += i.getBonus();
			} else {
				i = new Piece(images.getCoinIcon());
				i.setPosition(new Case(l, c));
				this.scoreMax += i.getBonus();
			}
			plateau[l][c].addEntite(i);
		}

	}

	/**
	 * Take an id as a parameter in order to return a troll
	 * 
	 * @param troll
	 * @return e or null
	 */
	public Troll chercherTroll(String troll) {
		for (Entite e : listeEntite) {
			if ((e instanceof Troll) && e.getId().equals(troll)) {
				return (Troll) e;
			}
		}
		return null;
	}

	/**
	 * Same as the previous one but we search a dragon
	 * 
	 * @param dragon
	 * @return e or null
	 */
	public Dragon chercherDragon(String dragon) {
		for (Entite e : listeEntite) {
			if ((e instanceof Dragon) && e.getId().equals(dragon)) {
				return (Dragon) e;
			}
		}
		return null;
	}

	/**
	 * This function fill the game of a lot of entities
	 */
	@SuppressWarnings("static-access")
	public void genererPlateauFixe() {
		Dragon d;
		for (int i = 0; i < this.nombreDragons; ++i) {
			d = new Dragon(images.getDragonSleepingIcon(),
					images.getDragonIcon());
			// This equation allows to put one drogon near coins and the other
			// one near the shields
			this.ajouterEntite(d, i * 7, 7 - 6 * i);

		}

		Troll t;
		for (int i = 0; i < this.nombreJoueurs; ++i) {
			t = new Troll(this.trollIcon[i], this.trollIcon[4 + i]);
			t.setJoueur(i);
			this.ajouterEntite(t, 4, 4);
			if (i == 0)
				personnageSelectionne = t;
			if (diceMode)
				t.setDeplacementMax(0);
		}

		Rocher r1 = new Rocher(images.getRockIcon());
		Rocher r2 = new Rocher(images.getRockIcon());
		Rocher r3 = new Rocher(images.getRockIcon());
		Rocher r4 = new Rocher(images.getRockIcon());
		Rocher r5 = new Rocher(images.getRockIcon());
		Rocher r6 = new Rocher(images.getRockIcon());
		Eau w1 = new Eau(images.getWaterIcon());
		Eau w2 = new Eau(images.getWaterIcon());
		Arbre f1 = new Arbre(images.getForestIcon());
		Arbre f2 = new Arbre(images.getForestIcon());
		Arbre f3 = new Arbre(images.getForestIcon());
		Arbre f4 = new Arbre(images.getForestIcon());
		Arbre f5 = new Arbre(images.getForestIcon());
		Arbre f6 = new Arbre(images.getForestIcon());
		Piece c1 = new Piece(images.getCoinIcon());
		Piece c2 = new Piece(images.getCoinIcon());
		Piece c3 = new Piece(images.getCoinIcon());
		Piece c4 = new Piece(images.getCoinIcon());
		Piece c5 = new Piece(images.getCoinIcon());
		Piece c6 = new Piece(images.getCoinIcon());
		Bourse b1 = new Bourse(images.getBourseIcon());
		Bourse b2 = new Bourse(images.getBourseIcon());
		Cristal cr1 = new Cristal(images.getCristalIcon());
		Cristal cr2 = new Cristal(images.getCristalIcon());
		Coeur co1 = new Coeur(images.getHeartIcon());
		Coeur co2 = new Coeur(images.getHeartIcon());
		Coeur co3 = new Coeur(images.getHeartIcon());
		Bouclier bo1 = new Bouclier(images.getShieldIcon());
		Bouclier bo2 = new Bouclier(images.getShieldIcon());
		Bouclier bo3 = new Bouclier(images.getShieldIcon());
		this.ajouterEntite(cr1, 4, 0);
		this.ajouterEntite(cr2, 7, 7);
		this.ajouterEntite(co1, 6, 7);
		this.ajouterEntite(co2, 5, 0);
		this.ajouterEntite(co3, 1, 8);
		this.ajouterEntite(bo1, 7, 0);
		this.ajouterEntite(bo2, 7, 5);
		this.ajouterEntite(bo3, 6, 0);
		this.ajouterEntite(c1, 0, 3);
		this.ajouterEntite(c2, 0, 5);
		this.ajouterEntite(c3, 2, 3);
		this.ajouterEntite(c4, 2, 4);
		this.ajouterEntite(c5, 2, 6);
		this.ajouterEntite(c6, 3, 6);
		this.ajouterEntite(b1, 0, 6);
		this.ajouterEntite(b2, 2, 5);
		this.ajouterEntite(r1, 1, 1);
		this.ajouterEntite(r2, 1, 2);
		this.ajouterEntite(r3, 2, 2);
		this.ajouterEntite(r4, 1, 4);
		this.ajouterEntite(r5, 5, 6);
		this.ajouterEntite(r6, 6, 6);
		this.ajouterEntite(w1, 3, 5);
		this.ajouterEntite(w2, 4, 5);
		this.ajouterEntite(f1, 1, 5);
		this.ajouterEntite(f2, 3, 4);
		this.ajouterEntite(f3, 4, 1);
		this.ajouterEntite(f4, 5, 1);
		this.ajouterEntite(f5, 5, 3);
		this.ajouterEntite(f6, 6, 1);
	}

	/**
	 * This function randomly fills the board with items
	 */
	@SuppressWarnings("static-access")
	public void genererPlateauAleatoire() {
		int currentX = 4;
		int currentY = 4;
		boolean[][] browsedCheck = new boolean[taillePlateau][taillePlateau];
		List<Point> history = new LinkedList<Point>();
		// Add trolls
		Troll t;
		for (int i = 0; i < this.nombreJoueurs; ++i) {
			t = new Troll(this.trollIcon[i], this.trollIcon[4 + i]);
			t.setJoueur(i);
			t.setScore(i);
			joueurs.get(i).setTroll(t);
			this.scoreMax += i;
			this.ajouterEntite(t, currentX, currentY);
			if (i == 0) {
				personnageSelectionne = t;
			}
			if (diceMode) {
				t.setDeplacementMax(0);
			}
		}
		browsedCheck[currentX][currentY] = true;
		history.add(new Point(currentX, currentY));

		// Add random obstacles
		for (int i = 0; i < taillePlateau; i++) {
			for (int j = 0; j < taillePlateau; j++) {
				if (i != currentX && j != currentY) {
					int type = (int) (Math.random() * 5);
					switch (type) {
					case 0: // Rock
						this.ajouterEntite(new Rocher(images.getRockIcon()), i, j);
						break;
					case 1: // Water
						this.ajouterEntite(new Eau(images.getWaterIcon()), i, j);
						break;
					default: // Forest
						this.ajouterEntite(new Arbre(images.getForestIcon()), i, j);
						break;
					}
				}
			}
		}

		// Removing obstacles to put items
		int remaining = (int) ((taillePlateau * taillePlateau) / 1.9) - 1;
		int remainingDragons = nombreDragons;
		int remainingShields = nombreDragons;
		double horizontalDirection = 0.0;
		double verticalDirection = 0.0;
		while (remaining > 0) {
			// Either restart from an already browsed point
			if (Math.random() < 0.1) {
				int chosen = (int) (Math.random() * history.size());
				chosen = Math.min(history.size() - 1, Math.max(2, chosen - 5));
				currentX = history.get(chosen).x;
				currentY = history.get(chosen).y;
			} else {
				if (Math.random() > 0.7) {
					horizontalDirection = horizontalDirection
							+ (Math.random() * 2 - 1);
					verticalDirection = verticalDirection
							+ (Math.random() * 2 - 1);
					horizontalDirection = Math.min(1.99,
							Math.max(-1.99, horizontalDirection));
					verticalDirection = Math.min(1.99,
							Math.max(-1.99, verticalDirection));
				}
				if (verticalDirection > horizontalDirection) {
					currentX = Math.max(Math.min(currentX
							+ (int) (horizontalDirection), taillePlateau - 1), 0);
				} else {
					currentY = Math.max(Math.min(currentY
							+ (int) (verticalDirection), taillePlateau - 1), 0);
				}
			}
			if (!browsedCheck[currentX][currentY]) {
				this.plateau[currentX][currentY].deleteAllEntities();
				browsedCheck[currentX][currentY] = true;
				history.add(new Point(currentX, currentY));
				int type = (int) (Math.random() * 100);
				// Coin
				if (type >= 0 && type < 60) {
					this.ajouterEntite(new Piece(images.getCoinIcon()), currentX,
							currentY);
					remaining--;
				}
				// Bourse
				else if (type >= 60 && type < 70) {
					this.ajouterEntite(new Bourse(images.getBourseIcon()),
							currentX, currentY);
					remaining--;
				}
				// Cristal
				else if (type >= 70 && type < 75) {
					this.ajouterEntite(new Cristal(images.getCristalIcon()),
							currentX, currentY);
					remaining--;
				}
				// Heart
				else if (type >= 75 && type < 80) {
					this.ajouterEntite(new Coeur(images.getHeartIcon()), currentX,
							currentY);
					remaining--;
				}
				// Dragon
				else if (type >= 80 && type < 100 && remainingDragons > 0) {
					this.ajouterEntite(new Dragon(images.getDragonSleepingIcon(),
							images.getDragonIcon()), currentX, currentY);
					remaining--;
					remainingDragons--;
				}
				// Shield
				else if (type >= 80 && type < 100 && remainingShields > 0) {
					this.ajouterEntite(new Bouclier(images.getShieldIcon()),
							currentX, currentY);
					remaining--;
					remainingShields--;
				}
				// Nothing otherwise
				else if (type >= 80 && type <= 100) {
					remaining--;
				}
			}
		}
	}

	/**
	 * Getter of nombreJoueurs
	 * 
	 * @return nombreJoueurs
	 */
	public static int getNombreJoueurs() {
		return nombreJoueurs;
	}

	/**
	 * Setter of nombreJoueurs. Used for the customization in the options menu
	 * 
	 * @param player
	 */
	public static void setNombreJoueurs(int number) {
		if (number < 2) {
			number = 2;
		}
		if (number > 4) {
			number = 4;
		}
		nombreJoueurs = number;
	}

	/**
	 * indiceJoueurCourant getter
	 * 
	 * @return actifPlayer
	 */
	public int getIndiceJoueurCourant() {
		return indiceJoueurCourant;
	}
	
	public Joueur getJoueurCourant() {
		return joueurs.get(indiceJoueurCourant);
	}
	
	public List<Joueur> getJoueurs() {
		return joueurs;
	}

	/**
	 * nombreDragons getter
	 * 
	 * @return nombreDragons
	 */
	public int getNombreDragons() {
		return nombreDragons;
	}

	/**
	 * maxDragon Setter. Used in the options menu
	 * 
	 * @param dragon
	 */
	public static void setMaxDragon(int dragon) {
		if (dragon <= 1) {
			dragon = 1;
		}
		if (dragon > 2) {
			dragon = 2;
		}
		nombreDragons = dragon;
	}

	public int getScoreMax() {
		return scoreMax;
	}

	public void setScoreMax(int scoreMax) {
		this.scoreMax = scoreMax;
	}
	


	/**
	 * This method execute the move of the selected character
	 * 
	 * @param newC
	 */
	public void deplacer(Case newC) {
		if (personnageSelectionne instanceof Troll) {
			deplacerTroll(newC);
		}
		else if (personnageSelectionne instanceof Dragon) {
			deplacerDragon(newC);
		}
	}
	
	
	
	protected void deplacerTroll(Case destination) {
		// We execute the test of the collision function of the character
		if (deplacementValide(personnageSelectionne, destination)) {
			personnageSelectionne.deplacer(destination);// If we can, we move
		}

		// If the selected character is a troll, we execute the addItem operation
		if (personnageSelectionne instanceof Troll) {
			Troll t = (Troll) personnageSelectionne;
			(t).addItem();
			if (!t.getButin().isEmpty()
					&& (!backPackMode || t.getPosition().equals(t.getDepart()))) {
				((Troll) personnageSelectionne).validerButin();
			}
		}
	}
	
	
	
	protected void deplacerDragon(Case destination) {
		if (peutReveillerDragon(getJoueurCourant(), personnageSelectionne)
				&& deplacementValide(personnageSelectionne, destination)) {
			personnageSelectionne.deplacer(destination);// If we can, we move
		}
	}
	
	
	
	public boolean deplacementValide(Personnage personnage, Case destination) {
		return (!personnage.detecteCollision(destination) && !this.termine)		;
	}
	
	
	/**
	 * Test if a given player can wake up a given dragon
	 * @param j Player
	 * @param dragon Dragon
	 * @return True if the dragon can be woken up
	 */
	public boolean peutReveillerDragon(Joueur j, Personnage dragon) {
		return true;
		// TODO
	}

	/**
	 * Build the list of all possible action at the current state of the game
	 * @return
	 */
	public List<Action> listerActionsPossibles() {
		List<Action> actions = new ArrayList<Action>();
		actions.add(new PasserTour());
		return actions;
	}
	
	/**
	 * Check if a given action is valid for a given player
	 * @param j Player
	 * @param a Action
	 * @return True is the action can be played
	 */
	public boolean actionValide(Joueur j, Action a) {
		if (a instanceof PasserTour) {
			return true;
		}
		else if (a instanceof DeplacerTroll) {
			return deplacementValide(((DeplacerTroll) a).getPersonnage(), ((DeplacerTroll) a).getDestination());
		}
		else if (a instanceof ReveillerDeplacerDragon) {
			return peutReveillerDragon(getJoueurCourant(), ((ReveillerDeplacerDragon) a).getPersonnage())
					&& deplacementValide(((DeplacerTroll) a).getPersonnage(), ((DeplacerTroll) a).getDestination());
		}
		else {
			return false;
		}
	}
	
	/**
     * Play the move of an AI player
     */
    public synchronized void jouerIA() {
        if (getJoueurCourant() instanceof AbstractIA) {
            synchronized(this) {
                System.out.println(Thread.currentThread().getName()+": "+"================================[ " + getJoueurCourant().getClass().toString() + " " + getJoueurCourant().getNom() + " ]==========================================");
                ExecutorService executor = Executors.newSingleThreadExecutor();
                AbstractIA ia = (AbstractIA) getJoueurCourant();
                IAThread calcul = new IAThread(ia, this, executor);
                executor.execute(calcul);
                try {
//                    System.out.println("Top");
                    if (!executor.awaitTermination(AbstractIA.DELAI_DE_REFLEXION, TimeUnit.MILLISECONDS))
                    {
                        // Forcer la fin du thread du joueur artificiel
//                        System.out.println(Thread.currentThread().getName()+": "+"Forcer l'interruption");
                        executor.shutdownNow();
//                        System.out.println(Thread.currentThread().getName()+": "+"est interrompu ? = " + (calcul.isInterrupted()?"oui":"non"));
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                }
//                System.out.println("Stop");
                try {
                    calcul.join();
                } catch (InterruptedException ex) {
                	Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                }

                while (calcul.isAlive()) {
//                    calcul.stop();
                }
                System.gc();
                Action action;
                // Si aucun coup n'a ete retourne, prendre le dernier coup memorise
                if (calcul.getCoupChoisi() == null) {
//                    System.out.println(Thread.currentThread().getName()+": "+"Aucun coup choisi");
                	action = ia.getActionMemorisee();
                }
                else {
//                    System.out.println(Thread.currentThread().getName()+": "+"coup choisi = " + calcul.getCoupChoisi());
                	action = calcul.getCoupChoisi();
                }
                // Si aucun coup memorise, prendre un coup au hasard
                while (!actionValide(ia, action)) {
                    System.out.println(Thread.currentThread().getName()+": "+"Aucun coup memorise");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!! HASARD !!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    action = (new IAAleatoire()).choisirAction(this);
//                    System.out.println("Nouveau coup calcule = " + coup);
                }

                System.out.println("Coup choisi = "+action.toString());

                try {
                    action.appliquer(this);
                }
                catch (NullPointerException e) {
                	action = (new IAAleatoire()).choisirAction(this);
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!! HASARD !!!!!!!!!!!!!!!!");
                    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                    action.appliquer(this);
                }


                JeuIHM.getInstance().revalidate();
                try {
                    Thread.sleep(10);
//                    Thread.sleep(400);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
                }

                System.out.println("Fin de tour");
                for (Joueur j : this.joueurs) {
                    System.out.println(j.toString());
                }
            }
        }
    }

	/**
	 * This function will be used when a turn ends. It's also where the game's
	 * loop is
	 */
	public void finirTour() {
		do {
			// Try to play the AI player if needed
			if (getJoueurCourant() instanceof AbstractIA) {
				jouerIA();
			}
			
			if (this.personnageSelectionne != null) {
				// Move reset if the selected character is not null
				this.personnageSelectionne.resetDeplacement();
		
				if (diceMode) {
					for (Entite e : listeEntite) {
						if (e instanceof Personnage) {
							((Personnage) e).setDeplacementMax(0);
						}
					}
				}
		
				if (this.personnageSelectionne instanceof Troll) {
					Troll t = (Troll) this.personnageSelectionne;
					if (nombreJoueurs != 0
							&& t.getScore() >= this.scoreMax / nombreJoueurs)
						this.termine = true;
				}
		
				// If a dragon stops its movement on an obstacle
				if (this.personnageSelectionne.getPosition().getEntites().size() > 1) {
					for (Entite entite : this.personnageSelectionne.getPosition()
							.getEntites()) {
						if (entite instanceof Obstacle) {
							// We move the dragon to its previous position
							this.personnageSelectionne.turnBack();
						}
					}
				}
			}
		
			// Time to check if all the awaken dragons go back to sleep
			for (Dragon d : dragonList) {
				if (d.getEtat() instanceof Eveille) {
					Eveille dA = (Eveille) d.getEtat();
					if ((this.numeroTour - dA.getNbTurn()) >= DUREE_DRAGON)
						d.sleep();
				}
			}
		
			this.cracherFeu(taillePlateau);// Execution of the dragon fire operation
		
			for (Troll t : this.trollList) {
				if (t.getVies() == 0) {
					this.listeEntite.remove(t);
					this.tabRemoveEntite.add(t);
					this.deletedPlayers.add(t);
				}
			}
		
			for (Entite e : this.tabRemoveEntite) {
				e.getPosition().deleteEntite(e);
				this.trollList.remove(e);
			}
		
			this.tabRemoveEntite = new HashSet<Entite>();
		
			if ((this.trollList).size() <= 1)
				this.termine = true;
		
			setPersonnageSelectionne(null);// Reset of the selected character
		
			if (this.indiceJoueurCourant == nombreJoueurs - 1)
				numeroTour++;
		
			if (!estTermine())
				this.indiceJoueurCourant++;
			while (estHorsJeu(this.indiceJoueurCourant)) {
				this.indiceJoueurCourant++;
			}
		
			if (nombreJoueurs != 0) {
				// Change of player
				this.indiceJoueurCourant = this.indiceJoueurCourant % nombreJoueurs;
			}
				
			// Change of player
			this.indiceJoueurCourant = this.indiceJoueurCourant % nombreJoueurs;
		
			// The selected character is the next troll
			personnageSelectionne = chercherTroll("Troll_" + (indiceJoueurCourant + 1));
			
			System.out.println(this.toString());

		} while (getJoueurCourant() instanceof AbstractIA);
	}

	/**
	 * This function is used to pass the turn of a dead player
	 * 
	 * @param player
	 * @return true/false
	 */
	public boolean estHorsJeu(int player) {
		for (Troll t : this.deletedPlayers) {
			if (t.getJoueur() == player)
				return true;
		}
		return false;
	}

	/**
	 * NbTurn getter
	 * 
	 * @return nbTurns
	 */
	public int getNumeroTour() {
		return numeroTour;
	}

	/**
	 * Getter of termine.
	 * 
	 * @return True if the game is over.
	 */
	public boolean estTermine() {
		return termine;
	}

	/**
	 * This function will allow all the dragons to wake up
	 */
	public void reveillerDragons() {
		for (Dragon d : dragonList) {
			d.wakeUp();
			((Eveille) d.getEtat()).setNbTurn(this.numeroTour);
		}
	}

	/**
	 * Setter of nbLimit
	 * 
	 * @param i
	 */
	public static void setTaillePlateau(int i) {
		taillePlateau = i;
	}

	/**
	 * nbLimit getter
	 * 
	 * @return nbLimite
	 */
	public static int getTaillePlateau() {
		return taillePlateau;
	}

	/**
	 * Getter of the start position of all trolls
	 * 
	 * @return start
	 */
	public Case getMaison() {
		return maison;
	}

	/**
	 * Setter of the start position
	 * 
	 * @param start
	 */
	public void setMaison(Case start) {
		this.maison = start;
	}

	/**
	 * DiceMode getter. If it's true, the game works with a dice. Otherwise it's
	 * normal
	 * 
	 * @return diceMode
	 */
	public boolean onDiceMode() {
		return diceMode;
	}

	/**
	 * Enable or disable the back pack mode
	 * 
	 * @param b
	 */
	public static void setOptionBackPack(boolean b) {
		backPackMode = b;
	}

	/**
	 * Option dice setter. It's by this function we make the dice mode or not
	 * 
	 * @param b
	 */
	public static void setOptionDice(boolean b) {
		diceMode = b;
	}

	/**
	 * This function will return the troll's incomes into the gamePlateau
	 * 
	 * @param troll
	 */
	void reposerButin(Troll troll) {
		// Item it position
		Case c;
		int itAbscisse, itOrdonne;
		for (Objet it : troll.getButin()) {
			c = it.getPosition();
			itAbscisse = c.getAbscisse();
			itOrdonne = c.getOrdonnee();
			// we add it as first Entity of it square
			plateau[itAbscisse][itOrdonne].getEntites().add(0, it);
		}
		troll.setButin(new ArrayList<Objet>());
	}

	public Images getImages() {
		return images;
	}

	public void setImages(Images images) {
		this.images = images;
	}

	@Override
	public String toString() {
		String str =  "Jeu [joueurs=" + joueurs + ", plateau=\n";
		for (int i = 0; i < taillePlateau; i++) {
			for (int j = 0; j < taillePlateau; j++) {
				str += "\t"+plateau[i][j].toString();
			}
			str += "\n";
		}
		str += " scoreMax=" + scoreMax
			+ ", personnageSelectionne=" + personnageSelectionne
			+ ", indiceJoueurCourant=" + indiceJoueurCourant + ", termine="
			+ termine + ", numeroTour=" + numeroTour + "]";
		return str;
	}
	
	

}
