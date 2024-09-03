import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Wall
{
    private int xC;
    private int yC;
    private int height;
    private int width;
    private ImageIcon image;

    public Wall(int x1, int y1, int w, int h, ImageIcon im)
    {
	xC = x1;
	yC = y1;
	height = h;
	width = w;
	image = im;
    }

    public int getX()
    {// returns the x coordinates
	return xC;
    }

    public int getY()
    {// return the y coordinates
	return yC;
    }

    public int getHeight()
    {
	return height;
    }

    public int getWidth()
    {
	return width;
    }

    public Image getImage()
    {
	return image.getImage();
    }
}
