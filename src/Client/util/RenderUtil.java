package Client.util;

import java.awt.*;

public class RenderUtil {
    public static int rainbow(int delay) {
        double rainbow = Math.ceil((System.currentTimeMillis() + delay) / 10.0);
        return Color.getHSBColor((float) (rainbow % 360.0 / 360.0), 0.5f, 1.0f).getRGB();
    }
}
