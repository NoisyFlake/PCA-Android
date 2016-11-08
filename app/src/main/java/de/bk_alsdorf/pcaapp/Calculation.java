package de.bk_alsdorf.pcaapp;

import java.math.BigDecimal;

/**
 * Created by Administrator on 25.10.2016.
 */

public class Calculation {

    // Entered by user
    private BigDecimal agentPerHour, agentAmount, bolusAmount;
    private int tankVolume, runtime, bolusPerHour, bolusLock;

    // Calculated
    private BigDecimal agentAmountPerTank, concentration, basalRate, minimumRuntime = new BigDecimal(0);

    public static BigDecimal getAgentAmountPerTank(BigDecimal agentPerHour, int runtime) {
        BigDecimal agentAmountPerTank = agentPerHour.multiply(new BigDecimal(24)).multiply(new BigDecimal(runtime));
        return agentAmountPerTank;
    }

    public BigDecimal getBasalRate() {
        if (basalRate.compareTo(BigDecimal.ZERO) == 0) {
            basalRate = agentPerHour.multiply(concentration);
        }
        return basalRate;
    }

    public BigDecimal getConcentration() {
        if (concentration.compareTo(BigDecimal.ZERO) == 0) {
            concentration = agentAmount.divide(new BigDecimal(tankVolume));
        }

        return concentration;
    }

    public BigDecimal getMinimumRuntime() {
        if (minimumRuntime.compareTo(BigDecimal.ZERO) == 0) {
            BigDecimal maxBoliAgentPerHour = bolusAmount.multiply(new BigDecimal(bolusPerHour));
            BigDecimal maxAgentPerHour = basalRate.add(maxBoliAgentPerHour);

            minimumRuntime = new BigDecimal(tankVolume).divide(maxAgentPerHour);
        }

        return minimumRuntime;
    }
}