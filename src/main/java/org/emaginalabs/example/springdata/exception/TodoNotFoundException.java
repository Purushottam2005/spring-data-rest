package org.emaginalabs.example.springdata.exception;

/**
 * User: jaluque
 * Date: 16/05/13
 * Time: 16:19
 */
public class TodoNotFoundException extends Exception {

    public TodoNotFoundException(String message) {
        super(message);
    }
}
