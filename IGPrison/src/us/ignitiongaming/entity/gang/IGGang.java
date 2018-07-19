package us.ignitiongaming.entity.gang;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;
import us.ignitiongaming.util.convert.BooleanConverter;

public class IGGang extends HasID {

	public static final String TABLE_NAME = "gang";
	
	private String name;
	private int points = -1, founderId = -1;
	private double money = -1;
	private boolean closed = false;
	private boolean allowBuyDrugs = false;
	
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }
	
	public void setPoints(int points) { this.points = points; }
	public void addPoints(int points) { this.points += points; }
	public void removePoints(int points) { this.points -= points; }
	public int getPoints() { return points; }
	
	public void setMoney(double money) { this.money = money; }
	public void addMoney(double money) { this.money += money; }
	public void removeMoney(double money) { this.money -= money; }
	public double getMoney() { return money; }
	
	public void setClosed(boolean closed) { this.closed = closed; }
	public void close() { this.closed = true; }
	public void open() { this.closed = false; }
	public boolean getClosed() { return closed; }
	public boolean isClosed() { return closed == true; }
	
	public void setFounderId(int founderId) { this.founderId = founderId; }
	public int getFounderId() { return founderId; }
	
	public void setAllowToBuyDrugs(boolean allowBuyDrugs) { this.allowBuyDrugs = allowBuyDrugs; }
	public void allowMembersToBuyDrugs() { this.allowBuyDrugs = true; }
	public void disallowMembersToBuyDrugs() { this.allowBuyDrugs = false; }
	public boolean canMembersBuyDrugs() { return allowBuyDrugs; }
	public void toggleAllowedMembersToBuyDrugs() { allowBuyDrugs = !allowBuyDrugs; }
	
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPoints(results.getInt("points"));
			setName(results.getString("name"));
			setMoney(results.getDouble("money"));
			setClosed(BooleanConverter.getBooleanFromInteger(results.getInt("closed")));
			setFounderId(results.getInt("founderID"));
			setAllowToBuyDrugs(BooleanConverter.getBooleanFromInteger(results.getInt("memberBuyDrugs")));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean isValid() {
		return !(!hasId() || name == null || points < 0 || money < 0);
	}
	
	public void save() {
		SQLQuery query = new SQLQuery(QueryType.UPDATE, TABLE_NAME);
		query.addSet("name", name);
		query.addSet("points", points);
		query.addSet("money", money);
		query.addSet("closed", BooleanConverter.getIntegerFromBoolean(closed));
		query.addSet("founderID", founderId);
		query.addSet("memberBuyDrugs", BooleanConverter.getIntegerFromBoolean(allowBuyDrugs));
		query.addID(getId());
		query.execute();
	}
	
	public void delete() {
		SQLQuery query = new SQLQuery(QueryType.DELETE, TABLE_NAME);
		query.addID(getId());
		query.execute();
	}
}
