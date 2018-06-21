package us.ignitiongaming.factory.rank;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.enums.IGRanks;

public class IGRankFactory {

	/**
	 * Get all ranks in database.
	 * @return
	 */
	public static List<IGRank> getRanks() {
		List<IGRank> ranks = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGRank.TABLE_NAME);
			ResultSet results = query.getResults();
			
			//Add each of the ranks.
			while (results.next()) {
				IGRank rank = new IGRank();
				rank.assign(results);
				ranks.add(rank);
			}
			
			return ranks;
		} catch (Exception ex) {
			ex.printStackTrace();
			return ranks;
		}
	}
	
	/**
	 * Grab an IGRank object based on IGRanks enum.
	 * @param rank
	 * @return
	 */
	public static IGRank getIGRankByRank (IGRanks rank) {
		try {
			IGRank igRank = new IGRank();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGRank.TABLE_NAME);
			query.addWhere("name", rank.getName());
			ResultSet results = query.getResults();
			
			//Make sure that we a rank.
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				igRank.assign(results);
			}
			
			return igRank;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static IGRank getIGRankById(int id) {
		try {
			IGRank igRank = new IGRank();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGRank.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			//Make sure that we a rank.
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				igRank.assign(results);
			}
			
			return igRank;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
