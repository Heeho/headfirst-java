import java.util.Timer;
import java.util.TimerTask;

public interface Proactive {
    default void initProactive(Timer t) {
        TimerTask collides = new TimerTask() {            
            public void run() {
                action();
            }
        };
        
        t.scheduleAtFixedRate(collides, 0L, Toroid.FPS);
    }
    
    void action();
        //         if(collisionCheck(true)) {
        //             do stuff;
        //         }
    
    default boolean actionCondition(Mobile caller, Thing thg) {
        int[] d = caller.distance(thg);
        int collisionRange = (caller.size + thg.size)/2;
        
        if(
            Math.abs(d[0]) <= collisionRange
            &&
            Math.abs(d[1]) <= collisionRange
        ){
            return true;
        }        

        return false;
    }
}
