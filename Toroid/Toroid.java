import javax.swing.SwingUtilities;

public class Toroid {
    //public static int MAP_XY = 2400;
    public static int MAP_ROW_COLUMN_COUNT = 4;
    public static int DIMENSIONS = 2;
    public static int FPS = 16;
    
    static Map graceMap;
    
    public static void main(String[] args) {
        graceMap = new Map();
        graceMap.fillMap();
        //Map.animal.guiOn();
        Map.player.guiOn();
    }
}