package it.polimi.ingsw.model;

import java.util.List;
import java.util.UUID;

/**
 * utility class to convert the id of an element in a list to its index and vice-versa
 */
public final class ConverterUtility {
    /**
     * private to prevent from instantiating the class
     */
    private ConverterUtility() {}

    /**
     * converts the id of an element in a list of objects that offer the getId method to its index
     * @param uuid id to be converted
     * @param list of objects that implement the Identifiable interface
     * @return index of the element
     * @throws NoSuchFieldException if no element in the list has the provided id
     */
    static int idToIndex(UUID uuid, List<Identifiable> list) throws NoSuchFieldException {
        for (int i = 0; i < list.size(); i++) {
            UUID elementId = list.get(i).getId();
            if (elementId.equals(uuid)) return i;
        }
        throw new NoSuchFieldException("Id not found in the provided list");
    }

    /**
     * converts the index of an element in a list of objects that offer the getId method to its id
     * @param index index to be converted
     * @param list of objects that implement the Identifiable interface
     * @return id of the element
     */
    static UUID indexToId(int index, List<Identifiable> list) {
        return list.get(index).getId();
    }

}
