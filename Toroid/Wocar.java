import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;

/**Wocar
 * A simple packing carnivore animal
 * 
 * 15 minutes later: fuck, it's not that simple :|
 * 
 * TODO: bring all the bot logic up the inheritance tree and generalize it for all NPC's
 */
public class Wocar extends Living
{       
    //Wocar packAlpha;
    Thing opponent;
    
    //boolean isLeader = true;
    //int packSize;
 
    ArrayList<Memo> memory = new ArrayList<Memo>();
    
    public Wocar() {
        speed = 3;
        viewRange = 200;
        size = 8;
        color = Color.RED;
        isObstacle = true;
        
        //memory.add(new Memo(this));
        //memory.get(0).danger = 50;
        
        initWalker();
        initBehavior();
        //initWanderer();
    }

    private void initWanderer() {
        Timer t1 = new Timer();
        TimerTask wanderer = new TimerTask() {
            public void run() {
               wander();
            }
        };
        t1.scheduleAtFixedRate(wanderer, 0L, 100);
    }

    private void initBehavior() {
        Timer t2 = new Timer();
        TimerTask behaviour = new TimerTask() {
            public void run() {
               behave();
            }
        };
        t2.scheduleAtFixedRate(behaviour, 0L, 100);    
    }
    
    private void wander() {
        int[] p = Map.randLoc(viewRange/2);
        for(int i = 0; i < dimensions; i++) {
            destination[i] = location[i] + p[i] - viewRange/4;
        }
    }   

    private void behave() {
        //System.out.println("Thing " + this.iD + " behaves");
        /**
         * PRIO: if there are non-Wocar Living, check attack/flee
         * SECONDARY: if there are only Wocar, check contest/flee
         * IDLE: if there are no Living, hunt
         */
        int danger = 50;
        
        /**
         * 1)check Livings in proximity if they're dangerous or weak
         * 2)flee immediately or set the weakest as target
         * 3)chase target
         */
        outerloop:
        for(Thing thg: proximity) {
            if(
                thg instanceof Living
                &&
                thg.className() != this.className()
            ){      
                if(!memory.isEmpty()) {
                    for(Memo m: memory) {
                        if(
                            thg.className() == m.className
                        ) {
                            if(m.danger > 50) {
                                opponent = thg;
                                System.out.println(this.iD + " flees from " + thg.iD);
                                flee(thg);
                                break outerloop;
                            } else {
                                if(m.danger < danger) {
                                    opponent = thg;
                                    //System.out.println(this.iD + " chases " + thg.iD);
                                    danger = m.danger;
                                }
                            }
                        }                         
                    }
                } else {
                    opponent = thg;
                    memory.add(new Memo(thg));
                    System.out.println(this.iD + " chases " + thg.iD);
                }    
                
                chase(opponent);
            }
        }
    }
            //         } else {        
            //             if(!memory.isEmpty()) {
            //                 outerloop:
            //                 for(Memo m: memory) {
            //                     if(opponent.getClass().getSimpleName() == m.className) {
            //                         if(m.danger > 50) {
            //                             System.out.println(this.iD + " flees from " + opponent.iD);
            //                             flee(opponent);
            //                             break outerloop;
            //                         } else {
            //                             
            //                             System.out.println(this.iD + " chases " + opponent.iD);
            //                             chase(opponent);
            //                         }
            //                     }                
            //                 }
            //             } else {
            //                 System.out.println(this.iD + " chases " + opponent.iD);
            //                 chase(opponent);
            //             }  

       
        //if(opponent.isLeader && packSize == opponent.packSize) {       
        //    contest(opponent);       
        //}
   
    
    private void sniff(Thing thg) {
    }
    
    private void chase(Thing prey) {
        int[] d = this.distance(prey);
        int biteReach = (this.size + prey.size)/2;
        
        if(d[0] <= biteReach && d[1] <= biteReach) {
            bite(prey);
            //System.out.println("BITE!" + prey.iD);
        }
        
        for(int i = 0; i < dimensions; i++) {
            destination[i] = location[i] + d[i];
        }
    }
    
    private void bite(Thing prey) {
    }
    
    private void flee(Thing threat) {
        int[] d = this.distance(threat);
        
        for(int i = 0; i < dimensions; i++) {
            this.destination[i] = this.location[i] - d[i];
        }        
    }
    
    private void hunt() {
    }
    
    private void contest(Wocar w) {}
    
    private void die() {
        //get killer Class name, if there is already a memo, update it
        //m.encounterResults.add("death");
    }
    
    class Memo {
        ArrayList<String> encounterResults = new ArrayList<String>();
        
        String className;
        int danger = 0;
        
        public Memo(Thing thg) {
            className = thg.getClass().getSimpleName();
        }
        
        public int danger() {
            int d = 0;
            
            for(String s: encounterResults) {
                if(s == "death") {
                    d++;
                }
            }
            
            if(!encounterResults.isEmpty()) {
                d /= (int) encounterResults.size();
            }
            
            return d;
        }
    }
}
