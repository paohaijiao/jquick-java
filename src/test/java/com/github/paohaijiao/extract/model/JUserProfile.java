package com.github.paohaijiao.extract.model;

import com.google.gson.Gson;

import java.util.*;

public class JUserProfile {
    private String username;
    private int age;
    private List<String> hobbies;
    private Map<String, Double> skillRatings;

    public JUserProfile(String username, int age, List<String> hobbies, Map<String, Double> skillRatings) {
        this.username = username;
        this.age = age;
        this.hobbies = hobbies;
        this.skillRatings = skillRatings;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    public Map<String, Double> getSkillRatings() {
        return skillRatings;
    }

    public void setSkillRatings(Map<String, Double> skillRatings) {
        this.skillRatings = skillRatings;
    }

}

class Department {
    private String name;
    private int employeeCount;
    private double budget;

    public Department(String name, int employeeCount, double budget) {
        this.name = name;
        this.employeeCount = employeeCount;
        this.budget = budget;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }
    public static void main(String[] args) {
        Map<String, Object> complexMap = new HashMap<>();
        complexMap.put("companyName", "TechCorp Inc.");
        complexMap.put("foundedYear", 2005);
        complexMap.put("topProducts", Arrays.asList("ProductA", "ProductB", "ProductC"));
        Map<String, Integer> revenueByYear = new LinkedHashMap<>();
        revenueByYear.put("2020", 5000000);
        revenueByYear.put("2021", 6500000);
        revenueByYear.put("2022", 8200000);
        complexMap.put("revenueByYear", revenueByYear);

        List<String> hobbies1 = Arrays.asList("Reading", "Hiking", "Photography");
        Map<String, Double> skills1 = new HashMap<>();
        skills1.put("Java", 4.5);
        skills1.put("Python", 4.2);
        skills1.put("SQL", 4.0);
        JUserProfile user1 = new JUserProfile("john_doe", 32, hobbies1, skills1);

        List<String> hobbies2 = Arrays.asList("Gaming", "Cooking");
        Map<String, Double> skills2 = new HashMap<>();
        skills2.put("JavaScript", 4.7);
        skills2.put("React", 4.5);
        skills2.put("Node.js", 4.3);
        JUserProfile user2 = new JUserProfile("jane_smith", 28, hobbies2, skills2);
        Map<String, JUserProfile> users = new HashMap<>();
        users.put("user1", user1);
        users.put("user2", user2);
        complexMap.put("employees", users);

        Department devDept = new Department("Development", 45, 1200000.0);
        Department salesDept = new Department("Sales", 12, 800000.0);
        Department hrDept = new Department("HR", 5, 400000.0);

        Map<String, Department> departments = new TreeMap<>();
        departments.put("dev", devDept);
        departments.put("sales", salesDept);
        departments.put("hr", hrDept);
        complexMap.put("departments", departments);
        Map<String, Map<String, Object>> officeLocations = new HashMap<>();

        Map<String, Object> hq = new HashMap<>();
        hq.put("address", "123 Tech Street, Silicon Valley");
        hq.put("sizeSqft", 25000);
        hq.put("floors", 5);

        Map<String, Object> branch1 = new HashMap<>();
        branch1.put("address", "456 Innovation Ave, New York");
        branch1.put("sizeSqft", 15000);
        branch1.put("floors", 3);

        officeLocations.put("headquarters", hq);
        officeLocations.put("east_coast_branch", branch1);
        complexMap.put("officeLocations", officeLocations);
        System.out.println("完整Map结构:");
        Gson gson = new Gson();
        System.out.println(gson.toJson(complexMap));

    }


}