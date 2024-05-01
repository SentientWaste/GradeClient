package GuiFile.ClickGui.util.input;

public class MouseUtil {
    public static Boolean isMouseOver(int mouseX,int mouseY , float x , float y ,float width, float height){
        return (mouseX >= x && mouseX <= x + width) && (mouseY >= y && mouseY <= y+height);
    }
}
