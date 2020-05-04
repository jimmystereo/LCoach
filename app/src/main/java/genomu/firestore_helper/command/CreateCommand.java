package genomu.firestore_helper.command;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class CreateCommand extends DBCommand {
    private Object pojo;
    public CreateCommand(HanWen hanWen,Object pojo) {
        super(hanWen);
        this.pojo = pojo;
    }

    @Override
    public void work() {
        hanWen.sprout(pojo);
    }
}
