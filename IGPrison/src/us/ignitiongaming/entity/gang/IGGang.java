package us.ignitiongaming.entity.gang;

import java.sql.ResultSet;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.HasID;

public class IGGang extends HasID {

	public static final String TABLE_NAME = "gang";
	
	private String name;
	private int points = -1;
	private double money = -1;
	
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
	
	public void assign(ResultSet results) {
		try {
			setId(results);
			setPoints(results.getInt("points"));
			setName(results.getString("name"));
			setMoney(results.getDouble("money"));
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
		query.addID(getId());
		query.execute();
	}
}