package com.javarush.task.task36.task3608.view;

import com.javarush.task.task36.task3608.model.*;
import com.javarush.task.task36.task3608.controller.*;

public interface View {

    public void refresh(ModelData modelData);
    public void setController(Controller controller);
}
