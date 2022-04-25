// Reference:https://www.hackerearth.com/practice/algorithms/graphs/maximum-flow/tutorial/
import java.util.LinkedList;
import java.util.Scanner;

public class maximum_flow {
  static int vertices;
  static int[][] graph;
  
  public static boolean BFS(int[][] residual, int s, int t, int[] path) {
    boolean[] visited = new boolean[vertices];
    LinkedList<Integer> queue = new LinkedList<>();
    queue.add(s);
    path[s] = -1;
    visited[s] = true;
    while (queue.size() != 0) {
      int u = queue.poll();
      int i = 0;
      while (i < vertices) {
        if (visited[i] == false && residual[u][i] > 0) {
          if (i == t) {
            path[i] = u;
            return true;
          }
          queue.add(i);
          path[i] = u;
          visited[i] = true;
        }
        i++;
      }
    }
    return false;
  }
  public static int Flow_maximum(int given, int num) {
    int[][] residual = new int[vertices][vertices];
    int max = 0; // no flow initially
    // residual graph is initially the same as flow graph
    int i = 0;
    while (i < vertices) {
      //for (int j = 0; j < vertices; j++) {
      int j = 0;
      while(j < vertices) {
        residual[i][j] = graph[i][j];
        j++;
      }
      i++;
    }
    int[] path = new int[vertices];
    // while there is a path from given to num, find its minimum capacity and push that amount of
    // flow.
    while (BFS(residual, given, num, path)) {
      int capacity = Integer.MAX_VALUE;
      int temp = num;
      while (temp != given) {
        int s = path[temp];
        if (capacity > residual[s][temp]) {
          capacity = residual[s][temp];
        }
        temp = s;
      }
      // update residual graph
      temp = num;
      while (temp != given) {
        int s = path[temp];
        
        residual[s][temp] -= capacity;
        residual[temp][s] += capacity;
        temp = s;
      }
      // add new flow to max
      max += capacity;
    }
    return max;
  }


  public static void main(String[] args) {
    Scanner scnr = new Scanner(System.in);
    int instances = scnr.nextInt();
    int[] results = new int[instances];
    for (int k = 0; k < instances; k++) {
      // scnr.nextLine();
      vertices = scnr.nextInt();
      graph = new int[vertices][vertices];
      int edges = scnr.nextInt();
      // scnr.nextLine();
      // fill 2d array.
      for (int i = 0; i < edges; i++) {
        int start_index = scnr.nextInt() - 1;
        int end_index = scnr.nextInt() - 1;
        int capacity = scnr.nextInt();
        graph[start_index][end_index] += capacity;
        // scnr.nextLine();
      }
      int num = vertices - 1;
      int given = 0;
      int max_flow = Flow_maximum(given, num);
      results[k] = max_flow;
    }
    // print results
    for (int j = 0; j < results.length; j++) {
      System.out.println(results[j]);
    }
  }
}
