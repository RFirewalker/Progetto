package Model;
public class User {
    private String email,name,surname,address,phoneNumber,password,username;
    private int id;

    public int getId() {
        return id;
    }
    public User(int id,String email,String name,String surname,String address,String phoneNumber){
        this.id = id;
        this.email= email;
        this.name= name;
        this.surname= surname;            
        this.address= address; 
        this.phoneNumber= phoneNumber; 
        this.password="";
        this.username=""; 
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
}
