package user.Doctor;
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
 * This class Implements the Methods of the Interface UserDAO for the Doctor
 */
public class DoctorDAOImp implements UserDAO<Doctor> {

    @Override
    public boolean save(Doctor doctor) throws PasswordException, EmailException {

        try {

            PasswordManager.passwordVerification(doctor.getPassword());
            EmailVerification.verifyEmail(doctor.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "INSERT INTO doctors(userName, email, password, firstName, lastName, address, birthDate,specialization ) VALUES (?,?,?,?,?,?,?,?)";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, doctor.getUserName());
            ps.setString(2, doctor.getEmail());
            ps.setString(3, PasswordManager.encode(doctor.getPassword()));
            ps.setString(4,doctor.getFirstName());
            ps.setString(5,doctor.getLastName());
            ps.setString(6,doctor.getAddress());
            ps.setDate(7, Date.valueOf(doctor.getBirthDate()));
            ps.setString(8,doctor.getSpecialization().toString());

            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "You have successfully registered! Now you can login.");
            return true;
        }
        catch(PasswordException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (EmailException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        catch (SQLIntegrityConstraintViolationException e){
            JOptionPane.showMessageDialog(null, "User already exists! Please Login");

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return false;
    }







    @Override
    public void edit(Doctor doctor) {
        try{
            PasswordManager.passwordVerification(doctor.getPassword());
            EmailVerification.verifyEmail(doctor.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "Update doctors set  userName=?, email=?, password=?, firstName=?, lastName=?, address=?, birthDate=?,specialization=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, doctor.getUserName());
            ps.setString(2, doctor.getEmail());
            ps.setString(3, doctor.getPassword());
            ps.setString(4,doctor.getFirstName());
            ps.setString(5,doctor.getLastName());
            ps.setString(6,doctor.getAddress());
            ps.setDate(7, Date.valueOf(doctor.getBirthDate()));
            ps.setString(8,doctor.getSpecialization().toString());
            ps.setLong(9,doctor.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void editByAdmin(Doctor doctor) throws EmailException {
        try{
            EmailVerification.verifyEmail(doctor.getEmail());
            Connection con= DBConnection.getConnection();
            String sql = "Update doctors set  userName=?, email=?, firstName=?, lastName=?, address=?, birthDate=?,specialization=? where id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, doctor.getUserName());
            ps.setString(2, doctor.getEmail());
            ps.setString(3,doctor.getFirstName());
            ps.setString(4,doctor.getLastName());
            ps.setString(5,doctor.getAddress());
            ps.setDate(6, Date.valueOf(doctor.getBirthDate()));
            ps.setString(7,doctor.getSpecialization().toString());
            ps.setLong(8,doctor.getId());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
    }

    @Override
    public void delete(Doctor doctor) {
        try {

        Connection con = DBConnection.getConnection();
        String sql = "delete from doctors  WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, doctor.getId());
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Deleted!");
        } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error");
        }
    }
    public void deleteByID(long id){ try {

        Connection con = DBConnection.getConnection();
        String sql = "delete from doctors  WHERE id=?";
        PreparedStatement ps = con.prepareStatement(sql);
        ps.setLong(1, id);
        ps.executeUpdate();
        JOptionPane.showMessageDialog(null, "Deleted!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Error");
    }}

    @Override
    public boolean existEmail(String userEmail) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select email from doctors WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                return true;
            }
        }

        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }

        return false;
    }

    @Override
    public Doctor getByEmail(String userEmail) {
        Doctor doctor = new Doctor();
        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT * from doctors where email='"+userEmail+"'";
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            if(rs.next()) {

                doctor.setId(rs.getLong("id"));
                doctor.setUserName(rs.getString("userName"));
                doctor.setAddress(rs.getString("address"));
                doctor.setBirthDate(rs.getDate("birthDate").toLocalDate());
                doctor.setEmail(rs.getString("email"));
                doctor.setFirstName(rs.getString("firstName"));
                doctor.setLastName(rs.getString("lastName"));
                doctor.setPassword(rs.getString("password"));
                doctor.setSpecialization(Specialization.valueOf(rs.getString("specialization")));


            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return doctor;
    }

    @Override
    public Doctor getByID(long id) {
        Doctor doc=new Doctor();
        try {

            Connection con = DBConnection.getConnection();
            String sql = "SELECT userName, email, password, firstName, lastName, address, birthDate from doctors where id="+id;
            PreparedStatement ps = con.prepareStatement(sql);

            ResultSet rs=ps.executeQuery();

            if(rs.next()){
                doc.setAddress(rs.getString("address"));
                doc.setBirthDate(rs.getDate("birthDate").toLocalDate());
                doc.setEmail(rs.getString("email"));
                doc.setFirstName(rs.getString("firstName"));
                doc.setLastName(rs.getString("lastName"));
                doc.setPassword(rs.getString("password"));
                doc.setId(id);

            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return doc;
    }

    @Override
    public List<Doctor> getAll() {
        List<Doctor> list = new ArrayList<Doctor>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM doctors ";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Doctor doc = new Doctor();
                doc.setUserName(rs.getString("userName"));
                doc.setAddress(rs.getString("address"));
                doc.setBirthDate(rs.getDate("birthDate").toLocalDate());
                doc.setEmail(rs.getString("email"));
                doc.setFirstName(rs.getString("firstName"));
                doc.setLastName(rs.getString("lastName"));
                doc.setPassword(rs.getString("password"));
                doc.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
                doc.setId(rs.getInt("id"));

                list.add(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }

    @Override
    public String getPassword(String userEmail) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select password from doctors WHERE email='" + userEmail + "'";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString("password");
        }

        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "User doesn't exist!");
        }

        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return null;
    }

    @Override
    public String getFirstNameByID(long id) {
        try{
            Connection con = DBConnection.getConnection();
            String sql = "select firstName from doctors WHERE id="+id;
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            rs.next();
            return rs.getString("firstName");
        }

        catch(SQLException e){
            JOptionPane.showMessageDialog(null, "User doesn't exist!");

            return null;
    }}

    @Override
    public String getLastNameByID(long id) {
            try{
                Connection con = DBConnection.getConnection();
                String sql = "select lastName from doctors WHERE id="+id;
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs=ps.executeQuery();
                rs.next();
                return rs.getString("lastName");
            }

            catch(SQLException e){
                JOptionPane.showMessageDialog(null, "User doesn't exist!");

            }
        return null;
    }

    @Override
    public String getEmailById(long id) {
        return null;
    }


    public List<Doctor> getAllBySpecialization(Specialization specialization) {
        List<Doctor> list = new ArrayList<Doctor>();
        try {
            Connection con = DBConnection.getConnection();
            String sql = "SELECT * FROM doctors where specialization=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, specialization.toString());
            ResultSet rs = ps.executeQuery();

            while(rs.next()){
                Doctor doc = new Doctor();
                doc.setId(rs.getLong("id"));
                doc.setAddress(rs.getString("address"));
                doc.setBirthDate(rs.getDate("birthDate").toLocalDate());
                doc.setEmail(rs.getString("email"));
                doc.setFirstName(rs.getString("firstName"));
                doc.setLastName(rs.getString("lastName"));
                doc.setPassword(rs.getString("password"));
                doc.setSpecialization(Specialization.valueOf(rs.getString("specialization")));
                doc.setId(rs.getInt("ID"));

                list.add(doc);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error");
        }
        return list;
    }


}




