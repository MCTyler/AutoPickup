package AutoPickup;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NoPickup {
	public static boolean canPickup(ItemStack is) {
		if (is == null || is.getItemMeta() == null || is.getItemMeta().getLore() == null
				|| !is.getItemMeta().getLore().contains("NOPICKUP111")) {
			return true;
		}
		return false;
	}

	public static ItemStack remove(ItemStack is) {
		if (!canPickup(is)) {
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<>();
			lore.remove("NOPICKUP111");
			im.setLore(lore);
			is.setItemMeta(im);
		}
		return is;
	}

	public static ItemStack add(ItemStack is) {
		if (canPickup(is)) {
			ItemMeta im = is.getItemMeta();
			List<String> lore = im.getLore();
			if (lore == null)
				lore = new ArrayList<>();
			lore.add("NOPICKUP111");
			im.setLore(lore);
			is.setItemMeta(im);
		}
		return is;
	}
}
