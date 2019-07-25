import java.util.ArrayList;
import java.util.concurrent.*;
import java.awt.Point;
import javax.swing.JLayeredPane;
import java.util.Timer;
import java.util.TimerTask;

abstract class Living extends Mobile {
    Gui gui;
    
    CopyOnWriteArrayList<Memo> memory = new CopyOnWriteArrayList<Memo>();     

    int attackRange;
    int viewRange;

    public void guiOn() {
        gui = new Gui(this);
    }

    public void setDestination(int[] p) {
        if(gui.screen != null) {
            for(int i = 0; i < dimensions; i++) {
                destination[i] = location[i] + p[i] - viewRange;
            }

            //             System.out.println("Destination is set to " + destination[0] + " " + destination[1]);
            //             System.out.println("Location is " + location[0] + " " + location[1] + " in sector " + sector[0] + " " + sector[1]);
        } else {
            for(int i = 0; i < dimensions; i++) {
                destination[i] = p[i];
            }
            //             System.out.println("destination= " + destination[0] + " " + destination[1]);
        }
    }
    
    public void updateMemo(int id, int danger) {
        boolean familiar = false;
        
        if(!memory.isEmpty()) {
            for(Memo m: memory) {
                if(m.iD == id) {                   
                    familiar = true;
                    
                    if(danger != 0) {
                        if(danger > 0) {
                            if(danger > m.danger) {
                                m.danger = danger;
                            }
                        } else {
                            if(danger < m.danger) {
                                m.danger = danger;
                            }
                        }
                    }
                    
                    //System.out.println(iD + " now estimates " + id + " danger as " + m.danger);
                    break;
                }
            }
            
            if(!familiar) {
                createMemo(id, danger);
            }        
        } else {
            createMemo(id, danger);
        }
    }
    
    public void createMemo(int id, int danger) {
        Memo m = new Memo();
        m.iD = id;
        m.danger = danger;
        memory.add(m);
        
        //System.out.println(iD + " now estimates " + id + "'s danger as " + danger);            
    }
    
    class Memo {  
        int iD;
        int danger;               
    }   
    
    public void initBehavior() {
        Timer t2 = new Timer();
        TimerTask behaviour = new TimerTask() {
            public void run() {
               behave();
            }
        };
        t2.scheduleAtFixedRate(behaviour, 15L, 120);    
    }
      
    private void behave() {
        int initialDanger = 0;
        
        boolean familiar = false;
        int sumDanger = 0;
        
        int dangerMin = 100;
        int dangerMax = 0;
        
        Thing biggerThreat = this;
        Thing lesserThreat = biggerThreat;
        /**
         * 1) sum up the danger
         * 2) >0 <0
         */
        
        //make it on proximity add/remove
        outerloop:
        for(Thing thg: proximity) {
            if(!thg.equals(this)) {
                if(!memory.isEmpty()) {
                    for(Memo m: memory) {                    
                        if(thg.iD == m.iD) {
                            sumDanger += m.danger;
                           
                            if(m.danger > 0) {
                                if(dangerMax < m.danger) {
                                   dangerMax = m.danger;
                                   biggerThreat = thg;
                                }
                                
                                if(m.danger < dangerMin) {
                                   dangerMin = m.danger;
                                   lesserThreat = thg;
                                }
                            }
                            break;
                        }
                    }
                }
            } else {
                //createMemo(thg.iD, initialDanger);
            }
        }             
        
        if(dangerMax > 0) {
            distress();
            if(sumDanger > 0) {
                flee(biggerThreat);
            } else {
                chase(lesserThreat);
                attack(lesserThreat);
                //shoot(destination);
            }            
        } else {
            wander();
        }
    }
   
    public void die(int id) {        
        updateMemo(id, 0);
        
        outerloop:
        for(Memo m: memory) {
            if(m.iD == id) {
                m.danger++;
                break outerloop;
            }
        }
        
        offMap();
        //Map.graceMap.get(sector[0]).get(sector[1]).remove(this);
        currentMap.putRand(this);
        stop();
    }
    
    private void distress() {
        int friendlyDanger = -1;
        Living listener = null;
        
        for(Thing thg: proximity) {
            if(
                thg.className() == this.className()
                &&
                !thg.equals(this)
            ) {
                listener = (Living) thg;
                
                for(Memo m: memory) {
                    listener.updateMemo(m.iD, m.danger);
                    listener.updateMemo(iD, friendlyDanger);
                }
            }
        }
    }
    
    private void flee(Thing threat) {
        int[] d = this.distance(threat);

        for(int i = 0; i < dimensions; i++) {
            this.destination[i] = this.location[i] - d[i];
        }        
    }  
    
    private void chase(Thing thg) {
        int[] d = this.distance(thg);
        
        Living target = (Living) thg;
        
        for(int i = 0; i < dimensions; i++) {
            destination[i] = location[i] + d[i];
        }
    }            
    
    public void attack(Thing thg) {
        int[] d;
        int attackReach;
        
        attackReach = (this.size + thg.size)/2 + attackRange;
            
        if(
            thg instanceof Living
            &&
            !thg.equals(this)
        ) {
            d = thg.distance(this);
            Living target = (Living) thg;
            
            if(
                Math.abs(d[0]) < attackReach
                && 
                Math.abs(d[1]) < attackReach
            ){
                //System.out.println(a.iD + " is hit by " + iD);
                target.die(iD);
            }
        }
    }    

    public void attack() {
        int[] d;
        int attackReach;
        
        outerloop:
        for(Thing thg: proximity) {
            attackReach = (this.size + thg.size)/2 + attackRange;
            
            if(
                thg instanceof Living
                &&
                !thg.equals(this)
            ) {
                d = thg.distance(this);
                Living target = (Living) thg;
                
                if(
                    Math.abs(d[0]) < attackReach
                    && 
                    Math.abs(d[1]) < attackReach
                ){
                    System.out.println(target.iD + " is hit by " + iD);
                    target.die(iD);
                    break outerloop;
                }
            }
        }
    }    
    
    public void shoot(int[] p) {
       Arrow a = new Arrow(this, p);
    }
    
    private void breed() {
        /**
         * 1) if can't get sufficient pack to kill biggerThreat, spawn more className()
         */
        
        int sumDanger = 0;
        
        for(Memo m:memory) {
            sumDanger += m.danger;
        }
        
        if(sumDanger > 0) {
            Wocar w = new Wocar();

            for(Memo m: memory) {
                w.memory.add(m);
            }
        } else {
            wander();
        }
    }
    
    private void bite(Thing prey) {
        int[] d = this.distance(prey);
        int biteReach = (this.size + prey.size)/2;
        
        chase(prey);
        
        if(d[0] <= biteReach && d[1] <= biteReach) {
            //System.out.println("BITE!" + prey.iD);
        }
    }
        
    private void sniff(Thing thg) {
    }
    
    private void hunt() {
    }
    
    public void initWanderer() {
        Timer t1 = new Timer();
        TimerTask wanderer = new TimerTask() {
            public void run() {
               wander();
            }
        };
        t1.scheduleAtFixedRate(wanderer, 0L, 100);
    }

    private void wander() {
        int[] p = Map.randLoc(viewRange/2);
        for(int i = 0; i < dimensions; i++) {
            destination[i] = location[i] + p[i] - viewRange/4;
        }
    } 
}
