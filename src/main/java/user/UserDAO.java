package user;

import Exceptions.EmailException;
import Exceptions.PasswordException;

import java.util.List;

/**
 * This interface specifies the abstract methods that the DAO Implementations of the Subclasses of User must implement
 * @param <User> a generic Type of User
 */
public interface UserDAO<User>{
    /**
     *This is a method that receives an User object
    *and saves its properties into the database
    *after checking the validity of 
    *the password and email.
    *@param user of the type User
     * @return success of saving
    *@throws PasswordException password error
    *@throws  EmailException email error
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public boolean save(User user) throws PasswordException, EmailException;
    /** 
    *This is a method that receives an admin object
    *The validity of password and email is checked first
    *The method allows us edit the user properties
    *@param user of the type user
    *@throws PasswordException password error
    *@throws EmailException email error
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public void edit(User user) throws PasswordException, EmailException;
    /** 
    *This is a method that receives an user object
    *It searches for ID of the given user
    *and deletes the object inside the database.
    *@param user of the type User.
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public void delete(User user);
    /** 
    *This is a method that receives the ID of an Admin
    *It searches for the Admin with the given ID
    *and deletes the object inside the database.
    *@param id of the type long 
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public void deleteByID(long id);
    
    /** 
    *This is a method that receives an email address
    *It searches for the given email adress
    *if the email can be found it returns true
    *@param userEmail of type String
     * @return existenz of email
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public boolean existEmail(String userEmail);
     /** 
    *This is a method that recieves an email adress
    *It searches for the user with the given email adress
    *@return user of type User
    *@param userEmail of type String
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public User getByEmail(String userEmail);
    /** 
    *This is a method that recieves an ID
    *It searches for the user with the given ID
    *@return user of type User
    *@param id of type long
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public User getByID(long id);
    /** 
    *This is a method that returns all users
    *First an empty list is created
    *then all users are added to the list
    *@return list of type Admin
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public List<User> getAll();
    
    /** 
    *This is a method that gets the Password of a user
    *The method recieves email adress of a user
    *It seaches for the corrisponding password 
    *@param userEmail of type String
     * @return encrypted password
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    
    public String getPassword(String userEmail);
   /** 
    *This is a method that gets the first Name of the user based on his ID
    *It checks inside the database for the username corresponding to the given ID
    *It seaches for the corresponding password 
    *@param id of type long
    * @return first name
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public String getFirstNameByID(long id);
    
   /** 
    *This is a method that gets the last Name of the user based on his ID
    *It checks inside the databaste for the username corresponding to the given ID
    *It seaches for the corresponding password 
    *@param id of type long
    * @return email address
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public  String getLastNameByID(long id);
    /** 
    *This is a method that gets the email of the user based on his ID
    *It checks inside the databaste for the username corresponding to the given email
    *It seaches for the corresponding password 
    *@param id of type long
     * @return email address
    *@author Prabal, Daniel, Houda , Amine , Ahmed
    */
    public String getEmailById(long id);
    /** 
    *This is a method that recieves an user and allows the admin to edit the user
    *@param user of type User
     * @throws EmailException email error
    *@author Prabal, Daniel, Houda , Amine ,Ahmed
    */
    public void editByAdmin(User user) throws EmailException;
}
