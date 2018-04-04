/*
 * Copyright (C) 2015 Johnathan Louie
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io.jlouie.algorithm;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Johnathan Louie
 */
public class Knapsack {

    private final List<ValueWeight> items;
    private final Map<Integer, List<ValueWeight>> memSack = new HashMap<>();
    private final Map<Integer, Integer> memValue = new HashMap<>();

    public Knapsack(List<ValueWeight> items) {
        for (ValueWeight i : items) {
            if (i.getWeight() < 1) {
                throw new NonPositiveWeightException();
            }
        }
        this.items = new LinkedList<>(items);
    }

    public List<ValueWeight> pack(int capacity) {
        if (capacity < 1) {
            return new LinkedList<>();
        }
        List<ValueWeight> sack = null;
        int maxValue = Integer.MIN_VALUE;
        for (ValueWeight item : items) {
            if (item.getWeight() <= capacity) {
                int tempCapacity = capacity - item.getWeight();
                List<ValueWeight> tempSack = memoizeSack(tempCapacity);
                int value = memoizeValue(tempCapacity) + item.getValue();
                if (value > maxValue) {
                    sack = new LinkedList<>(tempSack);
                    sack.add(item);
                    maxValue = value;
                }
            }
        }
        if (sack == null) {
            return new LinkedList<>();
        }
        return sack;
    }

    private List<ValueWeight> memoizeSack(int capacity) {
        if (!memSack.containsKey(capacity)) {
            memSack.put(capacity, pack(capacity));
        }
        return memSack.get(capacity);
    }

    private int memoizeValue(int capacity) {
        if (!memValue.containsKey(capacity)) {
            memValue.put(capacity, sackValue(memoizeSack(capacity)));
        }
        return memValue.get(capacity);
    }

    private int sackValue(List<ValueWeight> sack) {
        int value = 0;
        for (ValueWeight i : sack) {
            value += i.getValue();
        }
        return value;
    }
}
