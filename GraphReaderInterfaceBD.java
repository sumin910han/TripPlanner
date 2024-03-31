import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Place holder class that replicates loading files and sending the correct output to be handled by BD.
 */
public class GraphReaderInterfaceBD implements GraphReaderInterface {
    @Override
    public List<NodeInterface> loadFromFile(String filename) throws FileNotFoundException {
        if(!filename.contains("good file")){
            throw new FileNotFoundException();
        }
        return new LinkedList<>(Arrays.asList(new NodeInterfaceBD("Node1", "node one"), new NodeInterfaceBD("Node2", "node two"), new NodeInterfaceBD("Node3", "node three")));
    }
}
