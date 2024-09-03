import java.awt.Image;

public abstract class obstacles
{
    protected int width;
    protected int height;
    protected int y;
    protected int x;
    public obstacles(int x1, int y1, int w, int h )
    {
	width = w;
	height = h;
	y = y1;
	x = x1;
    }
    public int getY()
    {
	return y;
    }
    public int getX()
    {
	return x;
    }
    public int getHeight()
    {
	return height;
    }
    public int getWidth()
    {
	return width;
    }
    public boolean checkCollision(int xC, int yC, int pXLength, int pYLength)
	{
	    if((xC + pXLength)>x&&xC<(x+width))
	    {
		if((yC + pYLength)>y&&yC<(y+height))
		{
		    return true;
		}
	    }
	    return false;
	}
    public abstract Image getImage();
    public abstract hitbox[] getHitboxes();

}
