/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Enrollment;

import eye_judge.EditAdmin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import splashdemo.LoginFrame;

/**
 *
 * @author STARLING
 */
public class EnrollUser {
    private int ID;
    private String fname;
    private String lname;
    private String gender;
    private String irisCode;
    private boolean enrollEn;
    private boolean userdetail;
    private boolean is_admin;
    private boolean admin;
    private boolean adminchange;
    private boolean isChange;
    private Connection conn;
    private Statement st;
    private LoginFrame loginFrame;
    private String db="jdbc:mysql://localhost:3306/eyejudge";

    //constructor for enrolling the user details along with iris code
    public EnrollUser(int ID,String fName,String lName,String gender,String iriscode){
        this.ID=ID;
        this.fname=fName;
        this.lname=lName;
        this.gender=gender;
        this.irisCode=iriscode;
        this.enrollEn=true;
    }

    //constructor for getting unique id for new user
    public EnrollUser(){
        this.enrollEn=false;
        this.userdetail=false;
        this.is_admin=false;
    }
    //constructor for getting the userdetail of inputted id
    public EnrollUser(int id){
        this.ID=id;
        this.userdetail=true;
        this.enrollEn=false;
    }
    public EnrollUser(String role,LoginFrame login){
        this.loginFrame=login;
        this.admin=false;
        if(role.equals("admin")){
            this.admin=true;
        }
        this.is_admin=false;
        this.enrollEn=false;
        this.userdetail=false;

    }

    public void setAdminchage(boolean adch){
        this.adminchange=adch;
    }
    public int getID(){
        return this.ID;
    }
    public String getFname(){
        return this.fname;
    }
    public String getLname(){
        return this.lname;
    }
    public String getGender(){
        return this.gender;
    }
    public String getIriscode(){
        return this.irisCode;
    }
    public boolean getIs_admin(){
        return this.is_admin;
    }

    //connect to the database
    public void connectToDatabase() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(db, "root", "");
            System.out.println("*****connected to database*****");
            st = conn.createStatement();
            performQuery();
            st.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(EnrollUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(EnrollUser.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }

    public boolean getIsChange(){
        return this.isChange;
    }
    //perform the function according to the query
    private void performQuery() throws SQLException{
        String query=null;
        if(adminchange){
            System.out.println("get "+EditAdmin.getUsername());
            System.out.println("get "+EditAdmin.getCurpass());
            System.out.println("get "+EditAdmin.getNewpass());
            this.isChange=changeAdmin(EditAdmin.getUsername(),EditAdmin.getCurpass(),EditAdmin.getNewpass());
        }
        if(admin){
            this.is_admin=authorizedAdmin(this.loginFrame.getUsername(),this.loginFrame.getPassword());
        }
        if(enrollEn){
                query = "insert into personinfo values ("+this.ID+",'"+this.fname+"','"+this.lname+"','"+this.gender+"','"+this.irisCode+"')";
                st.executeUpdate(query);
            }
            else{
            if(!userdetail){
                query="select max(ID) from personinfo";
                ResultSet rs=st.executeQuery(query);
                rs.next();
                this.ID=rs.getInt(1)+1;
                System.out.println(this.ID);
            }
            else{
                 query="select FirstName,LastName,Gender from personinfo where ID="+this.ID;
                 ResultSet rs=st.executeQuery(query);
                 while (rs.next()) {
                    this.fname=rs.getString("FirstName");
                    this.lname=rs.getString("LastName");
                    this.gender=rs.getString("Gender");
                 }
             }
            }

    }

    public boolean changeAdmin(String uname, String cpwd, String npwd){
        boolean Changed=false;
        try {
            String query="update admin set Username='"+uname+"',Password='"+npwd+"' where Password='"+cpwd+"'";
            if(st.executeUpdate(query)!=0){
                Changed=true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
                Changed=false;
        }
        return Changed;        
    }

    public boolean authorizedAdmin(String uname,String pwd){
        boolean isAdmin=false;
        System.out.println(uname+" "+pwd);
        try {
            String query = "select * from admin where Username='"+uname+"'";// and Password='" + pwd+"'";
            ResultSet rs = st.executeQuery(query);
            while (rs.next()) {
                isAdmin=true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.toString());
                isAdmin=false;
        }
        return isAdmin;
    }


   
}
