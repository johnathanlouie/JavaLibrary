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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import io.jlouie.io.File;
import io.jlouie.util.Strings;

/**
 *
 * @author Johnathan Louie
 */
public class Ini {

    private Set<Property> settings;

    public Ini() {
        settings = new HashSet<>();
    }

    public boolean load(File file) {
        if (file == null) {
            return false;
        }
        List<String> lines = file.readAllLines();
        if (lines == null) {
            return false;
        }
        Files.removeByteOrderMark(lines);
        settings = parse(lines);
        return true;
    }

    public boolean save(File file) {
        if (file == null) {
            return false;
        }
        return file.write(new Composer(settings, getSectionNames()).compose());
    }

    public String getParameter(String name) {
        for (Property i : settings) {
            if (i.getKey().equalsIgnoreCase(name)) {
                return i.getValue();
            }
        }
        return null;
    }

    public boolean setParameter(String section, String name, String value) {
        return add(Property.newInstance(section, name, value));
    }

    public boolean setParameter(String name, String value) {
        return Ini.this.setParameter(null, name, value);
    }

    public List<String> getParameterNames() {
        List<String> list = new ArrayList<>();
        for (Property i : settings) {
            list.add(i.getKey());
        }
        return list;
    }

    public List<String> getParameterNames(String section) {
        List<String> list = new ArrayList<>();
        for (Property i : settings) {
            if (section.equals(i.getSection())) {
                list.add(i.getKey());
            }
        }
        return list;
    }

    public Set<String> getSectionNames() {
        Set<String> sections = new HashSet<>();
        for (Property i : settings) {
            sections.add(i.getSection());
        }
        return sections;
    }

    private Set<Property> parse(List<String> lines) {
        Set<Property> properties = new HashSet<>();
        String section = null;
        for (String i : lines) {
            if (isComment(i)) {
            } else if (isSectionHead(i)) {
                section = parseSectionHead(i);
            } else if (isProperty(i)) {
                Property prop = Property.newInstance(section, parseProperty(i));
                properties.remove(prop);
                properties.add(prop);
            }
        }
        return properties;
    }

    private boolean isComment(String line) {
        return line.charAt(0) == ';' || line.charAt(0) == '#';
    }

    private boolean isSectionHead(String line) {
        return line.matches("\\x5B\\w+\\x5D");
    }

    private boolean isProperty(String line) {
        return line.matches("\\w+\\x3D.*");
    }

    private String parseSectionHead(String line) {
        int start = line.indexOf("[") + 1;
        int end = line.lastIndexOf("]");
        return line.substring(start, end);
    }

    private KeyValue parseProperty(String line) {
        String[] array = line.split("\\x3D", 2);
        return new KeyValue(array[0], array[1]);
    }

    private boolean add(Property prop) {
        if (prop == null) {
            return false;
        }
        settings.remove(prop);
        settings.add(prop);
        return true;
    }

    private class Composer {

        private final List<Property> settings;
        private final List<String> sections;

        public Composer(Set<Property> settings, Set<String> sectionNames) {
            this.settings = sortSet(settings);
            this.sections = sortSet(sectionNames);
        }

        private String composeParameter(Property prop) {
            return prop.getKey() + "=" + prop.getValue();
        }

        private String composeSectionHead(String section) {
            return "[" + section + "]";
        }

        public List<String> compose() {
            List<String> output = new ArrayList<>();
            for (String section : sections) {
                if (section != null) {
                    output.add(composeSectionHead(section));
                }
                for (Property prop : getProperties(section)) {
                    output.add(composeParameter(prop));
                }
            }
            return output;
        }

        private List<Property> getProperties(String section) {
            List<Property> filtered = new ArrayList<>();
            for (Property i : settings) {
                if (Strings.equals(section, i.getSection())) {
                    filtered.add(i);
                }
            }
            return filtered;
        }

        private <E extends Comparable> List<E> sortSet(Set<E> set) {
            List<E> list = new ArrayList<>(set);
            boolean hasNull = list.remove(null);
            Collections.sort(list);
            if (hasNull) {
                list.add(0, null);
            }
            return list;
        }

    }

    private static class Property implements Comparable<Property> {

        private final String section;
        private final String key;
        private final String value;

        private Property(String section, String key, String value) {
            this.section = section;
            this.key = key;
            this.value = value;
        }

        public String getSection() {
            return section;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public static Property newInstance(String section, String key, String value) {
            if (isValidSectionHead(section) && isValidKey(key) && isValidValue(value)) {
                return new Property(section, key, value);
            }
            return null;
        }

        public static Property newInstance(String section, KeyValue kv) {
            return newInstance(section, kv.getKey(), kv.getValue());
        }

        private static boolean isValidSectionHead(String sectionHead) {
            if (sectionHead != null) {
                return isValidKey(sectionHead);
            }
            return true;
        }

        private static boolean isValidKey(String key) {
            return key.matches("^\\w+$");
        }

        private static boolean isValidValue(String value) {
            return !value.matches("\\v");
        }

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 37 * hash + Objects.hashCode(this.key);
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Property other = (Property) obj;
            return Objects.equals(this.key, other.key);
        }

        @Override
        public int compareTo(Property o) {
            return key.compareToIgnoreCase(o.key);
        }

    }

    private class KeyValue {

        private final String key;
        private final String value;

        public KeyValue(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

    }

}
