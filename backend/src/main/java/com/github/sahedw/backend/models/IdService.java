package com.github.sahedw.backend.models;

import java.util.UUID;

public class IdService {
    private IdService() {
    }

    public static String randomId(){
        return UUID.randomUUID().toString();
    }
}
