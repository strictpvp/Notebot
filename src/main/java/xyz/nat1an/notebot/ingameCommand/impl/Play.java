package xyz.nat1an.notebot.ingameCommand.impl;

import xyz.nat1an.notebot.ingameCommand.Command;
import xyz.nat1an.notebot.utils.FileDownloader;

import java.nio.file.Path;

import static xyz.nat1an.notebot.Notebot.mc;

public class Play extends Command {
    public Play() {
        super("play/p/q", "play/p/q <direct_link>");
    }

    @Override
    public void onCommand(String string) {
        String[] parts = string.split("\\s+", 50);

        if (parts.length < 2) {
            mc.getNetworkHandler().sendChatMessage("Usage: " + getUsage());
            return;
        }
        if (!parts[1].startsWith("http")) {
            mc.getNetworkHandler().sendChatMessage("Usage: " + getUsage());
            return;
        }

        Thread thread = new Thread(() -> {
            try {
                Path path = FileDownloader.downloadFile(parts[1]);
                mc.getNetworkHandler().sendCommand("notebot queue add " + path);
                mc.getNetworkHandler().sendCommand("notebot start");
                mc.getNetworkHandler().sendChatMessage(path + " added to queue");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        thread.start();
    }
}
