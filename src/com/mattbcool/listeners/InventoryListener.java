/*    */ package com.mattbcool.listeners;
/*    */ 
/*    */ import com.mattbcool.data.PlayerData;
/*    */ import com.mattbcool.data.flags.EditStatus;
/*    */ import com.mattbcool.data.flags.MovingFlag;
/*    */ import com.mattbcool.main.Main;
/*    */ import java.util.ArrayList;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.InventoryView;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryListener
/*    */   implements Listener
/*    */ {
/*    */   private Main plugin;
/*    */   
/*    */   public InventoryListener(Main plugin) {
/* 26 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onInventoryCloseEvent(InventoryCloseEvent e) {
/* 32 */     InventoryView view = e.getView();
/*    */     
/* 34 */     if (view.getTitle().equalsIgnoreCase(ChatColor.GREEN + "Flag Pattern")) {
/*    */       
/* 36 */       Player p = (Player)e.getPlayer();
/* 37 */       Inventory inv = e.getInventory();
/*    */       
/* 39 */       PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());
/*    */       
/* 41 */       if (pd != null && pd.getEditFlag() != null) {
/*    */         
/* 43 */         MovingFlag flag = this.plugin.dh.getFlagData(pd.getEditFlag());
/*    */         
/* 45 */         ArrayList<Material> flagMat = new ArrayList<>();
/*    */         
/* 47 */         int height = inv.getSize() / 9;
/* 48 */         int startWidth = -1, endWidth = -1, startHeight = -1, endHeight = -1;
/*    */         
/* 50 */         for (int i = 0; i < inv.getSize(); i++) {
/*    */           
/* 52 */           ItemStack is = inv.getItem(i);
/* 53 */           if (is != null) {
/*    */             
/* 55 */             if (startWidth == -1 || i % 9 < startWidth) startWidth = i % 9; 
/* 56 */             if (endWidth == -1 || endWidth < i % 9) endWidth = i % 9;
/*    */             
/* 58 */             height = (int)Math.floor(i / 9.0D);
/* 59 */             if (startHeight == -1 || height < startHeight) startHeight = height; 
/* 60 */             if (endHeight == -1 || endHeight < height) endHeight = height;
/*    */             
/* 62 */             flagMat.add(is.getType());
/*    */           }
/*    */           else {
/*    */             
/* 66 */             flagMat.add(null);
/*    */           } 
/*    */         } 
/* 69 */         System.out.print("startWidth: " + startWidth + ", endWidth: " + endWidth + ", startHeight: " + startHeight + ", endHeight: " + endHeight);
/* 70 */         pd.setEditFlag(null);
/* 71 */         pd.setEditStatus(EditStatus.NONE);
/* 72 */         this.plugin.dh.flagData.remove(flag);
/* 73 */         flag.clearFlag();
/*    */         
/* 75 */         MovingFlag newFlag = new MovingFlag(flag.getBase(), flag.getAngle(), flagMat, startWidth, endWidth, startHeight, endHeight);
/* 76 */         newFlag.setLower(flag.getLower());
/* 77 */         this.plugin.dh.flagData.add(newFlag);
/*    */         
/* 79 */         p.sendMessage(ChatColor.GREEN + "You have successfully set the flag's pattern!");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\listeners\InventoryListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */