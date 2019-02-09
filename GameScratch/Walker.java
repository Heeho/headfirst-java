import java.util.ArrayList;
import java.awt.Point;
import java.awt.geom.Point2D;

/*Walker
 * An engine that calculates the position of Things that are visible to caller (are in viewRange)
 * 
 * As of now, the "map" list is considered to contain thing locations on AxA map,
 * and the map is looped in toroid manner.
 * 
 * viewList is used to fill player gui pane or npc vision list 
 * 
 * 3D calc would be possible with code copy/paste for Z axis and i like it
 */

class Walker {
    ArrayList<Thing> viewList = new ArrayList<Thing>();
    ArrayList<Thing> remList = new ArrayList<Thing>();
    ArrayList<Thing> addList = new ArrayList<Thing>();

    ArrayList<Thing> map;   
    int mXY;
    int mS;
    
    int vR;

    //values related to caller here
    Point loc, dest;
    
    
    int aX, aY, bX, bY;
    int dX, dY;
    int err; //set to 0 when dest changes? meh

    //values related to visible things below
    int distX, distY;
    int x, y, tX, tY;
    
    public Walker(int viewRange, Point location, Point destination){
        map = Map.thgMap;
        mXY = GameScratch.MAP_XY;
        mS = mXY + 1; // mapSize
        
        vR = viewRange;
        
        loc = location;
        dest = destination;
    }
    
    public void walk() {
        remList.clear();
        addList.clear();

        int deltaX, deltaY;
        
        aX = (int) loc.getX();
        aY = (int) loc.getY();
        
        bX = (int) dest.getX();
        bY = (int) dest.getY();
            
        deltaX = bX - aX;
        deltaY = bY - aY;

        dX = calcD(deltaX);
        dY = calcD(deltaY);
    
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);

        recalcD(deltaX, deltaY);
        
        move();
        
        show();
    }
    
    private int calcD(int delta) {
        int d = 0;

        if(delta != 0) {
            if(delta < 0) {
                d = -1;
            } else {
                d = 1;
            }
        }        
        return d;
    }
    
    //Here comes my Bresenham's algorithm implementation
    private void recalcD(int delta1, int delta2) {
        if(delta1 != delta2) {
            if(delta1 > delta2) {
                dY = dA(delta1, delta2, dY);
            } else {
                dX = dA(delta2, delta1, dX);
            }
        }
        //3D appears, what we do? Keep calm & copypaste shit for XZ, YZ
    }
    
    //The Bresenham's Algorithm snippet
    private int dA(int a, int b, int d) {   //A as in Axis
        int add = 0;
        err += b;
        if(2*err >= a) {
            add = d;
            err -= a;
        }
        return add;
    }
    
    //Finally use our well-baked d's as intended
    private void move() {
        if(dX != 0 || dY != 0) {
            aX += dX;
            aY += dY;   
            wrap();
        }
    }
    
    //This wraps the caller's location over the map's edge, hense loops the map into a toroid
    private void wrap () {
        if(aX > mXY) {
            aX -= mS;
            bX -= mS;
        }
        
        if(aX < 0) {
            aX += mS;
            bX += mS;
        }
        
        if(aY > mXY) {
            aY -= mS;
            bY -= mS;
        }
        
        if(aY < 0) {
            aY += mS;
            bY += mS;
        }
        loc.setLocation(aX, aY);
        dest.setLocation(bX, bY);
        System.out.println("Arrived to " + aX + " " + aY);
    }
    
    //A method to 
    //1) Calculate the surrounding Things locations and 
    //2) Add/remove those if they're in/out of viewRange
    private void show() {
        moveRem();
        addThg();
    }
    
    //Calculate Thing locations, then add those that are out of range to remList
    private void moveRem() {
        int x, y;

        for(Thing thg: viewList) {    
            x = (int) thg.getLocation().getX();     
            y = (int) thg.getLocation().getY();
            
            thg.setLocation(x - dX, y - dY);

            tX = (int) thg.tLoc.getX();
            tY = (int) thg.tLoc.getY();

            distX = Math.abs(tX - aX);
            distY = Math.abs(tY - aY);

            if(
                (distX > vR && distX < (mS - vR)) ||
                (distY > vR && distY < (mS - vR))
            ){
                System.out.println("Thing " + thg.iD + " fades at " + x + " " + y + " on screen");
                remList.add(thg);
            }
        }//for
        
        //for (Thing thg: remList) {
        //    viewList.remove(thg);
        //}   
    }
    
    //adds Things to addList if they're in range
    private void addThg() {
        for(Thing thg: map) {           
            if(!viewList.contains(thg)) {
                distX = tX - aX;        
                distY = tY - aY;
                
                distX = wrapThg(distX);
                distY = wrapThg(distY);

                if(
                    Math.abs(distX) < vR &&
                    Math.abs(distY) < vR
                ){
                    thg.setLocation(vR + distX, vR + distY);
                
                    System.out.println("Thing " + thg.iD + " at " + tX + " " + tY);
                    
                    addList.add(thg);
                }//if
            }//if
        }//for
        
        //for(Thing thg: addList) {
        //  viewList.add(thg);
        //}
    }//addthg
    
    //Checks if Things are far enough to be visible over the map edge (toroid right?)
    private int wrapThg(int dist) {
        if(Math.abs(dist) > (mS - vR)) {
            if(dist > 0) {
                dist -= mS;
            } else {
                dist += mS;
            }
        }
        return dist;
    }
}