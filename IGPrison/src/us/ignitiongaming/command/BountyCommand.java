package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.entity.bounty.IGBounty;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGBountyProgress;
import us.ignitiongaming.enums.IGRankNodes;
import us.ignitiongaming.factory.bounty.IGBountyFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.CurrencyConverter;

public class BountyCommand implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		try {
			if (sender instanceof Player) {
				Player player = (Player) sender;
				IGPlayer igPlayer = IGPlayerFactory.getIGPlayerByPlayer(player);
				
				if (lbl.equalsIgnoreCase("bounty")) {
					
					// [/bounty]
					if (args.length == 0) {
						//Display the help command.
						viewBountiesMadeByPlayer(player, igPlayer);
					}
					
					else if (args.length == 1) {
						
						// [/bounty help]
						if (args[0].equalsIgnoreCase("help")) {
							displayHelp(player);
						}
						
						// [/bounty list]
						else if (args[0].equalsIgnoreCase("list")) {
							displayList(player);
						}
						
						// [/bounty me]
						else if (args[0].equalsIgnoreCase("me")) {
							viewCurrentBounties(player, igPlayer);
						}
						
						else {
							
						}
						
					} 
					
					else if (args.length == 2) {
						// [/bounty remove <name>] - OR - [/bounty cancel <name>] - OR - [/bounty stop <name>]
						if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("cancel") || args[0].equalsIgnoreCase("stop")) {
							removeBounty(player, igPlayer, args[1]);
						}
						// [/bounty view <id>]
						else if (args[0].equalsIgnoreCase("view")) {
							viewBounty(player, args[1]);
						}
					}
					
					else if (args.length == 3) {
						// [/bounty add <name> <amount>] - OR - [/bounty create <name> <amount>] - OR - [/bounty start <name> <amount>]
						if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("start")) {
							createBounty(player, igPlayer, args[1], args[2]);
						}
					}
					
					else {
						displayHelp(player);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		return false;
	}

	private void displayHelp(Player player) {
		ChatConverter.clearPlayerChat(player);
		player.sendMessage("§7 ---- " + GlobalTags.BOUNTY + "§4HELP §7-----");
		player.sendMessage(" §9/bounty §7-> §fDisplays all bounties ongoing you made.");
		player.sendMessage(" §9/bounty §lme §r§7-> §fDisplays all of the ongoing bounties targetting you.");
		player.sendMessage(" §9/bounty §lhelp §r§7-> §fDisplays the help menu.");
		player.sendMessage(" §9/bounty §llist §r§7-> §fDisplays all currently ongoing bounties.");
		player.sendMessage(" §9/bounty §ladd §r§9§o<name> <amount> §r§7-> §fCreate a new bounty against a player.");
		player.sendMessage(" §9/bounty §lremove §r§9§o<name> §r§7-> §fRemove an ongoing bounty against a player.");
		player.sendMessage(" §9/bounty §lview §r§9§o<id> §r§7-> §fView the progress of a bounty by the identifier.");
	}

	/**
	 * Use this to display any bounties.
	 * @param player
	 * @param igBounty
	 */
	private void displayAdvancedBounty(Player player, IGBounty igBounty) {
		IGPlayer target = IGPlayerFactory.getIGPlayerById(igBounty.getTargetId());
		IGPlayer creator = IGPlayerFactory.getIGPlayerById(igBounty.getCreatorId());
		String currencyAmount = CurrencyConverter.convertToCurrency(igBounty.getAmount());
		
		// Default Template
		player.sendMessage("");
		player.sendMessage("§8-------- §4§lBounty #" + igBounty.getId() + "§r §8--------");
		player.sendMessage("");
		player.sendMessage(" §7Status: " + igBounty.getProgress().getLabel());
		player.sendMessage(" §7Creator: §f" + creator.getName());
		player.sendMessage(" §7Target: §f" + target.getName());
		player.sendMessage(" §7Reward: §a" + currencyAmount);
		player.sendMessage("");
		player.sendMessage("§8--------------------------");
		player.sendMessage("");
	}
	
	private void displayBounty(Player player, IGBounty igBounty) {
		IGPlayer target = IGPlayerFactory.getIGPlayerById(igBounty.getTargetId());
		String currencyAmount = CurrencyConverter.convertToCurrency(igBounty.getAmount());
		player.sendMessage(" §7[§4" + igBounty.getId() + "§7] §7(" + igBounty.getProgress().getLabel() + "§7) §fTarget: §6" + target.getName() + " §7- §fPrice: §a" + currencyAmount);
	}

	private void displayList(Player player) {
		List<IGBounty> bounties = IGBountyFactory.getBountiesByProgress(IGBountyProgress.ONGOING);
		if (bounties.size() == 0) {
			player.sendMessage(GlobalTags.BOUNTY + "§cNo currently ongoing bounties.");
		} else {
			ChatConverter.clearPlayerChat(player);
			for (IGBounty bounty : bounties) {
				displayBounty(player, bounty);
			}
		}
	}

