//Test Commit
package AutoPickup;

import org.bukkit.Material;

public class AutoBlockInfo {
	private int requiredAmount;
	private Material newType;
	private int createdAmount;

	public AutoBlockInfo(int requiredAmount, Material newType, int createdAmount) {
		this.setRequiredAmount(requiredAmount);
		this.setNewType(newType);
		this.setCreatedAmount(createdAmount);
	}

	public int getCreatedAmount() {
		return createdAmount;
	}

	private void setCreatedAmount(int createdAmount) {
		this.createdAmount = createdAmount;
	}

	public Material getNewType() {
		return newType;
	}

	private void setNewType(Material newType) {
		this.newType = newType;
	}

	public int getRequiredAmount() {
		return requiredAmount;
	}

	private void setRequiredAmount(int requiredAmount) {
		this.requiredAmount = requiredAmount;
	}
}
