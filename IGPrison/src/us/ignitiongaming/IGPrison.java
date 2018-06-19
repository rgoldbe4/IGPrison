package us.ignitiongaming;

import org.bukkit.plugin.java.JavaPlugin;

import us.ignitiongaming.command.AdminCommand;
import us.ignitiongaming.command.DonatorCommand;
import us.ignitiongaming.command.HelpCommand;
import us.ignitiongaming.command.RankupCommand;
import us.ignitiongaming.event.player.PlayerVerificationEvent;


public class IGPrison extends JavaPlugin {

	@Override
	public void onEnable() {
		
		/* Events */
		this.getServer().getPluginManager().registerEvents(new PlayerVerificationEvent(), this);
		
		/* Commands */
		// -- Help Command --
		this.getCommand("ighelp").setExecutor(new HelpCommand());
		
		// -- Donator Commands --
		this.getCommand("donate").setExecutor(new DonatorCommand());
		this.getCommand("claimdonator").setExecutor(new DonatorCommand());
		this.getCommand("points").setExecutor(new DonatorCommand());
		this.getCommand("donatorpoints").setExecutor(new DonatorCommand());
		
		// -- Admin Commands --
		this.getCommand("iga").setExecutor(new AdminCommand());
		
		// -- Rankup Command --
		this.getCommand("rankup").setExecutor(new RankupCommand());
	}
	
	@Override
	public void onDisable() {
		
	}
}
