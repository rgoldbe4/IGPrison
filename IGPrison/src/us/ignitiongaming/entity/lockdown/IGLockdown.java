package us.ignitiongaming.entity.lockdown;

import java.sql.ResultSet;
import java.util.Date;

import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.entity.other.IGCell;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.factory.other.IGCellFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.DateConverter;

public class IGLockdown extends HasID {

	public static final String TABLE_NAME = "lockdown";
	//Note: endId and ended will be NULL on creation.
	private int startId = 0, endId = 0, cellId = 0;
	private String started, ended;
	
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setStartId(results.getInt("startID"));
			setEndId(results.getInt("endID"));
			setCellId(results.getInt("cellID"));
			setStarted(results.getString("started"));
			setEnded(results.getString("ended"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || startId == 0 || cellId == 0 || started == null);
	}
	
	public boolean hasEnded() {
		return !(endId == 0 || ended == null);
	}
	
	public void save() {
		try {
			if (isValid()) {
				SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
				query.addSet("startID", startId);
				query.addSet("started", started);
				query.addSet("cellID", cellId);
				
				if (hasEnded()) {
					query.addSet("ended", ended);
					query.addSet("endID", endId);
				}

				query.execute();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void setStartId(int startId) { this.startId = startId; }
	public void setStartId(IGPlayer igPlayer) { this.startId = igPlayer.getId(); }
	public int getStartId() { return startId; }
	public IGPlayer getStartPlayer() { return IGPlayerFactory.getIGPlayerById(startId); }
	
	public void setEndId(int endId) { this.endId = endId; }
	public void setEndId(IGPlayer igPlayer) { this.endId = igPlayer.getId(); }
	public int getEndId() { return endId; }
	public IGPlayer getEndPlayer() { return IGPlayerFactory.getIGPlayerById(endId); }
	
	public void setCellId(int cellId) { this.cellId = cellId; }
	public void setCellId(IGCell igCell) { this.cellId = igCell.getId(); }
	public int getCellId() { return cellId; }
	public IGCell getCell() { return IGCellFactory.getCellById(cellId); }
	
	public String getStarted() { return started; }
	public Date getStartedDate() { return DateConverter.convertStringDateTimeToDate(started); }
	public void setStarted(String started) { this.started = started; }
	public void setStarted(Date started) { this.started = DateConverter.convertDateToString(started); }
	public String getStartedFriendly() { return DateConverter.toFriendlyDate(started); }
	
	public String getEnded() { return ended; }
	public Date getEndedDate() { return DateConverter.convertStringDateTimeToDate(ended); }
	public void setEnded(String ended) { this.ended = ended; }
	public void setEnded(Date ended) { this.ended = DateConverter.convertDateToString(ended); }
	public String getEndedFriendly() { return DateConverter.toFriendlyDate(ended); }
	
}
