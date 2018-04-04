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

    private final Map<Integer, List<Coin>> mem = new HashMap<>();
    private final Set<Coin> currency;

    public MinimumCoin(Set<Coin> currency) {
        this.currency = currency;
    }

    public List<Coin> makeChange(int amount) {
        if (amount == 0) {
            return new LinkedList<>();
        }
        List<Coin> change = null;
        int min = Integer.MAX_VALUE;
        for (Coin coin : currency) {
            if (coin.getValue() <= amount) {
                List<Coin> temp = memoize(amount - coin.getValue());
                if (temp != null && temp.size() + 1 < min) {
                    change = new LinkedList<>(temp);
                    change.add(coin);
                    min = change.size();
                }
            }
        }
        return change;
    }

    private List<Coin> memoize(int amount) {
        if (!mem.containsKey(amount)) {
            mem.put(amount, makeChange(amount));
        }
        return mem.get(amount);
    }
}
