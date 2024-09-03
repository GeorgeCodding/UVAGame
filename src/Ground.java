
import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Ground
{
    private int[] xC = new int[2];// contains the X coordinates
    private int yC;// contains the Y coorsinates
    private ImageIcon image;

    public Ground(int x1, int x2, int y1, int y2, ImageIcon im)
    {
	xC[0] = x1;
	xC[1] = x2;
	yC = y1;
	image = im;
    }

    public int[] getXCoords()
    {// returns the x coordinates
	return xC;
    }

    public int getYCoords()
    {// return the y coordinates
	return yC;
    }

    public Image getImage()
    {
	return image.getImage();
    }

}
