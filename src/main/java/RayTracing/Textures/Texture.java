package RayTracing.Textures;

import RayTracing.Models.Vector3D;

import java.awt.*;

public interface Texture {

    Color getColor(Vector3D intersectionPoint);

}
