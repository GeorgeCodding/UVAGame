import java.awt.Image;
import java.awt.Point;
import java.util.*;
import javax.swing.ImageIcon;

public class Bullet extends Projectile
{
	private String[] imageNames = {"football.png"};
	private ImageIcon image;
	private final int speed = 5;
	private hitbox[] w;
	Bullet(int xCoord1, int yCoord,int imageNum) {
		super(xCoord1, yCoord, 10/*width*/, 10/*height*/, (byte) 1, 0);
		image = new ImageIcon(imageNames[imageNum]);
		w = new hitbox[]{new hitbox(image.getIconWidth(), image.getIconHeight(), ((double)image.getIconHeight())/2)};
    }
	public void setImageStates()
	{
		
	}
	@Override
	protected void setImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return image.getImage();
	}
	@Override
	public void setMoveStates(int x, int y) {
		// TODO Auto-generated method stub
		double xDistance = (double)(x - xC);
		double yDistance = (double)(y - yC);
		double distance = Math.hypot((xDistance), (yDistance));
		double ratio = distance/speed;
		vertMoveState = (int)(yDistance/ratio);
		horizontalMoveState = (int)(xDistance/ratio);
	}

}
