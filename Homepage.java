import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Homepage implements ActionListener{
    JFrame frame;
    JLabel lbllogin,lbluname,lblpass,lblsignup;
    JTextField tfunmae;
    JPasswordField pfpass;
    JButton btnlogin,btnsignup;
    public Homepage(){
        frame = new JFrame("Inventory Management System");
        frame.setSize(300,300);
        lbllogin = new JLabel("Please Login to Continue");
        lbluname = new JLabel("Enter Username:");
        lblpass = new JLabel("Enter Password:");
        tfunmae = new JTextField();
        pfpass = new JPasswordField();
        btnlogin = new JButton("Log in");
        lblsignup = new JLabel("If you are new here, Please Register!");
        btnsignup = new JButton("Register Now!");
        lbllogin.setBounds(10,50,150,20);
        frame.add(lbllogin);
        lbluname.setBounds(10,80,100,20);
        frame.add(lbluname);
        tfunmae.setBounds(120,80,150,25);
        frame.add(tfunmae);
        lblpass.setBounds(10,110,100,20);
        frame.add(lblpass);
        pfpass.setBounds(120,110,150,25);
        frame.add(pfpass);
        btnlogin.setBounds(200,140,70,20);
        frame.add(btnlogin);
        btnlogin.addActionListener(this);
        lblsignup.setBounds(10,200,300,20);
        frame.add(lblsignup);
        btnsignup.setBounds(80,230,120,20);
        btnsignup.addActionListener(this);
        frame.add(btnsignup);
        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource() == btnlogin){
            String password = new String(pfpass.getPassword());
            if(tfunmae.getText().equals("admin") && password.equals("admin")){
                try {
                    frame.dispose();
                    new AdminDashboard();
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }else {
                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
                    Statement stmt = con.createStatement();
                    String q = "SELECT username,password FROM users WHERE username='" + tfunmae.getText() +"' and password='"+ new String(pfpass.getPassword())+"'";
                    ResultSet rs = stmt.executeQuery(q);
                    if(rs.next()){
                        JOptionPane.showMessageDialog(frame,"Login Sucessful");
                        con.close();
                        new UserDashboard(tfunmae.getText());
                        frame.dispose();
                    }else{
                        JOptionPane.showMessageDialog(frame,"Sorry! The credential does not match");
                        con.close();
                    }
                }catch (Exception exp){
                    System.out.println("Exception Occured!");
                }
            }
        }
        if (e.getSource() == btnsignup){
            frame.dispose();
            new RegistrationPage();
        }
    }
    public static void main(String[] args) throws Exception {
        new Homepage();
    }
}
