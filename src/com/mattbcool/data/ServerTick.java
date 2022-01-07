package com.mattbcool.data;

import com.mattbcool.data.flags.MovingFlag;
import com.mattbcool.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;

public class ServerTick
{
	private Main plugin;
	private int tick20 = 0;
	private float capRate;
	private boolean up;
	private double height;
	private double radius;
	private float speed;
	private float counter;

	public void doTicks() {
		do1Tick();
		do20Tick();
	}

	private void do1Tick() {
		doFlagEdits();
		doFlagAnimations();
		updateFlagLoads();
	}

	private void do20Tick() {
		if (this.tick20 >= 19) {
			this.tick20 = 0;
		}
		else {
			this.tick20++;
		} 
	}

	public ServerTick(Main plugin) {
		this.capRate = 100.0F;
		this.height = 2.0D;
		this.radius = 0.5D;
		this.speed = 0.25F;
		this.plugin = plugin;
	}


	private void spawnParticles() {
		for (PlayerData pd : this.plugin.dh.playerData) {
			pd.updateIsMoving();
			Player p = Bukkit.getPlayer(pd.getUUID());
			if (p.isOnline()) {
				Location loc = p.getLocation();
				if (pd.isMoving()) {
					loc.setY(loc.getY() + 0.1D);
					ParticleHandler.playParticle(Particle.DRAGON_BREATH, loc, 0.0D, 1);
					continue;
				} 
				loc.setX(loc.getX() + this.radius * Math.cos(this.counter));
				loc.setY(loc.getY() + this.height);
				if (p.isSneaking())
				{
					loc.setY(loc.getY() - 0.3D);
				}
				loc.setZ(loc.getZ() + this.radius * Math.sin(this.counter));
				ParticleHandler.playParticle(Particle.DRAGON_BREATH, loc, 0.0D, 1);
			} 
		} 


		this.counter += this.speed;
	}

	private void updateFlagLoads() {
		if (this.up) {
			if (++this.capRate >= 100.0F)
				this.up = false; 
		} else if (--this.capRate <= 0.0F) {
			this.up = true;
		} 
		for (MovingFlag flag : this.plugin.dh.flagData) {
			flag.updateLoaded();
			flag.setCapRate(this.capRate);
		} 
	}

	private void doFlagEdits() {
		for (PlayerData pd : this.plugin.dh.playerData) {
			if (pd.getEditFlag() != null) {
				Player p = Bukkit.getPlayer(pd.getUUID());
				if (p != null) {
					float yaw, angle;
					MovingFlag flag = this.plugin.dh.getFlagData(pd.getEditFlag());
					switch (pd.getEditStatus()) {
					case NONE:
						break;
					case LOWER:
					case POSITION:
						flag.setBase(getSelector(p, flag));
					case ANGLE:
						yaw = p.getLocation().getYaw();
						
						angle = 90 + (yaw < 0 ? 360 + yaw : yaw) % 360;
						flag.setAngle(angle);
						System.out.println(yaw + " | " + angle);
						break;
					case PATTERN:
						break;
					default:
						break;
					} 
				} 
			} 
		} 
	}

	private Location getSelector(Player p, MovingFlag flag) {
		Location target = null;
		int positionDistance = 5;
		BlockIterator blocksToAdd = new BlockIterator(p.getLocation(), 0.0D, positionDistance);
		while (blocksToAdd.hasNext()) {
			Location loc = blocksToAdd.next().getLocation();
			if (flag.getBase().distance(loc) > 0.5D)
				target = loc; 
			if (loc.getBlock().getType().isSolid())
				break; 
		} 
		return target;
	}

	private void doFlagAnimations() {
		for (MovingFlag flag : this.plugin.dh.flagData)
			flag.doIdleAnimation(); 
	}
}