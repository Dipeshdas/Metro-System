package Codeforces;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class Pair{
    int first;
    int second;
    Pair(int first,int second){
        this.first=first;
        this.second=second;
    }
}

class Verteices{
    public ArrayList<ArrayList<ArrayList<Integer>>>adjaCentList;
    public ArrayList<ArrayList<Integer>>edges=new ArrayList<>();
    public int totalVertex;

    public Map<String,Integer> map=new HashMap<>();

    public void showStation(){
        ArrayList<String>station=new ArrayList<>();

        for(Map.Entry<String, Integer>ent:map.entrySet()){
            station.add(ent.getKey());
        }

        System.out.println("Availaibe Stations Are::");
        for(int i=0;i<station.size();i++){
            System.out.println("<@> "+station.get(i));
        }
    }



    public void addEdge(String u, String v, int dist){
        int firstNode=Integer.parseInt(u.substring(0,u.indexOf('.')));
        int secondNode=Integer.parseInt(v.substring(0,v.indexOf('.')));
        map.put(u.substring(u.indexOf('.')+1),firstNode);
        map.put(v.substring(v.indexOf('.')+1),secondNode);


        totalVertex=Math.max(totalVertex,Math.max(firstNode,secondNode));

        ArrayList<Integer>temp=new ArrayList<>();
        temp.add(firstNode);
        temp.add(secondNode);
        temp.add(dist);
        edges.add(temp);


    }

    public  void createGraph(){
        adjaCentList=new ArrayList<>();
        int m=edges.size();
        //System.out.println(m);

        for(int i=0;i<=totalVertex;i++){
            adjaCentList.add(new ArrayList<ArrayList<Integer>>());
        }

        for(int i=0;i<m;i++){
            int u=edges.get(i).get(0);
            int v=edges.get(i).get(1);
            int w=edges.get(i).get(2);
            ArrayList<Integer> t1 = new ArrayList<Integer>();
            ArrayList<Integer> t2 = new ArrayList<Integer>();
            t1.add(v);
            t1.add(w);
            t2.add(u);
            t2.add(w);
            adjaCentList.get(u).add(t1);
            adjaCentList.get(v).add(t2);
        }

        //System.out.println(adjaCentList);


    }

    public int[] dijkstra(int V, ArrayList<ArrayList<ArrayList<Integer>>> adj,int source)
    {
        PriorityQueue<Pair> pq=new PriorityQueue<>((x, y)->x.first-y.first);

        int dist[]=new int[V+1];
        for(int i=0;i<V+1;i++){
            dist[i]=(int)1e9;
        }

        dist[source]=0;
        pq.add(new Pair(0,source));

        while(!pq.isEmpty()){
            int node=pq.peek().second;
            int dis=pq.peek().first;
            pq.poll();

            for(int i=0;i<adj.get(node).size();i++){
                int adjNode=adj.get(node).get(i).get(0);
                int edgeWeight=adj.get(node).get(i).get(1);

                if(dis+edgeWeight<dist[adjNode]){
                    dist[adjNode]=dis+edgeWeight;
                    pq.add(new Pair(dist[adjNode],adjNode));
                }
            }

        }
        return dist;

    }

    public int getShortestDist(String u,String v){
        int dist[]=dijkstra(totalVertex,adjaCentList,map.get(u));
        if(dist[map.get(v)]!=(int)1e9) return dist[map.get(v)];
        return 0;
    }

    public ArrayList<Integer> printShortestPath(int n, ArrayList<ArrayList<ArrayList<Integer>>> adj, int source,int dest){
        PriorityQueue<Pair> pq =
                new PriorityQueue<Pair>((x,y) -> x.first - y.first);

        // Create a dist array for storing the updated distances and a parent array
        //for storing the nodes from where the current nodes represented by indices of
        // the parent array came from.
        int[] dist = new int[n+1];
        int[] parent =new int[n+1];
        for(int i = 0;i<=n;i++) {
            dist[i] = (int)(1e9);
            parent[i] = i;
        }

        dist[source] = 0;

        // Push the source node to the queue.
        pq.add(new Pair(0, source));
        while(pq.size() != 0) {

            // Topmost element of the priority queue is with minimum distance value.
            Pair it = pq.peek();
            int node = it.second;
            int dis = it.first;
            pq.remove();

            // Iterate through the adjacent nodes of the current popped node.
            for(ArrayList<Integer> iter : adj.get(node)) {
                int adjNode = iter.get(0);
                int edW = iter.get(1);

                // Check if the previously stored distance value is
                // greater than the current computed value or not,
                // if yes then update the distance value.
                if(dis + edW < dist[adjNode]) {
                    dist[adjNode] = dis + edW;
                    pq.add(new Pair(dis + edW, adjNode));

                    // Update the parent of the adjNode to the recent
                    // node where it came from.
                    parent[adjNode] = node;
                }
            }
        }

        // Store the final path in the ‘path’ array.
        ArrayList<Integer> path = new ArrayList<>();

        // If distance to a node could not be found, return an array containing -1.
        if(dist[dest] == 1e9) {
            path.add(-1);
            return path;
        }

        int node = dest;
        // o(N)
        while(parent[node] != node) {
            path.add(node);
            node = parent[node];
        }
        path.add(source);

        // Since the path stored is in a reverse order, we reverse the array
        // to get the final answer and then return the array.
        Collections.reverse(path);
        return path;
    }

