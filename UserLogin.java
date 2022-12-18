package Java_Project;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class UserLogin {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        UserLogin.insertRecord();
        UserLogin.login();

    }

    static UserRegister uobj = new UserRegister(101, "Shantanu", "shan@123");
    static Scanner ch = new Scanner(System.in);

    static public java.sql.Connection getConnection() throws ClassNotFoundException {
        java.sql.Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3305/java_project", "root", "shantanu");

        } catch (SQLException e) {
            System.out.println(e);

        }
        return con;
    }

    static public void insertRecord() throws ClassNotFoundException, SQLException {

        java.sql.Connection con = UserLogin.getConnection();

        String sql = "insert into Userdetails (userId,userName,password) values(?,?,?)";
        java.sql.PreparedStatement pstmt = con.prepareStatement(sql);
        pstmt.setInt(1, uobj.getUserId());
        pstmt.setString(2, uobj.getUserName());
        pstmt.setString(3, uobj.getUserPassword());

        int i = pstmt.executeUpdate();

        if (i > 0) {
            System.out.println(i + " row inserted Successfully");
        } else {
            System.out.println("No row affected.");
        }
    }

    static public void login() throws ClassNotFoundException, SQLException {

        System.out.println("Enter the UserID : ");
        int userId = ch.nextInt();

        System.out.println("Enter the Password : ");
        String userPassword = ch.next();
        java.sql.Connection con = UserLogin.getConnection();

        java.sql.PreparedStatement pstmt = con.prepareStatement("select * from Userdetails where userId = ? and password = ? ");
        pstmt.setInt(1,userId);
        pstmt.setString(2,userPassword);
        ResultSet rs = pstmt.executeQuery();

        // rs.next();

        // int Uid = rs.getInt(1);
        // String pass = rs.getString(3);

        // if ((userId == Uid) && (userPassword.equals(pass))) {
        if (rs.next()) {

            System.out.println("Enter the Referral Code : ");
            String referalCode = ch.next();

            uobj.setReferralCode(referalCode);

            String sql1 = "update Userdetails set referalCode = ? where userId = ?";
            java.sql.PreparedStatement pstmt1 = con.prepareStatement(sql1);
            pstmt1.setString(1, uobj.getReferralCode());
            pstmt1.setInt(2, uobj.getUserId());

            int i = pstmt1.executeUpdate();

            if (i > 0) {
                System.out.println(i + " row updated Successfully");
            } else {
                System.out.println("No row affected.");
            }

        } else {
            System.out.println("UseNAME OR PASSWORD UNMATCHED");
        }
        ch.close();
    }

}
