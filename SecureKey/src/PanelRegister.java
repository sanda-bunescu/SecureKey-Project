import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;

public class PanelRegister extends JPanel{
    
    JTextField username;
    JPasswordField password;
    JButton register;
    JButton login;
    JLabel error , signUp;
    Listener listner;
    
    PanelRegister(){
        setLayout(new GridBagLayout());
        setBackground(new Color(0x262626));
        listner = new Listener();
        GridBagConstraints constraints = new GridBagConstraints();
        
        constraints.insets = new Insets(5,10,5,10);
        
        signUp = new JLabel("Sign Up");
        signUp.setPreferredSize(new Dimension(250,100));
        signUp.setFont(new Font("Arial",Font.BOLD, 24 ));
        signUp.setForeground(Color.white);
        signUp.setHorizontalAlignment(SwingConstants.CENTER);
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(signUp,constraints);
        
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
        
        register = new JButton("Register");
        register.setBackground(new Color(0xa7696a));
        register.addActionListener(listner);
        register.setPreferredSize(new Dimension(200,50));
        register.setFocusable(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(register,constraints);
        
        login = new JButton("Login");
        login.setBackground(new Color(0xa7696a));
        login.addActionListener(listner);
        login.setFocusable(false);
        login.setPreferredSize(new Dimension(200,50));
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(login,constraints);
        
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
                if(!username.getText().trim().equals("") && !Arrays.equals(password.getPassword(),new char[0])){
                    if(GUI.getInstance().db.retrieveUName(username.getText().trim()) == null ){
                        if(isStrongPassword(Arrays.toString(password.getPassword()))){
                            GUI.getInstance().db.insertInDB(username.getText().trim(), Encryption.hashing(Arrays.toString(password.getPassword())));
                            reset();
                            GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "Login");
                        }
                        else{
                            error.setText("Weak Password!");
                        }
                               
                    }else{
                        error.setText("Username is already taken!");
                    }  
                }else{
                    error.setText("You have to fill all the blanks!");
                }  
            }
            
            if(e.getSource() == login){
                reset();
                GUI.getInstance().cardLayout.show(GUI.getInstance().cardPanel, "Login");
            } 
        }
    }
    
    public static boolean isStrongPassword(String string) {
        
        boolean hasUppercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;
        String uppercaseRegex = ".*[A-Z].*";
        String digitRegex = ".*\\d.*";
        String specialCharRegex = ".*[.!@#$%^&*()].*";

        if(string.length() > 7)
        {
            hasUppercase = string.matches(uppercaseRegex);
            hasDigit = string.matches(digitRegex);
            hasSpecialChar = string.matches(specialCharRegex);
        }

        return hasUppercase && hasDigit && hasSpecialChar;
    }
    
    public void reset() {
        username.setText("");
        password.setText("");
    }
}
