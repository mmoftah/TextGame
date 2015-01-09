package game;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

public class Inventory implements Serializable {
	private static final long serialVersionUID = -8426068221398718341L;
	private List<String> inventory;
	
	public Inventory(String charClass) {
		inventory = new ArrayList<String>();
		switch(charClass) {
		case "Fighter":
			inventory.add("Medium Health Potion (30)");
			inventory.add("Medium Strength Potion (15)");
			inventory.add("Steel Sword");
			break;
			
		case "Leader":
			inventory.add("Medium Health Potion (30)");
			inventory.add("Dagger");
			break;
			
		case "Survivor":
			inventory.add("Medicinal Herbs");
			inventory.add("Bow");
			inventory.add("Map");
			break;
		}
	}
	
	public List<String> getInventory() { return inventory; }
}
