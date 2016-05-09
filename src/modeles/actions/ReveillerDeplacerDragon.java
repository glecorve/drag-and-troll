package modeles.actions;

import controle.Jeu;
import modeles.Case;
import modeles.personnages.Dragon;
import modeles.personnages.Personnage;

public class ReveillerDeplacerDragon extends DeplacerPersonnage {

	public ReveillerDeplacerDragon(Dragon t, Case destination) {
		super(t, destination);
	}

	@Override
	public Object clone() {
		ReveillerDeplacerDragon rdd = new ReveillerDeplacerDragon(
				(Dragon) this.getPersonnage(), this.getDestination());
		return rdd;
	}

	@Override
	public void appliquer(Jeu etat) {
		// Find the character first
		for (Personnage d : etat.getDragons()) {
			if (personnage.equals(d)) {
				etat.setPersonnageSelectionne(d);
				((Dragon) d).reveiller();
				etat.getJoueurCourant().getTroll().diminuerMagie();
				break;
			}
		}
		etat.deplacer(etat.getCase(destination.getAbscisse(), destination.getOrdonnee()));
	}

	@Override
	public String toString() {
		return "Reveiller et deplacer " + getPersonnage().toString() + " de "
				+ getPersonnage().getPosition().toString() + " vers "
				+ getDestination().toString();
	}

}
