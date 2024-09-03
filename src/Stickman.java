
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;

public class Stickman{
	private int health = 3;// the amount of health the stickman has(hasn't been specified yet)
	private int xC;// 1st x coordinate
	private int yC;// 1st y coordinate
	private int js;// jumpstate
	private int xLength;// how long the stickman is horizontally
	private int yLength;// how tall the stickman is vertically
	private int clingHeight = 35;
	private int movingState;
	private int rState;
	private int lState;
	private int dMoveState;
	private boolean thisIsStupid = false;
	private int vState;
	private Sword sword = new Sword();
	private final int standingStillHeight = 58;
	private final int standingStillWidth = 52;
	private boolean facingRight;
	private final ImageIcon forwardFacingImage;
	private final ImageIcon backwardsFacingImage;
	private ImageIcon currentImage;
	private byte imageCheckingState;// 1:currentImage, 2:InAirRight 3:InAirLeft
	private final ImageIcon inAirRight;
	private final ImageIcon inAirLeft;
	private final ImageIcon midJumpRight;
	private boolean secondJump;
	private boolean justChanged;
	private final int swAttackEnd = 12;
	private final int swAttackEndLag = 10;
	private boolean isSwAttacking = false;
	private boolean justSwAttacked;
	private int swAttackTimer = 0;
	private int swAttackEndLagTimer = 0;
	private boolean justProjAttacked = false;
	private int projAttackTimer = 0;
	private int projAttackEndTimer = 40;
	private int projAttackEndLagTimer = 0;
	private int projAttackEndLag = 10;
	private boolean swordThrown = false;
	private boolean throwTheSword = false;
	private boolean isProjAttacking = false;
	private int swordTravelLength = 0;
	private int swAttackingWidth = 82;

	private final ImageIcon midJumpLeft;
	private hitbox[] currentHitboxes;
	private hitbox[] swordHitboxes;
	private ImageIcon[] attackLeft1 = {new ImageIcon("PML1AA.gif"),new ImageIcon("PML2AA.gif"),new ImageIcon("PML3AA.gif"), new ImageIcon("PML3AA.gif")};
	private ImageIcon[] attackLeft2 = {new ImageIcon("PML1AB.gif"),new ImageIcon("PML2AB.gif"),new ImageIcon("PML2AB.gif"), new ImageIcon("PML3AB.gif")};
	private ImageIcon[] attackLeft3 = {new ImageIcon("PML1AC.gif"),new ImageIcon("PML2AC.gif"), new ImageIcon("PML2AC.gif"), new ImageIcon("PML3AC.gif")};
	private ImageIcon[] attackRight1 = {new ImageIcon("PMR1AA.gif"),new ImageIcon("PMR2AA.gif"),new ImageIcon("PMR2AA.gif"), new ImageIcon("PMR3AA.gif")};
	private ImageIcon[] attackRight2 = {new ImageIcon("PMR1AB.gif"),new ImageIcon("PMR2AB.gif"), new ImageIcon("PMR2AB.gif"), new ImageIcon("PMR3AB.gif")};
	private ImageIcon[] attackRight3 = {new ImageIcon("PMR1AC.gif"),new ImageIcon("PMR2AC.gif"), new ImageIcon("PMR2AC.gif"), new ImageIcon("PMR3AC.gif")};
	private hitbox[] attackLeftHitboxes1 = {new hitbox(29.6,61,0)};//15.4
	private hitbox[] attackLeftHitboxes2 = {new hitbox(19.25,51.4,20),new hitbox(31.5,50.1,17)};
	private hitbox[] attackLeftHitboxes3 = {new hitbox(15,40,20)};
	private hitbox[] attackRightHitboxes1 = {new hitbox(15,61,0)};//15.4
	private hitbox[] attackRightHitboxes2 = {new hitbox(19.25,51.4,20), new hitbox(30.5,50.1,17)};
	private hitbox[] attackRightHitboxes3 = {new hitbox(47,40,20)};
	private final hitbox[] standingHitboxes = { new hitbox(20, 44.9, 15.35), new hitbox(20,27,10),
			new hitbox(11.5,9,8.5), new hitbox(28.5,9,8.5)};
	private ArrayList<hitbox[]> attackingHitboxes = new ArrayList<hitbox[]>();
	private int currentAttackHitboxNumber = 0;
	private ImageIcon[] runBackwardsAnimation = { new ImageIcon("runLeft1.png"), new ImageIcon("runLeft2.png"),
			new ImageIcon("runLeft3.png"), new ImageIcon("runLeft2.png") };
	private ImageIcon[] runForwardsAnimation = { new ImageIcon("runRight1.png"), new ImageIcon("runRight2.png"),
			new ImageIcon("runRight3.png"), new ImageIcon("runRight2.png") };
	private int runAnimationState;

