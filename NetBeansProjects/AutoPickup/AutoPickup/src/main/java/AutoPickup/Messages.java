package AutoPickup;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class Messages {
	public static String PICKUP_DISABLE;
	public static String PICKUP_ENABLE;
	public static String COMMAND_BLOCK;
	public static String COMMAND_BLOCK_FAIL;
	public static String COMMAND_SMELT;
	public static String COMMAND_SMELT_FAIL;
	public static String FULL_INVENTORY;
	public static String PREFIX;

	public static void setUp() {
		Main.MessageConfig = new SuperYaml(Main.dataFolder + "/Messages.yml");

		if ((boolean) easyConfig("Prefix", false))
			PREFIX = ChatColor.WHITE + "[" + ChatColor.AQUA + "AutoPickup" + ChatColor.WHITE + "] " + ChatColor.RESET;
		else
			PREFIX = "";
		COMMAND_BLOCK = PREFIX + ChatColor.GREEN
				+ easyConfig("Messages.Block Command", "Your inventory has been blocked!");
		COMMAND_BLOCK_FAIL = PREFIX + ChatColor.RED
				+ easyConfig("Messages.Block Fail", "Your inventory had nothing to be blocked!");
		COMMAND_SMELT = PREFIX + ChatColor.GREEN
				+ easyConfig("Messages.Smelt Command", "Your inventory has been smelted!");
		COMMAND_SMELT_FAIL = PREFIX + ChatColor.GREEN
				+ easyConfig("Messages.Smelt Fail", "Your inventory has been smelted!");
		FULL_INVENTORY = PREFIX + ChatColor.RED + easyConfig("Messages.Full Inventory", "You're inventory is full!");
		PICKUP_DISABLE = PREFIX + ChatColor.RED
				+ easyConfig("Messages.Disable Pickup", "Your AutoPickup has been disabled!");
		PICKUP_ENABLE = PREFIX + ChatColor.GREEN
				+ easyConfig("Messages.Enable Pickup", "Your AutoPickup has been enabled!");

		Main.MessageConfig.save();
	}

	private static Object easyConfig(String path, Object value) {
		Object originalValue = Main.MessageConfig.get(path);
		if (originalValue == null || !originalValue.getClass().equals(value.getClass())) {
			Main.MessageConfig.set(path, value);
			return value;
		}
		Object object = Main.MessageConfig.get(path);
		if (object instanceof String)
			return Main.addColor((String) object);
		return object;
	}

	public static void send(Player p, String message) {
		if (message.equalsIgnoreCase("off") || message.equalsIgnoreCase("disabled"))
			return;
		p.sendMessage(message);
	}
}
