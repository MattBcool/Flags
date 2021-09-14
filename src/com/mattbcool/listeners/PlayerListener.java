package com.mattbcool.listeners;

import com.mattbcool.data.PlayerData;
import com.mattbcool.data.flags.EditStatus;
import com.mattbcool.data.flags.MovingFlag;
import com.mattbcool.main.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;


public class PlayerListener
  implements Listener
{
  private Main plugin;
  
  public PlayerListener(Main plugin) {
    this.plugin = plugin;
  }

  
  @EventHandler
  public void onPlayerInteractEvent(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    Action a = e.getAction();
    
    if (a == Action.LEFT_CLICK_AIR || a == Action.LEFT_CLICK_BLOCK) {
      
      PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());
      
      if (pd != null && pd.getEditFlag() != null) {
        MovingFlag flag;
        switch (pd.getEditStatus()) {

          case LOWER:
            flag = this.plugin.dh.getFlagData(pd.getEditFlag());
            flag.setLower(flag.getBase());
            flag.setBase(flag.getTempBase().add(-0.5D, 0.0D, -0.5D));
            flag.setTempBase(null);
            p.sendMessage(ChatColor.GREEN + "You have successfully set the flag's lower position!");
            break;
          case POSITION:
            p.sendMessage(ChatColor.GREEN + "You have successfully set the flag's position!");
            break;
		case ANGLE:
			break;
		case NONE:
			break;
		case PATTERN:
			break;
		default:
			p.sendMessage(ChatColor.GREEN + "You have successfully set the flag's angle!");
			break;
        } 
        pd.setEditStatus(EditStatus.NONE);
        pd.setEditFlag(null);
        e.setCancelled(true);
      } 
    } 
  }

  
  @EventHandler
  public void onPlayerMoveEvent(PlayerMoveEvent e) {
    Player p = e.getPlayer();
    
    PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());
    
    if (pd != null && pd.getEditFlag() != null)
    {
      MovingFlag movingFlag = this.plugin.dh.getFlagData(pd.getEditFlag());
    }
  }
  
  @EventHandler
  public void onPlayerJoinEvent(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());
    
    if (pd == null)
    {
      this.plugin.dh.playerData.add(new PlayerData(p.getUniqueId()));
    }
  }
}