import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.*;

public class UserDashboard implements ActionListener,MouseListener {
    JFrame f;
    JLabel lblwelcome,lblmyprofile;
    JButton btnlogout;
    JComboBox cbxordproname;
    JTextField tfordprocat,tfordqty,tfordpri,tfordtotpri;
    JButton btnproceed,btnorder;
    String loginUser;
    int stockquantity;
    public UserDashboard(String user) throws Exception{
        loginUser = user;
        f = new JFrame("IMS: User Dashboard");
        f.setSize(400,400);
        lblwelcome = new JLabel("Welcome, "+user+" to the Inventory Management System!");
        lblwelcome.setBounds(10,50,350,20);
        f.add(lblwelcome);
        lblmyprofile = new JLabel("My Profile");
        lblmyprofile.setBounds(230,20,80,25);
        f.add(lblmyprofile);
        lblmyprofile.addMouseListener(this);
        btnlogout = new JButton("Logout");
        btnlogout.setBounds(300,20,80,25);
        f.add(btnlogout);
        btnlogout.addActionListener(this);

        //View for ordering
        JLabel lblorder = new JLabel("Please Select the quantity you want to order:");
        lblorder.setBounds(10,90,300,20);
        f.add(lblorder);
        JLabel lblorderpro = new JLabel("Please Select Product:");
        lblorderpro.setBounds(10,115,150,20);
        f.add(lblorderpro);
        cbxordproname = new JComboBox();
        cbxordproname.removeAll();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT product_name from product");
        while (rs.next()){
            cbxordproname.addItem(rs.getString("product_name"));
        }
        cbxordproname.setBounds(160,115,150,20);
        f.add(cbxordproname);
        JLabel lblprocat = new JLabel("Product Category:");
        lblprocat.setBounds(10,140,150,20);
        f.add(lblprocat);
        tfordprocat = new JTextField();
        tfordprocat.setBounds(160,140,150,20);
        f.add(tfordprocat);
        tfordprocat.setEditable(false);
        JLabel lblorderqua = new JLabel("Enter Quantity:");
        lblorderqua.setBounds(10,165,150,20);
        f.add(lblorderqua);
        tfordqty = new JTextField();
        tfordqty.setBounds(160,165,150,20);
        f.add(tfordqty);
        JLabel lblorderpri = new JLabel("Enter Price:");
        lblorderpri.setBounds(10,190,150,20);
        f.add(lblorderpri);
        tfordpri = new JTextField();
        tfordpri.setBounds(160,190,150,20);
        f.add(tfordpri);
        JLabel lblordtotpri = new JLabel("Total Price:");
        lblordtotpri.setBounds(10,215,150,20);
        f.add(lblordtotpri);
        tfordtotpri = new JTextField();
        tfordtotpri.setBounds(160,215,150,20);
        f.add(tfordtotpri);
        tfordtotpri.setEditable(false);
        btnproceed = new JButton("Calculate");
        btnproceed.setBounds(210,245,100,20);
        f.add(btnproceed);
        btnproceed.addActionListener(this);
        btnorder = new JButton("Order");
        btnorder.setBounds(210,270,100,20);
        f.add(btnorder);
        btnorder.setEnabled(false);
        btnorder.addActionListener(this);

        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public boolean calculateOrder() throws Exception{
        if(tfordqty.getText().equals("")){
            JOptionPane.showMessageDialog(f,"Please enter the quantity you want:");
            return false;
        } else if (tfordpri.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please enter the price you want this product at:");
            return false;
        }else {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from product WHERE product_name='"+cbxordproname.getSelectedItem()+"'");
            rs.next();
            tfordprocat.setText(rs.getString("category_name"));
            Statement stmt1 = con.createStatement();
            ResultSet rs1 = stmt1.executeQuery("SELECT remaining_quantity FROM stock WHERE name='"+cbxordproname.getSelectedItem()+"'");
            rs1.next();
            stockquantity = rs1.getInt(1);
            stmt1.close();
            //int stockquantity = Integer.parseInt(String.valueOf(rs.getInt("quantity")));
            int purchasedprice = Integer.parseInt(String.valueOf(rs.getInt("price")));
            if (stockquantity<Integer.parseInt(tfordqty.getText())){
                JOptionPane.showMessageDialog(f,"There is only "+stockquantity+" qunatity on stock");
                tfordqty.setText(String.valueOf(stockquantity));
                return false;
            }
            if(purchasedprice>Integer.parseInt(tfordpri.getText())){
                JOptionPane.showMessageDialog(f,"We cannot sell you at that price!");
                return false;
            }
            stmt.close();
            con.close();
            tfordtotpri.setText(String.valueOf(Integer.parseInt(tfordpri.getText())*Integer.parseInt(tfordqty.getText())));
            return true;
        }
    }
    public void orderProduct() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        PreparedStatement ps = con.prepareStatement("INSERT INTO tblorder VALUES (null,?,?,?,?,?,?)");
        ps.setString(1,loginUser);
        ps.setString(2, (String) cbxordproname.getSelectedItem());
        ps.setString(3,tfordprocat.getText());
        ps.setInt(4,Integer.parseInt(tfordqty.getText()));
        ps.setInt(5,Integer.parseInt(tfordpri.getText()));
        ps.setInt(6,Integer.parseInt(tfordtotpri.getText()));
        int r = ps.executeUpdate();
        JOptionPane.showMessageDialog(f,"Your order has been placed Sucessfully!");
        updateStock();
        cbxordproname.setEnabled(true);
        tfordqty.setEnabled(true);
        tfordpri.setEnabled(true);
        btnproceed.setEnabled(true);
        btnorder.setEnabled(false);
        tfordprocat.setText("");
        tfordqty.setText("");
        tfordpri.setText("");
        tfordtotpri.setText("");
        ps.close();
        con.close();
    }
    public void updateStock() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt1.executeQuery("SELECT sold_quantity FROM stock WHERE name='"+cbxordproname.getSelectedItem()+"'");
        rs.next();
        int soldQuantity = rs.getInt(1);
        stmt1.close();
        System.out.println(soldQuantity);
        Statement stmt = con.createStatement();
        int r = stmt.executeUpdate("UPDATE stock SET sold_quantity="+(Integer.parseInt(tfordqty.getText())+soldQuantity)+",remaining_quantity="+(stockquantity-Integer.parseInt(tfordqty.getText()))+" WHERE name='"+cbxordproname.getSelectedItem()+"'");
        System.out.println("Stock updated Sucessfully");
        stmt.close();
        con.close();
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnlogout){
            f.dispose();
            new Homepage();
        } else if (e.getSource()==btnproceed) {
            try {
                if(calculateOrder()==true){
                    tfordqty.setEnabled(false);
                    tfordpri.setEnabled(false);
                    cbxordproname.setEnabled(false);
                    btnproceed.setEnabled(false);
                    btnorder.setEnabled(true);
                }
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnorder) {
            try {
                orderProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void mouseClicked(MouseEvent e){
        f.dispose();
        try {
            new UserProfile(loginUser);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e){}

}
