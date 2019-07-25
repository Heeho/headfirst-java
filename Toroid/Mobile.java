import java.awt.geom.Point2D;
import java.util.concurrent.*;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**Mobile
 * An abstract class that describes movement, collisions and else for mobile entities
 */
abstract class Mobile extends Thing {
    int[] destination;
    int speed = 1;
    Timer walk;
    
    CopyOnWriteArrayList<Thing> proximity = new CopyOnWriteArrayList<Thing>();    

    public Mobile() {      
        destination = location;
    }
    
    public void initWalker() {
        walk = new Timer();
        //System.out.println("Walker initialized for " + this); 
        
        TimerTask walker = new TimerTask() {            
            public void run() {               
                walk();
            }
        };
        walk.scheduleAtFixedRate(walker, 15L, speed);
    }
    
    public void setDest(int[] p) {
        for(int i = 0; i < dimensions; i++) {
            destination[i] = p[i];
        }

        //         //System.out.println("destination= " + destination);
    }
    
    int[] aA = new int[dimensions];
    int[] bA = new int[dimensions];
    int[] dA = new int[dimensions];
    int[] deltaA = new int[dimensions];

    int err; //Breseenham's algorithm part. Set to 0 when dest changes? meh    

    public void stop() {
        setDest(location);
    }
    
    public void walk() {
        ////System.out.println("walk()");
        for(int i = 0; i < dimensions; i++) {
            deltaA[i] = destination[i] - location[i];            
            dA[i] = calcD(deltaA[i]);            
            deltaA[i] = Math.abs(deltaA[i]);
        }
        
        recalcD(deltaA);
        
        proximity();
        collide();
        move(dA);     
    }    
   
    private int calcD(int deltaA) {
        int dA = 0;

        if(deltaA != 0) {
            if(deltaA < 0) {
                dA = -1;
            } else {
                dA = 1;
            }
        }
        return dA;
    }
    
    //Here comes the Bresenham's algorithm implementation
    private void recalcD(int[] deltaA) {
        if(deltaA[0] != deltaA[1]) {
            if(deltaA[0] > deltaA[1]) {
                dA[1] = bresA(deltaA[0], deltaA[1], dA[1]);
            } else {
                dA[0] = bresA(deltaA[1], deltaA[0], dA[0]);
            }
        }
        //3D appears, what we do? Keep calm & copypaste shit for XZ, YZ
    }
    
    //The Bresenham's Algorithm snippet
    private int bresA(int a, int b, int d) {   //A as in Axis
        int add = 0;
        err += b;
        if(2*err >= a) {
            add = d;
            err -= a;
        }
        return add;
    }

    private void move(int[] dA) {       
       if(dA[0] != 0 || dA[1] != 0) {
           isMoving = true;
           boolean shift = false;
           int[] currentSector = new int[dimensions];
            
           for(int i = 0; i < dimensions; i++) {
                location[i] += dA[i];
                currentSector[i] = (int) sector[i];
                
                if(location[i] >= currentMap.sectorSize) {
                    location[i] = 0;
                    destination[i] -= currentMap.sectorSize;
                    sector[i] += 1;
                    
                    if(sector[i] >= currentMap.sectors) {
                        sector[i] = 0;
                    }
                    
                    shift = true;                    
                } else {
                    if(location[i] < 0) {
                        location[i] += currentMap.sectorSize;
                        destination[i] += currentMap.sectorSize;
                        sector[i] -= 1;
                        
                        if(sector[i] < 0) {
                            sector[i] += currentMap.sectors;
                        }
            
                        shift = true;
                    }                    
                }
                ////System.out.println("Arrived to " + location[0] + " " + location[1] + " sector " + sector[0] + " " + sector[1]);
           }
            
           if(shift) {
                currentMap.map.get(sector[0]).get(sector[1]).add(this);
                currentMap.map.get(currentSector[0]).get(currentSector[1]).remove(this);
           }
       } else {
           isMoving = false;
       }
       
       if(dA[1] > 0) {
           angle = 0;
       } else {
           if(dA[1] < 0) {
               angle = 1;
           }
       }
    }
    
