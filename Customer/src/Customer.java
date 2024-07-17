import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class CustomerApp extends JFrame {

    // Customer class
    static class Customer {
        private int customerId;
        private String lastName;
        private String firstName;
        private String phone;

        public Customer(int customerId, String lastName, String firstName, String phone) {
            this.customerId = customerId;
            this.lastName = lastName;
            this.firstName = firstName;
            this.phone = phone;
        }

        public int getCustomerId() {
            return customerId;
        }

        public String getLastName() {
            return lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getPhone() {
            return phone;
        }
    }

    // Simulated Customer Data Access Object
    static class CustomerDAO {
        private List<Customer> customers;

        public CustomerDAO() {
            customers = new ArrayList<>();
            for (int i = 1; i <= 20; i++) {
                customers.add(new Customer(i, "Last" + i, "First" + i, "123-456-789" + i));
            }
        }

        public List<Customer> getCustomers() {
            return customers;
        }
    }

    private JTable table;
    private DefaultTableModel tableModel;
    private CustomerDAO customerDAO;
    private int currentIndex = 0;

    public CustomerApp() {
        customerDAO = new CustomerDAO();
        List<Customer> customers = customerDAO.getCustomers();

        setTitle("Customer Information");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the table
        String[] columnNames = {"ID", "Last Name", "First Name", "Phone"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Add buttons
        JPanel buttonPanel = new JPanel();
        JButton previousButton = new JButton("Previous");
        JButton nextButton = new JButton("Next");

        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex > 0) {
                    currentIndex--;
                    showCustomers(customers);
                }
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentIndex < customers.size() - 1) {
                    currentIndex++;
                    showCustomers(customers);
                }
            }
        });

        buttonPanel.add(previousButton);
        buttonPanel.add(nextButton);
        add(buttonPanel, BorderLayout.SOUTH);

        showCustomers(customers);
    }

    private void showCustomers(List<Customer> customers) {
        tableModel.setRowCount(0); // Clear the table
        int endIndex = Math.min(currentIndex + 7, customers.size());
        for (int i = currentIndex; i < endIndex; i++) {
            Customer customer = customers.get(i);
            tableModel.addRow(new Object[]{
                    customer.getCustomerId(),
                    customer.getLastName(),
                    customer.getFirstName(),
                    customer.getPhone()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerApp app = new CustomerApp();
            app.setVisible(true);
        });
    }
}
