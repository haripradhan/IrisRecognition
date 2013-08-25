/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Identification;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author HP
 */
public class MatchIris {
    private String code;
    public MatchIris(String irisCode){
        code=irisCode;
    }

 public void connectToDatabase() {       
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn;
            String db = "jdbc:mysql://localhost:3306/eyejudge";
            conn = DriverManager.getConnection(db, "root", "");
            System.out.println("*****connected to database*****");
            Statement st = conn.createStatement();
            String query = "select * from personinfo";
            ResultSet rs = st.executeQuery(query);
            String dbCode = "";
            float count = 0,countPix=0;
            while (rs.next()) {
                dbCode = rs.getString("IrisCode");
                count = 0;
                countPix = 0;
                for (int i = 0; i < code.length(); i++) {
                    if (code.charAt(i)!=dbCode.charAt(i)) {
                        count++;
                    }
                    countPix++;
                }
            }
            // System.out.println("count:"+count);
            // System.out.println("totalPix: "+countPix);
            float HD = count / countPix;
            System.out.println("Hamming Distance: " + HD);
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MatchIris.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(MatchIris.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
