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
        if(X-1>-1 && Y-1>-1) {
            if (ENTITY_MAP[Y - 1][X - 1].alive) {
                AmountOfLivingEntities++;
            }
        }
        if(X-1>-1) {
            if (ENTITY_MAP[Y][X - 1].alive) {
                AmountOfLivingEntities++;
            }
        }
        if(X-1>-1 & Y+1<Projector.HEIGHT-1) {
            if (ENTITY_MAP[Y + 1][X - 1].alive) {
                AmountOfLivingEntities++;
            }
        }
        if(Y-1>-1) {
            if (ENTITY_MAP[Y - 1][X].alive) {
                AmountOfLivingEntities++;
            }
        }
        if(X+1<Projector.WIDTH-1 && Y+1<Projector.HEIGHT-1){
            if(ENTITY_MAP[Y+1][X+1].alive){
                AmountOfLivingEntities++;
            }
        }
        if(Y+1<Projector.HEIGHT-1){
            if(ENTITY_MAP[Y+1][X].alive){AmountOfLivingEntities++;}
        }
        if(X+1<Projector.WIDTH-1 && Y-1>-1) {
            if (ENTITY_MAP[Y - 1][X + 1].alive) {
                AmountOfLivingEntities++;
            }
        }
        if(X+1<Projector.WIDTH-1) {
            if (ENTITY_MAP[Y][X + 1].alive) {
                AmountOfLivingEntities++;
            }
        }
        return AmountOfLivingEntities;
    }
}
