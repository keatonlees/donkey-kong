package ca.teamdk.jgame.donkeykong;

import jgame.JGObject;

public class Barrels extends JGObject {
	public static final int COLLISION_ID = 32;
	public static final String NAME = "wbarrels";
	public static final String TILE = "d";
	public static String IMAGE_FILENAME = "barrel1.png";
	public static int SPEED = 0;
	private DonkeyKong game;

	public boolean touchingGirder = false;

	public Barrels(DonkeyKong game, int x, int y) {
		super(NAME, true, x, y, COLLISION_ID, TILE, SPEED, SPEED, 1d, 1d, -1);
		setBBox(15, 15, 5, 15);
		this.game = game;
	}

	@Override
	public void move() {
		super.move();
		
		if (xdir != 0) {
			if (x > 625) {
				touchingGirder = false;
				xdir = 0;

				if (y < 400) {
					game.makeBarrel = true;
				}

			} else if (x < 10) {
				touchingGirder = false;
				xdir = 0;
			}
		}

		if ((touchingGirder) && (x > 625)) {
			xdir = -4;
			setAnim("brl");
		} else if ((touchingGirder) && (x < 10)) {
			xdir = 4;
			setAnim("brr");
		}
	}

	@Override
	public void hit(JGObject obj) {
		if (and(obj.colid, GirderBG.COLLISION_ID)) {
			y -= 4;
			touchingGirder = true;
		} else if (and(obj.colid, FireBarrel.COLLISION_ID)) {
			remove();
		}
	}
}
