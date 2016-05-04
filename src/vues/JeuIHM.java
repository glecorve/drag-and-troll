package vues;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import controle.Jeu;


import modeles.Case;
import modeles.Entite;
import modeles.personnages.Dragon;
import modeles.personnages.Eveille;
import modeles.personnages.Personnage;
import modeles.personnages.Troll;

import images.Images;

/**
 * This is the main interface of the game
 * @author brahim, prukev
 *
 */
@SuppressWarnings("serial")
public class JeuIHM extends JFrame {
	
	private static final String nomFenetre = "Drag and Troll";
	
	/**
	 * The IHM has a variable which represents the game
	 */
	private static Jeu game;

	/**
	 * This variable will be the game's table
	 */
	private Case[][] plateauIHM;
	
	/**
	 * This table will represent the game's table structure by a table of JButton for the display
	 */
	private JButton[][] plateauJPanel;
	
	/**
	 * This class is a singleton
	 */
	private static JeuIHM instance = null;
	
	/**
	 * This panel will contain the game
	 */
	private JPanel gamePanel = new JPanel();
	
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
	 * This ihm data is used to show the type of the selected character
	 */
	private JLabel selection;
	
	/**
	 * This ihm data is used to show the number of turns. A turn counts when every player has played one time
	 */
	private JLabel nbTurns;
	
	/**
	 * This panel will contain all the informations needed like who play, which character is selected
	 */
	private JPanel infoJeu=new JPanel();
	
	/**
	 * This panel will be use in order to show all the data of the game
	 */
	private JPanel playersInfo = new JPanel();
	
	/**
	 * This panel will display the table of scores
	 */
	private JPanel playersTable = new JPanel();
	
	/**
	 * This table label will contain a lot of the informations
	 */
	private JLabel[][] playerTablePanel;
	
	/**
	 * Name of first column for players data
	 */
	private String[] dataList = {"Nom", "Score", "Lifes", "Magic", "Shield"};	
	
	/**
	 * Troll's home
	 */
	private JButton depart = new JButton();
	
	Images DeIcon=new Images();
	/**
	 * This is the table which contains all the icons of dice
	 */
	private ImageIcon[] des = { DeIcon.getDe1(), DeIcon.getDe2(), DeIcon.getDe3(),DeIcon.getDe4(),DeIcon.getDe5(),DeIcon.getDe6() };
	
	/**
	 * This label will display a little message for the players
	 */
	
	private JLabel inforTurnPlay = new JLabel();
	
	
	/**
	 * This table contains all the names. By default "Joueur x"
	 */
	private static String joueurNom[]= {"Joueur 1", "Joueur 2", "Joueur 3", "Joueur 4"};
	
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
	 * @param name
	 * @param taille
	 */
	private JeuIHM(int taille) {
		super(nomFenetre); //We used the constructor of a JFrame object
		
		//We create a new game if there is no game before with the limit for the table
		game = new Jeu(taille);
		
		plateauIHM = game.getPlateau(); //We get the game's table
		this.setSize(1000, 800); //
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.genererPlateauAleatoire();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		drawGame();
		setVisible(true);
	}
	
	/**
	 * This is the IHM constructor
	 * @param name
	 * @param size
	 */
	private JeuIHM(Jeu jeu) {
		super(nomFenetre); //We used the constructor of a JFrame object
		
		//We create a new game if there is no game before with the limit for the table
		game = jeu;
		
		plateauIHM = game.getPlateau(); //We get the game's table
		this.setSize(1000, 800); //
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		game.genererPlateauAleatoire();
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		drawGame();
		setVisible(true);
	}
	
	/**
	 * This function returns the current instance
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
		
		//We create a table for the display with the limit of the game's table
		this.plateauJPanel = new JButton[getTaillePlateau()][getTaillePlateau()];
		
		this.playerTablePanel = new JLabel[5][Jeu.getNombreJoueurs()+1];

		//We set the layout into a gridlayout. More easy to have a table
		gamePanel.setLayout(new GridLayout(getTaillePlateau()+1, this.getTaillePlateau()));
		for (int i = 0; i < this.getTaillePlateau(); ++i) {
			for (int j = 0; j < this.getTaillePlateau(); ++j) {
				final Case actualC = plateauIHM[i][j];
				final JButton panCase = new JButton();
				panCase.setBackground(Entite.COLOR); //By default, every case will have the same background
				java.util.List<Entite> entites = this.plateauIHM[i][j].getEntites();
				if (!entites.isEmpty() && entites.contains(game.getPersonnageSelectionne())) {
					panCase.setBorder(BorderFactory.createLineBorder(Color.blue,2));
				} else {
					panCase.setBorder(BorderFactory.createLineBorder(Color.black,2));
				}
				// Draw the background
				if (!entites.isEmpty()) {
					panCase.setBackground(entites.get(0).getColor());
				}
				
				final ArrayList<Entite> actualList = actualC.getEntites();

				if (!actualList.isEmpty()) {
					//String name = "";
					for (Entite entite : actualList) {
						//name = name + entite.getId() + " ";
						/*
						//Depending the type of the entity, there will be different color
						if (entite instanceof Forest) panCase.setBackground(Color.green);
						if (entite instanceof Water) panCase.setBackground(Color.blue);
						if (entite instanceof Rock) panCase.setBackground(Color.darkGray);
						if (entite instanceof Coin || entite instanceof Bourse)panCase.setBackground(Color.yellow);
						if (entite instanceof Coeur) panCase.setBackground(Color.red);
						if (entite instanceof Cristaux) panCase.setBackground(Color.white);
						if (entite instanceof Bouclier) panCase.setBackground(Color.pink);*/
						
