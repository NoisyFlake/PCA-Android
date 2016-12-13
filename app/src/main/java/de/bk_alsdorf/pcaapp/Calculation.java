package de.bk_alsdorf.pcaapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculation {

    // Wirkstoffmenge = Basalrate in mg/h * 24 * Laufzeit in Tagen
    public static BigDecimal getAgentAmountPerTank(BigDecimal agentPerHour, int runtime) {
        BigDecimal agentAmountPerTank = agentPerHour.multiply(new BigDecimal(24)).multiply(new BigDecimal(runtime));
        return agentAmountPerTank.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    // Basalrate = Basalrate in mg/h * Wirkstoffkonzentration
    public static BigDecimal getBasalRate(BigDecimal agentPerHour, BigDecimal concentration) {
        BigDecimal basalRate = agentPerHour.multiply(concentration);
        return basalRate.setScale(1, BigDecimal.ROUND_HALF_UP);
    }

    // Wirkstoffkonzentration = Wirkstoffmenge / Kassettenvolumen
    public static BigDecimal getConcentration(BigDecimal agentAmount, int tankVolume) {
        return agentAmount.divide(new BigDecimal(tankVolume), 1, RoundingMode.HALF_UP);
    }

    // Minimale Laufzeit = Kassettenvolumen / ( Basalrate + ( Anzahl Boli pro Stunde * Wirkstoffmenge je Bolus ))
    public static BigDecimal getMinimumRuntime(BigDecimal bolusAmount, int bolusPerHour, BigDecimal basalRate, int tankVolume) {
        BigDecimal maxBoliAgentPerHour = bolusAmount.multiply(new BigDecimal(bolusPerHour));
        BigDecimal maxAgentPerHour = basalRate.add(maxBoliAgentPerHour);

        if (maxAgentPerHour.compareTo(new BigDecimal(0)) == 0) return new BigDecimal(0);
        return new BigDecimal(tankVolume).divide(maxAgentPerHour, 1, RoundingMode.HALF_UP);
    }

    // Bolusmenge in mg = Basalrate
    public static BigDecimal convertBolusAmountMlToMg(BigDecimal agentPerHour, BigDecimal agentAmount, int tankVolume) {
        BigDecimal concentration = getConcentration(agentAmount, tankVolume);
        return getBasalRate(agentPerHour, concentration);
    }

    //Bolusmenge in ml = Basalrate * (1/Wirkstoffkonzentration)
    public static BigDecimal convertBolusAmountMgToMl(BigDecimal agentPerHour, BigDecimal agentAmount, int tankVolume) {
        BigDecimal concentration = getConcentration(agentAmount, tankVolume);

        BigDecimal one = new BigDecimal(1.0);
        if(concentration.compareTo(BigDecimal.valueOf(0.0)) == 0) {
            return agentPerHour;
        }
        return agentPerHour.multiply(one.divide(concentration,3,RoundingMode.HALF_UP));
    }

    // Basalrate = Bolusmenge in ml / (1/Wirkstoffkonzentration)
    public static BigDecimal convertBasalrateFromBolusAmountMl(BigDecimal bolusAmount, int tankVolume) {
        BigDecimal concentration = getConcentration(bolusAmount, tankVolume);
        BigDecimal one = new BigDecimal(1.0);
        if(concentration.compareTo(BigDecimal.valueOf(0.0)) == 0) {
            concentration = one.divide(one, 1, RoundingMode.HALF_UP);
            return bolusAmount.divide(concentration);
        } else {
            concentration = one.divide(concentration, 1, RoundingMode.HALF_UP);
            return bolusAmount.divide(concentration);
        }
    }

    // Basalrate in mg/h = Wirkstoffmenge / 24 / laufzeit
    public static BigDecimal getAgentPerHour(BigDecimal agentAmountPerTank, int runtime){
        BigDecimal running = new BigDecimal(runtime);
        BigDecimal daytime = new BigDecimal(24);
        BigDecimal divideHelp;

        divideHelp = agentAmountPerTank.divide(daytime,5, RoundingMode.HALF_UP);
        divideHelp = divideHelp.divide(running,1, RoundingMode.HALF_UP);

        return divideHelp;
    };

    //Bolussperrzeit = 60 / BoliProStunde
    public static BigDecimal BolusLockTime(BigDecimal boliPerHour){
        BigDecimal dividehelp = new BigDecimal(60);
        BigDecimal boliLockTime = dividehelp.divide(boliPerHour,0,RoundingMode.HALF_UP);

        return boliLockTime;
    };

    //BoliProStunde = 60 / Bolussperrzeit
    public static BigDecimal BolusLock(BigDecimal boliLockTime){
        BigDecimal dividehelp = new BigDecimal(60);
        BigDecimal bolusLock = dividehelp.divide(boliLockTime,0,RoundingMode.HALF_UP);

        return bolusLock;
    };

}