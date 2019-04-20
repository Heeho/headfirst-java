PROJECT TITLE: Toroid

PURPOSE OF PROJECT: to learn language, information theory and software building in general on an example of 2D game

VERSION or DATE: 1.0, 2019

HOW TO START THIS PROJECT: Toroid -> main();

AUTHORS: Heeho

USER INSTRUCTIONS: use mouse


// import java.util.concurrent.CopyOnWriteArrayList;
// import java.util.ArrayList;
// import java.awt.Point;
// import java.awt.geom.Point2D;
// import javax.swing.*;
// 
// /*Walker
//  * An engine that calculates the position of the caller during the move action started by setting
//  * a destination (via player controls or AI decision)
//  * 
//  * 3D calc would be possible with code copy/paste for Z axis and i like it
//  */
// 
// class Walker {
//     CopyOnWriteArrayList<Thing> proximity = new CopyOnWriteArrayList<Thing>();   
//     Mobile caller;
//     ArrayList<Integer> sectorsX = new ArrayList<Integer>();
//     ArrayList<Integer> sectorsY = new ArrayList<Integer>();
//     
//     //values related to caller here  
//     int aX, aY, bX, bY;
//     int deltaX, deltaY;
//     int dX, dY;
//     int err; //Breseenham's algorithm part. Set to 0 when dest changes? meh
//     
//     public Walker(Mobile caller) {
//         this.caller = caller; 
//     }
// 
//     public void walk() {
//         //System.out.println("walk()");
//         aX = caller.location[0];
//         aY = caller.location[1];
//         
//         bX = caller.destination[0];
//         bY = caller.destination[1];
//             
//         deltaX = bX - aX;
//         deltaY = bY - aY;
// 
//         dX = calcD(deltaX);
//         dY = calcD(deltaY);
//     
//         deltaX = Math.abs(deltaX);
//         deltaY = Math.abs(deltaY);
// 
//         recalcD(deltaX, deltaY);
//         
//         proximity();
//         collide();
//         move();     
//     }
//     
//     private int calcD(int deltaA) {
//         int dA = 0;
// 
//         if(deltaA != 0) {
//             if(deltaA < 0) {
//                 dA = -1;
//             } else {
//                 dA = 1;
//             }
//         }        
//         return dA;
//     }
//     
//     //Here comes the Bresenham's algorithm implementation
//     private void recalcD(int deltaA, int deltaB) {
//         if(deltaA != deltaB) {
//             if(deltaA > deltaB) {
//                 dY = dA(deltaA, deltaB, dY);
//             } else {
//                 dX = dA(deltaB, deltaA, dX);
//             }
//         }
//         //3D appears, what we do? Keep calm & copypaste shit for XZ, YZ
//     }
//     
//     //The Bresenham's Algorithm snippet
//     private int dA(int a, int b, int d) {   //A as in Axis
//         int add = 0;
//         err += b;
//         if(2*err >= a) {
//             add = d;
//             err -= a;
//         }
//         return add;
//     }
//     
//     //Finally use our well-baked d's as intended
//     private void move() {
//         
//         
//         if(dX != 0 || dY != 0) {
//             //System.out.println("dX= " + dX + " dY= " + dY);
//             
//             aX += dX;
//             aY += dY;   
//             
//             wrap();
//             
//             caller.location[0] = aX;
//             caller.location[1] = aY;
//             caller.destination[0] = bX;
//             caller.destination[1] = bY;
//             
//             //System.out.println("Arrived to " + aX + " " + aY);
//         }
//     }
//     
//     /**TODO
//      * 1) rewrite using Map.wrapTor()
//      */
//     //This wraps the caller's location over the map's edge, hense loops the map into a toroid
//     private void wrap () {
//         if(aX >= Map.sectorSize) {
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
//             
//             caller.sector[0]++;
//             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
//             //if(++caller.sector[0] == Toroid.MAP_ROW_COLUMN_COUNT) {
//             //    caller.sector[0] = 0;
//             //}
//             
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
//             
//             aX -= Map.sectorSize;
//             bX -= Map.sectorSize;
//         }
//         
//         if(aX < 0) {
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
// 
//             --caller.sector[0];
//             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
//             //if(--caller.sector[0] < 0) {
//             //    caller.sector[0] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
//             //}
//             
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
//             
//             aX += Map.sectorSize;
//             bX += Map.sectorSize;
//         }
//         
//         if(aY >= Map.sectorSize) {
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
//             
//             caller.sector[0]++;
//             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
//             //if(++caller.sector[1] == Toroid.MAP_ROW_COLUMN_COUNT) {
//             //    caller.sector[1] = 0;
//             //}
//             
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
//             
//             aY -= Map.sectorSize;
//             bY -= Map.sectorSize;
//         }
//         
//         if(aY < 0) {
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
//             
//             --caller.sector[0];
//             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
//             // if(--caller.sector[1] < 0) {
//             //     caller.sector[1] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
//             // }
//             
//             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);            
//             
//             aY += Map.sectorSize;
//             bY += Map.sectorSize;
//         }
//     }
//     
//     //add Things in adjacent sectors to proximity list
//     private void proximity() {
//         //System.out.println("proximity()");               
//         boolean remove = true;
//         
//         sectorsX.clear();
//         sectorsY.clear();
//         
//         for(int i = 0; i < 3; i++) {
//             sectorsX.add(Map.wrapValue(caller.sector[0] - 1 + i, Toroid.MAP_ROW_COLUMN_COUNT));
//             sectorsY.add(Map.wrapValue(caller.sector[1] - 1 + i, Toroid.MAP_ROW_COLUMN_COUNT));
//         }
//         
//         for(Integer x: sectorsX) {
//             for(Integer y: sectorsY) {                            
//                 //System.out.println("iterating in sector:\nx=" + x + " y=" + y);
//                 
//                 for(Thing thg: Map.thingMap.get(x).get(y)) {    
//                     if(!proximity.contains(thg)) {
//                         proximity.add(thg);
//                         System.out.println("Thing " + thg.iD + " is in proximity");
//                     }
//                 }
//                 //System.out.println("proximity add complete");
//             }
//         }
//         
//         for(Thing thg: proximity) {
//             if(!(sectorsX.contains(thg.sector[0]) && sectorsY.contains(thg.sector[1]))) {
//                 proximity.remove(thg);
//                 System.out.println("Thing " + thg.iD + " is off proximity");
//             }
//         }
//     }
//     
//     //collision check with Rigid objects
//     private void collide() {
//         int oX, oY, range;
//         int rand;
//         
//         for(Thing thg: proximity) {
//             if(thg.isObstacle) {
//                 oX = (int) thg.location[0];
//                 oY = (int) thg.location[1];
//                 range = (thg.size + caller.size)/2;
//                 
//                 deltaX = oX - aX;
//                 deltaY = oY - aY;            
//     
//                 deltaX = Map.wrapThg(deltaX, range);
//                 deltaY = Map.wrapThg(deltaY, range);
//                 
//                 if(    
//                     Math.abs(deltaX) <= range
//                     &&
//                     Math.abs(deltaY) <= range
//                 ){
//                     if(deltaX > deltaY) {
//                         if(Math.abs(deltaX) > Math.abs(deltaY)) {
//                             if(dX == 1) {
//                                 dX = 0;
//                             }
//                         }
//                         if(Math.abs(deltaX) < Math.abs(deltaY)) {
//                             if(dY == -1) {
//                                 dY = 0;
//                             }
//                         }
//                         if(Math.abs(deltaX) == Math.abs(deltaY)) {
//                             if(dX != 0) {
//                                 dY = dX;
//                             }
//                             if(dY != 0) {
//                                 dX = dY;
//                             }
//                         }
//                     }
//                     
//                     if(deltaX < deltaY) {
//                         if(Math.abs(deltaX) > Math.abs(deltaY)) {
//                             if(dX == -1) {
//                                 dX = 0;
//                             }
//                         } 
//                         if(Math.abs(deltaX) < Math.abs(deltaY)) {
//                             if(dY == 1) {
//                                 dY = 0;
//                             }
//                         }                    
//                         if(Math.abs(deltaX) == Math.abs(deltaY)) {
//                             dX = 1 - 2*(int) (Math.random()*2);
//                             dY = dX;
//                         }
//                     }
//                     
//                     if(Math.abs(deltaX) == Math.abs(deltaY)) {
//                         dX = 1 - 2*(int) (Math.random()*2);
//                         dY = -dX;
//                     }
//                 }
//             }
//         }
//     }           
// 
// //     /*TEST*/
// //     public static void main(String[] args) {        
// //         Thug t = new Thug();
// //         
// //         try {
// //             Thread.sleep(200);
// //         } catch(Exception ex) {}
// //         
// //         t.sector[0] = 1;
// //         t.sector[1] = 1;
// //         
// //         t.location[0] = 1;
// //         t.location[1] = 2;
// //         
// //         t.destination[0] = 0;
// //         t.destination[1] = 0;
// //          
// //         try {
// //             Thread.sleep(200);
// //         } catch(Exception ex) {}
// //         
// //         t.destination[0] = -2;
// //         t.destination[1] = 0;
// //         
// //         try {
// //             Thread.sleep(200);
// //         } catch(Exception ex) {}
// //                 
// //         t.destination[0] = 4;
// //         t.destination[1] = -1;
// //         
// //         try {
// //             Thread.sleep(200);
// //         } catch(Exception ex) {}
// //         
// //         t.destination[0] = 5;
// //         t.destination[1] = 4;
// //         
// //         try {
// //             Thread.sleep(200);
// //         } catch(Exception ex) {}
// //         
// //         t.destination[0] = 0;
// //         t.destination[1] = 5;        
// //     }
// }

