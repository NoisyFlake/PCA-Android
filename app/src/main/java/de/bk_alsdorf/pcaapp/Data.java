package de.bk_alsdorf.pcaapp;

import java.math.BigDecimal;

public class Data {

    // ===== PHARMACY INITIAL VALUES ===== //
    private static double basalRate                     = 0.0;
    private static int cartridge                        = 50;
    private static int duration                         = 10;
    private static double ingredientQuantity            = 0.0;
    private static double dosage                        = 0.0;

    // ===== PUMP INITIAL VALUES ===== //
    private static boolean isBasalRateInMl              = true;
    private static int bolusLock                        = 0;
    private static double bolusAmount                   = 0.0;
    private static boolean isBolusAmountInMl            = false;
    private static int boliPerHour                      = 0;



    // ===== PHARMACY GETTERS ===== //
    public static double getBasalRate()                 { return basalRate; }
    public static int getCartridge()                    { return cartridge; }
    public static int getDuration()                     { return duration;  }
    public static double getIngredientQuantity()        { return ingredientQuantity; }
    public static double getDosage()                    { return dosage; }

    // ===== PUMP GETTERS ===== //
    public static double getBasalRateInMl()             { return round(Calculation.convertMgToMl(basalRate), 2); }
    public static boolean isBasalRateInMl()             { return isBasalRateInMl; }
    public static double getBolusAmount()               { return bolusAmount; }
    public static double getBolusAmountInMl()           { return round(Calculation.convertMgToMl(bolusAmount), 2); }
    public static boolean isBolusAmountInMl()           { return isBolusAmountInMl; }
    public static int getBolusLock()                    { return bolusLock; }
    public static int getBoliPerHour()                  { return boliPerHour; }
    public static double getIngredientQuantityPerDay()  { return round(basalRate * 24, 1); }
    public static double getMinimalRuntime()            { return round(Calculation.getMinimumRuntime(), 1); }


    // ===== PHARMACY SETTERS ===== //
    public static void setBasalRate(double basalRate) {
        Data.basalRate = basalRate;
        ingredientQuantity = round(Calculation.getIngredientQuantity(), 1);
        dosage = round(Calculation.getDosage(), 2);
    }

    public static void setCartridge(int cartridge) {
        Data.cartridge = cartridge;
        dosage = round(Calculation.getDosage(), 2);
    }

    public static void setDuration(int duration) {
        Data.duration = duration;
        ingredientQuantity = round(Calculation.getIngredientQuantity(), 1);
        basalRate = round(Calculation.getBasalRate(), 1);
        dosage = round(Calculation.getDosage(), 2);
    }

    public static void setIngredientQuantity(double ingredientQuantity) {
        Data.ingredientQuantity = ingredientQuantity;
        basalRate = round(Calculation.getBasalRate(), 1);
        dosage = round(Calculation.getDosage(), 2);
    }


    // ===== PUMP SETTERS ===== //
    public static void setIsBasalRateInMl(boolean isBasalRateInMl) {
        Data.isBasalRateInMl = isBasalRateInMl;
    }

    public static void setBolusAmount(double bolusAmount) { Data.bolusAmount = bolusAmount; }

    public static void setBolusLock(int bolusLock) {
        Data.bolusLock = bolusLock;
        boliPerHour = Calculation.getBoliPerHour();
    }

    public static void setIsBolusAmountInMl(boolean isBolusAmountInMl) {
        Data.isBolusAmountInMl = isBolusAmountInMl;
    }

    public static void setBoliPerHour(int boliPerHour) {
        Data.boliPerHour = boliPerHour;
        bolusLock = Calculation.getBolusLock();
    }

    public static void setIngredientQuantityPerDay(double ingredientQuantityPerDay) {
        Data.basalRate = round(ingredientQuantityPerDay / 24, 1);
    }


    // ===== AUXILIARY FUNCTIONS ===== //
    private static double round(double d, int decimalPlace) {
        BigDecimal bd = new BigDecimal(Double.toString(d));
        bd = bd.setScale(decimalPlace, BigDecimal.ROUND_HALF_UP);
        return bd.doubleValue();
    }
}
