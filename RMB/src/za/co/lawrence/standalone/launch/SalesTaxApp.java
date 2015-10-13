package za.co.lawrence.standalone.launch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SalesTaxApp {

    private static Map<String, Double> productAndPrices = null;
    private static List<String> productBasicTax = null;
    private static List<String> customerOrderList = null;

    /**
     * Class constructor
     */
    public SalesTaxApp() {
        productAndPrices = new HashMap<String, Double>();
        productBasicTax = new ArrayList<String>();
        customerOrderList = new ArrayList<String>();
    }

    /**
     * Main method.
     * 
     * @param args
     *            - JVM created arrays of string.
     */
    public static void main(String[] args) {

        SalesTaxApp tax = null;
        try {
            tax = new SalesTaxApp();
            tax.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start program execution.
     */
    private void start() {
        // Populate products and prices
        populateProductNamesAndPrices();

        // Products List with basic tax.
        populateListProductsWithBasicTax();

        // Get customer order
        orderInput();

        // Print everything on the MAP
        printMapValues();
    }

    /**
     * Method to populate product names and prices.
     */
    private static void populateProductNamesAndPrices() {

        productAndPrices.put("Book", 12.49);
        productAndPrices.put("Music CD", 14.99);
        productAndPrices.put("Chocolate Bar", 0.85);

        productAndPrices.put("Imported box of chocolates_10", 10.00);
        productAndPrices.put("Imported bottle of perfume_47", 47.50);

        productAndPrices.put("Imported bottle of perfume_27", 27.99);
        productAndPrices.put("Bottle of perfume", 18.99);
        productAndPrices.put("Packet of headache pills", 9.75);
        productAndPrices.put("Box of imported chocolates", 11.25);

    }

    /**
     * Populate list with products eligible for basic tax. 10 %
     */
    private static void populateListProductsWithBasicTax() {

        productBasicTax.add("Music CD");
        productBasicTax.add("Bottle of perfume");
        productBasicTax.add("Imported bottle of perfume");

    }

    private static void orderInput() {

        double tax = 0.0;
        double productPriceWithTax = 0.0;
        double totalAmount = 0.0;

            // First Order.
            customerOrderList.add("Music CD");
            customerOrderList.add("Book");
            customerOrderList.add("Chocolate Bar");
            // Second Order
            customerOrderList.add("Imported box of chocolates_10");
            customerOrderList.add("Imported bottle of perfume_47");
            // Third Order
            customerOrderList.add("Imported bottle of perfume_27");
            customerOrderList.add("Bottle of perfume");
            customerOrderList.add("Packet of headache pills");
            customerOrderList.add("Box of imported chocolates");

        // Print Customer List.
        printCustomerProductList(customerOrderList);

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            for (String customerProduct : customerOrderList) {
                if (customerProduct.equalsIgnoreCase(entry.getKey())) {
                    if (customerProduct.contains("Imported") || customerProduct.contains("imported")) {
                        if (customerProduct.contains("chocolates")) {
                            productPriceWithTax = calculateAddtionalProductWithImportedTax(customerProduct);
                            tax += calculateAddtionalProductImportTax(customerProduct);
                        } else {
                            productPriceWithTax = calculateAddtionalProductWithTax(customerProduct);
                            tax += calculateAddtionalProductTax(customerProduct);
                        }
                        printCalculatedTax(productPriceWithTax, customerProduct);
                        totalAmount += productPriceWithTax;

                    } else if (customerProduct == "Music CD" || customerProduct == "Bottle of perfume") {
                        productPriceWithTax = calculateBasicProductPrice(customerProduct);
                        tax += calculateBasicTax(customerProduct);
                        printCalculatedTax(productPriceWithTax, customerProduct);
                        totalAmount += productPriceWithTax;
                    } else {
                        productPriceWithTax = getdoubleDigitFormat(entry.getValue());
                        printCalculatedTax(productPriceWithTax, customerProduct);
                        totalAmount += productPriceWithTax;
                    }
                }
            }
        }

        // Prints the Sales tax.
        printSalesTax(tax);
        // Prints the total amount due.
        printTotalAmountDue(totalAmount);
        //Clear the amounts
    }

    /**
     * Prints the total amount due with tax.
     * 
     * @param productPriceWithTax
     * @param tax
     */
    private static void printTotalAmountDue(double totalAmount) {
        System.out.println("Total: " + getdoubleDigitFormat(totalAmount));
    }

    /**
     * Prints the tax of all the products, should it be there.
     * 
     * @param tax
     */
    private static void printSalesTax(double tax) {
        System.out.println("Sales Taxes: " + getdoubleDigitFormat(tax));
    }

    /**
     * Prints the product name with either updated price with tax included or
     * normal product price.
     * 
     * @param productPriceWithTax
     * @param customerProduct
     */
    private static void printCalculatedTax(double productPriceWithTax, String customerProduct) {

        String temp = customerProduct;
        if ((temp.contains("_"))) {
            int index = temp.indexOf("_");
            temp = temp.substring(0, index);
            System.out.println("1 " + temp + " : " + productPriceWithTax);
        } else {
            System.out.println("1 " + customerProduct + " : " + productPriceWithTax);
        }
    }

    /**
     * Calculates basic tax of a product.
     * 
     * @param productName
     */
    private static double calculateAddtionalProductWithImportedTax(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat((entry.getValue() * 1.05)));
            }
        }
        return 0.0;
    }

    /**
     * Calculates basic tax of a product.
     * 
     * @param productName
     */
    private static double calculateAddtionalProductWithTax(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat((entry.getValue() * 1.15)));
            }
        }
        return 0.0;
    }

    /**
     * Calculates basic tax of a product.
     * 
     * @param productName
     */
    private static double calculateAddtionalProductTax(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat(entry.getValue() * 0.15));
            }
        }
        return 0.0;
    }

    /**
     * Calculates basic tax of a product for Imported Products.
     * 
     * @param productName
     */
    private static double calculateAddtionalProductImportTax(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat(entry.getValue() * 0.05));
            }
        }
        return 0.0;
    }

    /**
     * Calculates basic tax of a product.
     * 
     * @param productName
     */
    private static double calculateBasicProductPrice(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat(entry.getValue() * 1.1));
            }
        }
        return 0.0;
    }

    /**
     * Calculates basic tax of a product.
     * 
     * @param productName
     */
    private static double calculateBasicTax(String productName) {

        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            if (productName.equalsIgnoreCase(entry.getKey())) {
                return (getdoubleDigitFormat(entry.getValue() * 0.1));
            }
        }
        return 0.0;

    }

    /**
     * Keeps correct format of the value.
     * 
     * @param d
     * @return
     */
    private static double getdoubleDigitFormat(double value) {

        return (double) Math.round(value * 100) / 100;
    }

    /**
     * Customer product list with prices before tax.
     * 
     * @param cust
     */
    private static void printCustomerProductList(List<String> cust) {

        System.out.println("================================");
        System.out.println("Customer Product List : ");
        System.out.println("================================");
        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            for (String custProdName : cust) {
                if (custProdName.equalsIgnoreCase(entry.getKey())) {
                    String temp = entry.getKey().toString();
                    if ((temp.contains("_"))) {
                        int index = temp.indexOf("_");
                        temp = temp.substring(0, index);
                        System.out.println("1 " + temp + " at " + entry.getValue());
                    } else {
                        System.out.println("1 " + custProdName + " at " + entry.getValue());
                    }
                }
            }
        }
        System.out.println();
        System.out.println("================================");
        System.out.println("Updated Customer Product List : ");
        System.out.println("================================");
    }

    /**
     * Prints the values of the map.
     */
    private static void printMapValues() {
        for (Map.Entry<String, Double> entry : productAndPrices.entrySet()) {
            System.out.println("Product : " + entry.getKey() + " R" + entry.getValue());
        }
    }
}
