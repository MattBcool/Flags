package com.mattbcool.data;

import com.mattbcool.commands.FlagCommand;
import com.mattbcool.data.flags.MovingFlag;
import com.mattbcool.listeners.InventoryListener;
import com.mattbcool.listeners.PlayerListener;
import com.mattbcool.main.Main;
import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class DataHandler
{
  public static Main plugin;
  public ArrayList<PlayerData> playerData = new ArrayList<>();
  public ArrayList<MovingFlag> flagData = new ArrayList<>();

  
  public DataHandler(Main plugin) {
    DataHandler.plugin = plugin;
    
    initPlayerData();
    
    Bukkit.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(plugin), (Plugin)plugin);
    Bukkit.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(plugin), (Plugin)plugin);
    
    plugin.getCommand("flag").setExecutor((CommandExecutor)new FlagCommand(plugin));
  }

  
  private void initPlayerData() {
    for (Player p : Bukkit.getOnlinePlayers())
    {
      this.playerData.add(new PlayerData(p.getUniqueId()));
    }
  }

  
  public PlayerData getPlayerData(UUID uuid) {
    for (PlayerData pd : this.playerData) {
      
      if (pd.getUUID().compareTo(uuid) == 0)
      {
        return pd;
      }
    } 
    return null;
  }

  
  public MovingFlag getFlagData(UUID uuid) {
    for (MovingFlag flag : this.flagData) {
      
      if (flag.getUUID().compareTo(uuid) == 0)
      {
        return flag;
      }
    } 
    return null;
  }

  
  public MovingFlag getClosestFlag(Location loc) {
    double flagDetectionLimit = 100.0D;
    
    MovingFlag closestFlag = null;
    double closestDistance = -1.0D;
    
    for (MovingFlag flag : this.flagData) {
      
      double distance = flag.getBase().distance(loc);
      if (distance < flagDetectionLimit && (closestDistance == -1.0D || distance < closestDistance)) {
        
        closestFlag = flag;
        closestDistance = distance;
      } 
    } 
    return closestFlag;
  }
}