// import java.util.concurrent.CopyOnWriteArrayList;
// import java.util.ArrayList;
// import java.awt.Point;
// import java.awt.geom.Point2D;
// import javax.swing.*;
// 
// /*Seer
//  * An engine that calculates the position of Sprites that are visible to caller (are in viewRange) 
//  * 
//  * The "map" list is considered to contain Thing locations on AxA map,
//  * and the map is looped in toroid manner.
//  * 
//  * viewList is used to fill player GUI screen or NPC vision list 
//  */
// 
// class Seer {
//     JLayeredPane screen;
//     
//     CopyOnWriteArrayList<Thing> map;   
//     //CopyOnWriteArrayList<Thing> proximity
//     
//     ArrayList<Thing.Sprite> view = new ArrayList<Thing.Sprite>();       
//     ArrayList<Thing> things = new ArrayList<Thing>();   
//     
//     ArrayList<Thing.Sprite> spriteRemList = new ArrayList<Thing.Sprite>();
//     ArrayList<Thing> thingRemList = new ArrayList<Thing>();
//     
//     Living caller;
//     
//     int mS;
//     
//     int locX, locY, vR;
//     int deltaX, deltaY;
//     
//     public Seer(Living caller) {//int viewRange, Point location/*, CopyOnWriteArrayList<Thing> proximity*/) {
//         //this.proximity = proximity;
//         //map = Map.thingMap;
//         
//         this.caller = caller;
//         vR = caller.viewRange;
//         
//         mS = Toroid.MAP_ROW_COLUMN_COUNT * Map.sectorSize; // mapSize                
//     }        
//     
//     //A method to 
//     //1) Calculate the visible Things locations and 
//     //2) Add/remove Sprites for those Things, including those over the map edge
//     public void show() {                       
//         locX = caller.location[0];
//         locY = caller.location[1];
//         
//         addSprites();
//         moveSprites();
//         remSprites();
//     }
// 
//     /**TODO: 
//      * 1) put things.add part to proximity check in Walker
//      * 2) put the visual stuff to Gui
//      */
//     //adds Sprites to addList if they're in range
//     private void addSprites() {
//         int tX, tY;
//            
//         for(Thing thg: map) {    
//             tX = thg.location[0]; 
//             tY = thg.location[1];
//             
//             deltaX = tX - locX;        
//             deltaY = tY - locY;
//                 
//             deltaX = Map.wrapThg(deltaX, vR);
//             deltaY = Map.wrapThg(deltaY, vR);
// 
//             if(
//                 Math.abs(deltaX) < vR + thg.size &&
//                 Math.abs(deltaY) < vR + thg.size &&
//                 !things.contains(thg)
//             ){
//                 Thing.Sprite sprite;
//                 sprite = thg.createSprite();
//                 sprite.setLocation(vR + deltaX - sprite.picSize/2, vR + deltaY - sprite.picSize/2);
//                 
//                 view.add(sprite);
//                 things.add(thg);
//                                 
//                 //System.out.println("Thing " + thg.iD + " at " + tX + " " + tY + " added, sprite is in view at " + sprite.getLocation());
//                 
//                 if(screen != null) {
//                     SwingUtilities.invokeLater(new Runnable() {
//                         public void run() {                            
//                             sprite.setSize(screen.getPreferredSize());
//                             sprite.setOpaque(false);
//                             
//                             if(deltaX == 0 && deltaY == 0) {
//                                 screen.add(sprite, 5);                                
//                             } else {
//                                 screen.add(sprite, 0);
//                                 //JLayeredPane.DEFAULT_LAYER
//                             }
//                             
//                             screen.validate();
//                             
//                             //System.out.println("Thing " + thg.iD + " sprite added to screen");
//                         }
//                     });
//                 }
//             }
//         }
//     }
//     
//     //Relocate visible Sprites
//     private void moveSprites() {        
//         for(Thing.Sprite sprite: view) {
//             deltaX = sprite.location[0] - locX;
//             deltaY = sprite.location[1] - locY;
//             
//             deltaX = Map.wrapThg(deltaX, vR);
//             deltaY = Map.wrapThg(deltaY, vR);
//             
//             sprite.setLocation(vR + deltaX - sprite.picSize/2, vR + deltaY - sprite.picSize/2);
//             //System.out.println("Sprite " + sprite.iD + " location is " + sprite.getLocation());
//         }  
//     }
//   
//     //Calculate Sprite locations, then add those that are out of range to remList
//     private void remSprites() {          
//         spriteRemList.clear();
//         thingRemList.clear();
//         
//         int sX, sY;
//         
//         for(Thing.Sprite sprite: view) {    
//             sX = (int) sprite.getLocation().getX();
//             sY = (int) sprite.getLocation().getY();
// 
//             if(
//                 sX > 2*vR + sprite.picSize || sX < -2*sprite.picSize ||
//                 sY > 2*vR + sprite.picSize || sY < -2*sprite.picSize               
//             ){
//                 spriteRemList.add(sprite);
//                 
//                 if(screen != null) {                   
//                     SwingUtilities.invokeLater(new Runnable() {
//                         public void run() {                            
//                             screen.remove(sprite);
//                             //System.out.println("Thing " + sprite.iD + " sprite removed from screen");
//                         }
//                     });
//                 }
//             }
// 
//         }//for 
// 
//         for (Thing.Sprite sprite: spriteRemList) {
//             for(Thing thg: things) {
//                 if(thg.iD == sprite.iD) {
//                    thingRemList.add(thg);
//                 }
//             }
//             view.remove(sprite);
//             //System.out.println("Sprite " + sprite.iD + " removed from view");
//         }
//         
//         for (Thing thg: thingRemList) {
//             things.remove(thg);
//             //System.out.println("Thing " + thg.iD + " removed from things");
//         }
// 
//         spriteRemList.clear();
//         thingRemList.clear();
//     }
// //TEST
// //     public static void main(String[] args) {
// //         int viewRange = 2;
// //         Point location = new Point(2, 2);
// //         
// //         Thing t1 = new Tree(new Point(4, 0));
// //         Thing t2 = new Tree(new Point(0, 4));
// //         Thing t3 = new Tree(new Point(4, 4));
// // 
// //         Seer s = new Seer(null, viewRange, location);
// //         
// //         s.map = new ArrayList<Thing>();
// //         s.map.add(t1);
// //         s.map.add(t2);
// //         s.map.add(t3);
// //         
// //         s.mXY = 4;
// //         s.mS = 5;
// //         
// //         s.view = new ArrayList<Thing.Sprite>();
// //         
// //         System.out.println("Location is " + s.loc + " now");         
// //         s.show();
// // 
// //         s.loc.setLocation(0, 0);
// //         System.out.println("Location is " + s.loc + " now"); 
// //         s.show();
// //         
// //         s.loc.setLocation(1, 1);
// //         System.out.println("Location is " + s.loc + " now"); 
// //         s.show();
// //         
// //         s.loc.setLocation(2, 2);
// //         System.out.println("Location is " + s.loc + " now"); 
// //         s.show();
// //         
// //         s.loc.setLocation(3, 0);
// //         System.out.println("Location is " + s.loc + " now"); 
// //         s.show();
// //         
// //         s.loc.setLocation(3, 3);
// //         System.out.println("Location is " + s.loc + " now"); 
// //         s.show();      
// //     }
// }    

