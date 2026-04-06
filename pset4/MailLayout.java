import javax.swing.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;

// This program creates a simple email composition window using GridBagLayout. 
// If send button is clicked, the text in the body area is written to a file called outbox.txt and the subject field and body area are cleared.
public class MailLayout extends JFrame 
{ 
   private Container container;
   private GridBagLayout gbLayout;
   private GridBagConstraints gbConstraints; 
   private JTextField toField, ccField, bccField, subjectField;
   private JComboBox<String> fromBox;
   private JTextArea bodyArea;


   public MailLayout ()
   {
      super ("New Message");

      container = this.getContentPane ();
      gbLayout = new GridBagLayout ();
      container.setLayout (gbLayout );   
      gbConstraints = new GridBagConstraints ();


      // Send button: does not grow
      JButton sendBtn = new JButton("Send");
      gbConstraints.fill = GridBagConstraints.NONE;
      gbConstraints.weightx = 0;
      gbConstraints.weighty = 0;
      gbConstraints.insets = new Insets(4, 4, 4, 4);
      sendBtn.addActionListener(new ButtonListener());
      addComponent(sendBtn, 0, 0, 1, 1);

      // Labels: fixed size, don't grow
      gbConstraints.fill = GridBagConstraints.NONE;
      gbConstraints.anchor = GridBagConstraints.WEST;
      addComponent(new JLabel("To:"),      1, 0, 1, 1);
      addComponent(new JLabel("Cc:"),      2, 0, 1, 1);
      addComponent(new JLabel("Bcc:"),     3, 0, 1, 1);
      addComponent(new JLabel("From:"),    4, 0, 1, 1);
      addComponent(new JLabel("Subject:"), 5, 0, 1, 1);

      // Fields: grow horizontally, but not vertically
      toField      = new JTextField();
      ccField      = new JTextField();
      bccField     = new JTextField();
      fromBox      = new JComboBox<>(new String[]{"Kevin <kevin@harvard.edu>", "Larry <larry@harvard.edu>", "Curly <curly@harvard.edu>"});
      subjectField = new JTextField();

      // Adding listerner to subject field: when the user types something in the subject field JFrame title is updated
      subjectField.getDocument().addDocumentListener(new DocumentListener() {
         public void changedUpdate(DocumentEvent e) {
            MailLayout.this.setTitle((subjectField.getText().length() > 0 ) ? subjectField.getText() : "New Message");
            // System.out.println("1. Subject field changed: " + subjectField.getText());
         }
         public void removeUpdate(DocumentEvent e) {
            MailLayout.this.setTitle((subjectField.getText().length() > 0 ) ? subjectField.getText() : "New Message");
            // System.out.println("2. Subject field changed: " + subjectField.getText());
         }
         public void insertUpdate(DocumentEvent e) {
            MailLayout.this.setTitle((subjectField.getText().length() > 0 ) ? subjectField.getText() : "New Message");
            // System.out.println("3. Subject field changed: " + subjectField.getText()); 
         }
      });

      gbConstraints.fill = GridBagConstraints.HORIZONTAL;
      gbConstraints.weightx = 1;
      gbConstraints.weighty = 0;
      addComponent(toField,      1, 1, 1, 1);
      addComponent(ccField,      2, 1, 1, 1);
      addComponent(bccField,     3, 1, 1, 1);
      addComponent(fromBox,      4, 1, 1, 1);
      addComponent(subjectField, 5, 1, 1, 1);

      // --- Body: grows both ways, spans both columns ---
      bodyArea = new JTextArea("Lorem ipsum dolor sit amet.", 1, 2);
      bodyArea.setLineWrap(true);
      bodyArea.setWrapStyleWord(true);
      JScrollPane scrollPane = new JScrollPane(bodyArea); // putting the bodyarea insicde a scroll pane

      gbConstraints.fill = GridBagConstraints.BOTH;
      gbConstraints.weightx = 1;
      gbConstraints.weighty = 1;
      addComponent(scrollPane, 6, 0, 2, 1);

      setSize(550, 450);
      setVisible(true);

   }
   
   // addComponent adds a component to the container with the specified GridBagConstraints
   private void addComponent (Component c, int row, int column, int width, int height ) {
      // set gridx and gridy 
      gbConstraints.gridx = column;
      gbConstraints.gridy = row;

      // set gridwidth and gridheight
      gbConstraints.gridwidth = width;   
      gbConstraints.gridheight = height;

      // set constraints
      gbLayout.setConstraints (c, gbConstraints );  
      container.add (c);      // add component 
   }

   // ActionListener for the Send button: when the button is clicked, the text in the body area is written to a file called outbox.txt 
   // and the subject field and body area are cleared
   class ButtonListener implements ActionListener {
      public void actionPerformed (ActionEvent ae){
         System.out.println("Send button clicked");
         String email = bodyArea.getText();
         try {
            PrintWriter output = new PrintWriter("outbox.txt");
            output.print(email);
            output.close();
            toField.setText("");
            ccField.setText("");
            bccField.setText("");
            subjectField.setText("");
            bodyArea.setText("");
         } catch(Exception e) {
            e.printStackTrace();
         }

      }
   }

   public static void main (String [] args )
   {
      MailLayout app = new MailLayout ();
      app.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
   }

}