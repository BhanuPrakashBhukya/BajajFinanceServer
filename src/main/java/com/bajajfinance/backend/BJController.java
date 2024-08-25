package com.bajajfinance.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/bajajfinance")
@CrossOrigin(origins = "*")
class BjController {

    @Autowired
    private BJDAO bJDAO;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getOperationCode() {
        Map<String, Object> response = new HashMap<>();
        Optional<Integer> operationCode = bJDAO.getOperationCode();

        if (operationCode.isPresent()) {
            response.put("operation_code", operationCode.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("error", "Operation code not found");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> processData(@RequestBody Map<String, Object> requestData) {
        Map<String, Object> response = new HashMap<>();

        // Validate and parse data from request
        List<String> data = (List<String>) requestData.get("data");
        if (data == null) {
            response.put("is_success", false);
            response.put("error", "Invalid data format");
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        List<String> numbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> highestLowercaseAlphabet = new ArrayList<>();

        char highestChar = 'a' - 1;
        for (String item : data) {
            if (item.matches("\\d+")) {
                numbers.add(item);
            } else if (item.matches("[a-zA-Z]")) {
                alphabets.add(item);
                if (item.matches("[a-z]") && item.charAt(0) > highestChar) {
                    highestChar = item.charAt(0);
                }
            }
        }

        if (highestChar >= 'a') {
            highestLowercaseAlphabet.add(String.valueOf(highestChar));
        }

        // Replace these with actual user details
        String userId = "john_doe_17091999";
        String email = "john@xyz.com";
        String rollNumber = "ABCD123";

        response.put("is_success", true);
        response.put("user_id", userId);
        response.put("email", email);
        response.put("roll_number", rollNumber);
        response.put("numbers", numbers);
        response.put("alphabets", alphabets);
        response.put("highest_lowercase_alphabet", highestLowercaseAlphabet);

        bJDAO.saveDataEntry(userId, email, rollNumber, numbers, alphabets, highestLowercaseAlphabet);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

