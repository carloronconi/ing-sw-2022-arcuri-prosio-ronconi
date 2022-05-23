package it.polimi.ingsw.networkmessages.viewevents;

import it.polimi.ingsw.server.ClientHandler;
import it.polimi.ingsw.server.VirtualView;

import java.io.InvalidObjectException;
import java.io.Serializable;

public class ReadyToPlay implements Serializable, SetupViewEvent {
    private static boolean preferencesWereSet = false;

    public ReadyToPlay(){

    }

    @Override
   public void processMessage(VirtualView virtualView) throws InvalidObjectException, InterruptedException {
        if(virtualView.getThisInstanceNumber()!=0){
            synchronized (ReadyToPlay.class){
                while(!preferencesWereSet){
                    try {
                        System.out.println("thread "+ virtualView.getThisInstanceNumber() + " waiting");
                        ReadyToPlay.class.wait();
                        System.out.println("thread "+ virtualView.getThisInstanceNumber() + " restarted");
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
        /*
        if(virtualView.getThisInstanceNumber()==1) {
            synchronized (lock) {
                while (num < 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }


        if(2== virtualView.getThisInstanceNumber()) {
            synchronized (lock) {
                while (num < 2) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }*/


        synchronized (ReadyToPlay.class){
            preferencesWereSet = true;
            ReadyToPlay.class.notifyAll();
            System.out.println("thread " + virtualView.getThisInstanceNumber() + " notified all");
        }

        synchronized (ReadyToPlay.class){
            while (!virtualView.isItMyTurn()) {
                try {
                    ReadyToPlay.class.wait();
                    System.out.println("thread " + virtualView.getThisInstanceNumber() + " checking if it's his turn");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("thread " + virtualView.getThisInstanceNumber() + " gone from readyToPlay");
        virtualView.getAssistantCard();

        /*synchronized (ReadyToPlay.class){
            ReadyToPlay.class.notifyAll();
        }*/


    }
}


