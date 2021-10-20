package mcyt_hearts.mcyt_hearts.mixin;


import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.data.client.model.Texture;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import net.minecraft.client.gui.DrawableHelper.*;
import org.spongepowered.asm.mixin.injection.Inject;

import static net.minecraft.client.gui.DrawableHelper.drawTexture;

@Mixin(InGameHud.class)
public class InGameGuiMixin extends DrawableHelper {
    private MatrixStack matrices;
    private InGameHud.HeartType type;
    private int x;
    private int y;
    private int v;
    private boolean blinking;
    private boolean halfHeart;

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
        RenderSystem.setShaderTexture(0, new Identifier("mod","items/aphmauheart.png"));
        this.drawTexture(matrices, x, y, type.getU(halfHeart, blinking), v, 9, 9);
    }
}
