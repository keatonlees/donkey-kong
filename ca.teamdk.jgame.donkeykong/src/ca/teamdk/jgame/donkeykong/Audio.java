package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Audio extends JGObject {
	
	public static final int COLLISION_ID = 64;
	public static final String NAME = "audio";
	public static final String TILE = "a";
	public static final String FILENAME = "startingTrack.wav";
	public static int SPEED = 0;

	public Audio(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
	}
}
