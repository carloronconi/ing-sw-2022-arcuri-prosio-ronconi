package it.polimi.ingsw.cliview;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.StudentCounter;

import java.io.Serializable;

public class Matrix implements Serializable {

    private int numRighe;
    private int numColonne;

    private Cell[][] mat;

    private int startColumn;


    public Matrix(int n, int m){
        mat = new Cell[n][m];
        numRighe = n;
        numColonne = m;
    }



    public Matrix(int row, int columns, StudentCounter studentCounter, StudentCounter studentCounter2){

        mat = new Cell[row][columns];
        numColonne = columns;


        for (int i=0; i<row; i++){
            for (int j=0; j<columns; j++){
                mat[i][j] = new Cell();
            }
        }



        int rowIndexer=0;
        for (PawnColor color : PawnColor.values()){

            int columnIndexer=0;
            while (columnIndexer< studentCounter.count(color)){
                Color c = colorConverter(color);
                mat[rowIndexer][columnIndexer].getBullet().setColor(c);
                mat[rowIndexer][columnIndexer].getBullet().setSymbol();
                columnIndexer++;
            }
            rowIndexer++;
        }

        startColumn = mat[0].length;
        for (PawnColor color : PawnColor.values()){

            int columnIndexer=0;
            while (columnIndexer< studentCounter2.count(color)){
                Color c = colorConverter(color);
                mat[rowIndexer][startColumn].getBullet().setColor(c);
                mat[rowIndexer][startColumn].getBullet().setSymbol();
                columnIndexer++;
                startColumn++;
            }
            rowIndexer++;
        }
    }

    public int getNumRighe() {return numRighe;}

    public int getNumColonne() {return numColonne;}

    /*private int max(StudentCounter studentCounter){
        int max=-1;
        for (PawnColor c : PawnColor.values()){
            if (studentCounter.count(c)>max){
                max=studentCounter.count(c);
            }
        }
        return max;
    }*/

    private Color colorConverter(PawnColor color){
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

    @Override
    public String toString(){
        String s = new String();
        s += "\n";
        for (int i=0; i< mat.length; i++){
            s += "|";
            for (int j=0; j<numColonne; j++){
                s += mat[i][j].toString();
                if (j==startColumn){
                    s += "|";
                }
            }
            s += "|";
            s += "\n";
        }


        return s;
    }

    public void dumb(){
        System.out.println(this);
    }

    /*public Cell getCell(){
        return cell;
    }


    public Matrice(int n, int m) {
        mat=new Cella[n][m];
        for(int i=0;i<n;i++) {
            for(int j=0;j<m;j++) {
                mat[i][j]=new Cella();
            }
        }
    }

    public Cella[][] mat;*/




}
