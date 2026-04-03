package mephi.main;

import mephi.main.mission.Mission;
import mephi.main.reader.ParserFactory;
import mephi.main.report.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;


public class MissionGUI extends JFrame {

    private JButton selectFileButton;
    private JTextArea outputArea;
    private JScrollPane scrollPane;

    private JCheckBox curseCheckBox;
    private JCheckBox sorcerersCheckBox;
    private JCheckBox techniquesCheckBox;
    private JCheckBox enemyActivityCheckBox;
    private JCheckBox economicCheckBox;
    private JCheckBox environmentCheckBox;
    private JCheckBox timelineCheckBox;
    private JCheckBox civilianCheckBox;

    private JButton generateReportButton;
    private Mission currentMission;

    private static final String DEFAULT_PATH = "C:\\Users\\User\\Desktop\\missions";

    public MissionGUI() {
        setTitle("Анализатор миссий магов");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);

        selectFileButton = new JButton("Выбрать файл миссии");
        selectFileButton.setFont(new Font("Arial", Font.BOLD, 14));
        selectFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                selectFile();
            }
        });

        JPanel checkBoxPanel = new JPanel(new GridLayout(2, 4, 10, 5));
        checkBoxPanel.setBorder(BorderFactory.createTitledBorder("Выберите информацию для отчета"));

        curseCheckBox = new JCheckBox("Проклятие", true);
        sorcerersCheckBox = new JCheckBox("Участники", true);
        techniquesCheckBox = new JCheckBox("Техники", true);
        enemyActivityCheckBox = new JCheckBox("Активность врага", false);
        economicCheckBox = new JCheckBox("Экономическая оценка", false);
        environmentCheckBox = new JCheckBox("Условия среды", false);
        timelineCheckBox = new JCheckBox("Хронология", false);
        civilianCheckBox = new JCheckBox("Гражданские", false);

        checkBoxPanel.add(curseCheckBox);
        checkBoxPanel.add(sorcerersCheckBox);
        checkBoxPanel.add(techniquesCheckBox);
        checkBoxPanel.add(enemyActivityCheckBox);
        checkBoxPanel.add(economicCheckBox);
        checkBoxPanel.add(environmentCheckBox);
        checkBoxPanel.add(timelineCheckBox);
        checkBoxPanel.add(civilianCheckBox);

        generateReportButton = new JButton("Сформировать отчет");
        generateReportButton.setFont(new Font("Arial", Font.BOLD, 12));
        generateReportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateReport();
            }
        });

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
        File defaultDir = new File(DEFAULT_PATH);
        JFileChooser fileChooser;

        if (defaultDir.exists() && defaultDir.isDirectory()) {
            fileChooser = new JFileChooser(defaultDir);
        } else {
            fileChooser = new JFileChooser();
        }

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

            currentMission = ParserFactory.parse(file);

            outputArea.append("Файл успешно загружен: " + file.getName() + "\n");
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

        Report report = new SimpleReport();

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
        if (environmentCheckBox.isSelected()) {
            report = new EnvironmentDecorator(report);
        }
        if (timelineCheckBox.isSelected()) {
            report = new TimelineDecorator(report);
        }
        if (civilianCheckBox.isSelected()) {
            report = new CivilianDecorator(report);
        }

        String result = report.generate(currentMission);
        outputArea.setText(result);
    }
}