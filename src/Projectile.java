import java.awt.Image;
import java.util.*;

public abstract class Projectile {

	protected int xC;// 1st x coordinate
	protected int yC;// 1st y coordinate
	protected int xLength;// how long the stickman is horizontally
	protected int yLength;// how tall the stickman is vertically
	protected int timeAlive;
	protected int vertMoveState = 0;
	protected int horizontalMoveState = 0;
	protected byte projectileType;
	protected ArrayList<hitbox[]> hitboxes;
   Projectile(int xCoord1, int yCoord,int width,int height, byte pT, int hN) {
		xC = xCoord1;
		yC = yCoord;
		xLength = width;
		yLength = height;
		projectileType = pT;
		timeAlive = 0;
		hitboxes = new hitboxNumbers().getHitboxes(hN);
	}
	protected abstract void setImage();
 	public abstract Image getImage();
	public abstract void setMoveStates(int x, int y);
	public abstract void setImageStates();
	// returns the x coordinate
	public int getX() {
		return xC;
	}
	public hitbox[] getHitboxes()
	{
	    return hitboxes.get(0);
	}
	// returns the y coordinate
	public int getY() {
		return yC;
	}

	// returns the length of the man
	public int getXLength() {
		return xLength;
	}

	// returns the height of the man
	public int getYLength() {
		return yLength;
	}
	public int horizontalMoveState()
	{
		return horizontalMoveState;
	}
	public int vertMoveState()
	{
		return vertMoveState;
	}
	// changes the x coordinate
	public void changeX(int x1) {
		xC = x1;
	}
	public byte getProjectileType()
	{
	    return projectileType;
	}
	protected void moveProjectile()
	{
		yC = yC+vertMoveState;
		xC = xC+horizontalMoveState;
	}
			
	public boolean checkCollision(int x, int y, int pXLength, int pYLength)
	{
	    if((x + pXLength)>xC&&x<(xC+xLength))
	    {
		if((y >(yC-yLength)&&(y-pYLength)<(yC)))
		{
		    return true;
		}
	    }
	    return false;
	}
	public boolean outOfBounds(int xL, int yL)
	{
		if(yC>yL-10||xC<10||xC+xLength>xL-10||yC-yLength<10)
		{
			return true;
		}
		return false;
	}
   //in levels as we decide and give the entity the game values we can call this method to automate its movements

}
