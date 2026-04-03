package mephi.main.report;

import mephi.main.mission.Mission;
import mephi.main.mission.components.EnemyActivityComponent;

public class EnemyActivityDecorator extends ReportDecorator {

    public EnemyActivityDecorator(Report report) {
        super(report);
    }

    @Override
    public String generate(Mission mission) {
        String base = super.generate(mission);

        StringBuilder sb = new StringBuilder(base);

        mission.getComponent(EnemyActivityComponent.class).ifPresent(enemy -> {
            sb.append("\n--- Активность врага ---\n");
            if (enemy.getBehaviorType() != null)
                sb.append("  Тип поведения: ").append(enemy.getBehaviorType()).append("\n");
            if (enemy.getMobility() != null)
                sb.append("  Мобильность: ").append(enemy.getMobility()).append("\n");
            if (enemy.getEscalationRisk() != null)
                sb.append("  Риск эскалации: ").append(enemy.getEscalationRisk()).append("\n");
            if (!enemy.getTargetPriority().isEmpty())
                sb.append("  Приоритет целей: ").append(enemy.getTargetPriority()).append("\n");
            if (!enemy.getAttackPatterns().isEmpty())
                sb.append("  Паттерны атак: ").append(enemy.getAttackPatterns()).append("\n");
            if (!enemy.getCountermeasuresUsed().isEmpty())
                sb.append("  Использованные контрмеры: ").append(enemy.getCountermeasuresUsed()).append("\n");
        });

        return sb.toString();
    }
}