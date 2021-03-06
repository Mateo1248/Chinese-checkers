package project.game.client;

import javafx.scene.control.ChoiceDialog;

import java.util.ArrayList;
import java.util.Optional;

/**
 * @author danieldrapala
 *
 */
public class BotsNumWindow {

    private  int numb;

    /**
     * @param bn
     */
    public BotsNumWindow(int bn){
        ArrayList<Integer> choices = new ArrayList<>();
        switch(bn){
            case 2:

                  choices.add(1);
                  choices.add(0);

                  break;
            case 3:
                choices.add(0);
                choices.add(1);
               choices.add(2);
               break;
            case 4:
                choices.add(0);
                 choices.add(1);
                 choices.add(2);
                 choices.add(3);
                    break;
                 case 6:    
                choices.add(0);
                choices.add(1);
                choices.add(2);
                choices.add(3);
                choices.add(4);
                choices.add(5);
                break;
         }
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(null,choices);
        dialog.setTitle("Select  bots number ");
        dialog.setContentText("Choose number of Bots:");
        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(numb ->
                this.numb=numb);

    }
    /**
     * @return Get number of Bots
     */
    public int getnum(){
        return this.numb;
    }
    }
