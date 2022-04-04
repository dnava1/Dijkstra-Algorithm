import javax.swing.*;

public class FrameDisplay extends JFrame {
    int WIDTH = 600;
    int HEIGHT = 750;

    public FrameDisplay(Graph g) 
    {
        setTitle("Graph Display");
        setSize(WIDTH, HEIGHT);
        GraphDisplay panel = new GraphDisplay(g);
        add(panel);
    }
}