	Stickman(int xCoord, int yCoord) throws IOException {
		xC = xCoord;
		yC = yCoord;
		forwardFacingImage = new ImageIcon("standRight.png");
		backwardsFacingImage = new ImageIcon("standLeft1.png");
		inAirRight = new ImageIcon("runRight1.png");
		inAirLeft = new ImageIcon("runLeft1.png");
		midJumpRight = new ImageIcon("standRight.png");
		midJumpLeft = new ImageIcon("standLeft1.png");
		xLength = standingStillWidth;
		yLength = standingStillHeight;
		movingState = 0;
		rState = 0;
		lState = 0;
		dMoveState = 0;
		vState = 0;
		runAnimationState = 0;
		currentImage = forwardFacingImage;
		imageCheckingState = 1;
		facingRight = true;
		secondJump = false;
		justChanged = false;
		currentHitboxes = standingHitboxes;
		attackingHitboxes.add(attackLeftHitboxes1);
		attackingHitboxes.add(attackLeftHitboxes2);
		attackingHitboxes.add(attackLeftHitboxes3);
		attackingHitboxes.add(attackRightHitboxes1);
		attackingHitboxes.add(attackRightHitboxes2);
		attackingHitboxes.add(attackRightHitboxes3);
	}

	public void setSecondJumpTrue() {
		secondJump = true;
	}
    public void changeHealth()
	{
		health--;
	}
    public void changeHealth(int x)
	{
		health = x;
	}
    public int getHealth()
    {
	return health;
    }
	public void setSecondJumpFalse() {
		secondJump = false;
	}

	public void startAttack(boolean swordA, boolean projA) {
		if (!swordThrown) {
			if (!justSwAttacked) {
				isSwAttacking = true;
			} else {
				if (!justProjAttacked) {
					isProjAttacking = true;
				}
			}
		}
	}

	public boolean getIsSwordAttack() {
		return isSwAttacking;
	}
	
	public boolean getIsProjAttacking()
	{
		return isProjAttacking;
	}
	public hitbox[] getSwordHitboxes()
	{
	    return attackingHitboxes.get(currentAttackHitboxNumber);
	}
	public byte isHittingObstacle(ArrayList<Object> n, ArrayList<Byte> type) {
		for (int t = 0; t < n.size(); t++) {
			if (type.get(t) == 7) {
				Projectile killer = ((Projectile) n.get(t));
				hitbox[] projHitboxes = killer.getHitboxes();
				int oX = killer.getX();
				int oY = killer.getY();
				for (int i = 0; i < standingHitboxes.length; i++) {
					for (int j = 0; j < projHitboxes.length; j++) {
						if (standingHitboxes[i].isColliding(projHitboxes[j], xC, yC, oX, oY)) {
							return 7;
						}
					}
				}
			}
			if (type.get(t) == 6) {
				Entity killer = ((Entity) n.get(t));
				hitbox[] entHitboxes = killer.getHitboxes();
				if (killer.getHitboxes() == null) {
					continue;
				}
				int oX = killer.getX();
				int oY = killer.getY();
				for (int i = 0; i < standingHitboxes.length; i++) {
					for (int j = 0; j < entHitboxes.length; j++) {
						if (standingHitboxes[i].isColliding(entHitboxes[j], xC, yC, oX, oY)) {
							return 6;
						}
					}
				}
			}
			if (type.get(t) == 5) {
				StartingLine killer = ((StartingLine) n.get(t));
				hitbox obsHitboxes = killer.getHitbox();
				int oX = killer.getX();
				int oY = killer.getY();
				for (int i = 0; i < standingHitboxes.length; i++) {
					if (standingHitboxes[i].isColliding(obsHitboxes, xC, yC, oX, oY)) {
						return 5;
					}
				}
			}
			if (type.get(t) == 4) {
				finishLine killer = ((finishLine) n.get(t));
				hitbox obsHitboxes = killer.getHitbox();
				int oX = killer.getX();
				int oY = killer.getY();
				for (int i = 0; i < standingHitboxes.length; i++) {
					if (standingHitboxes[i].isColliding(obsHitboxes, xC, yC, oX, oY)) {
						return 4;
					}
				}
			}
			if (type.get(t) == 3) {
				obstacles killer = ((obstacles) n.get(t));
				hitbox[] obsHitboxes = killer.getHitboxes();
				int oX = killer.getX();
				int oY = killer.getY();
				for (int i = 0; i < standingHitboxes.length; i++) {
					for (int j = 0; j < obsHitboxes.length; j++) {
						if (standingHitboxes[i].isColliding(obsHitboxes[j], xC, yC, oX, oY)) {
							return 3;
						}
					}
				}
			}
		}
		return 0;
	}

