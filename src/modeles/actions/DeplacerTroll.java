package modeles.actions;

import modeles.Case;
import modeles.personnages.Troll;

public class DeplacerTroll extends DeplacerPersonnage {

	public DeplacerTroll(Troll t, Case destination) {
		super(t, destination);
	}

	@Override
	public Object clone() {
		DeplacerTroll dt = new DeplacerTroll((Troll) this.getPersonnage(), this.getDestination());
		return dt;
	}

	@Override
	public String toString() {
		return "Deplacer " + getPersonnage().toString() + " de "
				+ getPersonnage().getPosition().toString() + " vers "
				+ getDestination().toString();
	}

}
