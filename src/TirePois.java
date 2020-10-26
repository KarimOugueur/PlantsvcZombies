import java.util.LinkedList;
import java.util.List;
import java.util.Iterator;

public class TirePois extends Entite {

	private Timer timerP;
	
	public List<Pois> pois;
	private Position grille[][]= new Position[9][5]; ;
	static boolean occupeT[][]=new boolean[9][5];
	
	public TirePois(double x, double y) {
		super(x, y);
		this.pois = new LinkedList<Pois>();
		timerP = new Timer(1500);
		this.pv=300;
		
		
		for (int i = 0; i < 9; i++) {

			for (int j = 0; j < 5; j++) {
				grille[i][j] = new Position(0.055 + (i * 0.11), 0.1 + (j * 0.2));
			
			}
		}
	}
	

	public void remplirCase(Position p) {
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[i].length; j++) {
				if (p.getX() == grille[i][j].getX() && p.getY() == grille[i][j].getY()) {
					occupeT[i][j] = true;
					
				}
				else {
					occupeT[i][j] = false;
				}
				
			}
			
		}
		
	}
	

	

	public boolean casePleine(Position p) {
		Position pp = plusProche(p.getX(),p.getY());
		
		for (int i = 0; i < grille.length; i++) {

			for (int j = 0; j < grille[i].length; j++) {
				if(pp.getX() == grille[i][j].getX() && pp.getY() == grille[i][j].getY()) {
					return occupeT[i][j];
				}

			}
		}
		return false;
		
	}
	
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
	
	public double distance(Position t, Position k) {
		return Math.sqrt(Math.pow(t.getX() - k.getX(), 2) + Math.pow(t.getY() - k.getY(), 2));
	}
	
	@Override
	public void step() {
		if (timerP.hasFinished()) {
			this.pois.add(new Pois(this.position.getX(),this.position.getY()));
			timerP.restart(); 
		}
	
		Iterator<Pois> iterator = pois.iterator();
		while (iterator.hasNext() ) {
		    Pois p = iterator.next();
		    p.step();
		    if (p.getX()>1.0) {
		    	
		        iterator.remove();
		    }
		}
		for(Pois p : this.pois) {
			Position pP = plusProche(p.getX(),p.getY());
			remplirCase(pP);
			
		}
		
		
		
	}
	
	
	@Override
	public void dessine() {
		
		
		
		StdDraw.picture(this.position.getX(),this.position.getY(), "images/pieShooter.png",0.1,0.1);
		for (Pois p : this.pois) {
			p.dessine();
			
	
		}
		
	}
}
