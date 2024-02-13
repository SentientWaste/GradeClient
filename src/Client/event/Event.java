package Client.event;

import net.minecraft.client.Minecraft;

public abstract class Event {
    private boolean cancelled;
    private byte type;
    public static Minecraft mc = Minecraft.getMinecraft();

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }
}
