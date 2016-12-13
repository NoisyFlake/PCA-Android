package de.bk_alsdorf.pcaapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculation {

    // Wirkstoffmenge = Basalrate in mg/h * 24 * Laufzeit in Tagen
    public static BigDecimal getIngredientQuantity() {
        BigDecimal basalRate = new BigDecimal(Data.getBasalRate());
        BigDecimal duration = new BigDecimal(Data.getDuration());

        BigDecimal ingredientQuantity = basalRate.multiply(new BigDecimal(24)).multiply(duration);
        return ingredientQuantity.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    // Wirkstoffkonzentration = Wirkstoffmenge / Kassettenvolumen
    public static BigDecimal getDosage() {
        BigDecimal ingredientQuantity = new BigDecimal(Data.getIngredientQuantity());
        BigDecimal cartridge = new BigDecimal(Data.getCartridge());
        return ingredientQuantity.divide(cartridge, 1, RoundingMode.HALF_UP);
    }

    //Bolusmenge in ml = Basalrate * (1/Wirkstoffkonzentration)
    public static BigDecimal convertBolusAmountMgToMl() {
        BigDecimal basalRate = new BigDecimal(Data.getBasalRate());
        BigDecimal dosage = new BigDecimal(Data.getDosage());

        if(dosage.compareTo(BigDecimal.valueOf(0.0)) == 0) {
            return new BigDecimal(0);
        }

        BigDecimal one = new BigDecimal(1.0);
        BigDecimal bolusAmount = basalRate.multiply(one.divide(dosage,3,RoundingMode.HALF_UP));
        return bolusAmount.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    //Bolusmenge in mg = Bolusmenge in ml * Konzentration
    public static BigDecimal convertBolusAmountMlToMg() {
        BigDecimal bolusAmount = new BigDecimal(Data.getBolusAmount());
        BigDecimal dosage = new BigDecimal(Data.getDosage());

        BigDecimal basalRate = bolusAmount.multiply(dosage);
        return basalRate.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    // Basalrate in mg/h = Wirkstoffmenge / 24 / laufzeit
    public static BigDecimal getBasalRate(){
        BigDecimal ingredientQuantity = new BigDecimal(Data.getIngredientQuantity());
        BigDecimal duration = new BigDecimal(Data.getDuration());

        BigDecimal oneDay = new BigDecimal(24);

        BigDecimal divideHelp = ingredientQuantity.divide(oneDay, RoundingMode.HALF_UP);
        BigDecimal basalRate = divideHelp.divide(duration,1, RoundingMode.HALF_UP);

        return basalRate;
    };

    // Bolussperrzeit = 60 / Boli Pro Stunde
    public static BigDecimal getBolusLock(){
        BigDecimal oneHour = new BigDecimal(60);
        BigDecimal boliPerHour = new BigDecimal(Data.getBoliPerHour());
        BigDecimal bolusLock = oneHour.divide(boliPerHour,0,RoundingMode.HALF_UP);

        return bolusLock;
    };

    //Boli Pro Stunde = 60 / Bolussperrzeit
    public static BigDecimal getBoliPerHour(){
        BigDecimal oneHour = new BigDecimal(60);
        BigDecimal bolusLock = new BigDecimal(Data.getBolusLock());
        BigDecimal boliPerHour = oneHour.divide(bolusLock,0,RoundingMode.HALF_UP);

        return boliPerHour;
    };

    // Minimale Laufzeit = Kassettenvolumen / ( Basalrate + ( Anzahl Boli pro Stunde * Wirkstoffmenge je Bolus ))
    /* public static BigDecimal getMinimumRuntime(BigDecimal bolusAmount, int bolusPerHour, BigDecimal basalRate, int tankVolume) {
        BigDecimal maxBoliAgentPerHour = bolusAmount.multiply(new BigDecimal(bolusPerHour));
        BigDecimal maxAgentPerHour = basalRate.add(maxBoliAgentPerHour);

        if (maxAgentPerHour.compareTo(new BigDecimal(0)) == 0) return new BigDecimal(0);
        return new BigDecimal(tankVolume).divide(maxAgentPerHour, 1, RoundingMode.HALF_UP);
    } */

}