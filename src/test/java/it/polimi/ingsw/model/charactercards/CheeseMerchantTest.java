package it.polimi.ingsw.model.charactercards;

import it.polimi.ingsw.model.GameModel;
import org.junit.Before;

import static org.junit.jupiter.api.Assertions.*;

class CheeseMerchantTest {
    private CheeseMerchant cheeseMerchant;

    @Before
    public void setUp(){
        GameModel gameModel = null;
        cheeseMerchant = new CheeseMerchant(gameModel);

        assertEquals(2, cheeseMerchant.getCurrentCost());
        assertFalse(cheeseMerchant.isCostIncreased());
    }

    /*
    - creare due nickname e metterli in una lista
    - istanziare gamemodel passando la lista con i due nickname
    - controllare che entrambi i giocatori abbiano almeno uno studente dello stesso colore. se si, spostare
        lo studente dall'entrata alla diningRoom del primo player
    - chiamare il metodo "updateProfessorManager" così da far controllare il professore di quel colore al player
    - spostare lo studente dall'entrata alla diningRoom del secondo studente
    - chiamare l'effetto di cheeseMerchant, passando il secondo player, per testarne il funzionamento
     */


}