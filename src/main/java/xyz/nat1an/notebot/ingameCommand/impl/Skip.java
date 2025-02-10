package xyz.nat1an.notebot.ingameCommand.impl;

import xyz.nat1an.notebot.ingameCommand.Command;

import static xyz.nat1an.notebot.Notebot.mc;

public class Skip extends Command {
    public Skip() {
        super("skip", "skip");
    }

    @Override
    public void onCommand(String string) {
        mc.getNetworkHandler().sendCommand("notebot queue skip");
        mc.getNetworkHandler().sendChatMessage("Skipped");
    }
}
