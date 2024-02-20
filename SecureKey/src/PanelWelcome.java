
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

public class PanelWelcome extends JPanel {
    private JLabel welcomeLabel , label1 , iconLabel;
    private JButton createFileButton;
    private JButton loadFileButton;
    private JPanel buttonPanel;
    Listener listner;

    public PanelWelcome() {
        setLayout(new GridBagLayout());
        setBackground(new Color(0x262626));
        listner = new Listener();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;

        welcomeLabel = new JLabel("Welcome, user123!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(new Color(0xa7696a));
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(welcomeLabel, gbc);

        label1 = new JLabel("<html><p style='width: 100%; text-align: center;'>To continue, load the file with encrypted passwords.<br>If you are new to this app, create a new file in which passwords will be saved.</p></html>");
        label1.setFont(new Font("Arial", Font.PLAIN, 14));
        label1.setForeground(Color.white);
        gbc.gridy++;
        add(label1, gbc);

        iconLabel = new JLabel(new ImageIcon("/home/sanda/NetBeansProjects/estic/build/classes/icon2.png"));
        gbc.gridy++;
        add(iconLabel, gbc);
        
        
        buttonPanel = new JPanel(new GridLayout(2, 1 , 0 , 10));
        buttonPanel.setBackground(new Color(0x262626));
        
        createFileButton = new JButton("Create file");
        createFileButton.setPreferredSize(new Dimension(160,30));
        createFileButton.setFont(new Font("Arial", Font.PLAIN, 17));
        createFileButton.setBackground(new Color(0xa7696a));
        createFileButton.setFocusable(false);
        createFileButton.addActionListener(listner);
        buttonPanel.add(createFileButton);
        

        loadFileButton = new JButton("Load file");
        loadFileButton.setBackground(new Color(0xa7696a));
        loadFileButton.setFont(new Font("Arial", Font.PLAIN, 17));
        loadFileButton.setFocusable(false);
        loadFileButton.addActionListener(listner);
        buttonPanel.add(loadFileButton);
        
        gbc.gridy++;
        add(buttonPanel,gbc);
    }

    private class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == createFileButton){
                String fileName = JOptionPane.showInputDialog("Enter filename:");
                if (fileName != null && !fileName.isEmpty()) {
                    GUI.getInstance().fm.createFile(fileName);
                    GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "App");
                }
            }
            if(e.getSource() == loadFileButton){
                GUI.getInstance().fm.loadFile();
                if(GUI.getInstance().fm.getFile() != null){
                    GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "App");
                }
            }
        }
    }
}
