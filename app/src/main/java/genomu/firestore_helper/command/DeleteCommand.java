package genomu.firestore_helper.command;

import genomu.firestore_helper.DBCommand;
import genomu.firestore_helper.HanWen;

public class DeleteCommand<E> extends DBCommand {
    private E POJO;
    public DeleteCommand(HanWen hanWen,E POJO) {
        super(hanWen);
        this.POJO = POJO;
    }

    @Override
    public void work() {
        // plz override POJO toString(){return getId();}
        hanWen.seek().document(POJO.toString()).delete();
    }
}
