package modeles.actions;

import controle.Jeu;
import modeles.Case;
import modeles.personnages.Personnage;
import modeles.personnages.Troll;

public class DeplacerTroll extends DeplacerPersonnage {

	public DeplacerTroll(Troll t, Case destination) {
		super(t, destination);
	}

	@Override
	public Object clone() {
		DeplacerTroll dt = new DeplacerTroll((Troll) this.getPersonnage(),
				this.getDestination());
		return dt;
	}

	@Override
	public void appliquer(Jeu etat) {
		// Find the character first
		for (Personnage t : etat.getTrolls()) {
			if (personnage.equals(t)) {
				etat.setPersonnageSelectionne(t);
				break;
			}
		}
		etat.deplacer(etat.getCase(destination.getAbscisse(), destination.getOrdonnee()));
	}

	@Override
	public String toString() {
		return "Deplacer " + getPersonnage().toString() + "\n"
				+ "\tvers " + getDestination().toString();
	}

}
