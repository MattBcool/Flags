package com.mattbcool.data.flags;

import java.util.ArrayList;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class MovingFlag
{
  private UUID uuid;
  private Location base;
  private Location tempBase;
  private Location lower;
  private float angle;
  private float capRate = 100.0F;
  int startWidth = 0; int endWidth = 0; int startHeight = 0; int endHeight = 0;

  
  private int t1 = 0;
  private int t2 = 0;
  private ArrayList<Material> pattern = new ArrayList<>();
  private ArrayList<Entity> stands = new ArrayList<>();
  
  private boolean initialized = false;
  
  private boolean loaded = false;
  private boolean animating = false;
  
  public MovingFlag(Location base, float angle, ArrayList<Material> pattern, int startWidth, int endWidth, int startHeight, int endHeight) {
    this.uuid = UUID.randomUUID();
    
    this.base = base;
    this.angle = angle;
    
    if (pattern == null) {
      
      ArrayList<Material> tempPattern = new ArrayList<>();
      for (int i = 0; i < 54; i++) {
        
        if (i < 6) {
          
          tempPattern.add(Material.RED_WOOL);
        }
        else {
          
          tempPattern.add(null);
        } 
      } 
      
      pattern = tempPattern;
      
      generateFlag(tempPattern, 0, 5, 0, 0);
    }
    else {
      
      generateFlag(pattern, startWidth, endWidth, startHeight, endHeight);
    } 
  }

  
  public UUID getUUID() {
    return this.uuid;
  }

  
  public Location getBase() {
    return this.base;
  }

  
  public void setBase(Location base) {
    this.base = base.add(0.5D, 0.0D, 0.5D);
  }

  
  public Location getTempBase() {
    return this.tempBase;
  }

  
  public void setTempBase(Location tempBase) {
    this.tempBase = tempBase;
  }

  
  public Location getLower() {
    return this.lower;
  }

  
  public void setLower(Location lower) {
    this.lower = lower;
  }

  
  public float getAngle() {
    return this.angle;
  }

  
  public void setAngle(float angle) {
    this.angle = angle;
  }

  
  public float getCapRate() {
    return this.capRate;
  }

  
  public void setCapRate(float capRate) {
    this.capRate = capRate;
  }

  
  public ArrayList<Material> getPattern() {
    return this.pattern;
  }

  
  public MovingFlag clone() {
    MovingFlag flag = new MovingFlag(this.base, this.angle, this.pattern, this.startWidth, this.endWidth, this.startHeight, this.endHeight);
    
    return flag;
  }

  
  public void clearFlag() {
    this.initialized = false;
    for (Entity e : this.stands) {
      
      if (e != null)
      {
        e.remove();
      }
    } 
    this.stands.clear();
  }

  
  public void generateFlag(ArrayList<Material> pattern, int startWidth, int endWidth, int startHeight, int endHeight) {
    this.startWidth = startWidth;
    this.endWidth = endWidth;
    this.startHeight = startHeight;
    this.endHeight = endHeight;
    
    this.pattern = pattern;
    
    for (int i = 0; i < pattern.size(); i++) {
      
      ArmorStand stand = null;
      Material mat = pattern.get(i);
      
      if (mat != null) {
        
        this.base.getChunk().load();
        stand = (ArmorStand)this.base.getWorld().spawnEntity(this.base, EntityType.ARMOR_STAND);
        stand.setGravity(false);
        stand.setVisible(false);
        stand.setMarker(true);
        
        stand.getEquipment().setHelmet(new ItemStack(mat, 1));
        
        stand.teleport(this.base);
      } 
      this.stands.add(stand);
    } 
    
    this.initialized = true;
  }

  
  public boolean updateLoaded() {
    this.loaded = this.base.getChunk().isLoaded();
    return this.loaded;
  }

  
  public void doIdleAnimation() {
    if (!this.initialized || !this.loaded) {
      
      System.out.print("Flag not loaded!");
      
      return;
    } 
    if (this.animating)
      return; 
    this.animating = true;
    
    if (this.t1 <= 50) {
      
      this.t1++;
    }
    else {
      
      this.t1 = 0;
    } 
    
    this.t2++;
    
    if (this.t2 > 3600)
    {
      this.t2 = 0;
    }
    
    Location currentLoc = this.base.clone();
    
    if (this.lower != null) {
      
      float capPer = this.capRate / 100.0F;
      double xDiff = this.base.getX() - this.lower.getX();
      double yDiff = this.base.getY() - this.lower.getY();
      double zDiff = this.base.getZ() - this.lower.getZ();
      double x = xDiff * capPer + this.lower.getX();
      double y = yDiff * capPer + this.lower.getY();
      double z = zDiff * capPer + this.lower.getZ();
      
      currentLoc.setX(x);
      currentLoc.setY(y);
      currentLoc.setZ(z);
    } 
    
    for (int width = this.startWidth; width <= this.endWidth; width++) {
      
      for (int height = this.startHeight; height <= this.endHeight; height++) {
        
        ArmorStand stand = (ArmorStand)this.stands.get(height * 9 + width);
        
        if (stand != null) {
          
          double val = warpValue(this.t1 + width, 100);
          double val2 = warpValue(this.t1 + width + 1, 100);
          double offset2 = Math.cos(val / 25.0D * Math.PI) * width / 10.0D;
          double offsetFirst = Math.cos(val2 / 25.0D * Math.PI) * (width + 1) / 10.0D;
          
          double offset1 = width * 0.62D - Math.abs(offsetFirst * 0.25D);

          
          double x = lengthdir_x(offset1, this.angle) - lengthdir_y(offset2, this.angle);
          double z = lengthdir_y(offset1, this.angle) + lengthdir_x(offset2, this.angle);
          
          Location loc = currentLoc.clone().add(x, (this.endHeight - height) * 0.62D, z);
          loc.setYaw((float)(this.angle + offsetFirst * 15.0D));
          
          stand.teleport(loc);
        } 
      } 
    } 
    
    this.animating = false;
  }

  
  private double lengthdir_x(double i, double angle) {
    return Math.cos(angle * 0.017453292519943295D) * i;
  }

  
  private double lengthdir_y(double i, double angle) {
    return Math.sin(angle * 0.017453292519943295D) * i;
  }

  
  private int warpValue(int d, int m) {
    int val = d;
    if (d > m)
    {
      val = d - m;
    }
    return val;
  }
}
