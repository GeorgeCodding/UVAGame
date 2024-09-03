import java.util.*;
public class hitboxNumbers {
	
	private ArrayList<hitbox[]> shooterDummy = new ArrayList<hitbox[]>(); //-2
	private ArrayList<hitbox[]> hitDummy = new ArrayList<hitbox[]>(); //-1
	private ArrayList<hitbox[]> bullet = new ArrayList<hitbox[]>(); //0
	private ArrayList<hitbox[]> stationaryShootingEntity = new ArrayList<hitbox[]>();//1
	private ArrayList<hitbox[]> stationaryHittingEntity = new ArrayList<hitbox[]>();
	private ArrayList<hitbox[]> horizontalSpikes = new ArrayList<hitbox[]>();//4
	private void setArrayLists()
	{
		bullet.add(new hitbox[]{new hitbox(5.0,5.0,4.9)});
		stationaryShootingEntity.add(new hitbox[] {new hitbox(11.5,20.5,8.5), new hitbox(11,26,7),new hitbox(10,39,6.5)});
		hitDummy.add(new hitbox[] {new hitbox(12.5,39.5,9.8), new hitbox(21.5,23.2,6.5),new hitbox(26,6.8,5.5), new hitbox(38.1,6.9,5.9)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(50.5,45.6,20)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(12.8,35.1,10.7),new hitbox(35.8,36.9,19.4)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(19.2,35,12)});
		//stationaryHittingEntity.add(new hitbox[] {new hitbox(35,50.3,10.8)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(0,0,0)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(50.7,43.1,14.6),new hitbox(65.6,41.5,13.9),new hitbox(77.85,33.5,7.25)});
		stationaryHittingEntity.add(new hitbox[] {new hitbox(68.2,29.9,11.5)});
	}
	public hitboxNumbers()
	{
		setArrayLists();
	}
	public ArrayList<hitbox[]> getHitboxes(int x)
	{
		switch (x)
		{

	case 1: return stationaryHittingEntity;
	case 0: return bullet;
	case -1: return hitDummy;
	case -2: return stationaryShootingEntity;
		}
		return null;
	}
}