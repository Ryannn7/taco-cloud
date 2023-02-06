package org.example.taco.domain;

import lombok.Data;

import java.util.List;

/**
 * @author liuzongshuai
 * @date 2022/11/16 10:41
 */
@Data
public class Taco {

    private String name;
    private List<String> ingredients;
}
