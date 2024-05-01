package Client;

import GuiFile.ClickGui.ClickGui;
import de.Hero.clickgui.ClickGUI;
import Client.command.CommandManager;
import Client.event.EventManager;
import Client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;

public class Client {
    public static String NAME = "GradeClient",
            VERSION = "Beta",
            AUTHOR = "XFREELY_";

    public ClickGui cg;
    public ClickGUI clickgui;

    public static Client instance = new Client();

    private ModuleManager moduleManager;

    private CommandManager commandManager;

    public void startClient() {
        this.commandManager = new CommandManager();
        this.moduleManager = new ModuleManager();
        this.commandManager.init();
        this.moduleManager.init();
        this.cg = new ClickGui();
        this.clickgui = new ClickGUI();
        EventManager.instance.register(this.moduleManager, this.commandManager);
        set_title();
    }


    

    public void set_title(){
        Display.setTitle(NAME + " | " + VERSION + " | " + "AUTHOR" + " " + AUTHOR);
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
