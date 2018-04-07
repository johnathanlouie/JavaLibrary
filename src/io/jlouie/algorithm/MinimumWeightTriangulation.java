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
package io.jlouie.algorithm;

import io.jlouie.math.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Johnathan Louie
 */
public class MinimumWeightTriangulation {

    private final Map<List<Integer>, EdgeSet> memEdges = new HashMap<>();
    private final List<Point2D> polygon;

    public MinimumWeightTriangulation(List<Point2D> polygon) {
        this.polygon = new ArrayList<>(polygon);
    }

    public Set<Edge> triangulate() {
        List<Integer> convex = new ArrayList<>();
        for (int i = 0; i < polygon.size(); i++) {
            convex.add(i);
        }
        EdgeSet x = memoize(convex);
        Set<Edge> a = new HashSet<>();
        for (Edge2 e2 : x.edges) {
            a.add(new Edge(polygon.get(e2.a), polygon.get(e2.b)));
        }
        return a;
    }

    private EdgeSet mwt(List<Integer> convexPolygon) {
        if (convexPolygon.size() == 3) {
            return toEdgeList(convexPolygon);
        }
        double minWeight = Double.POSITIVE_INFINITY;
        EdgeSet minPolygon = null;
        for (int i = 2; i < convexPolygon.size() - 1; i++) {
            List<Integer> subConvex1 = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                subConvex1.add(convexPolygon.get(j));
            }
            List<Integer> subConvex2 = new ArrayList<>();
            subConvex2.add(convexPolygon.get(0));
            for (int j = i; j < convexPolygon.size(); j++) {
                subConvex2.add(convexPolygon.get(j));
            }
            EdgeSet subPolygon1 = memoize(subConvex1);
            EdgeSet subPolygon2 = memoize(subConvex2);
            double weight = subPolygon1.getWeight() + subPolygon2.getWeight();
            if (weight < minWeight) {
                minWeight = weight;
                minPolygon = new EdgeSet();
                minPolygon.edges.addAll(subPolygon1.edges);
                minPolygon.edges.addAll(subPolygon2.edges);
            }
        }
        return minPolygon;
    }

    private EdgeSet memoize(List<Integer> convex) {
        if (!memEdges.containsKey(convex)) {
            memEdges.put(convex, mwt(convex));
        }
        return memEdges.get(convex);
    }

    public class Edge {

        private final Point2D a;
        private final Point2D b;

        public Edge(Point2D a, Point2D b) {
            this.a = a;
            this.b = b;
        }

        public Point2D getA() {
            return a;
        }

        public Point2D getB() {
            return b;
        }

    }

    private EdgeSet toEdgeList(List<Integer> vertices) {
        EdgeSet edges = new EdgeSet();
        for (int a = 0, b = 1; b < vertices.size(); a++, b++) {
            edges.edges.add(new Edge2(a, b));
        }
        edges.edges.add(new Edge2(0, vertices.size() - 1));
        return edges;
    }

    private class EdgeSet {

        Set<Edge2> edges = new HashSet<>();

        double getWeight() {
            double weight = 0;
            for (Edge2 e : edges) {
                weight += e.getWeight();
            }
            return weight;
        }
    }

    private class Edge2 {

        public Edge2(int a, int b) {
            this.a = a;
            this.b = b;
        }

        int a;
        int b;

        double getWeight() {
            return polygon.get(a).distance(polygon.get(b));
        }

        @Override
        public int hashCode() {
            int hash = 7;
            hash = 23 * hash + this.a;
            hash = 23 * hash + this.b;
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
            final Edge2 other = (Edge2) obj;
            if (this.a != other.a) {
                return false;
            }
            if (this.b != other.b) {
                return false;
            }
            return true;
        }
    }
}
