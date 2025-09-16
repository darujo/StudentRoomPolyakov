package ru.daru_jo.config;

import javafx.application.Application;

public abstract class AbstractJavaFxApplicationSupport extends Application {

    private static String[] savedArgs;

    @Override
    public void init() throws Exception {
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }

    protected static void launchApp(Class<? extends AbstractJavaFxApplicationSupport> appClass, String[] args) {
        AbstractJavaFxApplicationSupport.savedArgs = args;
        Application.launch(appClass, args);
    }
}
