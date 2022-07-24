import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.awt.EventQueue;

public class Main {
    public static void main (String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

        EventQueue.invokeLater(new Runnable() {
            public void run() { new Login().setVisible(true); }   
        });
    }
}
