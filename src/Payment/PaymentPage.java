package Payment;

import AdminMainPage.AdminNavBar;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;

public class PaymentPage extends JFrame {

    private static final int TOTAL_COLUMN_INDEX = 4;
    
    public PaymentPage() throws IOException {
        setTitle("Payment Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        AdminNavBar navBar = new AdminNavBar(this);

        JPanel payment = new JPanel();
        payment.setLayout(new BorderLayout());
        payment.setBackground(Color.WHITE);

        JPanel paymentInfo = new JPanel();
        paymentInfo.setLayout(new BorderLayout());
        paymentInfo.setBackground(new Color(219, 208, 255, 90));
        payment.add(paymentInfo, BorderLayout.CENTER);

        JLabel paymentTitle = new JLabel("Payment Information");
        setComponentStyle(paymentTitle, Color.WHITE, new Font("Segeo UI", Font.BOLD, 30));
        paymentInfo.add(paymentTitle, BorderLayout.NORTH);

        JTable paymentTable = createPaymentTable();
        JScrollPane paymentScrollPane = new JScrollPane(paymentTable);
        paymentScrollPane.setPreferredSize(new Dimension(1480, 560));
        paymentScrollPane.getViewport().setBackground(new Color(219, 208, 255, 90));
        paymentScrollPane.setBorder(BorderFactory.createLineBorder(new Color(219, 208, 255, 90)));
        paymentInfo.add(paymentScrollPane, BorderLayout.CENTER);

        JButton paymentBtn = new JButton("+ Add Payment");
        paymentBtn.setFont(new Font("Segeo UI", Font.BOLD, 16));
        paymentBtn.setPreferredSize(new Dimension(180, 30));
        paymentBtn.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        paymentBtn.setBackground(Color.BLACK);
        paymentBtn.setForeground(Color.WHITE);
        paymentBtn.addActionListener(e -> {
            dispose();
            PaymentPortal paymentPortal = new PaymentPortal();
        });
        payment.add(paymentBtn, BorderLayout.SOUTH);

        add(navBar, BorderLayout.NORTH);
        add(payment, BorderLayout.CENTER);

        setVisible(true);
    }

//        Table
    private JTable createPaymentTable() throws IOException {
        String[] columnNames = {"Payment ID", "Name", "Description", "Payment Method", "Amount (RM)", "Remarks", "Payment Date"};
        List<Payment> payments = Payment.readPaymentFromFile("src/TextFiles/PaymentData.txt");
        Object[][] data = new Object[payments.size()][columnNames.length];

//            Loop and read file
        for (int i = 0; i < payments.size(); i++) {
            Payment payment = payments.get(i);
            data[i][0] = payment.getPaymentId();
            data[i][1] = payment.getName();
            data[i][2] = payment.getDesc();
            data[i][3] = payment.getPaymentMethod();
            data[i][4] = String.format("%.2f", payment.getAmount());
            data[i][5] = payment.getRemarks();
            data[i][6] = payment.getPaymentDate();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable paymentTable = new JTable(model) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? new Color(172, 247, 219, 70) : Color.WHITE);
                }
                return c;
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make all cells non-editable
            }
        };

        paymentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = paymentTable.getSelectedRow();

                if (selectedRow != -1 && selectedRow != paymentTable.getRowCount() - 1) {
                    String paymentID = paymentTable.getValueAt(selectedRow, 0).toString();
                    String payerName = paymentTable.getValueAt(selectedRow, 1).toString();
                    String desc = paymentTable.getValueAt(selectedRow, 2).toString();
                    String paymentMethod = paymentTable.getValueAt(selectedRow, 3).toString();
                    String paymentAmount = paymentTable.getValueAt(selectedRow, 4).toString();
                    String remarks = paymentTable.getValueAt(selectedRow, 5).toString();
                    String paymentDate = paymentTable.getValueAt(selectedRow,6).toString();
                    showPaymentDetails(paymentID, payerName, desc, paymentMethod, paymentAmount, remarks, paymentDate);
                }
            }

            private void showPaymentDetails(String paymentID, String payerName, String desc, String paymentMethod, String paymentAmount, String remarks, String paymentDate) {
                JOptionPane.showMessageDialog(null, "Redirecing to Payment Details");
                dispose();
                PaymentDetails paymentDetails = new PaymentDetails(paymentID, payerName, desc, paymentMethod, paymentAmount, remarks, paymentDate);
            }
        });

        JTableHeader header = paymentTable.getTableHeader();
        header.setDefaultRenderer(new HeaderRenderer(paymentTable));
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 40));

        double totalAmount = calculateTotalAmount(payments);
        // Create a custom renderer for the last row
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (row == table.getRowCount() - 1) {
                    // Bold the text for the Total word
                    if (column == 3) { // Total word col = 3
                        setFont(getFont().deriveFont(Font.BOLD));
                        setFont(getFont().deriveFont(20.0f));
                    } else {
                        setFont(getFont().deriveFont(Font.PLAIN));
                    }
                }
                return c;
            }
        };

        // Set the custom renderer for the table
        paymentTable.setDefaultRenderer(Object.class, renderer);
        model.addRow(new Object[]{"", "", "", "Total (RM)", String.format("%.2f", totalAmount), "", ""});

        paymentTable.getColumnModel().getColumn(TOTAL_COLUMN_INDEX).setCellRenderer(new LastRowRenderer());
        Rectangle rect = paymentTable.getCellRect(paymentTable.getRowCount() - 1, 0, true);
        paymentTable.scrollRectToVisible(rect);
        paymentTable.setRowHeight(paymentTable.getRowHeight() + 20);
        for (int i = 0; i < paymentTable.getColumnCount(); i++) {
            TableColumn column = paymentTable.getColumnModel().getColumn(i);
            if (i == 4) {
                column.setPreferredWidth(200);
            } else {
                column.setPreferredWidth(150);
            }
        }
        return paymentTable;
    }

    private double calculateTotalAmount(List<Payment> payments) {
        double totalAmount = 0;
        for (Payment payment : payments) {
            totalAmount += payment.getAmount();
        }
        return totalAmount;
    }

    private void setComponentStyle(JComponent component, Color bgColor, Font font) {
        component.setBackground(bgColor);
        component.setFont(font);
    }
}

class HeaderRenderer extends JLabel implements TableCellRenderer {

    public HeaderRenderer(JTable table) {
        setOpaque(true);
        setFont(new Font("Segoe UI", Font.BOLD, 18));
        setBackground(new Color(169, 220, 247, 80));
        setForeground(Color.BLACK);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(JLabel.CENTER);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        setText(value != null ? value.toString() : "");
        return this;
    }
}

class LastRowRenderer extends JLabel implements TableCellRenderer {

    public LastRowRenderer() {
        setOpaque(true);
        setHorizontalAlignment(JLabel.CENTER);
        setFont(new Font("Segeo UI", Font.BOLD, 18));
        setBorder(BorderFactory.createEmptyBorder());
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (row == table.getRowCount() - 1) {
            setForeground(Color.BLACK);
            setBackground(Color.WHITE);
            setText(value != null ? value.toString() : "");
            setBorder(BorderFactory.createEmptyBorder());
            int totalWidth = 0;
            for (int i = 0; i < table.getColumnCount(); i++) {
                totalWidth += table.getColumnModel().getColumn(i).getWidth();
            }
            setPreferredSize(new Dimension(totalWidth, getPreferredSize().height));
            return this;
        } else {
            return table.getDefaultRenderer(value != null ? value.getClass() : Object.class)
                    .getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        }
    }
}
