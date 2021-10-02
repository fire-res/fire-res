package io.github.fireres.core.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Getter
public class BoundShift<P extends Point<?>> {

    private final List<P> points = new ArrayList<>();

    public void add(P point) {
        if (points.stream().anyMatch(p -> p.getTime().equals(point.getTime()))) {
            throw new IllegalArgumentException("Bound shift already specified on time " + point.getTime());
        }

        points.add(point);
        points.sort(Comparator.comparing(Point::getTime));
    }

    public boolean remove(P point) {
        return points.remove(point);
    }

    public void clear() {
        points.clear();
    }

}
