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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.SwingConstants;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.JComboBox;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

class ManagerWindow extends JFrame {

    private static Connection conn;
    private static JFrame root;
    private final String managerID;
    private ImagePanel bannerPanel;
    private JLabel lblLoginID;
    private JButton btnLogout;
    private JPanel navigatingPanel;
    private JLabel lblLable1;
    private JSeparator separator;
    private JLabel lblTeacher;
    private JComboBox<String> cbBTeacher;
    private JButton btnOpenTeacherController;
    private JSeparator separator_1;
    private JLabel lblDepartmentOutcomesDetail;
    private JButton btnAverageAchievedPercentage;
    private JLabel lblStudentInside;
    private JComboBox<String> cbBStudent;
    private JButton btnOk;
    private JPanel displayPanel;
    private ChartPanel chartPanel;
    private JSeparator separator_2;
    private JPanel  infoPanel;
    private JScrollPane scrollPane;
    private JTextArea tAreaInfo;
  //for purpose of showing departmental outcomes
    HashMap<String, String> consideredSubjects;
    /**
     * Create the frame.
     */
    public ManagerWindow(String managerID, Connection conn, JFrame root) {
        ManagerWindow.conn = conn;
        ManagerWindow.root = root;
        this.managerID = managerID;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1194, 770);
        
        setTitle("Manager Window");
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
        
        lblLoginID = new JLabel("You logged in with name: " + managerID);
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
        gbc_navigatingPanel.insets = new Insets(0, 3, 3, 5);
        gbc_navigatingPanel.fill = GridBagConstraints.BOTH;
        gbc_navigatingPanel.gridx = 0;
        gbc_navigatingPanel.gridy = 1;
        getContentPane().add(navigatingPanel, gbc_navigatingPanel);
        GridBagLayout gbl_navigatingPanel = new GridBagLayout();
        gbl_navigatingPanel.columnWidths = new int[]{150, 150, 0};
        gbl_navigatingPanel.rowHeights = new int[]{50, 10, 50, 60, 20, 50, 60, 60, 60, 50, 50, 50, 0, 0};
        gbl_navigatingPanel.columnWeights = new double[]{0.0, 1.0, Double.MIN_VALUE};
        gbl_navigatingPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        navigatingPanel.setLayout(gbl_navigatingPanel);
        
        lblLable1 = new JLabel("Department's Controler");
        lblLable1.setVerticalAlignment(SwingConstants.BOTTOM);
        lblLable1.setHorizontalAlignment(SwingConstants.CENTER);
        lblLable1.setFont(new Font("Times New Roman", Font.ITALIC, 24));
        GridBagConstraints gbc_lblLable1 = new GridBagConstraints();
        gbc_lblLable1.anchor = GridBagConstraints.SOUTH;
        gbc_lblLable1.fill = GridBagConstraints.BOTH;
        gbc_lblLable1.gridwidth = 2;
        gbc_lblLable1.insets = new Insets(0, 0, 5, 0);
        gbc_lblLable1.gridx = 0;
        gbc_lblLable1.gridy = 0;
        navigatingPanel.add(lblLable1, gbc_lblLable1);
        
        separator = new JSeparator();
        separator.setForeground(new Color(154, 205, 50));
        GridBagConstraints gbc_separator = new GridBagConstraints();
        gbc_separator.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator.gridwidth = 2;
        gbc_separator.insets = new Insets(0, 45, 5, 45);
        gbc_separator.gridx = 0;
        gbc_separator.gridy = 1;
        navigatingPanel.add(separator, gbc_separator);
        
        lblTeacher = new JLabel("Teacher:");
        lblTeacher.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTeacher.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblTeacher = new GridBagConstraints();
        gbc_lblTeacher.fill = GridBagConstraints.BOTH;
        gbc_lblTeacher.insets = new Insets(0, 0, 5, 5);
        gbc_lblTeacher.gridx = 0;
        gbc_lblTeacher.gridy = 2;
        navigatingPanel.add(lblTeacher, gbc_lblTeacher);
        
        cbBTeacher = new JComboBox<>();
        cbBTeacher.setModel(new DefaultComboBoxModel<String>(
                getChoices("select distinct TC_ID from TEACHER where DPM_ID in (SELECT DPM_ID from MANAGER where MNG_ID = '" + managerID + "')")));
        cbBTeacher.setFont(new Font("Tahoma", Font.PLAIN, 16));
        cbBTeacher.setSelectedIndex(-1);
        GridBagConstraints gbc_cbBTeacher = new GridBagConstraints();
        gbc_cbBTeacher.insets = new Insets(5, 0, 10, 10);
        gbc_cbBTeacher.fill = GridBagConstraints.BOTH;
        gbc_cbBTeacher.gridx = 1;
        gbc_cbBTeacher.gridy = 2;
        navigatingPanel.add(cbBTeacher, gbc_cbBTeacher);
        
