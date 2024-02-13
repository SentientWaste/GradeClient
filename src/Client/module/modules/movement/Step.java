package Client.module.modules.movement;

import Client.event.EventTarget;
import Client.event.events.EventPreUpdate;
import Client.event.events.EventStep;
import Client.module.Category;
import Client.module.Module;
import Client.values.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.lwjgl.input.Keyboard;

public class Step extends Module {
    private final Mode<String> mode = new Mode<>("Mode", new String[]{"Vanilla", "NCP"}, "Vanilla");
    public Step() {
        super("Step", Keyboard.KEY_Z, Category.Movement);
        this.addValues(this.mode);
    }

    @EventTarget
    public void onUpdate(EventPreUpdate e) {
        this.setSuffix(this.mode.getValue());
        mc.thePlayer.stepHeight = 1.0f;
    }

    @EventTarget
    public void onStep(EventStep e) {
        if (mode.isCurrentMode("NCP")) {
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.42D, mc.thePlayer.posZ, false));
            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.75D, mc.thePlayer.posZ, false));
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
        super.onDisable();
    }
}
