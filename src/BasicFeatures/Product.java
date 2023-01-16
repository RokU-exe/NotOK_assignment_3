/*
  RMIT University Vietnam
  Course: COSC2081 Programming 1
  Semester: 2022C
  Assessment: Assignment 3
  Author: Group Not Ok
  ID: s3963227, s3932184, s3955317, s3866724
  Acknowledgement:
    - viewAllProductSort(): https://www.youtube.com/watch?v=wzWFQTLn8hI
    - viewCategorySort(): https://www.youtube.com/watch?v=wzWFQTLn8hI
*/

package BasicFeatures;

import Bot.Assistant;

import java.util.*;

public class Product {
    private String id;
    private String title;
    private long price;
    private String category;

    private static String tableFormat = "%-20s%-30s%-20s%-20s\n";
    private final String PRODUCT_FILEPATH = "src/Database/items.txt";

    ArrayList<Product> products = new ArrayList<>();

    public Product(){

    }

    public Product(String id, String title, long price, String category) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
    }


    //view all product, allow sorting by price range
    public void viewAllProductSort(String sortOrder){
        extractDatabase();

        printProductDetailHeader();

        //Source: https://www.youtube.com/watch?v=wzWFQTLn8hI
        if(sortOrder.equalsIgnoreCase("ascending")){
            Collections.sort(products, new Comparator<Product>()
            {
                public int compare(Product p1, Product p2){
                    return Long.valueOf(p1.price).compareTo(p2.price);
                }
            });
            for(int i = 0; i < products.size(); i++){
                String priceFormat = String.format("%,d", products.get(i).getPrice());
                System.out.printf(tableFormat, products.get(i).getId(), products.get(i).getTitle(), priceFormat + " VND", products.get(i).getCategory());
            }
        }
        else if(sortOrder.equalsIgnoreCase("descending")){
            Collections.sort(products, new Comparator<Product>()
            {
                public int compare(Product p1, Product p2){
                    return Long.valueOf(p2.price).compareTo(p1.price);
                }
            });
            for(int i = 0; i < products.size(); i++){
                String priceFormat = String.format("%,d", products.get(i).getPrice());
                System.out.printf(tableFormat, products.get(i).getId(), products.get(i).getTitle(), priceFormat + " VND", products.get(i).getCategory());
            }
        }
        else{
            for(Product product:products){
                String priceFormat = String.format("%,d", product.getPrice());
                System.out.printf(tableFormat, product.getId(), product.getTitle(), priceFormat + " VND", product.getCategory());
            }
        }

        printTableBottomBorder();
    }

    //view all product within specific category
    public void viewCategorySort(String category, String sortOrder) {
        extractDatabase();

        ArrayList<Product> categorizedProduct = new ArrayList<>();

        for(Product product: products){
            if(product.getCategory().equalsIgnoreCase(category)){
                categorizedProduct.add(product);
            }
        }

        printProductDetailHeader();

        //Source: https://www.youtube.com/watch?v=wzWFQTLn8hI
        if(sortOrder.equalsIgnoreCase("ascending")){
            Collections.sort(categorizedProduct, new Comparator<Product>()
            {
                public int compare(Product p1, Product p2){
                    return Long.valueOf(p1.price).compareTo(p2.price);
                }
            });
            for(int i = 0; i < categorizedProduct.size(); i++){
                String priceFormat = String.format("%,d", categorizedProduct.get(i).getPrice());
                System.out.printf(tableFormat, categorizedProduct.get(i).getId(), categorizedProduct.get(i).getTitle(), priceFormat + " VND", categorizedProduct.get(i).getCategory());
            }
        }
        else if(sortOrder.equalsIgnoreCase("descending")){
            Collections.sort(categorizedProduct, new Comparator<Product>()
            {
                public int compare(Product p1, Product p2){
                    return Long.valueOf(p2.price).compareTo(p1.price);
                }
            });
            for(int i = 0; i < categorizedProduct.size(); i++){
                String priceFormat = String.format("%,d", categorizedProduct.get(i).getPrice());
                System.out.printf(tableFormat, categorizedProduct.get(i).getId(), categorizedProduct.get(i).getTitle(), priceFormat + " VND", categorizedProduct.get(i).getCategory());
            }
        }
        else{
            for(Product product:categorizedProduct){
                String priceFormat = String.format("%,d", product.getPrice());
                System.out.printf(tableFormat, product.getId(), product.getTitle(), priceFormat + " VND", product.getCategory());
            }
        }

        printTableBottomBorder();
    }

    //view product within a price range
    public void viewPriceRangeSort(){
        extractDatabase();

        ArrayList<Product> priceRangeSort = new ArrayList<>();
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the MINIMUM price (Ex: 3000000): ");
        int min = keyboard.nextInt();
        System.out.print("Enter the MAXIMUM price (Ex: 5000000): ");
        int max = keyboard.nextInt();

        if(min <= max){
            printProductDetailHeader();
            for(Product product : products){
                if(product.getPrice() >= min && product.getPrice() <= max){
                    priceRangeSort.add(product);
                }
            }

            for(Product product:priceRangeSort ){
                String priceFormat = String.format("%,d", product.getPrice());
                System.out.printf(tableFormat, product.getId(), product.getTitle(), priceFormat + " VND", product.getCategory());
            }

            printTableBottomBorder();
            System.out.println();
        }
        else{
            System.out.println("Minimum price cannot be smaller than maxmimum price.");
            viewPriceRangeSort();
        }
    }

    //print table header and top border
    public static void printProductDetailHeader(){
        System.out.println();
        for(int i = 0; i <=90;i++){
            if(i == 35){
                System.out.print(" Product Detail ");
            }
            if(i < 35 || i > 50){
                System.out.print("-");
            }
        }
        System.out.println();
        System.out.printf(tableFormat,"Product ID", "Title", "Price", "Catetory");
        printTableBottomBorder();
    }

    //print table bottom border
    public static void printTableBottomBorder(){
        for(int i = 0; i <=90;i++){
            System.out.print("-");
        }
        System.out.println();
    }

    //get all available category
    public String getCategoryList(){
        String[] category = Assistant.ReadCol(3,PRODUCT_FILEPATH, ",");
        ArrayList<String> categoryList = new ArrayList<String>();


        for(int i = 1; i < category.length; i++){
            if(categoryList.contains(category[i]) == false){
                categoryList.add(category[i]);
            }
        }
        String list = Assistant.arrayListToCSVString(categoryList);
        return list;
    }

    //get product by productID
    public Product getProduct(String productID){
        extractDatabase();

        for(Product product:products){
            if(product.id.equals(productID)){
                return product;
            }
        }

        return null;
    }

    //allow admin to update product price
    public void updateProductPrice(String productID, long newPrice){
        extractDatabase();
        Product product = getProduct(productID);

        String oldContent = product.getId() + "," + product.getTitle() + "," + product.getPrice() + "," + product.getCategory();

        String[] updateProductPrice = oldContent.split(",");
        String updatedContent = "";

        updateProductPrice[2] = String.valueOf(newPrice);
        updatedContent = Assistant.arrayToCSVString(updateProductPrice);
        Assistant.modifyFile(PRODUCT_FILEPATH, oldContent, updatedContent);
    }

    //display product info
    public void printProductInfo(String productID){
        extractDatabase();
        for(Product product:products){
            if(product.id.equals(productID)){
                System.out.println("- ProductID: " + product.id);
                System.out.println("- Title: " + product.title);
                System.out.printf("- Price (in VND): %,d\n", product.price);
                System.out.println( "- Category: " + product.category);
            }
        }
    }

    //validate productID
    public boolean validateProductID(String productID){
        extractDatabase();
        boolean validateProductID = false;
        for(Product product:products) {
            if (product.getId().equals(productID)) {
                validateProductID = true;
            }
        }
        return validateProductID;
    }

    //extract data from database when there are updates and add object to arraylist
    public void extractDatabase(){
        //empty an ArrayList to make sure no info was repeated
        products.clear();

        int countLine = Assistant.countLine(PRODUCT_FILEPATH);
        String[] id = Assistant.ReadCol(0, PRODUCT_FILEPATH,",");
        String[] title = Assistant.ReadCol(1, PRODUCT_FILEPATH,",");
        String[] price = Assistant.ReadCol(2, PRODUCT_FILEPATH,",");
        String[] category = Assistant.ReadCol(3, PRODUCT_FILEPATH,",");

        for(int i = 0; i < countLine - 1; i++){
            products.add(new Product(id[i], title[i], Long.parseLong(price[i]), category[i]));
        }
    }

    //allow admin to update category for specific product
    public void updateCategory() {
        extractDatabase();

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the product ID your wanted to update: ");
        String productId = keyboard.nextLine();
        System.out.print("Enter the new category: ");
        String newCategory = keyboard.nextLine();

        Product product = getProduct(productId);

        String oldContent = product.getId() + "," + product.getTitle() + "," + product.getPrice() + "," + product.getCategory();

        String[] updateProductPrice = oldContent.split(",");
        String updatedContent = "";

        updateProductPrice[3] = String.valueOf(newCategory);
        updatedContent = Assistant.arrayToCSVString(updateProductPrice);
        Assistant.modifyFile(PRODUCT_FILEPATH, oldContent, updatedContent);
        System.out.println("Product category updated!");

    }

    //allow admin to remove category, set new category to None
    public void removeCategory(){
        Scanner keyboard = new Scanner(System.in);
        System.out.print("Enter the category you want to remove: ");
        String oldCategory = keyboard.nextLine();

        Assistant.modifyFile(PRODUCT_FILEPATH, oldCategory, "None");
        System.out.println("Category removed!");

    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public long getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }


}
