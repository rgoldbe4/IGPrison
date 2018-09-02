package us.ignitiongaming.util.handy;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import us.ignitiongaming.IGPrison;
import us.ignitiongaming.util.convert.TickConverter;

public class ScoreboardAnnouncer {

	private Player player;
	private ScoreboardManager manager;
	private Objective objective;
	private Scoreboard board;
	private List<String> texts = new ArrayList<>();
	private int seconds = 0;
	private DisplaySlot displaySlot = DisplaySlot.SIDEBAR;
	
	/**
	 * Hook a Scoreboard to a Player
	 * @param player
	 */
	public ScoreboardAnnouncer(Player player) {
		this.player = player;
		init();
	}
	
	private void init() {
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		
		objective = board.registerNewObjective("scoreboardcore", "dummy");
		objective.setDisplaySlot(displaySlot);
	}
	
	/**
	 * Set the Player (override Player in constructor)
	 * @param player
	 */
	public void setPlayer(Player player) { this.player = player; }
	public Player getPlayer() { return player; }
	
	/**
	 * Set the title (display name) of the scoreboard.
	 * @param title
	 */
	public void setTitle(String title) {
		if (title.length() >= 32) {
			Bukkit.getLogger().log(Level.WARNING, "The title for 'scoreboardcore' is too long.");
			return;
		}
		
		objective.setDisplayName(title);
	}
	
	/**
	 * Add a line with text.
	 * @param text
	 */
	public void addLine(String text) {
		texts.add(text);
	}
	
	/**
	 * Add a space between rows.
	 */
	public void addSpacer() {
		texts.add(" ");
	}
	
	public void addLineBreak() {
		texts.add("--------------------");
	}
	
	/**
	 * Bind the scoreboard to the player. Will override other scoreboards.
	 */
	public void hook() {
		for (int i = texts.size() - 1; i > -1; i--) {
			objective.getScore(texts.get(i)).setScore(i);
		}
		player.setScoreboard(board);
		if (seconds != 0) {
			Bukkit.getScheduler().runTaskLater(IGPrison.plugin, new Runnable() {
				@Override
				public void run(){
					board.clearSlot(displaySlot);
				}
			}, TickConverter.getTicksInSeconds(seconds));
		}
	}
	
	/**
	 * Add a timer to make the scoreboard disappear. (Recommended)
	 * @param seconds
	 */
	public void addTimer(int seconds) {
		this.seconds = seconds;
	}
	
	/**
	 * Remove the current scoreboard from the player in the specific location (slot) given.
	 * @param player
	 */
	public static void removeScoreboard(Player player, DisplaySlot displaySlot) {
		player.getScoreboard().clearSlot(displaySlot);
	}
	
	public void setDisplaySlot(DisplaySlot slot) { 
		this.displaySlot = slot; 
		init();
	}
	
	public DisplaySlot getDisplaySlot() { return displaySlot; }
	
	
}
