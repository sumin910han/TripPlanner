import org.junit.jupiter.api.Test;

import javax.print.attribute.IntegerSyntax;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
public class AlgorithmEngineerTests {
    @Test
    public void Test1(){
        int count = 0;
        AdvancedGraphAE graph = new AdvancedGraphAE();
        NodeAE temp = new NodeAE("Bascom Hall", "Hall");
        NodeAE temp2 = new NodeAE("Deluca Biochemistry Building", "Hall");
        NodeAE temp3 = new NodeAE("Vilas Hall", "Hall");
        NodeAE temp4 = new NodeAE("Birge Hall", "Hall");

        graph.insertNode(temp);
        graph.insertNode(temp2);
        graph.insertNode(temp3);
        graph.insertNode(temp4);



        EdgeAE edge1 = new EdgeAE(temp, temp2, 3);
        EdgeAE edge2 = new EdgeAE(temp2, temp3, 2);
        EdgeAE edge3 = new EdgeAE(temp3, temp4, 1);
        EdgeAE edge4 = new EdgeAE(temp4, temp, 8);


        graph.insertEdge(temp, temp2, edge1);
        graph.insertEdge(temp2, temp3, edge2);
        graph.insertEdge(temp3, temp4, edge3);
        graph.insertEdge(temp4, temp, edge4);


        graph.toggleStatus(temp, temp2);

        if (!edge1.getStatus()){
            count++;
        }

        assertEquals(1, count);

    }

    @Test
    public void Test2(){
        int count = 0;
        AdvancedGraphAE graph = new AdvancedGraphAE();
        NodeAE temp = new NodeAE("Bascom Hall", "Hall");
        NodeAE temp2 = new NodeAE("Deluca Biochemistry Building", "Hall");
        NodeAE temp3 = new NodeAE("Vilas Hall", "Hall");
        NodeAE temp4 = new NodeAE("Birge Hall", "Hall");

        graph.insertNode(temp);
        graph.insertNode(temp2);
        graph.insertNode(temp3);
        graph.insertNode(temp4);



        EdgeAE edge1 = new EdgeAE(temp, temp2, 3);
        EdgeAE edge2 = new EdgeAE(temp2, temp3, 2);
        EdgeAE edge3 = new EdgeAE(temp3, temp4, 1);
        EdgeAE edge4 = new EdgeAE(temp4, temp, 8);


        graph.insertEdge(temp, temp2, edge1);
        graph.insertEdge(temp2, temp3, edge2);
        graph.insertEdge(temp3, temp4, edge3);
        graph.insertEdge(temp4, temp, edge4);

        if (graph.shortestPathCost(temp, temp2) == 3){
            count++;
        }

        assertEquals(1, count);
    }


    @Test
    public void Test3(){
        int count = 0;
        AdvancedGraphAE graph = new AdvancedGraphAE();
        NodeAE temp = new NodeAE("Bascom Hall", "Hall");
        NodeAE temp2 = new NodeAE("Deluca Biochemistry Building", "Hall");
        NodeAE temp3 = new NodeAE("Vilas Hall", "Hall");
        NodeAE temp4 = new NodeAE("Birge Hall", "Hall");

        graph.insertNode(temp);
        graph.insertNode(temp2);
        graph.insertNode(temp3);
        graph.insertNode(temp4);



        EdgeAE edge1 = new EdgeAE(temp, temp2, 3);
        EdgeAE edge2 = new EdgeAE(temp2, temp3, 2);
        EdgeAE edge3 = new EdgeAE(temp3, temp4, 1);
        EdgeAE edge4 = new EdgeAE(temp4, temp, 8);


        graph.insertEdge(temp, temp2, edge1);
        graph.insertEdge(temp2, temp3, edge2);
        graph.insertEdge(temp3, temp4, edge3);
        graph.insertEdge(temp4, temp, edge4);

        List<EdgeInterface> list = graph.minimumSpanningTree();

        if (list.get(0).getStart().equals(temp3)){
            count++;
        }

        if (list.get(1).getStart().equals(temp2)){
            count++;
        }

        assertEquals(count, 2);
    }
    @Test
    public void Test4(){
        int count = 0;
        AdvancedGraphAE graph = new AdvancedGraphAE();
        NodeAE temp = new NodeAE("Bascom Hall", "Hall");
        NodeAE temp2 = new NodeAE("Deluca Biochemistry Building", "Hall");
        NodeAE temp3 = new NodeAE("Vilas Hall", "Hall");
        NodeAE temp4 = new NodeAE("Birge Hall", "Hall");

        graph.insertNode(temp);
        graph.insertNode(temp2);
        graph.insertNode(temp3);
        graph.insertNode(temp4);



        EdgeAE edge1 = new EdgeAE(temp, temp2, 3);
        EdgeAE edge2 = new EdgeAE(temp2, temp3, 2);
        EdgeAE edge3 = new EdgeAE(temp3, temp4, 1);
        EdgeAE edge4 = new EdgeAE(temp4, temp, 8);


        graph.insertEdge(temp, temp2, edge1);
        graph.insertEdge(temp2, temp3, edge2);
        graph.insertEdge(temp3, temp4, edge3);
        graph.insertEdge(temp4, temp, edge4);

        List<NodeInterface> test = graph.shortestPathData(temp, temp2);
        if (test.toString().contains("Bascom Hall") && test.toString().contains("Deluca")){
            count++;
        }

        assertEquals(1, count);

    }

    @Test
    public void Test5(){
        int count = 0;
        AdvancedGraphAE graph = new AdvancedGraphAE();
        NodeAE temp = new NodeAE("Bascom Hall", "Hall");
        NodeAE temp2 = new NodeAE("Deluca Biochemistry Building", "Hall");
        NodeAE temp3 = new NodeAE("Vilas Hall", "Hall");
        NodeAE temp4 = new NodeAE("Birge Hall", "Hall");
        NodeAE temp5 = new NodeAE("Witte Residence Hall", "Dorm");
        NodeAE temp6 = new NodeAE("Chadbourne Residence Hall", "Dorm");

        graph.insertNode(temp);
        graph.insertNode(temp2);
        graph.insertNode(temp3);
        graph.insertNode(temp4);

        graph.insertNode(temp5);
        graph.insertNode(temp6);




        EdgeAE edge1 = new EdgeAE(temp, temp2, 3);
        EdgeAE edge2 = new EdgeAE(temp2, temp3, 2);
        EdgeAE edge3 = new EdgeAE(temp3, temp4, 1);
        EdgeAE edge4 = new EdgeAE(temp4, temp, 8);

        edge3.setStatus(false);

        graph.insertEdge(temp, temp2, edge1);
        graph.insertEdge(temp2, temp3, edge2);
        graph.insertEdge(temp3, temp4, edge3);
        graph.insertEdge(temp4, temp, edge4);

        try{
            graph.shortestPathData(temp5, temp6);
        } catch (Exception e){
            count++;
        }



        if (graph.minimumSpanningTree().get(0).getStart().equals(temp2)){
            count++;
        }

        assertEquals(2, count);

    }


}
