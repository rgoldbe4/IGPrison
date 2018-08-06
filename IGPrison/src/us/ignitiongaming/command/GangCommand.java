package us.ignitiongaming.command;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import us.ignitiongaming.config.GlobalMessages;
import us.ignitiongaming.config.GlobalTags;
import us.ignitiongaming.config.ServerDefaults;
import us.ignitiongaming.database.ConvertUtils;
import us.ignitiongaming.entity.gang.IGGang;
import us.ignitiongaming.entity.gang.IGPlayerGang;
import us.ignitiongaming.entity.gang.IGPlayerGangRequest;
import us.ignitiongaming.entity.player.IGPlayer;
import us.ignitiongaming.enums.IGDrugType;
import us.ignitiongaming.enums.IGGangRank;
import us.ignitiongaming.enums.IGPlayerGangRequestAnswer;
import us.ignitiongaming.enums.IGSettings;
import us.ignitiongaming.factory.gang.IGGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangFactory;
import us.ignitiongaming.factory.gang.IGPlayerGangRequestFactory;
import us.ignitiongaming.factory.player.IGPlayerFactory;
import us.ignitiongaming.singleton.IGList;
import us.ignitiongaming.util.calculator.GangCalculator;
import us.ignitiongaming.util.convert.ChatConverter;
import us.ignitiongaming.util.convert.CurrencyConverter;

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
					else if (args.length == 1) {
						
						// [/gang help]
						if (args[0].equalsIgnoreCase("help")) {
							showHelpCommand(player);
						}
						
						// [/gang disband]
						else if (args[0].equalsIgnoreCase("disband")) {
							disbandGang(player, igPlayer, isPlayerInGang);
						}
						
						// [/gang requests]
						else if (args[0].equalsIgnoreCase("requests")) {
							showRequests(player, igPlayer, isPlayerInGang);
						}
						
						// [/gang list] [/gang ranking]
						else if (args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("ranking")) {
							showAllGangs(player);
						}
						
						// [/gang ranks]
						else if (args[0].equalsIgnoreCase("ranks")) {
							showAllRanks(player);
						}
						
						// [/gang chat]
						else if (args[0].equalsIgnoreCase("chat")) {
							toggleChat(player);
							//The actual talking event is handled in Player Events -> PlayerChatEvent
						}
						
						// [/gang canbuydrugs]
						else if (args[0].equalsIgnoreCase("canbuydrugs")) {
							toggleCanMembersBuyDrugs(player, igPlayer, isPlayerInGang);
						}
						
						// [/gang drugs]
						else if (args[0].equalsIgnoreCase("drugs")) {
							buyDrug(player, igPlayer, isPlayerInGang, ""); //Voluntary send it an invalid drug so it displays the drug types.
						}
						
						else if (args[0].equalsIgnoreCase("leave")) {
							leaveGang(player, igPlayer, isPlayerInGang);
						}
						
						else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
						
						
					}
					
					else if (args.length == 2) {
						
						// [/gang create <name>]
						if (args[0].equalsIgnoreCase("create")) {
							String name = ConvertUtils.getStringFromCommand(1, args); //e.g. The White Wolves
							createGang(player, igPlayer, isPlayerInGang, name);
						}
						
						// [/gang add <player>]
						else if (args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("invite")) {
							addPlayerToGang(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						// [/gang remove <player>]
						else if (args[0].equalsIgnoreCase("remove")) {
							removePlayerFromGang(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						// [/gang promote <player>]
						else if (args[0].equalsIgnoreCase("promote")) {
							promotePlayer(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						// [/gang demote <player>]
						else if (args[0].equalsIgnoreCase("demote")) {
							demotePlayer(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						// [/gang info <name>]
						else if (args[0].equalsIgnoreCase("info")) {
							showGangInformation(player, igPlayer, ConvertUtils.getStringFromCommand(1, args));
						}
						
						// [/gang accept <name>]
						else if (args[0].equalsIgnoreCase("accept")) {
							acceptGang(player, igPlayer, isPlayerInGang, ConvertUtils.getStringFromCommand(1, args));
						}
						
						// [/gang decline <name>]
						else if (args[0].equalsIgnoreCase("decline")) {
							declineGang(player, igPlayer, isPlayerInGang, ConvertUtils.getStringFromCommand(1, args));
						}
						
						// [/gang addfunds <amount>
						else if (args[0].equalsIgnoreCase("addfunds")) {
							addFunds(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						// [/gang help <page>]
						else if (args[0].equalsIgnoreCase("help")) {
							if (args[1].equalsIgnoreCase("1")) {
								showHelpCommand(player);
							} else {
								showSecondPageHelpCommand(player);
							}
						}
						
						// [/gang drugs <type>]
						else if (args[0].equalsIgnoreCase("drugs")) {
							buyDrug(player, igPlayer, isPlayerInGang, args[1]);
						}
						
						else {
							player.sendMessage(GlobalMessages.INVALID_COMMAND);
						}
					}
					
					else {
						showHelpCommand(player);
					}
					
				}
				
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}
	
	//When they run [/gang]
	private void displayGangInformation(Player player, IGPlayer igPlayer) {
		//Get the player's current PlayerGang information to get a Gang.
		IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
		IGGang gang = IGGangFactory.getGangById(playerGang.getGangId());
		List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(gang.getId());
		
		ChatConverter.clearPlayerChat(player);
		
		player.sendMessage("=== { §9§l" + gang.getName().toUpperCase() + "§f } ===");
		player.sendMessage("  §2§l" + CurrencyConverter.convertToCurrency(gang.getMoney()));
		player.sendMessage(GlobalTags.DEFIANCE + "§5Points§f: §8§l" + gang.getPoints());
		player.sendMessage("Can members buy drugs using gang money? §e" + gang.canMembersBuyDrugs()); 
		player.sendMessage("-----------------------");
		
		//Go through each gang and find out their name and rank... Sort by ranks (Leader > Officer > Member) in query.
		for (IGPlayerGang playerInGang : playersInGang) {
			IGPlayer igPlayerInGang = IGPlayerFactory.getIGPlayerById(playerInGang.getPlayerId());
			player.sendMessage(playerInGang.getGangRank().getLabel() + " - " + igPlayerInGang.getName());
		}
		
	}
	
	private void showHelpCommand(Player player) {
		ChatConverter.clearPlayerChat(player);
		
		player.sendMessage(" -- " + GlobalTags.GANG + " [1/2] --");
		player.sendMessage(" §8§o/gang §lcreate §r§8§o<name> §r§7§oCreates a new gang. This action costs money.");
		player.sendMessage(" §8§o/gang §ldisband §r§7§oDisband your gang. You must be the only leader of the gang.");
		player.sendMessage(" §8§o/gang §laccept §r§8§o<gang> §r§7§oAccept the invitation to join a gang.");
		player.sendMessage(" §8§o/gang §ldecline §r§8§o<gang> §r§7§oDecline the invitation to join the gang.");
		player.sendMessage(" §8§o/gang §laddfunds §r§8§o<amount> §r§7§oAdd money to the gang through your bank account.");
		player.sendMessage(" §8§o/gang §linfo §r§8§o<gang> §r§7§oDisplays public information of a <gang>.");
		player.sendMessage(" §8§o/gang §llist §r§7§oDisplays all gangs and their leaders.");
		player.sendMessage(" §8§o/gang §lranks §r§7§oDisplays all of the ranks and their functionality.");
		player.sendMessage(" §8§o/gang §lleave §r§7§oLeave the gang you are in.");
		
	}
	
	public void showSecondPageHelpCommand(Player player) {
		ChatConverter.clearPlayerChat(player);
		
		player.sendMessage(" -- " + GlobalTags.GANG + " [2/2] --");
		player.sendMessage("§8§o/gang §lpromote §r§8§o<player> §r§7§oPromote <player> to the next highest rank (Leader > Officer > Member).");
		player.sendMessage(" §8§o/gang §ldemote §r§8§o<player> §r§7§oDemote <player> from their rank to a lower rank (Member < Officer < Leader).");
		player.sendMessage(" §8§o/gang §ladd §r§8§o<player> §r§7§oAdd <player> to the gang. This costs based on how many members are in your gang.");
		player.sendMessage(" §8§o/gang §lremove §r§8§o<player> §r§7§oRemoves <player> from the gang. This action is free.");
		player.sendMessage(" §8§o/gang §lrequests §r§7§oView all requests from your gang (entire history). Must be a Leader.");
		player.sendMessage(" §8§o/gang §lchat §r§7§oToggle gang chat. You will receive chat messages regardless.");
		player.sendMessage(" §8§o/gang §ldrugs §r§8§o<type> §r§7§oBuy drugs from the server using your money or the gang's money (if allowed).");
		player.sendMessage(" §8§o/gang §lcanbuydrugs §r§7§oToggle whether or not gang members can buy drugs using the gang's money.");
	}
	
	private void addPlayerToGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			
			//Step 2: Determine if the player is an officer or leader.
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			if (igPlayerGang.getGangRank() == IGGangRank.LEADER || igPlayerGang.getGangRank() == IGGangRank.OFFICER) {
				
				//Step 3: Determine if the gang is closed or not.
				IGGang gang = IGGangFactory.getGangById(igPlayerGang.getGangId());
				
				if (gang.isClosed()) {
					player.sendMessage(GlobalTags.GANG + "§4Your gang has closed off the gang to new members.");
				} else {
					
					//Step 4: Determine if the gang's money can afford a new member...
					// Calculation: Yay Calculators.
					double costForNewMember = GangCalculator.costforNewMember(gang);
					if (costForNewMember > gang.getMoney()) {
						player.sendMessage(GlobalTags.GANG + "§4Your gang cannot afford a new member. Cost: §l" + CurrencyConverter.convertToCurrency(costForNewMember));
					} else {
						
						//Step 6b: Check if the IGPlayer exists...
						IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(name);
						
						if (igTarget.isValid()) {
							
							//Determine if the player is already in the gang...
							boolean isTargetInGang = IGPlayerGangFactory.isPlayerInGang(igTarget);
							
							if (!isTargetInGang) {
							
								//Step 7b: Check if the player already has a request by the gang.
								IGPlayerGangRequest igGangRequest = IGPlayerGangRequestFactory.getUnansweredRequestByIGPlayerAndIGGang(igTarget, gang);
								
								if (!igGangRequest.isValid()) {
									//Step 8b: Add the request.
									IGPlayerGangRequestFactory.add(igTarget, gang);
									
									//Remove the money from the gang.
									gang.removeMoney(costForNewMember);
									gang.save(); //Issue #51 -> Save after changing a value.
									
									//Step 9b: Let the player know.
									player.sendMessage(GlobalTags.GANG + "§eYour request has been sent to " + igTarget.getName() + ".");
									
									//Test if the player is online, if so, let them know they have a request.
									Player target = Bukkit.getPlayer(name);
									if (target != null) {
										target.sendMessage(GlobalTags.GANG + "§eYou have been invited to join " + gang.getName() + ".");
										target.sendMessage(GlobalTags.GANG + "§eCommand: /gang accept " + gang.getName() + " OR /gang decline " + gang.getName());
									}
									
								} else {
									player.sendMessage(GlobalTags.GANG + "§4This player already has a pending gang request.");
								}
							} else {
								player.sendMessage(GlobalTags.GANG + "§4The player you are attempting to add already belongs to the gang.");
							}
						} else {
							player.sendMessage(GlobalTags.GANG + "§4The player (" + name + ") you entered does not exist.");
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

	private void createGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		//Bug: Strip the name of special characters
		name = ChatConverter.removeSpecialCharacters(name);
		
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
				IGGangFactory.add(name, igPlayer);
				
				//Step 5: Add the player to the gang as leader...
				IGGang igGang = IGGangFactory.getGangByName(name); //TODO -> Have the "add" method return the IGGang rather than trusting SQL for string comparison.
				IGPlayerGangFactory.add(igPlayer, igGang, IGGangRank.LEADER);
				
				//Step 6: Let the player know the request is done.
				Bukkit.broadcastMessage(GlobalTags.GANG + "§a" + name + " is now a gang!");
			}
		}
	}

	private void disbandGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang) {
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			
			//Step 2: Determine if the player is a leader...
			IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			int gangID = playerGang.getGangId();
			
			if (playerGang.getGangRank() == IGGangRank.LEADER) {
				
				//Step 3: Determine if the player is the only leader.
				List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(gangID);
				IGGang gang = IGGangFactory.getGangById(gangID);
				int leaderCount = IGPlayerGangFactory.getLeadersInGang(gang).size();
				
				if (leaderCount > 1) {
					player.sendMessage(GlobalTags.GANG + "§4You are not the only leader of the gang.");
				} else {
					
					//Step 4: Remove all members of the gang from the gang.
					for (IGPlayerGang playerInGang : playersInGang) playerInGang.delete();
					
					//Step 5: Remove the gang.
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

	private void promotePlayer(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		
		if (isPlayerInGang) {
			//Step 1: Determine if the player that is running the command has the correct permissions.
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
			
			if (igPlayerGang.getGangRank() == IGGangRank.LEADER || igPlayer.getId() == igGang.getFounderId()) {
				
				//Step 2: Determine if the target can rank up.
				IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(name);
				
				if (igTarget.isValid()) {
					IGPlayerGang igTargetGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igTarget);
					
					//Additional Step: Determine if the target is in a gang.
					if (igTargetGang.isValid()) {
						
						//Another step: Make sure the target is in the same gang.
						if (igTargetGang.getGangId() == igPlayerGang.getGangId()) {
							if (igTargetGang.getGangRank() == IGGangRank.LEADER) {
								player.sendMessage(GlobalTags.GANG + "§4The player you entered cannot rank up any higher.");
							} else {
								
								//Step 3: Determine what rank the target should be and promote them.
								igTargetGang.promote();
								igTargetGang.save();
								player.sendMessage(GlobalTags.GANG + "§a" + igTarget.getName() + " is now: §f" + igTargetGang.getGangRank().getLabel());
								Player target = Bukkit.getPlayer(name);
								if (target != null || igPlayer.getId() != igGang.getFounderId()) 
									target.sendMessage(GlobalTags.GANG + "§aYou have been promoted by " + player.getName() + " to: §f" + igTargetGang.getGangRank().getLabel());
							}
						} else {
							player.sendMessage(GlobalTags.GANG + "§4The player you entered is not in your gang.");
						}
						
					} else {
						player.sendMessage(GlobalTags.GANG + "§4The player you entered is not in a gang.");
					}
					
				} else {
					player.sendMessage(GlobalTags.GANG + "§4The player (" + name + ") was not found.");
				}
				
			} else {
				player.sendMessage(GlobalTags.GANG + "§4Only a leader of the gang can run this command.");
			}
			
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You do not belong to a gang.");
		}
	}

	private void demotePlayer(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
			
			//Step 2: Determine if the player is a leader.
			if (igPlayerGang.getGangRank() == IGGangRank.LEADER) {
				
				//Step 3: Determine if the name is valid.
				IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(name);
				
				if (igTarget.isValid()) {
					
					//Step 4: Determine if the target is in a gang
					if (IGPlayerGangFactory.isPlayerInGang(igTarget)) {
						IGPlayerGang igTargetGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igTarget);
						
						//Step 5: Determine if the target is in the same gang.
						if (igTargetGang.getGangId() == igPlayerGang.getGangId()) {
							
							//Step 6: Determine if the target can be demoted...
							if (igTargetGang.getGangRank() != IGGangRank.MEMBER) {
								
								//Issue #46: Leaders can demote themselves if they are the last leader.
								int numberOfLeaders = IGPlayerGangFactory.getLeadersInGang(igGang).size();
								
								if (igTargetGang.getGangRank() == IGGangRank.LEADER && numberOfLeaders == 1) {
									player.sendMessage(GlobalTags.GANG + "§4All gangs must have one leader. Try promoting another leader and then demoting that person.");
								} else {
									
									//Issue #47: Founders of the gang cannot be demoted by anyone but themselves.
									if (igGang.getFounderId() == igTarget.getId()) {
										if (igTarget.getId() == igPlayer.getId()) {
											player.sendMessage(GlobalTags.GANG + "§aYou have stepped down as leader. However, you are still the founder of the gang and can reclaim leader at any time.");
											//Step 7: Demote the founder.
											igTargetGang.demote();
											igTargetGang.save();
										} else {
											player.sendMessage(GlobalTags.GANG + "§4You may not demote the founder of the gang.");
										}
									} else {
										//Step 7: Demote them and let them know they've been demoted.
										igTargetGang.demote();
										igTargetGang.save();
										Player target = Bukkit.getPlayer(name);
										if (target != null) 
											target.sendMessage(GlobalTags.GANG + "§cYou have been demoted by " + player.getName() + " to the rank of: §f" + igTargetGang.getGangRank().getLabel());
										player.sendMessage(GlobalTags.GANG + "§aYou have demoted " + name + " to: §f" + igTargetGang.getGangRank().getLabel());
									}
									
								}
							} else {
								player.sendMessage(GlobalTags.GANG + "§4The player you entered cannot be demoted further.");
							}
						} else {
							player.sendMessage(GlobalTags.GANG + "§4The player you entered is not in your gang.");
						}
						
					} else {
						player.sendMessage(GlobalTags.GANG + "§4The player you entered does not belong to a gang");
					}
				} else {
					player.sendMessage(GlobalTags.GANG + "§4The player (" + name + ") was not found.");
				}
			} else {
				player.sendMessage(GlobalTags.GANG + "§4You must be a leader of the gang to run this command.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You do not belong to a gang.");
		}
	}
	
	private void acceptGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		//Step 1: Determine if the player is not in a gang.
		if (!isPlayerInGang) {
			
			//Step 2: Determine if the gang exists.
			IGGang igGang = IGGangFactory.getGangByName(name);
			
			if (igGang.isValid()) {
				
				//Step 3: Determine if they have a request from said gang.
				IGPlayerGangRequest req = IGPlayerGangRequestFactory.getUnansweredRequestByIGPlayerAndIGGang(igPlayer, igGang);
				
				if (req.isValid()) {
					
					//Step 4: Accept the request and add them.
					req.accept();
					req.save();
					
					IGPlayerGangFactory.add(igPlayer, igGang);
					
					//Step 5: Mark the rest of the requests as DECLINED.
					List<IGPlayerGangRequest> requests = IGPlayerGangRequestFactory.getRequestByIGPlayer(igPlayer);
					
					for (IGPlayerGangRequest request : requests) {
						if (req.getId() != request.getId()) {
							//Only decline unanswered requests.
							if (request.getAnswer() == IGPlayerGangRequestAnswer.UNANSWERED) {
								request.decline();
								request.save();
							}
						}
					}
					
					player.sendMessage(GlobalTags.GANG + "§aYou have joined " + igGang.getName());
					
				} else {
					player.sendMessage(GlobalTags.GANG + "§4You do not have any pending requests from " + name);
				}
			} else {
				player.sendMessage(GlobalTags.GANG + "§4The gang (" + name + ") does not exist.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You already belong to a gang.");
		}
		
	}
	
	private void declineGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		
		if (!isPlayerInGang) {
			
			IGGang igGang = IGGangFactory.getGangByName(name);
			
			//Step 2: Determine if the gang exists.
			if (igGang.isValid()) {
				
				//Step 3: Determine if they have a request from the gang.
				IGPlayerGangRequest request = IGPlayerGangRequestFactory.getUnansweredRequestByIGPlayerAndIGGang(igPlayer, igGang);
				
				if (request.isValid()) {
					
					//Step 4: Decline the request.
					request.decline();
					request.save();
					
					//[#64] - Give the gang they got the request from their money back
					double refund = GangCalculator.costforNewMember(igGang);
					igGang.addMoney(refund);
					igGang.save();
					
					player.sendMessage(GlobalTags.GANG + "§cYou have declined the request from: §f" + name);
				} else {
					player.sendMessage(GlobalTags.GANG + "§4You do not have a request from that gang.");
				}
			} else {
				player.sendMessage(GlobalTags.GANG + "§4The gang you entered is not valid.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You already belong to a gang.");
		}
	}
	
	//When they run [/gang info] UNFINISHED
	private void showGangInformation(Player player, IGPlayer igPlayer, String name) {
		IGGang igGang = IGGangFactory.getGangByName(name);
		List<IGPlayerGang> playersInGang = IGPlayerGangFactory.getPlayersInGang(igGang.getId());
		if (igGang.isValid()) {
			ChatConverter.clearPlayerChat(player);
			
			player.sendMessage("=== { §9§l" + igGang.getName().toUpperCase() + "§f } ===");
			player.sendMessage("  §2§l" + CurrencyConverter.convertToCurrency(igGang.getMoney()));
			player.sendMessage(GlobalTags.DEFIANCE + "§5Points§f: §8§l" + igGang.getPoints());
			player.sendMessage("-----------------------");
			
			//Go through each gang and find out their name and rank... Sort by ranks (Leader > Officer > Member) in query.
			for (IGPlayerGang playerInGang : playersInGang) {
				IGPlayer igPlayerInGang = IGPlayerFactory.getIGPlayerById(playerInGang.getPlayerId());
				player.sendMessage(playerInGang.getGangRank().getLabel() + " - " + igPlayerInGang.getName());
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4The gang (" + name + ") was not found.");
		}
	}

	private void showRequests(Player player, IGPlayer igPlayer, boolean isPlayerInGang) {
		
		//Step 1: Determine if the player is in the gang.
		if (isPlayerInGang) {
			
			//Step 2: Determine if the player is an officer or above.
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			
			if (igPlayerGang.getGangRank() == IGGangRank.OFFICER || igPlayerGang.getGangRank() == IGGangRank.LEADER) {
				
				//Step 3: Grab all requests...
				List<IGPlayerGangRequest> requests = IGPlayerGangRequestFactory.getRequestsByGang(igPlayerGang.getGangId());
				
				ChatConverter.clearPlayerChat(player);
				
				player.sendMessage("=== §6§lRequest History§r ===");
				for (IGPlayerGangRequest request : requests) {
					IGPlayer sentTo = IGPlayerFactory.getIGPlayerById(request.getPlayerId());
					player.sendMessage(sentTo.getName() + " -> " + request.getAnswer().getLabel().toUpperCase());
				}
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You must belong to a gang.");
		}
	}

	private void showAllGangs(Player player) {
		ChatConverter.clearPlayerChat(player);		
		List<IGGang> gangs = IGGangFactory.getAllGangs();
		
		int ranking = 1;
		for (IGGang gang : gangs) {
			player.sendMessage("#" + ranking + ": §8" + gang.getName() + " §f[§2§l" + CurrencyConverter.convertToCurrency(gang.getMoney()) + "§r§f]");
			ranking++;
		}
	}
	
	private void showAllRanks(Player player) {
		ChatConverter.clearPlayerChat(player);
		
		player.sendMessage("----- Gang Ranks ------");
		player.sendMessage(IGGangRank.MEMBER.getLabel() + " -> The lowest rank of the gang. Can add funds to the gang and earn bonuses through killing other gang members.");
		player.sendMessage(IGGangRank.OFFICER.getLabel() + " -> The middle rank of the gang. Can add/remove gang members.");
		player.sendMessage(IGGangRank.LEADER.getLabel() + " -> The highest rank of the gang. Can add/remove gang members, can promote/demote gang members.");
	}

	private void addFunds(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String amount) {
		
		//Step 1: Determine if the player is in the gang.
		if (isPlayerInGang) {
			
			//Step 2: Convert the amount into $$
			try {
				double money = Double.parseDouble(amount);
				
				//Check if user has money in their bank
				double balance =ServerDefaults.econ.getBalance(player);
				
				if (balance >= money) {
					//Step 4: Withdraw from the account, and add it to the gang.
					ServerDefaults.econ.withdrawPlayer(player, money);
					IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
					IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
					igGang.addMoney(money);
					igGang.save();
					
					player.sendMessage(GlobalTags.GANG + "§aAdded §2§l" + CurrencyConverter.convertToCurrency(money) + "§r§a to your gang.");
					
				} else {
					player.sendMessage(GlobalTags.GANG + "§4You do not have that much money.");
				}
			} catch (Exception ex) {
				player.sendMessage(GlobalTags.GANG + "§4Unable to add funds. Please enter in a valid amount.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You must be in a gang.");
		}
	}

	private void toggleChat(Player player) {
		if (!IGList.gangChat.contains(player)) {
			IGList.gangChat.add(player);
			player.sendMessage(GlobalTags.GANG + "§fToggled gang chat: §2§lON");
		} else {
			IGList.gangChat.remove(player);
			player.sendMessage(GlobalTags.GANG + "§fToggled gang chat: §4§lOFF");
		}
	}

	private void buyDrug(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String drugLabel) {
		if (!IGDrugType.isDrugType(drugLabel)) {
			String drugTypes = "";
			for (IGDrugType drug : IGDrugType.values()) {
				drugTypes += " " + drug.getLabel().toLowerCase();
			}
			player.sendMessage(GlobalTags.DRUGS + "§4Drug types§f:" + drugTypes);
			return;
		}
		
		IGDrugType drugType = IGDrugType.getDrugByLabel(drugLabel);
		
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
			double playerBalance = ServerDefaults.econ.getBalance(player);
			
			//Step 2: Determine if the gang member can afford the drugs.
			double costOfDrugs = Double.parseDouble(ServerDefaults.getSetting(IGSettings.DEFAULT_DRUG_COST).getValue().toString());
			//1) Buy drugs through gang if allowed.
			if (igGang.canMembersBuyDrugs()){
				//Step 3: Determine if the gang can actually afford the drugs.
				if (igGang.getMoney() >= costOfDrugs) {
					//Buy the drugs and send them to the player.
					igGang.setMoney(igGang.getMoney() - costOfDrugs);
					igGang.save();
					
					player.getInventory().addItem(drugType.toDrug());
				} 
				//Check if the player can afford the drugs outright.
				else if (playerBalance >= costOfDrugs) {
					
					ServerDefaults.econ.withdrawPlayer(player, costOfDrugs);
					
					player.getInventory().addItem(drugType.toDrug());
				}
				else {
					//Alert the player that neither the gang or individual could buy the drugs
					if (igGang.canMembersBuyDrugs()) 
						player.sendMessage(GlobalTags.DRUGS + "§4You or your gang could not afford to buy drugs.");
					else
						player.sendMessage(GlobalTags.DRUGS + "§4You do not have enough money to buy drugs.");
				}
			} 
			//2) Buy drugs if player has enough.
			else if (playerBalance >= costOfDrugs) {
				ServerDefaults.econ.withdrawPlayer(player, costOfDrugs);
				player.getInventory().addItem(drugType.toDrug());
				
			} 
			//3) All failed, so let the player know they can't afford the drugs.
			else {
				if (igGang.canMembersBuyDrugs()) 
					player.sendMessage(GlobalTags.DRUGS + "§4You or your gang could not afford to buy drugs.");
				else
					player.sendMessage(GlobalTags.DRUGS + "§4You do not have enough money to buy drugs.");
			}
		}
	}

	private void toggleCanMembersBuyDrugs(Player player, IGPlayer igPlayer, boolean isPlayerInGang) {
		
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			
			//Step 2: Determine if the player is the right rank (Leader only).
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			if (igPlayerGang.getGangRank() == IGGangRank.LEADER) {
				//Step 3: Toggle the current setting.
				IGGang igGang = IGGangFactory.getGangById(igPlayerGang.getGangId());
				igGang.toggleAllowedMembersToBuyDrugs();
				igGang.save();
				if (igGang.canMembersBuyDrugs())
					player.sendMessage(GlobalTags.GANG + "§aMembers may buy drugs with the gang's money.");
				else
					player.sendMessage(GlobalTags.GANG + "§cMembers may not buy drugs with the gang's money.");
			} else {
				player.sendMessage(GlobalTags.GANG + "§4You must be a leader to run this command.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You must be in a gang.");
		}
	}

	private void removePlayerFromGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang, String name) {
		
		//Step 1: Determine if the player is in a gang.
		if (isPlayerInGang) {
			
			//Step 2: Determine if player has permission to remove (Officer and Leader)
			IGPlayerGang igPlayerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			
			if (igPlayerGang.getGangRank() == IGGangRank.LEADER || igPlayerGang.getGangRank() == IGGangRank.OFFICER) {
				
				//Step 3: Determine if the name is a valid IGPlayer
				IGPlayer igTarget = IGPlayerFactory.getIGPlayerFromName(name);
				
				if (igTarget.isValid()) {
					
					//Step 4: Remove the player from the gang.
					IGPlayerGang igTargetGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igTarget);
					igTargetGang.delete();
					
					player.sendMessage(GlobalTags.GANG + "§cYou have removed §l" + name + " §r§cfrom the gang.");
					Player target = Bukkit.getPlayer(name);
					if (target != null) target.sendMessage(GlobalTags.GANG + "§cYou were removed from the gang by §l" + player.getName() + "§r§c.");
					
					
				} else {
					player.sendMessage(GlobalTags.GANG + "§4The player you entered is not valid.");
				}
			} else {
				player.sendMessage(GlobalTags.GANG + "§4You are not an officer or leader of the gang.");
			}
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You don't belong to a gang.");
		}
	}
	
	private void leaveGang(Player player, IGPlayer igPlayer, boolean isPlayerInGang) {
		
		//Step 1: Determine if player is in the gang.
		if (isPlayerInGang) {
			IGPlayerGang playerGang = IGPlayerGangFactory.getPlayerGangFromPlayer(igPlayer);
			IGGang igGang = IGGangFactory.getGangById(playerGang.getGangId());
			
			//Step 2: Determine if player is a leader.
			if (playerGang.getGangRank() == IGGangRank.LEADER) {
				//Step 3: Check if the player is the only leader.
				List<IGPlayerGang> leaders = IGPlayerGangFactory.getLeadersInGang(igGang);
				
				if (leaders.size() > 1) {
					//Step 4: Determine if the player is the founder.
					if (igGang.getFounderId() != igPlayer.getId()) {
						
						//Step 5: Remove the player from the gang.
						playerGang.delete();
						
						//Step 6: Let the player know.
						player.sendMessage(GlobalTags.GANG + "§cYou have left the gang.");
					} else {
						player.sendMessage(GlobalTags.GANG + "§4The founder of a gang cannot leave their gang. You may only leave through disbanding the gang.");
					}
				} else {
					player.sendMessage(GlobalTags.GANG + "§4You cannot leave as the only leader.");
				}
			} else {
				if (igGang.getFounderId() != igPlayer.getId()) {
					
					//Step 5: Remove the player from the gang.
					playerGang.delete();
					
					//Step 6: Let the player know.
					player.sendMessage(GlobalTags.GANG + "§cYou have left the gang.");
				} else {
					player.sendMessage(GlobalTags.GANG + "§4The founder of a gang cannot leave their gang. You may only leave through disbanding the gang.");
				}
			}
			
		} else {
			player.sendMessage(GlobalTags.GANG + "§4You don't belong to a gang.");
		}
	}
}
