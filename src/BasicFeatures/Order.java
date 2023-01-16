/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Group Not Ok
  ID: s3963227, s3932184, s3955317, s3866724
  Acknowledgement:
    - findPopularProduct():
        + https://stackoverflow.com/questions/44367203/how-to-count-duplicate-elements-in-arraylist
        + https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
*/

package BasicFeatures;
import Bot.Assistant;
import Menu.Menu;
import java.io.IOException;
import java.util.*;

public class Order {
    private String orderID;
    private String productID;
    private String customerID;
    private String customerMembership;
    private long totalPrice;
    private String orderStatus;
    private String orderDate;
    private final String ORDER_FILEPATH = "src/Database/orders.txt";
    private final String PRODUCT_FILEPATH = "src/Database/items.txt";
    ArrayList<Order> orders = new ArrayList<>();
    public Order(){

    }

    public Order(String orderID, String productID, String customerID, String customerMembership, long totalPrice, String orderStatus, String orderDate) {
        this.orderID = orderID;
        this.productID = productID;
        this.customerID = customerID;
        this.customerMembership = customerMembership;
        this.totalPrice = totalPrice;
        this.orderStatus = orderStatus;
        this.orderDate = orderDate;
    }

    //allow customer to create new orders
    public void createOrder(Customer customer, Product product) throws IOException {
        Scanner keyboard = new Scanner(System.in);
        this.customerID = customer.getId();

        this.productID = product.getId();
        System.out.println("Here is the product info: ");
        product.printProductInfo(productID);

        customerMembership = customer.getMembership();

        //calculating price based on membership reward.
        if(customerMembership.equals("Platinum")){
            totalPrice = product.getPrice() - (product.getPrice()*15/100);
        }
        else if(customerMembership.equals("Gold")){
            totalPrice = product.getPrice() - (product.getPrice()*10/100);
        }
        else if(customerMembership.equals("Silver")){
            totalPrice = product.getPrice() - (product.getPrice()*5/100);
        }
        else{
            totalPrice = product.getPrice();
        }

        //show new price
        System.out.println("Based on your membership: " + customerMembership);
        System.out.printf("The order total price will be (in VND): %,d\n", totalPrice);
        System.out.print("Pay and confirm order (Y/N): ");
        String orderConfirm = keyboard.next();

        if(orderConfirm.equalsIgnoreCase("y")){
            orderID = "OD" + Assistant.countLine(ORDER_FILEPATH);
            orderStatus = "Delivering";
            orderDate = Assistant.getDate();

            String data = "\n" + orderID + "," + productID + "," + customer.getId() + "," + customer.getMembership() + "," + totalPrice + "," + orderStatus + "," + orderDate;
            Assistant.writeToFile(ORDER_FILEPATH, data);
            customer.addSpending(customer.getUsername(), totalPrice);
            System.out.println("Order confirmed! Your order ID is: " + orderID);
            customer.updateMembership(customer.getUsername());
        }

        Menu.CustomerActionMenu();
    }

    //get order by orderID
    public Order getOrder(String orderID){
        extractDatabase();

        for(Order order : orders){
            if(order.getOrderID().equals(orderID)){
                return order;
            }
        }
        return null;
    }

    //allow customer to view an order made by them only
    public void searchOrder(String orderID, Customer customer) throws IOException {
        Order order = getOrder(orderID);
        if(customer.getId().equals(order.getCustomerID())){
            System.out.println("The order " + order.getOrderID() + " contains: ");
            System.out.println("- Product ID: " + order.getProductID());
            System.out.printf("- Total Price: %,d\n",order.getTotalPrice());
            System.out.println("- Order Status: " + order.getOrderStatus());
            System.out.println("- Order date: " + order.getOrderDate());
        }
        else{
            System.out.println("Ineligible right.");
        }

        Menu.CustomerActionMenu();
    }

    //check if order id is valid
    public boolean validateOrderID(String orderID){
        extractDatabase();
        boolean validateOrderID = false;
        for(Order order:orders) {
            if (order.getOrderID().equals(orderID)) {
                validateOrderID = true;
            }
        }
        return validateOrderID;
    }

    //read the data from database everytime there is an update and append object to arraylist
    public void extractDatabase(){
        //empty an ArrayList to make sure no info was repeated
        orders.clear();

        int countLine = Assistant.countLine(ORDER_FILEPATH);
        String[] id = Assistant.ReadCol(0, ORDER_FILEPATH,",");
        String[] productID = Assistant.ReadCol(1, ORDER_FILEPATH,",");
        String[] customerID = Assistant.ReadCol(2, ORDER_FILEPATH,",");
        String[] customerMembership = Assistant.ReadCol(3, ORDER_FILEPATH,",");
        String[] totalPrice = Assistant.ReadCol(4, ORDER_FILEPATH,",");
        String[] orderStatus = Assistant.ReadCol(5, ORDER_FILEPATH,",");
        String[] orderDate = Assistant.ReadCol(6,ORDER_FILEPATH, ",");
        for(int i = 0; i < countLine - 1; i++){
            orders.add(new Order(id[i], productID[i], customerID[i], customerMembership[i], Long.parseLong(totalPrice[i]), orderStatus[i], orderDate[i]));
        }
    }

