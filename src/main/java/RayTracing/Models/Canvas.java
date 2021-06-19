package RayTracing.Models;

import java.awt.*;

public class Canvas {

    public static final int maximumRecursionDepth = 2;

    public double getDistanceToObserver() {
        return center.getZ();
    }

    private Color[][] canvas;

    private final int height;

    private final int width;

    private Vector3D center;

    public Canvas(int width, int height, Vector3D center){
        canvas = new Color[width][height];
        this.center=center;
        this.height=height;
        this.width=width;
    }

    public void putPixel(int x, int y, Color color){
        canvas[x][y]=color;
    }

    public Color getPoint(int x,int y){
        return canvas[x][y];
    }

    public int getHeight(){return height;}

    public int getWidth(){return width;}

    public Color[][] getImage(){

        return canvas;
    }

    public Vector3D getCenter(){return center;}
}
