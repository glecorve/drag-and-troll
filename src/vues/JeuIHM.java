package vues;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javafx.scene.layout.Border;

import javax.swing.*;

import controle.Jeu;

import modeles.Case;
import modeles.Entite;
import modeles.Joueur;
import modeles.personnages.Dragon;
import modeles.personnages.Eveille;
import modeles.personnages.Personnage;
import modeles.personnages.Troll;

import images.Images;

/**
 * This is the main interface of the game
 * 
 * @author brahim, prukev
 * 
 */
@SuppressWarnings("serial")
public class JeuIHM extends JFrame {

	private static final String nomFenetre = "Drag and Troll";
	
	private static final Color MAISON = new Color(255, 202, 155);
	private static final Color JOUEUR_EN_TETE = Color.YELLOW;

	/**
	 * The IHM has a variable which represents the game
	 */
	public static Jeu game;

	/**
	 * This variable will be the game's table
	 */
	private Case[][] plateauIHM;

	/**
	 * This table will represent the game's table structure by a table of
	 * JButton for the display
	 */
	private JButton[][] plateauJPanel;

	/**
	 * This class is a singleton
	 */
	private static JeuIHM instance = null;

	/**
	 * This panel will contain the board
	 */
	private JPanel boardPanel = new JPanel();

	/**
	 * This JPanel is the main panel which will contain the game
	 */
	private JPanel jeu = new JPanel();

	/**
	 * This boolean will be use in order to block a new throw of dice
	 */
	private boolean hasRoll = false;

	/**
	 * This ihm data is used to show who is the actif player
	 */
	private JLabel selectionJ;

	/**
	 * This ihm data is used to show the number of turns. A turn counts when
	 * every player has played one time
	 */
	private JLabel nbTurns;
	
	/**
	 * This IHM data is used to show the score to reach in order to win
	 */
	private JLabel scoreAAtteindre;

	/**
	 * This panel will contain all the informations needed like who play, which
	 * character is selected
	 */
	private JPanel infoJeu = new JPanel();

	/**
	 * This panel will be use in order to show all the data of the game
	 */
	private JPanel playersInfo = new JPanel();
	
	private java.util.List<JoueurIHM> vuesJoueurs = new LinkedList<JoueurIHM>();

	private JPanel playersTable = new JPanel();

	/**
	 * Name of first column for players data
	 */
	private String[] dataList = { "Nom", "Score", "Lifes", "Magic", "Shield" };

	/**
	 * Troll's home
	 */
	private JButton depart = new JButton();

	/**
	 * This is the table which contains all the icons of dice
	 */
	private ImageIcon[] des = { Images.getDe1(), Images.getDe2(),
			Images.getDe3(), Images.getDe4(), Images.getDe5(), Images.getDe6() };

	/**
	 * This table contains all the names. By default "Joueur x"
	 */
	private static String joueurNom[] = { "Joueur 1", "Joueur 2", "Joueur 3",
			"Joueur 4" };

	/**
	 * Button for rolling the dice
	 */
	JButton de = new JButton("Lancer de");

	/**
	 * Will contains the icons
	 */
	private JLabel dice = new JLabel();

	/**
	 * This is the IHM constructor
	 * 
	 * @param name
	 * @param taille
	 */
	private JeuIHM(int taille) {
		super(nomFenetre); // We call the constructor of a JFrame object

		// We create a new game if there is no game before with the limit for
		// the table
		game = new Jeu(taille);

		plateauIHM = game.getPlateau(); // We get the game's table
		this.setSize(1000, 800); //
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		game.genererPlateauAleatoire();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		drawGame();
		setVisible(true);
	}

	/**
	 * This is the IHM constructor
	 * 
	 * @param name
	 * @param size
	 */
	private JeuIHM(Jeu jeu) {
		super(nomFenetre); // We used the constructor of a JFrame object

		// We create a new game if there is no game before with the limit for
		// the table
		game = jeu;

		plateauIHM = game.getPlateau(); // We get the game's table
		this.setSize(1000, 800); //
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		game.genererPlateauAleatoire();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		drawGame();
		setVisible(true);
	}

	/**
	 * This function returns the current instance
	 * 
	 * @param size
	 * @return instance
	 */
	public static synchronized JeuIHM getInstance(int size) {
		if (instance == null) {
			instance = new JeuIHM(size);
		}
		return instance;
	}

	/**
	 * This function returns the current instance
	 * 
	 * @param name
	 * @param size
	 * @return instance
	 */
	public static synchronized JeuIHM getInstance(Jeu jeu) {
		if (instance == null) {
			instance = new JeuIHM(jeu);
		}
		return instance;
	}

	/**
	 * This function returns the current instance without parameters
	 * 
	 * @return instance
	 */
	public static synchronized JeuIHM getInstance() {
		return instance;
	}

