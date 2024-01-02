package app;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class FileParser {
    String FilePath;
    Integer GAME_WIDTH;
    Integer GAME_HEIGHT;
    Integer ITERATIONS;
    Entity[] ENTRY_ENTITIES;


    public FileParser(String filePath) {
        FilePath = filePath;
    }
    public void readFile(){
        try {
            BufferedReader bf = new BufferedReader(new FileReader(FilePath));
            String line;
            for (int i = 0;null != (line = bf.readLine()); i++) {
                switch (i){
                    case 0:
                        GAME_HEIGHT = Integer.valueOf(line);
                        break;
                    case 1:
                        GAME_WIDTH = Integer.valueOf(line);
                        break;
                    case 2:
                        ITERATIONS = Integer.valueOf(line);
                        break;
                    case 3:
                        ENTRY_ENTITIES = new Entity[Integer.parseInt(line)];
                        break;
                    default:
                        if(i-4 == ENTRY_ENTITIES.length){return;}
                        String[] cords = line.split(" ");
                        ENTRY_ENTITIES[i-4] =new Entity(Integer.parseInt(cords[0]),Integer.parseInt(cords[1]),true);
                        break;
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
