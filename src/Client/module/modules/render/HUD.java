package Client.module.modules.render;

import Client.Client;
import Client.event.EventTarget;
import Client.event.events.EventRender2D;
import Client.module.Category;
import Client.module.Module;
import Client.util.RenderUtil;
import Client.values.Booleans;
import Client.values.Mode;
import Client.values.Numbers;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class HUD extends Module {
    private final Mode<String> Theme = new Mode<>("Themes", new String[]{"Rainbow", "Color"}, "Rainbow");
    public HUD(){
        super("HUD", Keyboard.KEY_NONE, Category.Render);
        this.addValues(this.Theme);
        this.setEnabled(true);
    }
//        mc.getTextureManager().bindTexture(new ResourceLocation("Client/wallpaper.jpeg"));
//        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, width, height, width, height);
    @EventTarget
    public void onRender(EventRender2D e) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) return;
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();
        if (this.Theme.isCurrentMode("Rainbow")) {
            mc.fontRendererObj.drawStringWithShadow("Grade Client", 5, 5, RenderUtil.rainbow(50));
        }else {
            mc.fontRendererObj.drawStringWithShadow("Grade Client", 5, 5, new Color(255,255,255).getRGB());
        }
        ArrayList<Module> modulesHasEnabled = new ArrayList<>();
        for (Module module : Client.instance.getModuleManager().modules) { if (!module.isEnabled()) continue;
            modulesHasEnabled.add(module);
        }
        modulesHasEnabled.sort((compare1, compare2) -> mc.fontRendererObj.getStringWidth(compare2.getName() + compare2.getSuffix()) - mc.fontRendererObj.getStringWidth(compare1.getName() + compare1.getSuffix()));
        int y = 1;
        for (Module module : modulesHasEnabled) {
            int moduleWidth = mc.fontRendererObj.getStringWidth(module.getName() + module.getSuffix());
            if (this.Theme.isCurrentMode("Rainbow")) { Gui.drawRect(width - moduleWidth - 4, y - 1, width - moduleWidth - 3, y - 1 + 9, RenderUtil.rainbow(50)); }
            else{ Gui.drawRect(width - moduleWidth - 4, y - 1, width - moduleWidth - 3, y - 1 + 9, new Color(255,255,255).getRGB());}
            Gui.drawRect(width - moduleWidth - 3, y - 1, width, y - 1 + 9, new Color(0, 0, 0, 50).getRGB());
            if (!module.getSuffix().equals(""))mc.fontRendererObj.drawString(module.getSuffix(), width - mc.fontRendererObj.getStringWidth(module.getSuffix()), y, new Color(200, 200, 200).getRGB());
            if(this.Theme.isCurrentMode("Rainbow")){ mc.fontRendererObj.drawString(module.getName(), width - moduleWidth - 1, y, RenderUtil.rainbow(50)); }
            else{ mc.fontRendererObj.drawString(module.getName(), width - moduleWidth - 1, y, new Color(255,255,255).getRGB()); }
            y += mc.fontRendererObj.FONT_HEIGHT;
        }
    }
}
