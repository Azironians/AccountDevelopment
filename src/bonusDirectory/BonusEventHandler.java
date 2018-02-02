package bonusDirectory;

import managment.actionManagement.ActionEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BonusEventHandler {

    public void handle(final ActionEvent ... events){
        final List<ActionEvent> eventList = new ArrayList<>(){{
            this.addAll(Arrays.asList(events));
        }};
        handle(eventList);
    }

    public void handle(List<ActionEvent> events){

    }

}
