package game;

import java.util.Scanner;

public class Client {
	private static Player player1;
	private static Game game1;
	private static boolean autosave;
	public static void main(String[] args) {
		Scanner butler = new Scanner(System.in);
		
		System.out.print("Would you like to load a game? (y/n) ");
		if(butler.nextLine().equalsIgnoreCase("Y")) {
			game1 = new Game(player1);
			game1.loadGame();
		}
		else  {
			createCharacter();
			game1 = new Game(player1);
		}
		
		game1.saveGame();
		game1.showMap();
		
		do {
			enterCommand();
		} while(true);
	}
	
	private static void createCharacter() {
		String characterName, characterClass;
		Scanner butler = new Scanner(System.in);
		
		// Gets the name of the character
		System.out.print("What is your name, aventurer? ");
		characterName = butler.nextLine();
		while(characterName.length() <= 0) {
			System.out.println("I don't recognize that name!");
			System.out.print("What is your name, aventurer? ");
			characterName = butler.nextLine();
		}
		
		//Get character class
		System.out.print("Ah, " + characterName + "! Are you a (F)ighter, (L)eader, or (S)urivor? ");
		characterClass = butler.nextLine().toLowerCase();
		while (!(characterClass.equals("f") 
				|| characterClass.equals("s")
				|| characterClass.equals("l") 
				|| characterClass.equals("fighter")
				|| characterClass.equals("leader") 
				|| characterClass.equals("survivor"))) {
			System.out.print("What class was that? ");
			characterClass = butler.nextLine();
		}
		
		player1 = new Player(characterName, characterClass);
	}
	
	private static void enterCommand() {
		String input;
		boolean moved = false;
		Scanner butler = new Scanner(System.in);
		System.out.print("What do you want to do, " + player1.getName()
				+ "? Type 'help' for commands: ");
		input = butler.nextLine();
		switch (input.toLowerCase()) {	
		case "help": case "help ": case " help ": 
		case " help": case "'help'": case "h":
			Game.showHelp();
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
			game1.saveGame();
			break;
			
		case "map": case " map": case "map ":
		case " map ": case "'map'": case "m":
			game1.showMap();
			break;
			
		case "move up": case " move up": case "move up ":
		case " move up ": case "'move up'": case "u":
			if(game1.isValidY(player1.getY() - 1)) {
				player1.moveUp();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move down": case " move down": case "move down ":
		case " move down ": case "'move down'": case "d":
			if(game1.isValidY(player1.getY() + 1)) {
				player1.moveDown();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move right": case " move right": case "move right ":
		case " move right ": case "'move right'": case "r":
			if(game1.isValidX(player1.getX() + 1)) {
				player1.moveRight();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "move left": case " move left": case "move left ":
		case " move left ": case "'move left'": case "l":
			if(game1.isValidX(player1.getX() - 1)) {
				player1.moveLeft();
				moved = true;
			} else
				System.err.println("That is not a valid move!");
			break;
			
		case "autosave": case " autosave":
			break;
			
		default:
			System.out.println("Unknown Command.");
			enterCommand();
			break;
		}
		
		game1.saveGame();
		
		if(moved)
			game1.showMap();
	}
}
