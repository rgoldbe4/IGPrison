package us.ignitiongaming.factory.gang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGangRequest;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGPlayerGangRequestAnswer;

public class IGPlayerGangRequestFactory {

	public static List<IGPlayerGangRequest> getRequestByIGPlayer(IGPlayer igPlayer) {
		List<IGPlayerGangRequest> requests = new ArrayList<>();
		
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGangRequest.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerGangRequest request = new IGPlayerGangRequest();
				request.assign(results);
				requests.add(request);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return requests;
	}
	
	public static List<IGPlayerGangRequest> getUnansweredRequestByIGPlayer(IGPlayer igPlayer) {
		List<IGPlayerGangRequest> requests = new ArrayList<>();
		
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGangRequest.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("answer", IGPlayerGangRequestAnswer.UNANSWERED.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerGangRequest request = new IGPlayerGangRequest();
				request.assign(results);
				requests.add(request);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return requests;
	}
	
	public static IGPlayerGangRequest getRequestByIGPlayerAndIGGang(IGPlayer igPlayer, IGGang igGang) {
		IGPlayerGangRequest request = new IGPlayerGangRequest();
		
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGangRequest.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("gangID", igGang.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				request.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return request;
	}
	
	public static IGPlayerGangRequest getUnansweredRequestByIGPlayerAndIGGang(IGPlayer igPlayer, IGGang igGang) {
		IGPlayerGangRequest request = new IGPlayerGangRequest();
		
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGangRequest.TABLE_NAME);
			query.addWhere("playerID", igPlayer.getId());
			query.addWhere("gangID", igGang.getId());
			query.addWhere("answer", IGPlayerGangRequestAnswer.UNANSWERED.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				request.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		
		return request;
	}
	
	public static List<IGPlayerGangRequest> getRequestsByGang(int gangID) {
		List<IGPlayerGangRequest> requests = new ArrayList<>();
		
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayerGangRequest.TABLE_NAME);
			query.addWhere("gangID", gangID);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayerGangRequest request = new IGPlayerGangRequest();
				request.assign(results);
				requests.add(request);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return requests;
	}
	
	public static void add(IGPlayer igPlayer, IGGang igGang) {
		SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayerGangRequest.TABLE_NAME);
		query.addGrabColumns("playerID", "gangID");
		query.addValues(igPlayer.getId(), igGang.getId());
		query.execute();
	}
}
