package vues;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import controle.Jeu;


/**
 * This class represents the main interface when the game is launched
 * @author prukev, Brahim
 *
 */
@SuppressWarnings("serial")
public class MenuIHM extends JFrame {

	/**
	 * This class will be a singleton
	 */
	private static MenuIHM instance = null;
	
	/**
	 * Private constructor. Extension of a JFrame
	 * @param name
	 */
	private MenuIHM(String name) {
		super(name);
		this.setSize(600, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		drawMenu();
		setVisible(true);
	}
	
	/**
	 * Instance getter
	 * @param name
	 * @return instance
	 */
	public static synchronized MenuIHM getInstance(String name) {
		if (instance == null) instance = new MenuIHM(name);
		return instance;
	}
	
	/**
	 * Instance getter with no parameter
	 * @return instance
	 */
	public static synchronized MenuIHM getInstance() {
		return instance;
	}
	
	/**
	 * Drawing function of the interface composed only of 4 buttons
	 */
	public void drawMenu() {
		final JPanel menuPanel = new JPanel();
		
		menuPanel.setLayout(new GridLayout(3, 1));
		
		JButton bPlay = new JButton("Play the game");
		bPlay.setSize(50, 50);
		JButton bOptions = new JButton("Options");
		bOptions.setSize(50, 50);
		JButton bReset = new JButton("Reset");
		bReset.setSize(50, 50);
		JButton bExit = new JButton("Exit");
		bExit.setSize(50, 50);
		bReset.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		bPlay.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (JeuIHM.getInstance() == null){
					if( Jeu.getTaillePlateau()<=9){
						 Jeu.setTaillePlateau(9);
					}
					if(Jeu.getTaillePlateau()>11){
						 Jeu.setTaillePlateau(11);
					}
					JeuIHM.getInstance(Jeu.getTaillePlateau());
				}
				else JeuIHM.getInstance().setVisible(true);
			}
		});
		
		bOptions.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (OptionIHM.getInstance() == null) OptionIHM.getInstance("Option");
				else OptionIHM.getInstance().setVisible(true);
			}
		});
		
		bExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		menuPanel.add(bPlay);
		menuPanel.add(bOptions);
		menuPanel.add(bReset);
		menuPanel.add(bExit);
		
		getContentPane().add(menuPanel, BorderLayout.CENTER);
		

	}
	
	/**
	 * Extension of visible setter of a window. Used for the combinations with the other windows
	 * @param visible
	 */
	public void visible(boolean visible) {
		setVisible(visible);
	}
	
	public static void main(String[] args) {
		MenuIHM.getInstance("Troll & Dragon");
	}
	
}
