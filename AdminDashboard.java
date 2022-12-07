import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminDashboard implements ActionListener {
    JFrame f;
    JPanel panbtn,pancontent;
    JTextField tfcatid,tfcatname,tfsearchcat,tfrescatid,tfrescatname,tfdelcat,tfproid,tfproname,tfproquantity,tfproprice,tfprodes,tfproide,tfproidee,tfpronamee,tfproquantitye,tfpropricee,tfprodese,tfdelpro;
    JComboBox cbxcatname,cbxcatnamee;
    JButton btnDashboard,btnManageUser,btnManageCategory,btnManageProduct,btnLogout,btnaddcat,btnsearchcat,btnupdatecat,btndelcat,btnaddpro,btneditpro,btnupdatepro, btndelpro;
    JTable userTable;
    int purchase_amount,sales_amount;
    JScrollPane pane;
    public AdminDashboard() throws Exception {
        f = new JFrame("IMS: Admin Dashboard");
        f.setSize(700,500);
        panbtn = new JPanel();
        panbtn.setBounds(5,5,200,455);
        panbtn.setBackground(Color.WHITE);
        f.add(panbtn);
        pancontent = new JPanel();
        pancontent.setBounds(210,5,470,455);
        pancontent.setBackground(Color.WHITE);
        f.add(pancontent);
        btnDashboard = new JButton("Dashboard");
        btnManageUser = new JButton("User Management");
        btnManageCategory = new JButton("Category Management");
        btnManageProduct = new JButton("Product Management");
        btnLogout = new JButton("Log Out");
        btnDashboard.setBounds(10,50,180,30);
        btnManageUser.setBounds(10,100,180,30);
        btnManageCategory.setBounds(10,150,180,30);
        btnManageProduct.setBounds(10,200,180,30);
        btnLogout.setBounds(10,300,180,30);
        panbtn.add(btnDashboard);
        btnDashboard.addActionListener(this);
        panbtn.add(btnManageUser);
        btnManageUser.addActionListener(this);
        panbtn.add(btnManageCategory);
        btnManageCategory.addActionListener(this);
        panbtn.add(btnManageProduct);
        btnManageProduct.addActionListener(this);
        panbtn.add(btnLogout);
        btnLogout.addActionListener(this);
        panbtn.setLayout(null);
        pancontent.setLayout(null);
        f.setLayout(null);
        f.setVisible(true);
        JLabel lblWelcome = new JLabel("Welcome, Admin!");
        lblWelcome.setFont(new Font("Serif",Font.BOLD,30));
        lblWelcome.setBounds(10,100,300,40);
        pancontent.add(lblWelcome);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void dashboardContent() throws Exception{
        //Calculate total user
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(username) from users");
        JLabel lblTotalUser = new JLabel("Total User: ");
        lblTotalUser.setBounds(10,50,150,20);
        pancontent.add(lblTotalUser);
        while (rs.next()){
            lblTotalUser.setText("Total User: "+rs.getInt(1));
        }
        stmt.close();

        //Calculate total category
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt1.executeQuery("SELECT COUNT(category_id) from categories");
        JLabel lblTotalCategory = new JLabel("Total Category: ");
        lblTotalCategory.setBounds(10,80,150,20);
        pancontent.add(lblTotalCategory);
        while (rs1.next()){
            lblTotalCategory.setText("Total Category: "+rs1.getInt(1));
        }
        stmt1.close();

        //Calculate total Product
        Statement stmt2 = con.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT COUNT(product_id),sum(price) from product");
        JLabel lblTotalProduct = new JLabel("Total Product: ");
        lblTotalProduct.setBounds(10,110,150,20);
        pancontent.add(lblTotalProduct);
        JLabel lblTotalPurchase = new JLabel("Total Purchase Amount: ");
        lblTotalPurchase.setBounds(10,140,250,20);
        pancontent.add(lblTotalPurchase);
        while (rs2.next()){
            purchase_amount = rs2.getInt(2);
            lblTotalProduct.setText("Total Product: "+rs2.getInt(1));
            lblTotalPurchase.setText("Total Purchase Amount: "+purchase_amount);
        }
        stmt2.close();

        //Calculate total Sales
        Statement stmt3 = con.createStatement();
        ResultSet rs3 = stmt3.executeQuery("SELECT sum(total_price) from tblorder");
        JLabel lblTotalSales = new JLabel("Total Sales Amount: ");
        lblTotalSales.setBounds(10,170,250,20);
        pancontent.add(lblTotalSales);
        while (rs3.next()){
            sales_amount = rs3.getInt(1);
            lblTotalSales.setText("Total Sales Amount: "+sales_amount);
        }
        stmt3.close();

        //Calculate Profit or Loss
        JLabel lblProfitLoss = new JLabel();
        lblProfitLoss.setBounds(10,200,250,20);
        pancontent.add(lblProfitLoss);
        if(purchase_amount>sales_amount){
            lblProfitLoss.setText("Total Loss: "+ (purchase_amount-sales_amount));
        }else {
            lblProfitLoss.setText("Total Profit: "+(sales_amount-purchase_amount));
        }
        con.close();
    }
    public void manageUserContent() throws Exception{
        userTable = new JTable();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i=0;i<cols;i++)
            colName[i] = rsmd.getColumnName(i+1);
        model.setColumnIdentifiers(colName);
        long tel;
        int age;
        String name,address,email,gender,username,password;;
        while (rs.next()){
            name=rs.getString(1);
            address=rs.getString(2);
            tel = rs.getLong(3);
            email = rs.getString(4);
            age = rs.getInt(5);
            gender = rs.getString(6);
            username = rs.getString(7);
            password = rs.getString(8);
            String[] row = {name,address,String.valueOf(tel),email,String.valueOf(age),gender,username,password};
            model.addRow(row);
        }
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //userTable.setEnabled(false);
        pane = new JScrollPane(userTable);
        pancontent.add(pane);
        pane.setBounds(5,230,460,200);
        stmt.close();
        con.close();
    }
    public void loadCategoryTable() throws Exception{
        //show Categories entry on table
        userTable = new JTable();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categories");
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i=0;i<cols;i++)
            colName[i] = rsmd.getColumnName(i+1);
        model.setColumnIdentifiers(colName);
        int id;
        String name;
        while (rs.next()){
            id=rs.getInt(1);
            name=rs.getString(2);
            String[] row = {String.valueOf(id),name};
            model.addRow(row);
        }
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //userTable.setEnabled(false);
        pane = new JScrollPane(userTable);
        pancontent.add(pane);
        pane.setBounds(290,20,160,420);
        stmt.close();
        con.close();
    }
    public void manageCategory() throws Exception{
        loadCategoryTable();
        //generate view for adding category
        JLabel lblcatid = new JLabel("Enter Category ID:");
        JLabel lblcatname = new JLabel("Enter Category Name:");
        btnaddcat = new JButton("Add Category");
        tfcatid = new JTextField();
        tfcatname = new JTextField();
        lblcatid.setBounds(10,20,250,20);
        tfcatid.setBounds(10,45,200,20);
        lblcatname.setBounds(10,70,250,20);
        tfcatname.setBounds(10,95,200,20);
        pancontent.add(lblcatid);
        pancontent.add(tfcatid);
        pancontent.add(lblcatname);
        pancontent.add(tfcatname);
        btnaddcat.setBounds(100,120,110,20);
        btnaddcat.addActionListener(this);
        pancontent.add(btnaddcat);


        //generate view for Searching category
        JLabel lblSearchCat = new JLabel("Please enter category id to search:");
        tfsearchcat = new JTextField();
        btnsearchcat = new JButton("Search");
        lblSearchCat.setBounds(10,160,250,20);
        tfsearchcat.setBounds(10,185,100,20);
        btnsearchcat.setBounds(150,185,100,20);
        btnsearchcat.addActionListener(this);
        pancontent.add(lblSearchCat);
        pancontent.add(tfsearchcat);
        pancontent.add(btnsearchcat);
        JLabel lblrescatid = new JLabel("ID:");
        JLabel lblrescatname = new JLabel("Name:");
        tfrescatid = new JTextField();
        tfrescatname = new JTextField();
        btnupdatecat = new JButton("Edit");
        lblrescatid.setBounds(10,210,70,20);
        tfrescatid.setBounds(100,210,150,20);
        lblrescatname.setBounds(10,230,70,20);
        tfrescatname.setBounds(100,230,150,20);
        btnupdatecat.setBounds(150,255,100,20);
        pancontent.add(lblrescatid);
        pancontent.add(tfrescatid);
        tfrescatid.setEditable(false);
        pancontent.add(lblrescatname);
        pancontent.add(tfrescatname);
        tfrescatname.setEditable(false);
        pancontent.add(btnupdatecat);
        btnupdatecat.addActionListener(this);

        //generate view for deleting category
        JLabel lbldeletecat = new JLabel("Please Enter Category Id to delete:");
        tfdelcat = new JTextField();
        btndelcat = new JButton("Delete");
        lbldeletecat.setBounds(10,310,250,20);
        tfdelcat.setBounds(10,335,100,20);
        btndelcat.setBounds(150,335,100,20);
        pancontent.add(lbldeletecat);
        pancontent.add(tfdelcat);
        pancontent.add(btndelcat);
        btndelcat.addActionListener(this);
    }
    public boolean validateCategory(String id,String name){
        Pattern numericPattern = Pattern.compile("^\\d$");
        Matcher numberMatches = numericPattern.matcher(id);
        if(id.equals("")){
            JOptionPane.showMessageDialog(f,"Category ID not provided!");
            return false;
        } else if (name.equals("")) {
            JOptionPane.showMessageDialog(f,"Category Name not provided!");
            return false;
        } else if (!numberMatches.matches()){
            JOptionPane.showMessageDialog(f,"Category ID can only be an integer");
            return false;
        }else {
            return true;
        }
    }
    public void insertCategory(String id,String name) throws Exception{
        if(validateCategory(id,name)==true){
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
            PreparedStatement ps = con.prepareStatement("INSERT INTO categories VALUES (?,?)");
            ps.setInt(1,Integer.parseInt(id));
            ps.setString(2,name);
            int r = ps.executeUpdate();
            JOptionPane.showMessageDialog(f,"New category sucessfully added!");
            tfcatid.setText("");
            tfcatname.setText("");
            loadCategoryTable();
            ps.close();
            con.close();
        }
    }

    public void searchCategory() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM categories WHERE category_id="+Integer.parseInt(tfsearchcat.getText()));
        if (rs.next()==false){
            tfsearchcat.setText("");
            tfrescatid.setText("");
            tfrescatname.setText("");
            tfrescatid.setEditable(false);
            tfrescatname.setEditable(false);
            btnupdatecat.setText("Edit");
            JOptionPane.showMessageDialog(f,"No Search Result found!");
        }else {
            tfrescatname.setEditable(true);
            btnupdatecat.setText("Update");
            tfrescatid.setText(String.valueOf(rs.getInt(1)));
            tfrescatname.setText(rs.getString(2));
        }
        stmt.close();
        con.close();
    }
    public void updateCategory() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        int r = stmt.executeUpdate("UPDATE categories SET name='"+tfrescatname.getText()+"' WHERE category_id="+Integer.parseInt(tfrescatid.getText()));
        JOptionPane.showMessageDialog(f,"Category updated Sucessfully!");
        loadCategoryTable();
        stmt.close();
        con.close();
    }
    public void deleteCategory() throws Exception{
        int dialogButton = JOptionPane.YES_NO_OPTION;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String category_name;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT name FROM categories WHERE category_id="+Integer.parseInt(tfdelcat.getText()));

        if(rs.next()==false){
            JOptionPane.showMessageDialog(f,"No such category exists!");
        }else{
            category_name = rs.getString("name");
            int dialogResult = JOptionPane.showConfirmDialog(f,"Are you sure you want to delete "+category_name+" ?","Confirmation",dialogButton);
            if (dialogResult == 0){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
                Statement stmt = con.createStatement();
                int r = stmt.executeUpdate("DELETE FROM categories where category_id="+Integer.parseInt(tfdelcat.getText()));
                JOptionPane.showMessageDialog(f,"Category deleted Sucessfully!");
                loadCategoryTable();
                tfdelcat.setText("");
                stmt.close();
                con.close();
            }
        }
    }

    //Manage Product
    public void manageProduct() throws Exception{
        JLabel lblproid = new JLabel("Product ID:");
        lblproid.setBounds(5,10,90,20);
        pancontent.add(lblproid);
        tfproid = new JTextField();
        tfproid.setBounds(100,10,150,20);
        pancontent.add(tfproid);
        JLabel lblproname = new JLabel("Product Name:");
        lblproname.setBounds(5,30,90,20);
        pancontent.add(lblproname);
        tfproname = new JTextField();
        tfproname.setBounds(100,30,150,20);
        pancontent.add(tfproname);
        JLabel lblquan = new JLabel("Quantity:");
        lblquan.setBounds(5,50,90,20);
        pancontent.add(lblquan);
        tfproquantity = new JTextField();
        tfproquantity.setBounds(100,50,150,20);
        pancontent.add(tfproquantity);
        JLabel lblprice = new JLabel("Price:");
        lblprice.setBounds(5,70,90,20);
        pancontent.add(lblprice);
        tfproprice = new JTextField();
        tfproprice.setBounds(100,70,150,20);
        pancontent.add(tfproprice);
        JLabel lblprodes = new JLabel("Description:");
        lblprodes.setBounds(5,90,90,20);
        pancontent.add(lblprodes);
        tfprodes = new JTextField();
        tfprodes.setBounds(100,90,150,20);
        pancontent.add(tfprodes);
        JLabel lblcatname = new JLabel("Category:");
        lblcatname.setBounds(5,110,90,20);
        pancontent.add(lblcatname);
        cbxcatname = new JComboBox();
        cbxcatname.removeAll();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT name from categories");
        while (rs.next()){
            cbxcatname.addItem(rs.getString("name"));
        }
        cbxcatname.setBounds(100,110,150,20);
        pancontent.add(cbxcatname);
        btnaddpro = new JButton("Add");
        btnaddpro.setBounds(150,130,100,20);
        pancontent.add(btnaddpro);
        btnaddpro.addActionListener(this);

        //Edit Product
        JLabel lblproedit = new JLabel("Enter Product id to update:");
        lblproedit.setBounds(270,10,200,20);
        pancontent.add(lblproedit);
        tfproide = new JTextField();
        tfproide.setBounds(270,30,100,20);
        pancontent.add(tfproide);
        btneditpro = new JButton("Edit");
        btneditpro.setBounds(380,30,80,20);
        pancontent.add(btneditpro);
        btneditpro.addActionListener(this);
        JLabel lblproide = new JLabel("ID:");
        lblproide.setBounds(270,60,80,20);
        pancontent.add(lblproide);
        tfproidee = new JTextField();
        tfproidee.setBounds(350,60,100,20);
        pancontent.add(tfproidee);
        JLabel lblpronamee = new JLabel("Name:");
        lblpronamee.setBounds(270,80,80,20);
        pancontent.add(lblpronamee);
        tfpronamee = new JTextField();
        tfpronamee.setBounds(350,80,100,20);
        pancontent.add(tfpronamee);
        JLabel lblquane = new JLabel("Quantity:");
        lblquane.setBounds(270,100,80,20);
        pancontent.add(lblquane);
        tfproquantitye = new JTextField();
        tfproquantitye.setBounds(350,100,100,20);
        pancontent.add(tfproquantitye);
        JLabel lblpricee = new JLabel("Price:");
        lblpricee.setBounds(270,120,80,20);
        pancontent.add(lblpricee);
        tfpropricee = new JTextField();
        tfpropricee.setBounds(350,120,100,20);
        pancontent.add(tfpropricee);
        JLabel lblprodese = new JLabel("Description:");
        lblprodese.setBounds(270,140,80,20);
        pancontent.add(lblprodese);
        tfprodese = new JTextField();
        tfprodese.setBounds(350,140,100,20);
        pancontent.add(tfprodese);
        JLabel lblcatnamee = new JLabel("Category:");
        lblcatnamee.setBounds(270,160,80,20);
        pancontent.add(lblcatnamee);
        cbxcatnamee = new JComboBox();
        cbxcatnamee.removeAll();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con1 = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt1 = con.createStatement();
        ResultSet rs1 = stmt.executeQuery("SELECT name from categories");
        while (rs1.next()){
            cbxcatnamee.addItem(rs1.getString("name"));
        }
        stmt1.close();
        con1.close();
        cbxcatnamee.setBounds(350,160,100,20);
        pancontent.add(cbxcatnamee);
        btnupdatepro = new JButton("Update");
        btnupdatepro.setBounds(370,180,80,20);
        pancontent.add(btnupdatepro);
        btnupdatepro.addActionListener(this);

        //Delete Product
        JLabel lbldelpro = new JLabel("Enter Product id to delete");
        lbldelpro.setBounds(5,160,150,20);
        pancontent.add(lbldelpro);
        tfdelpro = new JTextField();
        tfdelpro.setBounds(5,180,100,20);
        pancontent.add(tfdelpro);
        btndelpro = new JButton("Delete");
        btndelpro.setBounds(110,180,80,20);
        pancontent.add(btndelpro);
        btndelpro.addActionListener(this);
        displayProductTable();
    }
    public void displayProductTable() throws Exception{
        userTable = new JTable();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM product");
        ResultSetMetaData rsmd = rs.getMetaData();
        DefaultTableModel model = (DefaultTableModel) userTable.getModel();
        int cols = rsmd.getColumnCount();
        String[] colName = new String[cols];
        for (int i=0;i<cols;i++)
            colName[i] = rsmd.getColumnName(i+1);
        model.setColumnIdentifiers(colName);
        while (rs.next()){
            String[] row = {String.valueOf(rs.getInt(1)),rs.getString(2),String.valueOf(rs.getString(3)),String.valueOf(rs.getString(4)),rs.getString(5),rs.getString(6)};
            model.addRow(row);
        }
        userTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //userTable.setEnabled(false);
        pane = new JScrollPane(userTable);
        pancontent.add(pane);
        pane.setBounds(10,230,450,200);
        stmt.close();
        con.close();
    }
    public void insertProduct() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        PreparedStatement ps = con.prepareStatement("INSERT INTO product VALUES (?,?,?,?,?,?)");
        ps.setInt(1,Integer.parseInt(tfproid.getText()));
        ps.setString(2,tfproname.getText());
        ps.setInt(3,Integer.parseInt(tfproquantity.getText()));
        ps.setInt(4,Integer.parseInt(tfproprice.getText()));
        ps.setString(5,tfprodes.getText());
        ps.setString(6, (String) cbxcatname.getSelectedItem());
        int r = ps.executeUpdate();
        JOptionPane.showMessageDialog(f,"New Product Added Sucessfully!");
        displayProductTable();
        insertStock();
        tfproid.setText("");
        tfproname.setText("");
        tfproquantity.setText("");
        tfproprice.setText("");
        tfprodes.setText("");
        cbxcatname.removeAll();
        ps.close();
        con.close();
    }
    public void insertStock() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        PreparedStatement ps = con.prepareStatement("INSERT INTO stock VALUES (null,?,?,null,?)");
        ps.setString(1,tfproname.getText());
        ps.setInt(2,Integer.parseInt(tfproquantity.getText()));
        ps.setInt(3,Integer.parseInt(tfproquantity.getText()));
        int r = ps.executeUpdate();
        ps.close();
        con.close();
    }
    public void searchProduct() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM product WHERE product_id="+Integer.parseInt(tfproide.getText()));
        if (rs.next()==false){
            JOptionPane.showMessageDialog(f,"No Search Result found!");
        }else {
            tfproide.setEditable(false);
            tfproidee.setText(String.valueOf(rs.getInt(1)));
            tfpronamee.setText(rs.getString(2));
            tfproquantitye.setText(String.valueOf(rs.getInt(3)));
            tfpropricee.setText(String.valueOf(rs.getInt(4)));
            tfprodese.setText(rs.getString(5));
            cbxcatnamee.setSelectedItem(rs.getString(6));
        }
        stmt.close();
        con.close();
    }
    public void updateProduct() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        int r = stmt.executeUpdate("UPDATE product SET product_id="+Integer.parseInt(tfproidee.getText())+",product_name='"+tfpronamee.getText()+"',quantity="+Integer.parseInt(tfproquantitye.getText())+",price="+Integer.parseInt(tfpropricee.getText())+",description='"+tfprodese.getText()+"',category_name='"+cbxcatnamee.getSelectedItem()+"' WHERE product_id="+Integer.parseInt(tfproide.getText()));
        JOptionPane.showMessageDialog(f,"Product updated Sucessfully!");
        displayProductTable();
        updateStock();
        tfproide.setEditable(true);
        tfproidee.setText("");
        tfpronamee.setText("");
        tfproquantitye.setText("");
        tfpropricee.setText("");
        tfprodese.setText("");
        cbxcatnamee.removeAll();
        tfproide.setText("");
        stmt.close();
        con.close();
    }
    public void updateStock() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt1 = con.createStatement();
        ResultSet rs = stmt1.executeQuery("SELECT sold_quantity FROM stock WHERE name='"+tfpronamee.getText()+"'");
        rs.next();
        int soldQuantity = rs.getInt(1);
        stmt1.close();
        Statement stmt = con.createStatement();
        int r = stmt.executeUpdate("UPDATE stock SET name='"+tfpronamee.getText()+"',purchased_quantity="+Integer.parseInt(tfproquantitye.getText())+",remaining_quantity="+(Integer.parseInt(tfproquantitye.getText())-soldQuantity)+" WHERE name='"+tfpronamee.getText()+"'");
        stmt.close();
        con.close();
    }
    public void deleteProduct() throws Exception{
        int dialogButton = JOptionPane.YES_NO_OPTION;
        Class.forName("com.mysql.cj.jdbc.Driver");
        String product_name;
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT product_name FROM product WHERE product_id="+Integer.parseInt(tfdelpro.getText()));
        if(rs.next()==false){
            JOptionPane.showMessageDialog(f,"No such product exists!");
        }else{
            product_name = rs.getString("product_name");
            int dialogResult = JOptionPane.showConfirmDialog(f,"Are you sure you want to delete "+product_name+" product?","Confirmation",dialogButton);
            if (dialogResult == 0){
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
                Statement stmt = con.createStatement();
                int r = stmt.executeUpdate("DELETE FROM product where product_id="+Integer.parseInt(tfdelpro.getText()));
                JOptionPane.showMessageDialog(f,"Category deleted Sucessfully!");
                displayProductTable();
                tfdelpro.setText("");
                stmt.close();
                con.close();
            }
        }
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnDashboard){
            pancontent.removeAll();
            try {
                dashboardContent();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            pancontent.revalidate();
            pancontent.repaint();
        }else if (e.getSource()==btnLogout){
            f.dispose();
            new Homepage();
        } else if (e.getSource()==btnManageUser) {
            pancontent.removeAll();
            try {
                manageUserContent();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            pancontent.revalidate();
            pancontent.repaint();
        } else if (e.getSource()==btnManageCategory) {
            pancontent.removeAll();
            try {
                manageCategory();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            pancontent.revalidate();
            pancontent.repaint();
        } else if (e.getSource()==btnaddcat) {
            try {
                insertCategory(tfcatid.getText(),tfcatname.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnsearchcat) {
            try {
                searchCategory();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnupdatecat) {
            if(btnupdatecat.getText()=="Update"){
                try {
                    updateCategory();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        } else if (e.getSource()==btndelcat) {
            try {
                deleteCategory();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnManageProduct) {
            pancontent.removeAll();
            try {
                manageProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
            pancontent.revalidate();
            pancontent.repaint();
        } else if (e.getSource()==btnaddpro) {
            try {
                insertProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btneditpro) {
            try {
                searchProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnupdatepro) {
            try {
                updateProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btndelpro) {
            try {
                deleteProduct();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
}
