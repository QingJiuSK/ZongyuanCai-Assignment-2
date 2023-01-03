package models;

public class EducationApp extends App{

   int level=0;
    public EducationApp(Developer developer, String appName, double appSize, double appVersion, double appCost,int level) {
        super(developer, appName, appSize, appVersion, appCost);
        setLevel(level);
    }

   public void setLevel(int level){this.level=level;}
    public int getLevel(){return this.level;}

    public String toString(){
        return getAppName()+getAppCost() + "(Version " + getAppVersion() +"\n"
                +getAppSize() + "MB"+"\n"
                + "Developer: " + getDeveloper() + "\n"
                + "Cost: " + getAppCost() + "\n"
                +"Ratings (" + calculateRating() + "\n"
                +"Level: " + getLevel()+"\n"
                +getRatings().toString();
    }

    public String appSummary(){
        return getAppName() + "(V" +getAppVersion() + getDeveloper().toString()+ "level " +getLevel()+"€" + getAppCost()+ "." + "Rating: " + calculateRating();
    }





}
