package AutoPickup;

import java.util.Date;

import org.bukkit.entity.Player;

public class FullInventory {
	static long cooldownTime = (long) (1 * 1000); // Seconds * (Milliseconds
													// per

	// Second)

	public static void addCooldown(Player p) {
		MetaLists.cooldown.add(p, new Date().getTime() + cooldownTime);
	}

	public static boolean hasCooldown(Player p) {
		if (!MetaLists.cooldown.contains(p))
			return false;
		if ((long) MetaLists.cooldown.get(p) > new Date().getTime())
			return true;
		removeCooldown(p);
		return false;

	}

	public static void removeCooldown(Player p) {
		MetaLists.cooldown.remove(p);
	}
}