        btnOpenTeacherController = new JButton("Open Teacher Controller");
        btnOpenTeacherController.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_btnOpenTeacherControler = new GridBagConstraints();
        gbc_btnOpenTeacherControler.fill = GridBagConstraints.BOTH;
        gbc_btnOpenTeacherControler.gridwidth = 2;
        gbc_btnOpenTeacherControler.insets = new Insets(15, 40, 5, 40);
        gbc_btnOpenTeacherControler.gridx = 0;
        gbc_btnOpenTeacherControler.gridy = 3;
        navigatingPanel.add(btnOpenTeacherController, gbc_btnOpenTeacherControler);
        
        separator_1 = new JSeparator();
        GridBagConstraints gbc_separator_1 = new GridBagConstraints();
        gbc_separator_1.insets = new Insets(5, 20, 5, 20);
        gbc_separator_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator_1.gridwidth = 2;
        gbc_separator_1.gridx = 0;
        gbc_separator_1.gridy = 4;
        navigatingPanel.add(separator_1, gbc_separator_1);
        
        lblDepartmentOutcomesDetail = new JLabel("Departmental Outcomes Detail");
        lblDepartmentOutcomesDetail.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        GridBagConstraints gbc_lblDepartmentOutcomesDetail = new GridBagConstraints();
        gbc_lblDepartmentOutcomesDetail.gridwidth = 2;
        gbc_lblDepartmentOutcomesDetail.insets = new Insets(0, 0, 5, 0);
        gbc_lblDepartmentOutcomesDetail.gridx = 0;
        gbc_lblDepartmentOutcomesDetail.gridy = 5;
        navigatingPanel.add(lblDepartmentOutcomesDetail, gbc_lblDepartmentOutcomesDetail);
        
        btnAverageAchievedPercentage = new JButton("Achieving Percentage In Average");
        btnAverageAchievedPercentage.setFont(new Font("Tahoma", Font.PLAIN, 15));
        btnAverageAchievedPercentage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        GridBagConstraints gbc_btnAverageAchievedPercentage = new GridBagConstraints();
        gbc_btnAverageAchievedPercentage.fill = GridBagConstraints.BOTH;
        gbc_btnAverageAchievedPercentage.gridwidth = 2;
        gbc_btnAverageAchievedPercentage.insets = new Insets(10, 10, 5, 10);
        gbc_btnAverageAchievedPercentage.gridx = 0;
        gbc_btnAverageAchievedPercentage.gridy = 6;
        navigatingPanel.add(btnAverageAchievedPercentage, gbc_btnAverageAchievedPercentage);
        
        lblStudentInside = new JLabel("Student Inside:");
        lblStudentInside.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_lblStudentInside = new GridBagConstraints();
        gbc_lblStudentInside.anchor = GridBagConstraints.SOUTH;
        gbc_lblStudentInside.insets = new Insets(10, 0, 10, 5);
        gbc_lblStudentInside.gridx = 0;
        gbc_lblStudentInside.gridy = 7;
        navigatingPanel.add(lblStudentInside, gbc_lblStudentInside);
        
        cbBStudent = new JComboBox<>();
        cbBStudent.setModel(new DefaultComboBoxModel<String>(
                getChoices("select STD_ID from STUDENT where Status = 'Graduated' and DPM_ID in (select DPM_ID from MANAGER where MNG_ID = '" + managerID + "')")));
        cbBStudent.setSelectedIndex(-1);
        cbBStudent.setFont(new Font("Tahoma", Font.PLAIN, 15));
        GridBagConstraints gbc_cbBStudent = new GridBagConstraints();
        gbc_cbBStudent.insets = new Insets(20, 0, 5, 10);
        gbc_cbBStudent.fill = GridBagConstraints.BOTH;
        gbc_cbBStudent.gridx = 1;
        gbc_cbBStudent.gridy = 7;
        navigatingPanel.add(cbBStudent, gbc_cbBStudent);
        
