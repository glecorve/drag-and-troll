package modeles.actions;

import controle.Jeu;
import modeles.Case;
import modeles.personnages.Dragon;

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
	public String toString() {
		return "Reveiller et deplacer " + getPersonnage().toString() + " de "
				+ getPersonnage().getPosition().toString() + " vers "
				+ getDestination().toString();
	}

}
