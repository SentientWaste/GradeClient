package Client.module.modules.movement;

import Client.event.EventTarget;
import Client.event.events.EventPostUpdate;
import Client.event.events.EventPreUpdate;
import Client.module.Category;
import Client.module.Module;
import Client.values.Mode;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import org.lwjgl.input.Keyboard;

public class NoSlow extends Module {
    private final Mode<String> mode = new Mode<>("Mode", new String[]{"Vanilla", "NCP"}, "Vanilla");
    public NoSlow() {
        super("NoSlow", Keyboard.KEY_B, Category.Movement);
        this.addValues(this.mode);
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {
        if (mode.isCurrentMode("NCP")) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @EventTarget
    public void onPost(EventPostUpdate e) {
        if (mode.isCurrentMode("NCP")) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
        }
    }
}
