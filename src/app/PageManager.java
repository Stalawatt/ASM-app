package app;

import java.util.HashMap;

public class PageManager {
    private Page currentPage = null;

    public HashMap<String, Page> pages = new HashMap<String, Page>();

    public void switchToPage(Page page) {
        this.currentPage = page;
    }

    public Page getCurrentPage() {

        if (this.currentPage == null) {
            this.currentPage = pages.get("homepage");
        }

        return this.currentPage;
    }

    public void goToHomePage() {
        currentPage = pages.get("homepage");
    }

    public void goToEditorPage() {
        currentPage = pages.get("editor");
    }




}
