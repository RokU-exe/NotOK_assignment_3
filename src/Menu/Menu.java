package Menu;

import BasicFeatures.Admin;
import BasicFeatures.Customer;
import BasicFeatures.Order;
import BasicFeatures.Product;

import java.io.IOException;
import java.util.Scanner;

public class Menu {
    static Customer customer = new Customer();
    static Admin admin = new Admin();

    static Product product = new Product();

    static Order order = new Order();
    static Scanner keyboard = new Scanner(System.in);

    private static final String CUSTOMER_FILEPATH = "src/Database/customers.txt";
    private static final String ORDER_FILEPATH = "src/Database/orders.txt";
    private static final String PRODUCT_FILEPATH = "src/Database/items.txt";


    //allow user to choose their role
    public static void StoreSystemMenu() throws IOException {
        System.out.println("============================== Store System Menu - Welcome to MBLH Store Management System ==============================");
        System.out.println("To begin, please let us know who you are: ");
        System.out.println("1. Customer");
        System.out.println("2. Admin");
        System.out.println("3. Exit");
        int option = optionInput();

        switch (option){
            case 1:
                CustomerWelcomeMenu();
                break;

            case 2:
                AdminLoginMenu();
                break;

            case 3:
                ThankYouMenu();
                break;
        }
    }


    //allow customer to move to login or register menu
    public static void CustomerWelcomeMenu() throws IOException {
        System.out.println("============================== Customer Welcome Menu ==============================");
        System.out.println("1. Register");
        System.out.println("2. Login.");
        System.out.println("3. Return to Store System Screen.");
        System.out.println("4. Exit.");
        int option = optionInput();

        switch (option){
            case 1:
                CustomerRegisterMenu();
                break;
            case 2:
                CustomerLoginMenu();
                break;
            case 3:
                StoreSystemMenu();
                break;
            case 4:
                ThankYouMenu();
                break;
        }
    }

    //allow customer to login
    public static void CustomerLoginMenu() throws IOException {
        System.out.println("============================== Customer - Login ==============================");
        System.out.print("Enter your username: ");
        String username = keyboard.nextLine();
        System.out.print("Enter your password: ");
        String password = keyboard.nextLine();

        customer.login(username, password);
    }

    //allow admin login
    public static void AdminLoginMenu() throws IOException {
        System.out.println("============================== Admin - Login ==============================");
        System.out.print("Enter your username: ");
        String username = keyboard.nextLine();
        System.out.print("Enter your password: ");
        String password = keyboard.nextLine();

        admin.login(username, password);
    }

    public static void AdminActionMenu() throws IOException {
        System.out.println("============================== Admin Action Menu ==============================");
        System.out.println("1. View information");
        System.out.println("2. Add or Remove product.");
        System.out.println("3. Update product price.");
        System.out.println("4. Get all order for particular customer.");
        System.out.println("5. Change status of order.");
        System.out.println("6. Remove a customer.");
        System.out.println("7. Perform statistics operator.");
        System.out.println("8. Check orders on particular day");
        System.out.println("9. Add or remove category.");
        System.out.println("10. Exit");
        int option = optionInput();

        switch (option){
            case 1:
                ViewInformationMenu();
                AdminActionMenu();
                break;
            case 2:
                ModifyProductMenu();
                AdminActionMenu();
                break;
            case 3:
                System.out.println("============================== Admin - Update Product Price ==============================");
                admin.updateProduct();
                AdminActionMenu();
                break;
            case 4:
                System.out.println("============================== Admin - Get a Customer Order ==============================");
                System.out.print("Enter the customer you wanted to get order: ");
                String customerID = keyboard.nextLine();

                if(customer.validateCustomerID(customerID) == true){
                    System.out.println("Here are all order(s) made by customer with ID: " + customerID);
                    System.out.println("- orderID, productID, customerID, customerMembership, totalPrice, orderStatus, orderDate");
                    order.getOrderByCustomerID(customerID);
                }
                else{
                    System.out.println("Customer not found.");
                }
                AdminActionMenu();
                break;
            case 5:
                System.out.println("============================== Admin - Approve Order ==============================");
                admin.approveOrder();
                AdminActionMenu();
                break;
            case 6:
                System.out.println("============================== Admin - Remove Customer ==============================");
                System.out.print("Enter the customerID of the customer you want to remove: ");
                customerID = keyboard.nextLine();
                admin.removeCustomer(customer.getCustomerByID(customerID));
                AdminActionMenu();
                break;
            case 7:
                AdminStatisticsOperatorMenu();
                break;
            case 8:
                System.out.println("============================== Admin - Get Orders on Specific Date ==============================");
                System.out.print("Enter the date in dd-MM-yyyy format: ");
                String orderDate = keyboard.nextLine();
                if(order.dateValidation(orderDate) == true){
                    order.getOrderByDate(orderDate);
                }
                else{
                    System.out.println("Date not found.");
                }
                AdminActionMenu();
                break;
            case 9:
                AdminModifyCategory();
                break;
            case 10:
                ThankYouMenu();
                break;
        }
    }

