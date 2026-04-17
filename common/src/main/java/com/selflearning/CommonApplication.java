package com.selflearning;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Slf4j
@SpringBootApplication
public class CommonApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommonApplication.class, args);
        log.info("Common Application started!");



        //Fail Fast
        try{
            List<String> failFastList = new ArrayList<>(List.of("A", "B", "C"));
            Iterator<String> itr = failFastList.iterator();
            while (itr.hasNext()) {
                System.out.println("failFastList - " + itr.next());
                failFastList.add("D"); // Structural modification during iteration
            }
        }catch (ConcurrentModificationException e){
            System.out.println("fail fast iterator failed on adding element during iteration");
        }
        //Fail safe
        CopyOnWriteArrayList<String> failSafeList = new CopyOnWriteArrayList<>(List.of("A", "B", "C"));
        Iterator<String> itr2 = failSafeList.iterator();
        while (itr2.hasNext()) {
            System.out.println("failSafeList - " + itr2.next());
            failSafeList.add("D"); // Allowed, no exception
        }
        System.out.println("Final list: " + failSafeList);
    }
}