package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Pauline extends JGObject {
	public static final int COLLISION_ID = 128;
	public static final String NAME = "pauline";
	public static final String TILE = "p";
	public static final String IMAGE_FILENAME = "pauline2.png";
	public static int SPEED = 0;
	private DonkeyKong game;

	public Pauline(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		this.game = game;
	}
}
