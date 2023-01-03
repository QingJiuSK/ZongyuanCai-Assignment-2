package models;

public class GameApp extends App{
    private boolean isMultiplayer=false;

    public GameApp(Developer developer, String appName, double appSize, double appVersion, double appCost,boolean isMultiplayer) {
        super(developer, appName, appSize, appVersion, appCost);
        setMultiplayer(isMultiplayer);
    }
    public void setMultiplayer(boolean isMultiplayers){
        this.isMultiplayer=isMultiplayers;
    }
    public boolean getMultiplayer(){return this.isMultiplayer;}
    public boolean isRecommendedApp() {
        if (this.isMultiplayer = true && calculateRating() >= 4.0 ){
            return true;
        }else {
            return false;
        }
    }

    public String appSummary(){
        return getAppName() + "(" + "V" + getAppVersion() + ")" + "by" + getDeveloper() + "€" + getAppCost() + "." +"Rating: " + calculateRating();
    }



    public String toString(){
        return getAppName() + "(Version " + getAppVersion() +"\n"
                + getDeveloper().toString() + "\n"
                + "Cost: " + getAppCost() +"\n"
                + getAppSize() + "MB"+"\n"
                +"Ratings (" + calculateRating() + "\n"
                +"IsMultiplayer:" + this.isMultiplayer
                +getRatings().toString();
    }
}
