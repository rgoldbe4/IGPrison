package us.ignitiongaming.entity.bounty;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.enums.IGBountyProgress;

public class IGBounty extends HasID {

	public static final String TABLE_NAME = "bounty";
	
	private int creatorId = 0, targetId = 0, claimedId = 0;
	private IGBountyProgress progress = IGBountyProgress.ONGOING;
	private double amount = -1;
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setCreatorId(results.getInt("creatorID"));
			setTargetId(results.getInt("targetID"));
			setClaimedId(results.getInt("claimedID"));
			setAmount(results.getDouble("amount"));
			setProgress(IGBountyProgress.getProgressById(results.getInt("progress")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public int getCreatorId() {
		return creatorId;
	}
	
	public void setCreatorId(int creatorId) {
		this.creatorId = creatorId;
	}
	
	public int getTargetId() {
		return targetId;
	}
	
	public void setTargetId(int targetId) {
		this.targetId = targetId;
	}
	
	public int getClaimedId() {
		return claimedId;
	}
	
	public void setClaimedId(int claimedId) {
		this.claimedId = claimedId;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
	public IGBountyProgress getProgress() {
		return progress;
	}
	
	public void setProgress(IGBountyProgress progress) {
		this.progress = progress;
	}
	
	public void claimed() { 
		progress = IGBountyProgress.CLAIMED;
	}
	
	public void cancel() {
		progress = IGBountyProgress.CANCELLED;
	}
	
	public boolean isCancelled() {
		return progress == IGBountyProgress.CANCELLED;
	}
	
	
	public boolean isValid() {
		return !(!hasId() || creatorId == 0 || targetId == 0 || amount < 0);
	}
	
	public boolean isClaimed() {
		if (!isValid()) return false;
		if (progress == IGBountyProgress.ONGOING) return false;
		return claimedId == 0;
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("creatorID", creatorId);
		query.addSet("targetID", targetId);
		if (isClaimed()) query.addSet("claimedID", claimedId);
		query.addSet("amount", amount);
		query.addSet("progress", progress.getId());
		query.addID(getId());
		query.execute();
	}
	
	/**
	 * Put this in case it was needed. Generally, you don't need this.
	 */
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
		query.addID(getId());
		query.execute();
	}
	

}
