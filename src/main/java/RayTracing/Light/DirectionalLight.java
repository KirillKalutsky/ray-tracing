package RayTracing.Light;

import RayTracing.Models.Vector3D;

import java.awt.*;

public class DirectionalLight extends LightObject {

    private Vector3D direction;

    public DirectionalLight(double intensity, LightType type, Color color, Vector3D direction){
        super(intensity,type,color);
        this.direction = direction;
    }

    public Vector3D getDirection() {
        return direction;
    }
}
