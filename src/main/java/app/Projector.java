package app;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Projector {
    static int WIDTH;
    static int HEIGHT;
    private boolean running;
    private final long OPTIMAL_TIME = 1000000000 / CONSTANTS.FPS_TARGET;
    public int cycle = 0;
    Entity[][] ENTITY_MAP;
    String[][] PRINT_MAP;

    Projector(int width, int height, Entity[][] EntryEntities){
        this.WIDTH = width;
        this.HEIGHT = height;
        ENTITY_MAP = new Entity[HEIGHT][WIDTH];
        PRINT_MAP = new String[HEIGHT][WIDTH];
        for (int i = 0; i < WIDTH; i++) {
            for (int j = 0; j < HEIGHT; j++) {
                if( null == EntryEntities[i][j]){
                    ENTITY_MAP[j][i] = new Entity(j,i,false);
                    PRINT_MAP[j][i] = Entity.SPRITE_DEAD;
                }else {
                    ENTITY_MAP[j][i] = new Entity(j,i,true);
                    PRINT_MAP[j][i] = Entity.SPRITE_ALIVE;
                }
            }
        }
    }
    /**
     * Game starting method, controlls game
     */
    public void start() {
        running = true;
        long lastUpdateTime = System.nanoTime();
        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastUpdateTime;

            if (elapsedTime >= OPTIMAL_TIME) {
                update();
                lastUpdateTime = currentTime;
            } else {
                long sleepTime = (OPTIMAL_TIME - elapsedTime) / 2000000;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }
        }
    }
    /**
     * Stops the game
     */
    public void stop() {
        running = false;
    }
    /**
     * Updates game state
     */
    private void update() {
        this.cycle++;
        Entity[][] NEXT_ENTITY_MAP = new Entity[HEIGHT][WIDTH];
            for (int j = 0; j < HEIGHT; j++) {
                for (int i = 0; i < WIDTH; i++) {
                    NEXT_ENTITY_MAP[j][i] = new Entity(j,i,false);
                    if ((null) != ENTITY_MAP[j][i]){
                    if(ENTITY_MAP[j][i].alive){
                        PRINT_MAP[j][i] = Entity.SPRITE_ALIVE;
                    }else{
                        PRINT_MAP[j][i] = Entity.SPRITE_DEAD;
                    }
                    Integer amountOfLivingCells = NEXT_ENTITY_MAP[j][i].CheckSurroundings(ENTITY_MAP);
                       if(ENTITY_MAP[j][i].alive && (amountOfLivingCells<2 || amountOfLivingCells>3)){
                           NEXT_ENTITY_MAP[j][i].alive = false;
                       }else if(ENTITY_MAP[j][i].alive && (amountOfLivingCells==3 || amountOfLivingCells==2)) {
                           NEXT_ENTITY_MAP[j][i].alive = true;
                       }else if(!ENTITY_MAP[j][i].alive && amountOfLivingCells==3){
                           NEXT_ENTITY_MAP[j][i].alive = true;
                       }
                    }else{
                        PRINT_MAP[j][i] = Entity.SPRITE_DEAD;
                    }
                }
            }
            System.out.println(render(PRINT_MAP)+"|");
            ArrayList<String> boundry = new ArrayList<>();
            for (int i = 0; i < WIDTH; i++) {
                boundry.add("=");
            }

            System.out.println(String.join("=",boundry));

            ENTITY_MAP = NEXT_ENTITY_MAP;
        }
    /**
     * Renders singular frame of game state
     * @param MAP Mapped positions of entities using 2dim array of strings
     * @return Rendered frame to be displayed
     */
    public static String render(String[][] MAP){
        String [] processed_rows = new String[HEIGHT];
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
            processed_rows[i] = String.join(" ",MAP[i]);
            }
        }
        return String.join("|\n",processed_rows);
    }
}