    public static void AdminModifyCategory() throws IOException {
        System.out.println("============================== Admin - Modify Category ==============================");
        System.out.println("1. Change category for specific product");
        System.out.println("2. Remove category");
        System.out.println("3. Return to Admin Action Menu");
        System.out.println("4. Exit");

        int option = optionInput();

        switch (option){
            case 1:
                product.updateCategory();
                AdminActionMenu();
                break;
            case 2:
                product.removeCategory();
                AdminActionMenu();
                break;
            case 3:
                AdminActionMenu();
                break;
            case 4:
                ThankYouMenu();
                break;
        }
    }

    public static void AdminStatisticsOperatorMenu() throws IOException {
        System.out.println("============================== Admin - Statistic Operation ==============================");
        System.out.println("1. Calculate total revenue.");
        System.out.println("2. Calculate revenue for specific date.");
        System.out.println("3. Find most/least popular product");
        System.out.println("4. Find customer who pays the most. ");
        System.out.println("5. List number of different type of membership");
        System.out.println("6. Return to Admin Action Menu");
        System.out.println("7. Exit");
        int option = optionInput();

        switch (option){
            case 1:
                System.out.println("============================== Admin - Store Total Revenue ==============================");
                order.calculateTotalRevenue();
                AdminStatisticsOperatorMenu();
                break;
            case 2:
                System.out.println("============================== Admin - Store Daily Revenue ==============================");
                System.out.print("Enter the date in dd-MM-yyyy format: ");
                String orderDate = keyboard.nextLine();

                if(order.dateValidation(orderDate) == true){
                    order.calculateDailyRevenue(orderDate);
                }
                else{
                    System.out.println("Date not found.");
                }
                AdminStatisticsOperatorMenu();
                break;
            case 3:
                System.out.println("============================== Admin - Find Popular Product ==============================");
                order.findPopularProduct();
                AdminStatisticsOperatorMenu();
                break;
            case 4:
                System.out.println("============================== Admin - View Most Spend Customer ==============================");
                customer.getHighestSpendingCustomer();
                AdminStatisticsOperatorMenu();
                break;
            case 5:
                System.out.println("============================== Admin - List Membership Count ==============================");
                customer.listMembershipCount();
                AdminStatisticsOperatorMenu();
                break;
            case 6:
                AdminActionMenu();
                break;
            case 7:
                ThankYouMenu();
                break;
        }
    }

    public static void ModifyProductMenu() throws IOException {
        System.out.println("============================== Admin - Modify Product ==============================");
        System.out.println("1. Add product");
        System.out.println("2. Remove product");
        System.out.println("3. Return to Admin Action Menu");
        System.out.println("4. Exit");
        int option = optionInput();

        switch (option){
            case 1:
                admin.addProduct();
                AdminActionMenu();
                break;
            case 2:
                System.out.print("Enter the productID of the product you want to remove: ");
                String productID = keyboard.nextLine();
                admin.removeProduct(product.getProduct(productID));
                AdminActionMenu();
                break;
            case 3:
                AdminActionMenu();
                break;
            case 4:
                ThankYouMenu();
                break;
        }
    }

