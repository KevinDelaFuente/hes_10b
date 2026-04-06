// This program asks the user for their age and then tells them if they are young or old. 
// It also handles the case where the user enters something that is not an integer.

import javax.swing.*;
// import java.awt.*;

public class Age {
    public static void main(String [] args){
        Integer age = null;
        while (age == null) {
            try {
                age = Integer.parseInt(JOptionPane.showInputDialog (null, "How old are you?"));
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog (null, "Please enter an integer!");
            }
        }
        
        if (age < 40) {
            JOptionPane.showMessageDialog (null, "You are young!");
        } else {
            JOptionPane.showMessageDialog (null, "You are old!");
        }
    }
}
