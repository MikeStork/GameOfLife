package app;

import java.util.ArrayList;
import java.util.HashMap;

import app.Entity;
import app.Projector;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("gol_IO").isEmpty()){
            System.out.println("Invalid arguments - chceck env variables");
            return;
        }
        FileParser fp = new FileParser(System.getenv("gol_IO"));
        fp.readFile();
        var HEIGHT = fp.GAME_HEIGHT;
        var WIDTH = fp.GAME_WIDTH;
        Entity[][] lista = new Entity[WIDTH][HEIGHT];
        for (int i = 0; i < fp.ENTRY_ENTITIES.length; i++) {
            int Y= fp.ENTRY_ENTITIES[i].Y;
            int X= fp.ENTRY_ENTITIES[i].X;
            lista[Y][X] = (new Entity(Y,X,true));
        }
//        @@@@ TEST DATA @@@@
//        lista[3][6] = (new Entity(3,6, true));
//        lista[8][12] = (new Entity(8,12,true));
//        lista[9][12] = (new Entity(9,12,true));
//        lista[10][12] = (new Entity(10,12,true));
//        lista[9][11] = (new Entity(9,11,true));
//        lista[14][12] = (new Entity(14,12,true));
//        lista[4][6] = (new Entity(4,6,true));
//        lista[3][7] = (new Entity(3,7,true));
//        lista[4][7] = (new Entity(4,7,true));
//        lista[2][1] = (new Entity(2,1,true));
//        lista[9][16] = (new Entity(9,16,true));
//        lista[9][17] = (new Entity(9,17,true));
//        lista[9][18] = (new Entity(9,18,true));
//        lista[10][16] = (new Entity(10,16,true));
//        lista[11][17] = (new Entity(11,17,true));
        Projector p = new Projector(WIDTH,HEIGHT, lista);
        try{
        p.start(fp.ITERATIONS,true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}