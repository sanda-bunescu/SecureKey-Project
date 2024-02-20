import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUI extends JFrame{

    JPanel register,login,app,welcome;
    final JPanel cardPanel;
    final CardLayout cardLayout;
    static GUI instance;
    DatabaseManager db;
    FileManager fm;
    
    private GUI(){
        super("SecureKey");
        db = new DatabaseManager();
        fm = new FileManager();
        
        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        login = new PanelLogin();
        register = new PanelRegister();
        app = new PanelApp();
        welcome = new PanelWelcome();
        
        cardPanel.add(login,"Login");
        cardPanel.add(register,"Register");
        cardPanel.add(app,"App");
        cardPanel.add(welcome,"Welcome");
        
        cardLayout.show(cardPanel, "Login");
        
        add(cardPanel);
        
        setMinimumSize(new Dimension(600, 400));
        setSize(580,450);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                db.closeConnection();
                dispose();
                System.exit(0);
            }
        });
        this.setLocationRelativeTo(null);
        setVisible(true);
        
    }
    
    public static GUI getInstance(){
        if(instance == null) instance = new GUI();
        return instance;
    }
   
}
