package RayTracing.Light;

import java.awt.*;

public class AmbientLight extends LightObject {

    public AmbientLight(double intensity, LightType type, Color color){
        super(intensity,type,color);
    }
}
