import java.io.*;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ExpenseTracker {
    private static final String FILE_NAME = "expenses.txt";
    private static List<String> expenses = new ArrayList<>();

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> createAndShowGUI());
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Expense Tracker");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel dateLabel = new JLabel("Date (YYYY-MM-DD):");
        JTextField dateField = new JTextField();
        JLabel categoryLabel = new JLabel("Category:");
        JTextField categoryField = new JTextField();
        JLabel amountLabel = new JLabel("Amount:");
        JTextField amountField = new JTextField();
        JButton addButton = new JButton("Add Expense");
        JButton viewButton = new JButton("View Expenses");

        panel.add(dateLabel);
        panel.add(dateField);
        panel.add(categoryLabel);
        panel.add(categoryField);
        panel.add(amountLabel);
        panel.add(amountField);
        panel.add(addButton);
        panel.add(viewButton);

        addButton.addActionListener(e -> {
            String date = dateField.getText();
            String category = categoryField.getText();
            String amount = amountField.getText();
            if (!date.isEmpty() && !category.isEmpty() && !amount.isEmpty()) {
                String entry = date + ", " + category + ", " + amount;
                expenses.add(entry);
                saveExpenses();
                JOptionPane.showMessageDialog(frame, "Expense added successfully!");
            } else {
                JOptionPane.showMessageDialog(frame, "Please fill all fields.");
            }
        });

        viewButton.addActionListener(e -> {
            loadExpenses();
            JTextArea textArea = new JTextArea();
            for (String expense : expenses) {
                textArea.append(expense + "\n");
            }
            JOptionPane.showMessageDialog(frame, new JScrollPane(textArea), "Expenses", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.add(panel);
        frame.setVisible(true);
    }

    private static void saveExpenses() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            for (String expense : expenses) {
                writer.write(expense);
                writer.newLine();
            }
            expenses.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadExpenses() {
        expenses.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                expenses.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

