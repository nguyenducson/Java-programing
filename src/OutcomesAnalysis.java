package src;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

class OutcomesAnalysis {
    
    /**
     * Get a map of outcomes and its array of obtained percentage in four assessing domains for specific student, subjectID, semester and schoolYear.
     */
    static Map<String, float[]> getSubjectOutcomesResultOfStudent(Connection conn, String studentID, String subjectID, int semester, int schoolYear) throws SQLException {
        Map<String, float[]> studentResult = new HashMap<String, float[]>();
        Map<String, float[]> outcomesAnalysis = OutcomesAnalysis.getSubjectOutcomesAnalysis(conn, subjectID);
        for (String outcome: outcomesAnalysis.keySet())
            studentResult.putIfAbsent(outcome, new float[] {0, 0, 0, 0});
        String query = null;
        Statement stmt = null;
        
        conn.setAutoCommit(true);
        stmt = conn.createStatement();
        
        //Processing of process and practice result;
        query = "select ProcessScore, PracticalScore from PROCESSANDPRACTICALRESULT where STD_ID = '" + studentID + "' and SJ_ID = '" + subjectID + "' and Semester = " + semester + " and SchoolYear = " + schoolYear;
        ResultSet rs = stmt.executeQuery(query);
        while (rs.next()) {
            for (String outcome: outcomesAnalysis.keySet()) {
                studentResult.get(outcome)[0] = outcomesAnalysis.get(outcome)[0]*(rs.getFloat(1)/10);
                studentResult.get(outcome)[1] = outcomesAnalysis.get(outcome)[1]*(rs.getFloat(2)/10);
            }
        }
        
        //Processing of Mid-term result
        
        //Calculate max score each outcome hold in mid-term test
        Map<String, Float> expectedMidtermScoreOfOutcomes = new HashMap<>();
        query = "select * from MIDTERMTEST where SJ_ID = '" + subjectID + "' and Semester = " + semester + " and SchoolYear = " + schoolYear;
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            for (int i=9; i<14; ++i) {
                if (rs.getString(i) != null) {
                    float eachPortion = rs.getFloat(i-5)/rs.getString(i).trim().split(",").length;
                    for (String outcome: rs.getString(i).trim().split(",")) 
                        if (expectedMidtermScoreOfOutcomes.putIfAbsent(outcome, eachPortion) != null)
                            expectedMidtermScoreOfOutcomes.put(outcome, expectedMidtermScoreOfOutcomes.get(outcome)+eachPortion);
                }
            }
        }
        
