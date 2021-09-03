package framework;

public class PlayerData {
	
	private int coins, gems;
	private int hearts = 3, maxHearts = 3;
	private int ammo = 10, maxAmmo = 10;

	public PlayerData() {
		coins = gems = 0;
	}
	
	public PlayerData(int coins, int gems) {
		this.coins = coins;
		this.gems = gems;
	}
	
	public int getHearts() {
		return hearts;
	}

	public void setHearts(int hearts) {
		if (hearts < 0)
			hearts = 0;
		if (hearts > maxHearts)
			hearts = maxHearts;
		this.hearts = hearts;
	}

	public int getMaxHearts() {
		return maxHearts;
	}

	public void setMaxHearts(int maxHearts) {
		this.maxHearts = maxHearts;
	}

	public int getCoins() {
		return coins;
	}

	public void setCoins(int coins) {
		this.coins = coins;
	}

	public int getGems() {
		return gems;
	}

	public void setGems(int gems) {
		this.gems = gems;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	
}
