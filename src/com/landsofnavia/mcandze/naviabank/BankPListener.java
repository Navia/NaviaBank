package com.landsofnavia.mcandze.naviabank;

import org.bukkit.ChatColor;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class BankPListener extends PlayerListener{
	private NaviaBank plugin;
	
	public BankPListener(NaviaBank instance){
		this.plugin = instance;
	}
	
	public void onPlayerTeleport(PlayerMoveEvent event){
		if (event.isCancelled()){
			return;
		}
		if (BankHandler.playerHasABank(event.getPlayer())){
			if (BankHandler.banks.get(event.getPlayer()).isPlayerInBank()){
				BankHandler.banks.get(event.getPlayer()).teleportBack();
				event.getPlayer().sendMessage(ChatColor.RED + "You can't do that. Teleporting you back.");
				event.setCancelled(true);
				return;
			}
		}
	}

	public void onPlayerJoin(PlayerEvent event){
		NaviaBankDataSource.loadBank(event.getPlayer());
	}
}