	// returns the x coordinate
	public int getX() {
		return xC;
	}

	// returns the y coordinate
	public int getY() {
		return yC;
	}

	// returns the length of the man
	public int getXLength() {
		return xLength;
	}

	// returns the height of the man
	public int getYLength() {
		return yLength;
	}

	public int getAttackWidth() {
		return swAttackingWidth;
	}

	// changes the x coordinate
	public void changeX(int x1) {
		xC = x1;
	}

	public Image currentImage() {
		return currentImage.getImage();
	}

	private void performAttack() {
		if (isSwAttacking) {
		    xLength = 62;
			swAttackTimer++;
			switch (swAttackTimer) {
			case 1:
				if(facingRight) {
					currentImage = attackRight1[runAnimationState%4];
					currentAttackHitboxNumber = 3;
				}
				else
				{
					currentImage = attackLeft1[runAnimationState%4];
					currentAttackHitboxNumber = 0;
					xC = xC - 10;
					thisIsStupid = true;
				}
				// currentImage == attackingImage 1
				break;
			case 3:
				if(facingRight) {
					currentImage = attackRight2[runAnimationState%4];
					currentAttackHitboxNumber = 4;
				}
				else
				{
					currentImage = attackLeft2[runAnimationState%4];
					xC = xC - 10;
					currentAttackHitboxNumber = 1;
				}
				// currentImage == attackingImage 2
				break;
			case 6:
				if(facingRight) {
					currentImage = attackRight3[runAnimationState%4];
					currentAttackHitboxNumber = 5;
				}
				else
				{
					currentImage = attackLeft3[runAnimationState%4];
					xC = xC - 10;
					currentAttackHitboxNumber = 2;
				}
				// currentImage == attackingImage 3
				break;
			}
			if (swAttackTimer == swAttackEnd) {
				swAttackTimer = 0;
				isSwAttacking = false;
				justSwAttacked = true;
				xLength = 35;
				currentAttackHitboxNumber = 0;
				if(thisIsStupid)
				{
				    thisIsStupid = false;
				    xC = xC + 20;
				}
			}
		} else {
			if (isProjAttacking) {
				projAttackTimer++;
				switch (projAttackTimer) {
				case 1:
					// currentImage == attackingImage 1
					break;
				case 10:
					// currentImage == attackingImage 2
					break;
				case 20:
					// currentImage == attackingImage 3
					break;
				case 30:
					// currentImage == attackingImage 4
					break;
				}
				if (projAttackTimer == projAttackEndTimer) {
					projAttackTimer = 0;
					isProjAttacking = false;
					swordLaunch();
					justProjAttacked = true;
				}
			}
		}
	}
	
	
	private void swordLaunch() {
		throwTheSword = true;
		swordThrown = true;
		
	}
	public boolean getThrowTheSword()
	{
		return throwTheSword;
	}
	private void endLag() {
		if (swAttackEndLagTimer < swAttackEndLag) {
			swAttackEndLagTimer++;
		} else {
			swAttackEndLagTimer = 0;
			justSwAttacked = false;
		}
		if (projAttackEndLagTimer < projAttackEndLag) {
			projAttackEndLagTimer++;
		} else {
			swAttackEndLagTimer = 0;
			justSwAttacked = false;
		}
	}

