package ru.daru_jo.config;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.io.InputStream;

public class ControllersConfiguration {
    public ViewHolder getMainView() throws IOException {
        return loadView("fxml/main.fxml");
    }
    public ViewHolder getEditView() throws IOException {
        return loadView("fxml/edit_strait.fxml");
    }
    public ViewHolder getFilterView() throws IOException {
        return loadView("fxml/filter-view.fxml");
    }

    protected ViewHolder loadView(String url) throws IOException {
        try (InputStream fxmlStream = getClass().getClassLoader().getResourceAsStream(url)) {
            FXMLLoader loader = new FXMLLoader();
            loader.load(fxmlStream);
            return new ViewHolder(loader.getRoot(), loader.getController());
        }
    }

    public class ViewHolder {
        private Parent view;
        private Object controller;

        public ViewHolder(Parent view, Object controller) {
            this.view = view;
            this.controller = controller;
        }

        public Parent getView() {
            return view;
        }

        public void setView(Parent view) {
            this.view = view;
        }

        public Object getController() {
            return controller;
        }

        public void setController(Object controller) {
            this.controller = controller;
        }
    }

}
