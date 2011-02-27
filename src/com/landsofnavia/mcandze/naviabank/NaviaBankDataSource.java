package com.landsofnavia.mcandze.naviabank;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class NaviaBankDataSource {
	public final static String banksDirectory = "plugins" + File.separator + "NaviaBank" + File.separator + "Data";
	
	public static void addBank(final Bank bank){
		File file = new File(banksDirectory);
		if (!file.exists()){
			file.mkdirs();
			return;
		}
		
		
		File txtFile = new File(banksDirectory, "banks.txt");
		if (!txtFile.exists())
			try{
				txtFile.createNewFile();
			} catch (IOException e){
				NaviaBank.log.warning("[NaviaBank] Could not create file: " + txtFile.getName());
			}
		
		String toWrite = bank.getPlayer().getName() 
		+ ":" + bank.getLocation().getWorld().getName()
		+ ":" + String.valueOf(bank.getLocation().getX())
		+ ":" + String.valueOf(bank.getLocation().getY())
		+ ":" + String.valueOf(bank.getLocation().getZ())
		+ ":" + String.valueOf(bank.getLocation().getYaw())
		+ ":" + String.valueOf(bank.getLocation().getPitch()) 
		+ ":" + bank.getBackLocation().getWorld().getName()
		+ ":" + String.valueOf(bank.getBackLocation().getX())
		+ ":" + String.valueOf(bank.getBackLocation().getY()) 
		+ ":" + String.valueOf(bank.getBackLocation().getZ())
		+ ":" + String.valueOf(bank.getBackLocation().getYaw())
		+ ":" + String.valueOf(bank.getBackLocation().getPitch());
		
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile, true));
					
					bw.write(toWrite);
					bw.newLine();
					bw.close();
					
				} catch (Exception e){
					NaviaBank.log.warning("[NaviaBank] Error while adding bank to file.");
					e.printStackTrace();
				}
				
		
	}
	
	public static void deleteBank(final Bank bank){
		File file = new File(banksDirectory);
		if (!file.exists()){
			file.mkdirs();
			return;
		}
		
		File txtFile = new File(banksDirectory, "banks.txt");
		if (!txtFile.exists())
			try{
				txtFile.createNewFile();
			} catch (IOException e){
				NaviaBank.log.warning("[NaviaBank] Could not create file: " + txtFile.getName());
			}
		
				try {
					BufferedReader br = new BufferedReader(new FileReader(txtFile));
					String curLine;
					
					StringBuilder toKeep = new StringBuilder();
					
					while ((curLine = br.readLine()) != null){
						if (!curLine.split(":")[0].equalsIgnoreCase(bank.getPlayer().getName())){
							toKeep.append(curLine).append("\r\n");
						}
					}
					br.close();
					BufferedWriter bw = new BufferedWriter(new FileWriter(txtFile));
					bw.write(toKeep.toString());
					bw.close();
					
				} catch (Exception e){
					NaviaBank.log.warning("[NaviaBank] Error while deleting bank.");
					e.printStackTrace();
				}
	}
	
	
	public static void loadBank(Player player){
		File file = new File(banksDirectory);
		if (!file.exists()){
			file.mkdirs();
		}
		
		File txtFile = new File(banksDirectory, "banks.txt");
		if (!txtFile.exists())
			try{
				txtFile.createNewFile();
			} catch (IOException e){
				NaviaBank.log.warning("[NaviaBank] Could not create file: " + txtFile.getName());
			}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(txtFile));
			String curLine;
			
			while ((curLine = br.readLine()) != null){
				String[] sBank = curLine.split(":");
				if (sBank[0].equalsIgnoreCase(player.getName())){
					World world = NaviaBank.server.getWorld(sBank[1]);
					Location loc = new Location(world, 
							Double.parseDouble(sBank[2]), 
							Double.parseDouble(sBank[3]), 
							Double.parseDouble(sBank[4]), 
							Float.parseFloat(sBank[5]), 
							Float.parseFloat(sBank[6]));
					Location backLoc = new Location(
							NaviaBank.server.getWorld(sBank[7]),
							Double.parseDouble(sBank[8]),
							Double.parseDouble(sBank[9]),
							Double.parseDouble(sBank[10]), 
							Float.parseFloat(sBank[11]), 
							Float.parseFloat(sBank[12]));
					
					Bank bank;
					if ((sBank[8] + sBank[9] + sBank[10]).equals("0.00.00.0")){
						bank = new Bank(loc, player, backLoc, false);
						BankHandler.banks.put(player, bank);
					} else {
						bank = new Bank(loc, player, backLoc, true);
						BankHandler.banks.put(player, bank);
					}
				}
			}
		} catch (Exception e){
			NaviaBank.log.warning("[NaviaBank] Error while loading banks.");
			e.printStackTrace();
		}
	}
	
	public static void updateBank(Bank bank){
		deleteBank(bank);
		addBank(bank);
	}
	
}
