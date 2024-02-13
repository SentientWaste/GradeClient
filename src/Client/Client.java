package Client;

import de.Hero.clickgui.ClickGUI;
import Client.command.CommandManager;
import Client.event.EventManager;
import Client.module.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.Display;

import java.io.File;

public class Client {
    public static String NAME = "GradeClient",
            VERSION = "Beta",
            AUTHOR = "XFREELY_";

    public ClickGUI clickgui;

    public static Client instance = new Client();

    private ModuleManager moduleManager;

    private CommandManager commandManager;

    public void startClient() {
        this.commandManager = new CommandManager();
        this.moduleManager = new ModuleManager();
        this.commandManager.init();
        this.moduleManager.init();
        this.clickgui = new ClickGUI();
        EventManager.instance.register(this.moduleManager, this.commandManager);
        create_folder();
        set_title();
    }

    public void create_folder(){
        File file = new File("C:\\GradeClient");
        File modules_file = new File("C:\\GradeClient\\modules");
        File combat_file = new File("C:\\GradeClient\\combat");
        File movement_file = new File("C:\\GradeClient\\movemoent");
        File player_file = new File("C:\\GradeClient\\player");
        File render_file = new File("C:\\GradeClient\\render");
        File world_file = new File("C:\\GradeClient\\world");
        if (!file.exists()){
            file.mkdirs();
            if(!modules_file.exists()){
                modules_file.mkdirs();
                if(!combat_file.exists()){ combat_file.mkdirs(); }
                if(!movement_file.exists()){ movement_file.mkdirs(); }
                if(!player_file.exists()){ player_file.mkdirs(); }
                if(!render_file.exists()){ render_file.mkdirs(); }
                if(!world_file.exists()){ world_file.mkdirs(); }
            }
        }
    }

    public void set_title(){
        Display.setTitle(NAME + " | " + VERSION + " | " + "AUTHOR" + " " + AUTHOR);
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }
}
