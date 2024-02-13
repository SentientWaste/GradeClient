package Client.module;

import Client.event.EventTarget;
import Client.event.events.EventKey;
import Client.module.modules.combat.*;
import Client.module.modules.movement.*;
import Client.module.modules.player.*;
import Client.module.modules.render.ClickGUI;
import Client.module.modules.render.FreeCam;
import Client.module.modules.render.HUD;
import Client.module.modules.world.Scaffold;

import java.util.ArrayList;

public class ModuleManager {
    public final ArrayList<Module> modules = new ArrayList<>();

    public void init() {
        this.modules.add(new Criticals());
        this.modules.add(new KillAura());
        this.modules.add(new Velocity());

        this.modules.add(new Fly());
        this.modules.add(new InvMove());
        this.modules.add(new NoSlow());
        this.modules.add(new Speed());
        this.modules.add(new Sprint());
        this.modules.add(new Step());

        this.modules.add(new NoFall());

        this.modules.add(new ClickGUI());
        this.modules.add(new FreeCam());
        this.modules.add(new HUD());

        this.modules.add(new Scaffold());
    }

    @EventTarget
    public void onKey(EventKey e) {
        for (Module module : this.modules) {
            if (module.getKey() != e.getKey()) continue;
            module.setEnabled(!module.isEnabled());
        }
    }

    public Module getModuleByName(String name) {
        for (Module module : this.modules) {
            if (!module.getName().equalsIgnoreCase(name)) continue;
            return module;
        }
        return null;
    }
}
