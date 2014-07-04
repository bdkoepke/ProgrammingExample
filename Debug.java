public class Debug {
    // the singleton pattern of evil...
    // this is really only appropriate for
    // debugging.
    private static Debug instance;
    private boolean enabled = true;

    private Debug() {
    }

    public static Debug getInstance() {
        if (instance == null)
            instance = new Debug();
        return instance;
    }

    public boolean isDebugEnabled() {
        return this.enabled;
    }

    public void enable() {
        this.enabled = true;
    }

    public void disable() {
        this.enabled = false;
    }

    public void println(String s) {
        System.out.println(s);
    }
}
