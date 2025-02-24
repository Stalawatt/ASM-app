package app;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static Window window;
    public static PageManager pageManager;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                window = new Window();
                pageManager = new PageManager();
                final HomePage homePage = new HomePage(window, pageManager);
                final Editor editorPage = new Editor();

                pageManager.pages.put("homepage", homePage);
                pageManager.pages.put("editor", editorPage);

                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

                window.displayPage(pageManager.getCurrentPage());

                /*

                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        mainLoop();
                    }
                };
                executor.scheduleAtFixedRate(task, 0,16, TimeUnit.MILLISECONDS);


                 */

            }
        });
    }

    public static void mainLoop() {

        window.displayPage(pageManager.getCurrentPage());

    }

}











































