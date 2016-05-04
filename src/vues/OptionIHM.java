package vues;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controle.Jeu;


/**
 * This interface will be used in order to customize the game like names, trolls, dragons
 * @author prukev, Brahim
 *
 */
@SuppressWarnings("serial")
public class OptionIHM extends JFrame {

	/**
	 * This class is a singleton
	 */
	private static OptionIHM instance;
	
	/**
	 * Private constructor. Nothing special in there except the basics configurations
	 * @param name
	 */
	private OptionIHM(String name) {
		super(name);
		this.setSize(600, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawOption();
		setVisible(true);
	}
	
	/**
	 * Instance getter
	 * @param name
	 * @return instance
	 */
	public static synchronized OptionIHM getInstance(String name) {
		if (instance == null) instance = new OptionIHM(name);
		return instance;
	}
	
	/**
	 * Instance getter with no parameters
	 * @return instance
	 */
	public static synchronized OptionIHM getInstance() {
		return instance;
	}
	
	/**
	 * Like the menu, there is a drawing function. Contains all the data a user can customized
	 */
	public void drawOption() {
		final JPanel menuPanel = new JPanel();
		
		JPanel nameZone = new JPanel();
		
		menuPanel.setLayout(new GridLayout(5, 2));
		
		JLabel nbPlayLabel = new JLabel("Nombre de joueurs : ");
		final JTextField nbPlayText = new JTextField("2");
		JLabel nbDragLabel = new JLabel("Nombre de dragons : ");
		final JTextField nbDragText = new JTextField("1");
		JLabel tailleGameLabel = new JLabel("Taille du tableau");
		final JTextField tailleGameText = new JTextField("9");
		JButton valid = new JButton("Valider");
		JButton cancel = new JButton("Cancel");
		JButton changeName = new JButton("ChangeName");
		
		JLabel optionDescribe = new JLabel("Mode de jeu");
		final JCheckBox backPack = new JCheckBox("Mode sac à dos");
		final JCheckBox choice = new JCheckBox("Mode dé");
		
		valid.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Jeu.setNombreJoueurs(Integer.parseInt(nbPlayText.getText()));
				Jeu.setMaxDragon(Integer.parseInt(nbDragText.getText()));
				Jeu.setTaillePlateau(Integer.parseInt(tailleGameText.getText()));
				Jeu.setOptionDice(choice.isSelected());
				Jeu.setOptionBackPack(backPack.isSelected());
				setVisible(false);
				if (MenuIHM.getInstance() == null) MenuIHM.getInstance("Troll & Dragon");
				else MenuIHM.getInstance().setVisible(true);
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (MenuIHM.getInstance() == null) MenuIHM.getInstance("Troll & Dragon");
				else MenuIHM.getInstance().setVisible(true);
			}
		});
		
		changeName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				Jeu.setNombreJoueurs(Integer.parseInt(nbPlayText.getText()));
				new NomIHM("Options names");
			}
		});
		
		nameZone.add(nbPlayLabel);
		nameZone.add(changeName);
		
		menuPanel.add(nameZone);
		menuPanel.add(nbPlayText);
		menuPanel.add(nbDragLabel);
		menuPanel.add(nbDragText);
		menuPanel.add(tailleGameLabel);
		menuPanel.add(tailleGameText);
		menuPanel.add(optionDescribe);
		JPanel group = new JPanel();
		group.add(backPack);
		group.add(choice);
		menuPanel.add(group);
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
