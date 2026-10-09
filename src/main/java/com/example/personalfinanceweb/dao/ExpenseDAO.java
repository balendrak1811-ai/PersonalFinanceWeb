package com.example.personalfinanceweb.dao;

import com.example.personalfinanceweb.model.Expense;
import com.example.personalfinanceweb.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExpenseDAO {

    // Add a new expense
    public boolean addExpense(Expense expense) {

        String sql = "INSERT INTO expenses " +
                "(user_id, amount, category, date, description) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, expense.getUserId());
            statement.setDouble(2, expense.getAmount());
            statement.setString(3, expense.getCategory());
            statement.setDate(4,
                    java.sql.Date.valueOf(expense.getDate()));
            statement.setString(5, expense.getDescription());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get expenses belonging to a user
    public List<Expense> getExpensesByUser(int userId) {

        List<Expense> expenses = new ArrayList<>();

        String sql = "SELECT * FROM expenses " +
                "WHERE user_id = ? ORDER BY date DESC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {

                    Expense expense = new Expense(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("category"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getString("description")
                    );

                    expenses.add(expense);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return expenses;
    }

    // Get one expense belonging to a particular user
    public Expense getExpenseById(int expenseId, int userId) {

        String sql = "SELECT * FROM expenses WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, expenseId);
            statement.setInt(2, userId);

            try (ResultSet resultSet = statement.executeQuery()) {

                if (resultSet.next()) {
                    return new Expense(
                            resultSet.getInt("id"),
                            resultSet.getInt("user_id"),
                            resultSet.getDouble("amount"),
                            resultSet.getString("category"),
                            resultSet.getDate("date").toLocalDate(),
                            resultSet.getString("description")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Update an existing expense
    public boolean updateExpense(Expense expense) {

        String sql = "UPDATE expenses " +
                "SET amount = ?, category = ?, date = ?, description = ? " +
                "WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setDouble(1, expense.getAmount());
            statement.setString(2, expense.getCategory());
            statement.setDate(3,
                    java.sql.Date.valueOf(expense.getDate()));
            statement.setString(4, expense.getDescription());
            statement.setInt(5, expense.getId());
            statement.setInt(6, expense.getUserId());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Delete an expense belonging to a particular user
    public boolean deleteExpense(int expenseId, int userId) {

        String sql = "DELETE FROM expenses WHERE id = ? AND user_id = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, expenseId);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
