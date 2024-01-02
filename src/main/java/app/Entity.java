package app;

public class Entity {
    public static String color = CONSTANTS.BLUE;
    public static String SPRITE_ALIVE = "E";
    public static String SPRITE_DEAD = " ";
    public boolean alive = false;
    public int X;
    public int Y;

    Entity( int y,int x, boolean alive){
        this.X = x;
        this.Y = y;
        this.alive = alive;
    }

    public Integer CheckSurroundings(Entity[][] ENTITY_MAP) {
        Integer AmountOfLivingEntities = 0;
        Integer MAX_X_COORD = Projector.WIDTH-1;
        Integer MAX_Y_COORD = Projector.HEIGHT-1;
            if (ENTITY_MAP[(MAX_Y_COORD+(Y - 1))%MAX_Y_COORD][(MAX_X_COORD+(X - 1))%MAX_X_COORD].alive) {
                AmountOfLivingEntities++;
            }
            if (ENTITY_MAP[Y][(MAX_X_COORD+(X - 1))%MAX_X_COORD].alive) {
                AmountOfLivingEntities++;
            }
            if (ENTITY_MAP[(MAX_Y_COORD+(Y + 1))%MAX_Y_COORD][(MAX_X_COORD+(X - 1))%MAX_X_COORD].alive) {
                AmountOfLivingEntities++;
            }
            if (ENTITY_MAP[(MAX_Y_COORD+(Y - 1))%MAX_Y_COORD][X].alive) {
                AmountOfLivingEntities++;
            }
            if(ENTITY_MAP[(MAX_Y_COORD+(Y+1))%MAX_Y_COORD][(MAX_X_COORD+(X+1))%MAX_X_COORD].alive){
                AmountOfLivingEntities++;
            }
            if(ENTITY_MAP[(MAX_Y_COORD+(Y+1))%MAX_Y_COORD][X].alive){AmountOfLivingEntities++;}

            if (ENTITY_MAP[(MAX_Y_COORD+(Y - 1))%MAX_Y_COORD][(MAX_X_COORD+(X + 1))%MAX_X_COORD].alive) {
                AmountOfLivingEntities++;
            }

            if (ENTITY_MAP[Y][(MAX_X_COORD+(X + 1))%MAX_X_COORD].alive) {
                AmountOfLivingEntities++;
            }

        return AmountOfLivingEntities;
    }
}
