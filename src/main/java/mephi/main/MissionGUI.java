package mephi.main;

import mephi.main.mission.Mission;
import mephi.main.reader.FileReader;
import mephi.main.reader.ParserFactory;
import mephi.main.reader.format.JSONReader;
import mephi.main.reader.format.TxtReader;
import mephi.main.reader.format.XMLReader;
import mephi.main.reader.format.YamlReader;
import mephi.main.report.*;

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

    // Чекбоксы для выбора информации
    private JCheckBox curseCheckBox;
    private JCheckBox sorcerersCheckBox;
    private JCheckBox techniquesCheckBox;
    private JCheckBox enemyActivityCheckBox;
    private JCheckBox economicCheckBox;

    private JButton generateReportButton;
    private Mission currentMission;

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

        // Панель с чекбоксами
        JPanel checkBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Выберите информацию для отчета"));

        curseCheckBox = new JCheckBox("Проклятие", true);
        sorcerersCheckBox = new JCheckBox("Участники", true);
        techniquesCheckBox = new JCheckBox("Техники", true);
        enemyActivityCheckBox = new JCheckBox("Активность врага", false);
        economicCheckBox = new JCheckBox("Экономическая оценка", false);

        checkBoxPanel.add(curseCheckBox);
        checkBoxPanel.add(sorcerersCheckBox);
        checkBoxPanel.add(techniquesCheckBox);
        checkBoxPanel.add(enemyActivityCheckBox);
        checkBoxPanel.add(economicCheckBox);

        // Кнопка формирования отчета
        generateReportButton = new JButton("Сформировать отчет");
        generateReportButton.setFont(new Font("Arial", Font.BOLD, 12));
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

        // Верхняя панель
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(selectFileButton, BorderLayout.NORTH);
        topPanel.add(checkBoxPanel, BorderLayout.CENTER);
        topPanel.add(generateReportButton, BorderLayout.SOUTH);

        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        scrollPane = new JScrollPane(outputArea);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
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
            currentMission = reader.read(file);

            outputArea.append("✅ Файл успешно загружен: " + file.getName() + "\n");
            outputArea.append("Нажмите 'Сформировать отчет' для вывода информации.\n");

        } catch (Exception e) {
            outputArea.setText("");
            outputArea.append("Ошибка: Файл поврежден или имеет неверный формат\n");
            outputArea.append("Файл: " + file.getName() + "\n");
            outputArea.append("Детали: " + e.getMessage() + "\n");
            outputArea.append("\nПожалуйста, выберите другой файл.");
        }
    }

    private void generateReport() {
        if (currentMission == null) {
            outputArea.setText("Сначала выберите файл миссии!");
            return;
        }

        // Начинаем с базового отчета
        Report report = new SimpleReport();

        // Оборачиваем декораторами в зависимости от выбранных чекбоксов
        if (curseCheckBox.isSelected()) {
            report = new CursesDecorator(report);
        }
        if (sorcerersCheckBox.isSelected()) {
            report = new SorcerersDecorator(report);
        }
        if (techniquesCheckBox.isSelected()) {
            report = new TechniquesDecorator(report);
        }
        if (enemyActivityCheckBox.isSelected()) {
            report = new EnemyActivityDecorator(report);
        }
        if (economicCheckBox.isSelected()) {
            report = new EconomicDecorator(report);
        }

        // Генерируем отчет
        String result = report.generate(currentMission);

        // Выводим
        outputArea.setText(result);
    }

    private FileReader createReader(File file) throws IOException {
        String fileName = file.getName().toLowerCase();

        if (fileName.endsWith(".json")) {
            outputArea.append("Обнаружен JSON файл\n\n");
            return new JSONReader();
        } else if (fileName.endsWith(".xml")) {
            outputArea.append("Обнаружен XML файл\n\n");
            return new XMLReader();
        } else if (fileName.endsWith(".txt")) {
            outputArea.append("Обнаружен TXT файл\n\n");
            return new TxtReader();
        } else if (fileName.endsWith(".yaml") || fileName.endsWith(".yml")) {
            outputArea.append("Обнаружен YAML файл\n\n");
            return new YamlReader();
        } else {
            throw new IOException("Неподдерживаемый формат файла: " + fileName);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MissionGUI().setVisible(true);
            }
        });
    }
}