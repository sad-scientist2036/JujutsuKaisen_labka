package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.EconomicComponent;

public class EconomicDecorator extends ReportDecorator {

    public EconomicDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);

        mission.getComponent(EconomicComponent.class).ifPresent(economic -> {
            sb.append("\n--- Экономическая оценка ---\n");
            if (economic.getTotalDamageCost() != null)
                sb.append("  Общий ущерб: ").append(economic.getTotalDamageCost()).append(" йен\n");
            if (economic.getInfrastructureDamage() != null)
                sb.append("  Ущерб инфраструктуре: ").append(economic.getInfrastructureDamage()).append(" йен\n");
            if (economic.getCommercialDamage() != null)
                sb.append("  Коммерческий ущерб: ").append(economic.getCommercialDamage()).append(" йен\n");
            if (economic.getTransportDamage() != null)
                sb.append("  Ущерб транспорту: ").append(economic.getTransportDamage()).append(" йен\n");
            if (economic.getRecoveryEstimateDays() != null)
                sb.append("  Восстановление: ").append(economic.getRecoveryEstimateDays()).append(" дней\n");
            if (economic.getInsuranceCovered() != null)
                sb.append("  Покрытие страхованием: ").append(economic.getInsuranceCovered() ? "Да" : "Нет").append("\n");
        });

        return sb.toString();
    }
}