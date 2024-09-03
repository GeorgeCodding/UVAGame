
public class hitbox
{
    double xC;
    double yC;
    double rad;

    public hitbox(double x, double y, double r)
    {
	xC = x;
	yC = y;
	rad = r;
    }
    public double getX()
    {
	return xC;
    }
    
    public double getY()
    {
	return yC;
    }
    
    public double getRadius()
    {
	return rad;
    }
    
    public boolean isColliding(hitbox entityHitbox, int x, int y, int otherX, int otherY)//(hitbox, playerX, playerY, entx, enty
    {

	double entityHitX = otherX + entityHitbox.getX();
	double entityHitY = otherY - (double)entityHitbox.getY();
	double playerX = (double)x + (double)xC;
	double playerY = (double)y - (double)yC;
	double distance = entityHitbox.getRadius()+rad;
	return (distance >= Math.hypot(Math.abs(entityHitX-playerX), Math.abs(entityHitY-playerY)));

    }
}
