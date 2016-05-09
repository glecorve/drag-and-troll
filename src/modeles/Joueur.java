package modeles;

import java.awt.Color;
import modeles.personnages.Troll;

/**
 * Represente un joueur (humain ou IA)
 * 
 * @author Gwenole Lecorve
 */
public class Joueur {
	private static int value = 1;
	private String nom = "Non defini";
	private Troll troll = null;
	private Color couleur;

	private static final Color couleurs[] = { new Color(20, 130, 255),
			new Color(255, 90, 90), new Color(130, 20, 255),
			new Color(255, 20, 130), new Color(20, 255, 130),
			new Color(255, 130, 20), new Color(200, 20, 200),
			new Color(200, 200, 20), new Color(20, 200, 200),
			new Color(130, 255, 20), new Color(90, 90, 255),
			new Color(90, 255, 90) };
	private static int couleurIterateur = -1;

	private static Color getCouleurSuivante() {
		couleurIterateur = (couleurIterateur + 1) % couleurs.length;
		return couleurs[couleurIterateur];
	}

	/****************** CONSTRUCTEUR ******************/
	
	public Joueur() {
		setNom("Joueur " + (couleurIterateur == -1 ? "1" : "2"));
		couleur = getCouleurSuivante();
	}

	public Joueur(String nom) {
		setNom(nom);
		couleur = getCouleurSuivante();
	}
	
	public Joueur(Troll t) {
		setNom("Joueur " + (couleurIterateur == -1 ? "1" : "2"));
		couleur = getCouleurSuivante();
		troll = t;
	}

	public Joueur(String nom, Troll t) {
		setNom(nom);
		couleur = getCouleurSuivante();
		troll = t;
	}

	/**
	 * Clone en profondeur l'objet courant
	 * 
	 * @return un nouveau joueur
	 */
	public Joueur clone() {
		Joueur clone = new Joueur(this.nom, (Troll) this.troll.clone());
		clone.couleur = this.couleur;
		return clone;
	}
	
	/**
	 * Clone tout sauf les cases
	 * 
	 * @return un nouveau joueur
	 */
	public Joueur clone(Case[][] plateau) {
		Joueur clone = new Joueur(this.nom, (Troll) this.troll.clone(plateau));
		clone.couleur = this.couleur;
		return clone;
	}
	
	/**
	 * Clone tout sauf les cases et le troll
	 * 
	 * @return un nouveau joueur
	 */
	public Joueur clone(Case[][] plateau, Troll t) {
		Joueur clone = new Joueur(this.nom, t);
		clone.couleur = this.couleur;
		return clone;
	}
	
	/**
	 * Fixe le nom du joueur
	 * @param nom String
	 */
	public void setNom(String nom) {
		if (nom.trim().isEmpty()){
			nom = "J" + value;
			Joueur.value += 1;
		}
		this.nom = nom;
	}
	
	/**
	 * Retourne le nom du joueur
	 * @return String nom
	 */
	public String getNom() {
		return nom;
	}

	@Override
	public String toString() {
		return getClass().toString() + " " + getNom() + " (" + Integer.toHexString(this.hashCode()) + ") =\n"
				+ "\tTroll = " + (this.getTroll() == null?"aucun":getTroll().toString());
	}

	public Troll getTroll() {
		return troll;
	}
	
	/**
	 * Set the troll.
	 * @param t The troll of the current player
	 */
	public void setTroll(Troll t) {
		troll = t;
	}

	/**
	 * Return the score of the current player
	 * @return A positive or null integer
	 */
	public int getScore() {
		if (troll == null) {
			return 0;
		}
		else {
			return troll.getScore();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((couleur == null) ? 0 : couleur.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (obj instanceof Joueur) {
			Joueur other = (Joueur) obj;
			return (couleur.equals(other.couleur)) && (nom.equals(other.nom));
		}
		else {
			return false;
		}
	}

}
