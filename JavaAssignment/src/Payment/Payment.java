/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Payment;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ASUS
 */
public class Payment {

    private String paymentId;
    private String name;
    private String description;
    private String paymentMethod;
    private double amount;
    private String remarks;
    private LocalDate paymentDate;

    public Payment(String paymentId, String name, String description, String paymentMethod,
            double amount, String remarks, LocalDate paymentDate) {
        this.paymentId = paymentId;
        this.name = name;
        this.description = description;
        this.paymentMethod = paymentMethod;
        this.amount = amount;
        this.remarks = remarks;
        this.paymentDate = paymentDate;
    }

    // Getters and setters for all attributes
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String description) {
        this.description = description;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public static List<Payment> readPaymentFromFile(String filePath) throws IOException {
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("src/TextFiles/PaymentData.txt"))) { //payment file path
            String line;
            while ((line = br.readLine()) != null) {
                String[] fields = line.split("\\|");
                if (fields.length == 7) {
                    double amount = Double.parseDouble(fields[4].trim());
                    LocalDate paymentDate = LocalDate.parse(fields[6].trim());
                    Payment payment = new Payment(fields[0].trim(), fields[1].trim(), fields[2].trim(), fields[3].trim(), amount, fields[5].trim(), paymentDate);
                    payments.add(payment);
                }
            }
        }
        return payments;
    }

//    Write payment to file
    public static void writePaymentsToFile(String filePath, List<Payment> payments) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new BufferedWriter(new FileWriter(filePath)))) {
            for (Payment payment : payments) {
                String line = String.format("%s |%s |%s |%s |%.2f |%s |%s",
                        payment.getPaymentId(),
                        payment.getName(),
                        payment.getDesc(),
                        payment.getPaymentMethod(),
                        payment.getAmount(),
                        payment.getRemarks(),
                        payment.getPaymentDate());
                bw.write(line);
                bw.newLine();
            }
        }
    }
}
