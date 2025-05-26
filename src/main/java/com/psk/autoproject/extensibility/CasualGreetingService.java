package com.psk.autoproject.extensibility;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

@Alternative
@ApplicationScoped
@GreetingBinding
public class CasualGreetingService implements GreetingService {
    @Override
    public String greet(String name) {
        return "Hey " + name + "!";
    }
}
