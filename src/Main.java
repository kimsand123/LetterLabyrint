import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class Main {

    int k;
    int numberOfLists;
    char[][] letterMatrix;

    public static void main(String[] args) throws IOException {
        Main go = new Main();

        AdjacencyList al = go.getInput();
        System.out.println(al.BFS(0));
        ;
    }





    public AdjacencyList getInput() throws IOException {
        BufferedReader scanner = new BufferedReader(new InputStreamReader(System.in));
        String row="";


        //Scanner scanner = new Scanner(System.in);
        k = Integer.parseInt(scanner.readLine());
        numberOfLists=k*k;
        AdjacencyList al = new AdjacencyList();
        letterMatrix = new char[k][k];
        System.out.println();


        for (int rowCounter = 0;rowCounter<k;rowCounter++) {
            row = scanner.readLine();
            for (int columnCounter = 0; columnCounter < k; columnCounter++) {
                letterMatrix[columnCounter][rowCounter]= row.charAt(columnCounter);
            }
        }
        scanner.close();

        for (int rowCounter = 0;rowCounter<k;rowCounter++) {

            for (int columnCounter = 0; columnCounter < k; columnCounter++) {
                checkAndAdd(al,"left", columnCounter,rowCounter);
                checkAndAdd(al,"right",columnCounter,rowCounter);
                checkAndAdd(al,"up",columnCounter,rowCounter);
                checkAndAdd(al,"down",columnCounter,rowCounter);
            }
        }
        return al;
    }

    public void checkAndAdd(AdjacencyList al, String direction, int column, int row){

        switch(direction){
            case "left":
                if (!(column==0)){
                    if (letterMatrix[column-1][row]!=letterMatrix[column][row]){
                        al.addEdgeToVertex(row*k+column, row*k+column-1);
                    }
                }
                break;
            case "right":
                if (!(column==k-1)){
                    if (letterMatrix[column+1][row]!=letterMatrix[column][row]){
                        al.addEdgeToVertex(row*k+column, row*k+column+1);


                    }
                }
                break;
            case "up":
                if (!(row==0)){
                    if (letterMatrix[column][row-1]!=letterMatrix[column][row]){
                        al.addEdgeToVertex(row*5+column, (row-1)*5+column);
                    }
                }
                break;
            case "down":
                if (!(row==k-1)){
                    if (letterMatrix[column][row+1]!=letterMatrix[column][row]){
                        al.addEdgeToVertex(row*5+column, (row+1)*5+column);
                    }
                }
                break;
        }

    }

    public class AdjacencyList {
        public LinkedList<Integer> adjacencyList[];

        public AdjacencyList() {
            adjacencyList = new LinkedList[numberOfLists];

            //Creating the root list
            for(int counter = 0; counter < numberOfLists;counter++){
                adjacencyList[counter] = new LinkedList<>();
            }
        }

        public void addEdgeToVertex(int vertex, int connectedNode) {
            int index =0;
            ListIterator<Integer> listIterator = adjacencyList[vertex].listIterator();

            while (listIterator.hasNext()) {
                if (listIterator.next() > connectedNode) {
                    break;
                } else{
                    index++;
                }
            }

            adjacencyList[vertex].add(index,connectedNode);
        }

        public int BFS(int startpoint){
            LinkedList<Integer> BFSqueue = new LinkedList<Integer>();
            boolean isVerticeVisited[] = new boolean[numberOfLists];
            int numberOfSteps=0;
            int smallestNumberOfSteps=0;

            BFSqueue.add(startpoint);
            isVerticeVisited[startpoint]= true;

            while(BFSqueue.size()!=0) {
                numberOfSteps = bfsRecurse(startpoint, isVerticeVisited, BFSqueue);
                if (numberOfSteps<smallestNumberOfSteps){
                    smallestNumberOfSteps=numberOfSteps;
                }
                BFSqueue.poll();
                }

            return numberOfSteps-1;
        }

        public int  bfsRecurse(int startpoint, boolean isVerticeVisited[], LinkedList<Integer> BFSqueue){
            int steps=0;

            Iterator<Integer>  adjacentVertices = adjacencyList[startpoint].listIterator();

            while (adjacentVertices.hasNext()){
                int checkVertexForVisited = adjacentVertices.next();
                if(!isVerticeVisited[checkVertexForVisited]){
                    isVerticeVisited[checkVertexForVisited]= true;
                    BFSqueue.add(checkVertexForVisited);
                    steps++;
                }
                bfsRecurse(BFSqueue.poll(), isVerticeVisited, BFSqueue);
            }
            return steps;
        }


    }
}