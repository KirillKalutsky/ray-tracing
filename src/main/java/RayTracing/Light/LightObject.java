package RayTracing.Light;

import java.awt.*;

public abstract class LightObject {

    public LightObject(double intensity, LightType type, Color color){
        this.intensity = intensity;
        this.type = type;
        this.color = color;
    }

    protected LightType type;

    public LightType getType() {
        return type;
    }

    protected Color color;

    protected double intensity;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        if(intensity<0)
            this.intensity = 0;
        else if(intensity>1)
            this.intensity=1;
        else
            this.intensity = intensity;
    }


}
