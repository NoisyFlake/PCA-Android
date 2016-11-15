package de.bk_alsdorf.pcaapp;

import java.math.BigDecimal;
import java.math.RoundingMode;

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
        return agentPerHour.multiply(new BigDecimal(24)).multiply(new BigDecimal(runtime));
    }

    public static BigDecimal getBasalRate(BigDecimal agentPerHour, BigDecimal concentration) {
        return agentPerHour.multiply(concentration);
    }

    public static BigDecimal getConcentration(BigDecimal agentAmount, int tankVolume) {
        return agentAmount.divide(new BigDecimal(tankVolume));
    }

    public static BigDecimal getMinimumRuntime(BigDecimal bolusAmount, int bolusPerHour, BigDecimal basalRate, int tankVolume) {
        BigDecimal maxBoliAgentPerHour = bolusAmount.multiply(new BigDecimal(bolusPerHour));
        BigDecimal maxAgentPerHour = basalRate.add(maxBoliAgentPerHour);

        return new BigDecimal(tankVolume).divide(maxAgentPerHour, 2, RoundingMode.HALF_UP);
    }
}