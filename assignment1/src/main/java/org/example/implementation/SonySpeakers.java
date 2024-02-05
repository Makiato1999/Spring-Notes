package org.example.implementation;

import org.example.interfaces.Speakers;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class SonySpeakers implements Speakers {
    @Override
    public String makeSound() {
        String speakers = "Sony speaker";
        // System.out.println(speakers);
        return speakers;
    }
}
