package com.example.target;

import java.io.*;
import java.sql.*;
import java.net.*;
import javax.servlet.http.*;

public class VulnerableApp {

    // 1. 하드코딩된 관리자 비밀번호 (Secrets)
    private static final String PASSWORD = "SUPER_PASSWORknkttD";

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        // 2. Command Injection (명령어 주입)
        // 사용자가 보낸 파일명으로 시스템 명령어를 직접 실행하는 매우 위험한 코드입니다.
        String fileName = request.getParameter("file");
        Runtime.getRuntime().exec("chmod 777 " + fileName);

        // 3. SQL Injection (SQL 주입) 
        // 입력값을 검증 없이 쿼리에 그대로 이어붙였습니다.
        String userId = request.getParameter("id");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/db", "user", "pass");
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users WHERE id = '" + userId + "'");

        // 4. Path Traversal (경로 조작)
        // 사용자가 상위 디렉토리(../../)로 접근하여 민감한 파일을 읽을 수 있습니다.
        File file = new File("/var/data/uploads/" + fileName);
        FileInputStream fis = new FileInputStream(file);

        // 5. SSRF (서버 측 요청 위조)
        // 사용자가 제공한 URL로 서버가 직접 요청을 보냅니다. 내부망 공격에 취약합니다.
        String targetUrl = request.getParameter("url");
        URL url = new URL(targetUrl);
        URLConnection urlConn = url.openConnection();
        urlConn.connect();
    }
}
