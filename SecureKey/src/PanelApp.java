import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;
import javax.swing.table.*;

public class PanelApp extends JPanel{
    JTable table;
    DefaultTableModel model;
    JScrollPane scrollPane;
    JButton add,remove,encrypt,decrypt,generate,logOut;
    JTextField username,password,platform;
    JLabel usernameLabel,passwordLabel,platformLabel,status;
    JPanel dataPanel , buttonPanel;
    Listener listner;
    
    PanelApp(){
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        setBackground(new Color(0x262626));
        listner = new Listener();
        
        String[] columns = {"Username","Password","Platform"};
        model = new DefaultTableModel(columns,0);
        table = new JTable(model);
        table.setBackground(new Color(0xa7696a)); 
        scrollPane = new JScrollPane(table);
        JViewport viewport = scrollPane.getViewport();
        viewport.setBackground(new Color(0x262626));
        viewport.setPreferredSize(new Dimension(444,170));
        
        
        buttonPanel = new JPanel(new GridLayout(2, 1 , 0 , 10));
        buttonPanel.setBackground(new Color(0x262626));
        buttonPanel.setPreferredSize(new Dimension(90,70));
        encrypt = new JButton("Encrypt");
        encrypt.setBackground(new Color(0xa7696a));
        encrypt.setFocusable(false);
        encrypt.addActionListener(listner);
        buttonPanel.add(encrypt);
        
        decrypt = new JButton("Decrypt");
        decrypt.setBackground(new Color(0xa7696a));
        decrypt.setFocusable(false);
        decrypt.addActionListener(listner);
        buttonPanel.add(decrypt);
        
        
        logOut = new JButton("Log Out");
        logOut.setBackground(new Color(0xa7696a));
        logOut.setFocusable(false);
        logOut.setPreferredSize(new Dimension(90,30));
        logOut.addActionListener(listner);
        
        dataPanel = new JPanel(new GridLayout(4, 3 , 2 , 3));
        dataPanel.setBackground(new Color(0x262626));
        username = new JTextField();
        password = new JTextField();
        platform = new JTextField();
        
        usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.white);
        
        passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.white);
        
        platformLabel = new JLabel("Platform:");
        platformLabel.setForeground(Color.white);
        
        generate = new JButton("Generate Password");
        generate.setBackground(new Color(0xa7696a));
        generate.setFocusable(false);
        generate.addActionListener(listner);
        
        add = new JButton("Add");
        add.setBackground(new Color(0xa7696a));
        add.setFocusable(false);
        add.addActionListener(listner);
        
        remove = new JButton("Remove");
        remove.setBackground(new Color(0xa7696a));
        remove.setFocusable(false);
        remove.addActionListener(listner);
        JLabel emptyLabel = new JLabel();
        
        dataPanel.add(usernameLabel);
        dataPanel.add(passwordLabel);
        dataPanel.add(platformLabel);
        dataPanel.add(username);
        dataPanel.add(password);
        dataPanel.add(platform);
        dataPanel.add(add);
        dataPanel.add(generate);
        dataPanel.add(emptyLabel);
        dataPanel.add(remove);
        status = new JLabel();
        status.setBounds(30, 350, 210, 20);
        status.setForeground(Color.white);
        
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.insets = new Insets(-70, 10, 10, 10);
        add(scrollPane, constraints);
        constraints.gridx++;
        constraints.insets = new Insets(-180, 10, 10, 10);
        add(buttonPanel, constraints);
        constraints.gridx--;
        constraints.gridy++;
        constraints.insets = new Insets(10, 10, 10, 10);
        add(dataPanel, constraints);
        constraints.gridx++;
        constraints.insets = new Insets(10, 10, -100, 10);
        add(logOut, constraints);
        constraints.gridx--;
        constraints.gridy++;
        constraints.insets = new Insets(10, 10, -90, 10);
        add(status, constraints);
    }
    private class Listener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == logOut){
                reset();
                GUI.getInstance().fm.setFile(null);
                GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "Login");
            }    
           
            if(e.getSource() == generate){
                Random random = new Random();
                StringBuilder parolaGenerata = new StringBuilder();
                for(int i = 0 ; i < 16 ; i++)
                    parolaGenerata.append( (char) (random.nextInt(94) + '!'));
                String parola = parolaGenerata.toString();
                password.setText(parola);
            }
           
            if(e.getSource() == add) 
            {
                model.addRow(new Object[]{username.getText().trim() , password.getText().trim() , platform.getText().trim()});
            } 
            
            if(e.getSource() == remove) 
            {
                if(table.getSelectedRow() != -1){
                    model.removeRow(table.getSelectedRow());
                }
            } 
            
            if(e.getSource() == encrypt)
            {
                if(GUI.getInstance().fm.file != null)
                {
                    StringBuilder sb = new StringBuilder();
                    for(int i = 0 ; i < model.getRowCount() ; i++)
                    {
                        for(int j = 0 ; j < model.getColumnCount() ; j++)
                        {
                            sb.append(model.getValueAt(i, j).toString());
                            if(j < model.getColumnCount() - 1)
                               sb.append(" | ");
                        }
                        sb.append("\n\r");
                    }
                    String key;
                    do{
                        key = JOptionPane.showInputDialog("Insert encryption key of 16 characters");
                        if (key == null) {
                            return; 
                        }
                    }while(key.length() != 16);
                    
                    String encryptedData = Encryption.encrypt(sb.toString(), key);
                    String hashedData = Encryption.hashing(sb.toString());
                    GUI.getInstance().fm.insertInFile(encryptedData , hashedData);
                }
                else 
                    status.setText("Missing file!");
            }
            
            if(e.getSource() == decrypt)
            {
                if(GUI.getInstance().fm.file != null)
                {
                    if(model.getRowCount() == 0){
                    String key;
                    do{
                        key = JOptionPane.showInputDialog("Insert decryption key of 16 characters");
                        if (key == null) {
                            return; 
                        }
                    }while(key.length() != 16);
                    
                    String[] fileData = GUI.getInstance().fm.retrieveFromFile();
                    
                    String data = Encryption.decrypt(fileData[1] , key);
                    if(data != null)
                    {
                        String hashFromDecrypt = Encryption.hashing(data);
                        
                        if(hashFromDecrypt.equals(fileData[0]))
                        {
                            String[] rows = data.split("\n");
                            for(int i = 0 ; i < rows.length-1 ; i++)
                            {
                                String[] fields = rows[i].split("\\|");
                                String username = fields[0].trim();
                                String password = fields[1].trim();
                                String platform = fields[2].trim();
                                model.addRow(new Object[] {username, password, platform});
                            }
                        }
                        
                    }
                    else{
                        status.setText("Wrong key!");
                    }
                    }else{
                        status.setText("Data is already decrypted");
                    }
                }
                else 
                    status.setText("Missing file!");
            }
        }
    }
    private void reset() {
        username.setText("");
        password.setText("");
        platform.setText("");
        model.setRowCount(0);
        status.setText("");
    }
    
}
