/*
This file is part of Notebot.
Notebot is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
Notebot is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
You should have received a copy of the GNU General Public License along with Notebot. If not, see <https://www.gnu.org/licenses/>.
*/

package xyz.nat1an.notebot;

import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xyz.nat1an.notebot.ingameCommand.CommandManager;
import xyz.nat1an.notebot.utils.NotebotFileManager;

public class Notebot implements ModInitializer {
    public static final Logger LOGGER = LoggerFactory.getLogger("notebot");
    public static final MinecraftClient mc = MinecraftClient.getInstance();

    public static CommandManager commandManager = new CommandManager();

    @Override
    public void onInitialize() {
        commandManager.init();

        NotebotFileManager.init();
        ModRegistries.registerModStuff();
    }
}
