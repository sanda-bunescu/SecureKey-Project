import java.sql.*;

public class DatabaseManager {
    private final String url = "jdbc:mysql://127.0.0.1:3306/users";
    private final String username = "root";
    private final String password = "sanda";
    private Connection connection;
    
    
    DatabaseManager(){
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException ex) {
            System.out.println("DATABASE CONNECTION ERROR");
        }
    }
    

    
    public void insertInDB(String username, String password){
        
        String query = "insert into Users(username,password) values (?,?)";
        
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,username);
            preparedStatement.setString(2,password);
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            System.out.println("INSERTING IN DATABASE ERROR");
        }
            
    }
    
    public String retrieveUName(String name){
        
        String retrievedUsername = null;
        String query = "select username from Users where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next() == true)
                retrievedUsername = resultSet.getString(1);
            
        } catch (SQLException ex) {
            System.out.println("RETRIVING USERNAME ERROR");
        }
        return retrievedUsername;
    }
    public String retrievePWord(String pass , String name){
        
        String query = "select password from Users where username = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            //hashing
            String retrievedPassword = resultSet.getString(1);
            //System.out.println(retrievedPassword);
            if(retrievedPassword.equals(pass))
                return retrievedPassword; 
            
        } catch (SQLException ex) {
            System.out.println("RETRIVING PASSWORD ERROR");
        }
        return null; 
    }

    public boolean verifyFromDB(String name , String pass){
        return (retrieveUName(name) != null && retrievePWord(pass,name) != null);
    }
    
    public void closeConnection()
    {
        try {
            connection.close();
        } catch (SQLException ex) {
            System.out.println("CLOSING CONNECTION ERROR");
        }
        
    }
}
