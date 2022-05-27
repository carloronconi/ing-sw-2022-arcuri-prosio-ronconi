package it.polimi.ingsw.cliview;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.TowerColor;
import it.polimi.ingsw.model.studentmanagers.StudentCounter;

import java.io.Serializable;
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




    public Matrix(int numPlayers, int numTowersUsed, Player player, EnumMap<PawnColor, UUID> professorOwners){
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

        insertingElements(player.getEntrance(), 0);
        insertingElements(player.getDiningRoom(), firstColumnDiningRoom);

        professorsColumn =false;
        for (PawnColor color : professorOwners.keySet()){
            if (professorOwners.get(color)!=null && player.getId().equals(professorOwners.get(color))){
                professorsColumn = true;
                break;
            }
        }

        if (professorsColumn) {
            int index = 0;
            for (PawnColor color : professorOwners.keySet()) {
                if (professorOwners.get(color) != null && player.getId().equals(professorOwners.get(color))) {
                    mat[index][profColumn].getBullet().setColor(pawnColorConverter(color));
                    mat[index][profColumn].getBullet().setSymbol();
                }
                index++;
            }
        }


        if (numTower-numTowersUsed>5){
            insertingTowers(5, firstColumnTowers, player);
            insertingTowers((numTower-numTowersUsed)-5, firstColumnTowers+1, player);
        }else insertingTowers(numTower-numTowersUsed, firstColumnTowers, player);




    }

    public int getNumRow() {return numRow;}

    public int getNumColumns() {return numColumns;}


    private void insertingTowers(int rowLenght, int column, Player p){
        for (int i=0; i<rowLenght; i++){
            mat[i][column].getBullet().setColor(towerColorConverter(p.getTowerColor()));
            mat[i][column].getBullet().setSymbol();
        }
    }

    private void insertingElements(StudentCounter studentCounter, int startingColumn){

        int column;

        int rowIndexer = 0;
        for (PawnColor color : PawnColor.values()){

            column = startingColumn;
            int columnIndexer = 0;
            while (columnIndexer < studentCounter.count(color)){
                mat[rowIndexer][column].getBullet().setColor(pawnColorConverter(color));
                mat[rowIndexer][column].getBullet().setSymbol();
                columnIndexer++;
                column++;
            }
            rowIndexer++;
        }
    }


    private Color pawnColorConverter(PawnColor color){
        switch (color){
            case RED:
                return Color.ANSI_RED;
            case GREEN:
                return Color.ANSI_GREEN;
            case BLUE:
                return Color.ANSI_BLUE;
            case YELLOW:
                return Color.ANSI_YELLOW;
            case PURPLE:
                return Color.ANSI_PURPLE;
        }
        return Color.RESET;
    }

    private Color towerColorConverter(TowerColor color){
        switch (color){
            case WHITE:
                return Color.ANSI_WHITE;
            case BLACK:
                return Color.ANSI_BLACK;
            case GREY:
                return Color.ANSI_GREY;
        }
        return Color.RESET;
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
