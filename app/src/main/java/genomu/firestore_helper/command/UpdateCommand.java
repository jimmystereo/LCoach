package genomu.firestore_helper.command;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class UpdateCommand<E> extends DBCommand {
    private E POJO;
    public UpdateCommand(HanWen hanWen,E POJO) {
        super(hanWen);
        this.POJO = POJO;
    }

    @Override
    public void work() {
        hanWen.seek().document(POJO.toString()).delete();
        hanWen.sprout(POJO);
    }
}
