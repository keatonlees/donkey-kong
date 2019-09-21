package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Heart extends JGObject {

	public static final int COLLISION_ID = 8;
	public static final String NAME = "heart";
	public static final String TILE = "h";
	public static final String IMAGE_FILENAME = "heart.png";
	public static int SPEED = 0;
	private DonkeyKong game;

	public Heart(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		this.game = game;
	}
}
