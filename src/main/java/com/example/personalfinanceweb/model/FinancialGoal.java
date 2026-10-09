package com.example.personalfinanceweb.model;

import java.time.LocalDate;

public class FinancialGoal {

    private int id;
    private int userId;
    private String description;
    private double targetAmount;
    private double currentAmount;
    private LocalDate deadline;

    public FinancialGoal() {
    }

    public FinancialGoal(
            int id,
            int userId,
            String description,
            double targetAmount,
            double currentAmount,
            LocalDate deadline) {

        this.id = id;
        this.userId = userId;
        this.description = description;
        this.targetAmount = targetAmount;
        this.currentAmount = currentAmount;
        this.deadline = deadline;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(double currentAmount) {
        this.currentAmount = currentAmount;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    public void setDeadline(LocalDate deadline) {
        this.deadline = deadline;
    }
}
