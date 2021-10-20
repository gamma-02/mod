package mcyt_hearts.mcyt_hearts.mixin;


import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


@Mixin(Entity.class)
public abstract class EntityMixin {

    @Shadow public abstract World getEntityWorld();

    @Shadow @Nullable public abstract MinecraftServer getServer();

    @Shadow public abstract EntityType<?> getType();

    @Inject(method = "isAttackable", at = @At("TAIL"), cancellable = true)
    public void attackMixin(CallbackInfoReturnable<Boolean> cir){
        if(this.getType().equals(EntityType.PLAYER)) {
            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++) {
                if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "aphmau_heart" && !(this.getEntityWorld().getRegistryKey().equals(DimensionType.THE_END_REGISTRY_KEY))) {
                    cir.setReturnValue(false);
                } else {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}
