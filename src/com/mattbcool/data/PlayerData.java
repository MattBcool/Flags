/*    */ package com.mattbcool.data;
/*    */ 
/*    */ import com.mattbcool.data.flags.EditStatus;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerData
/*    */ {
/*    */   private UUID uuid;
/* 15 */   private EditStatus editStatus = EditStatus.NONE;
/* 16 */   private UUID editFlag = null;
/*    */   
/*    */   private boolean isMoving = false;
/* 19 */   private Location prevLoc = null;
/*    */ 
/*    */   
/*    */   public PlayerData(UUID uuid) {
/* 23 */     this.uuid = uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getUUID() {
/* 28 */     return this.uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   public EditStatus getEditStatus() {
/* 33 */     return this.editStatus;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEditStatus(EditStatus editStatus) {
/* 38 */     this.editStatus = editStatus;
/*    */   }
/*    */ 
/*    */   
/*    */   public UUID getEditFlag() {
/* 43 */     return this.editFlag;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setEditFlag(UUID uuid) {
/* 48 */     this.editFlag = uuid;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMoving() {
/* 53 */     return this.isMoving;
/*    */   }
/*    */ 
/*    */   
/*    */   public void updateIsMoving() {
/* 58 */     Player p = Bukkit.getPlayer(this.uuid);
/*    */     
/* 60 */     if (p.isOnline()) {
/*    */       
/* 62 */       Location loc = p.getLocation();
/* 63 */       if (this.prevLoc != null && this.prevLoc.distance(loc) > 0.02D) {
/*    */         
/* 65 */         this.isMoving = true;
/*    */       }
/*    */       else {
/*    */         
/* 69 */         this.isMoving = false;
/*    */       } 
/*    */       
/* 72 */       this.prevLoc = loc;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\data\PlayerData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */