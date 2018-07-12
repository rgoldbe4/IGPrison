package us.ignitiongaming.entity.gang;

import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGPlayerGangRequestAnswer;

public class IGPlayerGangRequest extends HasID {

	public static final String TABLE_NAME = "player_gang_request";
	
	private int playerID = -1, gangID = -1;
	private IGPlayerGangRequestAnswer answer;
	
	public void setPlayerID(int playerID) { this.playerID = playerID; }
	public int getPlayerID() { return playerID; }
	
	public void setGangID(int gangID) { this.gangID = gangID; }
	public int getGangID() { return gangID; }
	
	public IGPlayerGangRequestAnswer getAnswer() { return answer; }
	public void setAnswer(IGPlayerGangRequestAnswer answer) { this.answer = answer; }
	public void setAnswer(int answer) { this.answer = IGPlayerGangRequestAnswer.getAnswerById(answer); }
	
}
