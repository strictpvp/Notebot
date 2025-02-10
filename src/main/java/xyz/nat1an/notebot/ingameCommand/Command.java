package xyz.nat1an.notebot.ingameCommand;

public class Command {
    private final String cmd;
    private final String usage;

    public Command(String cmd, String usage) {
        this.cmd = cmd;
        this.usage = usage;
    }

    public String getCmd() {
        return cmd;
    }

    public String getUsage() {
        return usage;
    }

    public void onCommand(String string) {
    }
}
