package vues;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controle.Jeu;


/**
 * This interface is used for specific customization
 * @author prukev, Brahim
 *
 */
@SuppressWarnings("serial")
public class NomIHM extends JFrame {

	/**
	 * This is the constructor with a name for a title of window
	 * @param name
	 */
	public NomIHM(String name) {
		super(name);
		this.setSize(600, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawOption();
		setVisible(true);
	}
	
	/**
	 * This is the drawing function of the window
	 */
	public void drawOption() {
		final JPanel menuPanel = new JPanel();
		
		menuPanel.setLayout(new GridLayout(Jeu.getNombreJoueurs()+1, 2));
		
		JLabel[] listLabelJoueur = new JLabel[Jeu.getNombreJoueurs()];
		final JTextField[] textFieldJoueur = new JTextField[Jeu.getNombreJoueurs()];
		
		for (int i = 0; i < Jeu.getNombreJoueurs(); ++i) {
			listLabelJoueur[i] = new JLabel("Joueur " + (i+1));
			textFieldJoueur[i] = new JTextField(JeuIHM.getPlayersNames()[i]);
			menuPanel.add(listLabelJoueur[i]);
			menuPanel.add(textFieldJoueur[i]);
		}
		
		JButton valid = new JButton("Valider");
		JButton cancel = new JButton("Cancel");
		
		valid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				for (int i = 0; i < Jeu.getNombreJoueurs(); ++i) {
					JeuIHM.getPlayersNames()[i] = textFieldJoueur[i].getText();
				}
				OptionIHM.getInstance().setVisible(true);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				OptionIHM.getInstance().setVisible(true);
			}
		});
		
		menuPanel.add(valid);
		menuPanel.add(cancel);
		
		
		getContentPane().add(menuPanel, BorderLayout.CENTER);
		

	}
	
	/**
	 * Extension of setVisible for combinations with others interfaces
	 * @param visible
	 */
	public void visible(boolean visible) {
		setVisible(visible);
	}
	
}
