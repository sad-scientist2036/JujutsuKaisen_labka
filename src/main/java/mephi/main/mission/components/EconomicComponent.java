package mephi.main.mission.components;

import mephi.main.mission.MissionComponent;
public class EconomicComponent implements MissionComponent {
    private final Long totalDamageCost;
    private final Long infrastructureDamage;
    private final Long commercialDamage;
    private final Long transportDamage;
    private final Integer recoveryEstimateDays;
    private final Boolean insuranceCovered;

    private EconomicComponent(Builder builder) {
        this.totalDamageCost = builder.totalDamageCost;
        this.infrastructureDamage = builder.infrastructureDamage;
        this.commercialDamage = builder.commercialDamage;
        this.transportDamage = builder.transportDamage;
        this.recoveryEstimateDays = builder.recoveryEstimateDays;
        this.insuranceCovered = builder.insuranceCovered;
    }

    @Override
    public String getComponentType() {
        return "economic";
    }

    @Override
    public String getInfo() {
        StringBuilder sb = new StringBuilder("Экономическая оценка: ");
        if (totalDamageCost != null) sb.append("общий ущерб=").append(totalDamageCost);
        if (infrastructureDamage != null) sb.append(", инфраструктура=").append(infrastructureDamage);
        if (commercialDamage != null) sb.append(", коммерческий=").append(commercialDamage);
        if (transportDamage != null) sb.append(", транспорт=").append(transportDamage);
        if (recoveryEstimateDays != null) sb.append(", восстановление=").append(recoveryEstimateDays).append("дн");
        if (insuranceCovered != null) sb.append(", страховка=").append(insuranceCovered);
        return sb.toString();
    }

    public Long getTotalDamageCost() { return totalDamageCost; }
    public Long getInfrastructureDamage() { return infrastructureDamage; }
    public Long getCommercialDamage() { return commercialDamage; }
    public Long getTransportDamage() { return transportDamage; }
    public Integer getRecoveryEstimateDays() { return recoveryEstimateDays; }
    public Boolean getInsuranceCovered() { return insuranceCovered; }

    public static class Builder {
        private Long totalDamageCost;
        private Long infrastructureDamage;
        private Long commercialDamage;
        private Long transportDamage;
        private Integer recoveryEstimateDays;
        private Boolean insuranceCovered;

        public Builder totalDamageCost(Long value) { this.totalDamageCost = value; return this; }
        public Builder infrastructureDamage(Long value) { this.infrastructureDamage = value; return this; }
        public Builder commercialDamage(Long value) { this.commercialDamage = value; return this; }
        public Builder transportDamage(Long value) { this.transportDamage = value; return this; }
        public Builder recoveryEstimateDays(Integer value) { this.recoveryEstimateDays = value; return this; }
        public Builder insuranceCovered(Boolean value) { this.insuranceCovered = value; return this; }

        public EconomicComponent build() {
            return new EconomicComponent(this);
        }
    }
}