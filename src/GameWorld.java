import java.util.*;
import java.util.Iterator;

//import com.sun.xml.internal.ws.api.addressing.AddressingVersion.EPR;

import java.awt.Font;

public class GameWorld {

	// l'ensemble des entites, pour gerer (notamment) l'affichage
	private static List<Entite> entites;
	private Timer timer = new Timer(6500);
	private Timer timerz = new Timer(20000);


	private int soleils = 50;

	// Pour savoir si la partie est gagnee ou pas
	private static boolean gameWon;
	// Idem pour savoir si le jeu est perdu (si le jeu n'est ni gagne ni perdu, il
	// est en cours)
	private static boolean gameLost;
	private double a, b;
	
	private Position grille[][] = new Position[9][5];
	private boolean cases[][] = new boolean[9][5];
	
	
	private static List<Zombie> zombies; 
	private static List<Tournesol> tournesols; 
	private static List<TirePois> tirepois; 
	List<Pois> poisTotal;
	// constructeur, il faut initialiser notre monde virtuel
	public GameWorld() {
		gameWon = false;
		gameLost = false;

		// On cree les collections Entite, Zombie, Tournesol, Tirepois, pois 
		entites = new LinkedList<Entite>();
		zombies= new LinkedList<Zombie>();
		tournesols= new LinkedList<Tournesol>();
		tirepois=new LinkedList<TirePois>();
		poisTotal= new LinkedList<Pois>();
	
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 5; j++) {
				grille[i][j] = new Position(0.055 + (i * 0.11), 0.1 + (j * 0.2));
			}
		}
	}
	
	/**
	 * Gestion des interactions clavier avec l'utilisateur
	 * 
	 * @param key Touche pressee par l'utilisateur
	 */
	public void processUserInput(char key) {
		switch (key) {
		case 't': // t comme Tournesol 

			System.out.println("Le joueur veut planter un Tournesol...");
			if (soleils >= 50 && !casePleine(new Position(a, b))) {
				entites.add(new Tournesol(a, b));
				tournesols.add(new Tournesol(a, b));
				remplirCase(new Position(a, b));
				soleils -= 50;
			}
			break;
		case 'p': // p --> Tire-Pois
			System.out.println("Le joueur veut planter un Tire-Pois...");
			if (soleils >= 100 && !casePleine(new Position(a, b))) {
				entites.add(new TirePois(a, b));
				tirepois.add(new TirePois(a, b));
				remplirCase(new Position(a, b));
				soleils -= 100;
			}
			break;
		case 'n':  // n --> Noix
			System.out.println("Le joueur veut planter une Noix...");
			if (soleils >= 50 && !casePleine(new Position(a, b))) {
				entites.add(new Noix(a, b));
				remplirCase(new Position(a, b));
				soleils -= 50;
			}
			break;		
		default:
			System.out.println("Touche non prise en charge");
			break;
		}
	}

	/**
	 * Gestion des interactions souris avec l'utilisateur (la souris a été
	 * cliquée)
	 * @param x position en x de la souris au moment du clic
	 * @param y position en y de la souris au moment du clic
	 */
	public void processMouseClick(double x, double y) {
		System.out.println("La souris a été cliquée en : " + x + " - " + y);

		Position Blocproche = plusProche(x, y);
		a = Blocproche.getX();
		b = Blocproche.getY();

		// recup�ration de la position du clic et comparaison avec la position du soleil 
		//s'ils ont les memes positions et que l'entite c'est un soleil alors on supprime
		// et on rajoute 25 soleils au joueur
		
		if (StdDraw.isMousePressed()) {
			
			Iterator<Entite> iterator = entites.iterator();
			while (iterator.hasNext() ) {
			    Entite e = iterator.next();
				double psx= e.position.getX();
				double psy= e.position.getY();
			

				if (a==psx && ((b-0.07)==psy||(b)==psy)  && e instanceof Soleil) {
					soleils+=25;
					iterator.remove();
					
				}

			}
		}
	}
	
	/**
	 *  calcul la distance entre deux positions
	 * @param t une position 
	 * @param k une position
	 * @return la distace entre les deux positions
	 */
	public double distance(Position t, Position k) {
		return Math.sqrt(Math.pow(t.getX() - k.getX(), 2) + Math.pow(t.getY() - k.getY(), 2));
	}
	
	/**
	 *  la position la plus proche lors du clic 
	 * @param cx coordonn�e sur l'axe des x
	 * @param cy coordonn�e sur l'axe des x
	 * @return la position la plus au clic 
	 */
	public Position plusProche(double cx, double cy) {
		Position souris = new Position(cx, cy);
		double distanceMin = 100;
		Position positionMin = new Position(0, 0);

		for (int i = 0; i < grille.length; i++) {

			for (int j = 0; j < grille[i].length; j++) {
				if (distance(grille[i][j], souris) < distanceMin) {
					positionMin = grille[i][j];
					distanceMin = distance(grille[i][j], souris);
				}

			}
		}

		return positionMin;

	}
	
	/***
	 *  regarde si la case est pleine ou non
	 * @param p une position 
	 * @return  true si la case est pleine sinon false 
	 */
	public boolean casePleine(Position p) {

		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				if (p.getX() == grille[i][j].getX() && p.getY() == grille[i][j].getY() && cases[i][j] == false) {
					return false;
				}

			}
		}
		return true;
	}
	
	/**
	 * remplire une case du tableau
	 * @param p une position
	 */
	public void remplirCase(Position p) {
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				if (p.getX() == grille[i][j].getX() && p.getY() == grille[i][j].getY()) {
					cases[i][j] = true;
				}

			}
		}
	}

	
	

	// on fait bouger/agir toutes les entites
	public void step() {
		for (Entite entite : this.entites)
			entite.step();
	}

	// dessine les entites du jeu
	
	public void dessine() {

		// Ici vous pouvez afficher du décors

		StdDraw.picture(0.5, 0.5, "images/jardin.png", 1, 1);
		Font font = new Font("Arial", Font.BOLD, 30);
		StdDraw.setFont(font);
		StdDraw.text(0.1, 0.95, "Soleils:"+soleils);
	
	// Soleil 
		if (timer.hasFinished() == true) {

			double x = Math.random();
			double y = Math.random();
			Position k = plusProche(x, y);
			entites.add(new Soleil(k.getX(), k.getY()));
			
			timer = new Timer(6500);
			timer.restart();

		}
		
		Iterator<Entite> iterator = entites.iterator();
		while (iterator.hasNext() ) {
		    Entite e = iterator.next();
	 //supprimer un soleil de plus de 20 secondes
			if ( e instanceof Soleil && e.timer.hasFinished()) {
				soleils+=25;
				iterator.remove();				
			}
			
			if(e.pv<=0&& !(e instanceof Soleil)) {
		    	iterator.remove();
		    }
					
		}
		Iterator<Tournesol> iteratorT = tournesols.iterator();
		while (iteratorT.hasNext() ) {
		     Tournesol t = iteratorT.next();
		     
		     if (t.timer.hasFinished()&& t.pv>0) {
					t.timer.restart();
					entites.add(new Soleil(t.getX(), t.getY()-0.07));
				}
		}	
		
	// instances de zombie

		if (timerz.hasFinished()) {

			double m = Math.random();

			Position n = plusProche(1.0, m);
			zombies.add(new Zombie(1.0, n.getY()));
			
			timerz = new Timer(15000);
			timerz.restart();
		}
	// attaque des zombies + pv 
		
		for (Zombie z : zombies) {
			if(z.position.getX()<=0) {
				StdDraw.text(0.5, 0.5, "Game lost");
				gameLost();
			}
			
			Position posZ= plusProche(z.getX(),z.getY());
		   for(TirePois t : tirepois) {
			   
			
		     if(t.casePleine(z.position)) {
		    	 
		    	 z.pv-=20;
		     }
		   }
			if (casePleine(posZ)) {
				z.stop();
				
				for(Entite e : entites) {
					Position posP= plusProche(e.getX(),e.getY());
					if((e instanceof Tournesol || e instanceof Noix || e instanceof TirePois)&& posZ.getX()==posP.getX() &&
							posZ.getY()==posP.getY()&& z.timerAtt.hasFinished()) {
						
						e.pv-=30;
						for (Tournesol t : tournesols) {
							t.pv-=30;
													
						}
						z.timerAtt.restart();
						if(e.pv<=0)
							for (int i = 0; i < grille.length; i++) {
								for (int j = 0; j < grille[i].length; j++) {
									if (posZ.getX() == grille[i][j].getX() && posZ.getY() == grille[i][j].getY()) {
										cases[i][j] = false;
										z.go();										
									}

								}
						}
					}
			}
		}
	}
		
		Iterator<Zombie> iteratorZ = zombies.iterator();
		while (iteratorZ.hasNext() ) {
		     Zombie t = iteratorZ.next();
		     
		     if ( t.pv<=0) {
					
					iteratorZ.remove();
				}
		}
			
		entites.removeAll(zombies);
		entites.addAll(zombies);
	
	// affiche les entites
		for (Entite entite : entites)	
			entite.dessine();			
}
	
	
	public static boolean gameWon() {
		return gameWon;
	}

	public static boolean gameLost() {
		return gameLost;
	}

}
