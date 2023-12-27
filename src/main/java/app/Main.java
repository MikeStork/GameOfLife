package app;

import java.util.ArrayList;
import java.util.HashMap;

import app.Entity;
import app.Projector;

public class Main {
    public static void main(String[] args) {
        var HEIGHT = 20;
        var WIDTH = 30;
        Entity[][] lista = new Entity[WIDTH][HEIGHT];
        lista[3][6] = (new Entity(3,6, true));
        lista[8][12] = (new Entity(8,12,true));
        lista[9][12] = (new Entity(9,12,true));
        lista[10][12] = (new Entity(10,12,true));
        lista[9][11] = (new Entity(9,11,true));
        lista[14][12] = (new Entity(14,12,true));
        lista[4][6] = (new Entity(4,6,true));
        lista[3][7] = (new Entity(3,7,true));
        lista[4][7] = (new Entity(4,7,true));
        lista[2][1] = (new Entity(2,1,true));
        Projector p = new Projector(WIDTH,HEIGHT, lista);
        p.start();
    }
}