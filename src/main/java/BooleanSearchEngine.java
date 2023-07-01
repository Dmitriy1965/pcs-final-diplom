import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {

    protected List<PageEntry> allwords = new ArrayList<>();
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
                        allwords.add(tmp);
                    }
                }
                doc.close();
            }
        }
        Collections.sort(allwords);
    }

    public String search(String word) {
        String json = null;
        List<PageEntry> searchWords = new ArrayList<>();

        try {
            for (int i = 0; i <= allwords.size() - 1; i++) {
                if (word.equals(allwords.get(i).word)) {

                    String pdfName = allwords.get(i).pdfName;
                    int page = allwords.get(i).page;
                    int count = allwords.get(i).count;

                    PageEntry tmp2 = new PageEntry(word, pdfName, page, count);
                    searchWords.add(tmp2);
                }
            }
            ObjectMapper objectMapper = new ObjectMapper();
            json = objectMapper.writeValueAsString(searchWords);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;
    }
}
