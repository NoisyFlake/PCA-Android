package de.bk_alsdorf.pcaapp;

public class Data {
    private static String basalRate = "0"; // is also used as bolusAmount in mg
    private static String basalRateDisplay = "0"; // rounded value of basalRate
    private static String cartridge = "50";
    private static String duration = "10";
    private static String ingredientQuantity = "0";
    private static String dosage = "0";

    private static String bolusAmount = "0"; // ml ONLY
    private static String bolusAmountDisplay = "0"; // rounded value of bolusAmount
    private static String bolusUnit = "0";
    private static String bolusLock = "0";
    private static String boliPerHour = "0";

    public static String getBasalRate()             { return basalRate; }
    public static String getBasalRateDisplay()      { return basalRateDisplay; }
    public static String getCartridge()             { return cartridge; }
    public static String getDuration()              { return duration;  }
    public static String getIngredientQuantity()    { return ingredientQuantity; }
    public static String getDosage()                { return dosage; }

    public static String getBolusAmount()           { return bolusAmount; }
    public static String getBolusAmountDisplay()    { return bolusAmountDisplay; }
    public static String getBolusUnit()             { return bolusUnit; }
    public static String getBolusLock()             { return bolusLock; }
    public static String getBoliPerHour()           { return boliPerHour; }

    public static void setBasalRate(String basalRate) {
        if (basalRate.equals("")) basalRate = "0";
        Data.basalRate = basalRate;
    }

    public static void setBasalRateDisplay(String basalRateDisplay) {
        if (basalRateDisplay.equals("")) basalRateDisplay = "0";
        Data.basalRateDisplay = basalRateDisplay;
    }

    public static void setCartridge(String cartridge) {
        Data.cartridge = cartridge;
    }

    public static void setDuration(String duration) {
        Data.duration = duration;
    }

    public static void setIngredientQuantity(String ingredientQuantity) {
        if (ingredientQuantity.equals("")) ingredientQuantity = "0";
        Data.ingredientQuantity = ingredientQuantity;
    }

    public static void setDosage(String dosage) {
        Data.dosage = dosage;
    }

    public static void setBolusAmount(String bolusAmount) {
        if (bolusAmount.equals("")) bolusAmount = "0";
        Data.bolusAmount = bolusAmount;
    }

    public static void setBolusAmountDisplay(String bolusAmountDisplay) {
        if (bolusAmountDisplay.equals("")) bolusAmountDisplay = "0";
        Data.bolusAmountDisplay = bolusAmountDisplay;
    }

    public static void setBolusUnit(String bolusUnit) {
        Data.bolusUnit = bolusUnit;
    }

    public static void setBolusLock(String bolusLock) {
        if (bolusLock.equals("")) bolusLock = "0";
        Data.bolusLock = bolusLock;
    }

    public static void setBoliPerHour(String boliPerHour) {
        if (boliPerHour.equals("")) boliPerHour = "0";
        Data.boliPerHour = boliPerHour;
    }
}
