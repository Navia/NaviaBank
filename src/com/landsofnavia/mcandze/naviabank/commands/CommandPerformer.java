package com.landsofnavia.mcandze.naviabank.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.navia.java.plugins.mcandze.CharHandler;

import com.landsofnavia.mcandze.naviabank.Bank;
import com.landsofnavia.mcandze.naviabank.BankHandler;
import com.landsofnavia.mcandze.naviabank.NaviaBank;
import com.landsofnavia.mcandze.naviachat.Channel;
import com.landsofnavia.mcandze.naviachat.ChannelManager;
import com.landsofnavia.mcandze.naviachat.command.CommandHandler;

public class CommandPerformer {
	public static boolean performPlayerCommand(Command command, Player player, String[] args){
		BankCommand cmd;
		
		try {
			cmd = BankCommand.valueOf(command.getName().toUpperCase());
		} catch (Exception e){
			player.sendMessage(ChatColor.RED + "Oh noes!11 Something went wrong.");
			return true;
		}
		switch (cmd){
		case BANK: return performBankCommand(player, args);
		case RETURN: return performReturnCommand(player, args);
		}
		return false;
	}
	
	public static boolean performBankCommand(Player player, String[] args){
		
		// Bank admin
		if (args.length == 2){
			if (!NaviaBank.permissions.has(player, "naviabank.bank.admin")){
				player.sendMessage(ChatColor.RED + "Either you're not a bank admin, or");
				player.sendMessage(ChatColor.RED + "You used the command in a wrong way.");
				return true;
			}
			
			if (args[0].equalsIgnoreCase("new")){
				if (BankHandler.playerHasABank(NaviaBank.server.getPlayer(args[1]))){
					player.sendMessage(ChatColor.RED + "Player already has a bank.");
					return true;
				}
				Bank bank = new Bank(player.getLocation(), NaviaBank.server.getPlayer(args[1]), new Location(player.getWorld(), 0, 0, 0, 0, 0), false);
				BankHandler.addBank(bank);
				player.sendMessage("Created a bank.");
				player.sendMessage("Owner: " + bank.getPlayer().getName());
				return true;
			} else if (args[0].equalsIgnoreCase("delete")){
				Player p = NaviaBank.server.getPlayer(args[1]);
				if (BankHandler.playerHasABank(p)){
					BankHandler.removeBank(BankHandler.banks.get(p));
					player.sendMessage("Bank deleted!");
				} else {
					player.sendMessage("Player does not have a bank.");
				}
				return true;
			}
			return true;
		}
		
		// Bank
		if (args.length == 0){
			if (!NaviaBank.permissions.has(player, "naviabank.bank")){
				return true;
			}
			if (BankHandler.playerHasABank(player)){
				if (!BankHandler.banks.get(player).isPlayerInBank()){
					
					if (NaviaBank.usingNaviaChat){
						Channel c = ChannelManager.getFocusedChannel(player);
						if (c.isIc()){
							if (NaviaBank.usingNaviaChar){
								if (CharHandler.playerHasACharacter(player)){
									String action[] =  ("is rummaging through " + CharHandler.getCharacterByPlayerName(player.getName()).getGender().getThirdPerson() + " backpack.").split(" ");
									CommandHandler.sendMe(player, action);
								}
							}
						}
					}
					BankHandler.banks.get(player).teleportToBank();
					return true;
				} else {
					player.sendMessage(ChatColor.RED + "You are already in your bank!");
					return true;
				}
			} else {
				player.sendMessage(ChatColor.RED + "You do not have bank.");
				return true;
			}
		} else {
			return false;
		}
	}
	
	public static boolean performReturnCommand(Player player, String args[]){
		if (!BankHandler.playerHasABank(player)){
			player.sendMessage(ChatColor.RED + "You do not have a bank.");
			return true;
		}
		if (!BankHandler.banks.get(player).isPlayerInBank()){
			player.sendMessage(ChatColor.RED + "You are not in your bank.");
			return true;
		}
		BankHandler.banks.get(player).teleportBack();
		return true;
	}
}
