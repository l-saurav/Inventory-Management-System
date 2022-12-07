import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ManageProduct extends AdminDashboard{
    JTextField tfproid,tfproname,tfproquantity,tfproprice,tfprodes;
    JComboBox cbxcatname;
    public ManageProduct() throws Exception {
        cbxcatname = new JComboBox();
        cbxcatname.removeAll();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name from categories");
        while (rs.next()){
            cbxcatname.addItem(rs.getString("name"));
        }
        cbxcatname.setBounds(10,20,150,20);
        pancontent.add(cbxcatname);
    }
}
