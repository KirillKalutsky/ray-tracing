package RayTracing.Models;

public class Observer {

    private Vector3D location;

    public Observer(Vector3D location){
        this.location=location;
    }

    public Vector3D getLocation(){return location;}

    private Vector3D view;

}
