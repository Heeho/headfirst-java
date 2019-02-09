import javax.swing.SwingUtilities;

public class GameScratch {
    public static int MAP_XY = 2400, VIEW_SIZE = 600;

    public static void main(String[] args) {
        Map.fillMap(MAP_XY);
        Player p1 = new Player();
    }
}