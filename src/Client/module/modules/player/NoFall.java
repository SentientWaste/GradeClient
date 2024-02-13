package Client.module.modules.player;

import Client.event.EventTarget;
import Client.event.events.EventPreUpdate;
import Client.module.Category;
import Client.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class NoFall extends Module {
    public NoFall() {
        super("NoFall", Keyboard.KEY_NONE, Category.Player);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        if (mc.thePlayer.fallDistance > 2.0) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
        }
    }
}
