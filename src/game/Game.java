package game;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

@SuppressWarnings("unused")
public class Game {
	private static int MAPX = 52, MAPY = 52;
	private Player player1;
	private String saveName;
	private String[][] map;
	private String[][] displayedMap;
	private static boolean autosave;
	private static Scanner butler = new Scanner(System.in);
	private static String[] commands = { 
		"(h)elp: brings up this menu",
		"(i)nventory: shows you all the items you have",
		"(s)ave: saves the game", 
		"(m)ap: shows you the map",
		"(c)haracter: gives you basic info on the character",
		"move up (w): moves you up on the map",
		"move down (s): moves you down on the map",
		"move left (a): moves you left on the map",
		"move right (d): moves you right on the map",
		"info ****: get info on anything in the game (?)",
		"(e)xit: exit the current game",
		"(q)uit: quit the program"
	};
	private static String[] mapKey = {
		"'#': border",
		"'-': empty space"
	};
	
	public Game(Player player, boolean loaded) {
		player1 = player;
		if (!loaded) {
			System.out.print("Would you like to enable autosave? ");
			while (!(butler.hasNext("[ynYN]"))) {
				System.out.print("Please eneter a 'y' or an 'n': ");
				butler.nextLine();
			}
			if (butler.nextLine().equalsIgnoreCase("y"))
				autosave = true;
			else
				autosave = false;
		}
		createMap();
	}
	
	public void saveGame() {
		saveName = System.getProperty("user.dir");
		saveName = saveName.concat("\\saved games\\" + player1.getName() + ".sav");
		try { // Catch errors in I/O if necessary.
				// Open a file to write to, named "SaveGame.sav".
			FileOutputStream saveFile = new FileOutputStream(saveName);

			// Create an ObjectOutputStream to put objects into save file.
			ObjectOutputStream save = new ObjectOutputStream(saveFile);

			// Now we do the save.
			save.writeObject(player1.getName());
			save.writeObject(player1.getCharacterClass());
			save.writeObject(player1.getFood());
			save.writeObject(player1.getWater());
			save.writeObject(player1.getInventory());
			save.writeObject(player1.getX());
			save.writeObject(player1.getY());
			save.writeObject(autosave);
			save.writeObject(map);
			save.writeObject(displayedMap);
			
			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.println("(Game Saved!)");
		} catch (IOException exc) {
			exc.printStackTrace(); // If there was an error, print the info.
		}
	} 
	
	public int loadGame() {
		String dir = System.getProperty("user.dir") // current directory
				+ "\\saved games";					// directory of saved games
		String fileName; // name of save file
		File[] fileList = new File(dir).listFiles(); 	// list of files in saved games directory
		List<String> files = new ArrayList<String>();	// list of names of files
		for (File f : fileList) 					// for every file in the file list
			if (f.isFile()) 						// check if the file is a file (not directory)
				if(f.getName().endsWith(".sav")) {	// check if the file is a ".sav"
					String fName = f.getName().substring(0, f.getName().length() - 4);
					files.add(fName); 				// add the name of the file to the ArrayList
				}
		
		if (fileList.length == 0) {	// if there are no ".sav" files in the saved games directory
			System.out.println("Sorry, I could not find any saved games :("
					+ "\nMake sure your saved games are in: "
					+ "\n" + dir);
			return -1;
		}
		
		// Print out all the saved game file names
		System.out.println("Saved Games:");
		for(String f : files)
			System.out.println(f);
		
		boolean validFileName = false, goBack = false;
		do {
			System.out.print("Which game would you like to load? ");
			fileName = butler.nextLine();
			for(String f : files)
				if(fileName.equalsIgnoreCase(f))
					validFileName = true;
			if(fileName.equalsIgnoreCase("exit")
				|| fileName.equalsIgnoreCase("back")) {
				validFileName = true;
				goBack = true;
			}
			if(!validFileName) {
				System.err.println("File not found!");
				System.out.print("Would you like to load another game? (y/n) ");
				while (!(butler.hasNext("[ynYN]"))) {
					System.out.print("Please eneter a 'y' or an 'n': ");
					butler.nextLine();
				}
				if (butler.nextLine().equalsIgnoreCase("n"))
					return -1;
			}
		} while(!validFileName);
		
		saveName = dir.concat("\\" + fileName + ".sav");
		System.out.println("Loading '" + saveName + "'");
		
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
			player1.updateFood((Integer) save.readObject());
			player1.updateWater((Integer) save.readObject());
			player1.updateInventory((Inventory) save.readObject());
			player1.updateX((Integer) save.readObject());
			player1.updateY((Integer) save.readObject());
			autosave = (boolean) save.readObject();
			map = (String[][]) save.readObject();
			displayedMap = (String[][]) save.readObject();

			// Close the file.
			save.close(); // This also closes saveFile.
			System.out.println("Game Loaded!");
		} catch (IOException | ClassNotFoundException exc) {
			exc.printStackTrace(); // If there was an error, print the info.
			try { writeErrors(player1.getName(), exc); }
			catch(IOException e) { e.printStackTrace(); }
			return -2;
		}
		catch (ClassCastException exc) {
			System.err.println("Game could not be loaded.");
			return -3;
		}
		
