package com.mattbcool.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;


public class ParticleHandler
{
  public static void playParticle(Particle particle, Location loc, double speed, int amount) {
    for (Player p : Bukkit.getOnlinePlayers())
    {
      
      p.spawnParticle(particle, loc.getX(), loc.getY(), loc.getZ(), 0, 0.0D, 0.0D, speed, amount);
    }
  }
}