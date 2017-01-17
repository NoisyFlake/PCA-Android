package de.bk_alsdorf.pcaapp;

public class Calculation {

    // Wirkstoffmenge = Basalrate in mg/h * 24 * Laufzeit in Tagen
    public static double getIngredientQuantity() {
        double basalRate = Data.getBasalRate();
        int duration = Data.getDuration();

        return basalRate * (24 * duration);
    }

    // Wirkstoffkonzentration = Wirkstoffmenge / Kassettenvolumen
    public static double getDosage() {
        double ingredientQuantity = Data.getIngredientQuantity();
        int cartridge = Data.getCartridge();

        if (cartridge <= 0) return 0;

        return ingredientQuantity / cartridge;
    }

    // Basalrate in mg/h = Wirkstoffmenge / 24 / laufzeit
    public static double getBasalRate(){
        double ingredientQuantity = Data.getIngredientQuantity();
        int duration = Data.getDuration();

        return ingredientQuantity / 24 / duration;
    }

    // Bolussperrzeit = 60 / Boli Pro Stunde
    public static int getBolusLock(){
        int boliPerHour = Data.getBoliPerHour();
        if (boliPerHour == 0) return 0;

        return 60/boliPerHour;
    }

    // Boli Pro Stunde = 60 / Bolussperrzeit
    public static int getBoliPerHour(){
        int bolusLock = Data.getBolusLock();
        if (bolusLock == 0) return 0;

        return 60/bolusLock;
    }

    // Minimale Laufzeit = Kassettenvolumen / ( Basalrate + ( Anzahl Boli pro Stunde * Wirkstoffmenge je Bolus ))
    public static double getMinimumRuntime() {

        double bolusAmount = Data.getBolusAmount();
        int bolusPerHour = Data.getBoliPerHour();
        double basalRate = Data.getBasalRate();
        int tankVolume = Data.getCartridge();

        double maxBoliAgentPerHour = bolusAmount * bolusPerHour;
        double maxAgentPerHour = basalRate + maxBoliAgentPerHour;

        if (maxAgentPerHour == 0)return 0;

        return tankVolume/maxAgentPerHour;
    }

    //Basalrate in ml = Basalrate * (1 / Wirkstoffkonzentration)
    public static double convertMgToMl(double mg) {
        double dosage = Data.getDosage();
        if(dosage == 0) return 0;

        return mg * (1/dosage);
    }

    //Basalrate in mg = Basalrate in ml * Wirkstoffkonzentration
    public static double convertMlToMg(double ml) {
        double dosage = Data.getDosage();
        if(dosage == 0) return 0;

        return ml * dosage;
    }

}