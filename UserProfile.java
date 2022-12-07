import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserProfile implements ActionListener {
    JFrame f;
    JLabel lblname,lbladd,lbltelno,lblemail,lblage,lblsex,lbluname,lblpass,lblcpass;
    JTextField tfname,tfadd,tftelno,tfemail,tfage,tfuname;
    JPasswordField pfpass,pfcpass;
    JRadioButton rbtnmale,rbtnfemale;
    JButton btnedit,btnclose;
    ButtonGroup btnggen;
    String username,gender;
    public UserProfile(String user) throws Exception {
        f = new JFrame("IMS: User Profile");
        f.setSize(400,400);
        lblname = new JLabel("Name:");
        lblname.setBounds(20,20,100,20);
        f.add(lblname);
        tfname = new JTextField();
        tfname.setBounds(120,20,200,25);
        f.add(tfname);
        lbladd = new JLabel("Address:");
        lbladd.setBounds(20,50,100,20);
        f.add(lbladd);
        tfadd = new JTextField();
        tfadd.setBounds(120,50,200,25);
        f.add(tfadd);
        lbltelno = new JLabel("Telephone No.:");
        lbltelno.setBounds(20,80,100,20);
        f.add(lbltelno);
        tftelno = new JTextField();
        tftelno.setBounds(120,80,200,25);
        f.add(tftelno);
        lblemail = new JLabel("E-mail:");
        lblemail.setBounds(20,110,100,20);
        f.add(lblemail);
        tfemail = new JTextField();
        tfemail.setBounds(120,110,200,25);
        f.add(tfemail);
        lblage = new JLabel("Age:");
        lblage.setBounds(20,140,100,20);
        f.add(lblage);
        tfage = new JTextField();
        tfage.setBounds(120,140,150,25);
        f.add(tfage);
        lblsex = new JLabel("Sex:");
        lblsex.setBounds(20,170,100,20);
        f.add(lblsex);
        rbtnmale = new JRadioButton("Male");
        rbtnmale.setBounds(120,170,80,25);
        f.add(rbtnmale);
        rbtnfemale = new JRadioButton("Female");
        rbtnfemale.setBounds(200,170,80,25);
        f.add(rbtnfemale);
        btnggen = new ButtonGroup();
        btnggen.add(rbtnmale);
        btnggen.add(rbtnfemale);
        lbluname = new JLabel("Username:");
        lbluname.setBounds(20,200,100,20);
        f.add(lbluname);
        tfuname = new JTextField();
        tfuname.setBounds(120,200,200,25);
        f.add(tfuname);
        lblpass = new JLabel("Password:");
        lblpass.setBounds(20,230,100,20);
        f.add(lblpass);
        pfpass = new JPasswordField();
        pfpass.setBounds(120,230,200,25);
        f.add(pfpass);
        lblcpass = new JLabel("Re-Password:");
        lblcpass.setBounds(20,260,100,20);
        f.add(lblcpass);
        pfcpass = new JPasswordField();
        pfcpass.setBounds(120,260,200,25);
        f.add(pfcpass);
        btnedit = new JButton("Edit");
        btnedit.setBounds(120,300,100,25);
        f.add(btnedit);
        btnedit.addActionListener(this);
        btnclose = new JButton("Close");
        btnclose.setBounds(240,300,80,25);
        f.add(btnclose);
        btnclose.addActionListener(this);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        makeEditable(false);
        fetchData(user);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==btnedit){
            if (btnedit.getText().equals("Edit")){
                makeEditable(true);
                btnedit.setText("Update");
            }else{
                makeEditable(false);
                btnedit.setText("Edit");
                try {
                    if(validateEntry()==true){
                        updateData();
                    }else{
                        makeEditable(true);
                        btnedit.setText("Update");
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                } catch (ClassNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
        }else{
            f.dispose();
            try {
                new UserDashboard(tfuname.getText());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void makeEditable(Boolean arg){
        tfname.setEditable(arg);
        tfadd.setEditable(arg);
        tftelno.setEditable(arg);
        tfemail.setEditable(arg);
        tfage.setEditable(arg);
        //rbtnmale.setDisabledIcon();
        tfuname.setEditable(arg);
        pfpass.setEditable(arg);
        pfcpass.setEditable(arg);
    }
    public void fetchData(String user) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users where username='"+user+"'");
        while (rs.next()){
            tfname.setText(rs.getString("name"));
            tfadd.setText(rs.getString("address"));
            tftelno.setText(rs.getString("telephone_no"));
            tfemail.setText(rs.getString("email"));
            tfage.setText(String.valueOf(rs.getInt("age")));
            if(rs.getString("gender").equals("Male")){
                rbtnmale.setSelected(true);
            }else{
                rbtnfemale.setSelected(true);
            }
            tfuname.setText(rs.getString("username"));
            pfpass.setText(rs.getString("password"));
            pfcpass.setText(rs.getString("password"));
        }
        username = tfuname.getText();
        stmt.close();
        con.close();
    }
    public void updateData() throws Exception{
        if(rbtnmale.isSelected()==true){
            gender="Male";
        }else{
            gender="Female";
        }
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        String query = "UPDATE users SET name='"+tfname.getText()+"',Address='"+tfadd.getText()+"',telephone_no="+tftelno.getText()+",email='"+tfemail.getText()+"',age="+Integer.parseInt(tfage.getText())+",gender='"+gender+"',username='"+tfuname.getText()+"',password='"+new String(pfpass.getPassword())+"' WHERE username='"+username+"'";
        Statement stmt = con.createStatement();
        int r= stmt.executeUpdate(query);
        if(r>0){
            JOptionPane.showMessageDialog(f,"Your Profile has been updated!");
        }
        stmt.close();
        con.close();
    }
    public boolean validateEntry() throws SQLException, ClassNotFoundException {
        Pattern phonePattern = Pattern.compile("^\\d{10}$");
        Matcher phoneMatches = phonePattern.matcher(tftelno.getText());
        Pattern agePattern = Pattern.compile("^(?:1[8-9]|[2-9][0-9]|100)$");
        Matcher ageMatches = agePattern.matcher(tfage.getText());
        Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher emailMatches = emailPattern.matcher(tfemail.getText());
        if (tfname.getText().equals("")){
            JOptionPane.showMessageDialog(f,"Please Enter Your Name!");
            return false;
        } else if (tfadd.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Address!");
            return false;
        } else if (tftelno.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Phone Number!");
            return false;
        } else if (tfemail.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your E-mail Address!");
            return false;
        } else if (tfage.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Age!");
            return false;
        } else if ((!rbtnmale.isSelected()) && (!rbtnfemale.isSelected())) {
            JOptionPane.showMessageDialog(f, "Please Select Your Appropriate Gender!");
            return false;
        }else if (tfuname.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Username!");
            return false;
        }else if (pfpass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(f,"Please Enter Password!");
            return false;
        }else if (pfcpass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(f,"Please Re-Enter Password!");
            return false;
        } else if (!((new String(pfpass.getPassword())).equals(new String(pfcpass.getPassword())))) {
            JOptionPane.showMessageDialog(f,"Password doesnot Match!");
            return false;
        } else if (!phoneMatches.matches()) {
            JOptionPane.showMessageDialog(f,"Phone number must only be Integer of 10 Character long");
            return false;
        }else if (!ageMatches.matches()) {
            JOptionPane.showMessageDialog(f,"Age must lie between 18 to 100");
            return false;
        }else if(!emailMatches.matches()){
            JOptionPane.showMessageDialog(f,"Please enter valid email address!");
            return false;
        }else{
            return true;
        }
    }
}
