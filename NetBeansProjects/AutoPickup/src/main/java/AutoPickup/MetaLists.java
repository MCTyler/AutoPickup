package AutoPickup;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.Metadatable;

public class MetaLists {
	public static MetaLists off = new MetaLists("off");
	public static MetaLists cooldown = new MetaLists("cooldown");
	public static MetaLists who = new MetaLists("WHO");
	public static MetaLists guess = new MetaLists("Guess");

	private String name;

	public MetaLists(String name) {
		this.name = name;
	}

	public Boolean contains(Metadatable md) {
		if (md == null)
			return false;
		return md.hasMetadata(getName());
	}

	public Object get(Metadatable md) {
		if (md == null)
			return null;
		return md.getMetadata(getName()).get(0).value();
	}

	public void add(Metadatable md) {
		if (md == null)
			return;
		add(md, true);
	}

	public void add(Metadatable md, Object object) {
		if (md == null)
			return;
		md.setMetadata(getName(), new FixedMetadataValue(Main.plugin, object));
	}

	public void remove(Metadatable md) {
		if (md == null)
			return;
		md.removeMetadata(getName(), Main.plugin);
	}

	public String getName() {
		return name;
	}
}