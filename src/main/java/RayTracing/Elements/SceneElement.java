package RayTracing.Elements;

import RayTracing.Textures.Texture;
import RayTracing.Models.Vector3D;

import java.awt.*;

public abstract class SceneElement {

    protected Texture texture;

    protected Color color;

    protected double reflectionCoefficient;

    protected double specularCoefficient;

    protected double transparencyCoefficient;

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public double getSpecularCoefficient() {
        return specularCoefficient;
    }

    public void setSpecularCoefficient(double specularCoefficient) {
            this.specularCoefficient = specularCoefficient;
    }

    public double getReflectionCoefficient() {
        return reflectionCoefficient;
    }

    public void setReflectionCoefficient(double reflectionCoefficient) {
            this.reflectionCoefficient = reflectionCoefficient;
    }

    public Color getColor(Vector3D intersectionPoint){
        var resultColor = color;
        if(texture!=null)
            resultColor = texture.getColor(intersectionPoint);
        return resultColor;
    }

    public void setColor(Color newColor){
        this.color=newColor;
    }

    public abstract Vector3D getCrossing(Vector3D obs, Vector3D pix, double minValue, double maxValue);

    public double getTransparencyCoefficient() {
        return transparencyCoefficient;
    }

    public void setTransparencyCoefficient(double transparencyCoefficient) {
        this.transparencyCoefficient = transparencyCoefficient;
    }
}
