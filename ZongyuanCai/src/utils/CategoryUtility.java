package utils;

import java.util.*;


public class CategoryUtility {

    private static ArrayList<String> categories = new ArrayList<>(){{
        add("Home");
        add("Work");
        add("Hobby");
        add("Holiday");
        add("College");
    }};
    public static ArrayList<String> getCategories() {
        return categories;
    }
    public static boolean isValidCategory(String category) {
        //must not be
        for (String cat: categories){
            if (cat.equalsIgnoreCase(category)) {
                return true;
            }
        }
        return false;
    }
}
