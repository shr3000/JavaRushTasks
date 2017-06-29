package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.controller.Controller;
import com.javarush.task.task36.task3608.model.ModelData;

/**
 * Created by MaX on 22.02.2017.
 */
public class EditUserView implements View {

    private Controller controller;

    @Override
    public void refresh(ModelData modelData) {
        System.out.println("User to be edited:");
        System.out.print('\t');
        System.out.println(modelData.getActiveUser());
        System.out.println("===================================================");
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void fireEventUserDeleted(long id) {
        controller.onUserDelete(id);
    }
    public void fireEventUserChanged(String name, long id, int level) {
        controller.onUserChange(name, id, level);
    }
}
