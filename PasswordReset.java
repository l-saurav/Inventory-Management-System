import javax.swing.*;
import java.awt.event.*;
import java.sql.*;

public class PasswordReset implements ActionListener, MouseListener {
    JFrame frame;
    JTextField tfusername,tftelno,tfemail;
    JLabel lblNewPassword,lblReNewPassword,lblRememberPassword;
    JPasswordField pfnewpassword,pfrenewpassword;
    JButton btnCheckDetail,btnResetPassword;
    public PasswordReset(){
        frame = new JFrame("Password Reset");
        frame.setSize(300,300);
        JLabel lblpasswordreset = new JLabel("Please enter the following data correctly!");
        lblpasswordreset.setBounds(5,10,300,20);
        frame.add(lblpasswordreset);
        JLabel lblUsername = new JLabel("Enter Username:");
        lblUsername.setBounds(5,50,100,20);
        frame.add(lblUsername);
        tfusername = new JTextField();
        tfusername.setBounds(110,50,150,20);
        frame.add(tfusername);
        JLabel lblUserPhone = new JLabel("Telephone No.:");
        lblUserPhone.setBounds(5,75,100,20);
        frame.add(lblUserPhone);
        tftelno = new JTextField();
        tftelno.setBounds(110,75,150,20);
        frame.add(tftelno);
        JLabel lblUserEmail = new JLabel("E-Mail Address:");
        lblUserEmail.setBounds(5,100,100,20);
        frame.add(lblUserEmail);
        tfemail = new JTextField();
        tfemail.setBounds(110,100,150,20);
        frame.add(tfemail);
        lblRememberPassword = new JLabel("I Remember my Password");
        lblRememberPassword.setBounds(5,130,150,20);
        frame.add(lblRememberPassword);
        lblRememberPassword.addMouseListener(this);
        btnCheckDetail = new JButton("Reset");
        btnCheckDetail.setBounds(170,130,100,20);
        frame.add(btnCheckDetail);
        btnCheckDetail.addActionListener(this);

        lblNewPassword = new JLabel("New Password:");
        lblNewPassword.setBounds(5,160,100,20);
        frame.add(lblNewPassword);
        lblNewPassword.setVisible(false);
        pfnewpassword = new JPasswordField();
        pfnewpassword.setBounds(130,160,130,20);
        frame.add(pfnewpassword);
        pfnewpassword.setVisible(false);
        lblReNewPassword = new JLabel("Re-Enter Password:");
        lblReNewPassword.setBounds(5,185,120,20);
        frame.add(lblReNewPassword);
        lblReNewPassword.setVisible(false);
        pfrenewpassword = new JPasswordField();
        pfrenewpassword.setBounds(130,185,130,20);
        frame.add(pfrenewpassword);
        pfrenewpassword.setVisible(false);
        btnResetPassword = new JButton("Reset");
        btnResetPassword.setBounds(150,210,100,20);
        frame.add(btnResetPassword);
        btnResetPassword.setVisible(false);
        btnResetPassword.addActionListener(this);

        frame.setLayout(null);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    public void checkDetail() throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
        Statement stmt = con.createStatement();
        String q = "SELECT username FROM users WHERE username='" + tfusername.getText() +"' and telephone_no="+Long.parseLong(tftelno.getText()) +" and email='"+tfemail.getText()+"'";
        ResultSet rs = stmt.executeQuery(q);
        if(rs.next()){
            showResetOption(true);
            con.close();
        }else{
            JOptionPane.showMessageDialog(frame,"Sorry! There is no such record");
            con.close();
        }
    }
    public void showResetOption(boolean args){
        tfusername.setEnabled(false);
        tftelno.setEnabled(false);
        tfemail.setEnabled(false);
        btnCheckDetail.setEnabled(false);
        lblNewPassword.setVisible(args);
        pfnewpassword.setVisible(args);
        lblReNewPassword.setVisible(args);
        pfrenewpassword.setVisible(args);
        btnResetPassword.setVisible(args);
    }
    public void resetPassword() throws SQLException, ClassNotFoundException {
        if (pfnewpassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(frame, "Please Enter Password!");
        } else if (pfrenewpassword.getPassword().length == 0) {
            JOptionPane.showMessageDialog(frame, "Please Re-Enter Password!");
        } else if (!((new String(pfnewpassword.getPassword())).equals(new String(pfrenewpassword.getPassword())))) {
            JOptionPane.showMessageDialog(frame, "Password doesnot Match!");
        }else {
            //Reset Password Code
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ims","root","");
            String query = "UPDATE users SET password='"+new String(pfnewpassword.getPassword())+"' WHERE username='"+tfusername.getText()+"'";
            Statement stmt = con.createStatement();
            int r= stmt.executeUpdate(query);
            if(r>0){
                JOptionPane.showMessageDialog(frame,"Password Successfully Updated!");
                frame.dispose();
                new Homepage();
            }
            stmt.close();
            con.close();
        }
    }
    public void actionPerformed(ActionEvent e){
        if (e.getSource()==btnCheckDetail){
            try {
                checkDetail();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getSource()==btnResetPassword) {
            try {
                resetPassword();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            } catch (ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        }
    }
    public void mouseClicked(MouseEvent e){
        frame.dispose();
        try {
            new Homepage();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
    public void mouseExited(MouseEvent e){}
    public void mouseEntered(MouseEvent e){}
    public void mouseReleased(MouseEvent e){}
    public void mousePressed(MouseEvent e){}
}
