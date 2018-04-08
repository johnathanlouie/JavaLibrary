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

import java.util.Objects;

/**
 *
 * @author Johnathan Louie
 */
public class UndirectedEdge {

    private final Point2D a;
    private final Point2D b;

    public UndirectedEdge(Point2D a, Point2D b) {
        if (a.getX() < b.getX()) {
            this.a = a;
            this.b = b;
        } else if (a.getX() == b.getX()) {
            if (a.getY() < b.getY()) {
                this.a = a;
                this.b = b;
            } else {
                this.a = b;
                this.b = a;
            }
        } else {
            this.a = b;
            this.b = a;
        }
    }

    public boolean isSelfLoop() {
        return a.equals(b);
    }

    public Point2D getA() {
        return a;
    }

    public Point2D getB() {
        return b;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.a);
        hash = 53 * hash + Objects.hashCode(this.b);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UndirectedEdge other = (UndirectedEdge) obj;
        if (!Objects.equals(this.a, other.a)) {
            return false;
        }
        if (!Objects.equals(this.b, other.b)) {
            return false;
        }
        return true;
    }

}
