package RayTracing.Models;

import RayTracing.Elements.SceneElement;
import RayTracing.Light.LightObject;
import RayTracing.Models.Observer;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Scene {

    List<LightObject> lights;

    List<SceneElement> elements;

    {
        elements = new ArrayList<>();
        lights = new ArrayList<>();
    }

    public void addLightObject(LightObject light){
        lights.add(light);
    }

    public void addElement(SceneElement newElement){
        elements.add(newElement);
    }

    public List<LightObject> getLights() {
        return lights;
    }

    public static Color getSceneColor(){
        return Color.BLACK;
    }

    public List<SceneElement> getElements(){
        return elements;
    }


    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }

    private  Observer observer;

}
