

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class BooleanSearchEngine implements SearchEngine {

    public Map<String, String> search1 = new HashMap<>();
    File pdfsDir = null;

    public BooleanSearchEngine(File pdfsDir) throws IOException {
        this.pdfsDir = pdfsDir;

//        public Map<String, String> search1 = new HashMap<>();
        List<PageEntry> allwords = new ArrayList<>();
        File file = new File(String.valueOf(pdfsDir));
//try {
        System.out.println(file.listFiles());

        for (File item : file.listFiles()) {
            // проверим, является ли объект каталогом
            if (item.isDirectory()) {
            } else {
                var doc = new PdfDocument(new PdfReader(item));
                int pages = doc.getNumberOfPages();
                String text;

                for (int page = 1; page <= pages; page++) {
                    text = PdfTextExtractor.getTextFromPage(doc.getPage(page));
                    String[] words = text.toLowerCase().split("\\P{IsAlphabetic}+");
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
//        } catch (ArithmeticException e ) {
//
//        System.out.println("Ошибка! Каталог не найден!");
//    }
        }
        Collections.sort(allwords);
//                public Map<String, String> search1 = new HashMap<>();
        String searchValue = null;
        for (int i = 0; i <= allwords.size() - 1; i++) {

            String pageValue = allwords.get(i).pdfName + " " +
                    (allwords.get(i).page) + "  " +
                    (allwords.get(i).count) + "\n";

            searchValue = search1.get(allwords.get(i).word);
            if (searchValue == null) {
                searchValue = pageValue;
            } else {
                searchValue = searchValue + pageValue;
            }
            search1.put(allwords.get(i).word, searchValue);         // to get the arraylist
        }
    }


    public String search(String word) {
//        return search1.get(word);

        String json = null;
        try {
            json = new ObjectMapper().writeValueAsString(search1.get(word));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return json;


//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String json = mapper.writeValueAsString( return search1.get(word));
//            return json;
//        } catch (IOException ex) {
//            Logger.getLogger(JacksonMapToJSONStringExample.class.getName())
//                    .log(Level.SEVERE, null, ex);
//        }
    }
}



