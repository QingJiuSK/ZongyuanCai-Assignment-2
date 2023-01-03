package controllers;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import models.*;
import utils.Utilities;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;

import static utils.RatingUtility.generateRandomRating;

public class AppStoreAPI {


    //TODO refer to the spec and add in the required methods here (make note of which methods are given to you first!)

    //---------------------
    // Method to simulate ratings (using the RatingUtility).
    // This will be called from the Driver (see skeleton code)
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED method as you start working through this class
    //---------------------

    public ArrayList<App>apps =new ArrayList<>();

    public void simulateRatings(){
        for (App app :apps) {
            app.addRating(generateRandomRating());
        }
    }
    /*
     * This method will add an App object (passed as a parameter) to the ArrayList Apps.
     * There is no validation in this method. Returns true if the app was added and false if not.
     * */
    public boolean addApp(App app){
        return apps.add(app);
    }

     /*
     This method returns an App object at the location index, which is passed as a parameter.
     get the app according to the index passed in
      */
    public App getAppByIndex(int index){
        return apps.get(index);
    }

    /*
    set apps by enter the special apps
     */
    public void setApps(ArrayList<App> apps) {
        this.apps = apps;
    }

    //get all the number of apps
    public int numberOfApps(){
        return apps.size();
    }

     //list all the apps if the ArrayList is empty, prints "No apps"
    public String listAllApps(){
        String str = "";
        if (apps.isEmpty()){
            return "No apps";
        }else {
            for (int i = 0; i < apps.size(); i++) {
                str += apps.get(i).toString();
            }
            return str;
        }
    }

    //This method  return a String containing the summary of all the apps in apps
    // The index number of each app should be included in the string too. If no apps exist yet, "No apps" should be returned.
    public String listSummaryOfAllApps(){
        String str = "";
        if (apps.isEmpty()){
            return "No apps";
        }else {
            for (int i = 0; i < apps.size(); i++) {
                str += findApp(i).appSummary();
            }
            return str;
        }
    }

    //list all game If the arrayList is empty or there are no Game apps, print"No Game apps"
    public String listAllGameApp(){
        String str="";
        for (App app : apps){
            if (app instanceof GameApp){
                str += apps.indexOf(app) + ": " + app.appSummary() + "\n";
            }
        }
        if (str.isEmpty()){
            return "No Game apps";
        }else {
            return str;
        }
    }

    //list all Education apps，if there is no Education apps ,print "no Education apps".
    public String listAllEducationApps(){
        String str = "";
        for (App app : apps){
            if (app instanceof EducationApp){
                str += apps.indexOf(app) + ": " + app.appSummary() + "\n";
            }
        }
        if (str.isEmpty()){
            return "No Education apps";
        }else {
            return str;
        }
    }

    //list all Productivity apps，if there is no Education apps ,print "no Productivity apps".
    public String listAllProductivityApps(){
        String str = "";
        for (App app : apps){
            if (app instanceof ProductivityApp){
                str += apps.indexOf(app) + ": " + app.appSummary() + "\n";
            }
        }
        if (str.isEmpty()){
            return "no productivity apps";
        }else {
            return str;
        }
    }
    //This method return a String containing the details of all the apps in apps which have a rating equal to or above the rating passed as a parameter.
    // If no such apps exist, "No apps have a rating of " + rating + " or above" should be returned
    public String listAllAppsAboveOrEqualAGivenStarRating(int rating){
        String str="";
            if (Utilities.validRange(rating,1,5)){
                for (int i=0;i<apps.size();i++){
                    if (findApp(i).calculateRating()>=rating){
                     str+=findApp(i).toString();
                    }
                }
            }
            if (str.isEmpty()){return "No apps have a rating of " + rating + " or above";}
            else {
                return str;
            }
    }

    /*
    This method removes an App object at the location index, which is passed as a parameter.
     */
    public App deleteAppByIndex(int indexToDelete){
        if (isValidIndex( indexToDelete)){return apps.remove(indexToDelete);}
        return null;
    }

    public ArrayList<App> getApps() {
        return apps;
    }

    /*
       This method returns an App object with that exact name (ignoring case), which is passed as a parameter
     */
    public App getAppByName(String name){
        if (name == null){
            return null;
        }
        for (App app : apps){
            if (app.getAppName().equalsIgnoreCase(name)){
                return app;
            }
        }
        return null;
    }

    //This method return the list of Apps whose appName contains the name  passed as a paramenter.
    // If no such apps exist, No apps for name "appName" exists.
    public String listAllAppsByName(String name){
        String str="";
        for (int i=0;i<apps.size();i++){
            if (getAppByIndex(i).getAppName().equalsIgnoreCase(name)){
                str+=getAppByIndex(i).getAppName();
            }
        }
        if (getAppByName(name)==null){return "No apps for name" + name + "exists";}
        else {
            return str;
        }
    }

    //This method searches through the apps collection for the apps whose developer field matches the developer object (given as parameter).
    // If no such apps exist, "No apps for developer: " + developer should be returned.
    public String listAllAppsByChosenDeveloper(String developername){
        String str="";
        for (int i=0;i<apps.size();i++){
              if (getAppByIndex(i).getDeveloperName()==developername){
                  str+=getAppByIndex(i).getAppName();
              }
        }if (str.isEmpty()){
            return "No apps for developer:"+developername;}
        else {
        return str;}
    }

