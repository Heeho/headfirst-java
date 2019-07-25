import java.util.concurrent.*;
import java.util.*;
import java.awt.Point;
import java.awt.geom.Point2D;

class Map {
    public static ArrayList<Map> world = new ArrayList<Map>();
    public ArrayList<ArrayList<CopyOnWriteArrayList<Thing>>> map;
    
    static int dimensions = Toroid.DIMENSIONS;
    
    static int viewRangeMax = 600;
    static int sizeMax = 100; //placeholder
    
    static Living player;
    
    int sectors = Toroid.MAP_ROW_COLUMN_COUNT;
    int sectorSize, mapSize;

    public Map() {
        map = new ArrayList<ArrayList<CopyOnWriteArrayList<Thing>>>();
        makeMap();
        //world.add(this);
    }
    
    private void makeMap() {
        for(int x = 0; x < sectors; x++) {
            map.add(new ArrayList<CopyOnWriteArrayList<Thing>>(sectors));
            //System.out.println("row " + x + " created");

            for(int y = 0; y < sectors; y++) {
                map.get(x).add(new CopyOnWriteArrayList<Thing>());
                //System.out.println("column " + x + " row " + y + " created (contains Thing list)");
            }            
        }
    }
    
    public void fillMap() {
        int treeAmount = (int) (Math.pow(sectors, 2) * 3);
        int animalAmount = (int) (Math.pow(sectors, 2));

        sectorSize = viewRangeMax + sizeMax/2;
        
        mapSize = sectorSize * sectors;
                
        while(treeAmount != 0) {           
            putRand(new Tree());
            treeAmount--;
        }    
        
        while(animalAmount != 0) {
            putRand(new Wocar());
            animalAmount--;
        }
        
        Stone s = new Stone();
        
        s.setLoc(new int[]{200, 200});
        //         for(int i = 0; i < dimensions; i++) {
        //             s.location[i] = 200;
        //         }
        
        put(s); 
        
        Portal p = new Portal();
        putRand(p); 
        
        player = new Thug();
        putRand(player);        
        
        System.out.println("Player at " + player.sector[0] + " " + player.sector [1] + ", " + player.location[0] + " " + player.location[1]);
    }
    
    public static int[] randLoc(int range) {
        int d = Toroid.DIMENSIONS;
        int[] location = new int[d];
        
        for(int i = 0; i < d; i++) {
            location[i] = (int) (Math.random()*range);
        }
        return location;
    }

    public void put(Thing t) {
        t.currentMap = this;
        map.get(t.sector[0]).get(t.sector[1]).add(t);
        //System.out.println("Thing " + t.iD + " is put at sector " + t.sector[0] + " " + t.sector [1] + ", location " + t.location[0] + " " + t.location[1]);
    }
    
    public void putRand(Thing t) {
        t.currentMap = this;
        t.sector = randLoc(sectors);
        t.location = randLoc(viewRangeMax);
        map.get(t.sector[0]).get(t.sector[1]).add(t);       
    }        
}
