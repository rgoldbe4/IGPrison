package us.ignitiongaming.entity.gang;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGPlayerGangRequestAnswer;

public class IGPlayerGangRequest extends HasID {

	public static final String TABLE_NAME = "player_gang_request";
	
	private int playerID = -1, gangID = -1;
	private IGPlayerGangRequestAnswer answer;
	
	public void setPlayerId(int playerId) { this.playerID = playerId; }
	public int getPlayerId() { return playerID; }
	
	public void setGangId(int gangId) { this.gangID = gangId; }
	public int getGangId() { return gangID; }
	
	public IGPlayerGangRequestAnswer getAnswer() { return answer; }
	public void setAnswer(IGPlayerGangRequestAnswer answer) { this.answer = answer; }
	public void setAnswer(int answer) { this.answer = IGPlayerGangRequestAnswer.getAnswerById(answer); }
	public void accept() { this.answer = IGPlayerGangRequestAnswer.ACCEPTED; }
	public void decline() { this.answer = IGPlayerGangRequestAnswer.DECLINED; }
	public void pending() { this.answer = IGPlayerGangRequestAnswer.UNANSWERED; }
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPlayerId(results.getInt("playerID"));
			setGangId(results.getInt("gangID"));
			setAnswer(results.getInt("answer"));
		} catch (Exception ex) {
			
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || playerID == -1 || gangID == -1 || answer == null);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("playerID", playerID);
		query.addSet("gangID", gangID);
		query.addSet("answer", answer.getId());
		query.addId(getId());
		query.execute();
	}
	
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
		query.addId(getId());
		query.execute();
	}
}
