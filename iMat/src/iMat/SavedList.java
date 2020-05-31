package iMat;

import se.chalmers.cse.dat216.project.ShoppingItem;

import java.util.List;

public class SavedList {
    public List<ShoppingItem> items;
    public String description;

    public SavedList(List<ShoppingItem> items, String description){
        this.items = items;
        this.description = description;
    }
}
