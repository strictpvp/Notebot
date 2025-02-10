package xyz.nat1an.notebot.commands.queue;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.FabricClientCommandSource;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.text.Text;
import xyz.nat1an.notebot.NotebotPlayer;

import static xyz.nat1an.notebot.Notebot.mc;

public class NotebotQueueSkipCommand {
    public static void register(CommandDispatcher<FabricClientCommandSource> clientCommandSourceCommandDispatcher,
                                CommandRegistryAccess commandRegistryAccess) {
        clientCommandSourceCommandDispatcher.register(
                ClientCommandManager.literal("notebot")
                        .then(ClientCommandManager.literal("queue")
                                .then(ClientCommandManager.literal("skip")
                                        .executes(NotebotQueueSkipCommand::run)
                                )
                        )
        );
    }

    private static int run(CommandContext<FabricClientCommandSource> context) {
        NotebotPlayer.song = null;

        mc.player.sendMessage(
                Text.literal("§6Skipped"),
                false
        );

        return 1;
    }
}
