package GuiFile;

import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import Client.*;

import java.io.IOException;

public class MainMenuGui extends GuiScreen {
    @Override
    public void initGui() {
        int j = this.height / 4 + 48;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, j + 48 , 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, this.width / 2 + 2, j + 48, 98, 20, I18n.format("menu.quit", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, j, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, j + 24, I18n.format("menu.multiplayer", new Object[0])));

    }
    private static final long start = System.currentTimeMillis();

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
//        drawBackground(0);
        GlStateManager.disableCull();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.color(1f, 1f, 1f, 1f);
        GLSL glsl = new GLSL("/shaders/noise");
        GL20.glUseProgram(glsl.program);
        GL20.glUniform1f(GL20.glGetUniformLocation(glsl.program, "time"), (System.currentTimeMillis() - start) / 512f);
        GL20.glUniform2f(GL20.glGetUniformLocation(glsl.program, "resolution"), width , height);
        // Draw
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2i(-1,-1);
        GL11.glVertex2i(-1,1);
        GL11.glVertex2i(1,1);
        GL11.glVertex2i(1,-1);
        GL11.glEnd();

        GL20.glUseProgram(0);
        GlStateManager.popMatrix();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }


    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    }
}
