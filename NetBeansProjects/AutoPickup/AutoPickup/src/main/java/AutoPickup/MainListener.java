package AutoPickup;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

public class MainListener implements Listener {
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		new SuperLoc(e.getBlock().getLocation(), p);
		boolean isNatural = Natural.isNatural(e.getBlock());
		Natural.naturalize(e.getBlock());
		if (p.hasPermission(Permissions.INFINITE_PICK) && p.getItemInHand().getType().name().contains("PICK"))
			p.getItemInHand().setDurability((short) -1);

		if ((p.hasPermission(Permissions.AUTO_SMELT)
				&& (e.getBlock().getType().equals(Material.IRON_ORE) || e.getBlock().getType()
						.equals(Material.GOLD_ORE)) || Main.superFortune)
				&& !p.getGameMode().equals(GameMode.CREATIVE)
				&& p.getItemInHand().containsEnchantment(Enchantment.LOOT_BONUS_BLOCKS)
				&& WhiteList.getBlocks(p.getItemInHand()).contains(e.getBlock().getType())
				&& (e.getBlock().getType().equals(Material.IRON_ORE)
						|| e.getBlock().getType().equals(Material.GOLD_ORE) || isNatural)) {
			int i = p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS);
			if (p.getItemInHand().getDurability() - 1 <= 0)
				p.getItemInHand().setType(Material.AIR);
			else
				p.getItemInHand().setDurability((short) (p.getItemInHand().getDurability() - 1));
			for (ItemStack is : e.getBlock().getDrops(e.getPlayer().getItemInHand())) {
				if (is.getAmount() == 1) {
					is.setAmount(new Random().nextInt(i));
					Location loc = e.getBlock().getLocation().add(.5, .1, .5);
					MetaLists.who.add(loc.getWorld().dropItem(loc, is), p);
				}
			}
		}
		if (Check.hasAutoXP(p)) {
			p.giveExp(e.getExpToDrop());
			e.setExpToDrop(0);
		}
		if (e.getBlock().getState() instanceof InventoryHolder) {
			InventoryHolder ih = (InventoryHolder) e.getBlock().getState();
			ItemStack[] conts = ih.getInventory().getContents().clone();
			ih.getInventory().clear();
			e.setCancelled(true);
			e.getBlock().breakNaturally();
			for (ItemStack is : conts)
				if (is != null) {
					NoPickup.add(is);
					e.getBlock().getLocation().getWorld()
							.dropItemNaturally(e.getBlock().getLocation().add(.5, .5, .1), is);
				}
		}
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onItemSpawn(ItemSpawnEvent e) {
		SuperLoc.doStuff(e.getEntity());
	}

	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onPlace(BlockPlaceEvent e) {
		Natural.deNaturalize(e.getBlock());
	}

	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onEntityDeath(EntityDeathEvent e) {
		Player killer = e.getEntity().getKiller();

		// If there is a player, and he doesn't want to have mob autopickup
		// Or The thing killed is a person
		// Then NoPickup will happen
		if ((killer != null && !Check.hasAutoPickupMob(killer)) || e.getEntity() instanceof Player) {
			for (ItemStack is : e.getDrops())
				NoPickup.add(is);
			return;
		}

		// A player killed it
		if (killer != null && killer.isValid()) {
			ArrayList<ItemStack> drops = new ArrayList<>(e.getDrops());
			e.getDrops().clear();
			new SuperLoc(e.getEntity().getLocation(), killer);
			for (ItemStack is : drops) {
				e.getEntity().getWorld()
						.dropItem(e.getEntity().getLocation().getBlock().getLocation().add(.5, 0, .5), is);
			}
		}
		if (killer != null && !(e.getEntity() instanceof Player) && Check.hasAutoXP(killer)) {
			killer.giveExp(e.getDroppedExp());
			e.setDroppedExp(0);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onItemDrop(PlayerDropItemEvent e) {
		if (!e.isCancelled())
			e.getItemDrop().setItemStack(NoPickup.add(e.getItemDrop().getItemStack()));
	}
}
