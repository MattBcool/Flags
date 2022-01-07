package com.mattbcool.data;

import com.mattbcool.data.flags.EditStatus;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;




public class PlayerData
{
  private UUID uuid;
  private EditStatus editStatus = EditStatus.NONE;
  private UUID editFlag = null;
  
  private boolean isMoving = false;
  private Location prevLoc = null;

  
  public PlayerData(UUID uuid) {
    this.uuid = uuid;
  }

  
  public UUID getUUID() {
    return this.uuid;
  }

  
  public EditStatus getEditStatus() {
    return this.editStatus;
  }

  
  public void setEditStatus(EditStatus editStatus) {
    this.editStatus = editStatus;
  }

  
  public UUID getEditFlag() {
    return this.editFlag;
  }

  
  public void setEditFlag(UUID uuid) {
    this.editFlag = uuid;
  }

  
  public boolean isMoving() {
    return this.isMoving;
  }

  
  public void updateIsMoving() {
    Player p = Bukkit.getPlayer(this.uuid);
    
    if (p.isOnline()) {
      
      Location loc = p.getLocation();
      if (this.prevLoc != null && this.prevLoc.distance(loc) > 0.02D) {
        
        this.isMoving = true;
      }
      else {
        
        this.isMoving = false;
      } 
      
      this.prevLoc = loc;
    } 
  }
}