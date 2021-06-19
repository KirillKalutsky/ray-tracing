package RayTracing.Textures;

import RayTracing.Models.Vector3D;

import java.awt.*;

public class ChessboardTexture implements Texture {

    Color firstColor;
    Color secondColor;

    double cellSize;

    public ChessboardTexture(Color firstColor, Color secondColor, double cellSize){
        this.firstColor = firstColor;
        this.secondColor = secondColor;
        this.cellSize = cellSize;
    }

    @Override
    public Color getColor(Vector3D intersectionPoint) {
        Color resultColor;
        if(
                (Math.floor(intersectionPoint.getX()/cellSize) +
                Math.floor(intersectionPoint.getY()/cellSize) +
                Math.floor(intersectionPoint.getZ()/cellSize))%2 == 0
        )
            resultColor = firstColor;
        else
            resultColor = secondColor;
        return resultColor;
    }

}
