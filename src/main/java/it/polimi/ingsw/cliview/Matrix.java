package it.polimi.ingsw.cliview;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.studentmanagers.Cloud;
import it.polimi.ingsw.model.studentmanagers.StudentCounter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.UUID;

public class Matrix implements Serializable {

    private int numRow;
    private int numColumns;

    private Cell[][] mat;

    int firstColumnDiningRoom;
    int profColumn;
    int firstColumnTowers;
    boolean professorsColumn;


    public Matrix(int n, int m){
        mat = new Cell[n][m];
        numRow = n;
        numColumns = m;
    }






    public Matrix(int numPlayers, int numTowersUsed, UUID player, TowerColor towerColor, EnumMap<PawnColor, Integer> entrance, EnumMap<PawnColor, Integer> diningRoom, EnumMap<PawnColor, UUID> professorOwners){
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
        for (int i=0; i< getNumRow(); i++){
            s += "|";
            for (int j=0; j<getNumColumns(); j++){
                if (j==firstColumnDiningRoom){
                    s += "|";
                }
                if (j==profColumn){
                    s += "|";
                }
                if (j==firstColumnTowers){
                    s += "|";
                }
                s += mat[i][j].toString();

            }
            s += "|";
            s += "\n";
        }


        return s;
    }

    public void dumb(){
        System.out.println(this);
    }



}
