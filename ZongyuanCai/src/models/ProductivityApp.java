package models;

public class ProductivityApp extends App{
    public ProductivityApp(Developer developer,String appName,double appSize,double appVersion,double appCost){
        super(developer,appName,appSize,appVersion,appCost);

    }
    public boolean isRecommendedApp(){
        if (getAppCost() > 1.99 && calculateRating() > 3.0){
            return true;
        }else {
            return false;
        }
    }

    public String appSummary(){
        return      getAppName() + "(V" +getAppVersion()+"\n"
                    +getDeveloper().toString()+"\n"
                    +"€" + getAppCost()+"\n"
                    +"Rating: " + calculateRating();
    }


    public String toString() {
        return getAppName() + "(Version " + getAppVersion() +"\n"
                + getDeveloper().toString() + "\n"
                + "Cost: " + getAppCost() +"\n"
                + getAppSize() + "MB"+"\n"
                +"Ratings (" + calculateRating() + "\n"
                +getRatings().toString();
    }
}