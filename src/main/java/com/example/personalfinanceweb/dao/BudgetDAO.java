package com.example.personalfinanceweb.dao;

import com.example.personalfinanceweb.model.Budget;
import com.example.personalfinanceweb.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetDAO {

    // ADD BUDGET
    public boolean addBudget(Budget budget) {
        String sql = "INSERT INTO budgets (user_id, category, amount, start_date, end_date) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, budget.getUserId());
            statement.setString(2, budget.getCategory());
            statement.setDouble(3, budget.getAmount());
            statement.setDate(4, Date.valueOf(budget.getStartDate()));
            statement.setDate(5, Date.valueOf(budget.getEndDate()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // VIEW BUDGETS
    public List<Budget> getBudgetsByUser(int userId) {
        List<Budget> budgets = new ArrayList<>();
        String sql = "SELECT * FROM budgets WHERE user_id = ? ORDER BY start_date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    budgets.add(new Budget(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("category"),
                            resultSet.getDouble("amount"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate()
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return budgets;
    }

    // GET ONE BUDGET FOR EDITING
    public Budget getBudgetById(int id, int userId) {
        String sql = "SELECT * FROM budgets WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return new Budget(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getString("category"),
                            resultSet.getDouble("amount"),
                            resultSet.getDate("start_date").toLocalDate(),
                            resultSet.getDate("end_date").toLocalDate()
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // UPDATE BUDGET
    public boolean updateBudget(Budget budget) {
        String sql = "UPDATE budgets SET category = ?, amount = ?, start_date = ?, end_date = ? WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, budget.getCategory());
            statement.setDouble(2, budget.getAmount());
            statement.setDate(3, Date.valueOf(budget.getStartDate()));
            statement.setDate(4, Date.valueOf(budget.getEndDate()));
            statement.setInt(5, budget.getId());
            statement.setInt(6, budget.getUserId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // DELETE BUDGET
    public boolean deleteBudget(int id, int userId) {
        String sql = "DELETE FROM budgets WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}