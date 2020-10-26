import java.awt.*;

public class Main {

	public static void main(String[] args) {
		Font font = new Font("Arial", Font.BOLD, 30);
		GameWorld world = new GameWorld();
		Dimension ecran = Toolkit.getDefaultToolkit().getScreenSize();
		int height = ecran.height;
		int width = ecran.width;
		Position p1 = new Position(0.09, 0.09);
		System.out.println(world.plusProche(0.09, 0.09));

		// reglage de la taille de la fenetre de jeu, en pixels (nb: un écran fullHD =
		// 1980x1050 pixels)
		StdDraw.setCanvasSize(width, height);

		// permet le double buffering, pour permettre l'animation
		StdDraw.enableDoubleBuffering();

		// la boucle principale de notre jeu
		while (!GameWorld.gameLost() && !GameWorld.gameWon()) {
			// Efface la fenetre graphique
			StdDraw.clear();

			// Capture et traite les eventuelles interactions clavier du joueur
			if (StdDraw.hasNextKeyTyped()) {
				char key = StdDraw.nextKeyTyped();
				world.processUserInput(key);
			}

			if (StdDraw.isMousePressed()) {
				world.processMouseClick(StdDraw.mouseX(), StdDraw.mouseY());
			}

			world.step();

			// dessine la carte
			world.dessine();
			

			// Montre la fenetre graphique mise a jour et attends 20 millisecondes
			StdDraw.show();
			StdDraw.pause(20);

		}

		if (GameWorld.gameWon())
			System.out.println("Game won !");
		if (GameWorld.gameLost())
			
			System.out.println("Game lost...");

	}

}
