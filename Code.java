import java.io.*;
import java.math.*;
import java.security.*;
import java.text.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.function.*;
import java.util.regex.*;
import java.util.stream.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

class Result {

    static int V;
    static int E;
    static Edge []edge;

    static class Edge implements Comparable<Edge>{
        int src;
        int dest;
        int weight;

        public int compareTo(Edge x){
            return this.weight - x.weight;
        }

    }

    static class Subset{
        int parent;
        int rank;
    }

    static class Graph{
        

        public Graph(int v, int e){
            V = v;
            E = e;
            edge = new Edge[E];

            for(int i=0; i<e;i++){
                edge[i] = new Edge(); 
            }
        }
    }

    static int find(Subset []subsets, int i){
        if(subsets[i].parent != i){
            subsets[i].parent = find(subsets, subsets[i].parent);
        }
        
        return subsets[i].parent;
        
        
    }

    static void union(Subset []subsets, int x, int y){
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if(subsets[xroot].rank>subsets[yroot].rank){
            subsets[yroot].parent = xroot; 
        }
        else if(subsets[xroot].rank<subsets[yroot].rank){
            subsets[xroot].parent = yroot;            
        }
        else{
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }

    public static int kruskals(int gNodes, List<Integer> gFrom, List<Integer> gTo, List<Integer> gWeight) {
        int res = 0;
        int V = gNodes;
        int E =  gFrom.size();

        Graph g = new Graph(V, E);
        for(int i =0; i<E; i++){
            edge[i].src = gFrom.get(i);
            edge[i].dest = gTo.get(i);
            edge[i].weight = gWeight.get(i);

        }

        
        Arrays.sort(edge);
        

        Subset []subsets = new Subset[V];
        for(int i =0; i<V; i++){
            subsets[i] = new Subset();
            subsets[i].parent = i;
            subsets[i].rank =0;
        }

        
        int e =0;
        int k =0;
        
        while(e<V-1){
            
            Edge new_edge = new Edge();
            new_edge = edge[k++];
            
            int x = find(subsets, new_edge.src);
            int y = find(subsets, new_edge.dest);

            if(x!= y){
                e++;
                res = res + new_edge.weight;
                union(subsets, x, y);
            }
        }
        

        return res;
    }

}

public class Solution {
    public static void main(String[] args){
        Scanner s = new Scanner(System.in);
        int nodes = s.nextInt();
        int edges = s.nextInt();
        List<Integer> source = new ArrayList<>(); 
        List<Integer> destination = new ArrayList<>(); 
        List<Integer> weights = new ArrayList<>(); 

        for(int i =0; i<edges; i++){
            int so = s.nextInt();
            int d = s.nextInt();
            int w = s.nextInt();

            source.add(so-1);
            destination.add(d-1);
            weights.add(w);
        }

        Result r = new Result();
        int fin = r.kruskals(nodes, source, destination, weights);
        System.out.print(fin);
    }
}
