package Payment;

import javax.swing.*;
import java.awt.*;
import java.awt.print.*;

public class InvoiceGeneration {

    public void generateAndPrintReceipt(String paymentIDValue, String payerNameValue, String descValue, String paymentMethodValue, String paymentAmountValue, String remarksValue, String paymentDate, JFrame parentFrame) {
        // Generate receipt text
        StringBuilder receiptText = new StringBuilder();
        receiptText.append("-----------------------------AppointWell Clinic------------------------------\n");
        receiptText.append("------------------------------Payment Receipt-------------------------------\n");
        receiptText.append("----------------------------------------------------------------------------------\n");
        receiptText.append("            Payment ID: ").append(paymentIDValue).append("\n");
        receiptText.append("            Payment Date: ").append(paymentDate).append("\n");
        receiptText.append("            Name: ").append(payerNameValue).append("\n");
        receiptText.append("            Description: ").append(descValue).append("\n");
        receiptText.append("            Payment Method: ").append(paymentMethodValue).append("\n");
        receiptText.append("            Amount (RM): ").append(paymentAmountValue).append("\n");
        receiptText.append("            Remarks: ").append(remarksValue).append("\n");
        
        receiptText.append("-----------------------------------------------------------------------------------\n");
        receiptText.append("-------------------Thanks for visiting and recovering soon!-----------------\n");

        // Display receipt in JTextArea
        JTextArea receiptTextArea = new JTextArea(receiptText.toString());
        receiptTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptTextArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        JOptionPane.showMessageDialog(parentFrame, scrollPane, "Receipt Preview", JOptionPane.PLAIN_MESSAGE);

        // Print receipt
        printReceipt(receiptText.toString(), parentFrame);
    }

    private void printReceipt(String receiptText, JFrame parentFrame) {
        PrinterJob job = PrinterJob.getPrinterJob();
        job.setJobName("Print Receipt");

        job.setPrintable((graphics, pageFormat, pageIndex) -> {
            if (pageIndex > 0) {
                return Printable.NO_SUCH_PAGE;
            }

            // Rendering
            Graphics2D g2d = (Graphics2D) graphics;
            g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            FontMetrics fm = g2d.getFontMetrics();

            String[] lines = receiptText.split("\n");
            int y = 100; // Starting y position for receipt

            for (String line : lines) {
                g2d.drawString(line, 100, y);
                y += fm.getHeight() + 10;
            }

            return Printable.PAGE_EXISTS;
        });

        boolean ok = job.printDialog();
        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                // Find the parent frame dynamically
                JOptionPane.showMessageDialog(parentFrame, "Error printing receipt: " + ex.getMessage(), "Print Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