    public static void ViewInformationMenu() throws IOException {
        System.out.println("============================== Admin - View Information Menu ==============================");
        System.out.println("1. View products information");
        System.out.println("2. View orders information");
        System.out.println("3. View customers information");
        System.out.println("4. Return to Admin Action Menu");
        int option = optionInput();

        switch (option){
            case 1:
                System.out.println("============================== Admin - View All Products ==============================");
                admin.viewAllInformation(PRODUCT_FILEPATH);
                AdminActionMenu();
                break;
            case 2:
                System.out.println("============================== Admin - View All Orders ==============================");
                admin.viewAllInformation(ORDER_FILEPATH);
                AdminActionMenu();
                break;
            case 3:
                System.out.println("============================== Admin - View All Customers ==============================");
                admin.viewAllInformation(CUSTOMER_FILEPATH);
                AdminActionMenu();
                break;
            case 4:
                AdminActionMenu();
                break;
        }
    }


    //View all basic features of customers
    public static void CustomerActionMenu() throws IOException {
        System.out.println("============================== Customer Action Menu ==============================");
        System.out.println("1. View and Update personal information.");
        System.out.println("2. Check current membership status.");
        System.out.println("3. List all products and view product details.");
        System.out.println("4. Create new order.");
        System.out.println("5. Look for specific order with order ID.");
        System.out.println("6. Get information of all orders made.");
        System.out.println("7. Exit.");
        int option = optionInput();

        switch(option) {
            case 1:
                CustomerUpdateMenu();
                break;
            case 2:
                System.out.println("============================== Customer - Membership Status ==============================");
                customer.viewMembership(customer.getUsername());
                System.out.println();
                CustomerActionMenu();
                break;
            case 3:
                ViewProductMenu();
                break;
            case 4:
                CreateOrderMenu();
                break;
            case 5:
                SearchOrderMenu();
                break;
            case 6:
                order.getAllOrder(customer.getCustomer(customer.getUsername()));
                break;
            case 7:
                ThankYouMenu();
                break;
        }

    }

    //Allow customer to register
    public static void CustomerRegisterMenu() throws IOException {
        System.out.println("============================== Customer - Register ==============================");
        System.out.print("Enter your username: ");
        String username = keyboard.nextLine();

        if(customer.usernameValidation(username) == true){
            System.out.println("Username existed. Moving you back to Customer Welcome Menu");
            CustomerWelcomeMenu();
        }
        else{
            System.out.print("Enter your password: ");
            String password = keyboard.nextLine();

            System.out.print("Enter your full name: ");
            String fullname = keyboard.nextLine();

            System.out.print("Enter your email: ");
            String email = keyboard.nextLine();

            System.out.print("Enter your address: ");
            String address = keyboard.nextLine();

            System.out.print("Enter your phone number: ");
            String phoneNumber = keyboard.nextLine();

            customer.register(fullname,email,address,phoneNumber,username,password);

            System.out.println("Registration completed!");
            CustomerWelcomeMenu();
        }
    }

    //allow customer to update their personal information
    public static void CustomerUpdateMenu() throws IOException {
        System.out.println("============================== Customer - View and Update Personal Information ==============================");
        customer.viewCustomerInfo(customer.getUsername());

        //Ask user for which update option
        System.out.println("----- What do you want to update? -----");
        System.out.println("1. Full name.");
        System.out.println("2. Email.");
        System.out.println("3. Address.");
        System.out.println("4. Phone number.");
        System.out.println("5. Password.");
        System.out.println("6: No update, return to Customer Action Menu.");
        System.out.println("7. Exit.");
        System.out.print("Enter your option in NUMBER format (1-7): ");
        int option = optionInput();

        System.out.println();
        customer.customerUpdateInfo(option, customer.getUsername());
        CustomerActionMenu();
    }

