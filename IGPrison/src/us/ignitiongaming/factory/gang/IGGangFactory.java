package us.ignitiongaming.factory.gang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.OrderBy;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.player.IGPlayer;

public class IGGangFactory {

	public static List<IGGang> getAllGangs() { 
		List<IGGang> gangs = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			query.addOrderBy("money", OrderBy.DESC);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGGang gang = new IGGang();
				gang.assign(results);
				gangs.add(gang);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gangs;
	}
	
	public static IGGang getGangById(int id) {
		IGGang gang = new IGGang();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				gang.assign(results);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gang;
	}
	
	public static IGGang getGangByName(String name) {
		IGGang gang = new IGGang();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			query.addWhere("name", name);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				gang.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return gang;
	}
	
	public static void add(String name, IGPlayer founder) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGGang.TABLE_NAME);
			query.addGrabColumns("name", "founderID");
			query.addValues(name, founder.getId());
			query.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
