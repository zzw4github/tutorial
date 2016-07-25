package com.infosea.spring;

import org.springframework.boot.SpringApplication;

/**
 * Created by infosea on 2016/7/6.
 */
public class Main {
    public static void main(String [] args) {
        System.exit(SpringApplication.exit(SpringApplication.run(
                BatchConfiguration.class, args)));
    }
}
