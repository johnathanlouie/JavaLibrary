/*
 * Copyright (C) 2018 Johnathan Louie
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

/**
 *
 * @author Johnathan Louie
 */
public class Radians {

    private final double angle;

    public Radians(double radians) {
        this.angle = radians;
    }

    public double getValue() {
        return angle;
    }

    public double noWrap() {
        return (angle % (2 * Math.PI)) + (2 * Math.PI) % (2 * Math.PI);
    }

    public double normalize() {
        return ((noWrap() + Math.PI) % (2 * Math.PI)) - Math.PI;
    }

}
