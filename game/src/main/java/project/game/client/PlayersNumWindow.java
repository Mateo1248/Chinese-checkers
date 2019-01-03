package project.game.client;

import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.ChoiceDialog;

public class PlayersNumWindow {

    private int numb;
    private int num;

    public PlayersNumWindow() {

        ArrayList<Integer> choices = new ArrayList<>();
        choices.add(2);
        choices.add(3);
        choices.add(4);
        choices.add(6);
        ChoiceDialog<Integer> dialog = new ChoiceDialog<>(null,choices);
        dialog.setTitle("Select players and bots number ");
        dialog.setContentText("Choose number of Players:");
        Optional<Integer> result = dialog.showAndWait();
        result.ifPresent(num ->
        {
            BotsNumWindow b=new BotsNumWindow(num);
             this.num=num;
             this.numb=b.getnum();
        });

    }
    /**
     * @return number of players
     */
    public int getnumP(){
        return num;
    }

    /**
     * @return number of Bots
     */
    public int getnumB(){
        return numb;
    }

}