        //Calculate real score each outcome got in mid-term result
        Map<String, Float> realMidtermScoreOfOutcomes = new HashMap<>();
        query = "select Result.Part1, Result.Part2, Result.Part3, Result.Part4, Result.Part5, Test.P1_OCs, Test.P2_OCs, Test.P3_OCs, Test.P4_OCs, Test.P5_OCs from"
                + " MIDTERMRESULT as Result JOIN MIDTERMTEST as Test on Result.SJ_ID = Test.SJ_ID and Result.Semester = Test.Semester and Result.SchoolYear = Test.SchoolYear"
                + " where Result.STD_ID = '" + studentID + "' and Result.SJ_ID = '" + subjectID + "' and Result.Semester = " + semester + " and Result.SchoolYear = " + schoolYear;
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            for (int i=6; i<11; ++i) {
                if (rs.getString(i) != null) {
                    float eachPortion = rs.getFloat(i-5)/rs.getString(i).trim().split(",").length;
                    for (String outcome: rs.getString(i).trim().split(",")) 
                        if (realMidtermScoreOfOutcomes.putIfAbsent(outcome, eachPortion) != null)
                            realMidtermScoreOfOutcomes.put(outcome, realMidtermScoreOfOutcomes.get(outcome)+eachPortion);
                }
            }
        }
        //Calculate real percent each outcome achieved in four domains.
        for (String outcome: outcomesAnalysis.keySet())
            if (expectedMidtermScoreOfOutcomes.get(outcome) != null)
                studentResult.get(outcome)[2] = outcomesAnalysis.get(outcome)[2]*(realMidtermScoreOfOutcomes.get(outcome)/expectedMidtermScoreOfOutcomes.get(outcome));
        
        //Processing of End-term result
        
        //Calculate max score each outcome hold in end-term test
        Map<String, Float> expectedEndtermScoreOfOutcomes = new HashMap<>();
        query = "select * from ENDTERMTEST where SJ_ID = '" + subjectID + "' and Semester = " + semester + " and SchoolYear = " + schoolYear;
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            for (int i=9; i<14; ++i) {
                if (rs.getString(i) != null) {
                    float eachPortion = rs.getFloat(i-5)/rs.getString(i).trim().split(",").length;
                    for (String outcome: rs.getString(i).trim().split(",")) 
                        if (expectedEndtermScoreOfOutcomes.putIfAbsent(outcome, eachPortion) != null)
                            expectedEndtermScoreOfOutcomes.put(outcome, expectedEndtermScoreOfOutcomes.get(outcome)+eachPortion);
                }
            }
        }
        
        //Calculate real score each outcome got in end-term result
        Map<String, Float> realEndtermScoreOfOutcomes = new HashMap<>();
        query = "select Result.Part1, Result.Part2, Result.Part3, Result.Part4, Result.Part5, Test.P1_OCs, Test.P2_OCs, Test.P3_OCs, Test.P4_OCs, Test.P5_OCs from"
                + " ENDTERMTEST as Test JOIN ENDTERMRESULT as Result on Test.SJ_ID = Result.SJ_ID and Test.Semester = Result.Semester and Test.SchoolYear = Result.SchoolYear"
                + " where Result.STD_ID = '" + studentID + "' and Result.SJ_ID = '" + subjectID + "' and Result.Semester = " + semester + " and Result.SchoolYear = " + schoolYear;
        rs = stmt.executeQuery(query);
        while (rs.next()) {
            for (int i=6; i<11; ++i) {
                if (rs.getString(i) != null) {
                    float eachPortion = rs.getFloat(i-5)/rs.getString(i).trim().split(",").length;
                    for (String outcome: rs.getString(i).trim().split(",")) 
                        if (realEndtermScoreOfOutcomes.putIfAbsent(outcome, eachPortion) != null)
                            realEndtermScoreOfOutcomes.put(outcome, realEndtermScoreOfOutcomes.get(outcome)+eachPortion);
                }
            }     
        }
        //Calculate real percent each outcome achieved in four domains.
        for (String outcome: outcomesAnalysis.keySet())
            if (expectedEndtermScoreOfOutcomes.get(outcome) != null)
                studentResult.get(outcome)[3] = outcomesAnalysis.get(outcome)[3]*(realEndtermScoreOfOutcomes.get(outcome)/expectedEndtermScoreOfOutcomes.get(outcome));
        
        DecimalFormat df = new DecimalFormat("#.#");
        for (Map.Entry<String, float[]> entry: studentResult.entrySet())
            for (int i=0; i<4; ++i)
                entry.getValue()[i] = Float.parseFloat(df.format(entry.getValue()[i]));
                
        
        return studentResult;
    }
    
    /**
     * Calculate expecting percents of each outcomes in four assessing domains (process, practice, mid-term and end-term) of specified subject
     * @param subjectID subject to query
     * @return outcomes analysis in four domains.
     * @throws SQLException 
     */
    static Map<String, float[]> getSubjectOutcomesAnalysis(Connection conn, String subjectID) throws SQLException {
        Statement stmt = null;
        Map<String, float[]> outcomesAnalysis = new HashMap<>();
        String query = "select * from SUBJECT where SJ_ID = '" + subjectID + "'";
        conn.setAutoCommit(true);
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        int index=0;
        while (rs.next()) {
            for (int i=3; i<11; i+=2) {
                if (rs.getString(i) != null) {
                    String[] outcomes = rs.getString(i).trim().split(",");
                    float percentage = (float) rs.getInt(i+1);
                    for (String outcome: outcomes) {
                        outcomesAnalysis.putIfAbsent(outcome, new float[] { 0, 0, 0, 0 });
                        if (outcomesAnalysis.containsKey(outcome))
                            outcomesAnalysis.get(outcome)[index] = percentage/outcomes.length; 
                    }
                    ++index;
                }
            }
        }
        for (String key: outcomesAnalysis.keySet()) {
            float coeff = 0;
            for (float each: outcomesAnalysis.get(key))
                coeff += each;
            coeff = 100/coeff;
            DecimalFormat df = new DecimalFormat("#.#");
            for (int i=0; i<4; ++i) 
                outcomesAnalysis.get(key)[i] = Float.parseFloat(df.format(outcomesAnalysis.get(key)[i]*coeff));
        }
        if (stmt != null) stmt.close();
        return outcomesAnalysis;
    }
    
    /**
     * Calculate percent of departmental outcomes in subject in term of each subject's outcomes.
     * @param conn
     * @param subjectID
     * @return 
     * @throws SQLException 
     */
    static Map<String, float[]> getDepartmentalOutcomesAnalysisInSubject(Connection conn, String subjectID) throws SQLException {
        Statement stmt = null;
        Map<String, float[]> departmentalAnalysis = new HashMap<>();
        String query = "select * from SUBJECT where SJ_ID = '" + subjectID + "'";
        
        conn.setAutoCommit(true);
        stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        Map<String, Float> percentOfSubjectOutcomeInTotal = new HashMap<>();
        while (rs.next()) {
            for (int i=3; i<11; i+=2) {
                if (rs.getString(i) != null) {
                    String[] outcomes = rs.getString(i).trim().split(",");
                    float percentage = (float) rs.getInt(i+1);
                    for (String outcome: outcomes) {
                        percentOfSubjectOutcomeInTotal.putIfAbsent(outcome, (float) 0);
                        if (percentOfSubjectOutcomeInTotal.containsKey(outcome))
                            percentOfSubjectOutcomeInTotal.put(outcome, percentOfSubjectOutcomeInTotal.get(outcome) + percentage/outcomes.length);
                    }
                }
            }
        }
            
        query = "select SJOC_ID, DPMOC_IDs from SUBJECTOUTCOMES where SJ_ID = '" + subjectID + "' ORDER BY SJOC_ID ASC";
        rs = stmt.executeQuery(query);
        int size=percentOfSubjectOutcomeInTotal.size();
        int index=0;
        while (rs.next()) {
            String[] departmentOutcomes = rs.getString(2).trim().split(",");
            for (String dpm_outcome: departmentOutcomes) {
                if (!departmentalAnalysis.containsKey(dpm_outcome)) {
                    float[] arrayOfValues = new float[size];
                    for (int i=0;i<size;++i) arrayOfValues[i] = 0;
                    departmentalAnalysis.put(dpm_outcome, arrayOfValues);
                }
                departmentalAnalysis.get(dpm_outcome)[index] = percentOfSubjectOutcomeInTotal.get(rs.getString(1))/departmentOutcomes.length;
            }
            ++index;
        }
            
        for (String key: departmentalAnalysis.keySet()) {
            float coeff = 0;
            for (float each: departmentalAnalysis.get(key))
                coeff += each;
            coeff = 100/coeff;
            DecimalFormat df = new DecimalFormat("#.#");
            for (int i=0; i<size; ++i) 
                departmentalAnalysis.get(key)[i] = Float.parseFloat(df.format(departmentalAnalysis.get(key)[i]*coeff));
        }
            
        if (stmt != null) stmt.close();
        return departmentalAnalysis;
    }
}