    //get all orders by current customer
    public void getAllOrder(Customer customer) throws IOException {
        extractDatabase();
        System.out.println("Here are all your order: ");
        for(Order order:orders){
            if(customer.getId().equals(order.getCustomerID())) {
                order.searchOrder(order.orderID,customer);
            }
        }
    }


    //get order by customerID
    public void getOrderByCustomerID(String customerID) throws IOException {
        extractDatabase();
        for(Order order : orders){
            if(customerID.equals(order.getCustomerID())){
                System.out.println("- " + order.orderID + "," + order.productID + "," + order.customerID + "," + order.customerMembership + "," + order.totalPrice + "," + order.orderStatus + "," + order.orderDate);
            }
        }
    }

    //change order status to delivered - for admin usage
    public void changeOrderStatus(String orderID){
        extractDatabase();
        String oldString = "";

        for(Order order:orders){
            if(order.orderID.equals(orderID)){
                oldString = order.orderID + "," + order.productID + "," + order.customerID + "," + order.customerMembership + "," + order.totalPrice + "," + order.orderStatus + "," + order.orderDate;
            }
        }

        String[] updatedStringArray = oldString.split(",");
        updatedStringArray[5] = "Delivered";

        String updatedString = Assistant.arrayToCSVString(updatedStringArray);

        Assistant.modifyFile(ORDER_FILEPATH,oldString,updatedString);
    }

    //for admin to calculate total revenue
    public void calculateTotalRevenue(){
        extractDatabase();

        long totalRevenue = 0;
        for(Order order: orders){
            totalRevenue+=order.getTotalPrice();
        }

        String priceFormat = String.format("The store total revenue is: %,d", totalRevenue);
        System.out.println(priceFormat + " VND");
    }


    //for admin to calculate revenue on specific date
    public void calculateDailyRevenue(String orderDate){
        extractDatabase();

        long totalRevenue = 0;
        for(Order order: orders){
            if(order.getOrderDate().equals(orderDate)){
                totalRevenue += order.getTotalPrice();
            }
        }
        String priceFormat = String.format("The store total revenue is: %,d", totalRevenue);
        System.out.println(priceFormat + " VND");
    }

    //check if the date is valid
    public boolean dateValidation(String orderDate){
        extractDatabase();
        boolean validateOrderDate = false;
        for(Order order:orders) {
            if (order.getOrderDate().equals(orderDate)) {
                validateOrderDate = true;
            }
        }
        return validateOrderDate;
    }

    //find most/least popular product
    public void findPopularProduct(){
        //Source: https://stackoverflow.com/questions/44367203/how-to-count-duplicate-elements-in-arraylist
        String[] productOrdered = Assistant.ReadCol(1, ORDER_FILEPATH,",");
        List<String> productOrderedList = Arrays.asList(productOrdered);

        //create and append key, values to Hashmap
        HashMap<String, Integer> map = new HashMap<>();
        for(String str : productOrderedList){
            if(map.containsKey(str)){
                map.put(str, map.get(str)+1);
            }
            else{
                map.put(str,1);
            }
        }

        //add product that has not been sold yet if existed.
        String[] productID = Assistant.ReadCol(0, PRODUCT_FILEPATH,",");
        for(String str : productID){
            if(!map.containsKey(str)){
                map.put(str, 0);
            }
        }

        //Return product with the highest sales
        //Source: https://stackoverflow.com/questions/5911174/finding-key-associated-with-max-value-in-a-java-map
        //return max value in map
        System.out.println("Most popular product are: ");
        int maxValueInMap = (Collections.max(map.values()));
        //Iterate through HashMap
        for(Map.Entry<String, Integer> entry:map.entrySet()){
            if(entry.getValue()==maxValueInMap){
                System.out.println("- " + entry.getKey() + ": " + entry.getValue());
            }
        }

        //Return product with the least sales
        System.out.println("Least popular product are: ");
        int minValueInMap = (Collections.min(map.values()));
        //Iterate through HashMap
        for(Map.Entry<String, Integer> entry:map.entrySet()){
            if(entry.getValue()==minValueInMap){
                System.out.println("- " + entry.getKey() + ": " + entry.getValue());
            }
        }
    }

    //get order by orderDate
    public void getOrderByDate(String orderDate){
        extractDatabase();
        System.out.println("Here are the order made on " + orderDate + ": ");
        System.out.println("- orderID, productID, customerID, customerMembership, totalPrice, orderStatus, orderDate");
        for(Order order : orders){
            if(order.getOrderDate().equals(orderDate)){
                String priceFormat = String.format("%,d", order.getTotalPrice());
                System.out.printf("- " + order.getOrderID() + "," + order.getProductID() + "," + order.getCustomerID() + "," + order.getCustomerMembership() + "," + priceFormat + "," + order.getOrderStatus() + "," + order.getOrderDate() + "\n");
            }
        }
    }

    public String getOrderID() {
        return orderID;
    }

    public String getProductID() {
        return productID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public String getCustomerMembership() {
        return customerMembership;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public String getOrderDate() {
        return orderDate;
    }

}
