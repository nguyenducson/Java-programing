package src;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.RefineryUtilities;

import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTextArea;

class TeacherWindow extends JFrame {

    private static Connection conn;
    private static JFrame root;
    private final String teacherID;
    private JPanel bannerPanel;
    private JLabel lblLoginID;
    JButton btnLogout;
    private JPanel navigatingPanel;
    private JPanel displayPanel;
    private JLabel lblTitle1;
    private JLabel lblChosenYear;
    private JComboBox<String> cbBYear;
    private JLabel lblChosenSemester;
    private JComboBox<String> cbBSemester;
    private JLabel lblChosenSubject;
    private JComboBox<String> cbBSubject;
    private JButton btnOK;
    private JButton btnMode;
    private JSeparator separator;
    private JLabel lblTitle2;
    private JLabel lblInputYear;
    JTextField tfInputYear;
    private JLabel lblInputSemester;
    JTextField tfInputSemester;
    private JLabel lblInputSubject;
    JTextField tfInputSubject;
    private JButton btnInputFile;
    private JPanel tablePanel;
    private JPanel detailPanel;
    private JLabel lblTableTitle;
    private JScrollPane tableScrollPane;
    private JTable studentTable;
    private JScrollPane textScrollPane;
    private JTextArea taInfo;
    
    private JFileChooser fileChosser;    
 
