package xyz.nat1an.notebot.ingameCommand.impl;

import xyz.nat1an.notebot.ingameCommand.Command;

import static xyz.nat1an.notebot.Notebot.mc;

public class Stop extends Command {
    public Stop() {
        super("stop", "stop");
    }

    @Override
    public void onCommand(String string) {
        mc.getNetworkHandler().sendCommand("notebot stop");
        mc.getNetworkHandler().sendChatMessage("Stop and Queue removed");
    }
}
