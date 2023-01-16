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
import Bot.Assistant;
import Menu.Menu;
import java.io.IOException;
import java.util.Scanner;

public class Admin {

    private final String USERNAME = "admin";
    private final String PASSWORD = "adminPassword";
    private final String CUSTOMER_FILEPATH = "src/Database/customers.txt";
    private final String PRODUCT_FILEPATH = "src/Database/items.txt";


    public Admin() {

    }
    //allow admin to login
    public void login(String username, String password) throws IOException {
        if(username.equals(USERNAME) && password.equals(PASSWORD)){
            System.out.println("Login success! Welcome back admin!");
            Menu.AdminActionMenu();
        }
        else{
            System.out.println("Username or password is incorrect.");
            Menu.StoreSystemMenu();
        }
    }

    //allow admin to view all information
    public void viewAllInformation(String filepath){
        Assistant.readAllLines(filepath);
    }

    //allow admin to add a new product
    public void addProduct() throws IOException {
        Scanner keyboard = new Scanner(System.in);
        int productCount = Assistant.countLine(PRODUCT_FILEPATH);
        String newProductID = "I" + productCount + "-" + Assistant.getRandomNumber();

        System.out.print("Enter product title: ");
        String newProductTitle = keyboard.nextLine();

        System.out.print("Enter product price: ");
        String newProductPrice = keyboard.nextLine();

        System.out.print("Enter product category: ");
        String newCategory = keyboard.nextLine();

        if(newCategory.isEmpty()){
            newCategory = "None";
        }

        String newProduct = "\n" + newProductID + "," + newProductTitle + "," + newProductPrice + "," + newCategory;
        Assistant.writeToFile(PRODUCT_FILEPATH, newProduct);
        System.out.println("New product added!");
        keyboard.close();
    }


    //allow admin to remove a product
    public void removeProduct(Product product) throws IOException {
        String deleteLine = product.getId() + "," + product.getTitle() + "," + product.getPrice() + "," + product.getCategory();
        Assistant.removeLine(PRODUCT_FILEPATH, deleteLine);
        System.out.println("Product removed!");
    }


    //allow admin to update product
    public void updateProduct(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the product ID your wanted to update: ");
        String productId = keyboard.nextLine();
        System.out.print("Enter the new price: ");
        long newPrice = Long.parseLong(keyboard.nextLine());

        Product product = new Product();
        product.updateProductPrice(productId, newPrice);
        System.out.print("Product price updated!");
    }

    //allow admin to change the order status to delivered
    public void approveOrder(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the oderID: ");
        String orderID = keyboard.nextLine();

        Order order = new Order();
        order.changeOrderStatus(orderID);

        System.out.println("Order status updated to delivered!");
    }

    //allow admin to remove a customer
    public void removeCustomer(Customer customer) throws IOException {
        String deleteLine = customer.getId() + "," + customer.getFullName() + "," + customer.getEmail() + "," + customer.getAddress() + "," + customer.getPhoneNumber() + "," + customer.getMembership() + "," + customer.getUsername() + "," + customer.getPassword() + "," + customer.getTotalSpending();
        Assistant.removeLine(CUSTOMER_FILEPATH, deleteLine);
        System.out.println("Customer removed!");
    }
}
