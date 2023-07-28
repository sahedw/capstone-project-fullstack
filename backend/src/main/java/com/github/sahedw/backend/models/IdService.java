package com.github.sahedw.backend.models;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@NoArgsConstructor
@Service
public class IdService {

    public String randomId(){
        return UUID.randomUUID().toString();
    }
}
