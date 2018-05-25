package org.mcmonkey.sentinel.integration;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.struct.Relation;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.mcmonkey.sentinel.SentinelIntegration;

public class SentinelFactionsUUID extends SentinelIntegration {

    @Override
    public String getTargetHelp() {
        return "factions:FACTION_NAME, factionsenemy:NAME, factionsally:NAME";
    }

    @Override
    public boolean isTarget(LivingEntity ent, String text) {
        try {
            if (text.startsWith("factions:") && ent instanceof Player) {
                String factionName = text.substring("factions:".length());
                Faction faction = Factions.getInstance().getFactionById(factionName);
                for (FPlayer pl: faction.getFPlayers()) {
                    if (pl.getPlayer() != null && pl.getPlayer().getUniqueId() != null
                            && pl.getPlayer().getUniqueId().equals(ent.getUniqueId())) {
                        return true;
                    }
                }
            }
            if (text.startsWith("factionsenemy:") && ent instanceof Player) {
                String factionName = text.substring("factionsenemy:".length());
                Faction faction = Factions.getInstance().getFactionById(factionName);
                Faction plf = FPlayers.getInstance().getByPlayer((Player) ent).getFaction();
                if (faction.getRelationTo(plf).equals(Relation.ENEMY)) {
                    return true;
                }
            }
            if (text.startsWith("factionsally:") && ent instanceof Player) {
                String factionName = text.substring("factionsally:".length());
                Faction faction = Factions.getInstance().getFactionById(factionName);
                Faction plf = FPlayers.getInstance().getByPlayer((Player) ent).getFaction();
                if (faction.getRelationTo(plf).equals(Relation.ALLY)) {
                    return true;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