		return 0;
	}
	
	public boolean isAutosave() { return autosave; }
	
	public void toggleAutosave() { autosave = !autosave; }
	
	public void showHelp() {
		for(int i = 0; i < commands.length; i ++) {
			if(i == 10)
				System.out.println("autosave: toggle autosave " + 
						(autosave ? "(currently enabled)" : "(currently disabled)"));
			System.out.println(commands[i]);
		}
	}
	
	public static void writeErrors(String playerName, Exception e) throws IOException {
		Date today = new Date();
		SimpleDateFormat simpleDate = new SimpleDateFormat("dd-MM-yyyy");
		String fileName = System.getProperty("user.dir") + "\\logs\\"
				+ playerName + "-" + simpleDate.format(today) + ".txt";
		
		// TODO check if the file already exists
		File errorFile = new File(fileName);
		int i = 1;
		while(errorFile.exists()) {
			fileName = fileName + i;
			errorFile = new File(fileName);
			i ++;
		}
		
		System.out.println(fileName);
		errorFile.createNewFile();
		
		BufferedWriter errorStream = new BufferedWriter(new FileWriter(errorFile));
		errorStream.write(today.toString());
		errorStream.newLine();
		errorStream.write(e.toString());
		errorStream.close();
	}
	
	/*
	 * Creates the map with all the features and
	 * the map that the player sees.
	 */
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
		String array[][] = new String[arraySize][];
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
	
	// Load a custom map from a txt file
	public void loadMap() throws IOException {
		String mapFileName;
		
		Scanner yScan = new Scanner(new File("Map.txt"));
		Scanner xScan;

		// Check to see if the map is valid (all rows have same number of
		// spaces)
		int yCount = 0, xCount = 0, tempXCount = 0;
		boolean validMapFile = true;
		while (yScan.hasNextLine()) { // checks if there is another row
			yCount++;
			xScan = new Scanner(yScan.nextLine()); // sets xScan to scan the current row
			xScan.useDelimiter(""); //xScan will scan each character individually
			tempXCount = 0; 
			while (xScan.hasNext()) {
				tempXCount++;
				xScan.next();
			}
			if (xCount == 0)
				xCount = tempXCount;
			else if (xCount != tempXCount) {
				validMapFile = false;
				break;
			}
		}
		
		if (!validMapFile) // if its not a valid map
			System.err.println("This is not a valid map file.");
		else { // if it is a valid map 
			System.out.println("xCount: " + xCount + "\nyCount: " + yCount);
			
			MAPX = xCount;
			MAPY = yCount;
			
			yScan = new Scanner(new File("Map.txt")); // Reset yScan
			String[] mapRow; // holds the array of characters in the current row
			map = new String[MAPY][MAPX];
			yCount = 0;
			while (yScan.hasNextLine()) {
				xCount = 0;
				xScan = new Scanner(yScan.nextLine()); // sets xScan to scan the current row
				xScan.useDelimiter(""); //xScan will scan each character individually
				mapRow = new String[MAPX];
				while (xScan.hasNext()) {
					mapRow[xCount] = xScan.next();
					xCount ++;
				}
				map[yCount] = new String[MAPY];
				map[yCount] = mapRow;
				yCount ++;
			}
		}
		
		yScan.close();
	}

	// Returns the character at the space the player is going to ove to
	public String checkNextMove(String direction) {
		switch (direction) {
		case "up":
			return map[player1.getY() - 1][player1.getX()];

		case "down":
			return map[player1.getY() + 1][player1.getX()];

		case "left":
			return map[player1.getY()][player1.getX() - 1];

		case "right":
			return map[player1.getY()][player1.getX() + 1];

		default:
			return "#";
		}
	}
	
	// Checks to see if x is a valid space to move to
	public boolean isValidX(int x) {
		//System.out.println(map[player1.getY()][x]);
		if(map[player1.getY()][x].equals("#") || map[player1.getY()][x].equals("L")) 
			return false;
		return true;
	}
	
	// Checks to see if y is a valid space to move to
	public boolean isValidY(int y) {
		//System.out.println(map[y][player1.getX()]);
		if(map[y][player1.getX()].equals("#") || map[y][player1.getX()].equals("L"))
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
	
	// Reveals the area around the player
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

	public void showFullMap() {
		for(int y = 0; y < MAPY; y ++) {
			System.out.println();
			for(int x = 0; x < MAPX; x ++)
				System.out.print(map[y][x] + " ");
		}
	}

	public static void help(String input) {
		// TODO Auto-generated method stud
		Scanner helpButler = new Scanner(input);
		helpButler.useDelimiter("");
		
		while(helpButler.hasNext("?") || helpButler.hasNext(" "))
			helpButler.next();
		
		System.out.println(helpButler.next());
		
		helpButler.close();
	}
}
