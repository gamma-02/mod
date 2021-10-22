package mcyt_hearts.mcyt_hearts.mixin;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    protected LivingEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "jump()V", at = @At("TAIL"))
    public void jumpMixin(CallbackInfo ci){
        if (this.getType().equals(EntityType.PLAYER)) {
            if (HeartComponent.HEART_COMPONENT.get(this).getDream()) {
                this.setVelocity(getVelocity().add(0, 0.9F, 0));
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
                if (HeartComponent.HEART_COMPONENT.get(target).getAphmau() && !(target.getEntityWorld().getDimension().equals(DimensionType.THE_END)))
                {
                    return false;
                }
                if (HeartComponent.HEART_COMPONENT.get(target).getPreston() && (this.getType() == EntityType.BLAZE || this.getType() == EntityType.MAGMA_CUBE || this.getType() == EntityType.GHAST)){
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

}