	public hitbox[] getHitbox() {
		return currentHitboxes;
	}

	public void setImage(boolean onTopWall, boolean isJumping) {
		if (justSwAttacked || justProjAttacked) {
			endLag();
		}
		else
		{
		performAttack();
		}
		if (!isSwAttacking && !isProjAttacking) {
			justChanged = false;
			byte pastImageCheckingState = imageCheckingState;
			if (movingState > 0) {
				facingRight = true;
			}
			if (movingState < 0) {
				facingRight = false;
			}
			if (!onTopWall) {
				if (facingRight) {
					imageCheckingState = 2;
					if (secondJump) {
						currentImage = midJumpRight;
					} else {
						currentImage = inAirRight;
					}
				} else {
					imageCheckingState = 3;
					if (secondJump) {
						currentImage = midJumpLeft;
					} else {
						currentImage = inAirLeft;
					}
				}
			} else {
				imageCheckingState = 1;
				if (movingState > 0 && vState == 0 && onTopWall) {
					runAnimationState++;
					currentImage = runForwardsAnimation[(runAnimationState / 20) % 4];
					imageCheckingState = 1;
					if (runAnimationState == 100000) {
						runAnimationState = 0;
					}
				} else {
					if (movingState < 0 && vState == 0 && onTopWall) {
						runAnimationState++;
						currentImage = runBackwardsAnimation[(runAnimationState / 20) % 4];
						imageCheckingState = 1;
						if (runAnimationState == 10000) {
							runAnimationState = 0;
						}
					} else {
						if (facingRight) {
							currentImage = forwardFacingImage;
						} else {
							currentImage = backwardsFacingImage;
						}
					}
				}
			}
			if (pastImageCheckingState != imageCheckingState) {
				justChanged = true;
			}
		}
	}

	// changes the y coordinate
	public void changeY(int y) {
		yC = y;
	}
	
	public Sword getSword()
	{
	    return sword;
	}

	public void setCoordinates() {
		xC = xC + movingState;
		yC = yC + vState;
	}

	// at what point of the jump is the man at(starting at JumpHeight and moving up
	// 1 less each frame)
	public int jumpState() {
		return js;
	}

	public void setMovingState() {
		movingState = rState + lState;
		rState = 0;
		lState = 0;
	}

	public void setRightMoving(int x) {
		rState = x;
	}

	public void setLeftMoving(int x) {
		lState = -x;
	}

	public int movingState() {
		return movingState;
	}

	public void changeMovingState(int c) {
		if (movingState < 0) {
			movingState = +c;
		} else {
			movingState = -c;
		}
		if (movingState <= c) {
			movingState = 0;
		}
	}

	public void zeroMovingState() {
		movingState = 0;
		rState = 0;
		lState = 0;
	}

	public void zeroVertMovingState() {
		vState = 0;
		dMoveState = 0;
	}

	public void zeroJumpState() {
		js = 0;
	}

	// change the jump state to a given value
	public void setJumpState(int j) {
		js = j;
	}

	// make the jump state go down one(is called every frame where the stickman is
	// moving up but no longer is jumping
	public void changeJumpState(int change) {
		if (js < 0) {
			js = 0;
		}
		if (!(js == 0)) {
			js = js - change;
		}
	}

	public void setDownMoving(int d) {
		dMoveState = d;
	}

	public void setVertMoveState() {
		vState = dMoveState - js;
	}

	public int getVertMoveState() {
		return vState;
	}

	public Image getImage() {
		return forwardFacingImage.getImage();
	}

	public boolean getSecondJump() {
		return secondJump;
	}

	// moves the character to the ground piece(is called when the stickman is very
	// close to the ground but is not actually on the same pixel of the ground)
	public void moveToGround(Ground test) {
		int x = test.getYCoords();
		yC = x - 1;

	}

	public void moveToRightWall(Wall test) {
		xC = test.getX() - 1 - xLength;
	}

	public void moveToLeftWall(Wall test) {
		xC = test.getX() + test.getWidth() + 1;
	}

	public void moveToTopWall(Wall test) {
		int y = test.getY();
		yC = y - 1;
	}

	public void moveToBottomWall(Wall test) {
		int y = test.getY() + test.getHeight() + yLength;
		js = Levels.FALL_SPEED;
		yC = y + 1;
	}
}
