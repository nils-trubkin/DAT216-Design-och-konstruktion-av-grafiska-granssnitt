/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iMat;

/**
 *
 * @author oloft
 */

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Wrapper around the IMatDataHandler. The idea is that it might be useful to
 * add an extra layer that can provide special features
 *
 */
public class Model {

    private static Model instance = null;
    private IMatDataHandler iMatDataHandler;

     private final ArrayList<String> availableCardTypes = new ArrayList<String>(Arrays.asList("MasterCard", "Visa"));
     private final ArrayList<String> months = new ArrayList<String>(Arrays.asList("1", "2","3", "4", "5", "6"));
     private final ArrayList<String> years = new ArrayList<String>(Arrays.asList("19", "20", "21", "22", "23", "24", "25"));
    /**
     * Constructor that should never be called, use getInstance() instead.
     */
    protected Model() {
        // Exists only to defeat instantiation.
    }

    /**
     * Returns the single instance of the Model class.
     */
    public static Model getInstance() {
        if (instance == null) {
            instance = new Model();
            instance.init();
        }
        return instance;
    }

    private void init() {

        iMatDataHandler = IMatDataHandler.getInstance();

    }

    public List<Product> getProducts() {
        return iMatDataHandler.getProducts();
    }

    public List<Product> getProducts(ProductCategory pc) {
        return iMatDataHandler.getProducts(pc);
    }

    public Product getProduct(int idNbr) {
        return iMatDataHandler.getProduct(idNbr);
    }
    
    public List<Product> findProducts(String s) {
        return iMatDataHandler.findProducts(s);
    }

    public Image getImage(Product p) {
        return iMatDataHandler.getFXImage(p);
    }

    public Image getImage(Product p, double width, double height) {
        return iMatDataHandler.getFXImage(p, width, height);
    }

    public void addToShoppingCart(Product p) {
        ShoppingItem item = new ShoppingItem(p);
        for(ShoppingItem si : Model.getInstance().getShoppingCart().getItems()){
            if (si.getProduct().equals(p)){
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    si.setAmount(si.getAmount() + 0.1);
                }
                else{
                    si.setAmount(si.getAmount() + 1);
                }
                Model.getInstance().getShoppingCart().fireShoppingCartChanged(si, true);
                return;
            }
        }
        Model.getInstance().getShoppingCart().addProduct(p);
    }

    public void subtractFromShoppingCart(Product p) {
        ShoppingItem item = new ShoppingItem(p);
        for(ShoppingItem si : Model.getInstance().getShoppingCart().getItems()){
            if (si.getProduct().equals(p)){
                if(si.getProduct().getUnitSuffix().equals("kg")){
                    if (si.getAmount() > 0.11) {
                        si.setAmount(si.getAmount() - 0.1);
                        Model.getInstance().getShoppingCart().fireShoppingCartChanged(si, true);
                    }
                    else {
                        Model.getInstance().getShoppingCart().removeItem(si);
                    }
                    return;
                }
                else {
                    if (si.getAmount() > 1) {
                        si.setAmount(si.getAmount() - 1);
                        Model.getInstance().getShoppingCart().fireShoppingCartChanged(si, true);
                    } else {
                        Model.getInstance().getShoppingCart().removeItem(si);
                    }
                    return;
                }
            }
        }
    }

    public List<String> getCardTypes() {
        return availableCardTypes;
    }
    
    public List<String> getMonths() {
        return months;
    }
    
    public List<String> getYears() {
        return years;
    }
    
    public CreditCard getCreditCard() {
        return iMatDataHandler.getCreditCard();
    }
    
    public Customer getCustomer() {
        return iMatDataHandler.getCustomer();
    }

    public ShoppingCart getShoppingCart() {
        return iMatDataHandler.getShoppingCart();
    }

    public void clearShoppingCart() {

        iMatDataHandler.getShoppingCart().clear();

    }

    public void placeOrder() {

        iMatDataHandler.placeOrder();

    }

    
    public int getNumberOfOrders() {

        return iMatDataHandler.getOrders().size();

    }

    public void shutDown() {
        iMatDataHandler.shutDown();
    }
}
