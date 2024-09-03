import javax.swing.ImageIcon;
import java.awt.*;

public class Spike extends obstacles
{
    ImageIcon image;
    hitbox[] hitboxes;
    public Spike(int x1, int y1, int w, int h, String imageName)
    {
        super(x1,y1,w,h);
        image = new ImageIcon(imageName);
        hitboxes = new hitbox[]{new hitbox(width/2,height/2,width/2)};
    }

    @Override
    public Image getImage()
    {
        return image.getImage();
    }

    @Override
    public hitbox[] getHitboxes()
    {
        return hitboxes;
    }
}
