/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Payment;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;

/**
 *
 * @author ASUS
 */
public class PaymentPortal extends JFrame {

    private JButton backButton;
    String method = "";

    PaymentPortal() {
        setTitle("Payment Portal"); //set the title of the frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //exit out of application
        setExtendedState(JFrame.MAXIMIZED_BOTH); //maximize the width and height
        setLayout(new BorderLayout());

//************************* Payment Portal Title ****************************************
        JPanel titleBar = new JPanel();
        titleBar.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 15));
        titleBar.setPreferredSize(new Dimension(800, 80));
        titleBar.setBorder(BorderFactory.createEtchedBorder(WIDTH, Color.lightGray, Color.BLACK));
        titleBar.setBackground(new Color(215, 203, 255, 80));

        ImageIcon back = new ImageIcon("src/images/backButton.png");
        Image backImg = back.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        backButton = new JButton(new ImageIcon(backImg));
        backButton.setPreferredSize(new Dimension(30, 30));
        backButton.setBackground(Color.WHITE);
        backButton.addActionListener((ActionEvent e) -> {
            dispose();
            try {
                PaymentPage paymentPage = new PaymentPage();
            } catch (IOException ex) {
                Logger.getLogger(PaymentPortal.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        titleBar.add(backButton);

        JLabel title = new JLabel("Payment");
        title.setFont(new Font("Segeo UI", Font.BOLD, 30));
        titleBar.add(title);

//************************* Payment Content ****************************************
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 100));
        contentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH));
        contentPanel.setBackground(new Color(157, 226, 191, 95));

        JPanel paymentPanel = new JPanel();
        paymentPanel.setLayout(new GridBagLayout());
        paymentPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, WIDTH, true));
        paymentPanel.setPreferredSize(new Dimension(800, 500));
        paymentPanel.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 20, 10, 20);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Set font style
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 18);
        Font fontField = new Font("Segoe UI", Font.PLAIN, 16);
        Color fieldColor = new Color(217, 241, 253);

        // Name
        JLabel payerName = new JLabel("Name:");
        payerName.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 0;
        paymentPanel.add(payerName, gbc);

        JTextField payerNameField = new JTextField(20);
        payerNameField.setFont(fontField);
        payerNameField.setBackground(fieldColor);
        gbc.gridx = 1;
        gbc.gridy = 0;
        paymentPanel.add(payerNameField, gbc);

        // Item/Service Description
        JLabel desc = new JLabel("Item/Service Description:");
        desc.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 1;
        paymentPanel.add(desc, gbc);

        JTextArea descField = new JTextArea(4, 20);
        descField.setBackground(fieldColor);
        descField.setFont(fontField);
        descField.setLineWrap(true);
        descField.setWrapStyleWord(true);

        JScrollPane descScrollPane = new JScrollPane(descField);
        descScrollPane.setPreferredSize(new Dimension(200, 80));
        gbc.gridx = 1;
        gbc.gridy = 1;
        paymentPanel.add(descScrollPane, gbc);

        // Amount
        JLabel amount = new JLabel("Amount (RM):");
        amount.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 2;
        paymentPanel.add(amount, gbc);

        JTextField amountField = new JTextField(20);
        amountField.setBackground(fieldColor);
        amountField.setFont(fontField);
        gbc.gridx = 1;
        gbc.gridy = 2;
        paymentPanel.add(amountField, gbc);

        // Payment Method
        JLabel paymentMethod = new JLabel("Choose the payment method:");
        paymentMethod.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 3;
        paymentPanel.add(paymentMethod, gbc);

        // Payment Buttons
        JPanel paymentMethodsPanel = new JPanel();
        paymentMethodsPanel.setLayout(new GridLayout(2, 2, 15, 15));
        paymentMethodsPanel.setBackground(Color.WHITE);

        JButton cashPayment = new JButton("Cash");
        cashPayment.setFont(fontField.deriveFont(Font.BOLD));
        cashPayment.setBackground(new Color(172, 213, 255));
        paymentMethodsPanel.add(cashPayment);

        JButton debitCard = new JButton("Debit Card");
        debitCard.setFont(fontField.deriveFont(Font.BOLD));
        debitCard.setBackground(new Color(255, 221, 176));
        paymentMethodsPanel.add(debitCard);

        JButton creditCard = new JButton("Credit Card");
        creditCard.setFont(fontField.deriveFont(Font.BOLD));
        creditCard.setBackground(new Color(255, 221, 225));
        paymentMethodsPanel.add(creditCard);

        JButton onlineBanking = new JButton("Online Banking");
        onlineBanking.setFont(fontField.deriveFont(Font.BOLD));
        onlineBanking.setBackground(new Color(219, 208, 255));
        paymentMethodsPanel.add(onlineBanking);

        Border borderSelected = BorderFactory.createLineBorder(Color.BLACK, 3);

        cashPayment.addActionListener((ActionEvent e) -> {
            cashPayment.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            debitCard.setBorder(BorderFactory.createEmptyBorder());
            creditCard.setBorder(BorderFactory.createEmptyBorder());
            onlineBanking.setBorder(BorderFactory.createEmptyBorder());
            method = "Cash";
        });
        debitCard.addActionListener((ActionEvent e) -> {
            debitCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            cashPayment.setBorder(BorderFactory.createEmptyBorder());
            creditCard.setBorder(BorderFactory.createEmptyBorder());
            onlineBanking.setBorder(BorderFactory.createEmptyBorder());
            method = "Debit Card";
        });
        creditCard.addActionListener((ActionEvent e) -> {
            creditCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            cashPayment.setBorder(BorderFactory.createEmptyBorder());
            debitCard.setBorder(BorderFactory.createEmptyBorder());
            onlineBanking.setBorder(BorderFactory.createEmptyBorder());
            method = "Credit Card";
        });

        onlineBanking.addActionListener((ActionEvent e) -> {
            onlineBanking.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));
            cashPayment.setBorder(BorderFactory.createEmptyBorder());
            debitCard.setBorder(BorderFactory.createEmptyBorder());
            creditCard.setBorder(BorderFactory.createEmptyBorder());
            method = "Online Banking";
        });

        paymentMethodsPanel.revalidate();
        paymentMethodsPanel.repaint();
        gbc.gridx = 1;
        gbc.gridy = 3;
        paymentPanel.add(paymentMethodsPanel, gbc);

        // Remarks
        JLabel remarks = new JLabel("Remarks:");
        remarks.setFont(fontLabel);
        gbc.gridx = 0;
        gbc.gridy = 4;
        paymentPanel.add(remarks, gbc);

        JTextArea remarksField = new JTextArea(3, 20);
        remarksField.setBackground(fieldColor);
        remarksField.setFont(fontField);
        remarksField.setLineWrap(true);
        remarksField.setWrapStyleWord(true);

        JScrollPane remarksScrollPane = new JScrollPane(remarksField);
        remarksScrollPane.setPreferredSize(new Dimension(300, 60));
        gbc.gridx = 1;
        gbc.gridy = 4;
        paymentPanel.add(remarksScrollPane, gbc);

        // Paid Button
        JButton paidBtn = new JButton("Paid");
        paidBtn.setFont(fontLabel);
        paidBtn.setBackground(new Color(47, 39, 206));
        paidBtn.setForeground(Color.WHITE);
        paidBtn.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        paidBtn.addActionListener((ActionEvent e) -> {
            String payerNameValue = payerNameField.getText();
            String descValue = descField.getText();
            String paymentMethodValue = method;
            String amountValue = amountField.getText();
            String remarksValue = remarksField.getText();
            LocalDate paymentDate = LocalDate.now();

            // Perform validation checks
            if (payerNameValue.isEmpty() || descValue.isEmpty() || paymentMethodValue.isEmpty() || amountValue.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill in all required fields except remarks.");
                return; // Exit method if any field is empty
            }

            // Name cannot have integer
            if (!payerNameValue.matches("[a-zA-Z\\s]+")) {
                JOptionPane.showMessageDialog(null, "Invalid name. Please enter only alphabetic characters.");
                return;
            }

            if (!amountValue.matches("\\d+(\\.\\d{1,2})?")) {
                JOptionPane.showMessageDialog(null, "Please enter numbers or floating points only!");
                return;
            }

            // Parse and format the amount value
            double amountV = 0;
            try {
                amountV = Double.parseDouble(amountValue);
                amountValue = String.format("%.2f", amountV);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Invalid amount value.");
                return;
            }

            // Increment rowCount
            int rowCount = getRowCount("src/TextFiles/PaymentData.txt");
            int currentPatientID = rowCount + 1;
            String paymentID = "PY" + String.format("%03d", currentPatientID);

            try {
                FileWriter fw = new FileWriter("src/TextFiles/PaymentData.txt", true);
                try (BufferedWriter bw = new BufferedWriter(fw)) {
                    bw.write(paymentID + "|");
                    bw.write(payerNameValue + "|");
                    bw.write(descValue + "|");
                    bw.write(paymentMethodValue + "|");
                    bw.write(amountValue + "|");
                    bw.write(remarksValue + "|");
                    bw.write(paymentDate + "\n");
                    bw.flush(); // Flushes the stream
                    bw.close();
                }

                JOptionPane.showMessageDialog(null, "Payment made successfully!");
                // Refresh the payment panel
                // Clear text fields
                payerNameField.setText("");
                descField.setText("");
                amountField.setText("");
                remarksField.setText("");

                // Reset button borders
                cashPayment.setBorder(BorderFactory.createEmptyBorder());
                debitCard.setBorder(BorderFactory.createEmptyBorder());
                creditCard.setBorder(BorderFactory.createEmptyBorder());
                onlineBanking.setBorder(BorderFactory.createEmptyBorder());

                // Reset payment method
                method = "";

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error occurred while making payment.");
            }

        });
        paymentPanel.add(paidBtn, gbc);

        contentPanel.add(paymentPanel);

        add(contentPanel, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        setVisible(true);

    }

    private int getRowCount(String filePath) {
        int rowCount = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            while (reader.readLine() != null) {
                rowCount++;
            }
        } catch (IOException e) {
        }
        return rowCount;
    }
}
