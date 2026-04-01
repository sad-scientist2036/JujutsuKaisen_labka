package mephi.main;

import mephi.main.mission.Mission;
import mephi.main.mission.components.*;
import mephi.main.reader.FileReader;
import mephi.main.reader.ParserFactory;
import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        // Путь к XML файлу
        String filePath = "C:\\Users\\User\\Desktop\\labr1\\Данные о миссиях. Вариант 1\\Mission A.txt";

        System.out.println("Чтение файла: " + filePath);

        File file = new File(filePath);

        if (!file.exists()) {
            System.out.println("Ошибка: файл не найден: " + filePath);
            return;
        }

        try {
            // Фабричный метод создает нужный парсер
            FileReader reader = ParserFactory.createReader(file);

            // Читаем миссию
            Mission mission = reader.read(file);

            // Выводим информацию
            printMission(mission);

        } catch (IOException e) {
            System.out.println("Ошибка при чтении файла: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.out.println("Ошибка валидации: " + e.getMessage());
        }
    }

    private static void printMission(Mission mission) {
        System.out.println("\n========================================");
        System.out.println("        ИНФОРМАЦИЯ О МИССИИ");
        System.out.println("========================================");

        // Core
        System.out.println("ID миссии:    " + mission.getMissionId());
        System.out.println("Дата:         " + mission.getDate());
        System.out.println("Локация:      " + mission.getLocation());
        System.out.println("Итог:         " + mission.getOutcome());
        System.out.println("Ущерб:        " + mission.getDamageCost() + " йен");

        // Curse
        mission.getComponent(CurseComponent.class).ifPresent(curse -> {
            System.out.println("\n--- Проклятие ---");
            System.out.println("  Имя:    " + curse.getName());
            System.out.println("  Уровень: " + curse.getThreatLevel());
        });

        // Sorcerers
        if (!mission.getSorcerers().isEmpty()) {
            System.out.println("\n--- Участники ---");
            for (SorcererComponent s : mission.getSorcerers()) {
                System.out.println("  Маг: " + s.getName() + " (" + s.getRank() + ")");
            }
        }

        // Techniques
        if (!mission.getTechniques().isEmpty()) {
            System.out.println("\n--- Техники ---");
            for (TechniqueComponent t : mission.getTechniques()) {
                System.out.println("  Техника: " + t.getName() +
                        " (" + t.getType() + ")" +
                        ", владелец: " + t.getOwner() +
                        ", урон: " + t.getDamage());
            }
        }

        // EnemyActivity (из файла Mission A3.xml)
        mission.getComponent(EnemyActivityComponent.class).ifPresent(enemy -> {
            System.out.println("\n--- Активность врага ---");
            if (enemy.getBehaviorType() != null)
                System.out.println("  Тип поведения: " + enemy.getBehaviorType());
            if (!enemy.getAttackPatterns().isEmpty())
                System.out.println("  Паттерны атак: " + enemy.getAttackPatterns());
            if (enemy.getMobility() != null)
                System.out.println("  Мобильность: " + enemy.getMobility());
            if (enemy.getEscalationRisk() != null)
                System.out.println("  Риск эскалации: " + enemy.getEscalationRisk());
        });

        System.out.println("\n========================================");
    }
}