package AutoPickup;

import org.bukkit.entity.Player;

public class Check {
	public static boolean hasAutoPickup(Player p) {
		return p.hasPermission(Permissions.USE) && !MetaLists.off.contains(p);
	}

	public static boolean hasAutoPickupMob(Player p) {
		return p.hasPermission(Permissions.AUTO_MOB) && hasAutoPickup(p);
	}
	public static boolean hasAutoXP(Player p) {
		return p.hasPermission(Permissions.AUTO_XP) && hasAutoPickup(p);
	}
}
