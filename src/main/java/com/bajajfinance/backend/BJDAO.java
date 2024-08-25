package com.bajajfinance.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class BJDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveDataEntry(String userId, String email, String rollNumber, List<String> numbers, List<String> alphabets, List<String> highestLowercaseAlphabet) {
        String numbersStr = String.join(",", numbers);
        String alphabetsStr = String.join(",", alphabets);
        String highestLowercaseAlphabetStr = String.join(",", highestLowercaseAlphabet);

        String sql = "INSERT INTO BAJAJTABLE (user_id, email, roll_number, numbers, alphabets, highest_lowercase_alphabet) VALUES (?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(sql, userId, email, rollNumber, numbersStr, alphabetsStr, highestLowercaseAlphabetStr);
    }

    public Optional<Integer> getOperationCode() {
        String sql = "SELECT operation_code FROM BAJAJTABLE LIMIT 1";  // Ensure `operation_code` is a valid column
        Integer operationCode = jdbcTemplate.queryForObject(sql, Integer.class);
        return Optional.ofNullable(operationCode);
    }
}

