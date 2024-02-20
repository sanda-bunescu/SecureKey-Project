import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class PanelLogin extends JPanel{
    
    JTextField username;
    JPasswordField password;
    JButton login,register;
    JLabel error,welcome;
    final Listener listener;
    
    PanelLogin(){
        setLayout(new GridBagLayout());
        setBackground(new Color(0x262626));
        listener = new Listener();
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5,10,5,10);
        
        welcome = new JLabel("SecureKey");
        welcome.setPreferredSize(new Dimension(250,100));
        welcome.setFont(new Font("Arial",Font.BOLD, 24 ));
        welcome.setForeground(Color.white);
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(welcome,constraints);
        
        username = new JTextField();
        username.setPreferredSize(new Dimension(200,50));
        username.setBackground(new Color(0x3D3E3E));
        username.setBorder(null);
        username.setForeground(Color.WHITE);
        username.setCaretColor(Color.WHITE);
        
        username.addFocusListener(new FocusListener() {
            
            @Override
            public void focusGained(FocusEvent u) {
                
                if (username.getText().equals("Username")) {
                    username.setFocusable(true);
                    username.setText("");
                    username.setForeground(Color.WHITE);
                    username.setCaretColor(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent u) {
                
                if (username.getText().isEmpty()) {
                    username.setText("Username");
                    username.setForeground(Color.GRAY);
                    username.setCaretColor(Color.GRAY);
                }
            }
        });
        
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(username,constraints);
        
        password = new JPasswordField();
        password.setPreferredSize(new Dimension(200,50));
        password.setBackground(new Color(0x3D3E3E));
        password.setBorder(null);
        password.setText("Password");
        password.setForeground(Color.GRAY);
        password.setCaretColor(Color.WHITE);
        
        password.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                char[] passwordChars = password.getPassword();
                String passwordText = new String(passwordChars);

                if (passwordText.equals("Password")) {
                    password.setText("");
                    password.setForeground(Color.WHITE);
                    password.setCaretColor(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                char[] passwordChars = password.getPassword();
                String passwordText = new String(passwordChars);

                if (passwordText.isEmpty()) {
                    password.setText("Password");
                    password.setForeground(Color.GRAY);
                    password.setCaretColor(Color.GRAY);
                }
            }
        });
        
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(password,constraints);
        
        login = new JButton("Login");
        login.setBackground(new Color(0xa7696a));
        login.addActionListener(listener);
        login.setFocusable(false);
        login.setPreferredSize(new Dimension(200,50));
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(login,constraints);
        
        register = new JButton("Register");
        register.setBackground(new Color(0xa7696a));
        register.addActionListener(listener);
        register.setFocusable(false);
        register.setPreferredSize(new Dimension(200,50));
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(register,constraints);
        
        error = new JLabel();
        error.setPreferredSize(new Dimension(230,50));
        error.setForeground(Color.red);
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(error,constraints);
    }
    
    private class Listener implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == register){
                reset();
                GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "Register");
            }
            
            if(e.getSource() == login){
                if(GUI.getInstance().db.verifyFromDB(username.getText().trim(), Encryption.hashing(Arrays.toString(password.getPassword())))){
                    reset();
                    GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel,"Welcome");
                }
                else{
                    if(username.getText().isEmpty() || Arrays.equals(password.getPassword(),new char[0]))
                        error.setText("You have to fill all the blanks!");
                    else
                        error.setText("Wrong username or password!");
                }
                
            }
        }
    }
    public void reset() {
        error.setText("");
        username.setText("");
        password.setText("");
    }
}
