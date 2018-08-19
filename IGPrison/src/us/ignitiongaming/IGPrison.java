package us.ignitiongaming;

import java.util.logging.Level;

import net.milkbowl.vault.economy.Economy;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import us.ignitiongaming.command.AdminCommand;
import us.ignitiongaming.command.BountyCommand;
import us.ignitiongaming.command.ClearChatCommand;
import us.ignitiongaming.command.ConvertCommand;
import us.ignitiongaming.command.DevelopmentCommand;
import us.ignitiongaming.command.DonatorCommand;
import us.ignitiongaming.command.GangCommand;
import us.ignitiongaming.command.HelpCommand;
import us.ignitiongaming.command.IGBanCommand;
import us.ignitiongaming.command.IGKickCommand;
import us.ignitiongaming.command.LinkCommand;
import us.ignitiongaming.command.LockdownCommand;
import us.ignitiongaming.command.NicknameCommand;
import us.ignitiongaming.command.RankupCommand;
import us.ignitiongaming.command.SmeltCommand;
import us.ignitiongaming.command.SolitaryCommand;
import us.ignitiongaming.command.StaffChatCommand;
import us.ignitiongaming.command.StaffCommand;
import us.ignitiongaming.command.TeleportCommand;
import us.ignitiongaming.command.TowerCommand;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.other.IGSetting;
import us.ignitiongaming.enums.IGEnvironments;
import us.ignitiongaming.event.bounty.KillPlayerWithBountyEvent;
import us.ignitiongaming.event.gang.DrugUseEvent;
import us.ignitiongaming.event.gang.GangAttackEvent;
import us.ignitiongaming.event.gang.PendingRequestEvent;
import us.ignitiongaming.event.other.BatonAttackEvent;
import us.ignitiongaming.event.other.FancySignEvent;
import us.ignitiongaming.event.player.GuardDeathEvent;
import us.ignitiongaming.event.player.InteractBuySignEvent;
import us.ignitiongaming.event.player.InteractSellHeadSignEvent;
import us.ignitiongaming.event.player.InteractSellSignEvent;
import us.ignitiongaming.event.player.PickaxeDamageEvent;
import us.ignitiongaming.event.player.PlaceBuySignEvent;
import us.ignitiongaming.event.player.PlaceSellSignEvent;
import us.ignitiongaming.event.player.PlayerChatEvent;
import us.ignitiongaming.event.player.PlayerListEvent;
import us.ignitiongaming.event.player.PlayerRecordEvent;
import us.ignitiongaming.event.player.PlayerSpawnEvent;
import us.ignitiongaming.event.player.PlayerVerificationEvent;
import us.ignitiongaming.event.server.NotifyPlayerConnectionEvent;
import us.ignitiongaming.event.server.ServerListEvent;
import us.ignitiongaming.event.solitary.VerifySolitaryEvent;
import us.ignitiongaming.factory.other.IGSettingFactory;


public class IGPrison extends JavaPlugin {	
	
	public static Plugin plugin;
	public static IGEnvironments environment;
	
