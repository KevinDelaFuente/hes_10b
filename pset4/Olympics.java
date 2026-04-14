import javax.swing.*;
import java.awt.*;

// This class paints the olympic rings using Graphics2d
// 
public class Olympics {
    ringDrawer rd = new ringDrawer();
    // thickRings rd = new thickRings();

    public static void main(String[] args){
        Olympics olympics = new Olympics();

        JFrame frame = new JFrame();
        frame.setSize(450, 300);
        frame.setTitle("Olympic Thick Rings");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(olympics.rd);
        frame.setVisible(true);
    }

    // TOO THIN!
    // class ringDrawer extends JPanel {
    //     public void paintComponent(Graphics g) {
    //         super.paintComponent(g);
    //         drawRing(g, 50, 50, Color.BLUE.darker());
    //         drawRing(g, 160, 50, Color.BLACK);
    //         drawRing(g, 270, 50, Color.RED.darker());
    //         drawRing(g, 105, 120, Color.YELLOW.darker());
    //         drawRing(g, 215, 120, Color.GREEN.darker());
    //     }

    //     public void drawRing(Graphics g, int x, int y, Color color) {
    //         g.setColor(color);
    //         g.drawOval(x, y, 100, 100);
    //     }
    // }

    class ringDrawer extends JPanel{
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setStroke(new BasicStroke(10)); 
            // using RGB picker from https://rgbcolorpicker.com/
            drawRing(g2d, 50, 65, new Color(0,62,255));
            drawRing(g2d, 170, 65, Color.BLACK);
            drawRing(g2d, 290, 65, new Color(194,18,18));
            drawRing(g2d, 110, 120, new Color(224,188,27));
            drawRing(g2d, 230, 120, new Color(8, 99, 8));
        }

        public void drawRing(Graphics g, int x, int y, Color color) {
            g.setColor(color);
            g.drawOval(x, y, 100, 100); 
        }
    }
}



