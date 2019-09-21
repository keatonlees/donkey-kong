package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Kong extends JGObject {

	public static final int COLLISION_ID = 4;
	public static final String NAME = "ykong";
	public static final String TILE = "k";
	public static String IMAGE_FILENAME = "kong_climb1.png";
	public static int SPEED = 0;
	private DonkeyKong game;
	
	private boolean kongSmiling = false;
	
	public Kong(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		this.game = game;
	}
	
	@Override
	public void move() {
		super.move();
		
		if ((y % 5 == 0) && (y > 300)) {
			if (getGraphic() == TILE) {
				setGraphic(TILE + "2");
			} else if (getGraphic() == TILE + "2") {
				setGraphic(TILE);
			}
		}
		
		if (y < 120) {
			setGraphic(TILE + "s");
		}

		if ((x == 55) && (!kongSmiling))  {
			setGraphic(TILE + "m");
			kongSmiling = true;
		}
	}
}