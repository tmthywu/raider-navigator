import java.awt.Graphics;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
// possible exceptions
import java.io.IOException;

/**
 * for loading pictures
 * @author ICS3U6
 * @version May 2022
 */
public class Sprite {
    private int x;
    private int y;
    private BufferedImage picture;
    private int width;
    private int height;
    private int stepX;
    private int stepY;

    // ------------------------------------------------------------------------------
    Sprite(int x, int y, String picName) {
        this.x = x;
        this.y = y;
        try {
            this.picture = ImageIO.read(new File(picName));
        } catch (IOException ex) {
            System.out.println("File not found!");
        }
        this.width = this.picture.getWidth();
        this.height = this.picture.getHeight();
        this.stepX = Const.STEP;
    }

    // ------------------------------------------------------------------------------
    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getStepX() {
        return this.stepX;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setStepX(int stepX) {
        this.stepX = stepX;
    }

    public void setStepY(int stepY) {
        this.stepY = stepY;
    }

    // ------------------------------------------------------------------------------
    public void draw(Graphics g) {
        g.drawImage(this.picture, this.x, this.y, null);
    }

    public boolean inside(int x, int y) {
        return (x > this.x && x < this.x + this.width && y > this.y && y < this.y + this.height);
    }

    // ------------------------------------------------------------------------------
    public void moveOneStepX() {
        this.x = this.x + this.stepX;
    }

    public void bounceX() {
        this.stepX = -this.stepX;
    }

    public void moveOneStepY() {
        this.y = this.y + this.stepY;
    }

    public void bounceY() {
        this.stepY = -this.stepY;
    }

    public boolean crossingLeft(int boundary) {
        return (this.x + this.stepX < boundary);
    }

    public boolean crossingRight(int boundary) {
        return (this.x + this.width + this.stepX > boundary);
    }

    public boolean crossingTop(int boundary) {
        return (this.y + this.stepY < boundary);
    }

    public boolean crossingBottom(int boundary) {
        return (this.y + this.width + this.stepY > boundary);
    }
}