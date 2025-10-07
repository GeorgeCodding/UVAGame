import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;

public class movingHittingEntity extends Entity
{
    private final static byte enemyNumber = 2;
    private final int attackCoolDown = 100;
    private int attackCoolDownTimer = 100;
    private final int AttackingRange = 60;
    private String[] imageNames;
    private ImageIcon[] attackLeftImages = new ImageIcon[4];
    private ImageIcon[] attackRightImages = new ImageIcon[4];
    private ImageIcon currentImage;
    private byte imageCheckingState;
    private byte attackingFrame = 0;
    private boolean isAttacking = false;
    private boolean isMovingLeft = false;
    private boolean isMovingRight = false;
    private String[][] images = { // the images of the dummy
	    { "images/attack1left.gif", "images/attack2left.gif", "images/attack3left.gif", "images/attack4left.gif", "images/attack1right.gif",
		    "images/attack2right.gif", "images/attack3right.gif", "images/attack4right.gif" }
		    };
    private boolean change;

    public movingHittingEntity(int xCoord1, int yCoord, int patrolDistance, byte imageNum) throws IOException
    {
	super(xCoord1, yCoord, enemyNumber, 1);
	imageNames = images[imageNum];
	for (int i = 0; i < 8; i++)
	{
		if (i < 4)
		{
		    attackLeftImages[i] = new ImageIcon(imageNames[i]);
		} else
		{
		    attackRightImages[i - 4] = new ImageIcon(imageNames[i]);
		}
	}
	yLength = 66;
	xLength = 88;
	imageCheckingState = 1;
	currentImage = attackLeftImages[0];
	change = true;
	// TODO Auto-generated constructor stub
    }

    private void moveEntity()
    {
	
    }
    
    public void runEntity(int pXCoord)
    {
	moveEntity();
	setImage(pXCoord);
	if (attackCoolDownTimer < attackCoolDown)
	{
	    attackCoolDownTimer++;
	}
	if (isAttacking)
	{
	    if (facingLeft(pXCoord))
	    {
		switch (attackingFrame)
		{
		case 0:
		    currentImage = attackLeftImages[0];
		    currentHitboxNumber = 0;
		    break;
		case 5:
		    currentImage = attackLeftImages[1];
		    currentHitboxNumber = 0;
		    break;
		case 10:
		    currentImage = attackLeftImages[2];
		    currentHitboxNumber = 1;
		    break;
		case 15:
		    currentImage = attackLeftImages[3];
		    currentHitboxNumber = 2;
		    break;
		}
	    } else
	    {
		switch (attackingFrame)
		{
		case 0:
		    currentImage = attackRightImages[0];
		    currentHitboxNumber = 3;
		    break;
		case 5:
		    currentImage = attackRightImages[1];
		    currentHitboxNumber = 3;
		    break;
		case 10:
		    currentImage = attackRightImages[2];
		    currentHitboxNumber = 4;
		    break;
		case 15:
		    currentImage = attackRightImages[3];
		    currentHitboxNumber = 5;
		    break;
		}
	    }
	    attackingFrame++;
	    if (attackingFrame == 20)
	    {
		attackingFrame = 0;
		isAttacking = false;
	    }
	} else
	    
	{
	    currentHitboxNumber = 0;
	}
    }

    private boolean facingLeft(int pX)
    {
	return xC + xLength / 2 > pX;
    }

    protected void setImage(int pX)
    {
	if (!isAttacking)
	{
	    if (!facingLeft(pX))
	    {
		currentImage = attackRightImages[0];
		currentHitboxNumber = 3;
	    } else
	    {
		currentImage = attackLeftImages[0];
		currentHitboxNumber = 0;
	    }
	}
    }

    public void startAttack()
    {
	if (!isAttacking && !(attackCoolDownTimer < attackCoolDown))
	{
	    attackCoolDownTimer = 0;
	    isAttacking = true;
	}
    }

    public Image getImage()
    {
	// TODO Auto-generated method stub
	return currentImage.getImage();
    }

}
