package mcyt_hearts.mcyt_hearts.mixin;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    @Shadow LivingEntity attacker;

    @Shadow private LivingEntity attacking;

    @Shadow @Nullable public abstract LivingEntity getAttacking();

    @Environment(EnvType.SERVER)
    @Inject(method = "jump()V", at = @At("HEAD"))
    public void jumpMixin(CallbackInfo ci){
        if(this.getType().equals(EntityType.PLAYER)) {
            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++) {
                if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "dream_heart") {
                    setVelocity(0, 1, 0);
                }

            }
        }

    }

    @Inject(method = "tick", at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci){
        if(!(this.getAttacking() == (null)))
        {
            if (this.getAttacking().getType().equals(EntityType.PLAYER))
            {
                for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
                {
                    if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "aphmau_heart")
                    {
                        setVelocity(0, 1, 0);
                    }

                }
            }
        }


    }

    @Inject(method = "fall", at = @At("HEAD"))
    public void fallMixin(CallbackInfo ci){
        if(!(this.getAttacking() == (null))){
            if(this.getType().equals(EntityType.PLAYER))
            {

                for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
                {
                    if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "george_heart")
                    {
                        this.fallDistance = this.fallDistance / 2;
                    }
                }
            }
        }
    }
}
