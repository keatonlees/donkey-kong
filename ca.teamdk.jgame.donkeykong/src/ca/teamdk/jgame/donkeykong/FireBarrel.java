package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class FireBarrel extends JGObject {
	public static final int COLLISION_ID = 64;
	public static final String NAME = "wFireBarrel";
	public static final String TILE = "f";
	public static String IMAGE_FILENAME = "oil1.png";
	public static int SPEED = 0;
	private DonkeyKong game;
	
	public boolean touchingGirder = false;
	
	public FireBarrel(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		this.game = game;
	}

	@Override
	public void move() {
		super.move();
		
	}	
}