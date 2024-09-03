import java.util.ArrayList;
import java.util.Random;
import java.awt.Image;
import java.awt.Point;

import javax.swing.ImageIcon;

public abstract class Entity {
	protected int health;// the amount of health the entity has(hasn't been specified yet)
	protected int xC;// 1st x coordinate
	protected int yC;// 1st y coordinate
	protected int xLength;// how long the stickman is horizontally
	protected int yLength;// how tall the stickman is vertically
	protected int currentHitboxNumber = 0;
	protected int standingHitboxNumber = 0;
	protected byte entityType;
	protected ArrayList<hitbox[]> hitboxes;
   Entity(int xCoord1, int yCoord, byte eT, int hN) {
		xC = xCoord1;
		yC = yCoord;
		xLength = 0;
		yLength = 0;
		entityType = eT;
		hitboxes = new hitboxNumbers().getHitboxes(hN);
	}
	public hitbox[] getHitboxes()
	{
	    return hitboxes.get(currentHitboxNumber);
	}
 	public abstract Image getImage();
	// returns the x coordinate
	public int getX() {
		return xC;
	}

	// returns the y coordinate
	public int getY() {
		return yC;
	}

	// returns the length of the man
	public int getXLength() {
		return xLength;
	}
	public boolean isHit(hitbox[] pHitboxes, int playerX, int playerY)
	{
		for(int i = 0; i < hitboxes.get(standingHitboxNumber).length; i++)
		{
			for(int j = 0; j < pHitboxes.length; j++)
			{
				if(hitboxes.get(standingHitboxNumber)[i].isColliding(pHitboxes[j], xC, yC,playerX, playerY))
				{
					return true;
				}
			}
		}
		return false;
	}
	// returns the height of the man
	public int getYLength() {
		return yLength;
	}

	// changes the x coordinate
	public void changeX(int x1) {
		xC = x1;
	}
	public byte getEnitityType()
	{
	    return entityType;
	}
	public boolean checkCollision(int x, int y, int pXLength, int pYLength)
	{
	    if((x + pXLength)>xC&&x<(xC+xLength))
	    {
		if((y + pYLength)>yC&&y<(yC+yLength))
		{
		    return true;
		}
	    }
	    return false;
	}
   //in levels as we decide and give the entity the game values we can call this method to automate its movements

 
}