	private void viewBounty(Player player, String identifier) {
		try {
			int id = Integer.parseInt(identifier);
			IGBounty bounty = IGBountyFactory.getBountyById(id);
			
			if (bounty.isValid()) {
				displayAdvancedBounty(player, bounty);
			} else {
				player.sendMessage(GlobalTags.BOUNTY + "§4The id you entered (" + identifier + ") was not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			player.sendMessage(GlobalTags.BOUNTY + "§4The id you entered (" + identifier + ") was invalid.");
		}
	}
	
	private void createBounty(Player player, IGPlayer igCreator, String target, String amount) {
		try {
			double amt = Double.parseDouble(amount);
			double balance = ServerDefaults.econ.getBalance(player);
			IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(target);
			
			//Step 1: Determine if the target exists.
			if (igTarget.isValid()) {
				
				//Step 2: Determine if the target has an existing bounty...
				if (!IGBountyFactory.doesPlayerAlreadyHaveOngoingBounty(igCreator, igTarget)) {
					
					//Step 3: Determine if the player has enough money.
					if (amt <= 0) {
						player.sendMessage(GlobalTags.BOUNTY + "§4You entered an invalid amount to pay the bounty.");
					} else if (amt <= balance) {
						
						//Step 4: Place the bounty.
						IGBountyFactory.add(igCreator, igTarget, amt);
						IGRankNodes playerRank = IGRankNodes.getPlayerRank(player);
						//Step 5: Deduct from the player.
						ServerDefaults.econ.withdrawPlayer(player, amt);
						
						StringBuilder builder = new StringBuilder();
						builder.append(" " + playerRank.getFormatting() + player.getName() + " §fhas placed a new bounty!");
						
						Bukkit.broadcastMessage(GlobalTags.BOUNTY);
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(builder.toString());
						Bukkit.broadcastMessage(" ");
						Bukkit.broadcastMessage(GlobalTags.BOUNTY);
						
					} else {
						player.sendMessage(GlobalTags.BOUNTY + "§4You do not have enough money to place the bounty.");
					}
				} else {
					player.sendMessage(GlobalTags.BOUNTY + "§4You already have a bounty toward that player.");
				}
			} else {
				player.sendMessage(GlobalTags.BOUNTY + "§4The player you entered (§l" + target + "§r§4) was not found.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void viewBountiesMadeByPlayer(Player player, IGPlayer igPlayer) {
		ChatConverter.clearPlayerChat(player);
		List<IGBounty> bounties = IGBountyFactory.getBountiesByPlayer(igPlayer); //We want all bounties.
		if (bounties.size() == 0) player.sendMessage(GlobalTags.BOUNTY + "§cYou do not have any bounties.");
		for (IGBounty bounty : bounties) {
			displayBounty(player, bounty);
		}
	}
	private void viewCurrentBounties(Player player, IGPlayer igPlayer) {
		ChatConverter.clearPlayerChat(player);
		List<IGBounty> bounties = IGBountyFactory.getBountiesTargettingPlayer(igPlayer, IGBountyProgress.ONGOING); //We only want current bounties, not finished ones.
		if (bounties.size() == 0) player.sendMessage(GlobalTags.BOUNTY + "§cYou do not have any ongoing bounties.");
		for (IGBounty bounty : bounties) {
			displayBounty(player, bounty);
		}
	}
	
	private void removeBounty(Player player, IGPlayer igPlayer, String playerName) {
		IGPlayer target = IGPlayerFactory.getIGPlayerFromName(playerName);
		//Step 1: Determine if the target exists.
		if (target.isValid()) {
			//Step 2: Determine if the target has a bounty against them by you.
			if (IGBountyFactory.doesPlayerAlreadyHaveOngoingBounty(igPlayer, target)) {
				//Step 3: Mark it as cancelled and give the player their money back.
				IGBounty bounty = IGBountyFactory.getBountyByTargetAndCreator(igPlayer, target);
				
				bounty.cancel();
				ServerDefaults.econ.depositPlayer(player, bounty.getAmount());
				bounty.save();
				
				//Step 4: Alert the player and the target (if online).
				player.sendMessage(GlobalTags.BOUNTY + "§aYou have removed the bounty.");
				Player targetPlayer = Bukkit.getPlayer(target.getName());
				if (targetPlayer != null) targetPlayer.sendMessage(GlobalTags.BOUNTY + "§e" + player.getName() + "§f has removed their bounty against you.");
				
			} else {
				player.sendMessage(GlobalTags.BOUNTY + "§4You do not have an ongoing bounty toward that player.");
			}
			
		} else {
			player.sendMessage(GlobalTags.BOUNTY + "§4The player you entered, " + playerName + ", was not found.");
		}
	}
}
