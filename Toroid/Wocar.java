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
public class Wocar extends Living {       
    //Wocar packAlpha;
    //Thing opponent;
    
    //boolean isLeader = true;
    //int packSize;
    
    public Wocar() {
        speed = 3;
        viewRange = 200;
        attackRange = 1;
        size = 8;
        color = Color.RED;
        isObstacle = true;
        
        initWalker();
        initBehavior();
        //initWanderer();
    }
}
//     private void contest(Wocar w) {}

//         familiar = true;
//         
//         if(m.danger > 0) {
//             if(hunger < m.danger) {
//                 System.out.println(this.iD + " flees from " + thg.iD);
//                 flee(thg);
//                 break outerloop;
//             } else {
//                 //System.out.println(this.iD + " attacks " + thg.iD);
//                 chase(thg);
//                 attack(thg);
//                 break outerloop;
//             }
//         } else {
//             if(m.danger < 0) {
//                 System.out.println(this.iD + " follows " + thg.iD);
//                 chase(thg);
//             }
//         } 
//         if(!familiar) {
//             memory.add(new Memo(thg.iD));
//             familiar = false;
//         }


//         public int danger() {
//             int d = 0;
//             
//             for(String s: encounterResults) {
//                 if(s == "death") {
//                     d++;
//                 }
//             }
//             
//             if(!encounterResults.isEmpty()) {
//                 d /= (int) encounterResults.size();
//             }
//             
//             return d;
//         }


//     private void behave() {
//         //System.out.println("Thing " + this.iD + " behaves");
//         /**
//          * PRIO: if there are non-Wocar Living, check attack/flee
//          * SECONDARY: if there are only Wocar, check contest/flee
//          * IDLE: if there are no Living, hunt
//          */
//         int danger = 50;
//         
//         /**
//          * 1)check Livings in proximity if they're dangerous or weak
//          * 2)flee immediately or set the weakest as target
//          * 3)chase target
//          */
//         outerloop:
//         for(Thing thg: proximity) {
//             if(
//                 thg instanceof Living
//                 &&
//                 thg.className() != this.className()
//             ){      
//                 if(!memory.isEmpty()) {
//                     for(Memo m: memory) {
//                         if(
//                             thg.className() == m.className
//                         ) {
//                             if(m.danger > 50) {
//                                 opponent = thg;
//                                 System.out.println(this.iD + " flees from " + thg.iD);
//                                 flee(thg);
//                                 break outerloop;
//                             } else {
//                                 if(m.danger < danger) {
//                                     opponent = thg;
//                                     //System.out.println(this.iD + " chases " + thg.iD);
//                                     danger = m.danger;
//                                 }
//                             }
//                         }                         
//                     }
//                 } else {
//                     opponent = thg;
//                     memory.add(new Memo(thg));
//                     System.out.println(this.iD + " chases " + thg.iD);
//                 }    
//                 
//                 chase(opponent);
//             }
//         }
// 
//             //         } else {        
//             //             if(!memory.isEmpty()) {
//             //                 outerloop:
//             //                 for(Memo m: memory) {
//             //                     if(opponent.getClass().getSimpleName() == m.className) {
//             //                         if(m.danger > 50) {
//             //                             System.out.println(this.iD + " flees from " + opponent.iD);
//             //                             flee(opponent);
//             //                             break outerloop;
//             //                         } else {
//             //                             
//             //                             System.out.println(this.iD + " chases " + opponent.iD);
//             //                             chase(opponent);
//             //                         }
//             //                     }                
//             //                 }
//             //             } else {
//             //                 System.out.println(this.iD + " chases " + opponent.iD);
//             //                 chase(opponent);
//             //             }  
// 
//        
//         //if(opponent.isLeader && packSize == opponent.packSize) {       
//         //    contest(opponent);       
//         //}
//     }