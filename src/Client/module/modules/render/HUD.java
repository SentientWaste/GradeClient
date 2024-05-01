package Client.module.modules.render;

import Client.Client;
import Client.event.EventTarget;
import Client.event.events.EventRender2D;
import Client.module.Category;
import Client.module.Module;
import Client.module.ModuleManager;
import Client.util.Render.RenderUtil;
import Client.values.Mode;
import com.mojang.realmsclient.dto.PlayerInfo;
import de.Hero.clickgui.util.FontUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.server.management.PlayerManager;
import org.jetbrains.java.decompiler.code.optinstructions.NEW;
import org.lwjgl.input.Keyboard;
import de.Hero.clickgui.Panel;

import java.awt.*;
import java.util.ArrayList;

public class HUD extends Module {
    public Panel panel;
    public final Mode<String> Theme = new Mode<>("Themes", new String[]{"Rainbow", "Custom","Cycle"}, "Rainbow");
    public HUD(){
        super("HUD", Keyboard.KEY_NONE, Category.Render);
        this.addValues(this.Theme);
        this.setEnabled(true);
    }

    @EventTarget
    public void onRender(EventRender2D e) {
        if (mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat)) return;
        int width = new ScaledResolution(mc).getScaledWidth();
        int height = new ScaledResolution(mc).getScaledHeight();

        ArrayList<Module> modulesHasEnabled = new ArrayList<>();
        for (Module module : Client.instance.getModuleManager().modules) { if (!module.isEnabled()) continue;
            modulesHasEnabled.add(module);
        }
        modulesHasEnabled.sort((compare1, compare2) -> mc.fontRendererObj.getStringWidth(compare2.getName() + compare2.getSuffix()) - mc.fontRendererObj.getStringWidth(compare1.getName() + compare1.getSuffix()));
        final int[] y = {2};
        final int[] counter = {1};
        for (Module module : modulesHasEnabled) {

            int moduleWidth = mc.fontRendererObj.getStringWidth(module.getName() + module.getSuffix());

            if (!module.getSuffix().equals(""))mc.fontRendererObj.drawString(module.getSuffix(), width - mc.fontRendererObj.getStringWidth(module.getSuffix()), y[0], new Color(200, 200, 200).getRGB());

            if(this.Theme.isCurrentMode("Rainbow")){
                mc.fontRendererObj.drawString(module.getName(), width - moduleWidth - 1, y[0], RenderUtil.rainbow(counter[0] * 300));
                counter[0]++;
                y[0] += mc.fontRendererObj.FONT_HEIGHT;
            }
            if(this.Theme.isCurrentMode("Custom")){
                mc.fontRendererObj.drawString(module.getName(), width - moduleWidth - 1, y[0], new Color(72, 0, 0).getRGB());
                y[0] += mc.fontRendererObj.FONT_HEIGHT;
            }
            if(this.Theme.isCurrentMode("Cycle")){
                mc.fontRendererObj.drawString(module.getName(), width - moduleWidth - 1, y[0], RenderUtil.rainbow(50));
                y[0] += mc.fontRendererObj.FONT_HEIGHT;
            }
        }
    }
}
