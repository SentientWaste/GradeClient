package Client.module.modules.render;

import Client.module.Category;
import Client.module.Module;
import org.lwjgl.input.Keyboard;

public class ESP extends Module {

    public ESP() {
        super("ESP", Keyboard.KEY_NONE, Category.Render);
    }
}
