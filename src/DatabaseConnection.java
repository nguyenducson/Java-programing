package src;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

class DatabaseConnection {
    
    private Connection conn;
    
    public DatabaseConnection(JFrame root) {
        conn = connectToDatabase(root);
    }
    /**
     * Connect to database
     */
    private Connection connectToDatabase(JFrame root) {
        String userName = "sonnguyen98";
        String password = "Letmein309";
        String url = "jdbc:sqlserver://localhost;databaseName=FinalProject";
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(url, userName, password);
            return conn;
        } catch (ClassNotFoundException e) {
            notifyMessage(root, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            notifyMessage(root, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    Connection getConnection() {
        return conn;
    }
    
    /**
     * Display a message panel
     */
    private void notifyMessage(JFrame root, String message, String title, int messageType) {
        JOptionPane.showMessageDialog(root, message, title, messageType);
    }

}
