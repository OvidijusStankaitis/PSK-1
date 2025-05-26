package com.psk.autoproject.jsf;

import com.psk.autoproject.extensibility.GreetingService;
import com.psk.autoproject.extensibility.GreetingBinding;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named
@ViewScoped
public class GreetingBean implements Serializable {
    private String name;
    private String output;

    @Inject
    private GreetingService greetingService;

    @PostConstruct
    public void init() {
        name = "World";
        output = "";
    }

    public void sayHello() {
        output = greetingService.greet(name);
    }

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getOutput() { return output; }
}
