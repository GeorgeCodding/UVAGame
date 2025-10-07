import java.awt.Image;
import java.awt.Point;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class stationaryShootingEntity extends Entity{

	private final static byte enemyNumber = 1;
	final int fireRate = 200;//in frames
	private int bulletNum = 0;
	private String[] imageNames;
	private ImageIcon standingLeftImage;
	private Point standingLeftImageBulletPoints;

	private ImageIcon standingLeftShootingImage;
	private Point standingRightImageBulletPoints;
	private ImageIcon standingRightImage;
	private ImageIcon standingRightShootingImage;
	private ImageIcon currentImage;
	private Point currentImageBulletPoints;
	private ArrayList<Point> standingImagePoints;
    private ArrayList<Point> currentImagePoints;
	private byte imageCheckingState;
	private boolean change;
	private byte bulletImageNum;
	private String[][] images = {                              // the images of the dummy
			{"images/attackgunleft1.gif","images/attackgunright1.gif","images/attackgunleft2.gif","images/attackgunright2.gif"},
			{"images/littleEnemyFootballPlayerLeft1.png","images/littleEnemyFootballPlayerRight1.png","images/littleEnemyFootballPlayerLeft2.png","images/littleEnemyFootballPlayerRight2.png"}
	};
	public stationaryShootingEntity(int xCoord1, int yCoord, byte imageNum,byte bIN) throws IOException {
		super(xCoord1, yCoord, enemyNumber,-2);
		imageNames = images[imageNum];
		standingLeftImage = new ImageIcon(imageNames[0]);
		standingRightImage = new ImageIcon(imageNames[1]);
		standingLeftShootingImage = new ImageIcon(imageNames[2]);
		standingRightShootingImage = new ImageIcon(imageNames[3]);
		imageCheckingState = 1;
		currentImage = standingLeftImage;
		currentImageBulletPoints = standingLeftImageBulletPoints;
		yLength = standingLeftImage.getIconHeight();
		xLength = standingLeftImage.getIconWidth();
		standingLeftImageBulletPoints = new Point(0,yLength/2);
		standingRightImageBulletPoints = new Point(xLength-1,yLength/2);
		change = true;
		bulletImageNum = bIN;
		// TODO Auto-generated constructor stub
	}
    public void runEntity(int playerX, int playerY)
    {
	setImage(playerX,playerY);
	bulletNum++;
    }
    public boolean getBulletState()
    {
	if(bulletNum==fireRate)
	{
	    bulletNum = 0;
	    return true;
	}
	return false;
    }
    public Point getBulletPoints()
    {
	return currentImageBulletPoints;
    }

	protected void setImage(int x, int y)
	{
	  double xDist = xC - x;
	  double yDist = yC - y;
	  double ratio = Math.abs(xDist/yDist);
	  if(xDist>0)//if to the left
	  {
		  currentImageBulletPoints = standingLeftImageBulletPoints;
			 if(!this.getBulletState()&&bulletNum>5)//if it's not shooting
			 {
				 currentImage = standingLeftImage;
			 }
			 else
			 {
				 currentImage = standingLeftShootingImage;
			 }

	  }
	  else
	  {
		  currentImageBulletPoints = standingRightImageBulletPoints;
		  if(!this.getBulletState()&&bulletNum>5)//if it's not shooting
		  {
			  currentImage = standingRightImage;
		  }
		  else
		  {
			  currentImage = standingRightShootingImage;
		  }
	  }
	}
	public Image getImage()
	{
	    // TODO Auto-generated method stub
	    return currentImage.getImage();
	}
	public byte getBulletImageNum()
	{
		return bulletImageNum;
	}
}
