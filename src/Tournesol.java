
public class Tournesol extends Entite{

	
	public Tournesol(double x, double y) {
		super(x, y);
		
		this.pv=300;
		timer=new Timer(24000);
	}
	
	
	
	@Override
	public void step() {
		
	}

	@Override
	public void dessine() {
	
		StdDraw.picture(this.position.getX(),this.position.getY(), "images/tournesol.png",0.1,0.1);
			
		
		
	}
}
