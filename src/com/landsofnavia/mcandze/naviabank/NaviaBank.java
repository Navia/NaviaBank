package com.landsofnavia.mcandze.naviabank;

import java.util.logging.Logger;

import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import com.landsofnavia.mcandze.naviabank.commands.CommandPerformer;
import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

public class NaviaBank extends JavaPlugin{
	
	public static PermissionHandler permissions;
	private BankPListener playerListener;
	public static boolean usingNaviaChat;
	public static boolean usingNaviaChar;
	public static Logger log = Logger.getLogger("Minecraft");
	public static Server server;
	/**
	 * @param pluginLoader
	 * @param instance
	 * @param desc
	 * @param folder
	 * @param plugin
	 * @param cLoader
	 */
	public NaviaBank(){
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args){
		Player player;
		if (!sender.isPlayer()){
			return true;
		} else {
			player = (Player)sender;
			return CommandPerformer.performPlayerCommand(command, player, args);
		} 
	}

	@Override
	public void onDisable() {
		for (Bank b: BankHandler.banks.values()){
			NaviaBankDataSource.updateBank(b);
		}
		log.info("[NaviaBank] Saved banks!");
		
	}

	@Override
	public void onEnable() {
		
		server = getServer();
		
		loadPermissions();
		BankHandler.initialize();
		playerListener = new BankPListener(this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_TELEPORT, playerListener, Event.Priority.Highest, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLAYER_JOIN, playerListener, Event.Priority.Normal, this);
		getServer().getPluginManager().registerEvent(Event.Type.PLUGIN_ENABLE, new NBServerListener(), Priority.Monitor, this);
		loadNaviaChat();
		loadNaviaChar();
		
		for (Player p: getServer().getOnlinePlayers()){
			NaviaBankDataSource.loadBank(p);
		}
		
		PluginDescriptionFile pdfFile = this.getDescription();
		(Logger.getLogger("Minecraft")).info("[NaviaBank] " + pdfFile.getName() + " " + pdfFile.getVersion() + " by Mcandze, is enabled.");
		
	}
	
	
	public void loadPermissions(){
		Plugin test = getServer().getPluginManager().getPlugin("Permissions");
		Logger log = Logger.getLogger("Minecraft");
		
		if (this.permissions == null){
			if (test != null){
				this.permissions = ((Permissions)test).getHandler();
			} else {
				log.info("[NaviaBank] Permissions not installed, disabling.");
				getServer().getPluginManager().disablePlugin(this);
			}
		}
	}
	
	public void loadNaviaChat(){
		Plugin test = getServer().getPluginManager().getPlugin("NaviaChat");
		if (test != null){
			usingNaviaChat = true;
			return;
		}
		usingNaviaChat = false;
	}
	
	public void loadNaviaChar(){
		Plugin test = getServer().getPluginManager().getPlugin("NaviaChar");
		if (test != null){
			usingNaviaChar = true;
			return;
		}
		usingNaviaChar = false;
	}

}
