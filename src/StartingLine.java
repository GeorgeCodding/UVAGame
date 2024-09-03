import java.awt.Image;

import javax.swing.ImageIcon;

public class StartingLine
{
    private int width;

    private int lvlCS;
    private int height;
    private int xC;
    private int yC;
    private ImageIcon image;
    hitbox hbox;
    public StartingLine(int x, int y, int wi, int he, int cS, ImageIcon i)
    {
	xC = x;
	yC = y+he;
	width = wi;
	height = he;
	image = i;
    lvlCS = cS;
	hbox = new hitbox(50,50,50);
	// TODO Auto-generated constructor stub
    }
    public int getLevelChangeState()
    {
        return lvlCS;
    }
    public int getX()
    {
	return xC;
    }
    public int getY()
    {
	return yC;
    }
    public int getWidth()
    {
	return width;
    }
    public int getHeight()
    {
	return height;
    }
    public hitbox getHitbox() {
    	return hbox;
    }
    public Image getImage()
    {
	return image.getImage();
    }
    public boolean checkCollision(int x, int y, int pXLength, int pYLength)
	{
	    if((x + pXLength)>xC&&x<(xC+width))
	    {
		if((y >(yC-height)&&(y-pYLength)<(yC)))
		{
		    return true;
		}
	    }
	    return false;
	}
}
