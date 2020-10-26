
public class Soleil extends Entite {
	
	
	
	public Soleil(double x, double y) {
		super(x, y);
		timer=new Timer(20000);

	}
	
	
	public void step() {
		
	}

	@Override
	public void dessine() {
		
		StdDraw.picture(this.position.getX(),this.position.getY(), "images/soleil.png", 0.1, 0.1);

	}

}
