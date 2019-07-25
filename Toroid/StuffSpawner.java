
/**
 * 0) Spawn it on map, set to walk around
 * 2) If no Livings in viewRangeMax, check chanse to spawn a random item/dungeon/portal/etc
 * 3) Monitor activity on map and adjust chanses for particular spawn cases
 */

import java.util.Timer;
import java.util.TimerTask;

public class StuffSpawner extends Mobile {
    Timer spawner = new Timer();
    int spawnRate = 3000;
    
    public StuffSpawner() {
        initWalker();
    }
    
    private void initSpawner() {
        TimerTask spawns = new TimerTask() {            
            public void run() {
                spawn();
            }
        };
        
        spawner.scheduleAtFixedRate(spawns, 0L, spawnRate);
    }
    
    private void spawn() {
        int[] d = null;
        
        for(Thing thg: proximity) {
            d = this.distance(thg);
            
            if(
                thg instanceof Living
                &&
                (d[0] < Map.viewRangeMax || d[1] < Map.viewRangeMax)
            ){
                return;
            } else {
                //spawn stuff
            }
        }
    }
}
