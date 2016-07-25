package actors.define;

/**
 * Created by infosea on 2016-07-21.
 */
public class App {
    public static void main(String[] args) {
        akka.Main.main(new String[]{MyUntypedActor.class.getName()});
    }
}
