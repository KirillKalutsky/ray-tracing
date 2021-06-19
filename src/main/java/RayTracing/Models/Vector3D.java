package RayTracing.Models;

import RayTracing.Elements.SceneElement;

import java.awt.*;

public class Vector3D {

    private Vector3D normal;

    private SceneElement parentElement;

    public SceneElement getParentElement() {
        return parentElement;
    }

    public void setParentElement(SceneElement parentElement) {
        this.parentElement = parentElement;
    }

    public Vector3D getNormal() {
        return normal;
    }

    public void setNormal(Vector3D normal) {
        this.normal = normal;
    }

    private double intersectionSolution;

    public double getIntersectionSolution() {
        return intersectionSolution;
    }

    public void setIntersectionSolution(double intersectionSolution) {
        this.intersectionSolution = intersectionSolution;
    }

    private double x;

    private double y;

    private double z;

    private Color color;

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Vector3D(Color color){
        this.color = color;
    }

    public Vector3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3D(double x, double y, double z, Color color) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.color=color;
    }

    public double getLength(){
        return Math.sqrt(x*x+y*y+z*z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public int hashCode() {
        return (int)(x*23+y*7+z*43);
    }

    @Override
    public boolean equals(Object obj) {
        var o = (Vector3D)obj;
        if(o == null)
            return false;
        return o.getX()==x && o.getY()==y && o.getZ()==z;
    }

    @Override
    public String toString() {
        return x+" "+y+" "+z;
    }

    public static double vectorMult(Vector3D p1, Vector3D p2){
        return p1.getX()* p2.getX()+ p1.getY()*p2.getY()+ p1.getZ()*p2.getZ();
    }

    public Vector3D sub(Vector3D p){
        return new Vector3D(x- p.getX(),y-p.getY(),z-p.getZ());
    }

    public Vector3D vectorMult(double a){
        return new Vector3D(x*a,y*a,z*a);
    }

    public Vector3D vectorMult(Vector3D a){
        return new Vector3D(
                this.y*a.getZ()-this.z*a.getY(),
                (-1)*(this.x*a.getZ()-this.z*a.getX()),
                this.x*a.getY()-this.y*a.getX()
        );
    }

    public Vector3D rotateY(double angle){
        return new Vector3D(
                x*Math.cos(angle)+z*Math.sin(angle),
                y,
                -x*Math.sin(angle)+z*Math.cos(angle)
        );
    }


    public Vector3D rotateX(double angle){
        return new Vector3D(
                x,
                y*Math.cos(angle)+z*Math.sin(angle),
                -y*Math.sin(angle)+z*Math.cos(angle)
        );
    }

    public Vector3D rotateZ(double angle){
        return new Vector3D(
                x*Math.cos(angle)-y*Math.sin(angle),
                -x*Math.sin(angle)+y*Math.cos(angle),
                z
        );
    }


    public Vector3D sum(Vector3D p){
        return new Vector3D(x+ p.getX(),y+p.getY(),z+p.getZ());
    }
}
