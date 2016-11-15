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
}