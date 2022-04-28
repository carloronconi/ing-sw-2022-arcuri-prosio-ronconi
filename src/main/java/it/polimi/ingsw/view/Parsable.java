package it.polimi.ingsw.view;

/**
 * needed to pass lambda expression to showTextInputPrompt method in CliView class
 */
public interface Parsable {
    String parse(String text);
}
