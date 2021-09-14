/*    */ package com.mattbcool.data;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Particle;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class ParticleHandler
/*    */ {
/*    */   public static void playParticle(Particle particle, Location loc, double speed, int amount) {
/* 12 */     for (Player p : Bukkit.getOnlinePlayers())
/*    */     {
/*    */       
/* 15 */       p.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), 0, 0.0D, 0.0D, speed, amount);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\data\ParticleHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */