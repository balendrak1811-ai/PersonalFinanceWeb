package com.example.personalfinanceweb.dao;

import com.example.personalfinanceweb.model.FinancialGoal;
import com.example.personalfinanceweb.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class FinancialGoalDAO {

    // =========================
    // ADD FINANCIAL GOAL
    // =========================

    public boolean addGoal(FinancialGoal goal) {

        String sql =
                "INSERT INTO financial_goals " +
                        "(user_id, description, target_amount, current_amount, deadline) " +
                        "VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, goal.getUserId());
            statement.setString(2, goal.getDescription());
            statement.setDouble(3, goal.getTargetAmount());
            statement.setDouble(4, goal.getCurrentAmount());
            statement.setDate(5, Date.valueOf(goal.getDeadline()));

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    // =========================
    // GET GOALS OF USER
    // =========================

    public List<FinancialGoal> getGoalsByUser(int userId) {

        List<FinancialGoal> goals = new ArrayList<>();

        String sql =
                "SELECT * FROM financial_goals " +
                        "WHERE user_id = ? " +
                        "ORDER BY deadline ASC";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {

                FinancialGoal goal = new FinancialGoal(
                        resultSet.getInt("id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("description"),
                        resultSet.getDouble("target_amount"),
                        resultSet.getDouble("current_amount"),
                        resultSet.getDate("deadline").toLocalDate()
                );

                goals.add(goal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return goals;
    }


    // =========================
    // DELETE FINANCIAL GOAL
    // =========================

    public boolean deleteGoal(int id, int userId) {

        String sql =
                "DELETE FROM financial_goals " +
                        "WHERE id = ? AND user_id = ?";

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