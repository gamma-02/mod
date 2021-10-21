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
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
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
     * @author
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
    @Inject(method = "applyDamage", at = @At("HEAD"))
    public void damageMixin(DamageSource source, float amount, CallbackInfo ci){
        if(this.getType() != null)
        {
            if(this.getType().equals(EntityType.PLAYER))
            {

                for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(this).size() + 1; i++)
                {
                    if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "karl_heart"))
                    {
                        world.getServer().getPlayerManager().getPlayer(this.getName().asString()).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 10));
                    }
                    if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "craftee_heart"))
                    {
                        TntEntity tnt = EntityType.TNT.create(world);
                        Explosion ex = world.createExplosion(tnt, this.getX(), this.getY(), this.getZ(), 4f, Explosion.DestructionType.DESTROY);
                        this.isInvulnerableTo(DamageSource.explosion(ex));
                    }
                    if (source.getAttacker() != null)
                        if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(),
                                "preston_heart") && (source.getAttacker()
                                .getType() == EntityType.BLAZE || source.getAttacker().getType() == EntityType.MAGMA_CUBE || source.getAttacker().getType()
                                .equals(EntityType.GHAST)) || source.getAttacker().getType().equals(EntityType.FIREBALL) || source.getAttacker().getType()
                                .equals(EntityType.SMALL_FIREBALL))
                        {
                            this.isInvulnerableTo(source);
                        }

                }
            }
        }
    }

}
