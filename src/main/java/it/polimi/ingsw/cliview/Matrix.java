package it.polimi.ingsw.cliview;

import it.polimi.ingsw.client.CliViewIdConverter;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.StudentCounter;

import java.io.Serializable;
import java.util.*;

public class Matrix implements Serializable {

    private int numRow;
    private int numColumns;

    private Cell[][] mat;

    private int firstColumnDiningRoom;
    private int profColumn;
    private int firstColumnTowers;
    private boolean professorsColumn;

    private String matrixType;


    public Matrix(int n, int m){
        mat = new Cell[n][m];
        numRow = n;
        numColumns = m;
    }



    public Matrix(CliViewIdConverter converter, HashMap<UUID, UUID> islandOwners, LinkedHashMap<UUID, TowerColor> colorPlayersTowers, LinkedHashMap<UUID, Boolean> banOnIslands, UUID motherNaturePosition, LinkedHashMap<UUID, Integer> islandsSize, LinkedHashMap<UUID, ArrayList<PawnColor>> islands){

        matrixType = "islands";

        numRow = 8;
        numColumns = (11 * islands.size()) + (islands.size()-1);

        mat =  new Cell[numRow][numColumns];

        for (int i=0; i<numRow; i++){
            for (int j=0; j<numColumns; j++){
                mat[i][j] = new Cell();
            }
        }

        int columnIndexer = 1;
        /*
        questo codice inserisce i trattini sopra e sotto per delimitare le isole
         */
        for (int i = 0; i< islands.size(); i++){
            for (int j=0; j<9; j++){
                mat[0][columnIndexer].getBullet().setSymbol("-");
                mat[7][columnIndexer].getBullet().setSymbol("-");
                columnIndexer++;
            }
            columnIndexer += 3;  //distanza tra l'ultimo "|" dell'isola precedente e la successiva, in mezzo c'è lo spazio
                          //e il trattino "|" dell'isola successiva (perchè column è stato anche incrementato prima)
        }

        columnIndexer = 0;
        for (int i = 0; i< islands.size(); i++){
            for (int j=1; j<7; j++){
                mat[j][columnIndexer].getBullet().setSymbol("|");
            }
            columnIndexer += 10;
            for (int j=1; j<7; j++){
                mat[j][columnIndexer].getBullet().setSymbol("|");
            }
            columnIndexer += 2;  //distanza tra "|" dell'isola peecedente e la successiva, in mezzo c'è lo spazio
        }

        columnIndexer = 1;
        for (UUID id : islands.keySet()){
            String islandName = converter.idToName(id, CliViewIdConverter.converterSetting.ISLAND);

            for (int i=0; i<islandName.length(); i++){
                mat[1][columnIndexer+i].getBullet().setSymbol(String.valueOf(islandName.charAt(i)));
            }
            columnIndexer += 12;

        }


        columnIndexer = 1;
        for (UUID id : islands.keySet()) {
            for (PawnColor color : PawnColor.values()) {
                int howManyColors = 0;
                for (PawnColor c : islands.get(id)){
                    if (color == c){
                        howManyColors++;
                    }
                }
                mat[2][columnIndexer].getBullet().setColor(Color.pawnColorConverter(color));
                mat[2][columnIndexer].getBullet().setSymbol(String.valueOf(howManyColors));

                columnIndexer += 2;
            }
            columnIndexer += 2;
        }

        columnIndexer = 1;
        for (UUID id : islands.keySet()){
            mat[4][columnIndexer].getBullet().setSymbol("s");
            mat[4][columnIndexer+1].getBullet().setSymbol("i");
            mat[4][columnIndexer+2].getBullet().setSymbol("z");
            mat[4][columnIndexer+3].getBullet().setSymbol("e");
            mat[4][columnIndexer+4].getBullet().setSymbol(":");
            mat[4][columnIndexer+6].getBullet().setSymbol(String.valueOf(islandsSize.get(id)));

            columnIndexer += 12;


        }

        columnIndexer = 1;
        for (UUID id : islands.keySet()){
            mat[5][columnIndexer].getBullet().setSymbol("M");
            mat[5][columnIndexer+1].getBullet().setSymbol("N");
            mat[5][columnIndexer+2].getBullet().setSymbol(":");
            if (id.equals(motherNaturePosition)) {
                mat[5][columnIndexer + 4].getBullet().setSymbol("\u2316");
            }


            columnIndexer += 12;
        }

        columnIndexer = 1;
        for (UUID id : islands.keySet()){

            if (banOnIslands.get(id)){
                mat[6][columnIndexer].getBullet().setSymbol("B");
                mat[6][columnIndexer+1].getBullet().setSymbol("A");
                mat[6][columnIndexer+2].getBullet().setSymbol("N");
            }

            columnIndexer += 12;
        }

        columnIndexer = 1;
        for (UUID id : islands.keySet()){

            if (islandOwners.get(id)!=null){

                for (int i=0; i<islandsSize.get(id); i++){
                    mat[3][columnIndexer+i].getBullet().setSymbol();
                    mat[3][columnIndexer+i].getBullet().setColor(Color.towerColorConverter(colorPlayersTowers.get(islandOwners.get(id))));
                }

            }

            columnIndexer +=12;
        }





    }




