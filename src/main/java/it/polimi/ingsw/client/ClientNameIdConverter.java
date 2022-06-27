package it.polimi.ingsw.client;

import it.polimi.ingsw.networkmessages.modelevents.GameState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class ClientNameIdConverter {
    private GameState gameState;
    private HashMap<UUID, String> idToNameMap;
    private HashMap<String, UUID> nameToIdMap;

    public ClientNameIdConverter(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public enum ConverterSetting {
        CLOUD, ISLAND, PLAYER
    }

    private void updateMaps(ConverterSetting setting){
        if (setting == ConverterSetting.PLAYER) return;

        idToNameMap = new HashMap<>();
        nameToIdMap = new HashMap<>();

        ArrayList<UUID> idList = null;
        String elementName = null;
        if (setting == ConverterSetting.CLOUD){
            idList = new ArrayList<>(gameState.getClouds().keySet());
            elementName = "cloud";
        } else if (setting == ConverterSetting.ISLAND){
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

    public String idToName(UUID uuid, ConverterSetting setting){
        if (setting == ConverterSetting.PLAYER) return gameState.getNicknames().get(uuid);

        updateMaps(setting);

        return idToNameMap.get(uuid);

    }

    public UUID nameToId(String name, ConverterSetting setting){
        if (setting == ConverterSetting.PLAYER){
            for (UUID id : gameState.getNicknames().keySet()){
                if (gameState.getNicknames().get(id).equals(name)) return id;
            }
            throw new IllegalArgumentException("nickname not present among players!");
        }

        updateMaps(setting);

        return nameToIdMap.get(name);
    }

    public int getSize(ConverterSetting setting){
        if (setting == ConverterSetting.PLAYER){
            return gameState.getNicknames().size();
        }

        updateMaps(setting);

        return idToNameMap.size();
    }
}