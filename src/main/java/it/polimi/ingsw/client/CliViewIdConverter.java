package it.polimi.ingsw.client;

import it.polimi.ingsw.networkmessages.modelevents.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class CliViewIdConverter {
    private GameState gameState;
    private HashMap<UUID, String> idToNameMap;
    private HashMap<String, UUID> nameToIdMap;

    public CliViewIdConverter(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public enum converterSetting {
        CLOUD, ISLAND, PLAYER
    }

    private void updateMaps(converterSetting setting){
        if (setting == converterSetting.PLAYER) return;

        idToNameMap = new HashMap<>();
        nameToIdMap = new HashMap<>();

        ArrayList<UUID> idList = null;
        String elementName = null;
        if (setting == converterSetting.CLOUD){
            idList = new ArrayList<>(gameState.getClouds().keySet());
            elementName = "cloud";
        } else if (setting == converterSetting.ISLAND){
            idList = new ArrayList<>(gameState.getIslands().keySet());
            elementName = "island";
        }
        int i = 1;
        for (UUID id : idList){
            //String additionalSpace = i<10? " " : "";
            String currName = elementName + Integer.toString(i);
            idToNameMap.put(id, currName);
            nameToIdMap.put(currName, id);
            i++;
        }

    }

    public String idToName(UUID uuid, converterSetting setting){
        if (setting == converterSetting.PLAYER) return gameState.getNicknames().get(uuid);

        updateMaps(setting);

        return idToNameMap.get(uuid);

    }

    public UUID nameToId(String name, converterSetting setting){
        if (setting == converterSetting.PLAYER){
            for (UUID id : gameState.getNicknames().keySet()){
                if (gameState.getNicknames().get(id).equals(name)) return id;
            }
            throw new IllegalArgumentException("nickname not present among players!");
        }

        updateMaps(setting);

        return nameToIdMap.get(name);
    }
}
