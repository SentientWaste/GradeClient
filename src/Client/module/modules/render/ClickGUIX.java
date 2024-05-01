package Client.module.modules.render;

import Client.Client;
import Client.module.Category;
import Client.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUIX extends Module {
    public ClickGUIX() {
        super("ClickGUIX", Keyboard.KEY_K, Category.Render);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(Client.instance.clickgui);
    }
}
