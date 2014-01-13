package com.example.bump.actions;

/**
 * Created by Arturo on 13/01/14.

public class CouleurBracelet {
    private Couleur color;

    public receiveColor(String s) {
        int indice = 1 ;
        String red = '';
        String green = '';
        String blue = '';
        while not(s.charAt(indice)==' ') {
            red = red + s.charAt(indice);
            indice = indice + 1;
        }
        color.setRouge((Integer)red);
        indice = indice + 1;

        while not(s.charAt(indice)==' ') {
            green = green + s.charAt(indice);
            indice = indice + 1;
        }
        color.setVert((Integer)green);
        indice = indice + 1;

        while not(s.charAt(indice)==' ') {
            blue = blue + s.charAt(indice);
            indice = indice + 1;
        }
        color.setBleu((Integer)blue);


    }
}
*/