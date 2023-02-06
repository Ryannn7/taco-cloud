package org.example.taco.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * @author liuzongshuai
 * @date 2022/11/16 10:30
 */
@Data
@RequiredArgsConstructor
public class Ingredient {
    private final String id;
    private final String name;
    private final Type type;
    public static enum Type {
        WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
    }

}


