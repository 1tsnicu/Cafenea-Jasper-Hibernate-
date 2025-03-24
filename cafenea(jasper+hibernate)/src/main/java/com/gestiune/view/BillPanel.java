package com.gestiune.view;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;

public class BillPanel extends JPanel {
    private static final Color COFFEE_BROWN = new Color(111, 78, 55);
    private static final Color LIGHT_BROWN = new Color(181, 136, 99);
    private static final Color BORDER_COLOR = new Color(200, 200, 200);
    
    private JComboBox<String> idCombo;
    private JTextField idField;
    private JTextField product_nameField;
    private JTextField category_nameField;
    private JTextField priceField;
    private JTextField order_dateField;
    private JTextField searchTextField;
    private JTable table;
    private JButton addButton;
    private JButton updateButton;
    private JButton deleteButton;
    private JButton searchButton;
    private JButton reportButton;

    public BillPanel() {
        setLayout(new BorderLayout(0, 0));
        setBackground(new Color(245, 222, 179)); // Light beige background

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 222, 179)); // Light beige background
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        searchPanel.setBackground(new Color(245, 222, 179)); // Light beige background

        idCombo = new JComboBox<>();
        idCombo.setPreferredSize(new Dimension(200, 35));
        idCombo.setBackground(Color.WHITE);
        idCombo.setFont(new Font("Serif", Font.PLAIN, 14));
        idCombo.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        
        searchTextField = new JTextField();
        searchTextField.setPreferredSize(new Dimension(350, 35));
        searchTextField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchTextField.setFont(new Font("Serif", Font.PLAIN, 14));
        searchTextField.setForeground(Color.GRAY);
        addPlaceholder(searchTextField, "Căutare vanzare...");
        
        searchButton = createStyledButton("Căutare");
        
        searchPanel.add(idCombo);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchTextField);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(searchButton);

        JPanel inputPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        inputPanel.setBackground(new Color(245, 222, 179)); // Light beige background
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        idField = createStyledTextField();
        product_nameField = createStyledTextField();
        category_nameField = createStyledTextField();
        priceField = createStyledTextField();
        order_dateField = createStyledTextField();

        addPlaceholder(idField, "Introduceți id-ul");
        addPlaceholder(product_nameField, "Introduceți numele produsului");
        addPlaceholder(category_nameField, "Introduceți categoria");
        addPlaceholder(priceField, "Introduceți pretul");
        addPlaceholder(order_dateField, "Introduceți data comenzii");
        
        inputPanel.add(createInputGroup("Id", idField));
        inputPanel.add(createInputGroup("Produs", product_nameField));
        inputPanel.add(createInputGroup("Categorie", category_nameField));
        inputPanel.add(createInputGroup("Pret", priceField));
        inputPanel.add(createInputGroup("Data", order_dateField));

         JPanel topInputPanel = new JPanel(new BorderLayout());
        topInputPanel.setBackground(new Color(245, 222, 179)); // Light beige background
        topInputPanel.add(searchPanel, BorderLayout.NORTH);
        topInputPanel.add(inputPanel, BorderLayout.CENTER);
        
        topPanel.add(topInputPanel, BorderLayout.CENTER);

         table = new JTable();
        DefaultTableModel model = new DefaultTableModel(
            new String[]{"Id", "Produs", "Categorie", "Pret", "Data"}, 0
        );
        table.setModel(model);
        styleTable(table);
        
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getViewport().setBackground(new Color(245, 222, 179)); // Light beige background
        scrollPane.setBackground(new Color(245, 222, 179)); // Light beige background

         JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        buttonPanel.setBackground(new Color(245, 222, 179)); // Light beige background
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, BORDER_COLOR));

        addButton = createStyledButton("Adaugă");
        updateButton = createStyledButton("Modifică");
        deleteButton = createStyledButton("Șterge");
        reportButton = createStyledButton("Generare Raport Bill");
        
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(reportButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        populateComboBox();
    }
    public JTextField getIdField() { return idField; }
    public JTextField getProductNameField() { return product_nameField; }
    public JTextField getCategoryNameField() { return category_nameField; }
    public JTextField getPriceField() { return priceField; }
    public JTextField getOrderDateField() { return order_dateField; }

    public JTextField getSearchTextField() { return searchTextField; }
    public JComboBox<String> getIdCombo() { return idCombo; }
    public JTable getTable() { return table; }
    public JButton getAddButton() { return addButton; }
    public JButton getUpdateButton() { return updateButton; }
    public JButton getDeleteButton() { return deleteButton; }
    public JButton getSearchButton() { return searchButton; }
    public JButton getReportButton() { return reportButton; }

    private JPanel createInputGroup(String labelText, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(new Color(245, 222, 179)); // Light beige background
        
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Serif", Font.BOLD, 14));
        label.setForeground(Color.BLACK);
        
        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        
        return panel;
    }

    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setPreferredSize(new Dimension(200, 35));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setFont(new Font("Serif", Font.PLAIN, 14));
        field.setBackground(Color.WHITE);
        field.setForeground(Color.GRAY);
        return field;
    }

    private JTextArea createStyledTextArea() {
        JTextArea area = new JTextArea();
        area.setRows(2);
        area.setFont(new Font("Serif", Font.PLAIN, 14));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(2, 5, 2, 5)
        ));
        return area;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Serif", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(COFFEE_BROWN);
        button.setPreferredSize(new Dimension(130, 40));
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.DARK_GRAY, 2),
            BorderFactory.createEmptyBorder(8, 25, 8, 25)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(LIGHT_BROWN);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(COFFEE_BROWN);
            }
        });
        
        return button;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(new Color(230, 230, 230));
        table.setBackground(new Color(255, 248, 220)); // Light golden background
        table.setFont(new Font("Serif", Font.PLAIN, 14));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(255, 248, 220)); // Light golden background
        header.setForeground(Color.BLACK);
        header.setFont(new Font("Serif", Font.PLAIN, 14));
        header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER_COLOR));
        header.setPreferredSize(new Dimension(header.getWidth(), 30));
        ((DefaultTableCellRenderer)header.getDefaultRenderer()).setHorizontalAlignment(JLabel.LEFT);
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);
        leftRenderer.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(leftRenderer);
        }

        table.setSelectionBackground(new Color(240, 240, 240));
        table.setSelectionForeground(Color.BLACK);
    }

    private void addPlaceholder(JTextField textField, String placeholder) {
        textField.setText(placeholder);
        textField.setForeground(Color.GRAY);
        
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textField.getText().equals(placeholder)) {
                    textField.setText("");
                    textField.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textField.getText().isEmpty()) {
                    textField.setText(placeholder);
                    textField.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void addPlaceholder(JTextArea textArea, String placeholder) {
        textArea.setText(placeholder);
        textArea.setForeground(Color.GRAY);
        
        textArea.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textArea.getText().equals(placeholder)) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }
            
            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText(placeholder);
                    textArea.setForeground(Color.GRAY);
                }
            }
        });
    }

    private void populateComboBox() {
        idCombo.removeAllItems();
        idCombo.addItem("Toate");
        idCombo.addItem("Id");
        idCombo.addItem("Nume");
        idCombo.addItem("Categorie");
        idCombo.addItem("Pret");
        idCombo.addItem("Data");
    }
}