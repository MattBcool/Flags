package com.mattbcool.commands;

import com.mattbcool.data.PlayerData;
import com.mattbcool.data.flags.EditStatus;
import com.mattbcool.data.flags.MovingFlag;
import com.mattbcool.main.Main;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;



public class FlagCommand
implements CommandExecutor
{
	private Main plugin;

	public FlagCommand(Main plugin) {
		this.plugin = plugin;
	}



	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player))
		{
			return true;
		}
		Player p = (Player)sender;

		if (cmd.getName().equalsIgnoreCase("flag")) {
			if (args.length == 0 || args[0].equalsIgnoreCase("help")) {
				p.sendMessage(ChatColor.GOLD + "========= " + ChatColor.YELLOW + "FLAGS HELP" + ChatColor.GOLD + " =========");
				p.sendMessage(ChatColor.YELLOW + "* /flag <angle>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <copy|clone>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <delete|remove>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <help>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <lower>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <move>");
				p.sendMessage(ChatColor.YELLOW + "* /flag <pattern>");
				p.sendMessage(ChatColor.GOLD + "==============================");
			}
			else if (args[0].equalsIgnoreCase("create")) {

				if (p.hasPermission("flags.create")) {

					PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

					if (pd != null)
					{
						MovingFlag flag = new MovingFlag(p.getLocation(), 0.0F, null, 0, 0, 0, 0);
						this.plugin.dh.flagData.add(flag);
						pd.setEditStatus(EditStatus.POSITION);
						pd.setEditFlag(flag.getUUID());
						p.sendMessage(ChatColor.GREEN + "You are now placing a flag!");
					}
					else
					{
						p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else if (args[0].equalsIgnoreCase("copy") || args[0].equalsIgnoreCase("clone")) {

				if (p.hasPermission("flags.copy")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null) {

						PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

						if (pd != null)
						{
							flag = flag.clone();
							this.plugin.dh.flagData.add(flag);
							pd.setEditStatus(EditStatus.POSITION);
							pd.setEditFlag(flag.getUUID());
							p.sendMessage(ChatColor.GREEN + "You have successfully cloned a flag!");
						}
						else
						{
							p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
						}

					} else {

						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else if (args[0].equalsIgnoreCase("delete") || args[0].equalsIgnoreCase("remove")) {

				if (p.hasPermission("flags.delete")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null)
					{
						flag.clearFlag();
						this.plugin.dh.flagData.remove(flag);
						p.sendMessage(ChatColor.GREEN + "You have successfully removed a flag!");
					}
					else
					{
						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else if (args[0].equalsIgnoreCase("angle")) {

				if (p.hasPermission("flags.angle")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null) {

						PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

						if (pd != null)
						{
							pd.setEditStatus(EditStatus.ANGLE);
							pd.setEditFlag(flag.getUUID());
							p.sendMessage(ChatColor.GREEN + "You are now modifying a flag's angle!");
						}
						else
						{
							p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
						}

					} else {

						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");
				}

			}
			else if (args[0].equalsIgnoreCase("test")) {

				p.sendMessage("Testing");


			}
			else if (args[0].equalsIgnoreCase("lower")) {

				if (p.hasPermission("flags.lower")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null) {

						PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

						if (pd != null)
						{
							flag.setLower(null);
							flag.setTempBase(flag.getBase());
							pd.setEditStatus(EditStatus.LOWER);
							pd.setEditFlag(flag.getUUID());
							p.sendMessage(ChatColor.GREEN + "You are now setting the lower position for the flag!");
						}
						else
						{
							p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
						}

					} else {

						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else if (args[0].equalsIgnoreCase("move")) {

				if (p.hasPermission("flags.move")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null) {

						PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

						if (pd != null)
						{
							pd.setEditStatus(EditStatus.POSITION);
							pd.setEditFlag(flag.getUUID());
							p.sendMessage(ChatColor.GREEN + "You are now moving a flag!");
						}
						else
						{
							p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
						}

					} else {

						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else if (args[0].equalsIgnoreCase("pattern")) {

				if (p.hasPermission("flags.pattern")) {

					MovingFlag flag = this.plugin.dh.getClosestFlag(p.getLocation());
					if (flag != null) {

						PlayerData pd = this.plugin.dh.getPlayerData(p.getUniqueId());

						if (pd != null)
						{
							pd.setEditStatus(EditStatus.PATTERN);
							pd.setEditFlag(flag.getUUID());

							Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GREEN + "Flag Pattern");
							ArrayList<Material> pattern = flag.getPattern();

							for (int i = 0; i < inv.getSize(); i++) {

								if (pattern.get(i) != null) inv.setItem(i, new ItemStack(pattern.get(i)));

							} 
							p.openInventory(inv);

							p.sendMessage(ChatColor.GREEN + "You are now modifying a flag's pattern!");
						}
						else
						{
							p.sendMessage(ChatColor.RED + "An internal error has occurred! Please consult an administrator.");
						}

					} else {

						p.sendMessage(ChatColor.RED + "There is no flag in close proximity to modify!");
					}

				} else {

					p.sendMessage(ChatColor.RED + "You do not have perimission to execute this command!");

				}

			}
			else {

				p.sendMessage(ChatColor.RED + "Invalid arguments! Please use /flag help for more info.");
			} 
			return true;
		} 
		return false;
	}
}
