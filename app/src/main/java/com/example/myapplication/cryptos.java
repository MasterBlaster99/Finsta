package com.example.myapplication;

import com.scottyab.aescrypt.AESCrypt;

import java.security.GeneralSecurityException;

public class cryptos {
    public String encryptString(String s){
        try {
            String encrypted = AESCrypt.encrypt("key",s);
            return encrypted;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return s;
    }
    public String decryptString(String s){
        try {
            String decrypt = AESCrypt.decrypt("key",s);
          return decrypt;
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        return s;
    }
}
