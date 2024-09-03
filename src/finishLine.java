import java.awt.Image;

import javax.swing.ImageIcon;

public class finishLine
{
    private int width;
    private int height;
    private int xC;
    private int yC;
    private int lvlCS;
    private ImageIcon image;
	hitbox hbox;
    public finishLine(int x, int y, int wi, int he, int cS, ImageIcon i)
    {
	xC = x;
	yC = y+he;
	width = wi;
	height = he;
	image = i;
    lvlCS = cS;
	hbox = new hitbox(wi/2,he/2,wi/2);

	// TODO Auto-generated constructor stub
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
    public int getLevelChangeState()
    {
        return lvlCS;
    }

    public int getHeight()
    {
	return height;
    }
    public hitbox getHitbox()
    {
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
