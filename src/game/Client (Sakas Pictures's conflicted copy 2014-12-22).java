package game;

import java.util.Scanner;

public class Client {
	private static Player player1;
	private static Game game1;
	public static void main(String[] args) {
		Scanner butler = new Scanner(System.in);
		String characterName, characterClass;
		System.out.print("What is your name, aventurer? ");
		characterName = butler.nextLine();
		System.out.print("Ah," + characterName + "! Are you a (F)ighter, (L)eader, or (S)urivor? ");
		characterClass = butler.nextLine();
		player1 = new Player(characterName, characterClass);
		game1 = new Game(player1.getName(), player1.getCharacterClass(),
				player1.getPotions(), player1.getFood(), player1.getWater(),
				player1.getWeapons(), player1.getPeople(),
				player1.getVehicles(), player1.getX(), player1.getY());
		
		enterCommand();
		butler.close();
	}
	
	public static void enterCommand() {
		String input;
		Scanner butler = new Scanner(System.in);
		System.out.print("What do you want to do, " + player1.getName()
				+ "? Type 'help' for commands: ");
		input = butler.nextLine();
		switch (input.toLowerCase()) {
		default:
			System.out.println("Unknown Command.");
			enterCommand();
			break;
			
		case "help": case "help ": case " help ": 
		case " help": case "'help'": case "h":
			Game.showHelp();
			break;
			
		case "inventory": case " inventory": case "inventory ":
		case " inventory ": case "'inventory'": case "i":
			//inventory();
			break;
			
		case "save": case " save": case "save ":
		case " save ": case "'save'":
			game1.updateSave(player1.getName(), player1.getCharacterClass(),
					player1.getPotions(), player1.getFood(), player1.getWater(),
					player1.getWeapons(), player1.getPeople(),
					player1.getVehicles(), player1.getX(), player1.getY());
			break;
			
		case "map": case " map": case "map ":
		case " map ": case "'map'": case "m":
			//checkMap();
			break;
			
		case "move up": case " move up": case "move up ":
		case " move up ": case "'move up'": case "u":
			player1.moveUp();
			break;
			
		case "move down": case " move down": case "move down ":
		case " move down ": case "'move down'": case "d":
			player1.moveDown();
			break;
			
		case "move right": case " move right": case "move right ":
		case " move right ": case "'move right'": case "r":
			player1.moveRight();
			break;
			
		case "move left": case " move left": case "move left ":
		case " move left ": case "'move left'": case "l":
			player1.moveLeft();
			break;
		}
		
		butler.close();
	}
}
