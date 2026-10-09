package com.example.personalfinanceweb;

import com.example.personalfinanceweb.model.FinancialGoal;

import com.example.personalfinanceweb.dao.BudgetDAO;
import com.example.personalfinanceweb.dao.ExpenseDAO;
import com.example.personalfinanceweb.dao.FinancialGoalDAO;
import com.example.personalfinanceweb.dao.UserDAO;

import com.example.personalfinanceweb.model.Budget;
import com.example.personalfinanceweb.model.Expense;
import com.example.personalfinanceweb.model.FinancialGoal;
import com.example.personalfinanceweb.model.User;

import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@Controller
public class HomeController {

    // ================= HOME =================

    @GetMapping("/")
    public String home() {
        return "login";
    }


    // ================= LOGIN =================

    @PostMapping("/login")
    public String login(
            String email,
            String password,
            Model model,
            HttpSession session) {

        UserDAO userDAO = new UserDAO();

        User user = userDAO.loginUser(email, password);

        if (user != null) {

            session.setAttribute("user", user);

            return "redirect:/dashboard";
        }

        model.addAttribute(
                "error",
                "Invalid email or password"
        );

        return "login";
    }


    // ================= DASHBOARD =================

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        return "dashboard";
    }


    // ================= REGISTER =================

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }


    @PostMapping("/register")
    public String register(
            String name,
            String email,
            String password,
            String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {

            model.addAttribute(
                    "error",
                    "Passwords do not match"
            );

            return "register";
        }

        User user = new User(
                0,
                name,
                email,
                password,
                "USER"
        );

        UserDAO userDAO = new UserDAO();

        boolean success =
                userDAO.registerUser(user);

        if (success) {
            return "redirect:/";
        }

        model.addAttribute(
                "error",
                "Registration failed. Email may already exist."
        );

        return "register";
    }


    // ================= LOGOUT =================

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }


    // ================= EXPENSES =================

    @GetMapping("/expenses")
    public String expenses(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        ExpenseDAO expenseDAO =
                new ExpenseDAO();

        model.addAttribute(
                "expenses",
                expenseDAO.getExpensesByUser(
                        user.getId()
                )
        );

        return "expenses";
    }
    @GetMapping("/expenses/edit")
    public String editExpensePage(
            int id,
            Model model,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        ExpenseDAO expenseDAO = new ExpenseDAO();

        Expense expense = expenseDAO.getExpenseById(id, user.getId());

        if (expense == null) {
            return "redirect:/expenses";
        }

        model.addAttribute("expense", expense);

        return "edit-expense";
    }


    @PostMapping("/expenses/update")
    public String updateExpense(
            int id,
            double amount,
            String category,
            String date,
            String description,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        Expense expense = new Expense(
                id,
                user.getId(),
                amount,
                category,
                java.time.LocalDate.parse(date),
                description
        );

        ExpenseDAO expenseDAO = new ExpenseDAO();

        expenseDAO.updateExpense(expense);

        return "redirect:/expenses";
    }


    @PostMapping("/expenses/delete")
    public String deleteExpense(
            int id,
            HttpSession session) {

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        ExpenseDAO expenseDAO = new ExpenseDAO();

        expenseDAO.deleteExpense(id, user.getId());

        return "redirect:/expenses";
    }

    @PostMapping("/expenses/add")
    public String addExpense(
            double amount,
            String category,
            String date,
            String description,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        Expense expense =
                new Expense(
                        0,
                        user.getId(),
                        amount,
                        category,
                        LocalDate.parse(date),
                        description
                );

        ExpenseDAO expenseDAO =
                new ExpenseDAO();

        expenseDAO.addExpense(expense);

        return "redirect:/expenses";
    }





    // ================= BUDGETS =================

    @GetMapping("/budgets")
    public String budgets(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        BudgetDAO budgetDAO =
                new BudgetDAO();

        model.addAttribute(
                "budgets",
                budgetDAO.getBudgetsByUser(
                        user.getId()
                )
        );

        return "budgets";
    }


    @PostMapping("/budgets/add")
    public String addBudget(
            double amount,
            String category,
            String startDate,
            String endDate,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        Budget budget =
                new Budget(
                        0,
                        user.getId(),
                        category,
                        amount,
                        LocalDate.parse(startDate),
                        LocalDate.parse(endDate)
                );

        BudgetDAO budgetDAO =
                new BudgetDAO();

        budgetDAO.addBudget(budget);

        return "redirect:/budgets";
    }


    @PostMapping("/budgets/delete")
    public String deleteBudget(
            int id,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        BudgetDAO budgetDAO =
                new BudgetDAO();

        budgetDAO.deleteBudget(
                id,
                user.getId()
        );

        return "redirect:/budgets";
    }


    // ================= FINANCIAL GOALS =================

    @GetMapping("/financial-goals")
    public String financialGoals(
            Model model,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        FinancialGoalDAO goalDAO =
                new FinancialGoalDAO();

        model.addAttribute(
                "goals",
                goalDAO.getGoalsByUser(
                        user.getId()
                )
        );

        return "financial-goals";
    }


    @PostMapping("/financial-goals/add")
    public String addFinancialGoal(
            String description,
            double targetAmount,
            double currentAmount,
            String deadline,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        FinancialGoal goal =
                new FinancialGoal(
                        0,
                        user.getId(),
                        description,
                        targetAmount,
                        currentAmount,
                        LocalDate.parse(deadline)
                );

        FinancialGoalDAO goalDAO =
                new FinancialGoalDAO();

        goalDAO.addGoal(goal);

        return "redirect:/financial-goals";
    }


    @PostMapping("/financial-goals/delete")
    public String deleteFinancialGoal(
            int id,
            HttpSession session) {

        User user =
                (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/";
        }

        FinancialGoalDAO goalDAO =
                new FinancialGoalDAO();

        goalDAO.deleteGoal(
                id,
                user.getId()
        );

        return "redirect:/financial-goals";
    }
}