import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.sql.*;

public class demo{
    JFrame f;
    JTable userTable;
    JTableHeader header;
    JScrollPane pane;
    int id;
    String name,email,emailva,password,remtok,creat,updat;
    public static void main(String[] args) throws Exception {
        new demo();
    }
    public demo() throws Exception {
        f = new JFrame("Creating a Scrollable JTable!");
        //JPanel panel = new JPanel();
        String data[][] = {{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"},{"001","vinod","Bihar","India","Biology","65","First"},{"002","Raju","ABC","Kanada","Geography","58","second"},{"003","Aman","Delhi","India","computer","98","Dictontion"},{"004","Ranjan","Bangloor","India","chemestry","90","Dictontion"}};
        //String col[] = {"Id","Name","Email","Email ver at","Password","Remember tok at","Created at","Updated at"};
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/forum","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        ResultSetMetaData rsmd = rs.getMetaData();
        int cols = rsmd.getColumnCount();
        String[] col = new String[cols];
        for (int i=0;i<cols;i++)
            col[i] = rsmd.getColumnName(i+1);
        userTable = new JTable(data,col);
        /*DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        while (rs.next()){
            id=rs.getInt(1);
            name=rs.getString(2);
            email = rs.getString(3);
            emailva = rs.getString(4);
            password = rs.getString(5);
            remtok = rs.getString(6);
            creat = rs.getString(7);
            updat = rs.getString(8);
            String[] row = {String.valueOf(id),name,email,emailva,password,remtok,creat,updat};
            model.addColumn(row);
        }*/
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        header = userTable.getTableHeader();
        header.setBackground(Color.yellow);
        pane = new JScrollPane(userTable);
        f.add(pane);
        pane.setBounds(5,200,460,250);
        f.setSize(700,500);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.setVisible(true);
        /*
        userTable = new JTable();
        userTable.setBounds(5,200,460,300);
        pancontent.add(userTable);
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/forum","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name,address,telephone_no,email,age,gender,username FROM users");
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i=0;i<cols;i++)
            colName[i] = rsmd.getColumnName(i+1);
        model.setColumnIdentifiers(colName);
        int age;
        String name,address,telephone_no,email,gender,username;
        while (rs.next()){
            name=rs.getString(1);
            address=rs.getString(2);
            telephone_no = rs.getString(3);
            email = rs.getString(4);
            age = rs.getInt(5);
            gender = rs.getString(6);
            username = rs.getString(7);
            String[] row = {name,address,telephone_no,email,String.valueOf(age),gender,username};
            model.addRow(row);
        }
        stmt.close();
        con.close();
         */
    }
}