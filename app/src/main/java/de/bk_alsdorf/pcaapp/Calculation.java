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

        return new BigDecimal(tankVolume).divide(maxAgentPerHour, 1, RoundingMode.HALF_UP);
    }

    //Bolusmenge in ml = Basalrate * (1/Wirkstoffkonzentration)
    public static BigDecimal convertMgToMl(BigDecimal agentPerHour, BigDecimal agentAmount, int tankVolume) {
        BigDecimal concentration = getConcentration(agentAmount, tankVolume);
        BigDecimal basalrate = getBasalRate(agentPerHour, concentration);
        BigDecimal one = new BigDecimal(1);
        return basalrate.multiply((one.divide(concentration)));
    }

    // Bolusmenge in mg = Basalrate
    public static BigDecimal convertMlToMg(BigDecimal agentPerHour, BigDecimal agentAmount, int tankVolume) {
        BigDecimal concentration = getConcentration(agentAmount, tankVolume);
        return getBasalRate(agentPerHour, concentration);
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

}