import java.awt.Color;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ImageIcon;

public class Map {
	public final int SCREEN_X_SIZE = gameScreen.SCREEN_X_SIZE;
	public final int SCREEN_Y_SIZE = gameScreen.SCREEN_Y_SIZE;
	private ImageIcon background;
	private ImageIcon[] forwardsRAnimations;
	private ImageIcon[] backwardsRAnimations;
	private ArrayList<Ground> ground = new ArrayList<Ground>();
	private ArrayList<Wall> wall = new ArrayList<Wall>();
	private ArrayList<obstacles> obstacles = new ArrayList<obstacles>();
	private ArrayList<finishLine> finishLines = new ArrayList<finishLine>();
	private ArrayList<StartingLine> startingLines = new ArrayList<StartingLine>();
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	private int startingX;
	private int startingY;
	private String fileName1;
	String[] file;
	SparseMatrix<String> comp = new SparseMatrix<String>(SCREEN_X_SIZE, SCREEN_Y_SIZE); // stationary objects
																						// coordinates

	public Map(String f) throws IOException {
		fileName1 = f;
		file = readFile(fileName1);// reads in the map file which is expected
		String[] lineName;
		String[] temp;
		background = new ImageIcon(file[0]);
		for (int i = 0; i < file.length; i++) {
			lineName = file[i].split(" ");// splits the line into 2 pieces 1 being the name of the piece and one the//// rest of the info
			if(lineName[0].equals("Coords"))
			{
			    temp = lineName[1].split(",");
			    startingX = Integer.parseInt(temp[0]);
			    startingY = Integer.parseInt(temp[1]);
			}
			if (lineName[0].equals("GROUND"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits it into the correct arguments
				ground.add(new Ground(// makes the right ground object and adds it to the ground array
						Integer.parseInt(temp[0].trim()), Integer.parseInt(temp[1]), Integer.parseInt(temp[2]),
						Integer.parseInt(temp[2]), new ImageIcon(temp[3])));
				for (int t = Integer.parseInt(temp[0]); (t < Integer.parseInt(temp[1])) && (t < SCREEN_X_SIZE); t++) {
					for (int j = 0; (j < 10) && (j < SCREEN_Y_SIZE); j++) {
						comp.add(t, j+Integer.parseInt(temp[2]), "GROUND" + Integer.toString(i));
					}
				}
			}
			if (lineName[0].equals("WALL"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits it into the correct arguments
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length - 1; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				wall.add(new Wall(// makes the right ground object and adds it to the ground array
						keep[0], keep[1], keep[2], keep[3], new ImageIcon(temp[4])));
				for (int t = keep[0]; (t < keep[2] + keep[0]) && (t < SCREEN_X_SIZE); t++)// sets the sparsematrix to
																							// the ground objects
				{
					for (int j = keep[1]; (j < keep[1] + keep[3]) && (j < SCREEN_Y_SIZE); j++) {
						comp.add(t, j, "WALL," + Integer.toString(wall.size() - 1));
					}
				}
			}
			if (lineName[0].equals("HORIZONTALSPIKE"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length - 1; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				obstacles.add(new HorizontalSpike(// makes the right ground object and adds it to the ground array
						keep[0], keep[1], keep[2], temp[3]));
			}
			if (lineName[0].equals("SPIKE"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length - 1; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				obstacles.add(new Spike(// makes the right ground object and adds it to the ground array
						keep[0], keep[1], keep[2], keep[3], temp[4]));
			}
			if (lineName[0].equals("0"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				entities.add(new stationaryHittingEntity(keep[1], keep[2], (byte) keep[0]));
			}
			if (lineName[0].equals("1"))// checks for the right type of component
			{
				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				entities.add(new stationaryShootingEntity(keep[1], keep[2], (byte) keep[0],(byte)keep[3]));
			}
			if (lineName[0].equals("FINISHLINE"))// checks for the right type of component
			{

				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length - 1; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				finishLines.add(new finishLine(keep[0], keep[1], keep[2], keep[3],keep[4], new ImageIcon(temp[5])));
			}
			if (lineName[0].equals("STARTINGLINE"))// checks for the right type of component
			{

				temp = lineName[1].split(",");// splits the other line into the arguments needed for the stickman
				int[] keep = new int[temp.length];
				for (int count = 0; count < temp.length - 1; count++) {

					keep[count] = Integer.parseInt(temp[count].trim());
				}
				startingLines.add(new StartingLine(keep[0], keep[1], keep[2], keep[3],keep[4], new ImageIcon(temp[5])));
			}
		}
	}

	public void setRunAnimations(String[] temp) {
		int inputsBeforeRun = 8;
		int fowardRun = 0;
		ImageIcon[] forwardsRunAnimations;
		while (!temp[fowardRun].equals("END")) {
			fowardRun++;
		}
		forwardsRunAnimations = new ImageIcon[fowardRun - inputsBeforeRun];
		for (int wtest = inputsBeforeRun; wtest < fowardRun; wtest++) {
			forwardsRunAnimations[wtest - inputsBeforeRun] = new ImageIcon(temp[wtest]);
		}
		int backwardsRun = fowardRun + 1;
		while (!temp[backwardsRun].equals("END")) {
			backwardsRun++;
		}
		ImageIcon[] backwardsRunAnimations = new ImageIcon[backwardsRun - (fowardRun + 1)];
		for (int wtest = fowardRun + 1; wtest < backwardsRun; wtest++) {
			backwardsRunAnimations[wtest - (fowardRun + 1)] = new ImageIcon(temp[wtest]);
		}
		forwardsRAnimations = forwardsRunAnimations;
		backwardsRAnimations = backwardsRunAnimations;
	}

	public static int getFileSize(String fileName) throws IOException {
		Scanner input = new Scanner(new FileReader(fileName));
		int size = 0;
		while (input.hasNextLine()) // while there is another line in the file
		{
			size++; // add to the size
			input.nextLine(); // go to the next line in the file
		}
		input.close(); // always close the files when you are done
		return size;
	}

	// pre: "fileName" is the name of a real file containing lines of text - the
	// first line intended to be unused
	// post:returns a String array of all the elements in <filename>.txt, with index
	// 0 unused (heap) O(n)
	public static String[] readFile(String fileName) throws IOException {
		int size = getFileSize(fileName); // holds the # of elements in the file
		String[] list = new String[size]; // a heap will not use index 0;
		Scanner input = new Scanner(new FileReader(fileName));
		int i = 0; // index for placement in the array
		String line;
		while (input.hasNextLine()) // while there is another line in the file
		{
			line = input.nextLine(); // read in the next Line in the file and store it in line
			list[i] = line; // add the line into the array
			i++; // advance the index of the array
		}
		input.close();
		return list;
	}

	public int startingX()
	{
	    return startingX;
	}
	public int startingY()
	{
	    return startingY;
	}
	public ArrayList<Ground> getGround()// returns the ground array
	{
		return ground;
	}

	public ArrayList<Wall> getWall() {
		return wall;
	}

	public SparseMatrix<String> getMatrix()// returns the matrix
	{
		return comp;
	}

	public ArrayList<obstacles> getObstacles() {
		return obstacles;
	}

	public ImageIcon getBackgroundImage() {
		return background;
	}

	public ArrayList<finishLine> getFinishLines() {
		return finishLines;
	}

	public ArrayList<StartingLine> getStartingLines() {
		// TODO Auto-generated method stub
		return startingLines;
	}

	public ArrayList<Entity> getEntities() {
		return entities;
	}

}
