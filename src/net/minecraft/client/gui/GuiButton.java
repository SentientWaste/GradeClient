package net.minecraft.client.gui;

import Client.util.Render.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.util.ResourceLocation;


import java.awt.*;

public class GuiButton extends Gui
{
    protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
    public String i;

    /** Button width in pixels */
    protected int width;
    private int animation;

    /** Button height in pixels */
    protected int height;

    /** The x position of this control. */
    public int xPosition;

    /** The y position of this control. */
    public int yPosition;

    /** The string displayed on this control. */
    public String displayString;
    public int id;

    /** True if this control is enabled, false to disable. */
    public boolean enabled;

    /** Hides the button completely if false. */
    public boolean visible;
    protected boolean hovered;

    /**
     * The x position of this control.
     */
    public int x;

    /**
     * The y position of this control.
     */
    public int y;

    public GuiButton(int buttonId, int x, int y, String buttonText) {
        this(buttonId, x, y, 200, 20, buttonText);
    }

    public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
        this.enabled = true;
        this.visible = true;
        this.id = buttonId;
        this.x = x;
        this.y = y;
        this.xPosition = x;
        this.yPosition = y;
        this.width = widthIn;
        this.height = heightIn;
        this.displayString = buttonText;
        animation = 236;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    protected int getHoverState(boolean mouseOver) {
        int i = 1;

        if (!this.enabled) {
            i = 0;
        } else if (mouseOver) {
            i = 2;
        }

        return i;
    }

    /**
     * Draws this button to the screen.
     */
    private static final long start = System.currentTimeMillis();
    public void drawButton(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible) {
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            this.mouseDragged(mc, mouseX, mouseY);
            Color color;
            if (!this.enabled) {
            } else if (i == 2) {
                animation = animation + Integer.compare(255, animation) * 5;
                animation = Math.min(255, animation);
            } else {
                animation = animation + Integer.compare(236, animation) * 5;
                animation = Math.max(236, animation);
            }
            int alpha = (int) (102 + (animation - 236) * 2.842105263157895);
            if (i != 0) {
                RenderUtil.drawRoundedRect(this.x, this.y + 1, this.x + this.width, this.y + this.height - 1, 3, new Color(102, 102, 102, alpha).getRGB());
            } else {
                RenderUtil.drawRoundedRect(this.x, this.y + 1, this.x + this.width, this.y + this.height - 1, 3, new Color(40, 40, 40, 156).getRGB());
            }
        }
        FontRenderer fontrenderer = mc.fontRendererObj;
        int j = new Color(255, 255, 255, 255).getRGB();;
        this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
//
//Button绘制
//        if(hovered){
//            Gui.drawRect(this.xPosition + 1.5, this.yPosition, this.xPosition, this.yPosition + height, RenderUtil.rainbow(50));
//            Gui.drawRect(this.xPosition + 2.5, this.yPosition, this.xPosition + width, this.yPosition + height,new Color(0, 0, 0, 250).getRGB());
//        }
//        else{
//            Gui.drawRect(this.xPosition + 1.5, this.yPosition, this.xPosition +width, this.yPosition + height, new Color(0, 0, 0, 192).getRGB());
//            //Gui.drawRect(this.xPosition + 1.5, this.yPosition, this.xPosition, this.yPosition + height, RenderUtil.rainbow(50));
//            // Gui.drawRect(this.xPosition + 2.5, this.yPosition, this.xPosition + width, this.yPosition + height, new Color(124, 32, 178, 178).getRGB());
//        }
//        this.mouseDragged(mc, mouseX, mouseY);
//
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
    }

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY)
    {
    }

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        return this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
    }

    /**
     * Whether the mouse cursor is currently over the button.
     */
    public boolean isMouseOver()
    {
        return this.hovered;
    }

    public void drawButtonForegroundLayer(int mouseX, int mouseY)
    {
    }

    public void playPressSound(SoundHandler soundHandlerIn)
    {
        soundHandlerIn.playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public void setWidth(int width)
    {
        this.width = width;
    }
}
