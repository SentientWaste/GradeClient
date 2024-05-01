package Client.module.modules.render;

import Client.Client;
import Client.module.Category;
import Client.module.Module;
import org.lwjgl.input.Keyboard;

public class ClickGUI extends Module {
    public ClickGUI() {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.Render);
    }

    @Override
    public void onEnable() {
        this.setEnabled(false);
    }

    @Override
    public void onDisable() {
        mc.displayGuiScreen(Client.instance.cg);
    }
}
