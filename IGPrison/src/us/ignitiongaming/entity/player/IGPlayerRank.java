package us.ignitiongaming.entity.player;

import us.ignitiongaming.entity.HasID;

public class IGPlayerRank extends HasID {

	public static final String TABLE_NAME = "player_rank";
	
	private int playerId = 0, rankId = 0;
	
	public void setPlayerId(int playerId) { this.playerId = playerId; }
	public int getPlayerId() { return playerId; }
	
	public void setRankId(int rankId) { this.rankId = rankId; }
	public int getRankId() { return rankId; }
	
	public boolean isValid() {
		return (!hasId() || rankId == 0 || playerId == 0);
	}
}
