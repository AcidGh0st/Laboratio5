package domain;

public class Customer extends  Master {
    private int age;
    private String phoneNumber;
    private String email;


    public Customer(int id, String name, int age, String phoneNumber, String email) {
        super(id, name);
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    //Constructor sobrecargado
    public Customer(int id, String name) {
        super(id, name);
    }

    @Override
    String print() {
        return "Customer: " + getId() + " " + getName()+" "+age;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean equals(Customer other){
        return this.getId() == other.getId();
    }

    @Override
    public String toString() {
        return STR."\{super.toString()}, age=\{age}, phoneNumber='\{phoneNumber}\{'\''}, email='\{email}";
    }
}
