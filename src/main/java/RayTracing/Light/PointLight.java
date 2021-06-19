package RayTracing.Light;

import RayTracing.Models.Vector3D;

import java.awt.*;

public class PointLight extends LightObject{

    private Vector3D position;

    public PointLight(double intensity, LightType type, Color color, Vector3D direction){
        super(intensity,type,color);
        this.position = direction;
    }

    public Vector3D getPosition() {
        return position;
    }
}
