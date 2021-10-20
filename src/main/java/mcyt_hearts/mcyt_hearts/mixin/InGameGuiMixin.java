package mcyt_hearts.mcyt_hearts.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import static net.minecraft.client.gui.DrawableHelper.drawTexture;

@Mixin(InGameHud.class)
public abstract class InGameGuiMixin extends DrawableHelper {
    @Shadow private int heldItemTooltipFade;

    @Shadow public abstract PlayerListHud getPlayerListHud();

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    private MatrixStack matrices;
    private InGameHud.HeartType type;
    private int x;
    private int y;
    private int v;
    private boolean blinking;
    private boolean halfHeart;
    @Unique
    private int heartX,heartY,heartIndex;


    @Inject(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void storeLocals(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType type, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        this.heartX = p;
        this.heartY = q;
        this.heartIndex = m;
    }
    /**
     * @author gamma_02
     */

    @Environment(EnvType.CLIENT)
    @Overwrite
     public final void drawHeart(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart) {
        this.matrices = matrices;
        this.type = type;
        this.x = x;
        this.y = y;
        this.v = v;
        this.blinking = blinking;
        this.halfHeart = halfHeart;
        if(heartIndex == 0 || type == InGameHud.HeartType.CONTAINER){
            this.drawTexture(matrices, x, y, type.getU(halfHeart, blinking), v, 9, 9);
        }else {
            if(halfHeart){
                this.drawTexture(matrices, x, y, type.getU(true, blinking), v,9, 9);


                RenderSystem.setShaderTexture(0, new Identifier("mod", "textures/gui/"+HeartComponent.HEART_COMPONENT.get(this.getCameraPlayer()).getHeart(heartIndex).getPath()+".png"));
                this.drawTexture(matrices, x, y, 9,0, 9, 9, 18, 9);


            }else{
                RenderSystem.setShaderTexture(0, new Identifier("mod", "textures/gui/"+HeartComponent.HEART_COMPONENT.get(this.getCameraPlayer()).getHeart(heartIndex).getPath()+".png"));
                this.drawTexture(matrices, x, y, 0,0, 9, 9, 18, 9);

            }

        }

        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);
    }
}
