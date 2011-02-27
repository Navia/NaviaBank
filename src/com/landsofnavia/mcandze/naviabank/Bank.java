package com.landsofnavia.mcandze.naviabank;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import com.sun.org.apache.xml.internal.serializer.Method;

public class Bank {
	private Location location;
	private Location backLocation;
	private Player player;
	private boolean playerInBank;
	private World world;
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	public Bank(Location location, Player player, Location backLocation, boolean inBank){
		this.location = location;
		this.player = player;
		this.backLocation = backLocation;
		this.playerInBank = inBank;
	}
	
	/**
	 * @return the backLocation
	 */
	public Location getBackLocation() {
		return backLocation;
	}

	/**
	 * @param backLocation the backLocation to set
	 */
	public void setBackLocation(Location backLocation) {
		this.backLocation = backLocation;
	}

	/**
	 * @return the playerInBank
	 */
	public boolean isPlayerInBank() {
		return playerInBank;
	}

	/**
	 * @param playerInBank the playerInBank to set
	 */
	public void setPlayerInBank(boolean playerInBank) {
		this.playerInBank = playerInBank;
		if (!playerInBank){
			this.backLocation.setX(0.0); this.backLocation.setY(0.0); this.backLocation.setZ(0.0); 
		}
	}

	/**
	 * @return the location
	 */
	public Location getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(Location location) {
		this.location = location;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

	
	public synchronized void teleportToBank(){
		this.backLocation = player.getLocation();
		if (player.isOnline()){
			player.teleportTo(location);
			playerInBank = true;
		}
		NaviaBankDataSource.updateBank(this);
	}
	
	public synchronized void teleportBack(){
		if (player.isOnline()){
			playerInBank = false;
			player.teleportTo(backLocation);
			this.backLocation.setX(0.0);
			this.backLocation.setY(0.0);
			this.backLocation.setZ(0.0);
		}
		NaviaBankDataSource.updateBank(this);
		
	}
}
