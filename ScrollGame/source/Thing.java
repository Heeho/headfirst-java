abstract class Thing extends JLabel {
	static int count;
	int iD;

	Color color = Color.BLACK;
	int picSize;	

	public Thing() {
		iD = count++;
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(color);
		g2d.fillRect(0, 0, picSize, picSize);
	}
}

class Player extends Thing {
	Point pLoc;
	Point pDest;

	int speed = 1;
	JLayeredPane screen;
	int viewRange = GameScratch.VIEW_SIZE / 2;
	private Color color = Color.BLACK;

	public Player(JLayeredPane view) {
		super();	
		picSize = 20;

		pLoc = Map.randLoc(GameScratch.MAP_XY);
		pDest = new Point(pLoc);
 		screen = view;

		Walker w = new Walker(screen, viewRange, pLoc, pDest);
		
		Timer t1 = new Timer();
		TimerTask walker = new TimerTask() {
			public void run() {
				w.walk();
			}
		};
		t1.scheduleAtFixedRate(walker, 0L, speed);

		screen.addMouseListener(new PlayerControl());
	}

	class PlayerControl extends MouseAdapter implements MouseListener {
		public void mousePressed(MouseEvent event) {	
			if(SwingUtilities.isRightMouseButton(event)) {
				pDest.setLocation(new Point(event.getPoint()));
			}
		}
	}//inner
}

class Tree extends Thing {
	private Color color = new Color(0x8fbc8f);
	private Point tLoc;

	public Tree(Point p) {
		super();
		picSize = 21;
		tLoc = p;
	}
}
