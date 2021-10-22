package mcyt_hearts.mcyt_hearts.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class InGameGuiMixin extends DrawableHelper {

    @Shadow protected abstract PlayerEntity getCameraPlayer();

    @Unique
    private int heartIndex;


    @Inject(method = "renderHealthBar", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;drawHeart(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/gui/hud/InGameHud$HeartType;IIIZZ)V", ordinal = 0), locals = LocalCapture.CAPTURE_FAILHARD)
    private void storeLocal(MatrixStack matrices, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci, InGameHud.HeartType type, int i, int j, int k, int l, int m, int n, int o, int p, int q) {
        this.heartIndex = m;
    }
    /**
     * @author gamma_02
     * @reason only way lmao
     */

    @Environment(EnvType.CLIENT)
    @Overwrite
     public final void drawHeart(MatrixStack matrices, InGameHud.HeartType type, int x, int y, int v, boolean blinking, boolean halfHeart) {

        if (heartIndex == 0 || type != InGameHud.HeartType.NORMAL){//draws first heart and empty containers, as well as non-normal hearts
            this.drawTexture(matrices, x, y, type.getU(halfHeart, blinking), v, 9, 9);
        }else {
            if(halfHeart){//draws my custom half hearts
                this.drawTexture(matrices, x, y, type.getU(true, blinking), v,9, 9);//idk why this is still here? doesnt work without it tho :kek:


                RenderSystem.setShaderTexture(0, new Identifier("mod", "textures/gui/"+HeartComponent.HEART_COMPONENT.get(this.getCameraPlayer()).getHeart(heartIndex).getPath()+".png"));//sets correct texture
                this.drawTexture(matrices, x, y, 9,0, 9, 9, 18, 9);//draws heart


            }else{//draws custom full hearts
                RenderSystem.setShaderTexture(0, new Identifier("mod", "textures/gui/"+HeartComponent.HEART_COMPONENT.get(this.getCameraPlayer()).getHeart(heartIndex).getPath()+".png"));//sets correct texture
                this.drawTexture(matrices, x, y, 0,0, 9, 9, 18, 9);//draws heart

            }

        }

        RenderSystem.setShaderTexture(0, DrawableHelper.GUI_ICONS_TEXTURE);//sets texture back to the HUD icons atlas
    }
}
