public class PageEntry implements Comparable<PageEntry> {

    public final String pdfName;
    public final int page;
    public final int count;
    public final String word;

    public PageEntry(String word, String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
        this.word = word;
    }

    @Override
    public int compareTo(PageEntry o) {
        if (count > o.count) {
            return -10;
        } else if (count < o.count) {
            return 10;
        }
        return 0;
    }
}
