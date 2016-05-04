package modeles.actions;

import controle.Jeu;

public class PasserTour extends Action {

	public PasserTour() {
		super();
		// Nothing to do
	}
	
	@Override
	public Object clone() {
		return new PasserTour();
	}

	@Override
	public void appliquer(Jeu etat) {
		// Nothing to do
	}

	@Override
	public String toString() {
		return "Passer son tour";
	}
	
}
