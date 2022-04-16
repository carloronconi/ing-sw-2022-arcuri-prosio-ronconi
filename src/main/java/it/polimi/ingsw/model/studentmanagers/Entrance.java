package it.polimi.ingsw.model.studentmanagers;

import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

/**
 * subclass of StudentCounter that can just be filled in when initialised or with fill method
 */
public class Entrance extends StudentCounter {
    private final List<Cloud> clouds;

    /**
     * constructor to create Entrance filling it with 7 pawns from the bag
     * @param bag from which it takes the first 7 pawns
     * @param clouds from which it will be filled
     */
    public Entrance(Bag bag, List<Cloud> clouds) {
        super();
        this.clouds = clouds;
        IntStream.range(0,7).forEach(i -> movePawnFrom(bag));
    }


    /**
     * way to fill the Entrance from one of the clouds in the game
     * @param whichCloud to select the cloud from which to fill the Entrance
     * @throws IllegalArgumentException if the cloud is not found in the list
     */
    public void fill(UUID whichCloud) throws IllegalArgumentException {

        int cloudIndex=-1;
        boolean cloudFound=false;
        for(int i=0; i<clouds.size(); i++){
            if(clouds.get(i).getId().equals(whichCloud)){
                cloudIndex=i;
                cloudFound=true;
            }
        }

        if(!cloudFound) throw new IllegalArgumentException();

        for(int i=0; i<clouds.get(cloudIndex).count(); i++){
            movePawnFrom(clouds.get(cloudIndex));

        }

    }


}
