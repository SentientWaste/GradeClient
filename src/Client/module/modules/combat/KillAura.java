package Client.module.modules.combat;

import Client.Client;
import Client.event.EventTarget;
import Client.event.events.EventPostUpdate;
import Client.event.events.EventPreUpdate;
import Client.module.Category;
import Client.module.Module;
import Client.values.Booleans;
import Client.values.Mode;
import Client.values.Numbers;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.MathHelper;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KillAura extends Module {
    private List<Entity> targets = null;
    private Entity target = null;
    private long cpsTicker;
    private final Numbers<Double> range = new Numbers<>("Range", 3.0, 8.0, 0.1, 4.2);
    private final Booleans<Boolean> autoBlock = new Booleans<>("AutoBlock", false);
    private final Mode<String> priority = new Mode<>("Priority", new String[]{"Distance", "Health"}, "Distance");
    public KillAura() {
        super("KillAura", Keyboard.KEY_R, Category.Combat);
        this.addValues(this.priority, this.range, this.autoBlock);
    }

    @Override
    public void onEnable() {
        //mc.thePlayer.addChatMessage(new ChatComponentText("\247cModule: KillAura"));
        this.cpsTicker = 0;
        this.target = null;
        super.onEnable();
    }

    @EventTarget
    public void onPre(EventPreUpdate e) {//Pre获取目标
        if (this.target instanceof EntityPlayer || this.target instanceof EntityAnimal || this.target instanceof EntityMob || this.target instanceof EntityVillager) {
            this.target = null;
        }
        this.targets = mc.theWorld.loadedEntityList.stream().filter(entity -> mc.thePlayer.getDistanceToEntity(entity) <= this.range.getValue() && this.isTarget(entity)).collect(Collectors.toList());//4.2为距离
        if (!this.targets.isEmpty()) {
            this.priority();
            this.target = this.targets.get(0);
        }
        if (this.target != null) {
            double xDiff = this.target.posX - mc.thePlayer.posX;
            double zDiff = this.target.posZ - mc.thePlayer.posZ;
            EntityLivingBase entityLivingBase = (EntityLivingBase) this.target;
            double yDiff = entityLivingBase.posX + entityLivingBase.getEyeHeight() - mc.thePlayer.posY - mc.thePlayer.getEyeHeight();
            e.setYaw((float) (-Math.atan2(yDiff - 0.2, MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff)) * 180.0 / Math.PI));
            e.setPitch((float) (Math.atan2(zDiff, xDiff) * 180.0 / Math.PI) - 90.0f);
        }
        this.stopBlocking();
    }

    private void priority() {
        if (this.priority.isCurrentMode("Distance")) {
            this.targets.sort(Comparator.comparingDouble(e -> mc.thePlayer.getDistanceToEntity(e)));
        } else {
            this.targets.sort(Comparator.comparingDouble(e2-> ((EntityLiving) e2).getHealth()));
        }
    }

    private void stopBlocking() {
        KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), false);
        mc.playerController.onStoppedUsingItem(mc.thePlayer);
    }

    @EventTarget
    public void onPost(EventPostUpdate e) {//Post攻击
        if (this.target != null && this.shouldAttack()) {
            Criticals criticals = (Criticals) Client.instance.getModuleManager().getModuleByName("Criticals");
            if (criticals.isEnabled()) {
                criticals.packetCriticals();
            }
            mc.thePlayer.swingItem();
            mc.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
            this.cpsTicker = System.currentTimeMillis();
        }
        if (this.target != null && this.autoBlock.getValue()) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindUseItem.getKeyCode(), true);
            mc.playerController.sendUseItem(mc.thePlayer, mc.theWorld, mc.thePlayer.getHeldItem());
        }
    }

    private boolean shouldAttack() {
        return System.currentTimeMillis() - this.cpsTicker >= 1000 / 10.0;//10.0为cps
    }

    private boolean isTarget(Entity target) {
        return target != mc.thePlayer && (target instanceof EntityPlayer || target instanceof EntityAnimal || target instanceof EntityMob || target instanceof EntityVillager);
    }

    @Override
    public void onDisable() {
        if (this.targets != null) this.targets.clear();
        this.target = null;
        super.onDisable();
    }
}
