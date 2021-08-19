package framework;

public class PlayerData {
	
	private int coins, gems;
	
	public PlayerData() {
		coins = gems = 0;
	}
	
	public PlayerData(int coins, int gems) {
		this.coins = coins;
		this.gems = gems;
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
	
}
