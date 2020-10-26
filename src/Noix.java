
public class Noix extends Entite{

	public Noix(double x, double y) {
		super(x, y);
		this.pv=1500;
	}
	

	@Override
	public void step() {
	}

	@Override
	public void dessine() {
		
		
		
		StdDraw.picture(this.position.getX(),this.position.getY(), "images/noix.png",0.06,0.06);
		
	}
}
