package org.example.implementation;

import org.example.interfaces.Tyres;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Component
@Primary
public class MichenlinTyres implements Tyres {
    @Override
    public String rotate() {
        String tyres = "Michenlin tyres";
        // System.out.println(tyres);
        return tyres;
    }
}
