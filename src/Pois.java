
public class Pois extends Entite{
	private static final double TRUC_BOUGE_X = 0.0075;
	
	public Pois(double x, double y) {
		super(x, y);
	
	}

	public void step() {
		this.position.setX(this.position.getX() + TRUC_BOUGE_X);
		
	}

	public void dessine() {
		
		StdDraw.picture(this.position.getX(), this.position.getY(), "images/pois.png", 0.1, 0.1);
		
	}
}
