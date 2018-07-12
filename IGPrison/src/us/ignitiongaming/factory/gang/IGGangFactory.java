package us.ignitiongaming.factory.gang;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.gang.IGGang;

public class IGGangFactory {

	public static List<IGGang> getAllGangs() { 
		List<IGGang> gangs = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGGang gang = new IGGang();
				gang.assign(results);
				gangs.add(gang);
			}
			
			return gangs;
		} catch (Exception ex) {
			ex.printStackTrace();
			return gangs;
		}
	}
	
	public static IGGang getGangById(int id) {
		try {
			IGGang gang = new IGGang();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				gang.assign(results);
			}
			
			return gang;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static IGGang getGangByName(String name) {
		try {
			IGGang gang = new IGGang();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGGang.TABLE_NAME);
			query.addWhere("name", name);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				gang.assign(results);
			}
			
			return gang;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void add(String name) {
		try {
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGGang.TABLE_NAME);
			query.addGrabColumn("name");
			query.addValue(name);
			query.execute();
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
