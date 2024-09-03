import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import javax.swing.*;
import javax.swing.Timer;

public class gameScreen extends JFrame implements ActionListener, MouseListener, MouseMotionListener {
	// the ations for moving
	Action jumpAction;
	Action downAction;
	Action rightAction;
	Action leftAction;
	Action noJump;
	Action noRight;
	Action noLeft;
	Action switchS;
	Action swordAttack;
	Action projectileAttack;
	Timer timer;
	public static int gamePoints = 0;
	public final static int SCREEN_X_SIZE = 1280;
	public final static int SCREEN_Y_SIZE = 720;
	private int currentLevelNumber;

	private JLabel timeLabel = new JLabel("TimeAvailable");
	private ArrayList<Integer> levelEntranceNumbers = new ArrayList<Integer>(10);
	private ArrayList<Integer> levelExitNumbers = new ArrayList<Integer>(10);
	private Levels lvl;
	private int timeAvailable = 0;
	private boolean timerStarted = false;
	private final String[] levelsNames = {"OrientationRoom", "ClassSelectionRoom","clubFair", "sportsClub1", "sportsClub2", "sportsClub3","serviceClub1","serviceClub2","activitiesSpot","gym1"};
	boolean onScores;

	public gameScreen() throws IOException {
		levelExitNumbers.add(5);
		levelExitNumbers.add(7);
		levelExitNumbers.add(9);
		levelEntranceNumbers.add(3);
		levelEntranceNumbers.add(6);
		levelEntranceNumbers.add(9);
		currentLevelNumber = 0;
		lvl = new Levels(new Map(levelsNames[0]), 0);
		this.setSize(SCREEN_X_SIZE, SCREEN_Y_SIZE);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Stickman: Legend of the Bat");
		ImageIcon image = new ImageIcon("Stickman.png");
		this.setIconImage(image.getImage());
		this.add(lvl);
		lvl.add(timeLabel);
		timeLabel.setLocation(100,100);
		timeLabel.setVisible(true);
		jumpAction = new JumpAction();
		downAction = new DownAction();
		rightAction = new RightAction();
		leftAction = new LeftAction();
		noJump = new noJump();
		noRight = new noRight();
		noLeft = new noLeft();
		switchS = new switchScreen();
		swordAttack = new swordAttack();
		projectileAttack = new projectileAttack();
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('w'), "JAction");
		this.getRootPane().getActionMap().put("JAction", jumpAction);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('s'), "DAction");
		this.getRootPane().getActionMap().put("DAction", downAction);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(' '), "SAAction");
		this.getRootPane().getActionMap().put("SAAction", swordAttack);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('m'), "SAAction");
		this.getRootPane().getActionMap().put("SAAction", swordAttack);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('n'), "PAction");
		this.getRootPane().getActionMap().put("PAction", projectileAttack);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('a'), "LAction");
		this.getRootPane().getActionMap().put("LAction", leftAction);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke('d'), "RAction");
		this.getRootPane().getActionMap().put("RAction", rightAction);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, true), "NJAction");
		this.getRootPane().getActionMap().put("NJAction", noJump);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "NRAction");
		this.getRootPane().getActionMap().put("NRAction", noRight);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "NLAction");
		this.getRootPane().getActionMap().put("NLAction", noLeft);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0, true), "NJAction");
		this.getRootPane().getActionMap().put("NJAction", noJump);
		this.getRootPane().getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, true), "switch");
		this.getRootPane().getActionMap().put("switch", switchS);
		addMouseListener(this);
		addMouseMotionListener(this);
		timer = new Timer(15, this);// the timer which runs the game(runs every 15 milliseconds or idk fps
		timer.start();
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
		onScores = false;
	}

	private void setIconImages(ImageIcon imageIcon) {

	}

	private void changeLevel(int c) {
		if(currentLevelNumber==1&&c==1&&!timerStarted)
		{
			if(lvl.getPlayerX()<400)
			{
				timeAvailable = 33_750;
			}
			else {
				if(lvl.getPlayerX()<800)
				{
					timeAvailable = 67_500;
				}
				else
				{
					timeAvailable = 101_250;
				}
			}
			timerStarted = true;
		}
		this.remove(lvl);
		if(Math.abs(c)>1&&levelExitNumbers.contains(currentLevelNumber))
		{
			gamePoints += 3;
			levelExitNumbers.remove((Integer) currentLevelNumber);
		}
		currentLevelNumber = currentLevelNumber + c;
		if(levelEntranceNumbers.contains(currentLevelNumber))
		{
			gamePoints++;
			levelEntranceNumbers.remove((Integer)currentLevelNumber);
		}
		if(currentLevelNumber==2||currentLevelNumber==8)
		{
			try{
				lvl = new Levels(new Map(levelsNames[currentLevelNumber]),1);
			} catch (IOException e)
			{
				System.out.println(currentLevelNumber);
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
				lvl = new Levels(new Map(levelsNames[currentLevelNumber]), c);
			} catch (IOException e)
			{
				// TODO Auto-generated catch block
				System.out.println(currentLevelNumber);
				e.printStackTrace();
			}
		}
		this.add(lvl);
		lvl.add(timeLabel);
		timeLabel.setLocation(100,100);
		timeLabel.setVisible(true);
		this.setVisible(true);
	}

	public class JumpAction extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			lvl.jump();

		}
	}

	public class DownAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			lvl.fastFall();

		}
	}

	public class RightAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			lvl.moveRight();

		}
	}

	public class LeftAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			lvl.moveLeft();
		}
	}

	public class noJump extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e) {
			lvl.stopJump();

		}
	}

	public class noRight extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			lvl.stopRight();

		}
	}

	public class noLeft extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub

			lvl.stopLeft();
		}
	}

	public class swordAttack extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			lvl.playerSwordAttack();
		}
	}

	public class projectileAttack extends AbstractAction {
		public void actionPerformed(ActionEvent e) {
			lvl.playerProjAttack();
		}
	}

	public class switchScreen extends AbstractAction {
		@Override
		public void actionPerformed(ActionEvent e)
		{
			// TODO Auto-generated method stub
		    		//changeLevel(0);
		    for(int i = 0; i < currentLevelNumber-1;)
		    {
			changeLevel(-1);
		    }
		    currentLevelNumber = 0;
		    changeLevel(2);
		    lvl.switchToGame();
		}
		}


	@Override
	public void actionPerformed(ActionEvent arg0) {
		lvl.run();
		if(timerStarted)
		{
			timeAvailable--;
			timeLabel.setFont(new Font("Stay Pixel Regular", Font.BOLD, 100));
			timeLabel.setText(Integer.toString(timeAvailable));
			timeLabel.setVisible(true);
		}
		repaint();
		/*if(lvl.pointChange())
		{
			gamePoints += lvl.getPointChange();
		}*/
		int cS = lvl.getLevelChangeState();
		if (cS != 0) {
			changeLevel(cS);
			repaint();
			cS = 0;
		}
	}

	public void mouseClicked(MouseEvent e) {

	}

	public void mousePressed(MouseEvent e) {
		lvl.gameStart(e.getX(), e.getY());
	}

	public void mouseReleased(MouseEvent e) {

	}

	public void mouseEntered(MouseEvent e) {

	}

	public void mouseMoved(MouseEvent e) {
		lvl.gameStartCheck(e.getX(), e.getY());
	}

	public void mouseDragged(MouseEvent e) {

	}

	public void mouseExited(MouseEvent e) {

	}
}