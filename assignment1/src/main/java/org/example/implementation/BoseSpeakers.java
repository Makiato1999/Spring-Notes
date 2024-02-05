package org.example.implementation;

import org.example.interfaces.Speakers;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
public class BoseSpeakers implements Speakers {
    @Override
    public String makeSound() {
        String speakers = "Bose speaker";
        // System.out.println(speakers);
        return speakers;
    }
}