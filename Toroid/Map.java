import java.util.concurrent.*;
import java.util.*;
import java.awt.Point;
import java.awt.geom.Point2D;

class Map {
    public static ArrayList<ArrayList<CopyOnWriteArrayList<Thing>>> thingMap;
    
    static Living player;
    static Living animal;
    
    static int dimensions = Toroid.DIMENSIONS;
    
    static int viewRangeMax = 300;
    static int sizeMax = 100; //placeholder
    static int sectors = Toroid.MAP_ROW_COLUMN_COUNT;
    static int sectorSize, mapSize;
        
    public static void fillMap() {
        int treeAmount = sectors*2;
        int animalAmount = 6;

        sectorSize = viewRangeMax + sizeMax/2;
        
        mapSize = sectorSize * sectors;
        
        thingMap = new ArrayList<>(sectors);
        
        for(int x = 0; x < sectors; x++) {
            thingMap.add(new ArrayList<CopyOnWriteArrayList<Thing>>(sectors));
            //System.out.println("row " + x + " created");

            for(int y = 0; y < sectors; y++) {
                thingMap.get(x).add(new CopyOnWriteArrayList<Thing>());
                //System.out.println("column " + x + " row " + y + " created (contains Thing list)");
            }            
        }
        
        while(treeAmount != 0) {
            
            
            
            put(new Tree());
            treeAmount--;
        }    
        
        while(animalAmount != 0) {
            put(new Wocar());
            animalAmount--;
        }
        
        Stone s = new Stone();
        for(int i = 0; i < dimensions; i++) {
            s.location[i] = 200;
        }
        put(s); 
        
        player = new Thug();
        
        put(player);
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

        private static void put(Thing t) {
        t.sector = randLoc(sectors);
        t.location = randLoc(viewRangeMax);
        thingMap.get(t.sector[0]).get(t.sector[1]).add(t);
        System.out.println("Thing " + t.iD + " is put at sector " + t.sector[0] + " " + t.sector [1] + ", location " + t.location[0] + " " + t.location[1]);
    }
}
