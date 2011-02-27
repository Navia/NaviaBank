package com.landsofnavia.mcandze.naviabank;

import org.bukkit.event.server.PluginEvent;
import org.bukkit.event.server.ServerListener;

import com.nijikokun.bukkit.Permissions.Permissions;

public class NBServerListener extends ServerListener{
	public NBServerListener() {
    }

   @Override
    public void onPluginEnabled(PluginEvent event) {
        if(event.getPlugin().getDescription().getName().equals("Permissions")) {
            NaviaBank.permissions = ((Permissions)event.getPlugin()).getHandler();
        }
    }
}
