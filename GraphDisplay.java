import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class GraphDisplay extends JPanel {
    private Graph graph;
    public GraphDisplay(){}
    public GraphDisplay(Graph g) 
    {
        graph = g;
    }
    /**
     * Paints the graph example.
     * 
     * @param g graphics context
     */
    public void paint(Graphics g) {
        
        //math variables to draw circle
        int x = getWidth() / 2;
        int y = getHeight() / 2;
        int r = 4 * 400 / 5;
        int m = Math.min(x, y);
        r = 4 * m / 5;
        int r1 = Math.abs(m - r) / 2;

        //allows us to change line thickness
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Saves vertex position
        int[] xSave = new int[graph.VerticesNumber()];
        int[] ySave = new int[graph.VerticesNumber()];
        
        int[] p = new int[graph.VerticesNumber()];
        int[] d = new int[graph.VerticesNumber()];

        graph.allShortestPaths(p, d, graph.Source());

        // current vertex
        int current = graph.Target();

        // shortest path array
        ArrayList<Integer> path = new ArrayList<>();

        while (current != graph.Source()) { 
            path.add(current);
            current = p[current];
        }
        path.add(graph.Source());
        // fixes order
        Collections.reverse(path);

        //drawing out information strings
        g.setColor(Color.black);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        g.drawString("Source: " + graph.Source(), 10, 20);
        g.drawString("Target: " + graph.Target(), 10, 40);
        g.drawString("Shortest path in blue: " + path,10, 60);
        g.drawString("Total distance: " + d[graph.Target()],10, 80);

        //saves position in circle
        for (int i = 0; i < graph.VerticesNumber(); i++) 
        {

            double c = 2 * Math.PI * i / graph.VerticesNumber();
            int a= (int) Math.round(x + r * Math.cos(c));
            int b = (int) Math.round(y + r * Math.sin(c));
            xSave[i] = a;
            ySave[i] = b;

        }
        //setting line thickness
        g2.setStroke(new BasicStroke(5));

        // Draws the edges
        for (int i = 0; i < graph.VerticesNumber(); i++) 
        {
            for (int j = 0; j < graph.VerticesNumber(); j++) 
            {
                if (graph.Matrix()[i][j] > 0) {
                    g.setColor(Color.BLACK);
                    g.drawLine(xSave[i], ySave[i], xSave[j], ySave[j]);

                    g.setColor(Color.RED);
                    g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
                    g.drawString(String.valueOf(graph.Matrix()[i][j]), ((xSave[i] + xSave[j]) / 2)+10, ((ySave[i] + ySave[j]) / 2)+25);
                }
            }
        }

        // shortest path line
        for (int i = 0; i < path.size() - 1; i++) 
        {
            g.setColor(Color.GREEN);
            g2.setStroke(new BasicStroke(10));
            
            g.drawLine(xSave[path.get(i)], ySave[path.get(i)], xSave[path.get(i + 1)], ySave[path.get(i + 1)]);
        }

        // Draws the vertices
        for (int i = 0; i < graph.VerticesNumber(); i++) 
        {
            double t = 2 * Math.PI * i / graph.VerticesNumber();
            int x1= (int) Math.round(x + r * Math.cos(t));
            int y1 = (int) Math.round(y + r * Math.sin(t));

            g2.setStroke(new BasicStroke(5));

            g.setColor(Color.cyan);
            g.fillOval(x1 - r1, y1 - r1, 2 * r1+10, 2 * r1+10);
            g.setColor(Color.black);
            g.drawOval(x1 - r1, y1 - r1, 2 * r1+10, 2 * r1+10);
            g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 35));
            g.drawString(String.valueOf(i), x1, y1 + 10);
        }
    }
}
