public class PageEntry implements Comparable<PageEntry> {
    public final String pdfName;
    public final int page;
    public final int count;

    public PageEntry(String pdfName, int page, int count) {
        this.pdfName = pdfName;
        this.page = page;
        this.count = count;
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