    private void proximity() {
        int area = 3;
        int[][] a = new int[dimensions][area];
        int value = 0;
        
        boolean remove = true;
        
        if(currentMap != null) {
            for(int i = 0; i < dimensions; i++) {
                for(int j = 0; j < area; j++) {
                    value = sector[i] - 1 + j;
                    
                    if(value >= 0 && value < currentMap.sectors) {
                        a[i][j] = value;
                    } else{
                        if(value < 0) {
                            a[i][j] = currentMap.sectors - 1;
                        } else {
                            if(value >= currentMap.sectors) {
                                a[i][j] = 0;
                            }
                        }
                    }
                }
            }
                    
            for(int i = 0; i < area; i++) {
                for(int j = 0; j < area; j++) {
                    if(currentMap != null) {
                        for(Thing thg: currentMap.map.get(a[0][i]).get(a[1][j])){
                            if(!proximity.contains(thg)) {
                                proximity.add(thg);
                                //if thg living?, behave()
                                //System.out.println("Thing " + thg.iD + " is in proximity");
                            }   
                        }
                    }
                }
            }
            
            for(Thing thg: proximity) {
                remove = true;
                
                outerloop:
                for(int i = 0; i < area; i++) {
                    for(int j = 0; j < area; j++) {            
                        if(currentMap.map.get(a[0][i]).get(a[1][j]).contains(thg)){
                            remove = false;
                            break outerloop;
                        }
                    }
                }
                
                if(remove) {
                    proximity.remove(thg);
                    //if thg living?, behave()
                    //System.out.println("Thing " + thg.iD + " is out of proximity");
                }
            }
        }
    }
    
    //collision check with Rigid objects
    private void collide() {
        int collisionRange;
        
        if(isObstacle) {
            for(Thing thg: proximity) {
                if(
                    thg.isObstacle
                    &&
                    !thg.equals(this)
                ) {
                    collisionRange = (thg.size + this.size)/2;
                    deltaA = this.distance(thg);
                    
                    if(    
                        Math.abs(deltaA[0]) <= collisionRange
                        &&
                        Math.abs(deltaA[1]) <= collisionRange
                    ){
                        if(deltaA[0] > deltaA[1]) {
                            if(Math.abs(deltaA[0]) > Math.abs(deltaA[1])) {
                                if(dA[0] == 1) {
                                    dA[0] = 0;
                                }
                            }
                            if(Math.abs(deltaA[0]) < Math.abs(deltaA[1])) {
                                if(dA[1] == -1) {
                                    dA[1] = 0;
                                }
                            }
                            if(Math.abs(deltaA[0]) == Math.abs(deltaA[1])) {
                                if(dA[0] != 0) {
                                    dA[1] = dA[0];
                                }
                                if(dA[1] != 0) {
                                    dA[0] = dA[1];
                                }
                            }
                        }
                        
                        if(deltaA[0] < deltaA[1]) {
                            if(Math.abs(deltaA[0]) > Math.abs(deltaA[1])) {
                                if(dA[0] == -1) {
                                    dA[0] = 0;
                                }
                            } 
                            if(Math.abs(deltaA[0]) < Math.abs(deltaA[1])) {
                                if(dA[1] == 1) {
                                    dA[1] = 0;
                                }
                            }                    
                            if(Math.abs(deltaA[0]) == Math.abs(deltaA[1])) {
                                dA[0] = 1 - 2*(int) (Math.random()*2);
                                dA[1] = dA[0];
                            }
                        }
                        
                        if(Math.abs(deltaA[0]) == Math.abs(deltaA[1])) {
                            dA[0] = 1 - 2*(int) (Math.random()*2);
                            dA[1] = -dA[0];
                        }
                    }
                }
            }
        }
    }
}