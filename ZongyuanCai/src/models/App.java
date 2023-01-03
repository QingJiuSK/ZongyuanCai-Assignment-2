package models;

import java.util.ArrayList;

public abstract class App {
     String developerName="";
     String developerWebsite="";

     private Developer developer = new Developer(developerName, developerWebsite);
     private String appName = "No app name";
     private double appSize = 0;
     private double appVersion = 1.0;
     private double appCost =0;

     private ArrayList<Rating>ratings = new ArrayList<> ();
     public App(Developer developer,String appName,double appSize,double appVersion,double appCost){
          setDeveloper(developer);
          setAppName(appName);
          setAppSize(appSize);
          setAppCost(appCost);
          setAppVersion(appVersion);
     }

     public boolean addRating(Rating rating){
          return  ratings.add(rating);
     }

     public ArrayList<Rating>getRatings(){return ratings;}

     public Developer getDeveloper() {
          return developer;
     }

     public String getDeveloperName(){return this.developerName;}

     public double getAppSize(){return  this.appSize;}

     public double getAppVersion(){return this.appVersion;}

     public double getAppCost(){return this.appCost;}

     public void setAppCost(double appCost) {
          this.appCost = appCost;
     }

     public String getAppName() {
          return appName;
     }

     public String getDeveloperWebsite() {
          return developerWebsite;
     }

     public void setAppName(String appName) {
          this.appName = appName;
     }

     public void setAppSize(double appSize) {
          this.appSize = appSize;
     }

     public void setAppVersion(double appVersion) {
          this.appVersion = appVersion;
     }

     public void setDeveloper(Developer developer) {
          this.developer = developer;
     }

     public void setDeveloperName(String developerName) {
          this.developerName = developerName;
     }

     public void setRatings(ArrayList<Rating> ratings) {this.ratings = ratings;}

     public String listRatings(){
          if (ratings.isEmpty()){return "No ratings added yet";}
          String str="";
          for (int i=0;i<ratings.size();i++){
               str+=ratings.get(i).getRatingComment();
          }
          return str;
     }

     public String appSummary(){
          return getAppName() + "(" + "V" + getAppVersion() + ")" + "by" + this.developer + "," + "$" + getAppCost() + getRatings();

     }

     public double calculateRating() {
          if (ratings.size() > 0) {
               double stars = 0;
               for (int i = 0; i < ratings.size(); i++) {
                    if (ratings.get(i).getNumberOfStars() > 0) {
                         stars += ratings.get(i).getNumberOfStars();
                    }
               }
               double avg= stars / ratings.size() ;
               return avg ;
          } else {
               return 0;
          }
     }

     public boolean isRecommendedApp(){
          if(calculateRating()>=3.5) {
               if (getAppCost()==0.99){return false;}
               else {
               return true;}
          }else {
               return false;
          }
     }

     public String toString() {
          return getAppName() + "(" + "Version" + getAppVersion() + ")" +"\n"
                  + "Developer: " + getDeveloper() + "\n"
                  + "Cost: " + getAppCost() + "\n"
                  +"Ratings: " + calculateRating() + "\n";
     }

}