    //get the number of one specific developer's apps
    public int numberOfAllAppsByChosenDeveloper(String developername){
        int total=0;
        for (int i=0;i<apps.size();i++){
            if (getAppByIndex(i).getDeveloperName()==developername){
                total++;
            }
       }
        return total;
    }

    //This return a String containing all the apps that are recommended (according to the specific sub-class algorithms).
    // If no such apps exist, "No recommended apps" should be returned.
    public String listAllRecommendedApps(){
        boolean y=true;
        String str="";
        for (int i=0;i<apps.size();i++){
            if (apps.get(i).isRecommendedApp()){
                str+=getAppByIndex(i).getAppName();
                y=false;
            }
        }
        if (y){return "No recommended apps" ;}
        else {
            return str;
        }
    }

   //return a random App from the collection of apps. If no apps are in the collection, null should be returned.
    public App randomApp(){
        Random r = new Random();
        int num = r.nextInt(apps.size());
        return getAppByIndex(num);
    }

    public App findApp(int index){
        if (isValidIndex(index)){
            return apps.get(index);
        }
        return null;
    }

    //search the Specific apps according to the name entered
    public App searchAppsByName(String name){
         for (int i=0;i<apps.size();i++){
             if(findApp(i).getAppName().contains(name)){
               return findApp(i);
             }
         }
        return null;
    }



    //---------------------
    // Validation methods
    //---------------------
    // TODO UNCOMMENT THIS COMPlETED method as you start working through this class
    //---------------------

    public boolean isValidIndex(int index) {
        return (index >= 0) && (index < apps.size());
    }

    public boolean isValidAppName(String appName){
        for (App app : apps){
            if (app.getAppName().equals(appName))
                return true;
        }
        return false;
    }

    public boolean updateEducationApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, int level) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof EducationApp)) {
            ((EducationApp) foundApp).setDeveloper(developer);
            ((EducationApp) foundApp).setAppName(appName);
            ((EducationApp) foundApp).setAppCost(appCost);
            ((EducationApp) foundApp).setAppVersion(appVersion);
            ((EducationApp) foundApp).setAppSize(appSize);
            ((EducationApp) foundApp).setLevel(level);
            return true;
        }
        return false;
    }

    public boolean updateGameApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost, boolean isMultiplayer) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof GameApp)) {
            ((GameApp) foundApp).setDeveloper(developer);
            ((GameApp) foundApp).setAppName(appName);
            ((GameApp) foundApp).setAppSize(appSize);
            ((GameApp) foundApp).setAppCost(appCost);
            ((GameApp) foundApp).setAppVersion(appVersion);
            ((GameApp) foundApp).setMultiplayer(isMultiplayer);
            return true;
        }
        return false;
    }

    public boolean updateProductivityApp(int indexToUpdate, Developer developer, String appName, double appSize, double appVersion, double appCost) {
        App foundApp = findApp(indexToUpdate);
        if ((foundApp != null) && (foundApp instanceof ProductivityApp)) {
            ((ProductivityApp) foundApp).setAppName(appName);
            ((ProductivityApp) foundApp).setAppSize(appSize);
            ((ProductivityApp) foundApp).setAppVersion(appVersion);
            ((ProductivityApp) foundApp).setAppCost(appCost);
            ((ProductivityApp) foundApp).setDeveloper(developer);
            return true;
        }
        return false;
    }


    //This method should change the apps object so that it is sorted by name in ascending order.
    public void sortAppsByNameAscending() {
        for (int i = apps.size() - 1; i >= 0; i--) {
            int hIndex = 0;
            for (int j = 0; j <= i; j++) {
                if (apps.get(j).getAppName().compareTo(apps.get(hIndex).getAppName()) > 0) {
                    hIndex = j;
                }
            }
            swapApps(apps, i, hIndex);
        }
    }


    //This should be a private method that swaps the objects at positions i and j in the collection apps.
    // This method should be used in your sorting method.
    private void swapApps(ArrayList<App> apps, int i, int j) {
        App a = apps.get(i);
        App b = apps.get(j);
        apps.set(i, a);
        apps.set(j, b);
    }




    //---------------------
    // Persistence methods
    //---------------------
    // TODO UNCOMMENT THIS COMPLETED CODE block as you start working through this class
    //---------------------

    @SuppressWarnings("unchecked")
    public void load() throws Exception {
        //list of classes that you wish to include in the serialisation, separated by a comma
        Class<?>[] classes = new Class[]{App.class, EducationApp.class, GameApp.class, ProductivityApp.class, Rating.class};

        //setting up the xstream object with default security and the above classes
        XStream xstream = new XStream(new DomDriver());
        XStream.setupDefaultSecurity(xstream);
        xstream.allowTypes(classes);

        //doing the actual serialisation to an XML file
        ObjectInputStream in = xstream.createObjectInputStream(new FileReader(fileName()));
        apps = (ArrayList<App>)  in.readObject();
        in.close();
    }

    public void save() throws Exception {
        XStream xstream = new XStream(new DomDriver());
        ObjectOutputStream out = xstream.createObjectOutputStream(new FileWriter(fileName()));
        out.writeObject(apps);
        out.close();
    }

    public String fileName(){
        return "apps.xml";
    }

}