						panCase.setIcon(entite.getMyPicture());
					}
					//panCase.setText(name);				
				}
				if (actualC == game.getMaison()) {
					depart = panCase;
					panCase.setBackground(Color.pink);
				}

				panCase.addMouseListener(new MouseListener() {

					@Override
					public void mouseReleased(MouseEvent e) {}

					@Override
					public void mousePressed(MouseEvent e) {}

					@Override
					public void mouseExited(MouseEvent e) {}

					@Override
					public void mouseEntered(MouseEvent e) {}

					//This function is used for the selection of a character
					@Override
					public void mouseClicked(MouseEvent e) {
						//If it's the first selection OR the previous selected character hasn't move yet
						//AND the game is still actif
						if (((!actualC.getEntites().isEmpty() && game.getPersonnageSelectionne() == null) ||
								(!actualC.getEntites().isEmpty() && game.getPersonnageSelectionne() != null &&
								game.getPersonnageSelectionne().getDeplacementActuel() == 0)) && !game.estTermine()){
							
							//We look for the character in order to select it
							for (Entite entite : actualC.getEntites()) {
								if (entite instanceof Personnage) {
									Personnage chara = (Personnage) entite;
									
									/*
									 * Two differents selection :
									 * 1 : If the character is a dragon, awake, and hasn't move yet
									 * 2 : If the character is a troll, its player is the actual player
									 */
									if (chara instanceof Dragon && 
											((Dragon) chara).getEtat() instanceof Eveille 
											|| chara instanceof Troll &&
											((Troll)chara).getJoueur() == game.getIndiceJoueurCourant()) {
										game.setPersonnageSelectionne(chara);
										refresh();
									}
								}


							}

						}
					}
				});
				panCase.addKeyListener(new KeyListener() {

					@Override
					public void keyTyped(KeyEvent e) {}

					@Override
					public void keyReleased(KeyEvent e) {}

					//This function is used for the movement of the selected character
					@Override
					public void keyPressed(KeyEvent e) {
						//This will only works if a character is selected
						if (game.getPersonnageSelectionne() != null) {
							Personnage actualPersonnage = game.getPersonnageSelectionne();
							Case positionC = actualPersonnage.getPosition();

							if (e.getKeyCode() == KeyEvent.VK_UP && positionC.getAbscisse() > 0) {
								game.deplacer(plateauIHM[positionC.getAbscisse()-1][positionC.getOrdonnee()]);
							}

							if (e.getKeyCode() == KeyEvent.VK_DOWN && positionC.getAbscisse() < getTaillePlateau()-1) {
								game.deplacer(plateauIHM[positionC.getAbscisse()+1][positionC.getOrdonnee()]);							
							}

							if (e.getKeyCode() == KeyEvent.VK_LEFT && positionC.getOrdonnee() > 0) {
								game.deplacer(plateauIHM[positionC.getAbscisse()][positionC.getOrdonnee()-1]);
							}

							if (e.getKeyCode() == KeyEvent.VK_RIGHT && positionC.getOrdonnee() < getTaillePlateau()-1) {
								game.deplacer(plateauIHM[positionC.getAbscisse()][positionC.getOrdonnee()+1]);

							}

							refresh();
						}

					}
				});
				
				panCase.addMouseListener(new MouseAdapter(){
				    @Override
				    public void mouseClicked(MouseEvent e){
				        if(e.getClickCount()==2){
				        	//If it's the first selection OR the previous selected character hasn't move yet
							if ((!actualC.getEntites().isEmpty() && game.getPersonnageSelectionne() == null) ||
									(!actualC.getEntites().isEmpty() && game.getPersonnageSelectionne() != null &&
									game.getPersonnageSelectionne().getDeplacementActuel() == 0)){
								if(actualC.getEntites().size()>=2) {
									//We look for the character in order to select it
									Dragon chara = game.chercherDragon("Dragon_1");
								    game.setPersonnageSelectionne(chara);
								    refresh();
								}
							}
				        }
				    }
				});
				gamePanel.add(panCase);//Adding of the JButton to the panel
				
				/*
				 * Since we have the system table, we assign each case of the system to the ihm table.
				 * The ihm table is a data stocked because we will update it during the game
				 */
				this.plateauJPanel[i][j] = panCase;
			}
		}
		
		JButton endTurn = new JButton("End");
		endTurn.addActionListener(new ActionListener() {

			//This function is used when we end a turn
			@Override
			public void actionPerformed(ActionEvent e) {
				updateBandeau();
				game.finirTour();
				if (game.onDiceMode()) hasRoll = false;
				refresh();
			}
		});
		
		JButton reveillerD = new JButton("Reveiller dragons");
		reveillerD.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Troll t=game.chercherTroll("Troll_"+(game.getIndiceJoueurCourant()+1));
				if(t!=null&&t.getMagie()>0){
					game.reveillerDragons();
					t.setMagie(t.getMagie()-1);
					updateBandeau();
					refresh();				
				}
			}
		});
		
		de.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!hasRoll) {
					int faceDe = (int)(Math.random()*6+1);
					dice.setIcon(des[faceDe-1]);
					game.getPersonnageSelectionne().setDeplacementMax(faceDe);
					hasRoll = true;
				}
				plateauJPanel[game.getPersonnageSelectionne().getPosition().getAbscisse()]
						[game.getPersonnageSelectionne().getPosition().getOrdonnee()].requestFocus();
			}
		});
		
		JButton menu = new JButton("Menu");
		menu.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				if (MenuIHM.getInstance() == null) MenuIHM.getInstance("Troll & Dragon");
				else MenuIHM.getInstance().setVisible(true);
			}
		});

		selectionJ = new JLabel("Joueur: "+ joueurNom[game.getIndiceJoueurCourant()]);
		selection = new JLabel("Personnage:");
		nbTurns = new JLabel("Tour : 1");
		
		jeu.setLayout(new BorderLayout());
		
		drawBandeau();
		
		gamePanel.add(endTurn);
		gamePanel.add(menu);
		gamePanel.add(reveillerD);
		gamePanel.setBorder(BorderFactory.createLineBorder(Color.black,2));
		infoJeu.setBorder(BorderFactory.createLineBorder(Color.black,2));
		infoJeu.add(selectionJ);
		infoJeu.add(selection);
		infoJeu.add(nbTurns);
		jeu.add(infoJeu,BorderLayout.NORTH);

		jeu.add(gamePanel,BorderLayout.CENTER);
		
		getContentPane().add(jeu);
		
	}
	
	/**
	 * This function cleans the icon image of the JButton in the position (i,j)
	 * @param i
	 * @param j
	 */
	void clean(int i,int j){
		this.plateauJPanel[i][j].setIcon(null);
	}
	
	/**
	 * This function cleans all the icons in the plateauIHM
	 */
	void cleanEmpty(){
		for (int i = 0; i < this.getTaillePlateau(); ++i) {
			for (int j = 0; j < this.getTaillePlateau(); ++j) {
				
				if ((this.plateauIHM[i][j].getEntites()).isEmpty()) {
					clean(i,j);
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
		
		playersTable.setLayout(new GridLayout(5, Jeu.getNombreJoueurs()));
		
		JLabel panTemp;
		
		//Attribution of the first column :
		for (int i = 0; i < this.dataList.length; ++i) {
			panTemp = new JLabel();
			panTemp.setText(this.dataList[i]);
			this.playerTablePanel[i][0] = panTemp;
		}
		
		//Creation of the first line of the player's table		
		for (int i = 1; i <= Jeu.getNombreJoueurs(); ++i) {
			panTemp = new JLabel();
			panTemp.setText(joueurNom[i-1] + " ");
			this.playerTablePanel[0][i] = panTemp;
		}
		
		//Fill of the table
		for (int j = 1; j <= Jeu.getNombreJoueurs(); ++j) {
			Troll t = game.chercherTroll("Troll_"+j);
			panTemp = new JLabel();
			panTemp.setText("" + t.getScore());
			
			//Insertion of all scores
			this.playerTablePanel[1][j] = panTemp;
			
			panTemp = new JLabel();
			panTemp.setText("" + t.getVies());
			
			//Insertion of all life points
			this.playerTablePanel[2][j] = panTemp;
			
			panTemp = new JLabel();
			panTemp.setText("" + t.getMagie());
			
			//Insertion of all magic points
			this.playerTablePanel[3][j] = panTemp;
			
			panTemp = new JLabel();
			panTemp.setText("" + t.getBouclier());
			
			//Insertion of all shield
			this.playerTablePanel[4][j] = panTemp;
		}
		
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j <= Jeu.getNombreJoueurs(); ++j) {
				playersTable.add(this.playerTablePanel[i][j]);
			}
		}
		
		inforTurnPlay.setText("Score a atteindre >= "+game.getScoreMax()/(game.getNombreJoueurs()));	
		playersInfo.add(playersTable, BorderLayout.NORTH);
		playersInfo.add(inforTurnPlay,BorderLayout.EAST);
		if (game.onDiceMode()) playersInfo.add(de, BorderLayout.SOUTH);
		if (game.onDiceMode()) dice.setIcon(des[0]);
		playersInfo.add(dice,BorderLayout.CENTER);
		jeu.add(playersInfo, BorderLayout.EAST);
	}
	

	/**
	 * This function resets all the display of each JButton
	 */
	public void resetGame() {
		for (int i = 0; i < getTaillePlateau(); ++i) {
			for (int j = 0; j < getTaillePlateau(); ++j) {
				this.plateauJPanel[i][j].setText("");
				if (!this.plateauIHM[i][j].getEntites().isEmpty() && 
						this.plateauIHM[i][j].getEntites().contains(game.getPersonnageSelectionne())) {
					this.plateauJPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.blue,2));
				} else {
					this.plateauJPanel[i][j].setBorder(BorderFactory.createLineBorder(Color.black,2));
				}
//				this.plateauJPanel[i][j].setBackground(Color.gray);
			}
		}
		
	}

	/**
	 * This function draw the updated data
	 */
	public void redrawGame() {
		//updatePlateau();
		JButton panCase;
		cleanEmpty();
		for (int i = 0; i < this.getTaillePlateau(); ++i) {
			for (int j = 0; j < this.getTaillePlateau(); ++j) {
				panCase = this.plateauJPanel[i][j];
				java.util.List<Entite> entites = this.plateauIHM[i][j].getEntites(); 
				if (!entites.isEmpty()) {
					panCase.setBackground(entites.get(0).getColor());
					for (Entite entite : entites) {
						panCase.setIcon(entite.getMyPicture());
					}
				}
				if (panCase == depart) { panCase.setBackground(Color.pink); }
			}
		}
		
	}
	
	/**
	 * This function is used in order to update the game directly on the display
	 */
	public void refresh() {
		if (game.getPersonnageSelectionne() != null &&
				game.getPersonnageSelectionne().getDeplacementActuel() == game.getPersonnageSelectionne().getDeplacementMax() && 
				game.getPersonnageSelectionne().getDeplacementMax() != 0) {
			game.finirTour();
			hasRoll = false;
			updateBandeau();
		}
		resetGame();
		redrawGame();
		updateBandeau();
		if (game.estTermine()) displayWinner();
	}
	
	/**
	 * This function updates all the date of all players
	 */
	public void updateBandeau(){
		selectionJ.setText(("Joueur: "+joueurNom[game.getIndiceJoueurCourant()]));
		if (game.getPersonnageSelectionne() == null) selection.setText("Personnage: ");
		if (game.getPersonnageSelectionne() instanceof Troll) {
			selection.setText("Personnage: Troll");
			
		}
		if (game.getPersonnageSelectionne() instanceof Dragon) selection.setText("Personnage: Dragon");
		Troll t;
		for (int j = 1; j <= Jeu.getNombreJoueurs(); ++j) {
			t = game.chercherTroll("Troll_" + j);
			if (t != null ) {
				this.playerTablePanel[1][j].setText(""+t.getScore());
				this.playerTablePanel[2][j].setText(""+t.getVies());
				this.playerTablePanel[3][j].setText(""+t.getMagie());
				this.playerTablePanel[4][j].setText(""+t.getBouclier());
			}
		}
			

		this.nbTurns.setText("Tour : " + game.getNumeroTour());
	}
	
	/**
	 * This function is an extension of the normal visible function
	 * @param visible
	 */
	public void visible(boolean visible) {
		setVisible(visible);
	}
	
	/**
	 * This is the name's player getter
	 * @return joueurNom
	 */
	public static String[] getPlayersNames() {
		return joueurNom;
	}
	
	/**
	 * This function is used to customize the name of a player
	 * @param pos
	 * @param name
	 */
	public static void setNom(int pos, String name) {
		joueurNom[pos] = name;
	}
	
	/**
	 * This function displays a little windows with a nice message for the winner
	 */
	public void displayWinner() {
		JOptionPane.showMessageDialog(this , "Game Over \n" + joueurNom[game.getIndiceJoueurCourant()] + " gagne");
	}
	
	
}
