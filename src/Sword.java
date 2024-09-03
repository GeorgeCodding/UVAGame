
public class Sword {
	private int x = 0;
	private int y = 0;
	private int radius = 0;
	private int speed = 0;
	private int maxDistance = 0;
	private int distanceTraveled = 0;
	private hitbox[] hitboxes = {new hitbox(0,0,0)};

	public void moveSword(int playerX, int playerY)
	{
		
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getRadius()
	{
		return radius;
	}
	public hitbox[] getHitbox()
	{
	    return hitboxes;
	}
}
