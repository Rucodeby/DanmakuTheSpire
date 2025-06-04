package danmaku.helpers;

import com.badlogic.gdx.math.*;

public class CollisionHelper {

    private static final int defaultDetail = 20;

    public static boolean check(Polygon p1, Polygon p2) {
        return Intersector.overlapConvexPolygons(p1, p2);
    }

    public static boolean check(Rectangle r1, Rectangle r2) {
        return Intersector.overlaps(r1, r2);
    }

    public static boolean check(Circle c1, Circle c2) {
        return Intersector.overlaps(c1, c2);
    }

    private static boolean check(Ellipse e1, Ellipse e2) {
        //TODO
        return false;
    }

    public static boolean check(Rectangle r, Circle c) {
        return Intersector.overlaps(c, r);
    }

    private static boolean check(Ellipse e, Rectangle r) {
        //TODO
        return false;
    }

    private static boolean check(Ellipse e, Circle c) {
        //TODO
        return false;
    }

    private static boolean check(Polygon p, Ellipse e) {
        //TODO
        return false;
    }

    private static boolean check(Polygon p, Rectangle r) {
        //TODO
        return false;
    }

    private static boolean check(Polygon p, Circle c) {
        //TODO
        return false;
    }

    public static Polygon createPolygon(Rectangle r) {
        return new Polygon(new float[]{r.x, r.y, r.x + r.width, r.y, r.x + r.width, r.y + r.height});
    }

    public static Polygon createPolygon(Circle c, int detail) {
        float[] points = new float[detail*2];
        double radiansPerStep = 2*Math.PI/detail;
        for (int i = 0; i < detail*2; i += 2) {
            points[i] = (float) (c.radius * Math.cos(i * radiansPerStep) + c.x);
            points[i+1] = (float) (c.radius * Math.sin(i * radiansPerStep) + c.y);
        }
        return new Polygon(points);
    }

    public static Polygon createPolygon(Circle c) {
        return createPolygon(c, defaultDetail);
    }

    public static Polygon createPolygon(Ellipse e, int detail) {
        float[] points = new float[detail*2];
        double radiansPerStep = 2*Math.PI/detail;
        for (int i = 0; i < detail*2; i += 2) {
            //float angle = i * Math.PI / detail; <- Use this if radians per step would be bad
            points[i] = (float) (e.width/2 * Math.cos(i * radiansPerStep) + e.width/2);
            points[i+1] = (float) (e.height/2 * Math.sin(i * radiansPerStep) + e.height/2);
        }
        return new Polygon(points);
    }

    public static Polygon createPolygon(Ellipse e) {
        return createPolygon(e, defaultDetail);
    }
}