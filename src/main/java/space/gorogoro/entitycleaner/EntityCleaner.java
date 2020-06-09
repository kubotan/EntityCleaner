package space.gorogoro.entitycleaner;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * EntityCleaner
 * @license    LGPLv3
 * @copyright  Copyright gorogoro.space 2020
 * @author     kubotan
 * @see        <a href="https://gorogoro.space">gorogoro.space</a>
 */
public class EntityCleaner extends JavaPlugin implements Listener {

  /**
   * JavaPlugin method onEnable.
   */
  @Override
  public void onEnable() {
    try {
      getLogger().info("The Plugin Has Been Enabled!");
      getServer().getPluginManager().registerEvents(this, this);
    } catch (Exception e) {
      logStackTrace(e);
    }
  }

  @EventHandler
  public void onPlayerMove(PlayerMoveEvent e) {
    Player p = e.getPlayer();
    for(Entity entity:p.getNearbyEntities(16D, 16D, 16D)) {

      if (!entity.getType().equals(EntityType.ARMOR_STAND)) {
        continue;
      }

      ArmorStand armorStand = (ArmorStand)entity;
      if(armorStand.isVisible()) {
        continue;
      }

      if(armorStand.getPassengers().size() > 0) {
        continue;
      }

      getLogger().info("Clear ARMOR_STAND. player=" + p.getName() + " Location:" + armorStand.getLocation().toString());
      armorStand.remove();
    }
  }

  public void logStackTrace(Exception e){
    StringWriter sw = new StringWriter();
    PrintWriter pw = new PrintWriter(sw);
    e.printStackTrace(pw);
    pw.flush();
    getLogger().warning(sw.toString());
  }

  /**
   * JavaPlugin method onDisable.
   */
  @Override
  public void onDisable() {
    try {
      getLogger().info("The Plugin Has Been Disabled!");
    } catch (Exception e) {
      logStackTrace(e);
    }
  }
}
