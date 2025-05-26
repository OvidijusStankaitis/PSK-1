package com.psk.autoproject.extensibility;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Specializes;

@Specializes
@ApplicationScoped
@GreetingBinding
public class SpecialGreetingService extends FormalGreetingService {
    @Override
    public String greet(String name) {
        return "Special Welcome, " + name + " !";
    }
}
