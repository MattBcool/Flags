/*    */ package com.mattbcool.main;
/*    */ 
/*    */ import com.mattbcool.data.DataHandler;
/*    */ import com.mattbcool.data.ServerTick;
/*    */ import com.mattbcool.data.flags.MovingFlag;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class Main
/*    */   extends JavaPlugin
/*    */ {
/*    */   public DataHandler dh;
/*    */   private ServerTick st;
/*    */   
/*    */   public void onEnable() {
/* 17 */     this.dh = new DataHandler(this);
/* 18 */     this.st = new ServerTick(this);
/*    */     
/* 20 */     serverTick();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 25 */     for (MovingFlag flag : this.dh.flagData)
/*    */     {
/* 27 */       flag.clearFlag();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   private void serverTick() {
/* 33 */     BukkitScheduler scheduler = getServer().getScheduler();
/* 34 */     scheduler.scheduleSyncDelayedTask((Plugin)this, new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 38 */             Main.this.st.doTicks();
/* 39 */             Main.this.serverTick();
/*    */           }
/* 41 */         },  1L);
/*    */   }
/*    */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\main\Main.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */