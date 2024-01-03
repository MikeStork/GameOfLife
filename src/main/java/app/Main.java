package app;

import app.models.Entity;
import app.models.Projector;
import app.persistence.FileParser;

public class Main {
    public static void main(String[] args) {
        if (System.getenv("gol_IO") == null && args.length<1){
            System.out.println("Invalid arguments - chceck env variables");
            return;
        }
        FileParser fp;
        if (args.length>0){
            fp = new FileParser(args[0]);
        }else {
            fp = new FileParser(System.getenv("gol_IO"));
        }
        fp.readFile();
        var HEIGHT = fp.getGAME_HEIGHT();
        var WIDTH = fp.getGAME_WIDTH();
        Entity[][] lista = new Entity[WIDTH][HEIGHT];
        for (int i = 0; i < fp.getENTRY_ENTITIES().length; i++) {
            int Y= fp.getENTRY_ENTITIES()[i].Y;
            int X= fp.getENTRY_ENTITIES()[i].X;
            lista[Y][X] = (new Entity(Y,X,true));
        }
        Projector p = new Projector(WIDTH,HEIGHT, lista);
        try{
        p.start(fp.getITERATIONS(),true);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}