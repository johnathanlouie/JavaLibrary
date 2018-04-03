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
package io.jlouie.util;

/**
 *
 * @author Johnathan Louie
 */
public final class Strings {

    private Strings() {
    }

    public static boolean equals(String a, String b) {
        return (a == null ? b == null : a.equals(b));
    }

    public static String toRegularWidthAscii(String string) {
        for (int ascii = 0x21, fullwidth = 0xFF01; ascii <= 0x7E; ascii++, fullwidth++) {
            string = string.replace((char) fullwidth, (char) ascii);
        }
        return string;
    }

    public static String toFullwidthAscii(String string) {
        for (int ascii = 0x21, fullwidth = 0xFF01; ascii <= 0x7E; ascii++, fullwidth++) {
            string = string.replace((char) ascii, (char) fullwidth);
        }
        return string;
    }
}
