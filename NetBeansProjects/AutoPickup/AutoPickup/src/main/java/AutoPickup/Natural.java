package AutoPickup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class Natural {
	private static ArrayList<Location> unNaturals = new ArrayList<>();

	public static void setUp() {
		Main.DataConfig = new SuperYaml(Main.dataFolder + "/Data");
		load();
	}

	public static boolean isNatural(Block b) {
		return isNatural(b.getLocation());
	}

	public static boolean isNatural(Location loc) {
		return !unNaturals.contains(loc);
	}

	public static void deNaturalize(Block b) {
		deNaturalize(b.getLocation());
	}

	public static void deNaturalize(Location loc) {
		if (!unNaturals.contains(loc))
			unNaturals.add(loc);
	}

	public static void naturalize(Block b) {
		naturalize(b.getLocation());
	}

	public static void naturalize(Location loc) {
		unNaturals.remove(loc);
	}

	public static void save() {
		Map<World, List<Vector>> vectors = new HashMap<>();
		for (Location loc : unNaturals) {
			List<Vector> vecs = new ArrayList<>();
			World world = loc.getWorld();
			if (vectors.containsKey(world)) {
				vecs = vectors.get(world);
				vectors.remove(world);
			}
			vecs.add(loc.toVector());
			vectors.put(world, vecs);
		}
		for (World world : vectors.keySet())
			Main.DataConfig.set("Blocks." + world.getName(), vectors.get(world));
		Main.DataConfig.save();
	}

	public static void load() {
		unNaturals = new ArrayList<>();
		if (Main.DataConfig.get("Blocks") == null) {
			Main.DataConfig.set("Blocks", null);
			return;
		}
		for (String worldName : Main.DataConfig.getConfigurationSection("Blocks").getKeys(false))
			if (!(Main.DataConfig.get("Blocks." + worldName) instanceof List<?>)) {
				Main.DataConfig.set("Blocks." + worldName, null);
			} else if (Bukkit.getWorld(worldName) != null) {
				@SuppressWarnings("unchecked")
				List<Vector> vectors;
                    vectors = new ArrayList<>((List<Vector>) Main.DataConfig.config.getList("Blocks."
                            + worldName));
				@SuppressWarnings("unchecked")
				List<Vector> newList;
                    newList = new ArrayList<>((List<Vector>) Main.DataConfig.config.getList("Blocks."
                            + worldName));
				for (Vector vec : vectors) {
					Location loc = new Location(Bukkit.getWorld(worldName), vec.getX(), vec.getY(), vec.getZ());
					if (loc.getBlock().isEmpty() || loc.getBlock().isLiquid())
						newList.remove(vec);
					else
						unNaturals.add(loc);
				}
				if (!vectors.equals(newList))
					Main.DataConfig.set("Blocks." + worldName, newList);
			}
		Main.DataConfig.save();
	}
}
