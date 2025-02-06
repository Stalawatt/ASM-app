import java.util.Dictionary;

public class PageManager {

    /*
    The currently loaded page is what is shown on the screen at any given time
    */

    public static Integer currentlyLoadedPage;
    public static Dictionary<Integer, Object> Map;

    public PageManager() {
        this.currentlyLoadedPage = 0;

    }

    public void addPage(Integer key, Object page) {
        Map.put(key, page);
    }


    public void changePage(int page) {
        this.currentlyLoadedPage = page;
    }

    public Object getPage() {
        return Map.get(currentlyLoadedPage);
    }
}