    //allow customer to view product with sorting options
    public static void ViewProductMenu() throws IOException {
        System.out.println("============================== Customer - View Product Detail ==============================");
        System.out.println("Would you like to sort your product?");
        System.out.println("1. View all product.");
        System.out.println("2. Ascending price.");
        System.out.println("3. Descending price.");
        System.out.println("4. By product category.");
        System.out.println("5. By price range.");
        System.out.println("6. Return to Customer Action Menu.");
        System.out.println("7. Exit.");
        System.out.print("Enter your option in NUMBER format (1-7): ");
        int option = optionInput();

        switch (option){
            case 1:
                product.viewAllProductSort("none");
                ViewProductMenu();
                break;

            case 2:
                product.viewAllProductSort("ascending");
                ViewProductMenu();
                break;

            case 3:
                product.viewAllProductSort("descending");
                ViewProductMenu();
                break;

            case 4:
                System.out.println("Here is the category list: " + product.getCategoryList());
                System.out.print("Please enter a category for sorting: ");
                String category = keyboard.nextLine();

                System.out.println("How would you like to sort?");
                System.out.println("1. Ascending price.");
                System.out.println("2. Descending price.");
                System.out.println("3. None.");
                System.out.println("4. Return to View Product Detail Menu.");
                System.out.print("Enter your option in NUMBER format (1-3): ");
                option = keyboard.nextInt();
                System.out.println();

                switch (option){
                    case 1:
                        product.viewCategorySort(category,"ascending");
                        ViewProductMenu();
                        break;
                    case 2:
                        product.viewCategorySort(category,"descending");
                        ViewProductMenu();
                        break;
                    case 3:
                        product.viewCategorySort(category,"none");
                        ViewProductMenu();
                        break;
                    case 4:
                        ViewProductMenu();
                        break;
                }
                break;

            case 5:
                product.viewPriceRangeSort();
                ViewProductMenu();
                break;
            case 6:
                CustomerActionMenu();
                break;
            case 7:
                ThankYouMenu();
                break;
        }
    }

    //allow customer to create order
    public static void CreateOrderMenu() throws IOException {
        System.out.println("============================== Customer - Create Order ==============================");
        System.out.print("Enter the product ID you wanted to order: ");
        String productID = keyboard.nextLine();

        if(product.validateProductID(productID) == true){
            order.createOrder(customer.getCustomer(customer.getUsername()), product.getProduct(productID));
        }
        else{
            System.out.println("Product not found.");
            CreateOrderMenu();
        }
    }

    //allow customer to search order
    public static void SearchOrderMenu() throws IOException {
        System.out.println("============================== Customer - Search Order ==============================");
        System.out.print("Enter the order ID for view: ");
        String orderID = keyboard.nextLine();

        if(order.validateOrderID(orderID) == true){
            order.searchOrder(orderID, customer.getCustomer(customer.getUsername()));
        }
        else{
            System.out.println("Order not existed.");
            CustomerActionMenu();
        }
    }

    //ask for user option input
    public static int optionInput(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter your option in number format: ");
        int optionInput = keyboard.nextInt();

        return optionInput;
    }

    //display thank you message and group information
    public static void ThankYouMenu(){
        System.out.println("============================== Thank you for using our system! Goodbye ==============================");
        System.out.println("COSC2081 GROUP ASSIGNMENT");
        System.out.println("STORE ORDER MANAGEMENT SYSTEM");
        System.out.println("Instructor: Mr. Tom Huynh & Dr. Phong Ngo");
        System.out.println("Group: Not Ok :)");
        System.out.println("- s3963227, Tran Nguyen Ngoc Han");
        System.out.println("- s3932184, Do Xuan Gia Bao");
        System.out.println("- s3955317, Le Xuan Loc");
        System.out.println("- s3866724, Kim Minsung");
    }
}
