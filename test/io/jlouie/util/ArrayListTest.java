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

import io.jlouie.datastructure.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Johnathan Louie
 */
public class ArrayListTest {

    public ArrayListTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testSize() {
        System.out.println("size");
        ArrayList instance = new ArrayList();
        System.out.println("size add");
        for (int i = 0; i < 100; i++) {
            assertEquals(i, instance.size());
            instance.add(null, 0);
        }
        System.out.println("size remove");
        for (int i = 100; i > 0; i--) {
            assertEquals(i, instance.size());
            instance.remove(0);
        }
        assertEquals(0, instance.size());
    }

    @Test
    public void checkOrder1() {
        ArrayList instance = new ArrayList();
    }

    @Test
    public void checkOrder2() {
    }

    @Test
    public void checkOrder3() {
    }

}
