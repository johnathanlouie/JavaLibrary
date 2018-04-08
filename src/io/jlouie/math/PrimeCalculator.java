/*
 * Copyright (C) 2014 Johnathan Louie
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
package io.jlouie.math;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Johnathan Louie
 */
public class PrimeCalculator {

    public static final TreeSet<BigInteger> PRIMES = new TreeSet<>();
    public static TreeMap<BigInteger, BigInteger> SQRTS = new TreeMap<>();
    public static final BigInteger RSA = new BigInteger("3082010a0282010100c602e9f8284202be8017c1f89764da28cc397bbd48960ea65fff8c221886c4e62d005dfd1de88e01b3c87adeea912810c5ab41c9bcb56abe95a79a2d592c8c7b13f500e24fcfc5156519dafeccdc17743b66dac359363f2e957428cfb195aa6ea74b41ea66f069af12fd5fcd23542941c8e56565d8c60559bcbb7bd9e9fc7d8c77f4c765b5bd1d3e8b10a9f13732729599a0827f0b5cfb6280c9b74b247c9be16842db7b83b93503be413ba9621c051bc0d011265aa1180ec2cf484f2ba968ffa3ea662d5e699015dd04738965b6e3a34d5e4ec616e0fe9772b7281dac8a739f826475c07c521faf5ae5edeb733c5d2ff849e6727001895d23487857dfd777a10203010001", 16);

    public static void ensurePrime(BigInteger x) {
        PRIMES.add(new BigInteger("2"));
        while (lessThan(PRIMES.last(), x)) {
            PRIMES.add(findNextPrime(PRIMES.last()));
        }
    }

    public static boolean euclid() {
        BigInteger newNum = addOneToProductOfKnownPrimes();
        List<BigInteger> newNumFactors = factor(newNum);
        if (isPrime(newNumFactors)) {
            System.out.println("prime: " + newNum);
            return true;
        } else {
            System.out.println("compo: " + newNum);
            Set<BigInteger> primesCopy = new HashSet<>(PRIMES);
            boolean newFactor = false;
            for (BigInteger i : newNumFactors) {
                if (primesCopy.add(i)) {
                    System.out.println("facto: " + i);
                    newFactor = true;
                }
            }
            if (!newFactor) {
                System.out.println("wrong: " + newNum);
            }
            return newFactor;
        }
    }

    public static BigInteger addOneToProductOfKnownPrimes() {
        BigInteger newNum = BigInteger.ONE;
        for (BigInteger i : PRIMES) {
            newNum = newNum.multiply(i);
        }
        newNum = newNum.add(BigInteger.ONE);
        return newNum;
    }

    public static boolean isPrime(List<BigInteger> factors) {
        return factors.size() < 2;
    }

    public static List<BigInteger> factor(BigInteger x) {
        ensurePrime(sqrt(x));
        List<BigInteger> factors = new ArrayList<>();
        for (BigInteger i : PRIMES) {
            if (lessThan(x, i)) {
                break;
            }
            while (x.mod(i).equals(BigInteger.ZERO)) {
                x = x.divide(i);
                factors.add(i);
            }
        }
        if (greaterThan(x, BigInteger.ONE)) {
            factors.add(x);
        }
        return factors;
    }

    public static BigInteger findNextPrime(BigInteger current) {
        current = increment(current);
        while (true) {
            if (isPrime(factor(current))) {
                return current;
            }
            current = increment(current);
        }
    }

    public static BigInteger sqrt(BigInteger x) {
        BigInteger index = BigInteger.ZERO;
        while (index.pow(2).compareTo(x) == -1) {
            index = index.add(BigInteger.ONE);
        }
        return index;
    }

    public static boolean lessThan(BigInteger x, BigInteger y) {
        return x.compareTo(y) == -1;
    }

    public static boolean lessOrEquals(BigInteger x, BigInteger y) {
        return x.compareTo(y) != 1;
    }

    public static boolean greaterThan(BigInteger x, BigInteger y) {
        return x.compareTo(y) == 1;
    }

    public static boolean greaterOrEquals(BigInteger x, BigInteger y) {
        return x.compareTo(y) != -1;
    }

    public static BigInteger increment(BigInteger x) {
        return x.add(BigInteger.ONE);
    }

}