        btnOk = new JButton("OK");
        btnOk.setFont(new Font("Tahoma", Font.PLAIN, 16));
        GridBagConstraints gbc_btnOk = new GridBagConstraints();
        gbc_btnOk.fill = GridBagConstraints.BOTH;
        gbc_btnOk.insets = new Insets(20, 40, 5, 40);
        gbc_btnOk.gridx = 1;
        gbc_btnOk.gridy = 8;
        navigatingPanel.add(btnOk, gbc_btnOk);
        
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
        
        
        chartPanel = new ChartPanel(getBarChartForDepartmentalOutcomes(new ArrayList<String>(), new ArrayList<Float>()));
        GridBagConstraints gbc_chartPanel = new GridBagConstraints();
        gbc_chartPanel.fill = GridBagConstraints.BOTH;
        gbc_chartPanel.gridx = 0;
        gbc_chartPanel.gridy = 0;
        displayPanel.add(chartPanel, gbc_chartPanel);
               
        separator_2 = new JSeparator();
        GridBagConstraints gbc_separator_2 = new GridBagConstraints();
        gbc_separator_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_separator_2.gridx = 0;
        gbc_separator_2.gridy = 1;
        displayPanel.add(separator_2, gbc_separator_2);
        
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
         * Set application to original window when logged out.
         */
        Container rootPane = root.getContentPane();
        btnLogout.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(root, "Are you sure to log out?", "Message", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    ManagerWindow.root.setContentPane(rootPane);
                    ManagerWindow.root.firePropertyChange("contentPane", '*', ' ');
                    ManagerWindow.root.setVisible(true);
                }
            }
        });

        
        btnOpenTeacherController.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                TeacherWindow teacherWindow = new TeacherWindow((String)cbBTeacher.getSelectedItem(), conn, root);
                teacherWindow.tfInputSemester.setEnabled(false);
                teacherWindow.tfInputSubject.setEnabled(false);
                teacherWindow.tfInputYear.setEnabled(false);
                teacherWindow.btnLogout.setEnabled(false);
                teacherWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                teacherWindow.setVisible(true);
                
            }
        });
        
        /**
         * Showing achieving percentage in average of each departmental outcomes bases on all graduated student's data.
         */
        btnAverageAchievedPercentage.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent arg0) {
                Statement stmt = null;
                String query = "select STD_ID from STUDENT where Status = 'Graduated' and DPM_ID in (select DPM_ID from MANAGER where MNG_ID = '"
                        + managerID + "')";
                List<String> graduatedStudents = new ArrayList<>();
                DecimalFormat df = new DecimalFormat("#.#");
                try {
                    stmt = conn.createStatement();
                    ResultSet rs = stmt.executeQuery(query);
                    while (rs.next()) {
                        graduatedStudents.add(rs.getString(1));
                    }
                    Map<String, Float> averagePercentPerOutcomes = new HashMap<>();
                    for (String studentID: graduatedStudents) {
                        Map<String, Float> percentPerOutcome = new HashMap<>();
                        Map<String, Integer> outcomeCounting = new HashMap<>();
                        query = "select PAPR.SJ_ID, PAPR.Semester, PAPR.SchoolYear, (PAPR.ProcessScore+PAPR.PracticalScore+MTR.Total+ETR.Total) as DTB from PROCESSANDPRACTICALRESULT as PAPR " +
                                "JOIN MIDTERMRESULT as MTR on PAPR.STD_ID = MTR.STD_ID and PAPR.SJ_ID = MTR.SJ_ID and PAPR.SchoolYear = MTR.SchoolYear and PAPR.Semester = MTR.Semester " +
                                "JOIN ENDTERMRESULT as ETR on PAPR.STD_ID = ETR.STD_ID and PAPR.SJ_ID = ETR.SJ_ID and PAPR.SchoolYear = ETR.SchoolYear and PAPR.Semester = ETR.Semester " +
                                "where PAPR.STD_ID = '" + studentID + "'";
                        stmt = conn.createStatement();
                        rs = stmt.executeQuery(query);
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
                        for (int i=0; i<percents.size(); ++i)
                            percents.set(i, Float.parseFloat(df.format(percents.get(i))));
                        
                        for (int i=0; i<outcomes.size(); ++i) {
                            if (averagePercentPerOutcomes.putIfAbsent(outcomes.get(i), percents.get(i)) != null) {
                                averagePercentPerOutcomes.put(outcomes.get(i), averagePercentPerOutcomes.get(outcomes.get(i))+percents.get(i));
                                outcomeCounting.put(outcomes.get(i), outcomeCounting.get(outcomes.get(i))+1);
                            }
                            else outcomeCounting.putIfAbsent(outcomes.get(i), 1);
                        }
                        for (String outcome: averagePercentPerOutcomes.keySet())
                            averagePercentPerOutcomes.put(outcome, Float.parseFloat(df.format(averagePercentPerOutcomes.get(outcome)/outcomeCounting.get(outcome))));
                        List<String> departmentalOutcomes = new ArrayList<String>(averagePercentPerOutcomes.keySet());
                        List<Float> percentPerOutcomes = new ArrayList<>();
                        departmentalOutcomes.sort((o1, o2) -> {return o1.compareTo(o2);});
                        for (String each: departmentalOutcomes) percentPerOutcomes.add(averagePercentPerOutcomes.get(each));
                        chartPanel.setChart(getBarChartForDepartmentalOutcomes(departmentalOutcomes, percentPerOutcomes));
                        chartPanel.setPreferredSize(new Dimension(800, 380));
                        StudentWindow.customizeChartPlotting(chartPanel.getChart());
                        chartPanel.setVisible(true);
                        tAreaInfo.setText("");
                        
                        query = "select DPMOC_ID, DPMContent from DEPARTMENTALOUTCOMES WHERE DPM_ID = (select DPM_ID from MANAGER where MNG_ID = '" + managerID + "') ORDER BY DPMOC_ID ASC";
                        rs = stmt.executeQuery(query);
                        while (rs.next()) {
                            tAreaInfo.setText(tAreaInfo.getText() + rs.getString(1) + ": " + rs.getString(2) + "\n");
                        }
                    }
                } catch (SQLException ex) {
                    StudentWindow.notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                } finally {
                    if (stmt != null) { try {
                        stmt.close();
                    } catch (SQLException e1) {
                        StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                      }
                    }
                }
                
            }
        });
        
        /**
         * Showing achieving percentage of each student
         */
        btnOk.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                String studentID = (String)cbBStudent.getSelectedItem();
                Map<String, Float> percentPerOutcome = new HashMap<>();
                Map<String, Integer> outcomeCounting = new HashMap<>();
                String query = "select PAPR.SJ_ID, PAPR.Semester, PAPR.SchoolYear, (PAPR.ProcessScore+PAPR.PracticalScore+MTR.Total+ETR.Total) as DTB from PROCESSANDPRACTICALRESULT as PAPR " +
                        "JOIN MIDTERMRESULT as MTR on PAPR.STD_ID = MTR.STD_ID and PAPR.SJ_ID = MTR.SJ_ID and PAPR.SchoolYear = MTR.SchoolYear and PAPR.Semester = MTR.Semester " +
                        "JOIN ENDTERMRESULT as ETR on PAPR.STD_ID = ETR.STD_ID and PAPR.SJ_ID = ETR.SJ_ID and PAPR.SchoolYear = ETR.SchoolYear and PAPR.Semester = ETR.Semester " +
                        "where PAPR.STD_ID = '" + studentID + "'";
               Statement stmt = null;
               if (ManagerWindow.conn == null) ManagerWindow.conn = (new DatabaseConnection(root)).getConnection();
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
                   StudentWindow.customizeChartPlotting(chartPanel.getChart());
                   chartPanel.setVisible(true);
                   tAreaInfo.setText("");
                   
                   query = "select DPMOC_ID, DPMContent from DEPARTMENTALOUTCOMES WHERE DPM_ID = (select DPM_ID from STUDENT where STD_ID = '" + studentID + "') ORDER BY DPMOC_ID ASC";
                   rs = stmt.executeQuery(query);
                   while (rs.next()) {
                       tAreaInfo.setText(tAreaInfo.getText() + rs.getString(1) + ": " + rs.getString(2) + "\n");
                   }
                   
               } catch (SQLException ex) {
                   StudentWindow.notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
               } finally {
                   if (stmt != null) { try {
                       stmt.close();
                   } catch (SQLException e1) {
                       StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                     }
                   }
               }

                
            }
        });
        
    }
    
    private JFreeChart getBarChartForDepartmentalOutcomes(List<String> outcomes, List<Float> percents) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        for (int i=0; i<outcomes.size(); ++i) 
            dataset.addValue(percents.get(i), "Average", outcomes.get(i));
        return ChartFactory.createBarChart("Achieving Percentage In Average of each Departmental Outcomes", "Departmental Outcomes", "Percentage (%)", dataset, PlotOrientation.VERTICAL, true, true, false);
        
    }

    private String[] getChoices(String query) {
        if (ManagerWindow.conn == null)
            ManagerWindow.conn = (new DatabaseConnection(root)).getConnection();
        Statement stmt = null;
        List<String> resultList = new ArrayList<>();
        try {
            stmt = ManagerWindow.conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next())
               resultList.add(rs.getString(1));
        } catch (SQLException ex) {
            StudentWindow.notifyMessage(ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            if (stmt != null) { try {
                stmt.close();
            } catch (SQLException e1) {
                StudentWindow.notifyMessage(e1.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
              }
            }
        }
        return resultList.toArray(new String[0]);
    }
}