	private int getTaillePlateau() {
		return Jeu.getTaillePlateau();
	}

	/**
	 * This function is the drawing function of the IHM
	 */
	public void drawGame() {

		ajouterBarreMenu();
		
		// We create a table for the display with the limit of the game's table
		this.plateauJPanel = new JButton[getTaillePlateau()][getTaillePlateau()];

		// We set the layout into a gridlayout. More easy to have a table
		boardPanel.setLayout(new GridLayout(getTaillePlateau(), getTaillePlateau()));
		for (int i = 0; i < getTaillePlateau(); ++i) {
			for (int j = 0; j < getTaillePlateau(); ++j) {
				final Case actualC = plateauIHM[i][j];
				final JButton panCase = new JButton();
				
				panCase.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {
					}

					@Override
					public void keyReleased(KeyEvent e) {
					}

					// This function is used for the movement of the selected
					// character
					@Override
					public void keyPressed(KeyEvent e) {
						// This will only works if a character is selected
						if (game.getPersonnageSelectionne() != null) {
							Personnage personnageCourant = game
									.getPersonnageSelectionne();
							Case positionC = personnageCourant.getPosition();
							if (e.getKeyCode() == KeyEvent.VK_UP
									&& positionC.getAbscisse() > 0) {
								game.deplacer(plateauIHM[positionC
										.getAbscisse() - 1][positionC
										.getOrdonnee()]);
							}

							if (e.getKeyCode() == KeyEvent.VK_DOWN
									&& positionC.getAbscisse() < getTaillePlateau() - 1) {
								game.deplacer(plateauIHM[positionC
										.getAbscisse() + 1][positionC
										.getOrdonnee()]);
							}

							if (e.getKeyCode() == KeyEvent.VK_LEFT
									&& positionC.getOrdonnee() > 0) {
								game.deplacer(plateauIHM[positionC
										.getAbscisse()][positionC.getOrdonnee() - 1]);
							}

							if (e.getKeyCode() == KeyEvent.VK_RIGHT
									&& positionC.getOrdonnee() < getTaillePlateau() - 1) {
								game.deplacer(plateauIHM[positionC
										.getAbscisse()][positionC.getOrdonnee() + 1]);

							}
							
							if (e.getKeyChar() == '1') {
								essayerJouerDragon(1);
							}
							
							if (e.getKeyChar() == '2') {
								essayerJouerDragon(2);
							}
							
							if (e.getKeyCode() == KeyEvent.VK_SPACE) {
								game.finirTour();
								game.demarrerTour();
							}		

							rafraichirTout();
						}

					}
				});

				panCase.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							// If it's the first selection OR the previous
							// selected character hasn't move yet
							if ((!actualC.getEntites().isEmpty() && game
									.getPersonnageSelectionne() == null)
									|| (!actualC.getEntites().isEmpty()
											&& game.getPersonnageSelectionne() != null && game
											.getPersonnageSelectionne()
											.getDeplacementActuel() == 0)) {
								if (actualC.getEntites().size() >= 2) {
									// We look for the character in order to
									// select it
									Dragon chara = game
											.chercherDragon("Dragon_1");
									game.setPersonnageSelectionne(chara);
									rafraichirTout();
								}
							}
						}
					}
				});
				boardPanel.add(panCase);// Adding of the JButton to the panel

				/*
				 * Since we have the system table, we assign each case of the
				 * system to the ihm table. The ihm table is a data stocked
				 * because we will update it during the game
				 */
				this.plateauJPanel[i][j] = panCase;
			}
		}
		
		rafraichirBordures();
		rafraichirPlateau();

		de.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!hasRoll) {
					int faceDe = (int) (Math.random() * 6 + 1);
					dice.setIcon(des[faceDe - 1]);
					game.getPersonnageSelectionne().setDeplacementMax(faceDe);
					hasRoll = true;
				}
				plateauJPanel[game.getPersonnageSelectionne().getPosition()
						.getAbscisse()][game.getPersonnageSelectionne()
						.getPosition().getOrdonnee()].requestFocus();
			}
		});

		jeu.setLayout(new BorderLayout());
		
		drawBandeau();
		boardPanel.setBackground(Color.BLACK);

		jeu.add(boardPanel, BorderLayout.CENTER);

		getContentPane().add(jeu);

	}
	
	private void ajouterBarreMenu() {
		JMenuBar barre = new JMenuBar();
		barre.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// Menu
		JMenuItem menu = new JMenuItem("Menu");
		menu.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (MenuIHM.getInstance() == null)
					MenuIHM.getInstance("Troll & Dragon");
				else
					MenuIHM.getInstance().setVisible(true);
			}
		});
		barre.add(menu);
		
		// Restart
		JMenuItem recommencer = new JMenuItem("Recommencer");
		recommencer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				recommencer();
			}
		});
		barre.add(recommencer);
		
		// Help
		JMenuItem aide = new JMenuItem("Aide");
		aide.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(JeuIHM.getInstance(), "<html>Commandes :<br>" +
						"- Déplacements : flèches<br>" +
						"- Fin de tour : espace<br>" +
						"- Réveiller un dragon : touche numérotée<br><br>" +
						"Objets :<br>" +
						"- Pièce : rapporte un point<br>" +
						"- Bourse : rapporte 3 points<br>" +
						"- Coeur : ajoute une vie (maximum "+Troll.VIES_MAX+")<br>" +
						"- Cristal : ajoute un point de magie (maximum "+Troll.MAGIE_MAX+")<br>" +
						"- Bouclier : confère une protection contre le feu</html>");
			}
		});
		barre.add(aide);
		
		this.setJMenuBar(barre);
	}

	private void essayerJouerDragon(int dragonIndex) {
		if (dragonIndex >= 1 && dragonIndex <= game.getDragons().size()) {
			Dragon d = game.getDragons().get(dragonIndex-1);
			if (game.peutReveillerDragon(game.getJoueurCourant(), d)) {
				game.reveillerDragon(d);
			}
		}
	}
	
	/**
	 * This function cleans the icon image of the JButton in the position (i,j)
	 * 
	 * @param i
	 * @param j
	 */
	void clean(int i, int j) {
		this.plateauJPanel[i][j].setIcon(null);
	}

	/**
	 * This function cleans all the icons in the plateauIHM
	 */
	void cleanEmpty() {
		for (int i = 0; i < this.getTaillePlateau(); ++i) {
			for (int j = 0; j < this.getTaillePlateau(); ++j) {

				if ((this.plateauIHM[i][j].getEntites()).isEmpty()) {
					clean(i, j);
				}
			}
		}

	}

	/**
	 * This function draws the data of all the players
	 */
	@SuppressWarnings("static-access")
	public void drawBandeau() {

		playersInfo.setLayout(new BorderLayout());
		
		// Game information
		infoJeu.setLayout(new GridLayout(3, 1));
		nbTurns = new JLabel("", JLabel.CENTER);
		selectionJ = new JLabel("", JLabel.CENTER);
		scoreAAtteindre = new JLabel("<html><h2>Score a atteindre : "+ game.getScoreVictoire() + "</h2></html>", JLabel.CENTER);
		infoJeu.add(nbTurns);
		infoJeu.add(selectionJ);
		infoJeu.add(scoreAAtteindre);
		playersInfo.add(infoJeu, BorderLayout.NORTH);

		// Details of players
		playersTable.setLayout(new GridLayout(2, (Jeu.getNombreJoueurs()+1)/2));
		for (Joueur j : game.getJoueurs()) {
			JoueurIHM vue = new JoueurIHM(j);
			vuesJoueurs.add(vue);
			playersTable.add(vue);
		}
		playersInfo.add(playersTable, BorderLayout.CENTER);
		
		// Dice
		if (game.onDiceMode())
			playersInfo.add(de, BorderLayout.SOUTH);
		if (game.onDiceMode())
			dice.setIcon(des[0]);
		playersInfo.add(dice, BorderLayout.SOUTH);
		
		jeu.add(playersInfo, BorderLayout.EAST);
		
		rafraichirBandeau();
	}

	/**
	 * This function resets all the JButton borders
	 */
	public void rafraichirBordures() {
		// Clean
		for (int i = 0; i < getTaillePlateau(); ++i) {
			for (int j = 0; j < getTaillePlateau(); ++j) {
				this.plateauJPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
			}
		}
		// Browse
		for (int i = 0; i < getTaillePlateau(); ++i) {
			for (int j = 0; j < getTaillePlateau(); ++j) {
				this.plateauJPanel[i][j].setText("");
				if (!this.plateauIHM[i][j].getEntites().isEmpty()
						&& this.plateauIHM[i][j].getEntites().contains(
								game.getPersonnageSelectionne())) {
					this.plateauJPanel[i][j].setBorder(BorderFactory
							.createLineBorder(game.getJoueurCourant().getCouleur(), 4));
				} else {
					// Highlight dangerous squares
					for (Dragon d : game.getDragons()) {
						if (d.estEveille() && d.peutAtteindre(plateauIHM[i][j], game)) {
							this.plateauJPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.RED, 4));
						}
					}
				}
			}
		}

	}
	
	/**
	 * Start a new game
	 */
	public void recommencer() {
		game.reinitialiser();
		game.genererPlateauAleatoire();
		rafraichirTout();
		System.out.println(game.toString());
		game.demarrerTour();
		rafraichirTout();
	}

	/**
	 * This function draw the updated data
	 */
	public void rafraichirPlateau() {
		// updatePlateau();
		JButton panCase;
		cleanEmpty();
		for (int i = 0; i < this.getTaillePlateau(); ++i) {
			for (int j = 0; j < this.getTaillePlateau(); ++j) {
				panCase = this.plateauJPanel[i][j];
				java.util.List<Entite> entites = this.plateauIHM[i][j]
						.getEntites();
				if (!entites.isEmpty()) {
					panCase.setBackground(entites.get(0).getColor());
					for (Entite entite : entites) {
						panCase.setIcon(entite.getMyPicture());
						if (entite instanceof Dragon && game.getDragons().size() > 1) {
							int numDrag = 1;
							for (Dragon d : game.getDragons()) {
								if (entite == d) {
									panCase.setText(""+numDrag);
								}
								numDrag++;
							}
						}
					}
				}
				else {
					panCase.setBackground(Entite.COULEUR);
				}
				if (panCase == depart) {
					panCase.setBackground(MAISON);
				}
			}
		}
	}

	/**
	 * This function is used in order to update the game directly on the display
	 */
	public void rafraichirTout() {
		revalidate();
		synchronized (game) {
			if (game.getPersonnageSelectionne() != null
					&& game.getPersonnageSelectionne().getDeplacementActuel() == game
							.getPersonnageSelectionne().getDeplacementMax()
					&& game.getPersonnageSelectionne().getDeplacementMax() != 0) {
				game.finirTour();
				hasRoll = false;
				rafraichirBandeau();
				revalidate();
				game.demarrerTour();
			}
			rafraichirBordures();
			rafraichirPlateau();
			rafraichirBandeau();
			revalidate();
			if (game.estTermine())
				afficherVainqueur();
		}
	}

	/**
	 * This function updates all the date of all players
	 */
	public void rafraichirBandeau() {
		nbTurns.setText("<html><h1>Tour : " + game.getNumeroTour()+" / " + game.getNumeroTourMax() +"</h1></html>");
		selectionJ.setText("<html><h2>Joueur : " + game.getJoueurCourant().getNom() + "</h2></html>");
		scoreAAtteindre.setText("<html><h2>Score a atteindre : "+ game.getScoreVictoire() + "</h2></html>");
		
		for (JoueurIHM vue : vuesJoueurs) {
			vue.redraw();
			if (game.estEnTete(vue.getJoueur())) {
				vue.setBackground(JOUEUR_EN_TETE);
			}
			else {
				vue.setBackground(new JPanel().getBackground());
			}
		}
	}

	/**
	 * This function is an extension of the normal visible function
	 * 
	 * @param visible
	 */
	public void visible(boolean visible) {
		setVisible(visible);
	}

	/**
	 * This is the name's player getter
	 * 
	 * @return joueurNom
	 */
	public static String[] getPlayersNames() {
		return joueurNom;
	}

	/**
	 * This function is used to customize the name of a player
	 * 
	 * @param pos
	 * @param name
	 */
	public static void setNom(int pos, String name) {
		joueurNom[pos] = name;
	}

	/**
	 * This function displays a little windows with a nice message for the
	 * winner
	 */
	public void afficherVainqueur() {
		String msg = "";
		
		List<Joueur> vainqueurs = game.chercherVainqueurs();
		// Draw game
		if (vainqueurs.isEmpty() || vainqueurs.size() == Jeu.getNombreJoueurs()) {
			msg = "<html><b>Match nul !</b><br><br>";
		}
		// There is at least one winner
		else {
			msg = "<html><b>";
			msg += (vainqueurs.size() > 1?"Les joueurs ":"Le joueur ");
			for (int i = 0; i < vainqueurs.size(); i++) {
				if (i > 0 && i != vainqueurs.size() - 1) { msg += ", "; }
				else if (i > 0 && i == vainqueurs.size() - 1) { msg += " et "; }
				msg += (vainqueurs.get(i).getIndice() + 1) + " ("
						+ vainqueurs.get(i).getNom() + ")";
			}
			msg += "</b> gagne"+(vainqueurs.size() > 1?"nt":"")+" la partie !<br><br>";
		}
		
		// Scores
		msg += "Scores : <br>";
		for (int i = 0; i < game.getJoueurs().size(); i++) {
			msg += " - Joueur "+(i+1)+" ("+game.getJoueurs().get(i).getNom()+") = "
					+game.getJoueurs().get(i).getScore()
					+" point"+(game.getJoueurs().get(i).getScore()>1?"s":"")+"<br>";
		}
		msg += "</html>";
		Object[] options = { "Revoir le plateau", "Rejouer une partie", "Quitter" };
		int n = JOptionPane.showOptionDialog(this,
				msg, "FIN DE PARTIE",
				JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[2]);
		
		switch (n) {
		case 1:
			recommencer();
			break;
		case 2:
			System.exit(0);
			break;
		default:
			break;
		}
		
	}
}
