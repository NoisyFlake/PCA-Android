package de.bk_alsdorf.pcaapp;

public class Data {
    private static double basalRate                 = 0.0;
    private static int cartridge                    = 50;
    private static int duration                     = 10;
    private static double ingredientQuantity        = 0.0;
    private static double dosage                    = 0.0;

    private static double bolusAmount               = 0.0;
    private static boolean bolusUnit                = false;
    private static int bolusLock                    = 0;
    private static int boliPerHour                  = 0;


    public static double getBasalRate()             { return basalRate; }
    public static int getCartridge()                { return cartridge; }
    public static int getDuration()                 { return duration;  }
    public static double getIngredientQuantity()    { return ingredientQuantity; }
    public static double getDosage()                { return dosage; }

    public static double getBolusAmount()           { return bolusAmount; }
    public static boolean getBolusUnit()            { return bolusUnit; }
    public static int getBolusLock()                { return bolusLock; }
    public static int getBoliPerHour()              { return boliPerHour; }


    public static void setBasalRate(double basalRate) { Data.basalRate = basalRate; }
    public static void setCartridge(int cartridge) {
        Data.cartridge = cartridge;
    }
    public static void setDuration(int duration) {
        Data.duration = duration;
    }
    public static void setIngredientQuantity(double ingredientQuantity) { Data.ingredientQuantity = ingredientQuantity; }
    public static void setDosage(double dosage) {
        Data.dosage = dosage;
    }

    public static void setBolusAmount(double bolusAmount) { Data.bolusAmount = bolusAmount; }
    public static void setBolusUnit(boolean bolusUnit) {
        Data.bolusUnit = bolusUnit;
    }
    public static void setBolusLock(int bolusLock) { Data.bolusLock = bolusLock; }
    public static void setBoliPerHour(int boliPerHour) { Data.boliPerHour = boliPerHour;
    }
}
