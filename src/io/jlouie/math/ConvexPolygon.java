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
package io.jlouie.math;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author Johnathan Louie
 */
public class ConvexPolygon {

    private final List<Point2D> vertices = new ArrayList<>();

    public ConvexPolygon(Set<Point2D> points) {
        // Smallest polygon is a triangle.
        if (points.size() < 3) {
            throw new IllegalArgumentException("Must have at least 3 points.");
        }
        LinkedList<Point2D> remainingPoints = new LinkedList<>(points);

        // Find the first vertex.
        Point2D pt = firstVertex(remainingPoints);
        remainingPoints.remove(pt);
        vertices.add(pt);

        // Find the next vertex counterclockwise. Each angle must be a left turn.
        double lastAngle = 0.0;
        while (!remainingPoints.isEmpty()) {
            pt = nextVertex(pt, lastAngle, remainingPoints);
            if (pt == null) {
                throw new IllegalArgumentException("Points do not form a convex polygon.");
            }
            remainingPoints.remove(pt);
            vertices.add(pt);
        }

        // The final vertex must also form a left turn to first vertex.
        if (vertices.get(vertices.size() - 1).direction(vertices.get(0)).noWrap() >= 0.0) {
            throw new IllegalArgumentException("Points do not form a convex polygon.");
        }
    }

    private Point2D firstVertex(List<Point2D> points) {
        Point2D promising = new Point2D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        for (Point2D i : points) {
            if (i.getX() > promising.getX()) {
                promising = i;
            } else if ((i.getX() == promising.getX()) && (i.getY() < promising.getY())) {
                promising = i;
            }
        }
        return promising;
    }

    private Point2D nextVertex(Point2D lastPoint, double lastAngle, List<Point2D> remainingPoints) {
        double minAngle = Double.POSITIVE_INFINITY;
        Point2D promising = null;
        for (Point2D i : remainingPoints) {
            double angle = lastPoint.direction(i).noWrap();
            if (angle < minAngle && angle >= lastAngle) {
                promising = i;
                minAngle = angle;
            }
        }
        return promising;
    }

    public int size() {
        return vertices.size();
    }

    public Point2D getVertex(int i) {
        return vertices.get(i);
    }
}