//             
//             distX = Math.abs(tX - aX);
//             distY = Math.abs(tY - aY);
// 
// 
//             
//             if(
//                 (distX > vR && distX < (mS - vR)) ||
//                 (distY > vR && distY < (mS - vR))
//             ){
//                 remList.add(thg);
//                 System.out.println("Thing " + thg.iD + " fades at " + tX + " " + tY);
//                 
//                 if(screen != null) {
//                     SwingUtilities.invokeLater(new Runnable() {
//                         public void run() {                            
// 
//                         }
//                     });
//                 }
//             }

    
    //     public static int wrapTor(Integer value, int limit) {
    //         if(value >= limit) {
    //             value -= limit;
    //             return 1;
    //         } else {
    //             if(value < 0) {
    //                 value += limit;
    //                 return -1;
    //             }
    //             return 0;
    //         }
    //     }
    

    
    //     public static int wrapValue(int num, int border) {
    //         if(num < 0) {
    //             num += border;
    //         } else {
    //             if(num >= border) {
    //                 num -= border;
    //             }
    //         }
    //         return num;
    //     }
    
    //        public static void fillMap_(int mapSize) {
    //         int treeAmount = mapSize/15;
    //         int animalAmount = 6;
    // 
    //         while(treeAmount != 0) {
    //             //thingMap.add(new Tree(randLoc(mapSize)));
    //             treeAmount--;
    //         }
    //         
    //         while(animalAmount != 0) {
    //             //thingMap.add(new Wocar(randLoc(mapSize), Wocar.speed, Wocar.viewRange));
    //             animalAmount--;
    //         }
    //         //thingMap.add(new Wocar(randLoc(mapSize), Wocar.speed, Wocar.viewRange));
    //         
    //         animal = new Wocar(randLoc(mapSize), Wocar.speed, Wocar.viewRange);
    //         //thingMap.add(animal);
    //         
    //         //thingMap.add(new Stone(new Point(0, 0)));
    //         
    //         Point k = new Point(200, 200);
    //         player = new Thug(/*randLoc(mapSize)*/k, Thug.speed, Thug.viewRange);
    //         //thingMap.add(player);
    //     }
    
        //Finally use our well-baked d's as intended
    //     private void move() {
    //         
    //         
    //         if(dX != 0 || dY != 0) {
    //             //System.out.println("dX= " + dX + " dY= " + dY);
    //             
    //             aX += dX;
    //             aY += dY;   
    //             
    //             wrap();
    //             
    //             caller.location[0] = aX;
    //             caller.location[1] = aY;
    //             caller.destination[0] = bX;
    //             caller.destination[1] = bY;
    //             
    //             //System.out.println("Arrived to " + aX + " " + aY);
    //         }
    //     }
    //     
    //     /**TODO
    //      * 1) rewrite using Map.wrapTor()
    //      */
    //     //This wraps the caller's location over the map's edge, hense loops the map into a toroid
    //     private void wrap () {
    //         if(aX >= Map.sectorSize) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             caller.sector[0]++;
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(++caller.sector[0] == Toroid.MAP_ROW_COLUMN_COUNT) {
    //             //    caller.sector[0] = 0;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aX -= Map.sectorSize;
    //             bX -= Map.sectorSize;
    //         }
    //         
    //         if(aX < 0) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    // 
    //             --caller.sector[0];
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(--caller.sector[0] < 0) {
    //             //    caller.sector[0] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aX += Map.sectorSize;
    //             bX += Map.sectorSize;
    //         }
    //         
    //         if(aY >= Map.sectorSize) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             caller.sector[0]++;
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(++caller.sector[1] == Toroid.MAP_ROW_COLUMN_COUNT) {
    //             //    caller.sector[1] = 0;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aY -= Map.sectorSize;
    //             bY -= Map.sectorSize;
    //         }
    //         
    //         if(aY < 0) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             --caller.sector[0];
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             // if(--caller.sector[1] < 0) {
    //             //     caller.sector[1] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
    //             // }
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);            
    //             
    //             aY += Map.sectorSize;
    //             bY += Map.sectorSize;
    //         }
    //     }
    
    //add Things in adjacent sectors to proximity list 
    
    //Finally use our well-baked d's as intended
    //     private void move() {
    //         
    //         
    //         if(dX != 0 || dY != 0) {
    //             //System.out.println("dX= " + dX + " dY= " + dY);
    //             
    //             aX += dX;
    //             aY += dY;   
    //             
    //             wrap();
    //             
    //             caller.location[0] = aX;
    //             caller.location[1] = aY;
    //             caller.destination[0] = bX;
    //             caller.destination[1] = bY;
    //             
    //             //System.out.println("Arrived to " + aX + " " + aY);
    //         }
    //     }
    //     
    //     /**TODO
    //      * 1) rewrite using Map.wrapTor()
    //      */
    //     //This wraps the caller's location over the map's edge, hense loops the map into a toroid
    //     private void wrap () {
    //         if(aX >= Map.sectorSize) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             caller.sector[0]++;
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(++caller.sector[0] == Toroid.MAP_ROW_COLUMN_COUNT) {
    //             //    caller.sector[0] = 0;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aX -= Map.sectorSize;
    //             bX -= Map.sectorSize;
    //         }
    //         
    //         if(aX < 0) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    // 
    //             --caller.sector[0];
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(--caller.sector[0] < 0) {
    //             //    caller.sector[0] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aX += Map.sectorSize;
    //             bX += Map.sectorSize;
    //         }
    //         
    //         if(aY >= Map.sectorSize) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             caller.sector[0]++;
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             //if(++caller.sector[1] == Toroid.MAP_ROW_COLUMN_COUNT) {
    //             //    caller.sector[1] = 0;
    //             //}
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);
    //             
    //             aY -= Map.sectorSize;
    //             bY -= Map.sectorSize;
    //         }
    //         
    //         if(aY < 0) {
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).remove(caller);
    //             
    //             --caller.sector[0];
    //             caller.sector[0] = Map.wrapValue(caller.sector[0], Toroid.MAP_ROW_COLUMN_COUNT);
    //             // if(--caller.sector[1] < 0) {
    //             //     caller.sector[1] = Toroid.MAP_ROW_COLUMN_COUNT - 1;
    //             // }
    //             
    //             Map.thingMap.get(caller.sector[0]).get(caller.sector[1]).add(caller);            
    //             
    //             aY += Map.sectorSize;
    //             bY += Map.sectorSize;
    //         }
    //     }
    
    //add Things in adjacent sectors to proximity list 
  