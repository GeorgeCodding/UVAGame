import java.awt.Image;

import javax.swing.ImageIcon;

public class HorizontalSpike extends obstacles
{
    private final static int height = 15;
    private final static int width = 8;
    private ImageIcon image;
    private hitbox[] hitboxes;
    public HorizontalSpike(int x, int y, int n, String ImageName)
    {
	super(x,y,n*width, height);
	hitboxes = new hitbox[n];
	for(int i = 0; i < hitboxes.length; i++)
	{
		hitboxes[i] = new hitbox((8 + (width*i)),6.7,6.2);
	}
	image = new ImageIcon(ImageName);
	// TODO Auto-generated constructor stub
    }
    public Image getImage()
    {
	return image.getImage();
    }
    public hitbox[] getHitboxes()
    {
    	return hitboxes;
    }
}
