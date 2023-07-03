import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {
    protected Map<String, Object> mapAll = new HashMap<>();
    protected File pdfsDir;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        this.pdfsDir = pdfsDir;

        File file = new File(String.valueOf(pdfsDir));

        for (File item : file.listFiles()) {
            if (item.isFile()) {
                var doc = new PdfDocument(new PdfReader(item));
                int pages = doc.getNumberOfPages();
                String text;

                for (int page = 1; page <= pages; page++) {
                    text = PdfTextExtractor.getTextFromPage(doc.getPage(page));
                    String[] words = text.toLowerCase().split("\\P{IsAlphabetic}+");  // кол вхождений каждого слова на странице
                    Map<String, Long> map = Arrays.stream(words)
                            .collect(Collectors.groupingBy(s -> s, Collectors.counting()));

                    for (Map.Entry<String, Long> entry : map.entrySet()) {
                        String key = entry.getKey();  // get key
                        Long value = entry.getValue(); // get value
                        PageEntry tmp = new PageEntry(key, item.getName(),
                                page, Math.toIntExact(value));

                        List<PageEntry> temp2 = new ArrayList<>();
                        if (mapAll.containsKey(key)) {
                            temp2 = (List<PageEntry>) mapAll.get(key);
                        }
                        temp2.add(tmp);
                        mapAll.put(key, temp2);
                    }
                }
                doc.close();
            }
        }
    }

    public ArrayList search(String word) {

        ArrayList<PageEntry> searchWords = (ArrayList<PageEntry>) mapAll.get(word);
        Collections.sort(searchWords);
        return searchWords;
    }
}
