package us.ignitiongaming.factory.player;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

import us.ignitiongaming.database.SQLQuery;
import us.ignitiongaming.database.SQLQuery.QueryType;
import us.ignitiongaming.entity.player.IGPlayer;

public class IGPlayerFactory {

	/**
	 * <h1>GetIGPlayerByPlayer</h1>
	 * <i>Lookup a player's database information via Player object</i> 
	 * @param player - Player object of player to lookup.
	 * @return IGPlayer Object or null
	 */
	public static IGPlayer getIGPlayerByPlayer(Player player) {
		IGPlayer igPlayer = new IGPlayer();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addWhere("uuid", player.getUniqueId());
			ResultSet results = query.getResults();
			
			while (results.next()) {
				igPlayer.assign(results);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return igPlayer;
	}
	
	public static IGPlayer getIGPlayerById(int id) {
		IGPlayer igPlayer = new IGPlayer();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addId(id);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				igPlayer.assign(results);
			}
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return igPlayer;
	}
	
	public static IGPlayer getIGPlayerForNickname(String nickname) {
		IGPlayer igPlayer = new IGPlayer();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addWhere("nickname", nickname);
			ResultSet results = query.getResults();
			
			if(results.next()) {
				igPlayer.assign(results);
			}
			
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return igPlayer;
	}
	
	public static IGPlayer getIGPlayerFromName(String name) {
		IGPlayer igPlayer = new IGPlayer();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			query.addWhere("name", name);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				igPlayer.assign(results);
			}
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return igPlayer;
	}
	
	public static List<IGPlayer> getAllPlayers() {
		List<IGPlayer> players = new ArrayList<>();
		try {
			SQLQuery query = new SQLQuery(QueryType.SELECT, IGPlayer.TABLE_NAME);
			ResultSet results = query.getResults();
			
			while (results.next()) {
				IGPlayer igPlayer = new IGPlayer();
				igPlayer.assign(results);
				players.add(igPlayer);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return players;
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
			query.addGrabColumns("name", "uuid", "ip", "nickname");
			query.addValues(player.getName(), player.getUniqueId(), player.getAddress().getAddress().getHostAddress(), "");
			query.execute();
			
			//Grab the IGPlayer for usage.
			IGPlayer igPlayer = getIGPlayerByPlayer(player);
			
			// -- Add IGPlayerStats --
			IGPlayerStatsFactory.add(igPlayer);
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
