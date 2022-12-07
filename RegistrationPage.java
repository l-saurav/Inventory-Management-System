import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.regex.*;

public class RegistrationPage implements ActionListener{
    JFrame f;
    JLabel lblname,lbladd,lbltelno,lblemail,lblage,lblsex,lbluname,lblpass,lblcpass;
    JTextField tfname,tfadd,tftelno,tfemail,tfage,tfuname;
    JPasswordField pfpass,pfcpass;
    JRadioButton rbtnmale,rbtnfemale;
    JButton btnreg,btnsignin;
    ButtonGroup btnggen;
    public RegistrationPage(){
        f = new JFrame("IMS: Registration Form");
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
        btnreg = new JButton("Register");
        btnreg.setBounds(120,300,100,25);
        f.add(btnreg);
        btnsignin = new JButton("Sign in");
        btnsignin.setBounds(240,300,80,25);
        btnreg.addActionListener(this);
        btnsignin.addActionListener(this);
        f.add(btnsignin);
        f.setLayout(null);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==btnsignin){
            f.setVisible(false);
            new Homepage();
        }
        if(e.getSource()==btnreg){
            try {
                validateEntry();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void validateEntry() throws SQLException, ClassNotFoundException {
        Pattern phonePattern = Pattern.compile("^\\d{10}$");
        Matcher phoneMatches = phonePattern.matcher(tftelno.getText());
        Pattern agePattern = Pattern.compile("^(?:1[8-9]|[2-9][0-9]|100)$");
        Matcher ageMatches = agePattern.matcher(tfage.getText());
        Pattern emailPattern = Pattern.compile("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher emailMatches = emailPattern.matcher(tfemail.getText());
        if (tfname.getText().equals("")){
            JOptionPane.showMessageDialog(f,"Please Enter Your Name!");
        } else if (tfadd.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Address!");
        } else if (tftelno.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Phone Number!");
        } else if (tfemail.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your E-mail Address!");
        } else if (tfage.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Your Age!");
        } else if ((!rbtnmale.isSelected()) && (!rbtnfemale.isSelected())) {
            JOptionPane.showMessageDialog(f, "Please Select Your Appropriate Gender!");
        }else if (tfuname.getText().equals("")) {
            JOptionPane.showMessageDialog(f,"Please Enter Username!");
        }else if (pfpass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(f,"Please Enter Password!");
        }else if (pfcpass.getPassword().length == 0) {
            JOptionPane.showMessageDialog(f,"Please Re-Enter Password!");
        } else if (!((new String(pfpass.getPassword())).equals(new String(pfcpass.getPassword())))) {
            JOptionPane.showMessageDialog(f,"Password doesnot Match!");
        } else if (!phoneMatches.matches()) {
            JOptionPane.showMessageDialog(f,"Phone number must only be Integer of 10 Character long");
        }else if (!ageMatches.matches()) {
            JOptionPane.showMessageDialog(f,"Age must lie between 18 to 100");
        }else if(!emailMatches.matches()){
            JOptionPane.showMessageDialog(f,"Please enter valid email address!");
        }else{
            insertData();
        }
    }
    public void insertData() throws ClassNotFoundException, SQLException {
        String gender;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        PreparedStatement ps = con.prepareStatement("INSERT INTO users VALUES (?,?,?,?,?,?,?,?)");
        ps.setString(1,tfname.getText());
        ps.setString(2,tfadd.getText());
        ps.setString(3,tftelno.getText());
        ps.setString(4,tfemail.getText());
        ps.setInt(5,Integer.parseInt(tfage.getText()));
        if (rbtnmale.isSelected()){
            gender = "Male";
        }else{
            gender = "Female";
        }
        ps.setString(6,gender);
        ps.setString(7,tfuname.getText());
        ps.setString(8,new String(pfpass.getPassword()));
        int r = ps.executeUpdate();
        JOptionPane.showMessageDialog(f,"You have been Sucessfully Registered!");
        f.dispose();
        new Homepage();
        con.close();
    }
}
