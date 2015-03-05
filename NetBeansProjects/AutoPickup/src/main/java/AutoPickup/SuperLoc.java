package AutoPickup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SuperLoc {
	public static HashMap<Location, SuperLoc> superLocs = new HashMap<>();
	public Player p;

	public SuperLoc(Location loc, Player p) {
		if (loc == null)
			return;
		final Location finalLoc = loc.getBlock().getLocation();
		this.p = p;
		superLocs.put(finalLoc, this);
		Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {

			@Override
                        @SuppressWarnings("IncompatibleEquals")
			public void run() {
				if (finalLoc != null && superLocs.containsKey(finalLoc) && superLocs.get(finalLoc).equals(this))
					superLocs.remove(finalLoc);
			}

		}, 5L);
	}

	public static void doStuff(Item item) {
		if (item == null)
			return;

		// If you can't pick it up (from broken chest or something)
		if (!NoPickup.canPickup(item.getItemStack())) {
			item.setItemStack(NoPickup.remove(item.getItemStack()));
			return;
		}

		Location loc = item.getLocation().getBlock().getLocation();
		if (!superLocs.containsKey(loc))
			return;
		SuperLoc superLoc = superLocs.get(loc);
		if (superLoc == null || superLoc.p == null)
			return;

		superLoc.smelt(item);
		superLoc.autoPickup(item);

		item.remove();
	}

	@SuppressWarnings("deprecation")
	private void autoPickup(Item item) {
		if (!p.isOnline() || !p.isValid() || !Check.hasAutoPickup(p))
			return;
		ArrayList<ItemStack> finalItems = Main.addToInventory(p, item.getItemStack());
		if (!finalItems.isEmpty()) {
			if (!FullInventory.hasCooldown(p)) {
				Messages.send(p, Messages.FULL_INVENTORY);
				FullInventory.addCooldown(p);
			}
			if (Main.fullDrop)
				for (ItemStack is : finalItems) {
					NoPickup.add(is);
					item.getWorld().dropItemNaturally(item.getLocation(), is);
				}
		}

		if (p.hasPermission(Permissions.AUTO_BLOCK)) {
			ItemStack[] newInvCont = Main.convertToBlocks(p.getInventory().getContents());
			if (!Arrays.equals(p.getInventory().getContents(), newInvCont)) {
				p.getInventory().setContents(newInvCont);
				p.updateInventory();
			}
		}

	}

	private void smelt(Item item) {
		if ((p.getItemInHand() == null || !p.getItemInHand().containsEnchantment(Enchantment.SILK_TOUCH))
				&& p.hasPermission(Permissions.AUTO_SMELT)) {
			ItemStack is = item.getItemStack();
			if (Main.blocksToSmelt.containsKey(is.getType())) {
				is.setType(Main.blocksToSmelt.get(is.getType()).getNewType());
				item.setItemStack(is);
			}
		}
	}
}
