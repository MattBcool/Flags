package com.mattbcool.listeners;

import com.mattbcool.data.PlayerData;
import com.mattbcool.data.flags.EditStatus;
import com.mattbcool.data.flags.MovingFlag;
import com.mattbcool.main.Main;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;



public class InventoryListener
  implements Listener
{
  private Main plugin;
  
  public InventoryListener(Main plugin) {
    this.plugin = plugin;
  }

  
  @EventHandler
  public void onInventoryCloseEvent(InventoryCloseEvent e) {
    InventoryView view = e.getView();
    
    if (view.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Flag Pattern")) {
      
      Player p = (Player)e.getPlayer();
      Inventory inv = e.getInventory();
      
      PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());
      
      if (pd != null && pd.getEditFlag() != null) {
        
        MovingFlag flag = this.plugin.dh.getFlagData(pd.getEditFlag());
        
        ArrayList<Material> flagMat = new ArrayList<>();
        
        int height = inv.getSize() / 9;
        int startWidth = -1, endWidth = -1, startHeight = -1, endHeight = -1;
        
        for (int i = 0; i < inv.getSize(); i++) {
          
          ItemStack is = inv.getItem(i);
          if (is != null) {
            
            if (startWidth == -1 || i % 9 < startWidth) startWidth = i % 9; 
            if (endWidth == -1 || endWidth < i % 9) endWidth = i % 9;
            
            height = (int)Math.floor(i / 9.0D);
            if (startHeight == -1 || height < startHeight) startHeight = height; 
            if (endHeight == -1 || endHeight < height) endHeight = height;
            
            flagMat.add(is.getType());
          }
          else {
            
            flagMat.add(null);
          } 
        } 
        System.out.print("startWidth: " + startWidth + ", endWidth: " + endWidth + ", startHeight: " + startHeight + ", endHeight: " + endHeight);
        pd.setEditFlag(null);
        pd.setEditStatus(EditStatus.NONE);
        this.plugin.dh.flagData.remove(flag);
        flag.clearFlag();
        
        MovingFlag newFlag = new MovingFlag(flag.getBase(), flag.getAngle(), flagMat, startWidth, endWidth, startHeight, endHeight);
        newFlag.setLower(flag.getLower());
        this.plugin.dh.flagData.add(newFlag);
        
        p.sendMessage(ChatColor.GREEN + "You have successfully set the flag's pattern!");
      } 
    } 
  }
}
