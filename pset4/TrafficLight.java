import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


// TrafficLight.java 
// A traffic light drawing that switches on and off colors if you click on the respective panel
public class TrafficLight extends JFrame {
    static final int FRAME_WIDTH = 200;
    static final int FRAME_HEIGHT = 570;

    public static void main(String[] args) {
        TrafficLight fr = new TrafficLight();
        fr.setLayout(new GridLayout(3, 1)); // much easier to use GridLayout for this problem than BorderLayout or GridBagLayout
        fr.setSize(FRAME_WIDTH, FRAME_HEIGHT); // I initially was using these to define the size of the circles, but it wasn't dynamic 
        // so this may not be needed anymore other than initial sizing
        fr.setTitle("Traffic Light");
        fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyLightPanel red    = new MyLightPanel(Color.RED,    true);
        MyLightPanel yellow = new MyLightPanel(Color.YELLOW, false);
        MyLightPanel green  = new MyLightPanel(Color.GREEN,  false);

        // clicking a panel lights it up and dims the others
        // using adapter to avoid imnplementing all the methods of MouseListener
        red.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                red.setLit(true);
                yellow.setLit(false);
                green.setLit(false);
            }
        });
        yellow.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                red.setLit(false);
                yellow.setLit(true);
                green.setLit(false);
            }
        });
        green.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                red.setLit(false);
                yellow.setLit(false);
                green.setLit(true);
            }
        });


        fr.add(red);
        fr.add(yellow);
        fr.add(green);
        fr.setVisible(true);
    }
}

// Customized JPanel that draws a circle of a specified color, either lit (baseline color) or dimmed (dark dark version of the color).
// the setLit method in addition to changing the boolean value triggers the repaint() method
class MyLightPanel extends JPanel {

    private Color color;
    private boolean lit;

    public MyLightPanel(Color color, boolean lit) {
        this.color = color;
        this.lit = lit;
        setBackground(Color.GRAY);
    }

    public void setLit(boolean lit) {
        this.lit = lit;
        repaint(); // it took me a while to understand that repains invokes  paintComponent(). 
        // This is more about getting familiar with the package than logic and I don't like it.. ha
    }


    public void paintComponent(Graphics g) {
        // super.paintComponent(g); I don't think this matters in this case.
        int diameter = Math.min(getWidth(), getHeight()) - 10;
        int x = (getWidth() - diameter) / 2; // keep the circle centered horizontally
        // int y = (getHeight() - diameter) / 2;

        // dimmed = dark version of the color; lit = full color
        g.setColor(lit ? color : color.darker().darker());
        g.fillOval(x, 0, diameter, diameter);
    }
}
