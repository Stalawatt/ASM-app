import java.util.Dictionary;
import java.util.Hashtable;

public class PageManager {

    /*
    The currently loaded page is what is shown on the screen at any given time
    */

    public Integer currentlyLoadedPage;
    public Dictionary<Integer, Page> Map;

    public PageManager() {
        this.currentlyLoadedPage = 0;
        this.Map = new Hashtable<Integer,Page>();

    }

    public void addPage(Integer key, Page page) {
        this.Map.put(key, page);
    }

    public void changePage(int page) {
        this.currentlyLoadedPage = page;
    }

    public Page getPage() {
        return Map.get(currentlyLoadedPage);
    }
}
