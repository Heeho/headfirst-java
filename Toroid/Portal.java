import java.util.*;
import java.util.concurrent.*;

public class Portal extends Thing implements Interactive {
    Map beyondMap;
    Portal exit;
    
    public Portal() {
        size = 24;
        isObstacle = false;
 
        //Map.fillMap(beyondMap);
        
        //currentMap.putRand(this);
        beyondMap = new Map();
        exit = new Portal(this);
        beyondMap.putRand(exit);
    }
        
    public Portal(Portal entrance) {
        size = 24;
        isObstacle = false;
        
        exit = entrance;
        beyondMap = entrance.currentMap;        
    }        
    
    public void interact(Mobile thg) {
        //open
        
        if(Math.abs(thg.location[1] - location[1]) <= 2) {
           teleport(thg);
        }
    }    
    
    private void teleport(Mobile thg) {
        //send Living to beyondMap, place at Portal location
        //         thg.offMap();
        //         
        //         thg.setSec(exit.sector);
        //         
        //         thg.location[0] = exit.location[0];
        //         thg.location[1] = exit.location[1] + 3 /*+ thg.size + exit.size*/;
        //             
        //         beyondMap.put(thg);
        //         thg.proximity.clear();
        //         thg.stop();
        //         
        System.out.println("Teleported!");
    }
}
