package analyser;


import javax.swing.*;
import java.awt.event.*;

public class swing1 extends JFrame {

   public swing1() {
      super("titre de l'application");

      WindowListener l = new WindowAdapter() {
         public void windowClosing(WindowEvent e){
            System.exit(0);
         }
      };

      addWindowListener(l);
      setSize(200,100);
      setVisible(true);
   }

   public static void main(String [] args){
      JFrame frame = new swing1();
   }
}