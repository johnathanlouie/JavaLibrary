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
package io.jlouie.io;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Johnathan Louie
 */
public class Argument {

    private final List<String> arguments;
    private final Set<String> options;

    public Argument(String[] args) {
        arguments = new ArrayList<String>();
        options = new HashSet<String>();
        for (String i : args) {
            if (isShortOption(i)) {
                options.addAll(parseShortOption(i));
            } else if (isLongOption(i)) {
                options.add(parseLongOption(i));
            } else {
                arguments.add(i);
            }
        }
    }

    private Set<String> parseShortOption(String option) {
        Set<String> set = new HashSet<String>();
        option = option.substring(1);
        for (char i : option.toCharArray()) {
            set.add(String.valueOf(i));
        }
        return set;
    }

    private String parseLongOption(String option) {
        return option.substring(2);
    }

    private boolean isShortOption(String arg) {
        return arg.charAt(0) == '-' && arg.charAt(1) != '-';
    }

    private boolean isLongOption(String arg) {
        return arg.charAt(0) == '-' && arg.charAt(1) == '-';
    }

    public String getArgument(int index) {
        return arguments.get(index);
    }

    public int argumentSize() {
        return arguments.size();
    }

    public boolean hasOption(String option) {
        return options.contains(option);
    }

}
