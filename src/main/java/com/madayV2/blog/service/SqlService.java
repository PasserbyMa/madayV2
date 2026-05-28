package com.madayV2.blog.service;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SqlService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Map<String, Object> execute(String sql) {
        String upper = sql.trim().toUpperCase();
        if (upper.startsWith("SELECT")) {
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql.trim());
            return Map.of("type", "select", "rows", rows, "count", rows.size());
        } else if (upper.startsWith("INSERT") || upper.startsWith("UPDATE") || upper.startsWith("DELETE")) {
            int affected = jdbcTemplate.update(sql.trim());
            return Map.of("type", "dml", "affected", affected);
        } else {
            jdbcTemplate.execute(sql.trim());
            return Map.of("type", "ddl", "message", "실행 완료");
        }
    }
}