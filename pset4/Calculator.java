import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

// Calculator is the GUI class. It builds the JFrame with a display and buttons,
// then delegates all math logic to CalcBackend. As specified, this class has no computation
// of its own — it only lays out the interface, listens for button clicks,
// feeds them to the backend via feedChar(), and updates the display via getDisplayVal().
public class Calculator extends JFrame {
    private CalcBackend backend = new CalcBackend();
    private JTextField display;
    private Container container;    
    private GridBagLayout gbLayout;
    private GridBagConstraints gbConstraints;
    private Font font = new Font(Font.SERIF, Font.BOLD, 24);

    // Creating color schemes for buttons
    private static final Color[] STYLE_SPECIAL  = { new Color(255, 170, 180), Color.blue};
    

    // Constructor builds the full calculator UI: display on top, buttons below.
    // I used GridBagLayout so I can control each button's position, size (colspan/rowspan),
    // and spacing individually.
    public Calculator () 
    {
        super ("Calculator");
        container = this.getContentPane ();
        gbLayout = new GridBagLayout ();
        container.setLayout (gbLayout );   
        gbConstraints = new GridBagConstraints ();

        // Display 
        display = new JTextField("0");
        display.setEditable(false);
        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setFocusable(false);                                
        display.setPreferredSize(new Dimension(0, 70));     
        display.setBackground(Color.YELLOW);                        
        display.setForeground(Color.BLACK);                         
        display.setFont(font);                                     

    
        gbConstraints.fill = GridBagConstraints.HORIZONTAL;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 0;
        gbConstraints.insets = new Insets(4, 4, 4, 4);
        addComponent(display, 0, 0, 4, 1);
    
        // Buttons — labels and styles are parallel arrays
        gbConstraints.fill = GridBagConstraints.BOTH;
        gbConstraints.weightx = 1;
        gbConstraints.weighty = 1;
        gbConstraints.insets = new Insets(1,1,1,1);

        String[] buttonLabels = {"C", "√", "/", "*", "7", "8", "9", "-", "4", "5", "6", "+", "1", "2", "3", "0", ".", "="};
        Color[][] buttonStyles = {
            null,          // C
            STYLE_SPECIAL, // √
            STYLE_SPECIAL, // /
            STYLE_SPECIAL, // *
            null, null, null, // 7, 8, 9
            STYLE_SPECIAL, // -
            null, null, null, // 4, 5, 6
            STYLE_SPECIAL, // +
            null, null, null, // 1, 2, 3
            null, null,    // 0, .
            STYLE_SPECIAL  // =
        };

        JButton[] buttons = new JButton[18];
        for (int i = 0; i < 18; i++) {
            buttons[i] = makeButton(buttonLabels[i], buttonStyles[i]);
        }

        // Position first 15 buttons in a 4-column grid (rows 1-4)
        for (int i = 0; i < 15; i++) {
            addComponent(buttons[i], 1 + i / 4, i % 4, 1, 1);
        }
        addComponent(buttons[15], 5, 0, 2, 1); // "0" spans two columns
        addComponent(buttons[16], 5, 2, 1, 1); // "."
        addComponent(buttons[17], 4, 3, 1, 2); // "=" spans two rows

    }

    // Creates a JButton with the given label and optional color style.
    // If style is null, the button keeps the default OS appearance.
    // Otherwise, background and foreground are applied and the native border is removed.
    private JButton makeButton(String label, Color[] style) {
        JButton btn = new JButton(label);
        if (style != null) {
            btn.setBackground(style[0]);
            btn.setForeground(style[1]);
            btn.setOpaque(true);
            btn.setBorderPainted(false);
            // btn.setRolloverEnabled(false);
        }
        btn.setFont(font);
        btn.addActionListener(new ButtonListener());
        return btn;
    }
    // Helper method from GridBagLayoutDemo that adds components at the given row/column
    // with the specified column and row span
    private void addComponent (Component c, int row, int column, int width, int height ) {
        // set gridx and gridy 
        gbConstraints.gridx = column; // which column
        gbConstraints.gridy = row; // which row

        // set gridwidth and gridheight
        gbConstraints.gridwidth = width;   // how many columns wide
        gbConstraints.gridheight = height; // how many rows tall

        // set constraints
        gbLayout.setConstraints (c, gbConstraints );  
        container.add (c);      // add component 
    }


    // ButtonListener handles all button clicks. It reads the button's label,
    // passes its first character to the backend via feedChar(), and then
    // updates the display with whatever getDisplayVal() returns.
    class ButtonListener implements ActionListener {
        public void actionPerformed (ActionEvent ae){
            // System.out.println(((JButton) ae.getSource()).getText() + " button clicked");
            backend.feedChar(((JButton) ae.getSource()).getText().charAt(0));
            display.setText(backend.getDisplayVal());
        }
    }
    // Creates the Calculator window and makes it visible.
    public static void main (String [] args )
    {
        Calculator app = new Calculator ();
        app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
        app.setSize(300, 400);
        app.setVisible(true);
    }
    
}