package org.example.implementation;

import org.example.interfaces.Tyres;
import org.springframework.stereotype.Component;

@Component
public class BridgeStoneTyres implements Tyres {
    @Override
    public String rotate() {
        String tyres = "BridgeStone tyres";
        // System.out.println(tyres);
        return tyres;
    }
}
