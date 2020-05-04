package genomu.firestore_helper;

public abstract class DBCommand {
    protected HanWen hanWen;

    public DBCommand(HanWen hanWen) {
        this.hanWen = hanWen;
    }

    public abstract void work();
}