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
import java.util.List;

/**
 *
 * @author Johnathan Louie
 */
public final class Files {

    private static final File JAR_FILE = new File(Files.class.getProtectionDomain().getCodeSource().getLocation().getFile()).getAbsolute();

    private Files() {
    }

    public static File getJarFile() {
        return JAR_FILE;
    }

    public static List<File> getAll(File parent) {
        List<File> all = new ArrayList<>();
        List<File> children = parent.listFiles();
        if (children != null) {
            all.addAll(children);
            for (File child : children) {
                if (child.isDirectory()) {
                    all.addAll(getAll(child));
                }
            }
        }
        return all;
    }

    public static List<File> getAllExceptTrash(File parent) {
        List<File> all = new ArrayList<>();
        List<File> children = parent.listFiles();
        List<File> childrenFiltered = new ArrayList<>();
        if (children != null) {
            for (File i : children) {
                if (!i.getNameElement(0).toString().equals("$RECYCLE.BIN")) {
                    childrenFiltered.add(i);
                }
            }
            all.addAll(childrenFiltered);
            for (File child : childrenFiltered) {
                if (child.isDirectory()) {
                    all.addAll(getAll(child));
                }
            }
        }
        return all;
    }

}
