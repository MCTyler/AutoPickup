package AutoPickup;

public class Permissions {
	public static String AUTO_MOB;
	public static String AUTO_XP;
	public static String RELOAD;
	public static String INFINITE_PICK;
	public static String AUTO_BLOCK;
	public static String AUTO_SMELT;
	public static String COMMANDS_SMELT;
	public static String COMMANDS_BLOCK;
	public static String TOGGLE;
	public static String USE;

	public static void setUp() {
		Main.PermissionConfig = new SuperYaml(Main.dataFolder + "/Permissions.yml");
		if (Main.MainConfig.get("Permission") != null) {
			Main.PermissionConfig.set("Permission", Main.MainConfig.get("Permission"));
			Main.MainConfig.set("Permission", null);
		}

		RELOAD = easyConfig("Permission.Reload", "AutoPickup.Reload");
		INFINITE_PICK = easyConfig("Permission.InfinitePick", "AutoPickup.InfinitePick");
		COMMANDS_BLOCK = easyConfig("Permission.Commands.AutoBlock", "AutoPickup.Commands.AutoBlock");
		COMMANDS_SMELT = easyConfig("Permission.Commands.AutoSmelt", "AutoPickup.Commands.AutoSmelt");
		AUTO_BLOCK = easyConfig("Permission.AutoBlock", "AutoPickup.AutoBlock");
		AUTO_SMELT = easyConfig("Permission.AutoSmelt", "AutoPickup.AutoSmelt");
		AUTO_XP = easyConfig("Permission.AutoXP", "AutoPickup.AutoXP");
		AUTO_MOB = easyConfig("Permission.AutoMob", "AutoPickup.AutoMob");
		TOGGLE = easyConfig("Permission.Toggle", "AutoPickup.Toggle");
		USE = easyConfig("Permission.Use", "AutoPickup.Use");

		Main.PermissionConfig.save();
	}

	private static String easyConfig(String path, String value) {
		if (Main.PermissionConfig.get(path) == null) {
			Main.PermissionConfig.set(path, value);
			return value;
		}
		return Main.PermissionConfig.getString(path);
	}
}
