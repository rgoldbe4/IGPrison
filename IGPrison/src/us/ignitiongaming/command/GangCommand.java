package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGGangRank;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.factory.gang.IGGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.calculator.GangCalculator;

public class GangCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				
				if (lbl.equalsIgnoreCase("gang")) {
					IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
					boolean isPlayerInGang = IGPlayerGangFactory.isPlayerInGang(igPlayer);
					
					// [/gang] Display info on gang of current member
					if (args.length == 0) {
						
						if (isPlayerInGang) {
							//Dislay the information of the player's gang.
							displayGangInformation(player, igPlayer);
						} else {
							//They aren't in a gang! So... Tell them that and give them help command.
							player.sendMessage(GlobalTags.GANG + "§4You are not in a gang.§r Try §8§o/gang help");
						}
					}
					
					
					if (args.length == 1) {
						
						// [/gang help]
						if (args[0].equalsIgnoreCase("help")) {
							player.sendMessage(" -- " + GlobalTags.GANG + "--");
							player.sendMessage(" §8§o/gang §lcreate §r§8§o<name> §r§7§oCreates a new gang. This action costs money.");
							player.sendMessage(" §8§o/gang §ldisband §r§7§oDisband a gang. You must be the only leader of the gang.");
							player.sendMessage("§8§o/gang §lpromote §r§8§o<player> §r§7§oPromote <player> to the next highest rank (Leader > Officer > Member).");
							player.sendMessage(" §8§o/gang §ladd §r§8§o<player> §r§7§oAdd <player> to the gang. This costs based on how many members are in your gang.");
							player.sendMessage(" §8§o/gang §ldemote §r§8§o<player> §r§7§oDemote <player> from their rank to a lower rank (Member < Officer < Leader).");
							player.sendMessage(" §8§o/gang §lremove §r§8§o<player> §r§7§oRemoves <player> from the gang. This action is free.");
							player.sendMessage(" §8§o/gang §linfo §r§8§o<gang> §r§7§oDisplays public information of a <gang>.");
							player.sendMessage(" §8§o/gang §llist §r§7§oDisplays all gangs and their leaders.");
							player.sendMessage(" §8§o/gang §lranks §r§7§oDisplays all of the ranks and their functionality.");
							player.sendMessage(" §8§o/gang §laccept §r§7§oAccept the invitation to join a gang.");
							player.sendMessage(" §8§o/gang §ldecline §r§7§oDecline the invitation to join the gang.");
						}
						
						// [/gang disband]
						if (args[0].equalsIgnoreCase("disband")) {
							
							//Step 1: Determine if the player is in a gang.
							if (isPlayerInGang) {
								
								//Step 2: Determine if the player is a leader...
								IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
								int gangID = playerGang.getGangID();
								
								if (playerGang.getGangRank() == IGGangRank.LEADER) {
									
									//Step 3: Determine if the player is the only leader.
									List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(gangID);
									int leaderCount = 0;
									for (IGPlayerGang playerInGang : playersInGang) {
										if (playerInGang.getGangRank() == IGGangRank.LEADER) leaderCount++;
									}
									
									if (leaderCount > 1) {
										player.sendMessage(GlobalTags.GANG + "§4You are not the only leader of the gang.");
									} else {
										
										//Step 4: Remove all members of the gang from the gang.
										for (IGPlayerGang playerInGang : playersInGang) playerInGang.delete();
										
										//Step 5: Remove the gang.
										IGGang gang = IGGangFactory.getGangById(gangID);
										String gangName = gang.getName();
										gang.delete();
										
										Bukkit.broadcastMessage(GlobalTags.GANG + "§c" + gangName + " has been disbanded.");
										
									}
									
								} else {
									player.sendMessage(GlobalTags.GANG + "§4You are not the leader of the gang.");
								}
								
								
							} else {
								player.sendMessage(GlobalTags.GANG + "§4You do not belong to a gang.");
							}
						}
						
						
					}
					
					if (args.length == 2) {
						
						// [/gang create <name>]
						if (args[0].equalsIgnoreCase("create")) {
							String name = ConvertUtils.getStringFromCommand(1, args); //e.g. The White Wolves
							
							//Step 1: Determine if player is in gang. If so, they cannot create a gang...
							if (isPlayerInGang) {
								player.sendMessage(GlobalTags.GANG + "§4You do not belong to a gang.");
							} else {
								//Step 2: Determine if the player can afford to create a new gang.
								double createPrice = Double.parseDouble(ServerDefaults.getSetting(IGSettings.CREATE_GANG_PRICE).getValue().toString());
								double playerBalance = ServerDefaults.econ.getBalance(player);
								
								if (createPrice > playerBalance) {
									player.sendMessage(GlobalTags.GANG + "§cYou cannot afford to create a new gang. It currently costs $" + createPrice + ".");
								} else {
									//Step 3: Remove the money from the player's account.
									ServerDefaults.econ.withdrawPlayer(player, createPrice);
									
									//Step 4: Create the gang...
									IGGangFactory.add(name);
									
									//Step 5: Add the player to the gang as leader...
									IGGang igGang = IGGangFactory.getGangByName(name); //TODO -> Have the "add" method return the IGGang rather than trusting SQL for string comparison.
									IGPlayerGangFactory.add(igPlayer, igGang, IGGangRank.LEADER);
									
									//Step 6: Let the player know the request is done.
									Bukkit.broadcastMessage(GlobalTags.GANG + "§a" + name + " is now a gang!");
								}
							}
						}
						
						// [/gang add <player>]
						if (args[0].equalsIgnoreCase("add")) {
							
							//Step 1: Determine if the player is in a gang.
							if (isPlayerInGang) {
								
								//Step 2: Determine if the player is an officer or leader.
								IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
								if (igPlayerGang.getGangRank() == IGGangRank.LEADER || igPlayerGang.getGangRank() == IGGangRank.OFFICER) {
									
									//Step 3: Determine if the gang is closed or not.
									IGGang gang = IGGangFactory.getGangById(igPlayerGang.getGangID());
									
									if (gang.isClosed()) {
										player.sendMessage(GlobalTags.GANG + "§4Your gang has closed off the gang to new members.");
									} else {
										
										//Step 4: Determine if the gang's money can afford a new member...
										// Calculation: Yay Calculators.
										double costForNewMember = GangCalculator.costforNewMember(gang);
										if (costForNewMember > gang.getMoney()) {
											player.sendMessage(GlobalTags.GANG + "§4Your gang cannot afford a new member. Cost: $" + costForNewMember);
										} else {
											
											//Step 5: Determine if the player is online or not.
											Player target = Bukkit.getPlayer(args[1]);
											if (target != null) {
												
												//Step 6: Send the player a request and give them a message to accept/decline.
											} else {
												//Step 6b: Check if the IGPlayer exists...
												IGPlayer igTarget = IGPlayerFactory.
											}
										}
									}
								} else {
									player.sendMessage(GlobalTags.GANG + "§4You are not an officer or leader of the gang.");
								}
							} else {
								player.sendMessage(GlobalTags.GANG + "§4You do not belong to a gang.");
							}
						}
					}
					
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	/**
	 * Display A Player's Gang Information
	 * @param player
	 * @param igPlayer
	 */
	private void displayGangInformation(Player player, IGPlayer igPlayer) {
		//Get the player's current PlayerGang information to get a Gang.
		IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
		IGGang gang = IGGangFactory.getGangById(playerGang.getGangID());
		List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(gang.getId());
		
		player.sendMessage("=== { " + gang.getName().toUpperCase() + " } ===");
		player.sendMessage("Money Pot: $" + gang.getMoney());
		player.sendMessage("Defiance Points: " + gang.getPoints());
		player.sendMessage("-----------------------");
		
		//Go through each gang and find out their name and rank... Sort by ranks (Leader > Officer > Member) in query.
		for (IGPlayerGang playerInGang : playersInGang) {
			IGPlayer igPlayerInGang = IGPlayerFactory.getIGPlayerById(playerInGang.getPlayerID());
			player.sendMessage(playerInGang.getGangRank().getLabel() + " " + igPlayerInGang.getName());
		}
		
	}
	
	public void displayGeneralGangInformation(IGGang gang) {
		
	}

}
