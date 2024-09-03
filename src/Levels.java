import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Levels extends JPanel
{
    private final int MOVEMENT_SPEED = 8;// how fast the character moves right and left
    private final int JUMP_HEIGHT = 16;// how high the character jumps
    public final static int FALL_SPEED = 8;
    private final int FAST_FALL_SPEED = 15;
    private final int JUMP_STATE_CHANGE = 1;
    private final int JUMP_TIMER = 10;
    private final int DOUBLE_JUMP_TIMER = 5;
    private final int DOUBLE_JUMP_HEIGHT = 20;
    private final int heartDisplayCoordX = 10;
    private final int heartDisplayCoordY = 10;
    private static final int STARTSCREEN = 0;// value for game start
    private static final int GAME = 1;// value for game during play
    private static final int SCORES = 2;// value for game start
    private final int SCREEN_X_SIZE = 1280;
    private final int SCREEN_Y_SIZE = 720;
    private final int WALL_JUMP_HEIGHT = 20;
    private final int WALL_JUMP_SPEED = 8;
    private final int WALL_JUMP_RANGE = 3;
    private final int WALL_JUMP_TIMER = 20;// end lag after wall jumping
    private final int BULLET_LIFESPAN = 250;
    private final Map map;// the map which the level is on
    private final int StartX;// the starting coordinates of the player
    private final int StartY;// the starting coordinates of the player
    private final int playerHealthMax = 3;

	private int finishLinesNumber = 0;
    private int pastVertMoveState = 0;
    private String collidedWidth;
    private int Score;
    private boolean mRight;// If the player is pressing the right movement button
    private boolean mLeft;// If the player is pressing the left movement button
    private boolean jump;// If the player is jumping
    private boolean jumpReset;// If the person has stopped holding down the jump button
    private boolean mDown;
    private boolean playerAttack;
    private boolean playerSwordAttack = false;
    private boolean playerProjAttack = false;
    private boolean playerIsProjAttacking = false;
    private boolean swordIsThrown = false;
    private boolean onTopWall;
    private boolean onRightWall;
    private boolean onLeftWall;
    private boolean clingingToLeftWall;
    private boolean clingingToRightWall;
    private boolean canWallJump;
    private boolean firstJump;
    private boolean justRightWallJumped;
    private boolean justLeftWallJumped;
    private boolean wallJump;

	private boolean pointChange = false;
	private int numPointChange = 0;
    private boolean NewH;
    private boolean LoadH;
    private boolean ExitH;
    private boolean OptionH;
    private int canChangeTimer;
    private int changeState;// 0 means no, 1 means forwards, 2 means backwards
	private int changeSide;
    private int jumpTimer;// how long the jump has been held down
    private int jumpAmount;// how many times the person has jumped
    private int wallJumpTime;
    private int mouseX;
    private int mouseY;
    private static int gameMode = 0;// default value for the startscreen which will change as the player inputs
    private Stickman player;
    private ArrayList<Ground> ground;// an arraylist with all ground objects
    private ArrayList<Wall> wall;
    private ArrayList<obstacles> obstacles;
    private ArrayList<finishLine> finishLines;
    private ArrayList<StartingLine> startingLines;
    private ArrayList<Entity> entities;
    private SparseMatrix<String> everything;// a sparsematrix which contains all objects on the boards
    private ArrayList<Bullet> bullets = new ArrayList<Bullet>();
    private ArrayList<Integer> currentBulletNumbers = new ArrayList<Integer>();
    private ArrayList<Object> harmfulThings = new ArrayList<Object>();
    private ArrayList<Byte> harmfulThingsByteNumbers = new ArrayList<Byte>();
    private Image startScreen;// the image of the start screen
    private Image Gear;// Image for gear icon
    private Image sky;// background for level 1
    private final Image fullHeart = new ImageIcon("fullheart.png").getImage();
    private final Image emptyHeart = new ImageIcon("emptyheart.png").getImage();
    private boolean needToBeReset;
    private ArrayList<Point> bulletImagePoints;

    public Levels(Map m, int startingSide) throws IOException
	{
	this.setPreferredSize(new Dimension(SCREEN_X_SIZE, SCREEN_Y_SIZE));
	startScreen = new ImageIcon("MainMenuScreen.png").getImage();
	sky = m.getBackgroundImage().getImage();
	Gear = new ImageIcon("Gear Icon.png").getImage();
	jump = false;
	mRight = false;
	mLeft = false;
	playerAttack = false;
	jumpTimer = 0;
	jumpAmount = 0;
	map = m;
	ground = map.getGround();
	startingLines = map.getStartingLines();
	obstacles = map.getObstacles();
	everything = m.getMatrix();
	wall = m.getWall();
	finishLines = m.getFinishLines();
	entities = m.getEntities();
	onTopWall = false;
	onRightWall = false;
	onLeftWall = false;
	canWallJump = false;
	clingingToRightWall = false;
	clingingToLeftWall = false;
	NewH = false;
	LoadH = false;
	ExitH = false;
	mouseX = 0;
	mouseY = 0;
	changeSide = 0;
	collidedWidth = "";
	wallJumpTime = 0;
	Score = 0;
	firstJump = false;
	canChangeTimer = 0;
	needToBeReset = false;
	if (startingSide >= 1)
	{
	    try
	    {
		player = new Stickman(startingLines.get(0).getX() + startingLines.get(0).getWidth() + 15,
			startingLines.get(0).getY() + startingLines.get(0).getHeight());
	    } catch (Exception e)
	    {
			player = new Stickman(map.startingX(),map.startingY());
	    }
	} else
	{
	    if (startingSide <= -1)
	    {
		try
		{
		    player = new Stickman(finishLines.get(0).getX() - (15 + 34),
			    finishLines.get(0).getY() + finishLines.get(0).getHeight() - 1);
		} catch (IOException e)
		{
			player = new Stickman(map.startingX(),map.startingY());
		    // TODO Auto-generated catch block
		}
	    } else
	    {
		try
		{
		    player = new Stickman(map.startingX(), map.startingY());
		} catch (IOException e)
		{
		    // TODO Auto-generated catch block
		    System.exit(0);
		    ;
		}
	    }
	}
	StartX = player.getX();
	StartY = player.getY();

    }

    public void run()
    {
	if (gameMode != 0)
	{
	    if (player.getY() > SCREEN_Y_SIZE || player.getX() < 0 || player.getX() > SCREEN_X_SIZE
		    || player.getY() - player.getYLength() < 0)
	    {// Resets the player if they fall out of the
	     // map
		reset();
	    }
	    if (canChangeTimer != 50)
	    {
		canChangeTimer++;
	    }
	    // checkEntityCollisions();
	    moveEntities();
	    checkForCheckpoints();
	    isHittingObstacle();
	    // checkEntityCollisions();
	    setMoveState();
	    movePlayer();
		playerActions();
	    if (player.getY() > SCREEN_Y_SIZE || player.getX() < 0 || player.getX() > SCREEN_X_SIZE
		    || player.getY() - player.getYLength() < 0)
	    {// Resets the player if they fall out of the
	     // map
		reset();
	    } else
	    {
		repaint();// paints the panel to the current state
	    }
	    isHittingObstacle();
	    if (needToBeReset)
	    {
		reset();
	    }
	    clearHarmfulThings();
	}

    }

    private void movePlayer()
    {
	onTopWall = false;
	onRightWall = false;
	onLeftWall = false;
	clingingToRightWall = false;
	clingingToLeftWall = false;
	if (player.getVertMoveState() > 0)// if going down
	{
	    if (isOnGround(player.getY(), player.getY() + player.getVertMoveState()))// if it's on the ground
	    {
		player.zeroVertMovingState();
		jumpTimer = 0;
		jumpAmount = 0;
		if (checkCollision(player.getX() + player.movingState(), player.getY()))
		{
		    String[] temp = collidedWidth.split(",");
		    if (player.movingState() < 0)
		    {
			player.moveToLeftWall(wall.get(Integer.parseInt(temp[1])));
			jumpAmount = 2;
		    } else
		    {
			player.moveToRightWall(wall.get(Integer.parseInt(temp[1])));
			jumpAmount = 2;
		    }
		    player.zeroMovingState();
		}
	    } else// if is moving down but isnt on the ground
	    {
		if (jumpAmount == 0)
		{
		    jumpAmount = 1;
		}
		if (player.movingState() != 0)
		{
		    if (checkCollision(player.getX() + player.movingState(), player.getY() + player.getVertMoveState()))
		    {
			if (!checkCollision(player.getX() + player.movingState(), player.getY()))
			{
			    String[] temp = collidedWidth.split(",");
			    player.zeroVertMovingState();
			    player.moveToTopWall(wall.get(Integer.parseInt(temp[1])));
			    onTopWall = true;
			} else
			{
			    checkCollision(player.getX() + player.movingState(), player.getY());
			    String[] temp = collidedWidth.split(",");
			    if (player.movingState() < 0)
			    {
				player.moveToLeftWall(wall.get(Integer.parseInt(temp[1])));
				onLeftWall = true;
				clingingToLeftWall = true;
				jumpAmount = 2;
			    } else
			    {
				player.moveToRightWall(wall.get(Integer.parseInt(temp[1])));
				onRightWall = true;
				clingingToRightWall = true;
				if(player.getY()-35<wall.get(Integer.parseInt(temp[1])).getY()&&player.getY()-35<wall.get(Integer.parseInt(temp[1])).getY()-wall.get(Integer.parseInt(temp[1])).getHeight())
				{
				clingingToRightWall = false;
				}
				jumpAmount = 2;
			    }

			    player.zeroMovingState();
			}
			if (checkCollision(player.getX(), player.getY() + player.getVertMoveState()))
			{
			    String[] temp = collidedWidth.split(",");
			    player.zeroVertMovingState();
			    onTopWall = true;
			    player.moveToTopWall(wall.get(Integer.parseInt(temp[1])));
			}
		    }
		} else
		{
		    if (checkCollision(player.getX(), player.getY() + player.getVertMoveState()))
		    {
			String[] temp = collidedWidth.split(",");
			player.zeroVertMovingState();
			jumpAmount = 0;
			onTopWall = true;
			player.moveToTopWall(wall.get(Integer.parseInt(temp[1])));
		    }
		}
	    }
	} else
	{
	    if (player.getVertMoveState() < 0)// if going up
	    {
		if (player.movingState() != 0)
		{
		    if (checkCollision(player.getX() + player.movingState(), player.getY() + player.getVertMoveState()))
		    {
			if (!checkCollision(player.getX() + player.movingState(), player.getY()))
			{
			    String[] temp = collidedWidth.split(",");
			    player.zeroVertMovingState();
			    player.moveToBottomWall(wall.get(Integer.parseInt(temp[1])));
			    jump = false;
			    jumpTimer = 1000;
			} else
			{
			    checkCollision(player.getX() + player.movingState(), player.getY());
			    String[] temp = collidedWidth.split(",");
			    if (player.movingState() < 0)
			    {
				player.moveToLeftWall(wall.get(Integer.parseInt(temp[1])));
				jumpAmount = 2;
			    } else
			    {
				player.moveToRightWall(wall.get(Integer.parseInt(temp[1])));
				jumpAmount = 2;
			    }
			    player.zeroMovingState();
			}
			if (checkCollision(player.getX(), player.getY() + player.getVertMoveState()))
			{
			    String[] temp = collidedWidth.split(",");
			   player.zeroVertMovingState();
			    player.moveToBottomWall(wall.get(Integer.parseInt(temp[1])));
			    jump = false;
			    jumpTimer = 1000;
			    
			}
		    }
		} else
		{
		    if (checkCollision(player.getX(), player.getY() + player.getVertMoveState()))
		    {
			String[] temp = collidedWidth.split(",");
			player.zeroVertMovingState();
			player.moveToBottomWall(wall.get(Integer.parseInt(temp[1])));
			jump = false;
			jumpTimer = 1000;
		    }
		 }
	    } else// if not going up or down
	    {
		if (checkCollision(player.getX() + player.movingState(), player.getY()))
		{
		    String[] temp = collidedWidth.split(",");
		    if (player.movingState() < 0)
		    {
			player.moveToLeftWall(wall.get(Integer.parseInt(temp[1])));
			jumpAmount = 2;
		    } else
		    {
			player.moveToRightWall(wall.get(Integer.parseInt(temp[1])));
			jumpAmount = 2;
		    }
		    player.zeroMovingState();
		}
	    }
	}
	if (onRightWall || onLeftWall)
	{
	    canWallJump = true;
	    wallJumpTime = 0;

	} else
	{
	    canWallJump = false;
	}
	if ((clingingToRightWall || clingingToLeftWall))
	{
	    mDown = false;
	    jump = false;
	    player.zeroVertMovingState();
	}
	if (wallJumpTime != 0)
	{
	    wallJumpTime++;
	} else
	{
	    justRightWallJumped = false;
	    justLeftWallJumped = false;
	}
	if (onTopWall)
	{
	    wallJumpTime = 0;
	    jumpAmount = 0;
	}
	if (wallJumpTime == WALL_JUMP_TIMER)
	{
	    wallJumpTime = 0;
	}
	pastVertMoveState = player.getVertMoveState();
	player.setImage(onTopWall, jump);
	checkForSpecialCollisions();
	player.setCoordinates();
	if(onTopWall)
	{
	    mDown = false;
	}
    }
	public int getPlayerX()
	{
		return player.getX();
	}
    private void setMoveState()
    {
	if(mDown)
	{
	    jump = false;
	    player.zeroJumpState();
	    jumpAmount = 2;
	}
	if (jump && canWallJump)
	{
	    wallJump();
	} else
	{
	    if (wallJumpTime != 0)
	    {
		jumpAmount = 2;
		if (justRightWallJumped)
		{
		    if (mRight && mLeft || !(mRight || mLeft))
		    {
			player.setLeftMoving(WALL_JUMP_SPEED);
		    } else
		    {
			if (mRight)
			{
			    player.setLeftMoving(WALL_JUMP_SPEED - WALL_JUMP_RANGE);
			} else
			{
			    player.setLeftMoving(WALL_JUMP_SPEED + WALL_JUMP_RANGE);
			}
		    }
		} else
		{
		    if (mRight && mLeft || !(mRight || mLeft))
		    {
			player.setRightMoving(WALL_JUMP_SPEED);
		    } else
		    {
			if (mRight)
			{
			    player.setRightMoving(WALL_JUMP_SPEED + WALL_JUMP_RANGE);
			} else
			{
			    player.setRightMoving(WALL_JUMP_SPEED - WALL_JUMP_RANGE);
			}
		    }
		}
		player.changeJumpState(JUMP_STATE_CHANGE);
		player.setMovingState();
		if(!mDown)
		{
		player.setDownMoving(FALL_SPEED);
		}
		else
		{
		    player.setDownMoving(FAST_FALL_SPEED);
		}
		player.setVertMoveState();
	    } else
	    {
		if (!(mRight && mLeft))
		{
		    if (mRight)
		    {
			player.setRightMoving(MOVEMENT_SPEED);
		    } else
		    {
			if (mLeft)
			{
			    player.setLeftMoving(MOVEMENT_SPEED);
			} else
			{
			    if (!(mLeft || mRight))
			    {
				if (player.movingState() != 0)
				{
				    player.changeMovingState(2);
				}
			    }
			}
		    }
		}
		player.setMovingState();
		if (jump && jumpTimer < JUMP_TIMER)
		{
		    jumpTimer++;
		    player.setJumpState(JUMP_HEIGHT);
		} else
		{
		    player.changeJumpState(JUMP_STATE_CHANGE);
		}
		if(!mDown)
		{
		player.setDownMoving(FALL_SPEED);
		}
		else
		{
		    player.setDownMoving(FAST_FALL_SPEED);
		}
		player.setVertMoveState();
	    }
	}
	if (jumpAmount == 2 && jump)
	{
	    player.setSecondJumpTrue();
	} else
	{
	    player.setSecondJumpFalse();
	}
	if (player.getSecondJump() && jumpTimer > DOUBLE_JUMP_TIMER)
	{
	    jump = false;
	    player.setJumpState(DOUBLE_JUMP_HEIGHT);
	}
    }

    private void clearHarmfulThings()
    {
	harmfulThings.clear();
	harmfulThingsByteNumbers.clear();
    }

    private void wallJump()
    {
	wallJumpTime = 1;
	canWallJump = false;
	jump = false;
	jumpAmount = 2;
	if (onRightWall)
	{
	    justRightWallJumped = true;
	    player.zeroMovingState();
	    player.setLeftMoving(WALL_JUMP_SPEED);
	} else
	{
	    justLeftWallJumped = true;
	    player.zeroMovingState();
	    player.setRightMoving(WALL_JUMP_SPEED);
	}
	player.setJumpState(WALL_JUMP_HEIGHT);
	player.setMovingState();
	player.setVertMoveState();
    }

    private void playerActions()
    {
		if(playerAttack)
		{
			attack();
		}
		playerAttack = false;
		if(!(player).getIsSwordAttack())
		{
		    playerSwordAttack = false;
		}
    }
    
    private void attack()
    {	
    	player.startAttack(playerSwordAttack,playerProjAttack); 
    	if(playerProjAttack == true)
    	{
    		playerIsProjAttacking = true;
    	}
    	if(playerIsProjAttacking)
    	{
    		if(player.getThrowTheSword())
    		{
    			playerIsProjAttacking = false;
    			swordIsThrown = true;
    		}
    	}

    }
    public void jump()
    {
	// is called if jump is held down

	if (jumpReset)
	{// if jump has been let go of since the last time it was pressed and cycled
	 // through all of the frames it's allowed to be pressed for
	    if (jumpAmount < 2 || canWallJump)
	    {// if the player hasn't exceeded the amount of jumps since it lands
	     // again
		jumpAmount++;// adds a jump to the amount of jumps done
		jump = true;
		jumpReset = false;
		jumpTimer = 0;
	    }
	}
    }
    
    public void moveRight()
    {// called when the user hits the move right key
	mRight = true;
    }

    public void moveLeft()
    {// called when the user hits the move left key
	mLeft = true;
    }
    public void playerSwordAttack()
    {
	playerAttack = true;
	playerSwordAttack = true;
    }
    public void playerProjAttack()
    {
    	playerAttack = true;
    	playerProjAttack = true;
    }
    public void fastFall()
    {//when the user presses the down key while in the air
	mDown = true;
    }
    public void stopJump()
    {// is called when the user releases the jump key
	jump = false;
	jumpTimer = 0;
	jumpReset = true;
    }

    public void stopRight()
    {// is called when the user releases the move right key
	mRight = false;
    }

    public void stopLeft()
    {// is called when the user releases the move left key
	mLeft = false;
    }
    public void reset()
    {// resets the players position to its start position
	player.changeX(StartX);
	player.changeY(StartY);
	bullets.clear();
	currentBulletNumbers.clear();
	harmfulThings.clear();
	repaint();
	needToBeReset = false;
	player.changeHealth();
	if(player.getHealth() == 0)
	{
		switchToScores();
	}
    }

    public int ScreenXSize()
    {
	return SCREEN_X_SIZE;
    }

    public int ScreenYSize()
    {
	return SCREEN_Y_SIZE;
    }

    public void switchToGame()
    {
    if(player.getHealth() == 0)
    {
    player.changeHealth(3);
    }
	gameMode = GAME;
    }

    public void switchToScores()
    {
	gameMode = SCORES;
    }

    public void showGraphics(Graphics g)
    {
	Graphics2D gd = (Graphics2D) g;
	if (gameMode == 0)
	{
	    // BACKGROUND COLOR/IMAGE
         this.setBackground(Color.DARK_GRAY);
		 gd.drawImage(startScreen, 0, 0, null);
      
      	// MAIN TITLE
         gd.setColor(Color.GRAY);
         Font TitleFont = new Font("Stay Pixel Regular", Font.BOLD, 100);
         gd.setFont(TitleFont);
         gd.drawString("Journey Through UVA", 75, 680);
         gd.setColor(Color.WHITE);
         gd.drawString("Journey Through UVA", 71, 680);
      
      	/*// SUBTITLE
      	gd.setColor(Color.GRAY);
      	Font SubSubTitleFont = new Font("Stay Pixel Regular", Font.BOLD, 20);
      	gd.setFont(SubSubTitleFont);
      	gd.drawString("And The", 44, 118);
      	gd.setColor(Color.WHITE);
      	gd.drawString("And The", 40, 114);
      
      	// SUBTITLE
      	gd.setColor(Color.GRAY);
      	Font SubTitleFont = new Font("Stay Pixel Regular", Font.BOLD, 30);
      	gd.setFont(SubTitleFont);
      	gd.drawString("Sacred Holdings Inside Toronto", 44, 154);
      	gd.setColor(Color.WHITE);
      	gd.drawString("Sacred Holdings Inside Toronto", 40, 150);*/
      
      	// NEW GAME
         Font ClickFont = new Font("Unispace Bold", Font.BOLD, 30);
         gd.setFont(ClickFont);
         if (NewH) {
            gd.setColor(Color.YELLOW);
         } else {
            gd.setColor(Color.BLACK);
         }
         gd.drawString("NEW GAME", 1050,  50);
      
      	// LOAD GAME
         if (LoadH) {
            gd.setColor(Color.YELLOW);
         } else {
            gd.setColor(Color.BLACK);
         }
         gd.drawString("LOAD GAME", 1050, 80);
         
         // OPTIONS
         if (OptionH) {
            gd.setColor(Color.YELLOW);
         } else {
            gd.setColor(Color.BLACK);
         }
         gd.drawString("OPTIONS", 1050, 110);
      
      	// EXIT
         if (ExitH) {
            gd.setColor(Color.YELLOW);
         } else {
            gd.setColor(Color.BLACK);
         }
      
         gd.drawString("EXIT", 1050, 140);
      
      	// SETTINGS ICON
         gd.drawImage(Gear, SCREEN_X_SIZE - 90, SCREEN_Y_SIZE - 90, this);

	} else if (gameMode == GAME)
	{
		gd.setColor(Color.black);
	    int deathCount = playerHealthMax;
	    gd.drawImage(sky, 0, 0, this);
	    for(int i = 0; i < player.getHealth(); i++)
	    {
		    deathCount--;
		    gd.drawImage(fullHeart, heartDisplayCoordX+(i*30), heartDisplayCoordY, this);	
	    }
	    for(int i = 0; i < deathCount; i++)
	    {
		gd.drawImage(emptyHeart, heartDisplayCoordX+(30*(playerHealthMax-(deathCount - i))), heartDisplayCoordY, this);
	   }
	    gd.drawImage(player.currentImage(), player.getX(), player.getY() - player.getYLength(), null);// paints the
													  // player
													  // image
													  // onto the
													  // frame
	    for (int i = 0; i < ground.size(); i++)
	    {// paints all of the ground elements onto the JPanel
		int[] xC = ground.get(i).getXCoords();
		int yC = ground.get(i).getYCoords();
		gd.drawLine(xC[0], yC, xC[1], yC);
		gd.drawImage(ground.get(i).getImage(), xC[0], yC, xC[1], yC + 10, 0, 0, Math.abs(xC[1] - xC[0]),
			yC + 10, null);
	    }
	    for (int i = 0; i < wall.size(); i++)
	    {
		int xC = wall.get(i).getX();
		int yC = wall.get(i).getY();
		gd.drawImage(wall.get(i).getImage(), xC, yC, xC + wall.get(i).getWidth(), yC + wall.get(i).getHeight(),
			0, 0, wall.get(i).getWidth(), wall.get(i).getHeight(), null);
	    }
	    for (int i = 0; i < obstacles.size(); i++)
	    {
		int xC = obstacles.get(i).getX();
		int yC = obstacles.get(i).getY();
		gd.drawImage(obstacles.get(i).getImage(), xC, yC - obstacles.get(i).getHeight(),
			xC + obstacles.get(i).getWidth(), yC, 0, 0, obstacles.get(i).getWidth(),
			obstacles.get(i).getHeight(), null);
	    }
	    for (int i = 0; i < finishLines.size(); i++)
	    {
		int xC = finishLines.get(i).getX();
		int yC = finishLines.get(i).getY();
		gd.drawImage(finishLines.get(i).getImage(), xC, yC-finishLines.get(i).getHeight(), xC + finishLines.get(i).getWidth(),
			yC, 0, 0, finishLines.get(i).getWidth(),
			finishLines.get(i).getHeight(), null);
	    }
	    for (int i = 0; i < startingLines.size(); i++)
	    {
		int xC = startingLines.get(i).getX();
		int yC = startingLines.get(i).getY();
		gd.drawImage(startingLines.get(i).getImage(), xC, yC-startingLines.get(i).getHeight(), xC + startingLines.get(i).getWidth(),
			yC, 0, 0, startingLines.get(i).getWidth(),
			startingLines.get(i).getHeight(), null);
	    }
	    for (int i = 0; i < entities.size(); i++)
	    {
		gd.drawImage(entities.get(i).getImage(), entities.get(i).getX(),
			entities.get(i).getY() - entities.get(i).getYLength(), null);
	    }
	    for (int i = 0; i < bullets.size(); i++)
	    {
		gd.drawImage(bullets.get(i).getImage(), bullets.get(i).getX(),
			bullets.get(i).getY() - bullets.get(i).getYLength(), null);
	    }
		Font ClickFont = new Font("Unispace Bold", Font.BOLD, 25);
		gd.setFont(ClickFont);
		gd.drawString("XP: "+String.valueOf(gameScreen.gamePoints),1200,50);
	} else if (gameMode == SCORES)
	{
	    this.setBackground(Color.BLACK);

	    // MAIN TITLE
	    Font TitleFont = new Font("Stay Pixel Regular", Font.BOLD, 50);
	    gd.setFont(TitleFont);
	    gd.setColor(Color.GRAY);
	    gd.drawString("Good try, you're almost there", 50, 80);
	    gd.setColor(Color.WHITE);
	    gd.drawString("Good try, you're almost there", 50, 76);

	    // SCORES
	    Font SubTitleFont = new Font("Unispace Bold", Font.BOLD, 35);
	    gd.setFont(SubTitleFont);
	    String scores = Integer.toString(Score);
	    gd.setColor(Color.GRAY);
	    gd.drawString("Press ESC to Restart", 50, 138);
	    gd.setColor(Color.WHITE);
	    gd.drawString("Press ESC to Restart", 50, 138);

	}
    }

    public void paint(Graphics g)
    {// the method which paints the entire panel to the state it needs to be
	super.paint(g);
	showGraphics(g);
    }

    public void checkForSpecialCollisions()
    {
	// player.setPoints();

    }

    public int getLevelChangeState()
    {
	if (canChangeTimer == 50)
	{
	    return changeState;
	}
	changeState = 0;
	return 0;
    }

    public boolean checkCollision(int x, int y)
    {
	for (int i = x; i <= x + player.getXLength(); i = i + player.getXLength() / 2 - 1)
	{
	    for (int j = y - player.getYLength(); j <= y; j = j + player.getYLength() / 2)
	    {
		if (everything.get(i, j) != null)
		{
		    String[] temp = everything.get(i, j).split(",");
		    if (temp[0].equals("WALL"))
		    {
			collidedWidth = everything.get(i, j);
			return true;
		    }
		}
	    }
	}
	return false;
    }

    public boolean isOnGround(int origY, int currY)
    {// Sparsematrix of every object and an array list of the ground
     // pieces
	for (int i = 0; i < ground.size(); i++)
	{
	    int y = ground.get(i).getYCoords();
	    if (origY <= y && currY >= y)
	    {
		int[] x = ground.get(i).getXCoords();
		if ((x[0] <= player.getX() && x[1] >= player.getX())
			|| (x[0] <= player.getX() + player.getXLength() && x[1] >= player.getX() + player.getXLength()))
		{
		    onTopWall = true;
		    player.moveToGround(ground.get(i));
		    return true;
		}
	    }
	}
	return false;
    }

    public void isHittingObstacle()
    {
	byte temp = player.isHittingObstacle(harmfulThings, harmfulThingsByteNumbers);
	switch (temp)
	{
	case 0:    	
	    break;
	case 4:
		findForwardChangeState();
	    break;
	case 5:
		findBackwardsChangeState();
	    break;
	case 3:
	    needToBeReset = true;
	    break;
	case 6:
	    needToBeReset = true;
	    break;
	case 7:
	    needToBeReset = true;
	    break;
	}
    }

	public void findForwardChangeState()
	{
		for(int i = 0; i < harmfulThings.size();i++)
		{
			if(harmfulThings.get(i).getClass()==finishLine.class)
			{
				finishLine tempLine = (finishLine)harmfulThings.get(i);
				changeState = tempLine.getLevelChangeState();
			}
		}
	}

	public void findBackwardsChangeState()
	{
		for(int i = 0; i < harmfulThings.size();i++)
		{
			if(harmfulThings.get(i).getClass()==StartingLine.class)
			{
				StartingLine tempLine = (StartingLine)harmfulThings.get(i);
				changeState = tempLine.getLevelChangeState();
			}
		}
	}
    public void gameStart(int x, int y)
    {
	if (gameMode == 0)
	{
	    mouseX = x;
	    mouseY = y;
	    // if mouse is within the bounds of the text
	    if((mouseX >= 1050 && mouseX <= 1214) && (mouseY >= 50 && mouseY <= 73))
	    {
	    	switchToGame(); // change value of gamemode
	    }else
	    {
	    if ((mouseX >= 1050 && mouseX <= 1227) && (mouseY >= 80 && mouseY <= 102))
	    {
	    	System.out.println("Cannot Load Game");
	    } else
	    {
		if ((mouseX >= 1050 && mouseX <= 1180) && (mouseY >= 110 && mouseY <= 133))
		{
		    System.out.println("Cannot Open Options");
		} else
		{
		    if ((mouseX >= 1050 && mouseX <= 1107) && (mouseY >= 140 && mouseY <= 162))
		    {
			System.exit(0);
		    }
			}
	    	}
	    	}
	    
	    repaint();
	}
    }

    private void checkForCheckpoints()
    {
	for (int i = 0; i < startingLines.size(); i++)
	{
	    if (startingLines.get(i).checkCollision(player.getX(), player.getY(), player.getXLength(),
		    player.getYLength()))
	    {
		harmfulThings.add(startingLines.get(i));
		harmfulThingsByteNumbers.add((byte) 5);
	    }
	}
	for (int i = 0; i < finishLines.size(); i++)
	{
	    if (finishLines.get(i).checkCollision(player.getX(), player.getY(), player.getXLength(),
		    player.getYLength()))
	    {
		harmfulThings.add(finishLines.get(i));
		harmfulThingsByteNumbers.add((byte) 4);
	    }
	}

    }

    private void moveEntities()
    {
	for (int i = 0; i < obstacles.size(); i++)
	{
	    if (obstacles.get(i).checkCollision(player.getX(), player.getY(), player.getXLength(), player.getYLength()))
	    {
		harmfulThings.add(obstacles.get(i));
		harmfulThingsByteNumbers.add((byte) 3);
	    }
	}
	for (int i = 0; i < entities.size(); i++)
	{
	    switch (entities.get(i).getEnitityType())
	    {
	    case 0:
		stationaryHittingEntity tempSHE = ((stationaryHittingEntity) (entities.get(i)));
		tempSHE.runEntity(player.getX());
		if(tempSHE.checkCollision(player.getX(), player.getY(), player.getXLength(),
			player.getYLength()))
		{
			((stationaryHittingEntity)entities.get(i)).startAttack();
		    harmfulThings.add(entities.get(i));
		    harmfulThingsByteNumbers.add((byte) 6);
		}
		break;
	    case 1:
		stationaryShootingEntity tempSSE = ((stationaryShootingEntity) (entities.get(i)));
		tempSSE.runEntity(player.getX(), player.getY());
		if (tempSSE.getBulletState())
		{
		    Point bulletPoints = tempSSE.getBulletPoints();
		    bullets.add(new Bullet(tempSSE.getX() + bulletPoints.x, tempSSE.getY() - bulletPoints.y,tempSSE.getBulletImageNum()));
		    bullets.get(bullets.size() - 1).setMoveStates(player.getX(), player.getY());
		}
		if (entities.get(i).checkCollision(player.getX(), player.getY(), player.getXLength(),
			player.getYLength()))
		{
		    harmfulThings.add(entities.get(i));
		    harmfulThingsByteNumbers.add((byte) 6);
		}
		break;
	    }
	    if(playerSwordAttack)
	    {
		System.out.println("Hello?");
		if(entities.get(i).isHit(player.getSwordHitboxes(), player.getX(), player.getY()))
		{
		    entities.remove(i);
		    i--;
		}
	    }
	    else
	    {
		if(playerProjAttack)
		{
		    if(entities.get(i).isHit(player.getSwordHitboxes(), player.getX(), player.getY()))
			{
			    entities.remove(i);
			    i--;
			}  
		}
		else
		{
		    if(swordIsThrown)
		    {
			Sword temp = new Sword();
			if(entities.get(i).isHit(temp.getHitbox(), temp.getX(), temp.getY()))
			{
			   entities.remove(i);
			   i--;
			}
		    }
		}
	    }
	}
	for (int i = 0; i < bullets.size(); i++)
	{
	    // bullets.get(i).setMoveStates(player.getX(), player.getY());
	    bullets.get(i).moveProjectile();
	    String whatOn = everything.get(bullets.get(i).getX(), bullets.get(i).getY());
	    String whatOn2 = everything.get(bullets.get(i).getX(), bullets.get(i).getY() - 5);
	    if (bullets.get(i).outOfBounds(SCREEN_X_SIZE, SCREEN_Y_SIZE) || whatOn != null || whatOn2 != null)
	    {
		bullets.remove(i);
		continue;
	    }
	    if (bullets.get(i).checkCollision(player.getX(), player.getY(), player.getXLength(), player.getYLength()))
	    {
		harmfulThings.add(bullets.get(i));
		harmfulThingsByteNumbers.add((byte) 7);
	    }
	}
	if(player.getIsSwordAttack())
    	for(int i = 0; i < entities.size(); i++)
    	{
    		if(entities.get(i).checkCollision(player.getX(),player.getY(), player.getXLength()+player.getAttackWidth(), player.getYLength()))
    		{
    			if(entities.get(i).isHit(player.getHitbox(),player.getX(),player.getY()))
    			{
    				entities.remove(i);
    				break;
    			}
    		}
    		
    	}
    }

    public void gameStartCheck(int x, int y)
    {
	if (gameMode == 0)
	{
	    mouseX = x;
	    mouseY = y;
	    // if mouse is within the bounds of the text
	    if((mouseX >= 1050 && mouseX <= 1214) && (mouseY >= 50 && mouseY <= 73))
	    {
	    	NewH = true;
	    }else
	    {
	    	NewH = false;
	    if ((mouseX >= 1050 && mouseX <= 1227) && (mouseY >= 80 && mouseY <= 102))
	    {
		LoadH = true;
	    } else
	    {
		LoadH = false;
		if ((mouseX >= 1050 && mouseX <= 1180) && (mouseY >= 110 && mouseY <= 133))
		{
		    OptionH = true;
		} else
		{
		    OptionH = false;
		    if ((mouseX >= 1050 && mouseX <= 1107) && (mouseY >= 140 && mouseY <= 162))
		    {
			ExitH = true;
		    } else
		    {
			ExitH = false;
		    }
		}
	    }
	    }
	    repaint();
	}
    }
}