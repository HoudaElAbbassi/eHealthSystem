package user;

import Exceptions.EmailException;
import Exceptions.PasswordException;

import java.util.List;

public interface UserDAO<User>{
    public boolean save(User user) throws PasswordException, EmailException;
    public void edit(User user) throws PasswordException, EmailException;
    public void delete(User user);
    public void deleteByID(long id);
    public boolean existEmail(String userEmail);
    public User getByEmail(String userEmail);
    public User getByID(long id);
    public List<User> getAll();
    public String getPassword(String userEmail);
    public String getFirstNameByID(long id);
    public  String getLastNameByID(long id);
    public String getEmailById(long id);
    public void editByAdmin(User user) throws EmailException;
}
