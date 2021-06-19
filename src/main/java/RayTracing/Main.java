package RayTracing;

import RayTracing.Elements.Sphere;
import RayTracing.Light.AmbientLight;
import RayTracing.Light.DirectionalLight;
import RayTracing.Light.LightType;
import RayTracing.Light.PointLight;
import RayTracing.Models.Observer;
import RayTracing.Models.Scene;
import RayTracing.Models.Vector3D;
import RayTracing.Textures.ChessboardTexture;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class Main {

    public static void main(String[] args) {
        var scene = new Scene();

        var sp1 = new Sphere(
                new Vector3D(0,-1,4.5),
                1,
                Color.PINK
        );
        sp1.setReflectionCoefficient(100);
        sp1.setSpecularCoefficient(1);
        sp1.setTransparencyCoefficient(0);
        scene.addElement(sp1);

        var sp2 = new Sphere(
                new Vector3D(1.25,0.25,3.75),
                1,
                Color.CYAN
        );
        sp2.setReflectionCoefficient(500);
        sp2.setSpecularCoefficient(0.3);
        sp2.setTransparencyCoefficient(1);
        scene.addElement(sp2);

        var sp3 = new Sphere(
                new Vector3D(-1.25,0.25,3.75),
                1,
                Color.MAGENTA
        );
        sp3.setTransparencyCoefficient(0.9);
        sp3.setReflectionCoefficient(10);
        sp3.setSpecularCoefficient(0.4);
        scene.addElement(sp3);

        var sp4 = new Sphere(
                new Vector3D(0,-5001,0),
                5000,
                Color.yellow
        );
        sp4.setTransparencyCoefficient(0.6);
        sp4.setReflectionCoefficient(1000);
        sp4.setSpecularCoefficient(0.2);
        sp4.setTexture(new ChessboardTexture(Color.BLUE,Color.ORANGE,1));
        scene.addElement(sp4);


        scene.addLightObject(
                new AmbientLight(0.2,LightType.AMBIENT,Color.white)
        );

        scene.addLightObject(
                new PointLight(0.6,LightType.POINT,Color.YELLOW,new Vector3D(3,1,0))
        );

        scene.addLightObject(
                new DirectionalLight(0.2,LightType.DIRECTIONAL,Color.YELLOW,new Vector3D(1,4,4))
        );



        scene.setObserver(new Observer(new Vector3D(0,0.4,-0.25)));

        var res =  Algorithm.Paint(
                scene,
                new RayTracing.Models.Canvas(
                        800,
                        800,
                        new Vector3D(0,0,scene.getObserver().getLocation().getZ()+1)
                )
        );


        BufferedImage bufferedImage = new BufferedImage(res.length, res[0].length,
                BufferedImage.TYPE_INT_RGB);

        for (int x = 0; x < res.length; x++) {
            for (int y = 0; y < res[x].length; y++) {
                bufferedImage.setRGB(x, y, res[x][y].getRGB());
            }
        }

        JFrame frame = new JFrame();
        frame.getContentPane().setLayout(new FlowLayout());
        frame.getContentPane().add(new JLabel(new ImageIcon(bufferedImage)));
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

}
