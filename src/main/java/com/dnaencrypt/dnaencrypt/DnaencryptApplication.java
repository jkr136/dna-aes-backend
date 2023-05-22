package com.dnaencrypt.dnaencrypt;

import java.util.InputMismatchException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class DnaencryptApplication {

    public static void main(String[] args) {
        SpringApplication.run(DnaencryptApplication.class, args);
    }
}

@RestController
@CrossOrigin(origins = "*")
class EncryptionController {
    @PostMapping("/encrypt")
    public String encryptText(@RequestBody EncryptionRequest request) throws InputMismatchException, Exception {
    	String text = request.getText();
    	String key = request.getKey();
        String encryptedText = Encryption.encrypt(text,key);
        return encryptedText;
    }
    
    @PostMapping("/decrypt")
    public String decryptText(@RequestBody EncryptionRequest request) throws InputMismatchException, Exception {
    	String text = request.getText();
    	String key = request.getKey();
        String decryptedText = Decryption.decrypt(text,key);
        return decryptedText;
    }
    
}

class EncryptionRequest {
    private String text;
    private String key;
    
    public String getText() {
        return text;
    }
    
    public void setText(String text) {
        this.text = text;
    }
    
    public String getKey() {
        return key;
    }
    
    public void setKey(String key) {
        this.key = key;
    }
}

//class EncryptionClass {
//    public static String encrypt(String text) {
//        // Your encryption logic here
//        // Process the provided text and return the encrypted result
//    }
