package model;

// Class that represents the grid behind the map to deal with characters positions and movements

import controller.ArenasController;
import view.Arenas;

public class MapGrid {

    private static final int MAX_OBSTACLES = 6 ;
    private static final int MAX_VAMPIRES = 20;
    private Things [][]map;
    private int width;
    private int height;
    private int [][] obstacles_positions;
    private int [][] vampires_positions;
    //private int [][] pistolero_position;
    private ArenasController arenas_controller;

    public MapGrid(Pistolero pistolero, ArenasController arena_controller, int new_width, int new_height) throws Exception {
        setWidth(new_width);
        setHeight(new_height);
        map = new Things[width][height];
        initialisation(map, pistolero);
        arenas_controller = arena_controller;
    }

    // Fill the grid with uninhabited cases
    private void initialisation(Things [][]map, Pistolero pistolero) throws Exception {
        for(int i = 0 ; i < width ; i++) {
            for(int j = 0 ; j < height ; j++) {
                map[i][j] = new Uninhabited();
            }
        }
        putObstacles();
        putVampires();
        putPistolero(pistolero);
    }

    private void putPistolero(Pistolero pistolero) throws Exception {
        map[Pistolero.getPrefPistoleroX()][Pistolero.getPrefPistoleroY()] = pistolero;
    }

    private void putVampires() {
        int x = 0, y = 0;
        vampires_positions = new int[MAX_VAMPIRES][2];
        for(int i = MAX_VAMPIRES ; i > 0 ; i--) {
            do {
                x = (int)(Math.random() * width/2);
                y = (int)(Math.random() * height);
            } while(!(map[x][y] instanceof Uninhabited));
            try {
                map[x][y] = new FemaleVampire(x,y,1);
                vampires_positions[i-1][0] = x;
                vampires_positions[i-1][1] = y;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Put MAX_OBSTACLES obstacles in the grid
    private void putObstacles() {
        int x = 0, y = 0;
        obstacles_positions = new int[MAX_OBSTACLES][2];
        for(int i = MAX_OBSTACLES ; i > 0 ; i--) {
            do {
                x = (int)(Math.random() * width);
                y = (int)(Math.random() * height);
            } while(!(map[x][y] instanceof Uninhabited) || (x == Pistolero.getPrefPistoleroX() && y == Pistolero.getPrefPistoleroY()));
            try {
                map[x][y] = new Obstacle(x,y);
                obstacles_positions[i-1][0] = x;
                obstacles_positions[i-1][1] = y;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Things getElement(int x, int y) { return map[x][y]; }

    public void setUnhabitated(int x, int y) { map[x][y] = new Uninhabited(); }

    public void setVampire(int x, int y, FemaleVampire v) { map[x][y] = v; }

    public int getXObstacles(int position) { return obstacles_positions[position-1][0]; }

    public int getYObstacles(int position) { return obstacles_positions[position-1][1]; }

    public int getXVampires(int position) { return vampires_positions[position-1][0]; }

    public int getYVampires(int position) { return vampires_positions[position-1][1]; }

    public void setObstaclesPositions(int i, int new_x, int new_y) {
        obstacles_positions[i][0] = new_x;
        obstacles_positions[i][1] = new_y;
    }

    public void leftCase(int x, int y) {
        map[x][y] = new Uninhabited();
    }

    public void moveOnCase(Things thing) {
        map[thing.getX()][thing.getY()] = thing;
    }

    public Things[][] getMap() { return map; }

    public int getWidth() { return width; }

    public void setWidth(int new_width) { width = new_width; }

    public int getHeight() { return height; }

    public void setHeight(int new_height) { height = new_height; }

    public static int getMaxObstacles() { return MAX_OBSTACLES; }

    public static int getMaxVampires() { return MAX_VAMPIRES; }
}
