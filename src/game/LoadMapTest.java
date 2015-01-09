package game;

import java.io.IOException;

public class LoadMapTest {
	public static void main(String[] args) {
		Game game1 = new Game(null, false);
		try { game1.loadMap(); } 
		catch (IOException e) { e.printStackTrace(); }
		game1.showFullMap();
	}
}
