package mcyt_hearts.mcyt_hearts.mixin;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.UUID;

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

    @Inject(method = "canTarget(Lnet/minecraft/entity/LivingEntity;)Z", at = @At("HEAD"))
    public void targetMixin(LivingEntity target, CallbackInfoReturnable<Boolean> cir){
        if(target instanceof ServerPlayerEntity){
            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
            {
                if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "aphmau_heart")
                {
                    cir.setReturnValue(false);
                }
            }
        }
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
    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void damageMixin(DamageSource source, float amount, CallbackInfo ci){
        if(Objects.requireNonNull(this.getType()).equals(EntityType.PLAYER))
        for(int i = 0; i<HeartComponent.HEART_COMPONENT.get(this).size()+1;i++) {
            if (HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "karl_heart") {
                world.getServer().getPlayerManager().getPlayer(this.getName().asString()).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10));
            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "craftee_heart"){
                TntEntity tnt = EntityType.TNT.create(world);
                Explosion ex = world.createExplosion(tnt, this.getX(), this.getY(), this.getZ(), 4f, Explosion.DestructionType.DESTROY);
                this.isInvulnerableTo(DamageSource.explosion(ex));
            }
            if(source.getAttacker() != null)
                if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "preston_heart" && (source.getAttacker().getType() == EntityType.BLAZE || source.getAttacker().getType() == EntityType.MAGMA_CUBE || source.getAttacker().getType().equals(EntityType.GHAST)) || source.getAttacker().getType().equals(EntityType.FIREBALL) || source.getAttacker().getType().equals(EntityType.SMALL_FIREBALL))
                {
                    this.isInvulnerableTo(source);
                }



        }
    }

}
