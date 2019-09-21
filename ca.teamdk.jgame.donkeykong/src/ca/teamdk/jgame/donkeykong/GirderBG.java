package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class GirderBG extends JGObject {
	
	public static final int COLLISION_ID = 8;
	public static final String NAME = "girder";
	public static final String TILE = "g";
	public static final String IMAGE_FILENAME = "tile_girder.png";
	public static int SPEED = 0;
	private DonkeyKong game;

	public GirderBG(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		setBBox(0, 0, 48, 2);
		this.game = game;
	}
}

