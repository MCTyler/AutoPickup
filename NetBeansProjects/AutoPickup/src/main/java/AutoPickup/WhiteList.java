package AutoPickup;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WhiteList {
	private static Map<Material, ArrayList<Material>> fortuneBlocks = new EnumMap<>(Material.class);

	public static void setUp() {
		Main.WhiteListConfig = new SuperYaml(Main.dataFolder + "/Item WhiteList.yml");
		fortuneBlocks = new EnumMap<>(Material.class);
		if (Main.WhiteListConfig.get("Tool.Shovel") == null
				|| !(Main.WhiteListConfig.get("Tool.Shovel") instanceof List<?>)) {
			Material[] def = { Material.DIRT, Material.GRASS, Material.MYCEL, Material.SAND, Material.GRAVEL };
			List<String> list = new ArrayList<>();
			for (Material material : def)
				list.add(material.name());
			Main.WhiteListConfig.set("Tool.Shovel", list);
		}
		if (Main.WhiteListConfig.get("Tool.Pick") == null
				|| !(Main.WhiteListConfig.get("Tool.Pick") instanceof List<?>)) {
			Material[] def = { Material.STONE, Material.DIAMOND_ORE, Material.DIAMOND_ORE, Material.IRON_ORE,
					Material.GOLD_ORE, Material.REDSTONE_ORE };
			List<String> list = new ArrayList<>();
			for (Material material : def)
				list.add(material.name());
			Main.WhiteListConfig.set("Tool.Pick", list);
		}
		if (Main.WhiteListConfig.get("Tool.Axe") == null || !(Main.WhiteListConfig.get("Tool.Axe") instanceof List<?>)) {
			Material[] def = { Material.WOOD, Material.LEAVES, Material.LEAVES_2 };
			List<String> list = new ArrayList<>();
			for (Material material : def)
				list.add(material.name());
			Main.WhiteListConfig.set("Tool.Axe", list);
		}
		if (Main.WhiteListConfig.get("Tool.Hoe") == null || !(Main.WhiteListConfig.get("Tool.Hoe") instanceof List<?>)) {
			Material[] def = { Material.AIR };
			List<String> list = new ArrayList<>();
			for (Material material : def)
				list.add(material.name());
			Main.WhiteListConfig.set("Tool.Hoe", list);
		}
		if (Main.WhiteListConfig.get("Tool.Shear") == null
				|| !(Main.WhiteListConfig.get("Tool.Shear") instanceof List<?>)) {
			Material[] def = { Material.AIR };
			List<String> list = new ArrayList<>();
			for (Material material : def)
				list.add(material.name());
			Main.WhiteListConfig.set("Tool.Shear", list);
		}
		Material[] shovels = { Material.WOOD_SPADE, Material.STONE_SPADE, Material.IRON_SPADE, Material.DIAMOND_SPADE };
		Material[] shears = { Material.SHEARS };
		Material[] picks = { Material.WOOD_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE,
				Material.DIAMOND_PICKAXE };
		Material[] axes = { Material.WOOD_AXE, Material.STONE_AXE, Material.IRON_AXE, Material.DIAMOND_AXE };
		Material[] hoes = { Material.WOOD_HOE, Material.STONE_HOE, Material.IRON_HOE, Material.DIAMOND_HOE };
		initialize(shovels, "Shovel");
		initialize(shears, "Shear");
		initialize(picks, "Pick");
		initialize(axes, "Axe");
		initialize(hoes, "Hoe");

		Main.WhiteListConfig.save();
	}

	@SuppressWarnings({"deprecation", "UnusedAssignment"})
	private static void initialize(Material[] tools, String string) {
		ArrayList<Material> blocks = new ArrayList<>();
		boolean save = false;
		List<String> newList = Main.WhiteListConfig.getStringList("Tool." + string);
		for (String materialName : Main.WhiteListConfig.getStringList("Tool." + string)) {
                        @SuppressWarnings("UnusedAssignment")
			Material mat;
                    mat = null;
			try {
				int id = Integer.parseInt(materialName);
				mat = Material.getMaterial(id);
			} catch (NumberFormatException ex) {
				mat = Material.getMaterial(materialName);
			}
			if (mat == null) {
				Bukkit.getServer()
						.getLogger()
						.log(Level.SEVERE, "[AutoPickup] There was an error in the WhiteList.yml at: Tool.{0}.{1}", new Object[]{string, materialName});
				newList.remove(materialName);
				save = true;
			} else
				blocks.add(mat);
		}
		for (Material toolMat : tools)
			fortuneBlocks.put(toolMat, blocks);
		if (save)
			Main.WhiteListConfig.set("Tool." + string, newList);
	}

	public static ArrayList<Material> getBlocks(Material tool) {
		if (fortuneBlocks.containsKey(tool))
			return fortuneBlocks.get(tool);
		return new ArrayList<>();
	}

	public static ArrayList<Material> getBlocks(ItemStack tool) {
		return getBlocks(tool.getType());
	}
}
