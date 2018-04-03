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
package io.jlouie.script;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 *
 * @author Johnathan Louie
 */
public final class Files {

    public static void removeBlankLines(List<String> lines) {
        for (Iterator<String> i = lines.iterator(); i.hasNext();) {
            String temp = i.next();
            if (temp.trim().isEmpty()) {
                i.remove();
            }
        }
    }

    public static void removeByteOrderMark(List<String> lines) {
        for (ListIterator<String> i = lines.listIterator(); i.hasNext();) {
            i.set(i.next().replaceAll("\uFEFF", ""));
        }
    }

    public static void removeUseless(List<String> lines) {
        for (ListIterator<String> i = lines.listIterator(); i.hasNext();) {
            String noBOM = i.next().replaceAll("\uFEFF", "");
            if (noBOM.trim().isEmpty()) {
                i.remove();
            } else {
                i.set(noBOM);
            }
        }
    }

}
