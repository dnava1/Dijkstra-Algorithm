import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Graph implements GraphInterface {
    private int verticesNumber;
    private int[][] matrix; 
    private int s;
    private int t;

    public int VerticesNumber()
    {
        return verticesNumber;
    }
    public int[][] Matrix()
    {
        return matrix;
    }
    public int Source()
    {
        return s;
    }
    public int Target()
    {
        return t;
    }

    /**
     * Instantiates a graph and initializes it with info from a text file.
     *
     * @param filename text file with graph info
     */
    public Graph(String filename) {
        File input = new File(filename);
        Scanner in = null;
        try 
        {
            in = new Scanner(input);
        } 
        catch (FileNotFoundException e) 
        {
            System.out.println("File not found!");
            System.exit(0);
        }
        while (in.hasNextLine()) {
            verticesNumber = in.nextInt();
            matrix = new int[verticesNumber][verticesNumber];

            for (int i = 0; i < verticesNumber; i++) {
                for (int j = 0; j < verticesNumber; j++) {
                    matrix[i][j] = in.nextInt();
                }
            }
            s = in.nextInt();
            t = in.nextInt();
        }
        in.close();
    }
    /**
     * Finds vertices adjacent to a given vertex.
     *
     * @param v given vertex
     * @return list of vertices adjacent to v;
     * 
     */
    public int[] findAdjacencyVertices(int v) {
        int[] vert = new int[verticesNumber];
        int total = 0;

        for (int i = 0; i < verticesNumber; i++) {
            if (matrix[v][i] != 0) {
                vert[total] = i;
                total++;
            }
        }
        return Arrays.copyOf(vert, total);
    }

    private int minDistance(boolean[] visited, int[] distance) {

        int index = -1;
        int min = Integer.MAX_VALUE;

        for (int i = 0; i < verticesNumber; i++) {
            if (!visited[i]) {
                if (distance[i] <= min) {
                    min = distance[i];
                    index = i;
                }
            }
        }

        return index;
    }

    public void allShortestPaths(int[] p, int[] d, int v) {

        boolean[] visited = new boolean[verticesNumber];

        for (int i = 0; i < verticesNumber; i++) {
            visited[i] = false;
            p[i] = -1;
            d[i] = Integer.MAX_VALUE;
        }

        d[v] = 0;

        for (int i = 0; i < verticesNumber - 1; i++) {
            int w = minDistance(visited, d);
            visited[w] = true;

            int[] adj = findAdjacencyVertices(w);

            for (int u : adj) {
                if (!visited[u]) {
                    if (d[w] + matrix[w][u] < d[u]) {
                        d[u] = d[w] + matrix[w][u];
                        p[u] = w;
                    }
                }
            }
        }
    }
}