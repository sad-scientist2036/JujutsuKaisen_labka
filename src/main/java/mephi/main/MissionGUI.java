package mephi.main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MissionGUI extends JFrame {

    private JButton selectFileButton;
    private JTextArea outputArea;
    private JScrollPane scrollPane;

    public MissionGUI() {
        setTitle("Анализатор миссий магов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);

        selectFileButton = new JButton("Выбрать файл миссии");
        selectFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(selectFileButton, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл миссии");

        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            analyzeFile(selectedFile);
        }
    }

    private void analyzeFile(File file) {
        try {
            outputArea.setText("");

            FileReader reader = createReader(file);
            Mission mission = reader.read(file);

            displayMissionInfo(mission);

        } catch (Exception e) {
            outputArea.setText("");
            outputArea.append("Ошибка: Файл поврежден или имеет неверный формат\n");
            outputArea.append("Файл: " + file.getName() + "\n");
            outputArea.append("Детали: " + e.getMessage() + "\n");
            outputArea.append("\nПожалуйста, выберите другой файл.");
        }
    }

    private FileReader createReader(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".json")) {
            outputArea.append(" Обнаружен JSON файл\n\n");
            return new JSONReader();
        } else if (fileName.endsWith(".xml")) {
            outputArea.append(" Обнаружен XML файл\n\n");
            return new XMLReader();
        } else if (fileName.endsWith(".txt")) {
            outputArea.append(" Обнаружен TXT файл\n\n");
            return new TxtReader();
        } else {
            throw new IOException("Неподдерживаемый формат файла: " + fileName);
        }
    }

    private void displayMissionInfo(Mission mission) {
        outputArea.append("Информация о миссии\n");
        outputArea.append("ID миссии:    " + (mission.getMissionId() != null ? mission.getMissionId() : "Не указано") + "\n");
        outputArea.append("Дата:         " + (mission.getDate() != null ? mission.getDate() : "Не указана") + "\n");
        outputArea.append("Локация:      " + (mission.getLocation() != null ? mission.getLocation() : "Не указано") + "\n");
        outputArea.append("Итог:         " + (mission.getOutcome() != null ? mission.getOutcome() : "Не указано") + "\n");
        outputArea.append("Ущерб:        " + mission.getDamageCost() + " йен\n");

        outputArea.append("\n Проклятья \n");
        if (mission.getCurse() != null && !mission.getCurse().isEmpty()) {
            for (int i = 0; i < mission.getCurse().size(); i++) {
                Curse curse = mission.getCurse().get(i);
                outputArea.append("Проклятие " + (i + 1) + ":\n");
                outputArea.append("  Имя:    " + (curse.getName() != null ? curse.getName() : "Не указано") + "\n");
                outputArea.append("  Уровень: " + (curse.getThreatLevel() != null ? curse.getThreatLevel() : "Не указано") + "\n");
                if (curse.getTechnique() != null) {
                    outputArea.append("  Техника: " + (curse.getTechnique().getName() != null ? curse.getTechnique().getName() : "Не указано") + "\n");
                }
                outputArea.append("\n");
            }
        } else {
            outputArea.append("  Нет данных о проклятиях\n");
        }

        outputArea.append("Участники\n");
        if (mission.getSorcerer() != null && !mission.getSorcerer().isEmpty()) {
            for (int i = 0; i < mission.getSorcerer().size(); i++) {
                Sorcerer sorcerer = mission.getSorcerer().get(i);
                outputArea.append("Маг #" + (i + 1) + ":\n");
                outputArea.append("  Имя:    " + (sorcerer.getName() != null ? sorcerer.getName() : "Не указано") + "\n");
                outputArea.append("  Ранг:   " + (sorcerer.getRank() != null ? sorcerer.getRank() : "Не указано") + "\n");
                if (sorcerer.getTechnique() != null) {
                    Technique t = sorcerer.getTechnique();
                    outputArea.append("  Техника: " + (t.getName() != null ? t.getName() : "Не указано") + "\n");
                    outputArea.append("  Тип:     " + (t.getType() != null ? t.getType() : "Не указано") + "\n");
                    outputArea.append("  Урон:    " + t.getDamage() + " ед.\n");
                }
                outputArea.append("\n");
            }
        } else {
            outputArea.append("  Нет данных об участниках\n");
        }

        outputArea.append("Техники\n");
        if (mission.getTechnique() != null && !mission.getTechnique().isEmpty()) {
            for (int i = 0; i < mission.getTechnique().size(); i++) {
                Technique technique = mission.getTechnique().get(i);
                outputArea.append("Техника #" + (i + 1) + ":\n");
                outputArea.append("  Название: " + (technique.getName() != null ? technique.getName() : "Не указано") + "\n");
                outputArea.append("  Тип:      " + (technique.getType() != null ? technique.getType() : "Не указано") + "\n");
                outputArea.append("  Урон:     " + technique.getDamage() + " ед.\n");
                if (technique.getOwner() != null) {
                    outputArea.append("  Владелец: " + technique.getOwner() + "\n");
                }
                outputArea.append("\n");
            }
        } else {
            outputArea.append("  Нет данных о техниках\n");
        }

        if (mission.getNote() != null && !mission.getNote().isEmpty()) {
            outputArea.append("Комментарий \n");
            outputArea.append(mission.getNote() + "\n");
        }
    }
}