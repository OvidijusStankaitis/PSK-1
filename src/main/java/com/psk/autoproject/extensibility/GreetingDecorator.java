package com.psk.autoproject.extensibility;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

@Decorator
public abstract class GreetingDecorator implements GreetingService {

    @Inject @Delegate
    private GreetingService delegate;

    @Override
    public String greet(String name) {
        // Pre-processing
        String base = delegate.greet(name);
        // Post-processing
        return base + " [decorated]";
    }
}
