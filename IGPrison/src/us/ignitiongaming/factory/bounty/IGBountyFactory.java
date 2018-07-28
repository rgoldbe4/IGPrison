package us.ignitiongaming.factory.bounty;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.bounty.IGBounty;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGBountyProgress;

public class IGBountyFactory {

	public static List<IGBounty> getAllBounties() {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static List<IGBounty> getBountiesByProgress(IGBountyProgress progress) {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("progress", progress.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static List<IGBounty> getBountiesByPlayer(IGPlayer igPlayer) {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("creatorID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static List<IGBounty> getBountiesByPlayer(IGPlayer igPlayer, IGBountyProgress progress) {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("creatorID", igPlayer.getId());
			query.addWhere("progress", progress.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static List<IGBounty> getBountiesTargettingPlayer(IGPlayer igPlayer) {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("targetID", igPlayer.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static List<IGBounty> getBountiesTargettingPlayer(IGPlayer igPlayer, IGBountyProgress progress) {
		List<IGBounty> bounties = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("targetID", igPlayer.getId());
			query.addWhere("progress", progress.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGBounty bounty = new IGBounty();
				bounty.assign(results);
				bounties.add(bounty);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounties;
	}
	
	public static boolean doesPlayerAlreadyHaveOngoingBounty(IGPlayer igCreator, IGPlayer igTarget) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("targetID", igTarget.getId());
			query.addWhere("creatorID", igCreator.getId());
			query.addWhere("progress", IGBountyProgress.ONGOING.getId());
			ResultSet results = query.getResults();
			/*
			 * 0 = They have no ongoing bounties
			 * 1 = They have ongoing bounties
			 */
			return (DatabaseUtils.getNumRows(results) == 1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	public static IGBounty getBountyById(int id) {
		IGBounty bounty = new IGBounty();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				bounty.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounty;
	}
	
	public static IGBounty getBountyByTargetAndCreator(IGPlayer creator, IGPlayer target) {
		IGBounty bounty = new IGBounty();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGBounty.TABLE_NAME);
			query.addWhere("targetID", target.getId());
			query.addWhere("creatorID", creator.getId());
			query.addWhere("progress", IGBountyProgress.ONGOING.getId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				bounty.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return bounty;
	}
	
	public static void add(IGPlayer createdBy, IGPlayer target, double amount) {
		SQLQuery query = new SQLQuery(QueryType.INSERT, IGBounty.TABLE_NAME);
		query.addGrabColumns("creatorID", "targetID", "amount");
		query.addValues(createdBy.getId(), target.getId(), amount);
		query.execute();
	}
}
