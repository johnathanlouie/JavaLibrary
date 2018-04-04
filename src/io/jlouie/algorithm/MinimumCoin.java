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
import java.util.Set;

/**
 *
 * @author Johnathan Louie
 */
public class MinimumCoin {

    private final Map<Integer, List<Integer>> mem = new HashMap<>();
    private final Set<Integer> currency;

    public MinimumCoin(Set<Integer> currency) {
        this.currency = currency;
    }

    public List<Integer> makeChange(int amount) {
        if (amount == 0) {
            return new LinkedList<>();
        }
        List<Integer> change = null;
        int min = Integer.MAX_VALUE;
        for (Integer coin : currency) {
            if (coin <= amount) {
                List<Integer> temp = memoize(amount - coin);
                if (temp != null && temp.size() + 1 < min) {
                    change = new LinkedList<>(temp);
                    change.add(coin);
                    min = change.size();
                }
            }
        }
        return change;
    }

    private List<Integer> memoize(int amount) {
        if (!mem.containsKey(amount)) {
            mem.put(amount, makeChange(amount));
        }
        return mem.get(amount);
    }
}
