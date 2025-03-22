/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Payment;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

/**
 *
 * @author ASUS
 */
public class PaymentDetails extends JFrame {

    private JButton backButton;
    String method = "";

    PaymentDetails(String paymentIDValue, String payerNameValue, String descValue, String paymentMethodValue, String paymentAmountValue, String remarksValue, String paymentDate) {

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
        payerNameField.setText(payerNameValue);
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
        descField.setText(descValue);

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
        amountField.setText(paymentAmountValue);
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

        // Clear all borders
        cashPayment.setBorder(BorderFactory.createEmptyBorder());
        debitCard.setBorder(BorderFactory.createEmptyBorder());
        creditCard.setBorder(BorderFactory.createEmptyBorder());
        onlineBanking.setBorder(BorderFactory.createEmptyBorder());

        Border borderSelected = BorderFactory.createLineBorder(Color.BLACK, 3);

        String trimmedPaymentMethodValue = paymentMethodValue.trim(); // Trim the string

        if (trimmedPaymentMethodValue.equalsIgnoreCase("Cash")) { // Case-insensitive comparison
            cashPayment.setBorder(borderSelected);
            method = "Cash";
        } else if (trimmedPaymentMethodValue.equalsIgnoreCase("Debit Card")) {
            debitCard.setBorder(borderSelected);
            method = "Debit Card";
        } else if (trimmedPaymentMethodValue.equalsIgnoreCase("Credit Card")) {
            creditCard.setBorder(borderSelected);
            method = "Credit Card";
        } else if (trimmedPaymentMethodValue.equalsIgnoreCase("Online Banking")) {
            onlineBanking.setBorder(borderSelected);
            method = "Online Banking";
        } else {
            System.out.println("No valid payment method selected.");
        }

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
        remarksField.setText(remarksValue);

        JScrollPane remarksScrollPane = new JScrollPane(remarksField);
        remarksScrollPane.setPreferredSize(new Dimension(300, 60));
        gbc.gridx = 1;
        gbc.gridy = 4;
        paymentPanel.add(remarksScrollPane, gbc);

        JLabel paymentDateLabel = new JLabel("Payment Date:");
        paymentDateLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));  // Set font size here
        gbc.gridx = 0;
        gbc.gridy = 5;
        paymentPanel.add(paymentDateLabel, gbc);

        JTextField paymentDateField = new JTextField(20);
        paymentDateField.setBackground(fieldColor);
        paymentDateField.setFont(fontField);
        paymentDateField.setText(paymentDate);
        paymentDateField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 5;
        paymentPanel.add(paymentDateField, gbc);

        // Save Button
        JButton saveBtn = new JButton("Save changes");
        saveBtn.setFont(fontLabel);
        saveBtn.setBackground(new Color(47, 39, 206));
        saveBtn.setForeground(Color.WHITE);
        saveBtn.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 0;
        gbc.gridy = 6;

        saveBtn.addActionListener((ActionEvent e) -> {
            try {
                List<Payment> payments = Payment.readPaymentFromFile("src/TextFiles/PaymentData.txt");
                for (Payment payment : payments) {
                    String paymentID = payment.getPaymentId();
                    if (paymentID.equals(paymentIDValue)) {
                        String nameField = payerNameField.getText();
                        payment.setName(nameField);
                        String description = descField.getText();
                        payment.setDesc(description);
                        String amountStr = amountField.getText();
                        try {
                            Double amount1 = Double.valueOf(amountStr);
                            payment.setAmount(amount1);
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(null, "Invalid amount. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        String paymentMtd = method;
                        payment.setPaymentMethod(paymentMtd);
                        String remarks1 = remarksField.getText();
                        payment.setRemarks(remarks1);
                        String paymentDates = paymentDateField.getText();
                        LocalDate paymentDateStored = LocalDate.parse(paymentDates, DateTimeFormatter.ISO_LOCAL_DATE);
                        payment.setPaymentDate(paymentDateStored);
                        break;
                    }
                }
                Payment.writePaymentsToFile("src/TextFiles/PaymentData.txt", payments);
                JOptionPane.showMessageDialog(null, "Payment details updated successfully.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "An error occured while updating the file.");
            }
        });

        paymentPanel.add(saveBtn, gbc);

        // Generate Receipt Button
        JButton generateBillBtn = new JButton("Generate receipt");
        generateBillBtn.setFont(fontLabel);
        generateBillBtn.setBackground(Color.BLACK);
        generateBillBtn.setForeground(Color.WHITE);
        generateBillBtn.setPreferredSize(new Dimension(200, 35));
        gbc.gridx = 1;
        gbc.gridy = 6;
        
        generateBillBtn.addActionListener((ActionEvent e)->{
            String paymentID = paymentIDValue;
            String payerNameValueR = payerNameField.getText();
            String descValueR = descField.getText();
            String paymentMethodR = method;
            String paymentAmountR = amountField.getText();
            String remarksR = remarksField.getText();
            String paymentDateR = paymentDateField.getText();

            InvoiceGeneration invoice = new InvoiceGeneration();
            invoice.generateAndPrintReceipt(paymentID, payerNameValueR, descValueR, paymentMethodR, paymentAmountR, remarksR, paymentDateR, this);
        });

        paymentPanel.add(generateBillBtn, gbc);

        contentPanel.add(paymentPanel);

        add(contentPanel, BorderLayout.CENTER);
        add(titleBar, BorderLayout.NORTH);

        setVisible(true);

    }
}
