package us.ignitiongaming.factory.player;

import java.sql.ResultSet;

import org.bukkit.entity.Player;

import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.database.DatabaseUtils;
import us.ignitiongaming.database.QueryType;
import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.entity.rank.IGRank;
import us.ignitiongaming.factory.rank.IGRankFactory;

public class IGPlayerFactory {

	/**
	 * <h1>GetIGPlayerByPlayer</h1>
	 * <i>Lookup a player's database information via Player object</i> 
	 * @param player - Player object of player to lookup.
	 * @return IGPlayer Object or null
	 */
	public static IGPlayer getIGPlayerByPlayer(Player player) {
		try {
			IGPlayer igPlayer = new IGPlayer();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addWhere("uuid", player.getUniqueId());
			ResultSet results = query.getResults();
			
			//Bad -> If we found more than one IGPlayer (HOW!?!) or no IGPlayer (can be expected).
			if (DatabaseUtils.getNumRows(results) != 1) {
				return null;
			}
			
			//Setup object. Since this is a one-to-one relationship, we don't need to manually assign things.
			while (results.next()) {
				igPlayer.assign(results);
			}
			
			return igPlayer;
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static IGPlayer getIGPlayerById(int id) {
		try {
			IGPlayer igPlayer = new IGPlayer();
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addID(id);
			ResultSet results = query.getResults();
			
			if (DatabaseUtils.getNumRows(results) == 0) return null;
			
			while (results.next()) {
				igPlayer.assign(results);
			}
			
			return igPlayer;
			
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * Add an IGPlayer (and child tables) to the database!
	 * @param player
	 */
	public static void add(Player player) {
		try {
			/*
			 * Players should have:
			 * - Their base IGPlayer which everything is based off of.
			 * - IGPlayerStats for statistics that shouldn't be in IGPlayer
			 * - IGPlayerRank for the player's rank (set to default).
			 */
			
			// -- Add IGPlayer --
			SQLQuery query = new SQLQuery(QueryType.INSERT, IGPlayer.TABLE_NAME);
			query.addGrabColumns("name", "uuid", "ip");
			query.addValues(player.getName(), player.getUniqueId(), player.getAddress().getAddress().getHostAddress());
			query.execute();
			
			//Grab the IGPlayer for usage.
			IGPlayer igPlayer = getIGPlayerByPlayer(player);
			
			// -- Add IGPlayerStats --
			IGPlayerStatsFactory.add(igPlayer);
			
			// -- Add IGPlayerRank --
			IGRank igRank = IGRankFactory.getIGRankByRank(ServerDefaults.DEFAULT_RANK); //Default rank.
			IGPlayerRankFactory.add(igPlayer, igRank);
			
			
		} catch (Exception ex) {
			
		}
	}
}
