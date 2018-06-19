package us.ignitiongaming;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.ignitiongaming.command.AdminCommand;
import us.ignitiongaming.command.DonatorCommand;
import us.ignitiongaming.command.HelpCommand;
import us.ignitiongaming.command.RankupCommand;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.event.player.PlayerChatEvent;
import us.ignitiongaming.event.player.PlayerRecordEvent;
import us.ignitiongaming.event.player.PlayerVerificationEvent;
import us.ignitiongaming.event.server.ServerListEvent;


public class IGPrison extends JavaPlugin {	
	public void onEnable() {
		
		//You know? Vault is kinda stupid for making me use a global variable...
		setupEconomy();
		
		/* Events */
		this.getServer().getPluginManager().registerEvents(new PlayerVerificationEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerRecordEvent(), this);
		this.getServer().getPluginManager().registerEvents(new ServerListEvent(), this);
		this.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
		
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
	
	public void onDisable() {
		
	}
	
	/**
	 * WEEEEEE private function to assign global variable >.>
	 * @return
	 */
	private boolean setupEconomy()
    {
		
        RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (economyProvider != null) {
            ServerDefaults.econ = economyProvider.getProvider();
            
        }

        return (ServerDefaults.econ != null);
    }
}
