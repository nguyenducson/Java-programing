package src;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Paint;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.LegendItem;
import org.jfree.chart.LegendItemCollection;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.labels.StandardCategoryItemLabelGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.data.category.DefaultCategoryDataset;
import java.awt.SystemColor;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartMouseListener;
import org.jfree.chart.ChartMouseEvent;
import javax.swing.ScrollPaneConstants;

class StudentWindow extends JFrame {
    
    private static Connection conn;
    private final String studentID;
    private static JFrame root;
    private JPanel bannerPanel;
    private JLabel lblLoginID;
    private JButton btnLogout;
    private JPanel navigatingPanel;
    private JLabel lblShowOutcomes;
    private JLabel lblYear;
    private JComboBox<String> cbBSchoolYear;
    private JLabel lblSemester;
    private JComboBox<String> cbBSemester;
    private JLabel lblSubject;
    private JComboBox<String> cbBSubject;
    private JButton btnOK;
    private JSeparator separator;
    private JLabel lblDepartmentOutcomes;
    private JButton btnWatch;
    private JPanel displayPanel;
    private ChartPanel chartPanel;
    private JSeparator separator_1;
    private JPanel infoPanel;
    private JScrollPane scrollPane;
    private JTextArea tAreaInfo;
    
    //for purpose of showing departmental outcomes
    HashMap<String, String> consideredSubjects;
    /**
     * Create the frame.
     */
    public StudentWindow(String studentID, Connection conn, JFrame root) {
        this.studentID = studentID;
        StudentWindow.conn = conn;
        StudentWindow.root = root;
        
        setBounds(100, 100, 1194, 770);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Student Information");
        setIconImage(Toolkit.getDefaultToolkit().getImage(InitialWindow.class.getResource("student.png")));
        setResizable(false);
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{300, 850, 0};
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
        
        lblLoginID = new JLabel("You logged in with name: " + studentID);
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
        btnLogout.setOpaque(false);
        btnLogout.setContentAreaFilled(true);
        btnLogout.setBorderPainted(false);
        
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
        gbl_navigatingPanel.columnWidths = new int[]{100, 0, 0};
        gbl_navigatingPanel.rowHeights = new int[]{70, 60, 60, 60, 50, 20, 60, 35, 0};
        gbl_navigatingPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_navigatingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        navigatingPanel.setLayout(gbl_navigatingPanel);
        
        lblShowOutcomes = new JLabel("Search for Subject's outcomes");
        lblShowOutcomes.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        GridBagConstraints gbc_lblShowOutcomes = new GridBagConstraints();
        gbc_lblShowOutcomes.gridwidth = 2;
        gbc_lblShowOutcomes.insets = new Insets(0, 0, 5, 0);
        gbc_lblShowOutcomes.gridx = 0;
        gbc_lblShowOutcomes.gridy = 0;
        navigatingPanel.add(lblShowOutcomes, gbc_lblShowOutcomes);
        
        lblYear = new JLabel("Year:");
        lblYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblYear = new GridBagConstraints();
        gbc_lblYear.anchor = GridBagConstraints.WEST;
        gbc_lblYear.insets = new Insets(0, 20, 5, 5);
        gbc_lblYear.gridx = 0;
        gbc_lblYear.gridy = 1;
        navigatingPanel.add(lblYear, gbc_lblYear);
        
        cbBSchoolYear = new JComboBox<>();
        cbBSchoolYear.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBSchoolYear.setModel(new DefaultComboBoxModel<String>(
                getChoices("SELECT DISTINCT SchoolYear FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID + "' ORDER BY SchoolYear ASC", "SchoolYear")));
        GridBagConstraints gbc_cbBSchoolYear = new GridBagConstraints();
        gbc_cbBSchoolYear.insets = new Insets(5, 20, 20, 80);
        gbc_cbBSchoolYear.fill = GridBagConstraints.BOTH;
        gbc_cbBSchoolYear.gridx = 1;
        gbc_cbBSchoolYear.gridy = 1;
        navigatingPanel.add(cbBSchoolYear, gbc_cbBSchoolYear);
        cbBSchoolYear.setSelectedIndex(-1);
        
        lblSemester = new JLabel("Semester:");
        lblSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSemester = new GridBagConstraints();
        gbc_lblSemester.anchor = GridBagConstraints.WEST;
        gbc_lblSemester.insets = new Insets(0, 20, 5, 5);
        gbc_lblSemester.gridx = 0;
        gbc_lblSemester.gridy = 2;
        navigatingPanel.add(lblSemester, gbc_lblSemester);
        
        cbBSemester = new JComboBox<>();
        cbBSemester.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBSemester.setModel(new DefaultComboBoxModel<String>(
                getChoices("SELECT DISTINCT Semester FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID + "' ORDER BY Semester ASC", "Semester")));
        GridBagConstraints gbc_cbBSemester = new GridBagConstraints();
        gbc_cbBSemester.insets = new Insets(5, 20, 20, 80);
        gbc_cbBSemester.fill = GridBagConstraints.BOTH;
        gbc_cbBSemester.gridx = 1;
        gbc_cbBSemester.gridy = 2;
        navigatingPanel.add(cbBSemester, gbc_cbBSemester);
        cbBSemester.setSelectedIndex(-1);
        
        lblSubject = new JLabel("Subject:");
        lblSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblSubject = new GridBagConstraints();
        gbc_lblSubject.anchor = GridBagConstraints.WEST;
        gbc_lblSubject.insets = new Insets(0, 20, 5, 5);
        gbc_lblSubject.gridx = 0;
        gbc_lblSubject.gridy = 3;
        navigatingPanel.add(lblSubject, gbc_lblSubject);
        
        cbBSubject = new JComboBox<>();
        cbBSubject.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBSubject.setModel(new DefaultComboBoxModel<String>(
                getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID + "' ORDER BY SJ_ID ASC", "SJ_ID")));
        GridBagConstraints gbc_cbBSubject = new GridBagConstraints();
        gbc_cbBSubject.fill = GridBagConstraints.BOTH;
        gbc_cbBSubject.insets = new Insets(5, 20, 20, 80);
        gbc_cbBSubject.gridx = 1;
        gbc_cbBSubject.gridy = 3;
        navigatingPanel.add(cbBSubject, gbc_cbBSubject);
        cbBSubject.setSelectedIndex(-1);
        
        btnOK = new JButton("OK");
        GridBagConstraints gbc_btnOK = new GridBagConstraints();
        gbc_btnOK.fill = GridBagConstraints.BOTH;
        gbc_btnOK.gridwidth = 2;
        gbc_btnOK.insets = new Insets(5, 160, 10, 120);
        gbc_btnOK.gridx = 0;
        gbc_btnOK.gridy = 4;
        navigatingPanel.add(btnOK, gbc_btnOK);
        
        separator = new JSeparator();
        separator.setForeground(Color.BLACK);
        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator.gridwidth = 2;
        gbc_separator.gridx = 0;
        gbc_separator.gridy = 5;
        navigatingPanel.add(separator, gbc_separator);
        
        lblDepartmentOutcomes = new JLabel("Departmental Obtained Outcomes");
        lblDepartmentOutcomes.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        GridBagConstraints gbc_lblDepartmentOutcomes = new GridBagConstraints();
        gbc_lblDepartmentOutcomes.anchor = GridBagConstraints.SOUTH;
        gbc_lblDepartmentOutcomes.insets = new Insets(0, 0, 30, 0);
        gbc_lblDepartmentOutcomes.gridwidth = 2;
        gbc_lblDepartmentOutcomes.gridx = 0;
        gbc_lblDepartmentOutcomes.gridy = 6;
        navigatingPanel.add(lblDepartmentOutcomes, gbc_lblDepartmentOutcomes);
        
        btnWatch = new JButton("Watch");
        btnWatch.setFont(new Font("Tahoma", Font.BOLD, 16));
        GridBagConstraints gbc_btnWatch = new GridBagConstraints();
        gbc_btnWatch.fill = GridBagConstraints.VERTICAL;
        gbc_btnWatch.gridwidth = 2;
        gbc_btnWatch.gridx = 0;
        gbc_btnWatch.gridy = 7;
        navigatingPanel.add(btnWatch, gbc_btnWatch);
        
        displayPanel = new JPanel();
        displayPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
        displayPanel.setForeground(Color.BLACK);
        GridBagConstraints gbc_displayPanel = new GridBagConstraints();
        gbc_displayPanel.insets = new Insets(0, 3, 3, 3);
        gbc_displayPanel.fill = GridBagConstraints.BOTH;
        gbc_displayPanel.gridx = 1;
        gbc_displayPanel.gridy = 1;
        getContentPane().add(displayPanel, gbc_displayPanel);
        GridBagLayout gbl_displayPanel = new GridBagLayout();
        gbl_displayPanel.columnWidths = new int[]{800, 0};
        gbl_displayPanel.rowHeights = new int[]{380, 15, 150, 0};
        gbl_displayPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_displayPanel.rowWeights = new double[]{1.0, 0.0, 1.0, Double.MIN_VALUE};
        displayPanel.setLayout(gbl_displayPanel);
        
        
        chartPanel = new ChartPanel(getBarChartForSubjectOutcomes(null, new ArrayList<Float>()));
        GridBagConstraints gbc_chartPanel = new GridBagConstraints();
        gbc_chartPanel.fill = GridBagConstraints.BOTH;
        gbc_chartPanel.gridx = 0;
        gbc_chartPanel.gridy = 0;
        displayPanel.add(chartPanel, gbc_chartPanel);
               
        separator_1 = new JSeparator();
        GridBagConstraints gbc_separator_1 = new GridBagConstraints();
        gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator_1.gridx = 0;
        gbc_separator_1.gridy = 1;
        displayPanel.add(separator_1, gbc_separator_1);
        
        infoPanel = new JPanel();
        infoPanel.setBackground(SystemColor.info);
        GridBagConstraints gbc_infoPanel = new GridBagConstraints();
        gbc_infoPanel.fill = GridBagConstraints.BOTH;
        gbc_infoPanel.gridx = 0;
        gbc_infoPanel.gridy = 2;
        displayPanel.add(infoPanel, gbc_infoPanel);
        GridBagLayout gbl_infoPanel = new GridBagLayout();
        gbl_infoPanel.columnWidths = new int[]{0, 0};
        gbl_infoPanel.rowHeights = new int[]{0, 0};
        gbl_infoPanel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_infoPanel.rowWeights = new double[]{1.0, Double.MIN_VALUE};
        infoPanel.setLayout(gbl_infoPanel);
        
        scrollPane = new JScrollPane();
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 0;
        infoPanel.add(scrollPane, gbc_scrollPane);
        
        tAreaInfo = new JTextArea();
        tAreaInfo.setFont(new Font("Serif", Font.PLAIN, 19));
        tAreaInfo.setLineWrap(true);
        tAreaInfo.setBackground(SystemColor.info);
        tAreaInfo.setWrapStyleWord(true);
        tAreaInfo.setEditable(false);
        scrollPane.setViewportView(tAreaInfo);
       
        
      
        
        /**
         * Set Semester's chooser and Subject's chooser accordingly based on current chosen SchoolYear.
         */
        cbBSchoolYear.addItemListener(new ItemListener() {
            
            @Override
            public void itemStateChanged(ItemEvent arg0) {
                if (arg0.getStateChange() == ItemEvent.SELECTED) {
                    cbBSemester.setModel(new DefaultComboBoxModel<String>(
                            getChoices("SELECT DISTINCT Semester FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID
                            + "' AND SchoolYear = " + (String)(cbBSchoolYear.getSelectedItem()) + " ORDER BY Semester ASC", "Semester")));
                    cbBSubject.setModel(new DefaultComboBoxModel<String>(
                            getChoices("SELECT SJ_ID FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID
                            + "' AND SchoolYear = " + (String)(cbBSchoolYear.getSelectedItem()) + " AND Semester = " + (String)(cbBSemester.getSelectedItem()) + " ORDER BY SJ_ID ASC", "SJ_ID")));
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
                            getChoices("SELECT DISTINCT SJ_ID FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID
                            + "' AND SchoolYear = " + (String)(cbBSchoolYear.getSelectedItem()) + " AND Semester = " + (String)(cbBSemester.getSelectedItem()) + " ORDER BY SJ_ID ASC", "SJ_ID")));
            }
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
                    StudentWindow.root.setContentPane(rootPane);
                    StudentWindow.root.firePropertyChange("contentPane", '*', ' ');
                    StudentWindow.root.setVisible(true);
                }
            }
        });
        
        /**
         * Show details of each outcomes when clicked.
         */
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            
            @Override
            public void chartMouseClicked(ChartMouseEvent event) {
                ChartEntity entity = event.getEntity();
                int beginIndex = entity.toString().indexOf("columnKey")+10;
                if (beginIndex == 9)
                    return;
                String entityString = entity.toString();
                String chosenOutcome = "";
                for (int i=beginIndex; ;++i)
                    if (entityString.charAt(i) == ',')
                        break;
                    else chosenOutcome += entityString.charAt(i);
                if (chosenOutcome.matches("G(\\d)+")) {                     //If chartPanel is displaying subject's outcomes
                    tAreaInfo.setText("");
                    Statement stmt = null;
                    String query = null;
                    if (StudentWindow.conn == null)
                        StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
                    int schoolYear = Integer.parseInt((String)cbBSchoolYear.getSelectedItem());
                    int semester = Integer.parseInt((String)cbBSemester.getSelectedItem());
                    String subjectID = (String)cbBSubject.getSelectedItem();
                    try {
                        stmt = conn.createStatement();
                        query = "SELECT SJOCContent FROM SUBJECTOUTCOMES WHERE SJOC_ID = '" + chosenOutcome + "' AND SJ_ID = '" + subjectID + "'";
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            tAreaInfo.setText("\t\t---------------------" + chosenOutcome + ": " + rs.getString(1) + "-----------------------\n");
                        }
                        
                        float[] expectedPercent = OutcomesAnalysis.getSubjectOutcomesAnalysis(conn, subjectID).get(chosenOutcome);
                        float[] realPercent = OutcomesAnalysis.getSubjectOutcomesResultOfStudent(conn, studentID, subjectID, semester, schoolYear).get(chosenOutcome);
                        
                        tAreaInfo.setText(tAreaInfo.getText() + "\tAssessing Component\tExpected Percent(%)\tYou get(%)\n");
                        tAreaInfo.setText(tAreaInfo.getText() + "\t          Process\t           " + expectedPercent[0] + "%\t\t    " + realPercent[0] + "%\n");
                        tAreaInfo.setText(tAreaInfo.getText() + "\t          Midterm\t           " + expectedPercent[1] + "%\t\t    " + realPercent[1] + "%\n");
                        tAreaInfo.setText(tAreaInfo.getText() + "\t          Practice\t\t           " + expectedPercent[2] + "%\t\t    " + realPercent[2] + "%\n");
                        tAreaInfo.setText(tAreaInfo.getText() + "\t          Endterm\t           " + expectedPercent[3] + "%\t\t    " + realPercent[3] + "%");
                        
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
                // If chartPanel is displaying departmental outcomes
                else {
                    Map<String, Float> percentPerSubject = new HashMap<>();
                    String query = "select SJ_ID, Semester, SchoolYear, DPMOCs, PercentPerDPMOC from STUDENT_SUBJECT where STD_ID = '" + studentID + "' and DPMOCs LIKE '%"
                            + chosenOutcome + "%'";
                    Statement stmt = null;
                    if (StudentWindow.conn == null) StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
                    try {
                        stmt = conn.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            if (consideredSubjects.get(rs.getString(1)).equals(rs.getInt(2)+" "+rs.getInt(3))) {
                                String[] outcomes = rs.getString(4).trim().split(",");
                                String[] percents = rs.getString(5).trim().split(",");
                                for (int i=0; i<outcomes.length; ++i)
                                    if (outcomes[i].equals(chosenOutcome)) {
                                        percentPerSubject.put(rs.getString(1), Float.parseFloat(percents[i])); break; }
                            }
                        }
                        query = "SELECT DPMContent FROM DEPARTMENTALOUTCOMES WHERE DPMOC_ID = '" + chosenOutcome + "' AND DPM_ID = (select DPM_ID from STUDENT where STD_ID = '" + studentID + "')";
                        rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            tAreaInfo.setText("\t---------------" + chosenOutcome + ": " + rs.getString(1) + "---------------\n");
                        }
                        tAreaInfo.setText(tAreaInfo.getText() + "\t\tIncluded In Subject\t     Achieving Percentage\n");
                        for (String subject: percentPerSubject.keySet())
                            tAreaInfo.setText(tAreaInfo.getText() + "\t\t         " + subject + "\t\t                " + percentPerSubject.get(subject) + "%\n");

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
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent arg0) {}
        });
        
        /**
         * Show information of subject's outcomes of student.
         */
        btnOK.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> outcomes = new ArrayList<>();
                List<Float> outcomePercents = new ArrayList<>();
                chartPanel.setChart(getBarChartForSubjectOutcomes(outcomes, outcomePercents));
                chartPanel.setPreferredSize(new Dimension(800, 380));
                customizeChartPlotting(chartPanel.getChart());
                chartPanel.setVisible(true);
                tAreaInfo.setText("");
                Statement stmt = null;
                String query = "SELECT SJOC_ID, SJOCContent FROM SUBJECTOUTCOMES WHERE SJ_ID = '" + (String)cbBSubject.getSelectedItem() + "' ORDER BY SJOC_ID ASC";
                if (StudentWindow.conn == null)
                    StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
                try {
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        tAreaInfo.setText(tAreaInfo.getText() + rs.getString(1) + ": " + rs.getString(2) + "\n");
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
        /**
         * Show achieving percentage of departmental outcomes of student.
         */
        btnWatch.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                Map<String, Float> percentPerOutcome = new HashMap<>();     //total percent of outcomes in all subject it was included.
                Map<String, Integer> outcomeCounting = new HashMap<>();     //count the number of subject the outcomes was included in
                String query = "select PAPR.SJ_ID, PAPR.Semester, PAPR.SchoolYear, (PAPR.ProcessScore+PAPR.PracticalScore+MTR.Total+ETR.Total) as DTB from PROCESSANDPRACTICALRESULT as PAPR " +
                        "JOIN MIDTERMRESULT as MTR on PAPR.STD_ID = MTR.STD_ID and PAPR.SJ_ID = MTR.SJ_ID and PAPR.SchoolYear = MTR.SchoolYear and PAPR.Semester = MTR.Semester " +
                        "JOIN ENDTERMRESULT as ETR on PAPR.STD_ID = ETR.STD_ID and PAPR.SJ_ID = ETR.SJ_ID and PAPR.SchoolYear = ETR.SchoolYear and PAPR.Semester = ETR.Semester " +
                        "where PAPR.STD_ID = '" + studentID + "'";
               Statement stmt = null;
               if (StudentWindow.conn == null) StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
               try {
                   stmt = conn.createStatement();
                   ResultSet rs = stmt.executeQuery(query);
                   Map<String, Float> DTBofSubjects = new HashMap<>();
                   Map<String, String> timeOfSubjects = new HashMap<>();
                   while (rs.next()) {
                       String subjectID = rs.getString(1);
                       if (DTBofSubjects.putIfAbsent(subjectID, rs.getFloat(4)) != null)
                           if (DTBofSubjects.get(subjectID) < rs.getFloat(4))
                               DTBofSubjects.put(subjectID, rs.getFloat(4));
                       if (timeOfSubjects.get(subjectID) != null) {
                           if (rs.getFloat(4) == DTBofSubjects.get(subjectID))
                               timeOfSubjects.put(subjectID, rs.getInt(2) + " " + rs.getInt(3));
                       } else timeOfSubjects.put(subjectID, rs.getInt(2) + " " + rs.getInt(3));
                   }
                   consideredSubjects = new HashMap<>(timeOfSubjects);
                   query = "select SJ_ID, Semester, SchoolYear, DPMOCs, PercentPerDPMOC from STUDENT_SUBJECT where STD_ID = '" + studentID + "'";
                   rs = stmt.executeQuery(query);
                   while (rs.next()) {
                       if (timeOfSubjects.get(rs.getString(1)).equals(rs.getInt(2) + " " + rs.getInt(3))) {
                           String[] outcomes = rs.getString(4).trim().split(",");
                           List<Float> percents = new ArrayList<>();
                           for (String each: rs.getString(5).trim().split(","))
                               percents.add(Float.parseFloat(each));
                           for (int i=0; i<outcomes.length; ++i) {
                               if (percentPerOutcome.get(outcomes[i]) == null) {
                                   percentPerOutcome.put(outcomes[i], percents.get(i));
                                   outcomeCounting.put(outcomes[i], 1);
                               }
                               else {
                                   percentPerOutcome.put(outcomes[i], percentPerOutcome.get(outcomes[i])+percents.get(i));
                                   outcomeCounting.put(outcomes[i], outcomeCounting.get(outcomes[i])+1);
                               }
                           }
                       }
                   }
             
                   for (String key: percentPerOutcome.keySet())
                       percentPerOutcome.put(key, percentPerOutcome.get(key)/outcomeCounting.get(key));
                   
                   List<String> outcomes = new ArrayList<>(percentPerOutcome.keySet());
                   outcomes.sort((s1, s2) -> { return (Integer.parseInt(s1.substring(2, s1.length())) >= Integer.parseInt(s2.substring(2, s2.length())))? 1 : -1; });
                   List<Float> percents = new ArrayList<>();
                   for (String outcome: outcomes)
                       percents.add(percentPerOutcome.get(outcome));
                   DecimalFormat df = new DecimalFormat("#.#");
                   for (int i=0; i<percents.size(); ++i)
                       percents.set(i, Float.parseFloat(df.format(percents.get(i))));
                   
                   chartPanel.setChart(getBarChartForDepartmentalOutcomes(outcomes, percents));
                   chartPanel.setPreferredSize(new Dimension(800, 380));
                   customizeChartPlotting(chartPanel.getChart());
                   chartPanel.setVisible(true);
                   tAreaInfo.setText("");
                   
                   query = "select DPMOC_ID, DPMContent from DEPARTMENTALOUTCOMES WHERE DPM_ID = (select DPM_ID from STUDENT where STD_ID = '" + studentID + "') ORDER BY DPMOC_ID ASC";
                   rs = stmt.executeQuery(query);
                   while (rs.next()) {
                       tAreaInfo.setText(tAreaInfo.getText() + rs.getString(1) + ": " + rs.getString(2) + "\n");
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
     * Create bar chart for showing subject's outcomes.
     */
    private JFreeChart getBarChartForSubjectOutcomes(List<String> outcomes, List<Float> outcomePercents) {
        final String chosenYear = (String)cbBSchoolYear.getSelectedItem();
        final String chosenSemester = (String)cbBSemester.getSelectedItem();
        final String chosenSubject = (String)cbBSubject.getSelectedItem();
        Statement stmt = null;
        String query = "SELECT SJOCs, PercentPerSJOC FROM STUDENT_SUBJECT WHERE STD_ID = '" + studentID 
                + "' AND SJ_ID = '" + chosenSubject + "' AND SchoolYear = " + chosenYear + " AND Semester = " + chosenSemester;
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        JFreeChart barChart = null;
        if (StudentWindow.conn == null)
            StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
        try {
            stmt = StudentWindow.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                for (String outcome: rs.getString(1).split(","))
                    outcomes.add(outcome);
                for (String percent: rs.getString(2).split(","))
                    outcomePercents.add(Float.parseFloat(percent));
            }
            for (int i=0; i < outcomePercents.size(); ++i)
                dataset.addValue(outcomePercents.get(i), studentID, outcomes.get(i)); 
            barChart = ChartFactory.createBarChart("Achieving Percentage of each Outcomes of " + ((chosenSubject == null) ? "Subject" : chosenSubject), "Outcomes", "Percentage (%)", dataset, PlotOrientation.VERTICAL, true, true, false);
            
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
        return barChart;
    }
    
    /**
     * Create a bar chart for DepartmentalOutcomes
     * @param outcomes
     * @param percents
     * @return
     */
    private JFreeChart getBarChartForDepartmentalOutcomes(List<String> outcomes, List<Float> percents) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i=0; i<outcomes.size(); ++i) 
            dataset.addValue(percents.get(i), studentID, outcomes.get(i));
        return ChartFactory.createBarChart("Achieving Percentage of each Departmental Outcomes of " + studentID, "Departmental Outcomes", "Percentage (%)", dataset, PlotOrientation.VERTICAL, true, true, false);
        
    }
    
    /**
     * Customize appearance of chart for better visual look.
     * @param chart - chart to customize
     */
    static void customizeChartPlotting(JFreeChart chart) {
        
        class myBarRenderer extends BarRenderer {
            
            @Override
            public Paint getItemPaint(int row, int column) {
                float value = (float) chart.getCategoryPlot().getDataset().getValue(row, column);
                if (value >= 50.0)
                    return new Color(0, 172, 178);
                return new Color(220, 240, 220);
            }
        }
        CategoryPlot plot = chart.getCategoryPlot();
        LegendItemCollection chartLegend = new LegendItemCollection();
        Shape shape = new Rectangle(10, 10);
        chartLegend.add(new LegendItem("Achieved", null, null, null, shape, new Color(0, 172, 178)));
        chartLegend.add(new LegendItem("Not Achieved", null, null, null, shape, new Color(220, 240, 220)));
        plot.setFixedLegendItems(chartLegend);
        plot.setRenderer(new myBarRenderer());
        plot.setBackgroundPaint(new Color(255, 255, 250));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.black);
        plot.setOutlineVisible(false);
        ((BarRenderer)plot.getRenderer()).setBarPainter(new StandardBarPainter());
        plot.getRenderer().setBaseItemLabelGenerator(new StandardCategoryItemLabelGenerator());
        plot.getRenderer().setBaseItemLabelsVisible(true);
        ((BarRenderer)plot.getRenderer()).setMaximumBarWidth(0.07);
    }
    
    /**
     * Get appropriate choices for student
     */
    static String[] getChoices(String query, String column) {
        if (StudentWindow.conn == null)
            StudentWindow.conn = (new DatabaseConnection(root)).getConnection();
        Statement stmt = null;
        List<String> resultList = new ArrayList<>();
        try {
            stmt = StudentWindow.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
                if (!column.equals("SJ_ID"))
                    resultList.add(String.valueOf(rs.getInt(1)));
                else resultList.add(rs.getString(1));
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
        return resultList.toArray(new String[0]);
    }
    
    /**
     * Display a message panel
     */
    static void notifyMessage(String message, String title, int messageType) {
        JOptionPane.showMessageDialog(StudentWindow.root, message, title, messageType);
    }
}
