/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package sunrise_dental_clinic;

import javax.swing.SwingUtilities;
import view.Login;

/**
 *
 * @author ASUS
 */
public class Sunrise_Dental_Clinic {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         SwingUtilities.invokeLater(() -> {
            Login loginFrame = new Login();
            loginFrame.setLocationRelativeTo(null);
            loginFrame.setVisible(true);      
        });
    }
    
}
