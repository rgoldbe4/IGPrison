package us.ignitiongaming.factory.player;

import java.sql.ResultSet;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.player.IGPlayerNickname;
public class IGPlayerNicknameFactory {
	
	public static IGPlayerNickname getIGPlayerNicknameForString(String nickname) {
		SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerNickname.TABLE_NAME);
		query.addWhere("nickname", nickname);
		ResultSet results = query.getResults();
		try {	
			if(results.next()) {
				IGPlayerNickname playerNick = new IGPlayerNickname();
				playerNick.assign(results);
				return playerNick;
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}
	public static IGPlayerNickname getIGPlayerNicknameForIGPlayer(IGPlayer igPlayer) {
		SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerNickname.TABLE_NAME);
		query.addWhere("playerID", igPlayer.getId());
		ResultSet results = query.getResults();
		try {	
			if(results.next()) {
				IGPlayerNickname nickname = new IGPlayerNickname();
				nickname.assign(results);
				return nickname;
			}
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}
	public static void setIGPlayerNicknameForIGPlayer(IGPlayer igPlayer, String nick) {
		SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerNickname.TABLE_NAME);
		query.addWhere("playerID", igPlayer.getId());
		ResultSet results = query.getResults();
		try {	
			if(results.next()) {
				IGPlayerNickname nickname = new IGPlayerNickname();
				nickname.assign(results);
				nickname.setNickname(nick);
				nickname.save();
			}
			else {
				query = new SQLQuery(QueryType.INSERT, IGPlayerNickname.TABLE_NAME);
				query.addGrabColumns("playerID", "nickname", "actualname");
				query.addValues(igPlayer.getId(), nick, igPlayer.getName());
				query.execute();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
