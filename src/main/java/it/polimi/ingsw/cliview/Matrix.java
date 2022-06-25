package it.polimi.ingsw.cliview;

import it.polimi.ingsw.client.CliViewIdConverter;
import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.TowerColor;

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
    private int initialRow;
    private int initialColumn;
    private int finalRow;
    private int finalColumn;


    public Matrix(int n, int m){
        mat = new Cell[n][m];
        numRow = n;
        numColumns = m;
    }

    //islands - circular shape
    public Matrix(CliViewIdConverter converter, HashMap<UUID, UUID> islandOwners, LinkedHashMap<UUID, TowerColor> colorPlayersTowers, LinkedHashMap<UUID, Boolean> banOnIslands, UUID motherNaturePosition, LinkedHashMap<UUID, Integer> islandsSize, ArrayList<UUID> initialIslandIds, LinkedHashMap<UUID, ArrayList<PawnColor>> islands){

        matrixType = "islands";

        numRow = 34;
        numColumns = (11 * 7) + 30;

        mat =  new Cell[numRow][numColumns];

        for (int i=0; i<numRow; i++){
            for (int j=0; j<numColumns; j++){
                mat[i][j] = new Cell();
            }
        }

        for (UUID uuid : initialIslandIds){

            if (islands.containsKey(uuid)){

                choiceCoordinates(initialIslandIds.indexOf(uuid));

                drawOutline(initialRow, initialColumn);

                insertStudents(initialRow, initialColumn, islands.get(uuid));

                insertIslandSize(initialRow, initialColumn, islandsSize.get(uuid));

                insertMotherNature(initialRow, initialColumn, uuid.equals(motherNaturePosition));

                insertBanOnIsland(initialRow, initialColumn, banOnIslands.get(uuid));

                insertTower(initialRow, initialColumn, islandOwners.get(uuid), colorPlayersTowers, islandsSize.get(uuid));

                String islandName = converter.idToName(uuid, CliViewIdConverter.ConverterSetting.ISLAND);

                insertName(initialRow, initialColumn, islandName);




            }
        }


    }

    private void choiceCoordinates(int positionInArray){
        switch (positionInArray){
            case 0:
                initialRow = 13;
                finalRow = 20;
                initialColumn = 0;
                finalColumn = 10;
                break;
            case 1:
                initialRow = 7;
                finalRow = 14;
                initialColumn = 16;
                finalColumn = 26;
                break;
            case 2:
                initialRow = 3;
                finalRow = 10;
                initialColumn = 32;
                finalColumn = 42;
                break;
            case 3:
                initialRow = 0;
                finalRow = 7;
                initialColumn = 48;
                finalColumn = 58;
                break;
            case 4:
                initialRow = 3;
                finalRow = 10;
                initialColumn = 64;
                finalColumn = 74;
                break;
            case 5:
                initialRow = 7;
                finalRow = 14;
                initialColumn = 80;
                finalColumn = 90;
                break;
            case 6:
                initialRow = 13;
                finalRow = 20;
                initialColumn = 96;
                finalColumn = 106;
                break;
            case 7:
                initialRow = 19;
                finalRow = 26;
                initialColumn = 80;
                finalColumn = 90;
                break;
            case 8:
                initialRow = 23;
                finalRow = 30;
                initialColumn = 64;
                finalColumn = 74;
                break;
            case 9:
                initialRow = 26;
                finalRow = 33;
                initialColumn = 48;
                finalColumn = 58;
                break;
            case 10:
                initialRow = 23;
                finalRow = 30;
                initialColumn = 32;
                finalColumn = 42;
                break;
            case 11:
                initialRow = 19;
                finalRow = 26;
                initialColumn = 16;
                finalColumn = 26;
                break;
        }
    }

    private void drawOutline(int initialRow, int initialColumn){
        int columnIndexer;

        columnIndexer = initialColumn; //use another variable because I have to increment it
        for (int i=0; i<9; i++){
            columnIndexer++;
            mat[initialRow][columnIndexer].getBullet().setSymbol("-");
            mat[initialRow+7][columnIndexer].getBullet().setSymbol("-");
        }

        for (int i=0; i<6; i++){
            initialRow++;
            mat[initialRow][initialColumn].getBullet().setSymbol("|");
            mat[initialRow][initialColumn+10].getBullet().setSymbol("|");
        }

    }

    private void insertStudents(int initialRow, int initialColumn, ArrayList<PawnColor> islandsColors){


        for (PawnColor color : PawnColor.values()){
            int howManyColors =0;
            for (PawnColor c : islandsColors){
                if (color==c){
                    howManyColors++;
                }
            }

            mat[initialRow+2][initialColumn+1].getBullet().setColor(Color.pawnColorConverter(color));
            mat[initialRow+2][initialColumn+1].getBullet().setSymbol(String.valueOf(howManyColors));

            initialColumn += 2;
        }

    }

    private void insertIslandSize(int initialRow, int initialColumn, Integer islandSize){

        mat[initialRow+4][initialColumn+1].getBullet().setSymbol("s");
        mat[initialRow+4][initialColumn+2].getBullet().setSymbol("i");
        mat[initialRow+4][initialColumn+3].getBullet().setSymbol("z");
        mat[initialRow+4][initialColumn+4].getBullet().setSymbol("e");
        mat[initialRow+4][initialColumn+5].getBullet().setSymbol(":");
        mat[initialRow+4][initialColumn+7].getBullet().setSymbol(String.valueOf(islandSize));

    }

    private void insertMotherNature(int initialRow, int initialColumn, boolean motherNatureHere){

        mat[initialRow+5][initialColumn+1].getBullet().setSymbol("M");
        mat[initialRow+5][initialColumn+2].getBullet().setSymbol("N");
        mat[initialRow+5][initialColumn+3].getBullet().setSymbol(":");
        if (motherNatureHere){
            mat[initialRow+5][initialColumn+5].getBullet().setSymbol("h");
            mat[initialRow+5][initialColumn+6].getBullet().setSymbol("e");
            mat[initialRow+5][initialColumn+7].getBullet().setSymbol("r");
            mat[initialRow+5][initialColumn+8].getBullet().setSymbol("e");
        }

    }

    private void insertBanOnIsland(int initialRow, int initialColumn, boolean banHere){

        if (banHere){
            mat[initialRow+6][initialColumn+1].getBullet().setSymbol("B");
            mat[initialRow+6][initialColumn+2].getBullet().setSymbol("A");
            mat[initialRow+6][initialColumn+3].getBullet().setSymbol("N");
        }
    }

    private void insertTower(int initialRow, int initialColumn, UUID islandOwners, LinkedHashMap<UUID, TowerColor> colorPlayersTowers, Integer islandsSize){
        int columnIndexer = initialColumn;

        columnIndexer++;

        if (islandOwners!=null){

            for (int i=0; i<islandsSize; i++){

                mat[initialRow+3][columnIndexer+i].getBullet().setSymbol();
                mat[initialRow+3][columnIndexer+i].getBullet().setColor(Color.towerColorConverter(colorPlayersTowers.get(islandOwners)));
            }

        }


    }

    private void insertName(int initialRow, int initialColumn, String islandName){
        int columnIndexer = initialColumn;

        columnIndexer++;
        for (int i=0; i<islandName.length(); i++){
            mat[initialRow+1][columnIndexer+i].getBullet().setSymbol(String.valueOf(islandName.charAt(i)));
        }

    }


    //islands - linear shape
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

        //questo codice inserisce i trattini sopra e sotto per delimitare le isole
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
            String name = converter.idToName(id, CliViewIdConverter.ConverterSetting.ISLAND);

            for (int i=0; i<name.length(); i++){
                mat[1][columnIndexer+i].getBullet().setSymbol(String.valueOf(name.charAt(i)));
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



    //school
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
