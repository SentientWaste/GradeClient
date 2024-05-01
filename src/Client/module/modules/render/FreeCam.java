package Client.module.modules.render;

import com.mojang.authlib.GameProfile;
import Client.event.EventTarget;
import Client.event.events.EventPacketSend;
import Client.module.Category;
import Client.module.Module;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import org.lwjgl.input.Keyboard;

import java.util.UUID;

public class FreeCam extends Module {
    private EntityOtherPlayerMP entity;
    public FreeCam() {
        super("FreeCam", Keyboard.KEY_Y, Category.Render);
    }

    @Override
    public void onEnable() {
        System.out.println("ModeManager : init");
        this.entity = new EntityOtherPlayerMP(mc.theWorld, new GameProfile(new UUID(250, 520), mc.thePlayer.getName()));
        entity.setPositionAndRotation(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch);
        mc.theWorld.addEntityToWorld(110, entity);
        super.onEnable();
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        e.setCancelled(true);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
        mc.theWorld.removeEntity(entity);
        super.onDisable();
    }
}
