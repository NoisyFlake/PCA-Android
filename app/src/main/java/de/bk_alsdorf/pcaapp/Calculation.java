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

        return ingredientQuantity / cartridge;
    }

    //Bolusmenge in ml = Basalrate * (1/Wirkstoffkonzentration)
    public static double convertBolusAmountMgToMl() {
        double basalRate = Data.getBasalRate();
        double dosage = Data.getDosage();

        if(dosage == 0) return 0;

        return basalRate * (1/dosage);
    }

    //Bolusmenge in mg = Bolusmenge in ml * Konzentration
    public static double convertBolusAmountMlToMg() {
        double bolusAmount = Data.getBolusAmount();
        double dosage = Data.getDosage();

        return bolusAmount * dosage;
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

    //Boli Pro Stunde = 60 / Bolussperrzeit
    public static int getBoliPerHour(){
        int bolusLock = Data.getBolusLock();
        if (bolusLock == 0) return 0;

        return 60/bolusLock;
    }

    // Minimale Laufzeit = Kassettenvolumen / ( Basalrate + ( Anzahl Boli pro Stunde * Wirkstoffmenge je Bolus ))
    /* public static BigDecimal getMinimumRuntime(BigDecimal bolusAmount, int bolusPerHour, BigDecimal basalRate, int tankVolume) {
        BigDecimal maxBoliAgentPerHour = bolusAmount.multiply(new BigDecimal(bolusPerHour));
        BigDecimal maxAgentPerHour = basalRate.add(maxBoliAgentPerHour);

        if (maxAgentPerHour.compareTo(new BigDecimal(0)) == 0) return new BigDecimal(0);
        return new BigDecimal(tankVolume).divide(maxAgentPerHour, 1, RoundingMode.HALF_UP);
    } */

}