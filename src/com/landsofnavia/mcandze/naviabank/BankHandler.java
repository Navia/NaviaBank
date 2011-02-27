package com.landsofnavia.mcandze.naviabank;

import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.entity.Player;

public class BankHandler {
	public static HashMap<Player, Bank> banks;
	
	public static void initialize(){
		banks = new HashMap<Player, Bank>();
	}
	
	public static boolean playerHasABank(Player player){
		return banks.containsKey(player);
	}
	
	public static void addBank(Bank bank){
		banks.put(bank.getPlayer(), bank);
		NaviaBankDataSource.addBank(bank);
	}
	
	public static void removeBank(Bank bank){
		banks.remove(bank.getPlayer());
		NaviaBankDataSource.deleteBank(bank);
	}
}
