package game;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game {
	private static final int MAPX = 52, MAPY = 52;
	private Player player1;
	private String saveName;
	private String[][] map;
	private String[][] displayedMap;
	private static String[] commands = { 
		"(h)elp: brings up this menu",
		"(i)nventory: shows you all the items you have",
		"(s)ave: saves the game", 
		"(m)ap: shows you the map",
		"(c)haracter: gives you basic info on the character",
		"move (r)ight: moves you right on the map",
		"move (l)eft: moves you left on the map",
		"move (u)p: moves you up on the map",
		"move (d)own: moves you down on the map" 
	};
	private static String[] mapKey = {
		"'#': border",
		"'-': empty space"
	};
	
	public Game(Player player) {
		player1 = player;
		
		createMap();
	}
	
	public void saveGame() {
		saveName = System.getProperty("user.dir");
		saveName = saveName.concat("\\saved games\\SavedGame.sav");
		try { // Catch errors in I/O if necessary.
				// Open a file to write to, named "SaveGame.sav".
			FileOutputStream saveFile = new FileOutputStream(saveName);

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			save.writeObject(player1.getName());
			save.writeObject(player1.getCharacterClass());
			save.writeObject(player1.getPotions());
			save.writeObject(player1.getFood());
			save.writeObject(player1.getWater());
			save.writeObject(player1.getWeapons());
			save.writeObject(player1.getPeople());
			save.writeObject(player1.getPotions());
			save.writeObject(player1.getX());
			save.writeObject(player1.getY());
			save.writeObject(map);
			save.writeObject(displayedMap);
			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.println("(Game Saved!)");
		} catch (IOException exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}
	
	public void loadGame() {
		Scanner butler = new Scanner(System.in);
		String dir = System.getProperty("user.dir");
		String fileName;
		dir = dir.concat("\\saved games");
		File[] fileList = new File(dir).listFiles();
		List<String> files = new ArrayList<String>();
		for (File f : fileList)
			if (f.isFile())
				files.add(f.getName());
		
		if (fileList.length == 0) {
			System.out.println("Sorry, I could not find any saved games :(");
			return;
		}
		
		System.out.println("Saved Games:");
		for(String f : files)
			System.out.println(f);
		
		boolean validFileName = false;
		do {
			System.out.print("Which game would you like to load? ");
			fileName = butler.nextLine();
			for(String f : files)
				if(fileName.equalsIgnoreCase(f))
					validFileName = true;
			if(!validFileName)
				System.err.println("File not found!");
		} while(!validFileName);
		
		saveName = dir.concat("\\" + fileName);
		
		try {
			// Open file to read from, named SaveGame.sav.
			FileInputStream saveFile = new FileInputStream(saveName);

			// Create an ObjectInputStream to get objects from save file.
			ObjectInputStream save = new ObjectInputStream(saveFile);

			// Now we do the restore.
			// readObject() returns a generic Object, we cast those back
			// into their original class type.
			// For primitive types, use the corresponding reference class.
			player1.updateCharacterName((String) save.readObject());
			player1.updateCharacterClass((String) save.readObject());
			player1.updatePotions((Integer) save.readObject());
			player1.updateFood((Integer) save.readObject());
			player1.updateWater((Integer) save.readObject());
			player1.updateWeapons((Integer) save.readObject());
			player1.updatePeople((Integer) save.readObject());
			player1.updateVehicles((Integer) save.readObject());
			player1.updateX((Integer) save.readObject());
			player1.updateY((Integer) save.readObject());
			map = (String[][]) save.readObject();
			displayedMap = (String[][]) save.readObject();

			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.println("Game Loaded!");
		} catch (IOException | ClassNotFoundException exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	}
	
	public static void showHelp() {
		for(int i = 0; i < commands.length; i ++)
			System.out.println(commands[i]);
	}
	
	public void createMap() {
		//Creates feature map
		map = new String[MAPY][MAPX];
		
		for(int x = 0; x < MAPX; x ++)
			map[0][x] = "#";
		
		for(int x = 0; x < MAPX; x ++)
			map[51][x] = "#";
		
		for(int y = 0; y < MAPY; y ++)
			map[y][0] = "#";
		
		for(int y = 0; y < MAPY; y ++)
			map[y][51] = "#";
		
		for(int y = 1; y <= 50; y ++)
			for(int x = 1; x <= 50; x ++)
				map[y][x] = "-";
		
		//Creates displayed map
		displayedMap = new String[MAPY][MAPX];
		
		for(int y = 0; y < MAPY; y ++)
			for(int x = 0; x < MAPX; x ++)
				displayedMap[y][x] = "?";
	}
	
	public void checkMap() {
		for(int x = 0; x < MAPX; x ++) {
			System.out.println();
			for(int y = 0; y < MAPY; y ++)
				System.out.print(map[y][x] + " ");
		}
		System.out.println();
	}
	
	/*public void checkMap() {
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream("Map");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));

		String strLine;
		int arraySize = 65;
		String array[][] = new String[arraySize][];
		int index = 0;
		try {
			while ((strLine = br.readLine()) != null) {
				if (index > arraySize - 1) {
					System.out.println("Error : Increase array size !");
					break;
				}
				array[index] = strLine.split(" ");
				index++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		for (int i = playerX - 4; i <= playerX + 4; i++) {
			if (array[i] != null) {
				for (int j = playerY - 4; j <= playerY + 4; j++) {
					if (i == playerX && j == playerY) {
						System.out.print("P ");
					} else {
						System.out.print(array[i][j] + " ");
					}
				}
				System.out.println(" ");
			}
		}
	}*/
	
	public boolean isValidX(int x) {
		if(map[player1.getY()][x] == "#" || map[player1.getY()][x] == "L")
			return false;
		return true;
	}
	
	public boolean isValidY(int y) {
		if(map[y][player1.getX()] == "#" || map[y][player1.getX()] == "L")
			return false;
		return true;
	}
	
	// Shows the 9 X 9 area around the player
	public void showMap() {
		revealArea();
		int leftBoundary = player1.getX() - 4, topBoundary = player1.getY() - 4;
		
		if(leftBoundary < 0) { leftBoundary = 0; }
		else if(leftBoundary + 9 > MAPX) { leftBoundary = MAPX - 9; }
		
		if(topBoundary < 0) { topBoundary = 0; }
		else if(topBoundary + 9 > MAPY) { topBoundary = MAPY - 9; }
		
		for(int y = 0; y < 9; y ++) {
			System.out.println();
			for(int x = 0; x < 9; x ++) {
				if(topBoundary + y == player1.getY() && leftBoundary + x == player1.getX())
					System.out.print("P ");
				else
					System.out.print(displayedMap[topBoundary + y][leftBoundary + x] + " "); 
			}
		}
		System.out.println();
	}
	
	public void revealArea() {
		int x = player1.getX(), y = player1.getY();
		
		displayedMap[y][x] = map[y][x];
		for(int y1 = -1; y1 <= 1; y1 ++) {
			for(int x1 = -1; x1 <= 1; x1 ++) {
				try {
					displayedMap[y + y1][x + x1] = map[y + y1][x + x1];
				} catch(IndexOutOfBoundsException e) { }
			}
		}
	}
}
