package com.javarush.task.task36.task3601;

import java.util.List;

/**
 * Created by MaX on 21.04.2017.
 */
public class Controller {
    Model model = new Model();
    public List<String> onDataListShow() {
        return model.getStringDataList();
    }
}
