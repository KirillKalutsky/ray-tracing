package RayTracing;

import RayTracing.Light.DirectionalLight;
import RayTracing.Light.LightType;
import RayTracing.Light.PointLight;
import RayTracing.Models.Canvas;
import RayTracing.Models.Scene;
import RayTracing.Models.Vector3D;

import java.awt.*;

public class Algorithm {
    public static Color[][] Paint(Scene scene, Canvas canvas){

        for (var w = 0; w < canvas.getWidth(); w++) {
            for(var h = 0;h<canvas.getHeight();h++){
                var color = traceRay(
                        scene,
                        scene.getObserver().getLocation(),
                        /*Переводим координаты пикселя из массива в координаты пикселя на картинке*/
                     canvas.getCenter().
                                sum(
                                        new Vector3D(
                                                (w -  canvas.getWidth() / 2) / (double)  canvas.getWidth(),
                                                (h -  canvas.getHeight() / 2) / (double)  canvas.getHeight()*(-1),
                                                0
                                        )
                                ).rotateX(-0.2),
                        0,
                        /*
                        Как минимальное значение выставляем значение расстояния от наблюдателя до экрана
                        так как нас интересуют только те значения которые находятся за экраном
                        */
                        canvas.getDistanceToObserver(),
                        Double.MAX_VALUE
                );
                canvas.putPixel(w,h,color);
            }
        }
        return canvas.getImage();
    }

    public static Color traceRay(Scene scene, Vector3D start, Vector3D finish, int recursionDepth, double tMin, double tMax){
        var resPoint = closestIntersection(scene,start,finish,tMin,tMax);

        /*Если найденная точка имеет нормаль пересечения значит мы наткнулись на какой-то объект сцены*/
        if (resPoint.getNormal()!=null) {
            var par = countLight(scene, resPoint,finish.vectorMult(-1));
            var color = resPoint.getColor();
            int pan1 = (int) (color.getRed() * par);
            int pan2 = (int) (color.getGreen() * par);
            int pan3 = (int) (color.getBlue() * par);

            pan1 = pan1>255? 255:pan1;
            pan2 = pan2>255? 255:pan2;
            pan3 = pan3>255? 255:pan3;
            resPoint.setColor(new Color(pan1, pan2, pan3));

            /*поиск отражений с ограничением по глубине рекурсии*/
            var specularCoefficient = resPoint.getParentElement().getSpecularCoefficient();
            if(specularCoefficient>0 && recursionDepth< RayTracing.Models.Canvas.maximumRecursionDepth){
                var R = reflectRay(finish.vectorMult(-1), resPoint.getNormal());
                var refColor = traceRay(scene,resPoint,R,++recursionDepth,0.001,Double.MAX_VALUE);
                var localColor = resPoint.getColor();

                var resColor = new Color(
                        (int)(localColor.getRed()*(1-specularCoefficient)+refColor.getRed()*specularCoefficient),
                        (int)(localColor.getGreen()*(1-specularCoefficient)+refColor.getGreen()*specularCoefficient),
                        (int)(localColor.getBlue()*(1-specularCoefficient)+refColor.getBlue()*specularCoefficient)
                );
                resPoint.setColor(resColor);
            }

            /*поиск лучей полупрозрачных объектов*/
            var transparencyCoefficient = resPoint.getParentElement().getTransparencyCoefficient();
            if(transparencyCoefficient<1 && recursionDepth<= Canvas.maximumRecursionDepth){
                var R = reflectRay(finish.vectorMult(-1),resPoint.getNormal());
                var transparencyColor = traceRay(scene,resPoint,R,++recursionDepth,0.001,Double.MAX_VALUE);
                var localColor = resPoint.getColor();

                var resColor = new Color(
                        (int)(localColor.getRed()*(specularCoefficient)+transparencyColor.getRed()*(1-specularCoefficient)),
                        (int)(localColor.getGreen()*(specularCoefficient)+transparencyColor.getGreen()*(1-specularCoefficient)),
                        (int)(localColor.getBlue()*(specularCoefficient)+transparencyColor.getBlue()*(1-specularCoefficient))
                );

                resPoint.setColor(resColor);
            }
        }
        return resPoint.getColor();
    }

    private static Vector3D closestIntersection(Scene scene,Vector3D start, Vector3D finish, double tMin, double tMax){
        /*Находим ближайшую точку пересечения с объектом сцены*/
        Vector3D resPoint=new Vector3D(Scene.getSceneColor());
        resPoint.setIntersectionSolution(Double.MAX_VALUE);

        for(var element: scene.getElements()) {
            var point = element.getCrossing(
                    start,
                    finish,
                    tMin,
                    tMax
            );

            if(point.getIntersectionSolution()<resPoint.getIntersectionSolution()){
                resPoint = point;
            }
        }
        return resPoint;
    }

    private static Vector3D reflectRay(Vector3D R, Vector3D N) {
        return N.vectorMult(2).vectorMult(Vector3D.vectorMult(N, R)).sub(R);
    }

    private static double countLight(Scene scene,Vector3D point, Vector3D view){
        var res = 0.0;
        var maxValue = Double.MAX_VALUE;
        for (var light : scene.getLights()) {
            if (light.getType() == LightType.AMBIENT) {
                res += light.getIntensity();
            } else {
                Vector3D lightSourceLocation ;

                if (light.getType() == LightType.POINT) {
                    lightSourceLocation = ((PointLight) light).getPosition().sub(point);
                    maxValue=1;
                }
                else{
                    lightSourceLocation = ((DirectionalLight)light).getDirection();
                }

                var intersectionPointNormal = point.getNormal();

                /*
                проверяем преграждает ли какой-нибудь объект сцены путь от источника света до нашей точки
                если преграждает то у нас образуется тень
                за tMin берем 0.001, так как не хотим наткнуться на нашу же точку,
                                если это произуйдет она будет отбрасывать тень сама на себя
                */
                Vector3D resPoint = closestIntersection(scene,point, lightSourceLocation,0.001,maxValue);

                if(resPoint.getParentElement()!=null ) {
                    continue;
                }

                /*Вычисляем косинус между нормалью нашей точки и источником света*/
                var cosNL = Vector3D.vectorMult(intersectionPointNormal, lightSourceLocation)/ (intersectionPointNormal.getLength() * lightSourceLocation.getLength());

                /*
                Вычисляем количество света полученного нашей точкой от точечных или направленных источников
                Суммируем только значения больше нуля иначе можем получить источник света вычитающий свет
                */
                if (cosNL > 0)
                    res += light.getIntensity() * cosNL ;

                var s = point.getParentElement().getReflectionCoefficient();
                if (s > 0) {
                    /*Находим вектор отражения*/
                    var R = reflectRay(lightSourceLocation,intersectionPointNormal);
                    var cosRV = Vector3D.vectorMult(R, view) / (R.getLength() * view.getLength());
                    /*Считаем сколько света отразится в направлении обзора*/
                    if (cosRV > 0)
                        res += light.getIntensity() * Math.pow(cosRV, s);
                }
            }
        }
        return res;
    }
}
