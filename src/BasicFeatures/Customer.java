/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Group Not Ok
  ID: s3963227, s3932184, s3955317, s3866724
  Acknowledgement: Acknowledge the resources that you use here.
*/

package BasicFeatures;
import Menu.Menu;

import Bot.Assistant;

import java.io.IOException;
import java.util.*;

public class Customer {
    private final String CUSTOMER_FILEPATH = "src/Database/customers.txt";
    private String id;
    private String fullName;
    private String email;
    private String address;
    private String phoneNumber;
    private String membership;
    private String username;
    private String password;
    private long totalSpending;

    ArrayList<Customer> customers = new ArrayList<>();

    public Customer(){

    }

    public Customer(String id, String fullName, String email, String address, String phoneNumber, String membership, String username, String password, long totalSpending) {
        this.id = id;
        this.fullName = fullName;
        this.email = email;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.membership = membership;
        this.username = username;
        this.password = password;
        this.totalSpending = totalSpending;
    }

    //allow customer to register
    public void register(String fullName, String email, String address, String phoneNumber, String username, String password) throws IOException {
        int countLine = Assistant.countLine(CUSTOMER_FILEPATH);
        id = "C" + countLine;
        membership = "Regular";
        totalSpending = 0;
        String newCustomer = "\n" + id+ "," + fullName+ "," + email+ "," + address+ "," + phoneNumber+ "," + membership+ "," + username+ "," + password+ "," + totalSpending;
        Assistant.writeToFile(CUSTOMER_FILEPATH, newCustomer);
    }

    //allow customer to login
    public void login(String username, String password) throws IOException {
        extractDatabase();

        boolean loginValidation = false;
        Customer customer = new Customer();
        customer = getCustomer(username);

        if(customer == null){
            System.out.println("Customer not found. Please register to begin.");
            Menu.StoreSystemMenu();
        }
        else{
            if(customer.getUsername()!= null && customer.getUsername().equals(username)&& customer.getPassword().equals(password)){;
                loginValidation = true;
            }
            else {
                loginValidation = false;
            }

            if(loginValidation == true){
                System.out.println("Login success! Welcome back customer!");
                setUsername(username);
                Menu.CustomerActionMenu();
            }
            else{
                System.out.println("Login failed! Username or password is incorrect.");
                Menu.CustomerWelcomeMenu();
            }
        }
    }

    //get username array list by reading columns in the database
    public ArrayList<String> getUsernameArraylist(){
        String[] usernameArray = Assistant.ReadCol(6, CUSTOMER_FILEPATH,",");
        ArrayList<String> userNameArrayList = new ArrayList<>();

        for(int i = 0; i < usernameArray.length; i++){
            userNameArrayList.add(usernameArray[i]);
        }

        return userNameArrayList;
    }

    public Customer getCustomer(String username){
        for(Customer customer : customers){
            if(customer.getUsername().equals(username)){
                return customer;
            }
        }
        return null;
    }

    //get customers from database and add it to customers arraylist
    public void extractDatabase(){
        //empty an ArrayList to make sure no info was repeated
        customers.clear();

        int countLine = Assistant.countLine(CUSTOMER_FILEPATH);
        String[] id = Assistant.ReadCol(0, CUSTOMER_FILEPATH,",");
        String[] fullname = Assistant.ReadCol(1, CUSTOMER_FILEPATH,",");
        String[] email = Assistant.ReadCol(2, CUSTOMER_FILEPATH,",");
        String[] address = Assistant.ReadCol(3, CUSTOMER_FILEPATH,",");
        String[] phoneNumber = Assistant.ReadCol(4, CUSTOMER_FILEPATH,",");
        String[] membership = Assistant.ReadCol(5, CUSTOMER_FILEPATH,",");
        String[] username = Assistant.ReadCol(6, CUSTOMER_FILEPATH,",");
        String[] password = Assistant.ReadCol(7, CUSTOMER_FILEPATH,",");
        String[] totalSpending = Assistant.ReadCol(8, CUSTOMER_FILEPATH,",");

        for(int i = 0; i < countLine - 1; i++){
            customers.add(new Customer(id[i],fullname[i], email[i], address[i], phoneNumber[i], membership[i], username[i], password[i], Long.parseLong(totalSpending[i])));
        }
    }

    //check if the username existed
    public boolean usernameValidation(String username){
        boolean usernameValidation = true;
        ArrayList<String> allUsernameList = getUsernameArraylist();

        if(allUsernameList.contains(username)) {
            usernameValidation = true;
        }
        else {
            usernameValidation = false;
        }

        return usernameValidation;
    }

    //search for a customer's information by username
    public void viewCustomerInfo(String username){
        extractDatabase();
        System.out.println("Here is your information: ");
        for(Customer customer:customers){
            if(customer.getUsername().equals(username)){
                System.out.println("- Name: " + customer.getFullName() + "\n" +
                        "- Email: " + customer.getEmail() + "\n" +
                        "- Address: " + customer.getAddress() + "\n" +
                        "- Phone number: " + customer.getPhoneNumber() + "\n" +
                        "- Password: " + customer.getPassword());
            }
        }
    }

    //search for customer membership by username
    public void viewMembership(String username){
        extractDatabase();
        String reward = "";
        for(Customer customer: customers){
            if(customer.username.equals(username)){
                System.out.println("Your current membership is: " + customer.membership);
            }

            if(customer.membership.equals("Silver")){
                reward = "5%";
            }
            else if(customer.membership.equals("Gold")){
                reward = "10%";
            }
            else if(customer.membership.equals("Platinum")){
                reward = "15%";
            }
            else{
                reward = "0%";
            }
        }
        System.out.println("Your current reward is: " + reward + " discount.");
    }

