package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;

public class GameBoardState {

    private BoardState boardState;
    private int remainingStudentMoves;
    private final int totalStudentMoves;

    public enum BoardState {
        MOVING_STUDENT,
        MOVING_MN,
        CHOOSING_CLOUD
    }

    public GameBoardState(int numOfPlayers) {
        boardState = BoardState.MOVING_STUDENT;
        totalStudentMoves = numOfPlayers == 2? 3 : 4;
        remainingStudentMoves = totalStudentMoves;
        System.out.println("initialized GameBoardState");
        System.out.println("state: " + boardState);
        System.out.println("stud moves: " + remainingStudentMoves);
    }

    public void nextState(){
        System.out.println("called nextState");
        if (boardState == BoardState.MOVING_STUDENT){
            if (remainingStudentMoves>1) remainingStudentMoves--;
            else boardState = BoardState.MOVING_MN;
        } else if (boardState == BoardState.MOVING_MN) {
            boardState = BoardState.CHOOSING_CLOUD;
        } else {
            System.out.println("total student moves: " + totalStudentMoves);
            System.out.println("recharging remainingStudentMoves");
            remainingStudentMoves = totalStudentMoves;
            System.out.println("remaining stud moves after recharge: " + remainingStudentMoves);
            boardState = BoardState.MOVING_STUDENT;
        }

    }

    public void previousState(){
        if (boardState == BoardState.MOVING_STUDENT){
            if (remainingStudentMoves<totalStudentMoves) remainingStudentMoves++;
            else boardState = BoardState.CHOOSING_CLOUD;
        } else if (boardState == BoardState.MOVING_MN) {
            remainingStudentMoves = 1;
            boardState = BoardState.MOVING_STUDENT;
        } else {
            boardState = BoardState.MOVING_MN;
        }
    }

    public BoardState getBoardState() {
        System.out.println("called getBoardState");
        System.out.println("state: " + boardState);
        System.out.println("stud moves: " + remainingStudentMoves);
        return boardState;
    }
}
