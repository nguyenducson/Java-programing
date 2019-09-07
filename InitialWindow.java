package src;

import java.awt.EventQueue;
import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Toolkit;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

public class InitialWindow {

    private JFrame frame;
    private JTextField tfUserID;
    private JPasswordField passwordField;
    private JPanel bannerPane;
    private JLabel pictureLabel;
    private JPanel backPanel;
    private JLabel lblRoles;
    private JLabel lblUserID;
    private JLabel lblPassword;
    private JButton btnLogin;
    
    private Connection conn;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    InitialWindow window = new InitialWindow();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public InitialWindow() {
        initialize();
    }
    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        
        //Establishing components for GUI
        frame = new JFrame();
        frame.setBounds(100, 100, 1194, 770);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Education Information");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(InitialWindow.class.getResource("student.png")));
        frame.setResizable(false);
        
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{200, 0, 100, 0};
        gridBagLayout.rowHeights = new int[]{140, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        frame.getContentPane().setLayout(gridBagLayout);
        
        bannerPane = new JPanel();
        GridBagConstraints gbc_bannerPane = new GridBagConstraints();
        gbc_bannerPane.gridwidth = 3;
        gbc_bannerPane.insets = new Insets(0, 0, 0, 0);
        gbc_bannerPane.fill = GridBagConstraints.BOTH;
        gbc_bannerPane.gridx = 0;
        gbc_bannerPane.gridy = 0;
        frame.getContentPane().add(bannerPane, gbc_bannerPane);
        GridBagLayout gbl_bannerPane = new GridBagLayout();
        gbl_bannerPane.columnWidths = new int[]{1194, 0};
        gbl_bannerPane.rowHeights = new int[]{140, 0};
        gbl_bannerPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
        gbl_bannerPane.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        bannerPane.setLayout(gbl_bannerPane);
        bannerPane.setOpaque(false);
        
        pictureLabel = new JLabel(new ImageIcon(InitialWindow.class.getResource("banner.png")));
        GridBagConstraints gbc_pictureLabel = new GridBagConstraints();
        gbc_pictureLabel.fill = GridBagConstraints.BOTH;
        bannerPane.add(pictureLabel, gbc_pictureLabel);
        
        backPanel = new ImagePanel("background.jpg");
        GridBagConstraints gbc_backPanel = new GridBagConstraints();
        gbc_backPanel.gridwidth = 3;
        gbc_backPanel.insets = new Insets(0, 0, 0, 0);
        gbc_backPanel.fill = GridBagConstraints.BOTH;
        gbc_backPanel.gridx = 0;
        gbc_backPanel.gridy = 1;
        frame.getContentPane().add(backPanel, gbc_backPanel);
        GridBagLayout gbl_backPanel = new GridBagLayout();
        gbl_backPanel.columnWidths = new int[]{400, 0, 0};
        gbl_backPanel.rowHeights = new int[]{80, 100, 100, 100, 0};
        gbl_backPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_backPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        backPanel.setLayout(gbl_backPanel);
        
        lblRoles = new JLabel("Input UserID and Password to login");
        lblRoles.setForeground(new Color(0, 128, 0));
        lblRoles.setHorizontalAlignment(SwingConstants.CENTER);
        lblRoles.setFont(new Font("Tahoma", Font.BOLD, 24));
        GridBagConstraints gbc_lblRoles = new GridBagConstraints();
        gbc_lblRoles.fill = GridBagConstraints.VERTICAL;
        gbc_lblRoles.insets = new Insets(0, 0, 5, 0);
        gbc_lblRoles.gridwidth = 2;
        gbc_lblRoles.gridx = 0;
        gbc_lblRoles.gridy = 0;
        backPanel.add(lblRoles, gbc_lblRoles);
        
        lblUserID = new JLabel("UserID");
        lblUserID.setForeground(Color.MAGENTA);
        lblUserID.setFont(new Font("Tahoma", Font.PLAIN, 18));
        lblUserID.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblUserID = new GridBagConstraints();
        gbc_lblUserID.anchor = GridBagConstraints.EAST;
        gbc_lblUserID.insets = new Insets(0, 0, 5, 20);
        gbc_lblUserID.gridx = 0;
        gbc_lblUserID.gridy = 1;
        backPanel.add(lblUserID, gbc_lblUserID);
        
        tfUserID = new JTextField();
        tfUserID.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_tfUserID = new GridBagConstraints();
        gbc_tfUserID.fill = GridBagConstraints.BOTH;
        gbc_tfUserID.insets = new Insets(30, 50, 30, 450);
        gbc_tfUserID.gridx = 1;
        gbc_tfUserID.gridy = 1;
        backPanel.add(tfUserID, gbc_tfUserID);
        tfUserID.setColumns(10);
        
        lblPassword = new JLabel("Password");
        lblPassword.setForeground(Color.MAGENTA);
        lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
        GridBagConstraints gbc_lblPassword = new GridBagConstraints();
        gbc_lblPassword.anchor = GridBagConstraints.EAST;
        gbc_lblPassword.insets = new Insets(0, 0, 5, 20);
        gbc_lblPassword.gridx = 0;
        gbc_lblPassword.gridy = 2;
        backPanel.add(lblPassword, gbc_lblPassword);
        
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_passwordField = new GridBagConstraints();
        gbc_passwordField.fill = GridBagConstraints.BOTH;
        gbc_passwordField.insets = new Insets(30, 50, 30, 450);
        gbc_passwordField.gridx = 1;
        gbc_passwordField.gridy = 2;
        backPanel.add(passwordField, gbc_passwordField);
        
        btnLogin = new JButton("Login");
        btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 18));
        GridBagConstraints gbc_btnLogin = new GridBagConstraints();
        gbc_btnLogin.insets = new Insets(10, 140, 50, 540);
        gbc_btnLogin.fill = GridBagConstraints.BOTH;
        gbc_btnLogin.gridx = 1;
        gbc_btnLogin.gridy = 3;
        backPanel.add(btnLogin, gbc_btnLogin);
        
        //Establishing connection to database.
        conn = (new DatabaseConnection(frame)).getConnection();
        
        /**
         * Clear password field when people logs out.
         */
        frame.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent arg0) {
                if (arg0.getPropertyName().equals("contentPane"))
                    passwordField.setText("");
            }
        });
        
        /**
         * Utility to enable button login when both userID and password are provided, otherwise disable it.
         */
        backPanel.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent arg0) {}
            
            @Override
            public void mousePressed(MouseEvent arg0) {}
            
            @Override
            public void mouseExited(MouseEvent arg0) {}
            
            @Override
            public void mouseEntered(MouseEvent arg0) {
                if (tfUserID.getText().equals("") || toPasswordString(passwordField.getPassword()).equals(""))
                    btnLogin.setEnabled(false);
                else btnLogin.setEnabled(true);
            }
            
            @Override
            public void mouseClicked(MouseEvent arg0) {}
        });
        
        /**
         * Process login action and set appropriate working window.
         */
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                Statement stmt = null;
                String query = null;
                if (conn == null)
                    conn = (new DatabaseConnection(frame)).getConnection();
                try {
                    stmt = conn.createStatement();
                    String selectedRole = tfUserID.getText();
                    if (selectedRole.matches("(\\d)+"))                         // Regular Expression to confirm student ID
                        query = "SELECT STD_ID, STDPass FROM STUDENT";
                    else if (selectedRole.matches("TC(\\d)+"))                  // Regular Expression to confirm teacher ID
                        query = "SELECT TC_ID, TCPass FROM TEACHER";
                    else
                        query = "SELECT MNG_ID, MNGPass FROM MANAGER";
                   
                    ResultSet rs = stmt.executeQuery(query);
                    boolean checkExistance = false;
                    while (rs.next()) {
                        if (rs.getString(1).equals(tfUserID.getText()) && rs.getString(2).equals(toPasswordString(passwordField.getPassword()))) {
                            checkExistance = true;
                            break;
                        }
                    }
                    if (!checkExistance) {
                        notifyMessage("UserID or Password is invalid", "Error", JOptionPane.ERROR_MESSAGE);
                        passwordField.setText("");
                        return;
                    }
                    
                    if (selectedRole.matches("(\\d)+")) {           //if match go to student window
                        StudentWindow studentFrame = new StudentWindow(tfUserID.getText(), conn, frame);
                        frame.setContentPane(studentFrame.getContentPane());
                        frame.setVisible(true);
                    }
                    else if (selectedRole.matches("TC(\\d)+")) {    //if match go to teacher window
                        TeacherWindow teacherFrame = new TeacherWindow(tfUserID.getText(), conn, frame);
                        frame.setContentPane(teacherFrame.getContentPane());
                        frame.setVisible(true);
                    }
                    else {                                          //otherwise go to manager window
                        ManagerWindow managerFrame = new ManagerWindow(tfUserID.getText(), conn, frame);
                        frame.setContentPane(managerFrame.getContentPane());
                        frame.setVisible(true);
                    }
                    
                } catch (SQLException ex) {
                    notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (stmt != null) { try {
                        stmt.close();
                    } catch (SQLException e1) {
                        notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
        });
    }
    
    /**
     * Display a message panel
     */
    private void notifyMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(frame, message, title, messageType);
    }
    
    /**
     * Convert characters array of password to String type
     */
    private String toPasswordString(char[] pass) {
        String password = "";
        for (int i=0; i<pass.length; ++i)
            password += pass[i];
        return password;
    }
    
}


