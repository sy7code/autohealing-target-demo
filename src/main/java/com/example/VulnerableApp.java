package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class VulnerableApp {

    // 1. 하드코딩된 민감 정보 (Snyk: High/Critical - Hardcoded Secret)
    private static final String AWS_SECRET_KEY = "AE34567890/PEY123456";

    public void processData(String userInput, String cmdInput) {
        try {
            // 2. SQL 인젝션 취약점 (Snyk: High - SQL Injection)
            // 사용자 입력을 검증 없이 쿼리에 직접 결합합니다.
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/db", "user", "pass");
            Statement stmt = conn.createStatement();
            String query = "SELECT * FROM users WHERE username = '" + userInput + "'";
            ResultSet rs = stmt.executeQuery(query);

            // 3. 커맨드 인젝션 취약점 (Snyk: High/Critical - Command Injection)
            // 사용자 입력이 OS 명령어로 그대로 들어갑니다.
            String command = "ls -l " + cmdInput;
            Process process = Runtime.getRuntime().exec(command);
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String getSecret() {
        // 민감한 키를 로그나 리턴값으로 노출하는 행위
        return AWS_SECRET_KEY;
    }
}