    public Matrix(int numPlayers, int numTowersUsed, UUID player, TowerColor towerColor, EnumMap<PawnColor, Integer> entrance, EnumMap<PawnColor, Integer> diningRoom, EnumMap<PawnColor, UUID> professorOwners){
        matrixType = "school";

        int numTower;


        if (numPlayers == 2){
            numColumns = 20;
            firstColumnDiningRoom = 7;
            profColumn = 17;
            firstColumnTowers = 18;
            numTower = 8;
        }else {
            numColumns = 22;
            firstColumnDiningRoom = 9;
            profColumn = 19;
            firstColumnTowers = 20;
            numTower = 6;
        }

        numRow = 5;



        mat = new Cell[numRow][numColumns];

        for (int i=0; i<numRow; i++){
            for (int j=0; j<numColumns; j++){
                mat[i][j] = new Cell();
            }
        }

        insertElements(entrance, 0);
        insertElements(diningRoom, firstColumnDiningRoom);

        professorsColumn =false;
        for (PawnColor color : professorOwners.keySet()){
            if (professorOwners.get(color)!=null && player.equals(professorOwners.get(color))){
                professorsColumn = true;
                break;
            }
        }

        if (professorsColumn) {
            int index = 0;
            for (PawnColor color : professorOwners.keySet()) {
                if (professorOwners.get(color) != null && player.equals(professorOwners.get(color))) {
                    mat[index][profColumn].getBullet().setColor(Color.pawnColorConverter(color));
                    mat[index][profColumn].getBullet().setSymbol();
                }
                index++;
            }
        }

        if (numTower-numTowersUsed>5){
            insertTowers(5, firstColumnTowers, towerColor);
            insertTowers((numTower-numTowersUsed)-5, firstColumnTowers+1, towerColor);
        }else insertTowers(numTower-numTowersUsed, firstColumnTowers, towerColor);



    }

    public int getNumRow() {return numRow;}

    public int getNumColumns() {return numColumns;}

    private void insertTowers(int rowLenght, int column, TowerColor towerColor){
        for (int i=0; i<rowLenght; i++){
            mat[i][column].getBullet().setColor(Color.towerColorConverter(towerColor));
            mat[i][column].getBullet().setSymbol();
        }
    }



    private void insertElements(EnumMap<PawnColor, Integer> entrance, int startingColumn){
        int column;

        int rowIndexer = 0;
        for (PawnColor color : entrance.keySet()){

            column = startingColumn;
            int columnIndexer = 0;
            while (columnIndexer < entrance.get(color)){
                mat[rowIndexer][column].getBullet().setColor(Color.pawnColorConverter(color));
                mat[rowIndexer][column].getBullet().setSymbol();
                columnIndexer++;
                column++;
            }
            rowIndexer++;

        }


    }


    @Override
    public String toString(){
        String s = new String();
        //s += "\n";
        if (matrixType=="school") {
            for (int i = 0; i < getNumRow(); i++) {
                s += "|";
                for (int j = 0; j < getNumColumns(); j++) {
                    if (j == firstColumnDiningRoom) {
                        s += "|";
                    }
                    if (j == profColumn) {
                        s += "|";
                    }
                    if (j == firstColumnTowers) {
                        s += "|";
                    }
                    s += mat[i][j].toString();

                }
                s += "|";
                s += "\n";
            }
        }else{
            for (int i=0; i<getNumRow(); i++){
                for (int j=0; j<getNumColumns(); j++){
                    s += mat[i][j].toString();
                }
                s += "\n";
            }
        }


        return s;
    }

    public void dumb(){
        System.out.println(this);
    }



}
