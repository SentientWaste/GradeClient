package Client.module.modules.render;

import Client.Client;
import Client.event.EventTarget;
import Client.event.events.EventRender2D;
import Client.module.Category;
import Client.module.Module;
import Client.util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;

public class DisplayTarget extends Module {

    public DisplayTarget() {
        super("DisplayTraget", Keyboard.KEY_NONE, Category.Render);
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        Entity entityHit = Minecraft.getMinecraft().objectMouseOver.entityHit;
        if (entityHit instanceof EntityLiving){
            EntityLivingBase entity = (EntityLivingBase) entityHit;
            RenderUtil.drawRoundedRect(100,100,200,140,5,RenderUtil.rainbow(50));
            GuiInventory.drawEntityOnScreen(100,100,30,0,0,entity);
        }
    }
}
