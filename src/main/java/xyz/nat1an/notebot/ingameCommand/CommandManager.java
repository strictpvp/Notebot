package xyz.nat1an.notebot.ingameCommand;

import com.mojang.authlib.GameProfile;
import net.minecraft.network.message.MessageType;
import net.minecraft.network.message.SignedMessage;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;
import xyz.nat1an.notebot.ingameCommand.impl.Play;
import xyz.nat1an.notebot.ingameCommand.impl.Skip;
import xyz.nat1an.notebot.ingameCommand.impl.Stop;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static xyz.nat1an.notebot.Notebot.LOGGER;
import static xyz.nat1an.notebot.Notebot.mc;

public class CommandManager {
    public static List<Command> COMMANDS = new ArrayList<>();

    // TODO: 커맨드 입력 안받아지는거 고치기
    public void init() {
        COMMANDS.add(new Play());
        COMMANDS.add(new Skip());
        COMMANDS.add(new Stop());
    }

    public void onChat(Text text, @Nullable SignedMessage signedMessage, @Nullable GameProfile gameProfile, MessageType.Parameters parameters, Instant instant) {
        String content = "";

        if (signedMessage == null) {
            content = text.getString().replaceFirst("<.*?> ", "");
        } else {
            content = signedMessage.getSignedContent();
        }

        if (!content.startsWith("!")) return;

        commandParse(content);
    }

    public void onServerChat(Text text, boolean b) {
        String content = text.getString().replaceFirst("<.*?> ", "");
        if (!content.startsWith("!")) return;
        commandParse(content);
    }

    private void commandParse(String content) {
        String[] parts = content
                .trim()
                .toLowerCase()
                .substring(1)
                .split("\\s+", 50);

        for (Command cmd : COMMANDS) {
            // 단일 명령어 처리
            if (!cmd.getCmd().contains("/")) {
                if (cmd.getCmd().toLowerCase().equals(parts[0])) {
                    cmd.onCommand(content);
                    return;
                }
            } else { // 다중 명령어 처리
                String[] cmdPart = cmd.getCmd().split("/", 50);
                for (String a : cmdPart) {
                    if (a.toLowerCase().equals(parts[0])) {
                        cmd.onCommand(content);
                        return;
                    }
                }
            }
        }
    }
}
