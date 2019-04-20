import javax.swing.SwingUtilities;

public class Toroid {
    //public static int MAP_XY = 2400;
    public static int MAP_ROW_COLUMN_COUNT = 5;
    public static int DIMENSIONS = 2;

    public static void main(String[] args) {
        Map.fillMap();
        //Map.animal.guiOn();
        Map.player.guiOn();
    }
}