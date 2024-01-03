package app.models;

import app.UpdateGameStateTask;
import app.persistence.CONSTANTS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Projector {
    static int WIDTH;
    static int HEIGHT;
    private boolean running;
    private final long OPTIMAL_TIME = 1000000000 / CONSTANTS.FPS_TARGET;
    int cycle = 0;
    Entity[][] ENTITY_MAP;

    Entity[][] NEXT_ENTITY_MAP;
    String[][] PRINT_MAP;

    public Projector(int width, int height, Entity[][] EntryEntities){
        WIDTH = width;
        HEIGHT = height;
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
        System.out.println(render(PRINT_MAP)+"|");
        System.out.println(Boundry());
    }
    /**
     * Game starting method, controlls game
     */
    public void start( Integer MAX_CYCLES, Boolean printMapState) throws InterruptedException{
        running = true;
        long lastUpdateTime = System.nanoTime();
        String boundry = Boundry();
        int NUM_OF_CORES = Integer.parseInt(System.getenv("NUMBER_OF_PROCESSORS"))-2;
        while (running) {
            long currentTime = System.nanoTime();
            long elapsedTime = currentTime - lastUpdateTime;
            ExecutorService executor = Executors.newFixedThreadPool(NUM_OF_CORES);

            CyclicBarrier barrier = new CyclicBarrier(NUM_OF_CORES);

            if (elapsedTime >= OPTIMAL_TIME) {
                this.cycle++;
                clearConsole();
                NEXT_ENTITY_MAP = new Entity[HEIGHT][WIDTH];
                int[] result = calculateSegmentLengths(WIDTH, NUM_OF_CORES);
                int currPos = 0;
                for (int i = 0; i < NUM_OF_CORES; i++) {
                    executor.submit(new UpdateGameStateTask(barrier,this,currPos,0,currPos+result[i],HEIGHT));
                    currPos+=result[i];
                }
                System.out.println("Awaiting for all threads to complete");
                executor.shutdown();
                if(executor.awaitTermination(1,TimeUnit.SECONDS)){
                System.out.println("All threads completed their tasks");
                }else{
                    System.out.println("Timeout occured - possible error");
                }
                for (int i = 0; i < HEIGHT-1; i++) {
                    for (int j = 0; j< WIDTH-1; j++){
                        if(null != NEXT_ENTITY_MAP[i][j]){
                            if(NEXT_ENTITY_MAP[i][j].alive){
                                PRINT_MAP[i][j] = Entity.SPRITE_ALIVE;
                            }else{
                                PRINT_MAP[i][j] = Entity.SPRITE_DEAD;
                            }
                        }
                        assert NEXT_ENTITY_MAP[i][j] != null;
                        ENTITY_MAP[i][j] = new Entity(i,j,NEXT_ENTITY_MAP[i][j].alive);
                    }
                }
                if(printMapState){
                    System.out.println(boundry);
                    System.out.println(render(PRINT_MAP)+"|");
                    System.out.println(boundry);
                }
                lastUpdateTime = currentTime;
            } else {
                long sleepTime = (OPTIMAL_TIME - elapsedTime) / 1000000;
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                }
            }

            if(this.cycle>=MAX_CYCLES){stop();}
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
    public static void clearConsole() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
            else {
                System.out.print("\033\143");
            }
        } catch (IOException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    public void update(Integer START_X, Integer START_Y, Integer END_X, Integer END_Y) {
            for (int j = START_Y; j < END_Y; j++) {
                for (int i = START_X; i < END_X; i++) {
                    NEXT_ENTITY_MAP[j][i] = new Entity(j,i,false);
                    if ((null) != ENTITY_MAP[j][i]){
                    Integer amountOfLivingCells = NEXT_ENTITY_MAP[j][i].CheckSurroundings(ENTITY_MAP);
                       if(ENTITY_MAP[j][i].alive && (amountOfLivingCells<2 || amountOfLivingCells>3)){
                           NEXT_ENTITY_MAP[j][i].alive = false;
                       }else if(ENTITY_MAP[j][i].alive && (amountOfLivingCells==3 || amountOfLivingCells==2)) {
                           NEXT_ENTITY_MAP[j][i].alive = true;
                       }else if(!ENTITY_MAP[j][i].alive && amountOfLivingCells==3){
                           NEXT_ENTITY_MAP[j][i].alive = true;
                       }
                    }
                }
            }

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
    static String Boundry(){
        ArrayList<String> boundryPart = new ArrayList<>();
        for (int i = 0; i < WIDTH; i++) {
            boundryPart.add("=");
        }

        return String.join("=",boundryPart);
    }
    /**
     * Calculates the lengths of segments when dividing a larger line into equal parts.
     *
     * @param totalLength The total length of the larger line.
     * @param partsToDivide           The number of equal parts to divide the larger line into.
     * @return An array of integers representing the lengths of each segment.
     */
    public static int[] calculateSegmentLengths(int totalLength, int partsToDivide) {
        int segmentLength = totalLength / partsToDivide;

        int remainingLength = totalLength % partsToDivide;

        int[] lengths = new int[partsToDivide];
        for (int i = 0; i < partsToDivide - remainingLength; i++) {
            lengths[i] = segmentLength;
        }
        for (int i = partsToDivide - remainingLength; i < partsToDivide; i++) {
            lengths[i] = segmentLength + 1;
        }

        return lengths;
    }

}
