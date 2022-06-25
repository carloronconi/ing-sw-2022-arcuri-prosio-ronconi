package it.polimi.ingsw.client;

import it.polimi.ingsw.controller.GameMode;

public class GameBoardState {

    private BoardState boardState;
    private int remainingStudentMoves;
    private int totalStudentMoves;

    public enum BoardState {
        MOVING_STUDENT,
        MOVING_MN,
        CHOOSING_CLOUD
    }

    public GameBoardState(int numOfPlayers) {
        boardState = BoardState.MOVING_STUDENT;
        totalStudentMoves = numOfPlayers == 2? 3 : 4;
        remainingStudentMoves = totalStudentMoves;
    }

    public void nextState(){
        if (boardState == BoardState.MOVING_STUDENT){
            if (remainingStudentMoves>0) remainingStudentMoves--;
            else boardState = BoardState.MOVING_MN;
        } else if (boardState == BoardState.MOVING_MN) {
            boardState = BoardState.CHOOSING_CLOUD;
        } else {
            remainingStudentMoves = totalStudentMoves;
            boardState = BoardState.MOVING_STUDENT;
        }
/*
        switch (boardState){
            case MOVING_STUDENT:
                if (remainingStudentMoves>0) remainingStudentMoves--;
                else boardState = BoardState.MOVING_MN;
            case MOVING_MN: boardState = BoardState.CHOOSING_CLOUD;
            case CHOOSING_CLOUD:
                remainingStudentMoves = totalStudentMoves;
                boardState = BoardState.MOVING_STUDENT;
        }*/
    }

    public BoardState getBoardState() {
        return boardState;
    }
}
