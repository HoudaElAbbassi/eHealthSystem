package user.Patient;

import Connection.DBConnection;
import Exceptions.EmailException;
import Exceptions.PasswordException;
import Security.EmailVerification;
import Security.PasswordManager;
import user.UserDAO;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

    /** 
    *This class implements the methods of the Patient class
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    
    
public class PatientDAOImp implements UserDAO<Patient> {
    /** 
    *This is a method that recieves an Patient object 
    *and it saves the properties  of the patient into the database
    *after checking the validity of 
    *the password and email.
    *@param patient of the type Patient
    *@throws PasswordException
    *@throws  EmailException
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    
    @Override
    public boolean save(Patient patient) throws PasswordException, EmailException {
        try {

            PasswordManager.passwordVerification(patient.getPassword());
            EmailVerification.verifyEmail(patient.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "INSERT INTO patients(userName, email, password, firstName, lastName, address, birthDate, insuranceType, insuranceName ) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patient.getUserName());
            ps.setString(2, patient.getEmail());
            ps.setString(3, patient.getPassword());
            ps.setString(4, patient.getFirstName());
            ps.setString(5, patient.getLastName());
            ps.setString(6, patient.getAddress());
            ps.setDate(7, Date.valueOf(patient.getBirthDate()));
            ps.setString(8, patient.getInsuranceType().toString());
            ps.setString(9, patient.getInsuranceName());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully registered! Now you can login.");
            return true;
        } catch (PasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (EmailException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (SQLIntegrityConstraintViolationException e) {
            JOptionPane.showMessageDialog(null, "User already exists! Please Login");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return false;
    }

    @Override
    public void edit(Patient patient) {
        try {
            ///PasswordManager.passwordVerification(patient.getPassword());
            EmailVerification.verifyEmail(patient.getEmail());
            Connection con = DBConnection.getConnection();
            String sql = "Update patients set  userName=?, email=?, password=?, firstName=?, lastName=?, address=?, birthDate=?, insuranceType=?, insuranceName=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patient.getUserName());
            ps.setString(2, patient.getEmail());
            ps.setString(3, PasswordManager.encode(patient.getPassword()));
            ps.setString(3, patient.getPassword());
            ps.setString(4, patient.getFirstName());
            ps.setString(5, patient.getLastName());
            ps.setString(6, patient.getAddress());
            ps.setDate(7, Date.valueOf(patient.getBirthDate()));
            ps.setString(8, patient.getInsuranceType().toString());
            ps.setString(9, patient.getInsuranceName());
            ps.setLong(10, patient.getId());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
       /* } catch (PasswordException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());*/
        } catch (EmailException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void editByAdmin(Patient patient) {
        try {

            EmailVerification.verifyEmail(patient.getEmail());
            Connection con = DBConnection.getConnection();
            String sql = "Update patients set  userName=?, email=?, firstName=?, lastName=?, address=?, birthDate=?, insuranceType=?, insuranceName=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, patient.getUserName());
            ps.setString(2, patient.getEmail());
            ps.setString(3, patient.getFirstName());
            ps.setString(4, patient.getLastName());
            ps.setString(5, patient.getAddress());
            ps.setDate(6, Date.valueOf(patient.getBirthDate()));
            ps.setString(7, patient.getInsuranceType().toString());
            ps.setString(8, patient.getInsuranceName());
            ps.setLong(9, patient.getId());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Updated!");
        } catch (EmailException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void delete(Patient patient) {
        try {

            Connection con = DBConnection.getConnection();
            String sql = "delete from patients  WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, patient.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    public void deleteByID(long id) {
        try {

            Connection con = DBConnection.getConnection();
            String sql = "delete from patients  WHERE id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setLong(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Deleted!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public boolean existEmail(String userEmail) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select email from patients WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return false;
    }

    @Override
    public Patient getByEmail(String userEmail) {
        Patient patient = new Patient();
        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT id, userName, email, password, firstName, lastName, address, birthDate, insuranceType, insuranceName from patients where email='"+userEmail+"'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            if(rs.next()){
                patient.setId(rs.getLong("id"));
                patient.setUserName(rs.getString("userName"));
                patient.setAddress(rs.getString("address"));
                patient.setBirthDate(rs.getDate("birthDate").toLocalDate());
                patient.setEmail(rs.getString("email"));
                patient.setFirstName(rs.getString("firstName"));
                patient.setLastName(rs.getString("lastName"));
                patient.setPassword(rs.getString("password"));
                patient.setInsuranceType(InsuranceType.valueOf(rs.getString("insuranceType")));
                patient.setInsuranceName(rs.getString("insuranceName"));
                //patient.setEmail(email);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return patient;
    }

    @Override
    public Patient getByID(long id) {
        Patient patient=new Patient();
        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT userName, email, password, firstName, lastName, address, birthDate from patients where id="+id;
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                patient.setAddress(rs.getString("address"));
                patient.setBirthDate(rs.getDate("birthDate").toLocalDate());
                patient.setEmail(rs.getString("email"));
                patient.setFirstName(rs.getString("firstName"));
                patient.setLastName(rs.getString("lastName"));
                patient.setPassword(rs.getString("password"));
                patient.setId(id);

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return patient;
    }

    @Override
    public List<Patient> getAll() {
        List<Patient> list = new ArrayList<Patient>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM patients ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Patient patient = new Patient();
                patient.setUserName(rs.getString("userName"));
                patient.setAddress(rs.getString("address"));
                patient.setBirthDate(rs.getDate("birthDate").toLocalDate());
                patient.setEmail(rs.getString("email"));
                patient.setFirstName(rs.getString("firstName"));
                patient.setLastName(rs.getString("lastName"));
                patient.setPassword(rs.getString("password"));
                patient.setInsuranceName(rs.getString("insuranceName"));
                patient.setInsuranceType(InsuranceType.valueOf(rs.getString("insuranceType")));
                patient.setId(rs.getInt("ID"));
                list.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public String getPassword(String userEmail) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select password from patients WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("password");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "User doesn't exist");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return null;
    }

    @Override
    public String getFirstNameByID(long id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select firstName from patients WHERE id=" + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("firstName");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "User doesn't exist");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

    @Override
    public String getLastNameByID(long id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select lastName from patients WHERE id=" + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("lastName");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "User doesn't exist");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

    @Override
    public String getEmailById(long id) {
        try {
            Connection con = DBConnection.getConnection();
            String sql = "select email from patients WHERE id=" + id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString("email");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "User doesn't exist");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

}

