package com.gestiune.controller;

import com.gestiune.model.entities.Category;
import com.gestiune.model.dao.impl.CategoryDAOImpl;
import com.gestiune.model.dao.interfaces.CategoryDAO;
import com.gestiune.view.CategoryPanel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CategoryController {
    private final CategoryPanel categoryPanel;
    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryPanel categoryPanel) {
        this.categoryPanel = categoryPanel;
        this.categoryDAO = new CategoryDAOImpl();

        this.categoryPanel.getAddButton().addActionListener(e -> addCategory());
        this.categoryPanel.getUpdateButton().addActionListener(e -> updateCategory());
        this.categoryPanel.getDeleteButton().addActionListener(e -> deleteCategory());
        this.categoryPanel.getSearchButton().addActionListener(e -> searchCategory());
        this.categoryPanel.getReportButton().addActionListener(e -> generateReport());
    }

    private void addCategory() {
        try {
            String name = categoryPanel.getNameField().getText();
            Category category = new Category();
            category.setName(name);
            categoryDAO.insert(category);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error adding category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateCategory() {
        try {
            int selectedRow = categoryPanel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(categoryPanel, "Please select a category to update.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = categoryPanel.getNameField().getText();
            Category category = categoryDAO.getByName(name);
            category.setName(name);
            categoryDAO.update(category);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error updating category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCategory() {
        try {
            int selectedRow = categoryPanel.getTable().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(categoryPanel, "Please select a category to delete.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String name = name = (String) categoryPanel.getTable().getValueAt(selectedRow, 0);
            categoryDAO.delete(name);
            loadData();
            JOptionPane.showMessageDialog(categoryPanel, "Category deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error deleting category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void searchCategory() {
        try {
            String searchTerm = categoryPanel.getSearchTextField().getText();
            List<Category> categories = categoryDAO.searchByName(searchTerm);
            updateTable(categories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error searching category: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void loadData() {
        try {
            List<Category> categories = categoryDAO.getAll();
            updateTable(categories);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(categoryPanel, "Error loading data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTable(List<Category> categories) {
        DefaultTableModel tableModel = (DefaultTableModel) categoryPanel.getTable().getModel();
        tableModel.setRowCount(0);
        for (Category category : categories) {
            tableModel.addRow(new Object[]{category.getName()});
        }
    }

    private void generateReport() {
        try {
            List<Category> categories = categoryDAO.getAll();

            List<Map<String, Object>> dataList = new ArrayList<>();
            for (Category category : categories) {
                Map<String, Object> item = new HashMap<>();
                item.put("name", category.getName());
                dataList.add(item);
            }

            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(dataList);

            Map<String, Object> parameters = new HashMap<>();
            parameters.put("dataGenerare", new Date());
            parameters.put("totalCategories", categories.size());

            String reportPath = "/reports/CategoryReport.jrxml";
            InputStream reportStream = getClass().getResourceAsStream(reportPath);
            if (reportStream == null) {
                throw new Exception("Nu pot găsi fișierul " + reportPath);
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(
                categoryPanel,
                "Eroare la generarea raportului: " + e.getMessage(),
                "Eroare",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}