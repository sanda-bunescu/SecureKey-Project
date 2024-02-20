import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class FileManager {
    
    File file;
   
    public void setFile(File file) {
        this.file = file;
    }

    public File getFile()
    {
        return file;
    }
    
    public void createFile(String fileName){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        
        int result = fileChooser.showSaveDialog(GUI.getInstance());
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                File selectedDirectory = fileChooser.getSelectedFile();
                
                file = new File(selectedDirectory.getAbsolutePath()+File.separator+fileName);
                file.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        } 
    }
    
    public void loadFile(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showSaveDialog(GUI.getInstance());
        
        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        } 
    }
    
    public void insertInFile(String encryptedData , String hashedData)
    {
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(hashedData + "\n");
            fileWriter.append(encryptedData);
            fileWriter.close();
        } catch (IOException ex) {
            System.out.println("INSERTING IN FILE ERROR");
        }
    }
    
    public String[] retrieveFromFile()
    {
        String hash = null;
        String encryption = null;
        try {
            Scanner scanner = new Scanner(file);
            hash = scanner.nextLine();
            encryption = scanner.nextLine();
            
        } catch (FileNotFoundException ex) {
            System.out.println("RETRIEVING FROM FILE ERROR!");
        }
        
        return new String[] {hash , encryption};
    }
    
    
}
