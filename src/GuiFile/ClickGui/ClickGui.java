package GuiFile.ClickGui;

import Client.Client;
import Client.module.Category;
import Client.module.Module;
import Client.util.Render.RenderUtil;
import GuiFile.ClickGui.util.input.MouseUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClickGui extends GuiScreen {
    private float x,y,lastX,lastY,width,height;
    private boolean dragging;
    private Category selectedCategory;

    public ClickGui(){
        this.width=450;
        this.height=250;
        this.x = 50;
        this.y = 50;
        this.selectedCategory = Category.Combat;

    }

    @Override
    public void drawScreen(int mouseX,int mouseY , float partialTicks){
        super.drawScreen(mouseX,mouseY,partialTicks);

        this.smoothDrag(mouseX,mouseY);
        int i=0;
        RenderUtil.drawRoundedRect((int)x, (int)y, (int)x + (int)width, (int)y + (int)height , 3, new Color(28, 28, 28).getRGB());
        //侧边栏
        RenderUtil.drawRoundedRect((int)x, (int)y, (int)x + 100, (int)y + (int)height , 3, new Color(19, 19, 19).getRGB());
        //拖动栏
        RenderUtil.drawRoundedRect((int)x, (int)y, (int)x + (int)width, (int)y+20 , 3, new Color(19, 19, 19).getRGB());
        //标题
        Gui.drawCenteredString(mc.fontRendererObj,Client.NAME + " " + Client.VERSION,x+50,y+6,-1);
        i = 0;
        for(Category category : Category.values()){
            //选择模块
            Gui.drawRect(x, y + 20 + i, x + 100, y+20+i+34,isSelectedCategory(category) ? new Color(86, 20, 20).getRGB() : new Color(19, 19, 19).getRGB());
            Gui.drawCenteredString(mc.fontRendererObj,category.name(),x+50,y+20+mc.fontRendererObj.FONT_HEIGHT/2+i,-1);
            i+=34;
        }
    }

    @Override
    protected void mouseClicked(int mouseX,int mouseY,int mouseButton) throws IOException{
        super.mouseClicked(mouseX,mouseY,mouseButton);

        if(isOverTop(mouseX,mouseY)){
            this.dragging = true;
            this.lastX = mouseX - x;
            this.lastY = mouseY - y;

        }

        int i = 0;
        for(Category category : Category.values()){
            if(MouseUtil.isMouseOver(mouseX,mouseY,x,y+20+i,100,34)){
                this.selectedCategory = category;
            }
            i+=34;
        }
    }

    private void smoothDrag(int mouseX,int mouseY){
        if(dragging){
            this.x=mouseX-lastX;
            this.y=mouseY-lastY;
        }
    }

    @Override
    protected void mouseReleased(int mouseX , int mouseY ,int state){
        super.mouseReleased(mouseX,mouseY,state);
        this.dragging = false;
    }

    private boolean isOverTop(int mouseX,int mouseY){
        return MouseUtil.isMouseOver(mouseX,mouseY,x,y,width,20);
    }


    private boolean isSelectedCategory(Category category){
        return selectedCategory.equals(category);
    }
}
