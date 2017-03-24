/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core;

import java.util.ArrayList;

/**
 *
 * @author Sébastien
 */
public class Board {
    
    private ArrayList<Player> players;
    private ArrayList<Town> towns;

    public Board() {
        
        players = new ArrayList<>();
        towns = new ArrayList<>();
        
    }
        
}
