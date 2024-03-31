import java.io.FileNotFoundException;
import java.util.List;

public interface GraphReaderInterface {
  public List<NodeInterface> loadFromFile (String filename) throws FileNotFoundException;
}
