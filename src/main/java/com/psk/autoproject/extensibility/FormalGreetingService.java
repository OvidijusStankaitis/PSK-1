package com.psk.autoproject.extensibility;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
@GreetingBinding
public class FormalGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Good day, " + name + ".";
    }
}
