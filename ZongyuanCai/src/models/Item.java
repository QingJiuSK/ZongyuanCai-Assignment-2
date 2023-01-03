package models;

import utils.Utilities;

public class Item {

    private boolean isItemCompleted = false;
    private String itemDescription = "No Description";

    public Item(String description){
        this.itemDescription=Utilities.truncateString(description,50);

    }
    public Item(String ItemDescription, boolean isItemComplete) {
        this.isItemCompleted = isItemComplete;
        this.itemDescription = Utilities.truncateString(ItemDescription, 50);
    }


    public boolean isItemCompleted() {
        return this.isItemCompleted;
    }

    public boolean isItemToDo() {
        if (isItemCompleted) {
            return false;
        }
        return true;
    }


    public void setItemDescription(String Description) {
        this.itemDescription = Description;
    }
    public void setItemCompleted(boolean itemCompleted) {

        this.isItemCompleted = itemCompleted;
    }


    public String getItemDescription() {
        return this.itemDescription;
    }


    public String toString() {
        return "note description: " + itemDescription+ ", currently in item line: " + Utilities.booleanToCT(isItemCompleted);
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return itemDescription == item.itemDescription && isItemCompleted == item.isItemCompleted;
    }
}

