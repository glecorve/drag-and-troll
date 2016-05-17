package vues;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import images.Images;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import modeles.Joueur;
import modeles.personnages.Troll;

public class JoueurIHM extends JPanel {

	private Joueur joueur;
	
	public JoueurIHM(Joueur j) {
		super();
		joueur = j;
		setPreferredSize(new Dimension(350, 200));
		setBorder(BorderFactory.createLoweredBevelBorder());
		redraw();
	}
	
	public void redraw() {
		int vies = joueur.getTroll().getVies();
		int magie = joueur.getTroll().getMagie();
		int largeur = Math.max(Troll.VIES_MAX, Troll.MAGIE_MAX)+1;
		
		removeAll();

		// Nom
		JPanel nomPanel = new JPanel();
		nomPanel.add(new JLabel("<html><h1><a color='"+Couleur.getHTMLColorString(joueur.getCouleur())+"'>"+joueur.getNom()+"</a></h1></html>"));
		
		// Vies
		JPanel viesPanel = new JPanel();
		viesPanel.setLayout(new GridLayout(1, largeur));
		for (int i = 0; i < vies; i++) {
			JLabel label = new JLabel(Images.getHeartIcon());
			viesPanel.add(label);
		}
		for (int i = vies; i < Troll.VIES_MAX; i++) {
			JLabel label = new JLabel(Images.getHeartContainerIcon());
			viesPanel.add(label);
		}
		viesPanel.add(joueur.getTroll().possedeBouclier()?new JLabel(Images.getShieldIcon()):new JLabel(Images.getShieldContainerIcon()));
		for (int i = Troll.VIES_MAX + 1; i < largeur; i++) { viesPanel.add(new JLabel()); }
		
		// Magie
		JPanel magiePanel = new JPanel();
		magiePanel.setLayout(new GridLayout(1, largeur));
		for (int i = 0; i < magie; i++) {
			JLabel label = new JLabel(Images.getCristalIcon());
			magiePanel.add(label);
		}
		for (int i = magie; i < Troll.MAGIE_MAX; i++) {
			JLabel label = new JLabel(Images.getCristalContainerIcon());
			magiePanel.add(label);
		}
		for (int i = Troll.MAGIE_MAX; i < largeur; i++) { magiePanel.add(new JLabel()); }
		
		// Score
		JPanel scorePanel = new JPanel();
		scorePanel.add(new JLabel(Images.getCoinIcon()));
		scorePanel.add(new JLabel("<html><font size='+3'>x "+joueur.getScore()+"</font><html>"));
		
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		add(nomPanel);
		add(viesPanel);
		add(magiePanel);
		add(scorePanel);
		
		setVisible(true);
	}
	
	public Joueur getJoueur() {
		return joueur;
	}
	
	@Override
	public void setBackground(Color color) {
		for (Component c : getComponents()) {
			c.setBackground(color);
		}
	}
	
}
