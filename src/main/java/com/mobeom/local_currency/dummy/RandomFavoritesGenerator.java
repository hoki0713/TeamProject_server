package com.mobeom.local_currency.dummy;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Component
public class RandomFavoritesGenerator {
    public static List<Boolean> booleanList =
            Arrays.asList(true,false,false);

    public boolean hasFavorites() {
        Collections.shuffle(booleanList);
        return booleanList.get(0);
    }

    public static int getRandomNumOfFavorites() {
        return (int)(Math.random()*10)+1;
    }
}
