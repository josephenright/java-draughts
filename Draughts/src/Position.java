import java.io.Serializable;

public class Position implements Serializable{

    private int x;
    private int y;


    /**
     * Position Constructor.
     * @param x The X co-ordinate of the Position.
     * @param y The Y co-ordinate of the Position.
     */
    public Position(int x, int y) {
        setX(x);
        setY(y);
    }


    /**
     * Get the X co-ordinate of the Position.
     * @return The x co-ordinate.
     */
    public int getX() {
        return x;
    }

    /**
     * Set the X co-ordinate of the Position.
     * @return The x co-ordinate.
     */
    public void setX(int x) {
        this.x = x;
    }


    /**
     * Get the Y co-ordinate of the Position.
     * @return The y co-ordinate.
     */
    public int getY() {
        return y;
    }

    /**
     * Set the Y co-ordinate of the Position.
     * @param y The y co-ordinate.
     */
    public void setY(int y) {
        this.y = y;
    }


    /**
     * Display the attributes of the Position class in a String.
     * @return The String containing Position information.
     */
    @Override
    public String toString() {
        return "Position: (" + getX() + "," + getY() + ")";
    }
}
