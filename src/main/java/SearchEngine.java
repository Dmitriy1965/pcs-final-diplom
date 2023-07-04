import java.util.ArrayList;
import java.util.List;

public interface SearchEngine {
    ArrayList<PageEntry> search(String word);
}
