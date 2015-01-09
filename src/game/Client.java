package game;

import java.util.Scanner;

public class Client {
	private static Player player1;
	private static Game game1;
	private static boolean cont = true;
	private static String input;
	private static Scanner butler = new Scanner(System.in);
	public static void main(String[] args) {
		
		
		System.out.print("Would you like to load a game? ");
		while (!(butler.hasNext("[ynYN]"))) {
			System.out.print("Please eneter a 'y' or an 'n': ");
			butler.nextLine();
		}
		if(butler.nextLine().equalsIgnoreCase("Y")) {
			player1 = new Player("", "");
			game1 = new Game(player1, true);
			game1.loadGame();
		}
		else  {
			createCharacter();
			game1 = new Game(player1, false);
			if(game1.isAutosave())
				game1.saveGame();
		}
		
		game1.showMap();
		
		do {
			enterCommand();
		} while(cont);
	}
	
	private static void createCharacter() {
		String characterName, characterClass;
		
		// Gets the name of the character
		System.out.print("What is your name, adventurer? ");
		characterName = butler.nextLine();
		while(characterName.length() <= 0) {
			System.out.println("I don't recognize that name!");
			System.out.print("What is your name, adventurer? ");
			characterName = butler.nextLine();
		}
		
		//Get character class
		System.out.print("Ah, " + characterName + "! Are you a Fighter, Leader, or Surivor? ");
		characterClass = butler.nextLine().toLowerCase();
		while (!(characterClass.equals("f") || characterClass.equals("fighter") 
				|| characterClass.equals("l") || characterClass.equals("leader") 
				|| characterClass.equals("s") || characterClass.equals("survivor"))) {
			System.out.print("What class was that? ");
			characterClass = butler.nextLine();
		}
		
		player1 = new Player(characterName, characterClass);
	}
	
	private static void enterCommand() {
		String input;
		boolean moved = false;
		System.out.print("What do you want to do, " + player1.getName()
				+ "? Type 'help' for commands: ");
		input = butler.nextLine();
		switch (input.toLowerCase()) {	
		case "help": case "help ": case " help ": 
		case " help": case "'help'": case "h":
			game1.showHelp();
			break;
			
		case "inventory": case " inventory": case "inventory ":
		case " inventory ": case "'inventory'": case "i":
			player1.showInventory();
			break;
			
		case "character": case " character": case "character ":
		case " character ": case "'character'": case "c":
			System.out.println(player1);
			break;
			
		case "save": case " save": case "save ":
		case " save ": case "'save'":
			if(!game1.isAutosave())
				game1.saveGame();
			break;
			
		case "map": case " map": case "map ":
		case " map ": case "'map'": case "m":
			game1.showMap();
			break;
			
		case "move up": case " move up": case "move up ":
		case " move up ": case "'move up'": case "w":
			if(game1.isValidY(player1.getY() - 1)) {
				player1.moveUp();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move down": case " move down": case "move down ":
		case " move down ": case "'move down'": case "s":
			if(game1.isValidY(player1.getY() + 1)) {
				player1.moveDown();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move right": case " move right": case "move right ":
		case " move right ": case "'move right'": case "d":
			if(game1.isValidX(player1.getX() + 1)) {
				player1.moveRight();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move left": case " move left": case "move left ":
		case " move left ": case "'move left'": case "a":
			if(game1.isValidX(player1.getX() - 1)) {
				player1.moveLeft();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
			
		case "autosave": case " autosave": case "autosave ":
		case " autosave ": case "'autosave'":
			game1.toggleAutosave();
			break;
			
		case "exit": case " exit": case "exit ":
		case " exit ": case "'exit'": case "e":
			cont = false;
			break;
		
		case "quit": case " quit": case "quit ":
		case " quit ": case "'quit'": case "q":
			cont = false;
			break;
			
		case "info": case " info": case "info ":
		case " info ": case "'info'":
			break;
		
		case "show full map":
			game1.showFullMap();
			
		default:
			if(input.startsWith("?"))
				Game.help(input);
			else
				System.out.println("Unknown Command.");
			enterCommand();
			break;
		}
		if (game1.isAutosave())
			game1.saveGame();
		
		if(moved)
			game1.showMap();
	}
}