    /**
     * Create the frame.
     */
    public TeacherWindow(String teacherID, Connection conn, JFrame root) {
        TeacherWindow.conn = conn;
        TeacherWindow.root = root;
        this.teacherID = teacherID;
        setBounds(100, 100, 1194, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Teacher's Workspace");
        setIconImage(Toolkit.getDefaultToolkit().getImage(InitialWindow.class.getResource("student.png")));
        setResizable(false);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{300, 0, 0};
        gridBagLayout.rowHeights = new int[]{140, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        getContentPane().setLayout(gridBagLayout);
        
        bannerPanel = new ImagePanel("banner.png");
        GridBagConstraints gbc_bannerPanel = new GridBagConstraints();
        gbc_bannerPanel.gridwidth = 2;
        gbc_bannerPanel.insets = new Insets(0, 0, 5, 0);
        gbc_bannerPanel.fill = GridBagConstraints.BOTH;
        gbc_bannerPanel.gridx = 0;
        gbc_bannerPanel.gridy = 0;
        getContentPane().add(bannerPanel, gbc_bannerPanel);
        bannerPanel.setOpaque(false);
        GridBagLayout gbl_bannerPanel = new GridBagLayout();
        gbl_bannerPanel.columnWidths = new int[]{0, 0, 0};
        gbl_bannerPanel.rowHeights = new int[]{30, 0};
        gbl_bannerPanel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_bannerPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        bannerPanel.setLayout(gbl_bannerPanel);
        
        lblLoginID = new JLabel("You logged in with name: " + teacherID);
        lblLoginID.setFont(new Font("Tahoma", Font.BOLD, 18));
        GridBagConstraints gbc_lblLoginID = new GridBagConstraints();
        gbc_lblLoginID.insets = new Insets(5, 0, 0, 30);
        gbc_lblLoginID.fill = GridBagConstraints.VERTICAL;
        gbc_lblLoginID.anchor = GridBagConstraints.EAST;
        gbc_lblLoginID.gridx = 0;
        gbc_lblLoginID.gridy = 0;
        bannerPanel.add(lblLoginID, gbc_lblLoginID);
        
        btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Tahoma", Font.BOLD, 16));
        GridBagConstraints gbc_btnLogout = new GridBagConstraints();
        gbc_btnLogout.insets = new Insets(5, 0, 0, 20);
        gbc_btnLogout.fill = GridBagConstraints.BOTH;
        gbc_btnLogout.gridx = 1;
        gbc_btnLogout.gridy = 0;
        bannerPanel.add(btnLogout, gbc_btnLogout); 
        btnLogout.setContentAreaFilled(true);
        btnLogout.setBorderPainted(true);
        
        navigatingPanel = new JPanel();
        navigatingPanel.setForeground(SystemColor.textHighlight);
        navigatingPanel.setBackground(new Color(240, 255, 255));
        navigatingPanel.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
        GridBagConstraints gbc_navigatingPanel = new GridBagConstraints();
        gbc_navigatingPanel.insets = new Insets(0, 3, 3, 3);
        gbc_navigatingPanel.fill = GridBagConstraints.BOTH;
        gbc_navigatingPanel.gridx = 0;
        gbc_navigatingPanel.gridy = 1;
        getContentPane().add(navigatingPanel, gbc_navigatingPanel);
        GridBagLayout gbl_navigatingPanel = new GridBagLayout();
        gbl_navigatingPanel.columnWidths = new int[]{150, 150, 0};
        gbl_navigatingPanel.rowHeights = new int[]{50, 50, 50, 50, 45, 50, 10, 40, 50, 50, 50, 50, 0, 0};
        gbl_navigatingPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_navigatingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        navigatingPanel.setLayout(gbl_navigatingPanel);
        
        lblTitle1 = new JLabel("Course's Information");
        lblTitle1.setForeground(UIManager.getColor("Label.foreground"));
        lblTitle1.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitle1.setFont(new Font("Times New Roman", Font.ITALIC, 22));
        GridBagConstraints gbc_lblTitle1 = new GridBagConstraints();
        gbc_lblTitle1.fill = GridBagConstraints.BOTH;
        gbc_lblTitle1.gridwidth = 2;
        gbc_lblTitle1.insets = new Insets(0, 0, 5, 0);
        gbc_lblTitle1.gridx = 0;
        gbc_lblTitle1.gridy = 0;
        navigatingPanel.add(lblTitle1, gbc_lblTitle1);
        
        lblChosenYear = new JLabel("Year: ");
        lblChosenYear.setHorizontalAlignment(SwingConstants.CENTER);
        lblChosenYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblChosenYear = new GridBagConstraints();
        gbc_lblChosenYear.fill = GridBagConstraints.BOTH;
        gbc_lblChosenYear.insets = new Insets(0, 25, 5, 5);
        gbc_lblChosenYear.gridx = 0;
        gbc_lblChosenYear.gridy = 1;
        navigatingPanel.add(lblChosenYear, gbc_lblChosenYear);
        
        cbBYear = new JComboBox<>();
        cbBYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBYear.setModel(new DefaultComboBoxModel<String>(
                StudentWindow.getChoices("SELECT DISTINCT SchoolYear FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID + "' ORDER BY SchoolYear ASC", "SchoolYear")));
        cbBYear.setSelectedIndex(-1);
        GridBagConstraints gbc_cbBYear = new GridBagConstraints();
        gbc_cbBYear.insets = new Insets(5, 15, 10, 50);
        gbc_cbBYear.fill = GridBagConstraints.BOTH;
        gbc_cbBYear.gridx = 1;
        gbc_cbBYear.gridy = 1;
        navigatingPanel.add(cbBYear, gbc_cbBYear);
        
        lblChosenSemester = new JLabel("Semester:");
        lblChosenSemester.setHorizontalAlignment(SwingConstants.CENTER);
        lblChosenSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblChosenSemester = new GridBagConstraints();
        gbc_lblChosenSemester.fill = GridBagConstraints.BOTH;
        gbc_lblChosenSemester.insets = new Insets(0, 25, 5, 5);
        gbc_lblChosenSemester.gridx = 0;
        gbc_lblChosenSemester.gridy = 2;
        navigatingPanel.add(lblChosenSemester, gbc_lblChosenSemester);
        
        cbBSemester = new JComboBox<>();
        cbBSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBSemester.setModel(new DefaultComboBoxModel<String>(
                StudentWindow.getChoices("SELECT DISTINCT Semester FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID + "' ORDER BY Semester ASC", "Semester")));
        cbBSemester.setSelectedIndex(-1);
        GridBagConstraints gbc_cbBSemester = new GridBagConstraints();
        gbc_cbBSemester.fill = GridBagConstraints.BOTH;
        gbc_cbBSemester.insets = new Insets(5, 15, 10, 50);
        gbc_cbBSemester.gridx = 1;
        gbc_cbBSemester.gridy = 2;
        navigatingPanel.add(cbBSemester, gbc_cbBSemester);
        
        lblChosenSubject = new JLabel("Subject:");
        lblChosenSubject.setHorizontalAlignment(SwingConstants.CENTER);
        lblChosenSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblChosenSubject = new GridBagConstraints();
        gbc_lblChosenSubject.fill = GridBagConstraints.BOTH;
        gbc_lblChosenSubject.insets = new Insets(0, 25, 5, 5);
        gbc_lblChosenSubject.gridx = 0;
        gbc_lblChosenSubject.gridy = 3;
        navigatingPanel.add(lblChosenSubject, gbc_lblChosenSubject);
        
        cbBSubject = new JComboBox<>();
        cbBSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBSubject.setModel(new DefaultComboBoxModel<String>(
                StudentWindow.getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID + "' ORDER BY SJ_ID ASC", "SJ_ID")));
        cbBSubject.setSelectedIndex(-1);
        GridBagConstraints gbc_cbBSubject = new GridBagConstraints();
        gbc_cbBSubject.insets = new Insets(5, 15, 10, 50);
        gbc_cbBSubject.fill = GridBagConstraints.BOTH;
        gbc_cbBSubject.gridx = 1;
        gbc_cbBSubject.gridy = 3;
        navigatingPanel.add(cbBSubject, gbc_cbBSubject);
        
        btnOK = new JButton("OK");
        btnOK.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btnOK.setEnabled(false);
        GridBagConstraints gbc_btnOK = new GridBagConstraints();
        gbc_btnOK.fill = GridBagConstraints.BOTH;
        gbc_btnOK.insets = new Insets(5, 54, 10, 85);
        gbc_btnOK.gridx = 1;
        gbc_btnOK.gridy = 4;
        navigatingPanel.add(btnOK, gbc_btnOK);
        
        btnMode = new JButton("Statistics Mode");
        btnMode.setActionCommand("Statiscal");
        btnMode.setFont(new Font("Tahoma", Font.PLAIN, 18));
        GridBagConstraints gbc_btnMode = new GridBagConstraints();
        gbc_btnMode.gridwidth = 2;
        gbc_btnMode.fill = GridBagConstraints.BOTH;
        gbc_btnMode.insets = new Insets(5, 95, 10, 95);
        gbc_btnMode.gridx = 0;
        gbc_btnMode.gridy = 5;
        navigatingPanel.add(btnMode, gbc_btnMode);
        
        separator = new JSeparator();
        separator.setForeground(new Color(160, 160, 160));
        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.insets = new Insets(0, 0, 5, 0);
        gbc_separator.gridwidth = 2;
        gbc_separator.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator.gridx = 0;
        gbc_separator.gridy = 6;
        navigatingPanel.add(separator, gbc_separator);
        
        lblTitle2 = new JLabel("Input of Course's Information");
        lblTitle2.setFont(new Font("Times New Roman", Font.ITALIC, 22));
        lblTitle2.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblTitle2 = new GridBagConstraints();
        gbc_lblTitle2.insets = new Insets(0, 0, 5, 0);
        gbc_lblTitle2.fill = GridBagConstraints.BOTH;
        gbc_lblTitle2.gridwidth = 2;
        gbc_lblTitle2.gridx = 0;
        gbc_lblTitle2.gridy = 7;
        navigatingPanel.add(lblTitle2, gbc_lblTitle2);
        
        lblInputSubject = new JLabel("Subject:");
        lblInputSubject.setHorizontalAlignment(SwingConstants.CENTER);
        lblInputSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblInputSubject = new GridBagConstraints();
        gbc_lblInputSubject.anchor = GridBagConstraints.EAST;
        gbc_lblInputSubject.fill = GridBagConstraints.VERTICAL;
        gbc_lblInputSubject.insets = new Insets(5, 10, 5, 20);
        gbc_lblInputSubject.gridx = 0;
        gbc_lblInputSubject.gridy = 8;
        navigatingPanel.add(lblInputSubject, gbc_lblInputSubject);
        
        tfInputSubject = new JTextField();
        tfInputSubject.setText("");
        tfInputSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_tfInputSubject = new GridBagConstraints();
        gbc_tfInputSubject.insets = new Insets(5, 10, 10, 50);
        gbc_tfInputSubject.fill = GridBagConstraints.BOTH;
        gbc_tfInputSubject.gridx = 1;
        gbc_tfInputSubject.gridy = 8;
        navigatingPanel.add(tfInputSubject, gbc_tfInputSubject);
        tfInputSubject.setColumns(10);
        
        lblInputYear = new JLabel("Year:");
        lblInputYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblInputYear.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblInputYear = new GridBagConstraints();
        gbc_lblInputYear.anchor = GridBagConstraints.EAST;
        gbc_lblInputYear.fill = GridBagConstraints.VERTICAL;
        gbc_lblInputYear.insets = new Insets(5, 10, 5, 20);
        gbc_lblInputYear.gridx = 0;
        gbc_lblInputYear.gridy = 9;
        navigatingPanel.add(lblInputYear, gbc_lblInputYear);
        
        tfInputYear = new JTextField();
        tfInputYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        tfInputYear.setText("");
        GridBagConstraints gbc_tfInputYear = new GridBagConstraints();
        gbc_tfInputYear.insets = new Insets(5, 10, 10, 50);
        gbc_tfInputYear.fill = GridBagConstraints.BOTH;
        gbc_tfInputYear.gridx = 1;
        gbc_tfInputYear.gridy = 9;
        navigatingPanel.add(tfInputYear, gbc_tfInputYear);
        tfInputYear.setColumns(10);
        
        lblInputSemester = new JLabel("Semester:");
        lblInputSemester.setHorizontalAlignment(SwingConstants.CENTER);
        lblInputSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblInputSemester = new GridBagConstraints();
        gbc_lblInputSemester.anchor = GridBagConstraints.EAST;
        gbc_lblInputSemester.fill = GridBagConstraints.VERTICAL;
        gbc_lblInputSemester.insets = new Insets(5, 10, 5, 20);
        gbc_lblInputSemester.gridx = 0;
        gbc_lblInputSemester.gridy = 10;
        navigatingPanel.add(lblInputSemester, gbc_lblInputSemester);
        
        tfInputSemester = new JTextField();
        tfInputSemester.setText("");
        tfInputSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_tfInputSemester = new GridBagConstraints();
        gbc_tfInputSemester.insets = new Insets(5, 10, 10, 50);
        gbc_tfInputSemester.fill = GridBagConstraints.BOTH;
        gbc_tfInputSemester.gridx = 1;
        gbc_tfInputSemester.gridy = 10;
        navigatingPanel.add(tfInputSemester, gbc_tfInputSemester);
        tfInputSemester.setColumns(10);
        
        btnInputFile = new JButton("Open Input Data File");
        btnInputFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
        btnInputFile.setEnabled(false);
        GridBagConstraints gbc_btnInputFile = new GridBagConstraints();
        gbc_btnInputFile.gridwidth = 2;
        gbc_btnInputFile.fill = GridBagConstraints.BOTH;
        gbc_btnInputFile.insets = new Insets(10, 80, 5, 80);
        gbc_btnInputFile.gridx = 0;
        gbc_btnInputFile.gridy = 11;
        navigatingPanel.add(btnInputFile, gbc_btnInputFile);
        
        displayPanel = new JPanel();
        displayPanel.setBorder(null);
        GridBagConstraints gbc_displayPanel = new GridBagConstraints();
        gbc_displayPanel.insets = new Insets(0, 3, 3, 3);
        gbc_displayPanel.fill = GridBagConstraints.BOTH;
        gbc_displayPanel.gridx = 1;
        gbc_displayPanel.gridy = 1;
        getContentPane().add(displayPanel, gbc_displayPanel);
        GridBagLayout gbl_displayPanel = new GridBagLayout();
        gbl_displayPanel.columnWidths = new int[]{809, 0};
        gbl_displayPanel.rowHeights = new int[]{400, 167, 0};
        gbl_displayPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_displayPanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        displayPanel.setLayout(gbl_displayPanel);
        
        tablePanel = new JPanel();
        tablePanel.setBackground(new Color(0, 255, 255));
        tablePanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        tablePanel.setFont(new Font("Tahoma", Font.PLAIN, 13));
        GridBagConstraints gbc_tablePanel = new GridBagConstraints();
        gbc_tablePanel.fill = GridBagConstraints.BOTH;
        gbc_tablePanel.insets = new Insets(0, 0, 5, 0);
        gbc_tablePanel.gridx = 0;
        gbc_tablePanel.gridy = 0;
        displayPanel.add(tablePanel, gbc_tablePanel);
        GridBagLayout gbl_tablePanel = new GridBagLayout();
        gbl_tablePanel.columnWidths = new int[]{0, 0};
        gbl_tablePanel.rowHeights = new int[]{40, 0, 0};
        gbl_tablePanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_tablePanel.rowWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        tablePanel.setLayout(gbl_tablePanel);
        
        lblTableTitle = new JLabel("Achieving Percentage of Outcomes of Students in Class");
        lblTableTitle.setBackground(new Color(0, 255, 204));
        lblTableTitle.setHorizontalAlignment(SwingConstants.CENTER);
        lblTableTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
        GridBagConstraints gbc_lblTableTitle = new GridBagConstraints();
        gbc_lblTableTitle.fill = GridBagConstraints.BOTH;
        gbc_lblTableTitle.gridx = 0;
        gbc_lblTableTitle.gridy = 0;
        tablePanel.add(lblTableTitle, gbc_lblTableTitle);
       
        studentTable = new JTable() {
            public String getToolTipText(MouseEvent e) {
                String tip = null;
                if (studentTable.isEnabled()) {
                    int rowIndex = (int)(e.getY()/studentTable.getRowHeight());
                    int colIndex = (int)(e.getX()/studentTable.getColumnModel().getColumn(0).getPreferredWidth());
                    if (colIndex >= 1 && rowIndex < studentTable.getRowCount()) {
                        try {
                            Map<String, float[]> outcomesDetail = OutcomesAnalysis.getSubjectOutcomesResultOfStudent(conn, (String)studentTable.getValueAt(rowIndex, 0), (String)cbBSubject.getSelectedItem(),
                                    Integer.parseInt((String)cbBSemester.getSelectedItem()), Integer.parseInt((String)cbBYear.getSelectedItem()));
                            Map<String, float[]> expectedGet = OutcomesAnalysis.getSubjectOutcomesAnalysis(conn, (String)cbBSubject.getSelectedItem());
                            float[] selectedOutcomeDetail = outcomesDetail.get(studentTable.getColumnName(colIndex));
                            float[] selectedOutcomeExpecting = expectedGet.get(studentTable.getColumnName(colIndex));
                            studentTable.setToolTipText(rowIndex + " " + colIndex);
                            tip = "<html>"
                                    + "Outcomes: [Process,   Practice,   Midterm,   Endterm]"
                                    + "<br>"
                                    + "Expected:  [";
                            for (float each: selectedOutcomeExpecting)
                                tip += ("   " + each + "%,");
                            tip = tip.substring(0, tip.length()-1) + "   ]";
                            tip +=    "<br>"
                                    + "Obtained:  [";
                            for (float each: selectedOutcomeDetail)
                                tip += ("   " + each + "%,");
                            tip = tip.substring(0, tip.length()-1) + "   ]" + "</html>";
                        } catch (SQLException ex) {
                            StudentWindow.notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            return null;
                        }
                    }
                }
                return tip;
            }
        };
        studentTable.setBackground(Color.white);
        studentTable.setFont(new Font("Serif", Font.PLAIN, 16));
        studentTable.setEnabled(false);

        tableScrollPane = new JScrollPane(studentTable);
        tableScrollPane.setForeground(new Color(102, 51, 204));
        tableScrollPane.setPreferredSize(new Dimension(450, 420));
        tableScrollPane.setBackground(Color.WHITE);
        tableScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        studentTable.setFillsViewportHeight(true);
        GridBagConstraints gbc_tableScrollPane = new GridBagConstraints();
        gbc_tableScrollPane.fill = GridBagConstraints.BOTH;
        gbc_tableScrollPane.gridx = 0;
        gbc_tableScrollPane.gridy = 1;
        tablePanel.add(tableScrollPane, gbc_tableScrollPane);
        
        detailPanel = new JPanel();
        detailPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        GridBagConstraints gbc_detailPanel = new GridBagConstraints();
        gbc_detailPanel.fill = GridBagConstraints.BOTH;
        gbc_detailPanel.gridx = 0;
        gbc_detailPanel.gridy = 1;
        displayPanel.add(detailPanel, gbc_detailPanel);
        GridBagLayout gbl_detailPanel = new GridBagLayout();
        gbl_detailPanel.columnWidths = new int[]{0, 0};
        gbl_detailPanel.rowHeights = new int[]{0, 0};
        gbl_detailPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_detailPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        detailPanel.setLayout(gbl_detailPanel);
        
        textScrollPane = new JScrollPane();
        textScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_textScrollPane = new GridBagConstraints();
        gbc_textScrollPane.fill = GridBagConstraints.BOTH;
        gbc_textScrollPane.gridx = 0;
        gbc_textScrollPane.gridy = 0;
        detailPanel.add(textScrollPane, gbc_textScrollPane);
        
        taInfo = new JTextArea();
        taInfo.setFont(new Font("Serif", Font.PLAIN, 19));
        taInfo.setLineWrap(true);
        taInfo.setBackground(SystemColor.info);
        taInfo.setWrapStyleWord(true);
        taInfo.setEditable(false);
        textScrollPane.setViewportView(taInfo);
        
        
        /**
         * Set Semester's chooser and Subject's chooser accordingly based on current chosen SchoolYear.
         */
        cbBYear.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    cbBSemester.setModel(new DefaultComboBoxModel<String>(
                            StudentWindow.getChoices("SELECT DISTINCT Semester FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID
                            + "' AND SchoolYear = " + (String)(cbBYear.getSelectedItem()) + " ORDER BY Semester ASC", "Semester")));
                    cbBSubject.setModel(new DefaultComboBoxModel<String>(
                            StudentWindow.getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID
                            + "' AND SchoolYear = " + (String)(cbBYear.getSelectedItem()) + " AND Semester = " + (String)(cbBSemester.getSelectedItem()) + " ORDER BY SJ_ID ASC", "SJ_ID")));
                }
                
            }
        });
        
        /**
         * Set Subject's chooser based in current chosen Semester
         */
        cbBSemester.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) 
                    cbBSubject.setModel(new DefaultComboBoxModel<String>(
                            StudentWindow.getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID
                            + "' AND SchoolYear = " + (String)(cbBYear.getSelectedItem()) + " AND Semester = " + (String)(cbBSemester.getSelectedItem()) + " ORDER BY SJ_ID ASC", "SJ_ID")));
            }
        });
        
        btnMode.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (arg0.getActionCommand().equals("Statiscal")) {
                    btnMode.setText("Normal Mode");
                    btnMode.setActionCommand("Normal");
                    cbBSemester.setEnabled(false);
                    cbBYear.setEnabled(false);
                    cbBSubject.setModel(new DefaultComboBoxModel<String>(
                            StudentWindow.getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE TC_ID = '" + teacherID + "' ORDER BY SJ_ID ASC", "SJ_ID")));
                    
                }
                else {
                    btnMode.setText("Statiscal Mode");
                    btnMode.setActionCommand("Statiscal");
                    cbBSemester.setEnabled(true);
                    cbBSemester.setSelectedIndex(-1);
                    cbBYear.setEnabled(true);
                    cbBYear.setSelectedIndex(-1);
                    cbBSubject.setSelectedIndex(-1);
                }
            }
        });
        
        /**
         * Check condition to enable btnOK and btnInputfile.
         */
        navigatingPanel.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseReleased(MouseEvent e) {}
            
            @Override
            public void mousePressed(MouseEvent e) {}
            
            @Override
            public void mouseExited(MouseEvent e) {}
            
            @Override
            public void mouseEntered(MouseEvent e) {
                if (btnMode.getActionCommand().equals("Statiscal"))
                    if (cbBSemester.getSelectedIndex() == -1 || cbBYear.getSelectedIndex() == -1 || cbBSubject.getSelectedIndex() == -1)
                        btnOK.setEnabled(false);
                    else btnOK.setEnabled(true);
                else
                    if (cbBSubject.getSelectedIndex() == -1)
                        btnOK.setEnabled(false);
                    else btnOK.setEnabled(true);
                if (tfInputSemester.getText().equals("") || tfInputSubject.getText().equals("") || tfInputYear.getText().equals(""))
                    btnInputFile.setEnabled(false);
                else btnInputFile.setEnabled(true);
            }
           
            @Override
            public void mouseClicked(MouseEvent e) {}
        });
        
        /**
         * Set application to original window when logged out.
         */
        Container rootPane = root.getContentPane();
        btnLogout.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(root, "Are you sure to log out?", "Message", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    TeacherWindow.root.setContentPane(rootPane);
                    TeacherWindow.root.firePropertyChange("contentPane", '*', ' ');
                    TeacherWindow.root.setVisible(true);
                }
            }
        });
        
        btnOK.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                String chosenSubject = (String)cbBSubject.getSelectedItem();
                Statement stmt = null;
                String query = "select SJOC_ID from SUBJECTOUTCOMES where SJ_ID = '" + chosenSubject + "' order by SJOC_ID asc";
                if (TeacherWindow.conn == null)
                    TeacherWindow.conn = (new DatabaseConnection(root)).getConnection();
                List<String> subjectOutcomes = new ArrayList<>();
                try {
                    conn.setAutoCommit(true);
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next())
                        subjectOutcomes.add(rs.getString(1));
                } catch (SQLException e1) {
                    StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } 
                
                if (btnMode.getActionCommand().equals("Statiscal")) {
                    int chosenSemester = Integer.parseInt((String)cbBSemester.getSelectedItem());
                    int chosenYear = Integer.parseInt((String)cbBYear.getSelectedItem());
                    try {
                        List<String> header = new ArrayList<>();
                        header.add("StudentID");
                        header.addAll(subjectOutcomes);
                        DefaultTableModel model = new DefaultTableModel(null, header.toArray(new String[0])) {
                            
                            public boolean isCellEditable(int row, int col) {
                                return false;
                            }
                        };
                        studentTable.setModel(model);
                        List<float[]> percentOfOutcomesPerStudent = new ArrayList<>();
                        int studentCount = 0;
                        query = "select STD_ID, PercentPerSJOC from STUDENT_SUBJECT where SJ_ID = '" + chosenSubject
                                + "' and TC_ID = '" + TeacherWindow.this.teacherID + "' and Semester = " + chosenSemester +  " and SchoolYear = " + chosenYear + " order by STD_ID asc";
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            String[] outcomePercent = rs.getString(2).trim().split(",");
                            Object[] newRow = new Object[outcomePercent.length+1];
                            newRow[0] = rs.getString(1);
                            for (int i=0; i<outcomePercent.length; ++i)
                                newRow[i+1] = (outcomePercent[i] + "%");
                            model.addRow(newRow);
                            float[] percents = new float[outcomePercent.length];
                            for (int i=0; i<outcomePercent.length; ++i)
                                percents[i] = Float.parseFloat(outcomePercent[i]);
                            percentOfOutcomesPerStudent.add(percents);
                            ++studentCount;
                        }
                        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
                        for (int i=0; i<header.size(); ++i) 
                            studentTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
                            
                        studentTable.setCellSelectionEnabled(true);
                        studentTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
                        studentTable.setShowGrid(true);
                        studentTable.setRowHeight(25);
                        TeacherWindow.setJTableColumnsWidth(studentTable, (int)tableScrollPane.getSize().getWidth(), header.size());
                        studentTable.setEnabled(true);
                        
                        List<int[]> statiscalData = new ArrayList<>();
                        String[] intervals = new String[] {"0% <= p < 25%", "25% <= p < 50%", "50% <= p < 75%", "75% <= p <= 100%"};
                        for (int i=0; i<intervals.length; ++i) {
                            int[] quantities = new int[header.size()-1];
                            for (int j=0; j <quantities.length; ++j) {
                                int count = 0;
                                for (float[] each: percentOfOutcomesPerStudent)
                                    switch (i) {
                                    case 0:
                                        if (each[j] < (float)25.0) count++;
                                        break;
                                    case 1:
                                        if (each[j] >= (float)25.0 && each[j] < (float)50.0) count++;
                                        break;
                                    case 2:
                                        if (each[j] >= (float)50.0 && each[j] < (float)75.0) count++;
                                        break;
                                    case 3:
                                        if (each[j] >= (float)75.0) count++;
                                        break;
                                    }
                                quantities[j] = count;
                            }
                            statiscalData.add(quantities);
                        }
                        
                        DecimalFormat df = new DecimalFormat("#.#");
                        
                        taInfo.setTabSize(8);
                        taInfo.setText("        Achieveing Percentage\t\tQuantity(Percent in Class's size)\n");
                        taInfo.setText(taInfo.getText() + "\t\t");
                        for (int i=1; i<header.size(); ++i)
                            taInfo.setText(taInfo.getText() + "\t" +  header.get(i));
                        taInfo.setText(taInfo.getText() + "\n");
                        for (int j=0; j<intervals.length; ++j) {
                            taInfo.setText(taInfo.getText() + "        " + intervals[j] + "\t");
                            for (int i=0; i<header.size()-1; ++i) 
                                taInfo.setText(taInfo.getText() + "\t" + statiscalData.get(j)[i] + "(" + df.format(((float)statiscalData.get(j)[i])*100/studentCount) + "%)");    
                            taInfo.setText(taInfo.getText() + "\n");
                        }
                        
                        
                    } catch (SQLException e1) {
                        StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (SQLException e2) {
                                StudentWindow.notifyMessage(e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
                else {
                    query =  "select SchoolYear, Semester from STUDENT_SUBJECT where SJ_ID = '" + chosenSubject + "' and TC_ID = '"
                            + teacherID + "' order by SchoolYear ASC, Semester ASC";
                    List<Integer> schoolYear = new ArrayList<>();
                    List<Integer> semester = new ArrayList<>();
                    try {
                        stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            schoolYear.add(rs.getInt(1));
                            semester.add(rs.getInt(2));
                        }
                        
                        List<float[]> outcomePercentOverTime =  new ArrayList<>();
                        List<String> timeline = new ArrayList<>();
                        for (int i=0; i<schoolYear.size(); ++i) {
                            query = "select PercentPerSJOC from STUDENT_SUBJECT where SJ_ID = '" + chosenSubject
                                    + "' and TC_ID = '" + TeacherWindow.this.teacherID + "' and Semester = " + semester.get(i) +  " and SchoolYear = " + schoolYear.get(i) + " order by STD_ID asc";
                            float[] outcomePercent = new float[subjectOutcomes.size()];
                            int[] studentCounting = new int[subjectOutcomes.size()];
                            int totalStudent = 0;
                            for (int j=0; j<outcomePercent.length; ++j) studentCounting[j] = 0;
                            rs = stmt.executeQuery(query);
                            while (rs.next()) {
                                String[] percents = rs.getString(1).trim().split(",");
                                for (int j=0; j<percents.length; ++j)
                                    if (Float.parseFloat(percents[j]) >= (float)50)
                                        studentCounting[j] += 1;
                                totalStudent++;
                            }
                            DecimalFormat df = new DecimalFormat("#.#");
                            for (int j=0; j<outcomePercent.length; ++j)
                                outcomePercent[j] = Float.parseFloat(df.format((float)studentCounting[j]/totalStudent*100));
                            outcomePercentOverTime.add(outcomePercent);
                            timeline.add(schoolYear.get(i) + "-" + semester.get(i));
                        }
                        
                        BarChartFrame chart = new BarChartFrame(getBarChartforSubjectOutcomesOverTime(subjectOutcomes, outcomePercentOverTime, timeline));
                        chart.pack();
                        RefineryUtilities.centerFrameOnScreen(chart);
                        chart.setVisible(true);
                    } catch (SQLException e1) {
                        StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        if (stmt != null) {
                            try {
                                stmt.close();
                            } catch (SQLException e2) {
                                StudentWindow.notifyMessage(e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }

                }
            }
        });
        
        fileChosser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Excel File", "xls", "xlsx");
        fileChosser.setFileFilter(filter);
        
        btnInputFile.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int inputYear = Integer.parseInt(tfInputYear.getText());
                int inputSemester = Integer.parseInt(tfInputSemester.getText());
                String inputSubject = tfInputSubject.getText();
                Statement stmt = null;
                String query = "select * from MIDTERMTEST where SJ_ID = '" + inputSubject + "' and SchoolYear = " + inputYear + " and Semester = " + inputSemester;
                try {
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    boolean checkEmpty = true;
                    while (rs.next()) {
                        checkEmpty = false;
                        break;
                    }
                    if (checkEmpty) {
                        StudentWindow.notifyMessage("Please check the input information to match the test provided", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }    
                } catch (SQLException e1) {
                    StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } finally {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (SQLException e2) {}
                    }
                }

                List<String> studentIDs = new ArrayList<>();
                int returnVal = fileChosser.showOpenDialog(TeacherWindow.root);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChosser.getSelectedFile();
                    PreparedStatement insert = null;
                    query = null;
                    try  { 
                        conn.setAutoCommit(false);
                        FileInputStream inputStream = new FileInputStream(file);
                        Workbook workbook = getWorkbook(inputStream, file.getName());
                        
                        //Processing of process and practice result
                        query = "insert into PROCESSANDPRACTICALRESULT(STD_ID, SJ_ID, TC_ID, Semester, SchoolYear, ProcessScore, PracticalScore) values (?, ?, ?, ?, ?, ?, ?)";
                        insert = conn.prepareStatement(query);
                        Sheet processAndPractice = workbook.getSheet("ProcessingAndPracticeResult");
                        Iterator<Row> rowIterator = processAndPractice.iterator();
                        Row row;
                        if (rowIterator.hasNext())
                            row = rowIterator.next();
                        loop_1: {
                            while (rowIterator.hasNext()) {
                        
                                row = rowIterator.next();
                                int cellIndex=1;
                                Iterator<Cell> cellIterator = row.cellIterator();
                                while (cellIterator.hasNext()) {
                                    Cell cell = cellIterator.next();
                                    switch (cellIndex) {
                                    case 1:
                                        if ((int)cell.getNumericCellValue() == 0)
                                            break loop_1;
                                        studentIDs.add(String.valueOf((int)cell.getNumericCellValue()));
                                        insert.setString(1, String.valueOf((int)cell.getNumericCellValue()));
                                        break;
                                    case 2:
                                        insert.setFloat(6, (float)cell.getNumericCellValue());
                                        break;
                                    case 3:
                                        insert.setFloat(7, (float)cell.getNumericCellValue());
                                        break;
                                    }
                                    if (cellIndex == 3) {
                                        insert.setString(2, inputSubject);
                                        insert.setString(3, teacherID);
                                        insert.setInt(4, inputSemester);
                                        insert.setInt(5, inputYear);
                                        insert.executeUpdate();
                                        conn.commit();
                                        break;
                                    }
                                    else ++cellIndex;
                                }
                            }
                        }
                        
                        //Insert data into MIDTERMRESULT table then calculate obtained score each of each outcome in mid-term result
                        query = "insert into MIDTERMRESULT(STD_ID, SJ_ID, TC_ID, Semester, SchoolYear, Part1, Part2, Part3, Part4, Part5, Total) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        insert = conn.prepareStatement(query);
                        Sheet midterm = workbook.getSheet("MidtermResult");
                        rowIterator = midterm.iterator();
                        if (rowIterator.hasNext())
                            row = rowIterator.next();
                        loop_2: {
                            while (rowIterator.hasNext()) {
                                row = rowIterator.next();
                                Iterator<Cell> cellIterator = row.cellIterator();
                                int cellIndex=1;
                                while (cellIterator.hasNext()) {
                                    Cell cell = cellIterator.next();
                                    switch (cellIndex) {
                                    case 1:
                                        if ((int)cell.getNumericCellValue() == 0)
                                            break loop_2;
                                        insert.setString(1, String.valueOf((int)cell.getNumericCellValue()));
                                        break;
                                    case 2:
                                        insert.setFloat(6, (float)cell.getNumericCellValue());
                                        break;
                                    case 3:
                                        insert.setFloat(7, (float)cell.getNumericCellValue());
                                        break;
                                    case 4:
                                        insert.setFloat(8, (float)cell.getNumericCellValue());
                                        break;
                                    case 5:
                                        insert.setFloat(9, (float)cell.getNumericCellValue());
                                        break;
                                    case 6:
                                        insert.setFloat(10, (float)cell.getNumericCellValue());
                                        break;
                                    case 7:
                                        insert.setFloat(11, (float)cell.getNumericCellValue());
                                        break;
                                    }
                                    if (cellIndex == 7) {
                                        insert.setString(2, inputSubject);
                                        insert.setString(3, teacherID);
                                        insert.setInt(4, inputSemester);
                                        insert.setInt(5, inputYear);
                                        insert.executeUpdate();
                                        conn.commit();
                                        break;
                                    } else cellIndex++;
                                }
                            }
                        }
                        //Insert data into ENDTERMRESULT table then calculate obtained score each of each outcome in end-term result
                        conn.setAutoCommit(false);
                        query = "insert into ENDTERMRESULT(STD_ID, SJ_ID, TC_ID, Semester, SchoolYear, Part1, Part2, Part3, Part4, Part5, Total) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        insert = conn.prepareStatement(query);
                        Sheet endterm = workbook.getSheet("EndtermResult");
                        rowIterator = endterm.iterator();
                        if (rowIterator.hasNext())
                            row = rowIterator.next();
                        loop_3: {
                            while (rowIterator.hasNext()) {
                                row = rowIterator.next();
                                Iterator<Cell> cellIterator = row.cellIterator();
                                int cellIndex=1;
                                while (cellIterator.hasNext()) {
                                    Cell cell = cellIterator.next();
                                    switch (cellIndex) {
                                    case 1:
                                        if ((int)cell.getNumericCellValue() == 0)
                                            break loop_3;
                                        insert.setString(1, String.valueOf((int)cell.getNumericCellValue()));
                                        break;
                                    case 2:
                                        insert.setFloat(6, (float)cell.getNumericCellValue());
                                        break;
                                    case 3:
                                        insert.setFloat(7, (float)cell.getNumericCellValue());
                                        break;
                                    case 4:
                                        insert.setFloat(8, (float)cell.getNumericCellValue());
                                        break;
                                    case 5:
                                        insert.setFloat(9, (float)cell.getNumericCellValue());
                                        break;
                                    case 6:
                                        insert.setFloat(10, (float)cell.getNumericCellValue());
                                        break;
                                    case 7:
                                        insert.setFloat(11, (float)cell.getNumericCellValue());
                                        break;
                                    }
                                    if (cellIndex == 7) {
                                        insert.setString(2, inputSubject);
                                        insert.setString(3, teacherID);
                                        insert.setInt(4, inputSemester);
                                        insert.setInt(5, inputYear);
                                        insert.executeUpdate();
                                        conn.commit();
                                        break;
                                    } else cellIndex++;
                                }
                            }
                        }
                        //Insert into STUDENT_SUBJECT table.
                        query = "insert into STUDENT_SUBJECT(STD_ID, SJ_ID, TC_ID, SchoolYear, Semester, SJOCs, PercentPerSJOC, DPMOCs, PercentPerDPMOC)"
                                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        insert = conn.prepareStatement(query);
                        for (String studentID: studentIDs) {
                            String subjectOutcomes = "";
                            String percentPerSJOCs = "";
                            String departmentalOutcomes = "";
                            String percentPerDPMOCs = "";
                            
                            //Processing of departmental outcomes student got in subject.
                            Map<String, float[]> expected = OutcomesAnalysis.getDepartmentalOutcomesAnalysisInSubject(conn, inputSubject);
                            Map<String, Float> realAchieved = new HashMap<>();
                            Map<String, float[]> studentResult = OutcomesAnalysis.getSubjectOutcomesResultOfStudent(conn, studentID, inputSubject, inputSemester, inputYear);
                            List<String> outcomes = new ArrayList<>(studentResult.keySet());
                            outcomes.sort((o1, o2) -> {return o1.compareTo(o2); });
                            DecimalFormat df = new DecimalFormat("#.#");
                            int index=0;
                            for (String outcome: outcomes) {
                                subjectOutcomes += (outcome + ",");
                                float total = 0;
                                for (float each: studentResult.get(outcome))
                                    total += each;
                                for (String each: expected.keySet()) 
                                    if (realAchieved.putIfAbsent(each, expected.get(each)[index]*total/100) != null)
                                        realAchieved.put(each, realAchieved.get(each)+expected.get(each)[index]*total/100);
                                percentPerSJOCs += (df.format(total) + ",");
                                ++index;
                            }
                            
                            subjectOutcomes = subjectOutcomes.substring(0, subjectOutcomes.length()-1);
                            percentPerSJOCs = percentPerSJOCs.substring(0, percentPerSJOCs.length()-1);
                            
                            for (Map.Entry<String, Float> entry: realAchieved.entrySet()) {
                                departmentalOutcomes += (entry.getKey() + ",");
                                percentPerDPMOCs += (df.format(entry.getValue()) + ",");
                            }
                            departmentalOutcomes = departmentalOutcomes.substring(0, departmentalOutcomes.length()-1);
                            percentPerDPMOCs = percentPerDPMOCs.substring(0, percentPerDPMOCs.length()-1);
                            
                            conn.setAutoCommit(false);
                            insert.setString(1, studentID);
                            insert.setString(2, inputSubject);
                            insert.setString(3, teacherID);
                            insert.setInt(4, inputYear);
                            insert.setInt(5, inputSemester);
                            insert.setString(6, subjectOutcomes);
                            insert.setString(7, percentPerSJOCs);
                            insert.setString(8, departmentalOutcomes);
                            insert.setString(9, percentPerDPMOCs);
                            insert.executeUpdate();
                            conn.commit();
                        }
                        StudentWindow.notifyMessage("Input data successfully!", "Message", JOptionPane.INFORMATION_MESSAGE);
                        
                    } catch (IOException ex) {
                        StudentWindow.notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } catch (SQLException e1) {
                        StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    } finally {
                        if (insert != null) {
                            try {
                                insert.close();
                            } catch (SQLException e2) {
                                StudentWindow.notifyMessage(e2.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                    }
                }
            }
        });
    }
    
    /**
     * Create a bar chart for showing information of subject through time
     * @param outcomes outcomes of subject
     * @param outcomePercentOverTime achieving percentage of each outcomes over time
     * @param timeline a series of points of time at which the subject was being teaching.
     * @return
     */
    private JFreeChart getBarChartforSubjectOutcomesOverTime(List<String> outcomes, List<float[]> outcomePercentOverTime, List<String> timeline) {
        
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i=0; i<timeline.size(); ++i)
            for (int j=0; j<outcomes.size(); ++j)
                dataset.addValue(outcomePercentOverTime.get(i)[j], outcomes.get(j), timeline.get(i));
        
        JFreeChart barChart = ChartFactory.createBarChart(
                "Achieving Percentage of Outcomes of Subject over time",
                "Outcome",
                "Percentage",
                dataset,
                PlotOrientation.VERTICAL, true, true, false);
        return barChart;
    }
    /**
     * Set table column's preferred width
     * @param table 
     * @param tablePreferredWidth
     * @param columnCount
     * @return
     */
    private static int setJTableColumnsWidth(JTable table, int tablePreferredWidth, int columnCount) {
        double eachCol = tablePreferredWidth/columnCount;
        for (int i=0; i<columnCount; ++i) {
            TableColumn column = table.getColumnModel().getColumn(i);
            column.setPreferredWidth((int)eachCol);
        }
        return (int)eachCol;
    }
    
    /**
     * Get appropriate workbook for each edition of MS Excel.
     * @param inputStream - file input stream to create workbook
     * @param excelFilePath - file name  
     * @return workbook of Excel 2003 backward  or Excel 2007 onward.
     */
    private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath) {
        Workbook workbook = null;
        try {
            if (excelFilePath.endsWith("xlsx")) workbook = new XSSFWorkbook(inputStream);
            else if (excelFilePath.endsWith("xls")) workbook = new HSSFWorkbook(inputStream);
            else StudentWindow.notifyMessage("The specified file is not Excel file", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            StudentWindow.notifyMessage(e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        return workbook;
    }
}
