
public class Zombie extends Entite {

	private static final double TRUC_BOUGE_X = 0.0005;
	private boolean statut;
	Timer timerAtt= new Timer(1000);
	public Zombie(double x, double y) {
		super(x, y);
		this.statut = true;
		this.pv=200;
	}

	public void step() {
		
		if (statut) {
			this.position.setX(this.position.getX() - TRUC_BOUGE_X);
		}
		else {
			this.position.setX(this.position.getX());
		}
		
	
	}
	
	public void stop() {
		statut=false;
	}
	public void go() {
		statut=true;
	}

	public void dessine() {

		StdDraw.picture(this.position.getX(), this.position.getY(), "images/zombie1.png", 0.1, 0.1);
		StdDraw.picture(this.position.getX(), this.position.getY(), "images/zombie2.png", 0.1, 0.1);
		}
}