    //allow customer to update certain personal information
    public void customerUpdateInfo(int userInput, String username) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        Customer customer = getCustomer(username);
        String oldContent = customer.getId() + "," + customer.getFullName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumber() + "," + customer.getMembership() + "," + customer.username + "," + customer.getPassword() + "," + customer.getTotalSpending();

        String[] updateCustomerInfo = oldContent.split(",");
        String updatedContent = "";

        switch(userInput){
            //update full name
            case 1:
                System.out.println("----- Full name update -----");
                System.out.print("Enter your new full name: ");
                String newFullname = keyboard.nextLine();

                updateCustomerInfo[1] = newFullname;
                updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
                Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
                break;

            //update email
            case 2:
                System.out.println("----- Email update -----");
                System.out.print("Enter your new email: ");
                String newEmail = keyboard.nextLine();

                updateCustomerInfo[2] = newEmail;
                updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
                Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
                break;

            //update address
            case 3:
                System.out.println("----- Address update -----");
                System.out.print("Enter your new address: ");
                String newAddress = keyboard.nextLine();

                updateCustomerInfo[3] = newAddress;
                updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
                Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
                break;

            //update phone number
            case 4:
                System.out.println("----- Phone Number update -----");
                System.out.print("Enter your new phone number: ");
                String newPhoneNumber = keyboard.nextLine();

                updateCustomerInfo[4] = newPhoneNumber;
                updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
                Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
                break;

            //update password
            case 5:
                System.out.println("----- Password update -----");
                System.out.print("Enter your new password: ");
                String newPassword = keyboard.nextLine();

                updateCustomerInfo[7] = newPassword;
                updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
                Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
                break;

            case 6:
                Menu.CustomerActionMenu();
                break;
            case 7:
                Menu.ThankYouMenu();
                break;
        }
    }

    //get the customer with the highest spending - for admin usage
    public void getHighestSpendingCustomer(){
        extractDatabase();

        Collections.sort(customers, new Comparator<Customer>()
        {
            public int compare(Customer c1, Customer c2){
                return Long.valueOf(c1.totalSpending).compareTo(c2.totalSpending);
            }
        });

        //hold most spending value
        long mostSpending = customers.get(customers.size()-1).getTotalSpending();

        //print all customers with most spending
        for(Customer customer : customers){
            if(customer.getTotalSpending() == mostSpending){
                System.out.println("- " + customer.getFullName() + " with the spending of: " + customer.getTotalSpending());
            }
        }
    }

    //get a customer by customerID
    public Customer getCustomerByID(String customerID){
        extractDatabase();
        for(Customer customer : customers){
            if(customer.getId().equals(customerID)){
                return customer;
            }
        }
        return null;
    }

    //update membership everytime their spending changes.
    public void updateMembership(String username){
        extractDatabase();

        Customer customer = getCustomer(username);
        String oldContent = customer.getId() + "," + customer.getFullName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumber() + "," + customer.getMembership() + "," + customer.username + "," + customer.getPassword() + "," + customer.getTotalSpending();

        String[] updateCustomerInfo = oldContent.split(",");
        String updatedContent = "";

        String newMembership = "";

        if(customer.getTotalSpending() < 5000000){
            newMembership = "Regular";
        }
        else if(customer.getTotalSpending() < 10000000){
            newMembership = "Silver";
        }
        else if(customer.getTotalSpending() < 25000000){
            newMembership = "Gold";
        }
        else{
            newMembership = "Platinum";
        }
        updateCustomerInfo[5] = String.valueOf(newMembership);
        updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
        Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
    }

    //add new spending to database when customer confirmed orders
    public void addSpending(String username, long orderPrice){
        extractDatabase();
        Customer customer = getCustomer(username);
        String oldContent = customer.getId() + "," + customer.getFullName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumber() + "," + customer.getMembership() + "," + customer.username + "," + customer.getPassword() + "," + customer.getTotalSpending();

        String[] updateCustomerInfo = oldContent.split(",");
        String updatedContent = "";

        long newSpending = getTotalSpending()+orderPrice;

        updateCustomerInfo[8] = String.valueOf(newSpending);
        updatedContent = Assistant.arrayToCSVString(updateCustomerInfo);
        Assistant.modifyFile(CUSTOMER_FILEPATH, oldContent, updatedContent);
    }

    //list the numbers of different type of membership
    public void listMembershipCount(){
        String[] membership = new String[]{"Regular","Silver","Gold","Platinum"};

        List<String> customerMembership = Arrays.asList(Assistant.ReadCol(5,CUSTOMER_FILEPATH,","));

        //create and append key, values to Hashmap
        HashMap<String, Integer> map = new HashMap<>();
        for(String str: membership){
            map.put(str,0);
        }

        //change values as count
        for(String str : customerMembership){
            if(map.containsKey(str)){
                map.put(str, map.get(str)+1);
            }
            else{
                map.put(str,0);
            }
        }

        //display map in bullet format
        for(String i:map.keySet()){
            System.out.println("- " + i + ": " + map.get(i));
        }
    }

    //check if customerID is valid
    public boolean validateCustomerID(String customerID){
        extractDatabase();
        boolean validateCustomerID = false;
        for(Customer customer: customers) {
            if (customer.getId().equals(customerID)) {
                validateCustomerID = true;
            }
        }
        return validateCustomerID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMembership() {
        return membership;
    }

    public long getTotalSpending() {
        return totalSpending;
    }
}
