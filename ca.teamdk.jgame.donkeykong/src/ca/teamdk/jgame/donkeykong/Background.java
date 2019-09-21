package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Background extends JGObject {

	public static final int COLLISION_ID = 1;
	public static final String NAME = "background";
	public static final String TILE = "b";
	public static final String IMAGE_FILENAME = "bg_scene1.png";
	public static int SPEED = 0;

	public int bgCount = 1;

	public Background(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
	}

	@Override
	public void move() {
		super.move();
		switch (bgCount) {
		case 2:
			setGraphic(TILE + "3");
			break;
		case 3:
			setGraphic(TILE + "4");
			break;
		case 4:
			setGraphic(TILE + "5");
			break;
		case 5:
			setGraphic(TILE + "6");
			break;
		case 6:
			setGraphic(TILE + "7");
			break;
		case 7:
			setGraphic(TILE + "8");
			break;
		case 8:
			setGraphic(null);
			break;
		}
	}
}
