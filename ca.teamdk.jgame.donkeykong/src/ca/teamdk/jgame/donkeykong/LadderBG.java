package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;
import jgame.RectangleData;

public class LadderBG extends JGObject {

	public static final int COLLISION_ID = 16;
	public static final String NAME = "aladder";
	public static final String TILE = "l";
	public static final String IMAGE_FILENAME = "ladder1.png";
	public static int SPEED = 0;
	private DonkeyKong game;
	
	public LadderBG(DonkeyKong game, int x, int y) {
		super(NAME,true, x, y - 23, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		setBBox(1, 0, 26, 103);
		setBBoxMulti(new RectangleData(1, 0, 26, 80),
			new RectangleData(1, 50, 26, 60));
		this.game = game;
	}
}
