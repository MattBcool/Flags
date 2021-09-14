/*     */ package com.mattbcool.data.flags;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class MovingFlag
/*     */ {
/*     */   private UUID uuid;
/*     */   private Location base;
/*     */   private Location tempBase;
/*     */   private Location lower;
/*     */   private float angle;
/*  19 */   private float capRate = 100.0F;
/*  20 */   int startWidth = 0; int endWidth = 0; int startHeight = 0; int endHeight = 0;
/*     */ 
/*     */   
/*  23 */   private int t1 = 0;
/*  24 */   private int t2 = 0;
/*  25 */   private ArrayList<Material> pattern = new ArrayList<>();
/*  26 */   private ArrayList<Entity> stands = new ArrayList<>();
/*     */   
/*     */   private boolean initialized = false;
/*     */   
/*     */   private boolean loaded = false;
/*     */   private boolean animating = false;
/*     */   
/*     */   public MovingFlag(Location base, float angle, ArrayList<Material> pattern, int startWidth, int endWidth, int startHeight, int endHeight) {
/*  34 */     this.uuid = UUID.randomUUID();
/*     */     
/*  36 */     this.base = base;
/*  37 */     this.angle = angle;
/*     */     
/*  39 */     if (pattern == null) {
/*     */       
/*  41 */       ArrayList<Material> tempPattern = new ArrayList<>();
/*  42 */       for (int i = 0; i < 54; i++) {
/*     */         
/*  44 */         if (i < 6) {
/*     */           
/*  46 */           tempPattern.add(Material.RED_WOOL);
/*     */         }
/*     */         else {
/*     */           
/*  50 */           tempPattern.add(null);
/*     */         } 
/*     */       } 
/*     */       
/*  54 */       pattern = tempPattern;
/*     */       
/*  56 */       generateFlag(tempPattern, 0, 5, 0, 0);
/*     */     }
/*     */     else {
/*     */       
/*  60 */       generateFlag(pattern, startWidth, endWidth, startHeight, endHeight);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUUID() {
/*  66 */     return this.uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getBase() {
/*  71 */     return this.base;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBase(Location base) {
/*  76 */     this.base = base.add(0.5D, 0.0D, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getTempBase() {
/*  81 */     return this.tempBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTempBase(Location tempBase) {
/*  86 */     this.tempBase = tempBase;
/*     */   }
/*     */ 
/*     */   
/*     */   public Location getLower() {
/*  91 */     return this.lower;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLower(Location lower) {
/*  96 */     this.lower = lower;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAngle() {
/* 101 */     return this.angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAngle(float angle) {
/* 106 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCapRate() {
/* 111 */     return this.capRate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCapRate(float capRate) {
/* 116 */     this.capRate = capRate;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList<Material> getPattern() {
/* 121 */     return this.pattern;
/*     */   }
/*     */ 
/*     */   
/*     */   public MovingFlag clone() {
/* 126 */     MovingFlag flag = new MovingFlag(this.base, this.angle, this.pattern, this.startWidth, this.endWidth, this.startHeight, this.endHeight);
/*     */     
/* 128 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearFlag() {
/* 133 */     this.initialized = false;
/* 134 */     for (Entity e : this.stands) {
/*     */       
/* 136 */       if (e != null)
/*     */       {
/* 138 */         e.remove();
/*     */       }
/*     */     } 
/* 141 */     this.stands.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateFlag(ArrayList<Material> pattern, int startWidth, int endWidth, int startHeight, int endHeight) {
/* 146 */     this.startWidth = startWidth;
/* 147 */     this.endWidth = endWidth;
/* 148 */     this.startHeight = startHeight;
/* 149 */     this.endHeight = endHeight;
/*     */     
/* 151 */     this.pattern = pattern;
/*     */     
/* 153 */     for (int i = 0; i < pattern.size(); i++) {
/*     */       
/* 155 */       ArmorStand stand = null;
/* 156 */       Material mat = pattern.get(i);
/*     */       
/* 158 */       if (mat != null) {
/*     */         
/* 160 */         this.base.getChunk().load();
/* 161 */         stand = (ArmorStand)this.base.getWorld().spawnEntity(this.base, EntityType.ARMOR_STAND);
/* 162 */         stand.setGravity(false);
/* 163 */         stand.setVisible(false);
/* 164 */         stand.setMarker(true);
/*     */         
/* 166 */         stand.getEquipment().setHelmet(new ItemStack(mat, 1));
/*     */         
/* 168 */         stand.teleport(this.base);
/*     */       } 
/* 170 */       this.stands.add(stand);
/*     */     } 
/*     */     
/* 173 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean updateLoaded() {
/* 178 */     this.loaded = this.base.getChunk().isLoaded();
/* 179 */     return this.loaded;
/*     */   }
/*     */ 
/*     */   
/*     */   public void doIdleAnimation() {
/* 184 */     if (!this.initialized || !this.loaded) {
/*     */       
/* 186 */       System.out.print("Flag not loaded!");
/*     */       
/*     */       return;
/*     */     } 
/* 190 */     if (this.animating)
/*     */       return; 
/* 192 */     this.animating = true;
/*     */     
/* 194 */     if (this.t1 <= 50) {
/*     */       
/* 196 */       this.t1++;
/*     */     }
/*     */     else {
/*     */       
/* 200 */       this.t1 = 0;
/*     */     } 
/*     */     
/* 203 */     this.t2++;
/*     */     
/* 205 */     if (this.t2 > 3600)
/*     */     {
/* 207 */       this.t2 = 0;
/*     */     }
/*     */     
/* 210 */     Location currentLoc = this.base.clone();
/*     */     
/* 212 */     if (this.lower != null) {
/*     */       
/* 214 */       float capPer = this.capRate / 100.0F;
/* 215 */       double xDiff = this.base.getX() - this.lower.getX();
/* 216 */       double yDiff = this.base.getY() - this.lower.getY();
/* 217 */       double zDiff = this.base.getZ() - this.lower.getZ();
/* 218 */       double x = xDiff * capPer + this.lower.getX();
/* 219 */       double y = yDiff * capPer + this.lower.getY();
/* 220 */       double z = zDiff * capPer + this.lower.getZ();
/*     */       
/* 222 */       currentLoc.setX(x);
/* 223 */       currentLoc.setY(y);
/* 224 */       currentLoc.setZ(z);
/*     */     } 
/*     */     
/* 227 */     for (int width = this.startWidth; width <= this.endWidth; width++) {
/*     */       
/* 229 */       for (int height = this.startHeight; height <= this.endHeight; height++) {
/*     */         
/* 231 */         ArmorStand stand = (ArmorStand)this.stands.get(height * 9 + width);
/*     */         
/* 233 */         if (stand != null) {
/*     */           
/* 235 */           double val = warpValue(this.t1 + width, 100);
/* 236 */           double val2 = warpValue(this.t1 + width + 1, 100);
/* 237 */           double offset2 = Math.cos(val / 25.0D * Math.PI) * width / 10.0D;
/* 238 */           double offsetFirst = Math.cos(val2 / 25.0D * Math.PI) * (width + 1) / 10.0D;
/*     */           
/* 240 */           double offset1 = width * 0.62D - Math.abs(offsetFirst * 0.25D);
/*     */ 
/*     */           
/* 243 */           double x = lengthdir_x(offset1, this.angle) - lengthdir_y(offset2, this.angle);
/* 244 */           double z = lengthdir_y(offset1, this.angle) + lengthdir_x(offset2, this.angle);
/*     */           
/* 246 */           Location loc = currentLoc.clone().add(x, (this.endHeight - height) * 0.62D, z);
/* 247 */           loc.setYaw((float)(this.angle + offsetFirst * 15.0D));
/*     */           
/* 249 */           stand.teleport(loc);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 254 */     this.animating = false;
/*     */   }
/*     */ 
/*     */   
/*     */   private double lengthdir_x(double i, double angle) {
/* 259 */     return Math.cos(angle * 0.017453292519943295D) * i;
/*     */   }
/*     */ 
/*     */   
/*     */   private double lengthdir_y(double i, double angle) {
/* 264 */     return Math.sin(angle * 0.017453292519943295D) * i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int warpValue(int d, int m) {
/* 269 */     int val = d;
/* 270 */     if (d > m)
/*     */     {
/* 272 */       val = d - m;
/*     */     }
/* 274 */     return val;
/*     */   }
/*     */ }


/* Location:              D:\Personal Files\Minecraft Servers\Plugin Testing\plugins\Flags.jar!\com\mattbcool\data\flags\MovingFlag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */