package mcyt_hearts.mcyt_hearts.mixin;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTracker;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
    @Shadow protected abstract boolean tryUseTotem(DamageSource source);

    @Shadow protected abstract float applyArmorToDamage(DamageSource source, float amount);

    @Shadow protected abstract float applyEnchantmentsToDamage(DamageSource source, float amount);

    @Shadow public abstract int getArmor();

    @Shadow public abstract float getAbsorptionAmount();

    @Shadow public abstract void setAbsorptionAmount(float amount);

    @Shadow public abstract float getHealth();

    @Shadow public abstract void setHealth(float health);

    @Shadow public abstract DamageTracker getDamageTracker();

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "jump()V", at = @At("TAIL"))
    public void jumpMixin(CallbackInfo ci){
        if(this.getType().equals(EntityType.PLAYER)) {
            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++) {
                if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath().equals("dream_heart")) {
                    this.setVelocity(getVelocity().add(0, 1F, 0));
                }else{
                    this.setVelocity(getVelocity().add(0, 0, 0));
                }

            }
        }

    }

    /**
     * @author gamma_02
     * @reason I can't modify the return value any other way
     */
    @Overwrite
    public boolean canTarget(LivingEntity target) {
        if(target instanceof PlayerEntity){

            for (int i = 0;i < HeartComponent.HEART_COMPONENT.get(target).size() + 1;i++)
            {
                if (Objects.equals(HeartComponent.HEART_COMPONENT.get(target).getHeart(i).getPath(), "aphmau_heart"))
                {
                    return false;
                }
                if (Objects.equals(HeartComponent.HEART_COMPONENT.get(target).getHeart(i).getPath(), "preston_heart") && (this.getType() == EntityType.BLAZE || this.getType() == EntityType.MAGMA_CUBE || this.getType() == EntityType.GHAST)){
                    return false;
                }


            }
            return this.world.getDifficulty() != Difficulty.PEACEFUL && target.canTakeDamage();
        }
        return true;
    }

//    @Inject(method = "tick", at = @At("HEAD"))
//    public void tickMixin(CallbackInfo ci){
//        if(!(this.getAttacking() == (null)))
//        {
//            if (this.getAttacking().getType().equals(EntityType.PLAYER))
//            {
//                for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
//                {
//                    if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "aphmau_heart")
//                    {
//                        setVelocity(0, 1, 0);
//                    }
//
//                }
//            }
//        }




    @Inject(method = "fall", at = @At("HEAD"))
    public void fallMixin(CallbackInfo ci){
        if(Objects.requireNonNull(this.getType()).equals(EntityType.PLAYER))
        {

            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
            {
                if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "george_heart") || Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "dream_heart"))
                {
                    this.fallDistance = this.fallDistance / 2;
                }
            }
        }

    }
    /**
     * @author gamma_02
     * @reason no other way that works /shrug
     */
    

}