	public void onEnable() {
		try {
			plugin = this;
			
			this.saveDefaultConfig();
	
			//This must go first... So we connect to the right database.
			//Display this to console.
			this.getLogger().log(Level.WARNING, "Config Environment: " + this.getConfig().getString("environment"));
			if (this.getConfig().getString("environment").contains("main")) {
				environment = IGEnvironments.MAIN;
			} else {
				environment = IGEnvironments.TESTING;
			}
			this.getLogger().log(Level.INFO, "Assigned Environment: " + environment);
			
			//You know? Vault is kinda stupid for making me use a global variable...
			setupEconomy();
			this.getLogger().log(Level.INFO, "Economy linked to plugin.");
			
			//Setup settings..
			ServerDefaults.settings = IGSettingFactory.getSettings();
			this.getLogger().log(Level.INFO, "Settings applied to plugin. Applied: ");
			for (IGSetting setting : ServerDefaults.settings) {
				this.getLogger().log(Level.INFO, setting.getId() + ": " + setting.getLabel().toUpperCase() + " = " + setting.getValue().toString());
			}
			
			/* Events */
			this.getServer().getPluginManager().registerEvents(new PlayerVerificationEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlayerRecordEvent(), this);
			this.getServer().getPluginManager().registerEvents(new ServerListEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlayerChatEvent(), this);
			this.getServer().getPluginManager().registerEvents(new VerifySolitaryEvent(), this);
			this.getServer().getPluginManager().registerEvents(new InteractSellSignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlaceSellSignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new FancySignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlayerSpawnEvent(), this);
			this.getServer().getPluginManager().registerEvents(new GuardDeathEvent(), this);
			this.getServer().getPluginManager().registerEvents(new NotifyPlayerConnectionEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PendingRequestEvent(), this);
			this.getServer().getPluginManager().registerEvents(new GangAttackEvent(), this);
			this.getServer().getPluginManager().registerEvents(new KillPlayerWithBountyEvent(), this);
			this.getServer().getPluginManager().registerEvents(new DrugUseEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlayerListEvent(), this);
			this.getServer().getPluginManager().registerEvents(new InteractSellHeadSignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PlaceBuySignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new InteractBuySignEvent(), this);
			this.getServer().getPluginManager().registerEvents(new PickaxeDamageEvent(), this);
			this.getServer().getPluginManager().registerEvents(new BatonAttackEvent(), this);
			
			/* Commands */
			// -- Help Command --
			this.getCommand("ighelp").setExecutor(new HelpCommand());
			this.getCommand("now").setExecutor(new HelpCommand());
			
			// -- Donator Commands --
			this.getCommand("donate").setExecutor(new DonatorCommand());
			this.getCommand("points").setExecutor(new DonatorCommand());
			
			// -- Admin Commands --
			this.getCommand("iga").setExecutor(new AdminCommand());
			
			// -- Rankup Command --
			this.getCommand("rankup").setExecutor(new RankupCommand());
			this.getCommand("setrank").setExecutor(new RankupCommand());
			
			// -- Teleport Commands --
			this.getCommand("spawn").setExecutor(new TeleportCommand());
			this.getCommand("warp").setExecutor(new TeleportCommand());
			this.getCommand("setspawn").setExecutor(new TeleportCommand());
			this.getCommand("goto").setExecutor(new TeleportCommand());
			this.getCommand("bring").setExecutor(new TeleportCommand());
			
			// -- Solitary Commands --
			this.getCommand("solitary").setExecutor(new SolitaryCommand());
			
			// -- Smelt Command --
			this.getCommand("smelt").setExecutor(new SmeltCommand());
			
			// -- Staff Chat Command --
			this.getCommand("sc").setExecutor(new StaffChatCommand());
			this.getCommand("staffchat").setExecutor(new StaffChatCommand());
			
			// -- Lockdown Command --
			this.getCommand("lockdown").setExecutor(new LockdownCommand());
			
			// -- Dev Commands (YAY!) --
			this.getCommand("igdev").setExecutor(new DevelopmentCommand());
			
			// -- Kick Ban Command --
			this.getCommand("igkick").setExecutor(new IGKickCommand());
			this.getCommand("igban").setExecutor(new IGBanCommand());
			this.getCommand("igunban").setExecutor(new IGBanCommand());
			
			// -- Clear Chat Command --
			this.getCommand("clearchat").setExecutor(new ClearChatCommand());
			
			// -- Nickname commands --
			this.getCommand("whois").setExecutor(new NicknameCommand());
			this.getCommand("nickname").setExecutor(new NicknameCommand());
			
			// -- Clockin/out commands --
			this.getCommand("guard").setExecutor(new StaffCommand());
			this.getCommand("warden").setExecutor(new StaffCommand());
			
			// -- Link command --
			this.getCommand("link").setExecutor(new LinkCommand());
			
			// -- Gang command --
			this.getCommand("gang").setExecutor(new GangCommand());
			
			// - Bounty command --
			this.getCommand("bounty").setExecutor(new BountyCommand());
			
			// - Tower command --
			this.getCommand("tower").setExecutor(new TowerCommand());
			
			// - Visit command --
			this.getCommand("visit").setExecutor(new TeleportCommand());
			
			// - Convert command --
			this.getCommand("convert").setExecutor(new ConvertCommand());
			
			// - Discord command --
			this.getCommand("discord").setExecutor(new DonatorCommand());
		
		} catch (Exception ex) {
			
		}
		
	}
	
	public void onDisable() {
		this.getLogger().log(Level.SEVERE, "IGPlugin is now disabled. Any data after this moment will not be saved or extracted.");
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