    public void getShortestPath(String u,String v){
        ArrayList<Integer>path=printShortestPath(totalVertex,adjaCentList,map.get(u),map.get(v));
        printPaths(path);
    }

    private void printPaths(ArrayList<Integer>path){
        if(path.size()==1) System.out.println("path doest not exit");
        else {
            String ans = "";

            for (int i = 0; i < path.size(); i++) {

                for (Map.Entry<String, Integer> ent : map.entrySet()) {

                    if (ent.getValue() == path.get(i)) {
                        if (i == path.size() - 1) {
                            ans = ans + ent.getKey();
                        } else {
                            ans = ans + ent.getKey() + " --->> ";
                        }
                    }
                }
            }
            System.out.println(ans);
        }
    }



}

public class SPF {

    public static void main(String[] args) throws IOException {
        Verteices g=new Verteices();


        g.addEdge("0.SEALDAH", "1.DUMDUM", 15);
        g.addEdge("1.DUMDUM", "2.SODEPUR", 12);
        g.addEdge("1.DUMDUM", "3.BARASAT", 11);
        g.addEdge("3.BARASAT", "4.NICCO PARK", 11);
        g.addEdge("4.NICCO PARK", "5.RABINDRA SAROBAR", 10);
        g.addEdge("5.RABINDRA SAROBAR", "0.SEALDAH", 8);
        g.addEdge("0.SEALDAH", "6.DIAMOND PARK", 18);
        g.addEdge("5.RABINDRA SAROBAR", "7.KALIGHAT", 3);
        g.addEdge("0.SEALDAH", "7.KALIGHAT", 7);
        g.addEdge("2.SODEPUR", "8.TITAGARH", 6);
        g.addEdge("8.TITAGARH", "9.BARRACKPORE", 3);
        g.addEdge("0.SEALDAH", "10.BIDHAN NAGAR", 7);
        g.addEdge("10.BIDHAN NAGAR", "4.NICCO PARK", 2);
        g.addEdge("4.NICCO PARK", "11.TITUMIR", 25);
        g.addEdge("1.DUMDUM", "11.TITUMIR", 11);
        g.addEdge("11.TITUMIR", "12.NEW BARRACKPORE", 8);
        g.addEdge("1.DUMDUM", "13.BIRATI", 4);
        g.addEdge("13.BIRATI", "12.NEW BARRACKPORE", 3);
        g.createGraph();

        System.out.println("\n\t\t\t****WELCOME TO THE KOLKATA METRO APP*****");
        BufferedReader inp = new BufferedReader(new InputStreamReader(System.in));
        //STARTING SWITCH CASE
        while(true)
        {
            System.out.println("\t\t\t\t~~LIST OF ACTIONS~~\n\n");
            System.out.println("1. LIST ALL THE STATIONS IN THE MAP");
            System.out.println("2. GET SHORTEST DISTANCE FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("3. GET SHORTEST PATH (DISTANCE WISE) TO REACH FROM A 'SOURCE' STATION TO 'DESTINATION' STATION");
            System.out.println("4. EXIT THE MENU");
            System.out.print("\nENTER YOUR CHOICE FROM THE ABOVE LIST (1 to 4) : ");
            int choice = -1;
            try {
                choice = Integer.parseInt(inp.readLine());
            } catch(Exception e) {
                // default will handle
            }
            System.out.print("\n***********************************************************\n");
            if(choice == 7)
            {
                System.exit(0);
            }
            switch(choice) {
                case 1:
                    g.showStation();
                    break;

                case 2:
                    System.out.println("ENTER THE NAME OF SOURCE STATION: ");
                    String u = (inp.readLine()).toUpperCase();
                    System.out.println("ENTER THE NAME OF DESTINATION STATION: ");
                    String v = (inp.readLine()).toUpperCase();
                    System.out.println();
                    System.out.println("Shortest distance between "+u+" and "+v + " is :"+g.getShortestDist(u, v) + "k.M");

                    break;

                case 3:
                    System.out.println("ENTER THE NAME OF SOURCE STATION: ");
                    String ux = (inp.readLine()).toUpperCase();
                    System.out.println("ENTER THE NAME OF DESTINATION STATION: ");
                    String vx = (inp.readLine()).toUpperCase();
                    System.out.println();
                    System.out.println("Shortest path between "+ux+" and "+vx + " is :");
                    g.getShortestPath(ux, vx);
                    break;

                default:  //If switch expression does not match with any case,
                    //default statements are executed by the program.
                    //No break is needed in the default case
                    System.out.println("Please enter a valid option! ");
                    System.out.println("The options you can choose are from 1 to 4. ");

            }
        }
    }
}
