package me.wowkfccc.logplayeraction.worldBorder;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public final class WorldBorder extends JavaPlugin {

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    updateScoreboard(player);
                }
            }
        }.runTaskTimer(this, 0L, 20L); // 每 20 tick (1 秒) 更新一次
    }

    private void updateScoreboard(Player player) {
        org.bukkit.WorldBorder border = player.getWorld().getWorldBorder();
        Location center = border.getCenter();
        double halfSize = border.getSize() / 2.0;

        double dx = Math.abs(player.getLocation().getX() - center.getX());
        double dz = Math.abs(player.getLocation().getZ() - center.getZ());
        double distance = halfSize - Math.max(dx, dz);
        distance = Math.max(distance, 0); // 避免負值

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective objective = board.registerNewObjective("borderDistance", "dummy", "§a距離邊界");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        Score score = objective.getScore("§f剩餘格數: §e" + (int) distance);
        score.setScore(1);

        player.setScoreboard(board);
    }
}


