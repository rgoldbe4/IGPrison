package us.ignitiongaming.factory.other;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.other.IGCell;
import us.ignitiongaming.enums.IGCells;

public class IGCellFactory {

	public static IGCell getCellById(int id) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGCell.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			IGCell igCell = new IGCell();
			while(results.next()) {
				igCell.assign(results);
			}
			
			return igCell;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static List<IGCell> getCells() { 
		List<IGCell> cells = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGCell.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGCell cell = new IGCell();
				cell.assign(results);
				cells.add(cell);
			}
			
			return cells;
		} catch (Exception ex) {
			ex.printStackTrace();
			return cells;
		}
	}
	
	public static IGCell getCellByIGCells(IGCells cells) {
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGCell.TABLE_NAME);
			query.addWhere("label", cells.getLabel());
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			IGCell cell = new IGCell();
			while (results.next()) {
				cell.assign(results);
			}
			
			return cell;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
