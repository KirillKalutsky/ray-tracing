package RayTracing.Elements;

import RayTracing.Models.Vector3D;
import RayTracing.Models.Scene;


import java.awt.*;
import java.util.*;

public class Sphere extends SceneElement {

    private Vector3D center;

    private double radius;

    public Sphere(Vector3D center, double radius, Color color){
        this.center = center;
        this.radius = radius;
        this.color = color;
    }

    @Override
    public Vector3D getCrossing(Vector3D start, Vector3D finish, double minValue, double maxValue) {
        Vector3D point = null;

        /*
        находим пересечение луча, исходящего из точки start проходящего через точку finish,
        которые задают направление луча, со сферой
        */
        var res = getRootsEquation(
                Vector3D.vectorMult(finish,finish),
                2* Vector3D.vectorMult(start.sub(center),finish),
                Vector3D.vectorMult(start.sub(center),start.sub(center))-radius*radius
        );

        /*
        Так как луч может пересекать сферу в 2 точках, но нас интересует только
        наименьшее значение для пересечения, так как оно будет видно наблюдателю
        */
        var minCross=res.stream().filter(x->x<=maxValue&&x>=minValue).min(Double::compare);

        /*
        Если нет удовлетворяющих нас пересечений со сферой то лучу присваивается цвет фона
        */
        if(!minCross.isPresent()) {
            point = new Vector3D(0,0,Double.MAX_VALUE,Scene.getSceneColor());
            point.setIntersectionSolution(Double.MAX_VALUE);
        }

        else {
            System.out.println("------------");
            for (var e : res) {
                System.out.println(e);
                System.out.println(finish.getX() + " " + finish.getY() + " " + finish.getZ());
            }

            /*параметр приводящий уровнение пересечения луча и сферы в верное равенство и удовлетворяющий условиям*/
            var t = minCross.get();

            /*подставляя вычисленный параметр находим координаты точки пересечения*/
            point = start.sum(finish.vectorMult(t));
            point.setColor(this.getColor(point));

            point.setIntersectionSolution(t);

            /*вычисляем нормаль в точке на поверхности сферы*/
            var norm = point.sub(this.center);
            point.setNormal(new Vector3D((norm.getX() / norm.getLength()), (norm.getY() / norm.getLength()), (norm.getZ() / norm.getLength())));
            point.setParentElement(this);

        }
        return point;
    }

    private ArrayList<Double> getRootsEquation(double a, double b, double c){
        var res = new ArrayList<Double>();
        var d = b*b-4*a*c;
        if(d==0) {
            res.add(-b / (2 * a));
        }
        if(d>0){
            res.add((-b+Math.sqrt(d))/(2*a));
            res.add((-b-Math.sqrt(d))/(2*a));
        }
        return res;
    }
}
