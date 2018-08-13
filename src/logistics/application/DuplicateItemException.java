/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logistics.application;

/**
 *
 * @author tbarry
 */
public class DuplicateItemException extends Exception {
    
    public DuplicateItemException(String msg) {
        super(msg);
    }
}
