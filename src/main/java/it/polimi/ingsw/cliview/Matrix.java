package it.polimi.ingsw.cliview;

import it.polimi.ingsw.model.PawnColor;
import it.polimi.ingsw.model.studentmanagers.StudentCounter;

import java.io.Serializable;

public class Matrix implements Serializable {

    private int numRow;
    private int numColumns;

    private Cell[][] mat;

    private int studentCounter2InitialColumn=0;

    private int firstStudentstudentCounter2;


    public Matrix(int n, int m){
        mat = new Cell[n][m];
        numRow = n;
        numColumns = m;
    }



    public Matrix(int row, int columns, int firstStudentstudentCounter2, StudentCounter studentCounter, StudentCounter studentCounter2){

        mat = new Cell[row][columns];
        numRow = row;
        numColumns = columns;

        this.firstStudentstudentCounter2=firstStudentstudentCounter2;


        for (int i=0; i<row; i++){
            for (int j=0; j<columns; j++){
                mat[i][j] = new Cell();
            }
        }


        //int studentCounter2InitialColumn=0;
        int rowIndexer=0;
        for (PawnColor color : PawnColor.values()){

            int columnIndexer=0;
            while (columnIndexer< studentCounter.count(color)){
                Color c = colorConverter(color);
                mat[rowIndexer][columnIndexer].getBullet().setColor(c);
                mat[rowIndexer][columnIndexer].getBullet().setSymbol();
                columnIndexer++;
                /*if (columnIndexer>studentCounter2InitialColumn){
                    studentCounter2InitialColumn=columnIndexer;
                }*/
            }
            rowIndexer++;
        }

        int m=firstStudentstudentCounter2;
        rowIndexer=0;
        //studentCounter2InitialColumn = mat[0].length;
        for (PawnColor color : PawnColor.values()){

            int columnIndexer=0;
            while (columnIndexer< studentCounter2.count(color)){
                Color c = colorConverter(color);
                mat[rowIndexer][m].getBullet().setColor(c);
                mat[rowIndexer][m].getBullet().setSymbol();
                columnIndexer++;
                m++;
                //studentCounter2InitialColumn++;
            }
            rowIndexer++;
            m=firstStudentstudentCounter2;
        }
    }

    public int getNumRow() {return numRow;}

    public int getNumColumns() {return numColumns;}

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
        for (int i=0; i< getNumRow(); i++){
            s += "|";
            for (int j=0; j<getNumColumns(); j++){
                if (j==firstStudentstudentCounter2){
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
