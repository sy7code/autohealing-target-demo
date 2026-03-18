package com.example.demo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VulnerableApp {

    // 🔴 취약점 1: 하드코딩된 AWS 시크릿 키 (Snyk Secrets 감지용)
    // 이 패턴은 Snyk이 즉시 'Critical' 등급으로 잡아내는 전형적인 키 패턴입니다.
    private static final String AWS_SECRET_KEY = "wJalrXUtnFEMI/K7MDENG/bPfuekhbihlcCYEXAMPeihlfnsd";

    public void findUser(String userId) {
        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydb", "admin", "password123");
            Statement stmt = conn.createStatement();

            // 🔴 취약점 2: SQL 인젝션 (Snyk Code 감지용)
            // 사용자 입력값을 직접 쿼리에 붙이는 위험한 방식입니다.
            String sql = "SELECT * FROM users WHERE id = '" + userId + "'";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                System.out.println("User found: " + rs.getString("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showKey() {
        // 비밀키를 로그에 노출하는 행위
        System.out.println("Using AWS Key: " + AWS_SECRET_KEY);
    }
}
