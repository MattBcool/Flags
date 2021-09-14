/*    */ package com.mattbcool.data;
/*    */ 
/*    */ import com.mattbcool.commands.FlagCommand;
/*    */ import com.mattbcool.data.flags.MovingFlag;
/*    */ import com.mattbcool.listeners.InventoryListener;
/*    */ import com.mattbcool.listeners.PlayerListener;
/*    */ import com.mattbcool.main.Main;
/*    */ import java.util.ArrayList;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class DataHandler
/*    */ {
/*    */   public static Main plugin;
/* 20 */   public ArrayList<PlayerData> playerData = new ArrayList<>();
/* 21 */   public ArrayList<MovingFlag> flagData = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public DataHandler(Main plugin) {
/* 25 */     DataHandler.plugin = plugin;
/*    */     
/* 27 */     initPlayerData();
/*    */     
/* 29 */     Bukkit.getServer().getPluginManager().registerEvents((Listener)new PlayerListener(plugin), (Plugin)plugin);
/* 30 */     Bukkit.getServer().getPluginManager().registerEvents((Listener)new InventoryListener(plugin), (Plugin)plugin);
/*    */     
/* 32 */     plugin.getCommand("flag").setExecutor((CommandExecutor)new FlagCommand(plugin));
/*    */   }
/*    */ 
/*    */   
/*    */   private void initPlayerData() {
/* 37 */     for (Player p : Bukkit.getOnlinePlayers())
/*    */     {
/* 39 */       this.playerData.add(new PlayerData(p.getUniqueId()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public PlayerData getPlayerData(UUID uuid) {
/* 45 */     for (PlayerData pd : this.playerData) {
/*    */       
/* 47 */       if (pd.getUUID().compareTo(uuid) == 0)
/*    */       {
/* 49 */         return pd;
/*    */       }
/*    */     } 
/* 52 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingFlag getFlagData(UUID uuid) {
/* 57 */     for (MovingFlag flag : this.flagData) {
/*    */       
/* 59 */       if (flag.getUUID().compareTo(uuid) == 0)
/*    */       {
/* 61 */         return flag;
/*    */       }
/*    */     } 
/* 64 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public MovingFlag getClosestFlag(Location loc) {
/* 69 */     double flagDetectionLimit = 100.0D;
/*    */     
/* 71 */     MovingFlag closestFlag = null;
/* 72 */     double closestDistance = -1.0D;
/*    */     
/* 74 */     for (MovingFlag flag : this.flagData) {
/*    */       
/* 76 */       double distance = flag.getBase().distance(loc);
/* 77 */       if (distance < flagDetectionLimit && (closestDistance == -1.0D || distance < closestDistance)) {
/*    */         
/* 79 */         closestFlag = flag;
/* 80 */         closestDistance = distance;
/*    */       } 
/*    */     } 
/* 83 */     return closestFlag;
/*    */   }
/*    */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\data\DataHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */