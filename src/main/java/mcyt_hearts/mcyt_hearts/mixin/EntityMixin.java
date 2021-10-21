package mcyt_hearts.mcyt_hearts.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin implements EntityLike
{

    @Shadow public abstract boolean isInsideWaterOrBubbleColumn();

    @Shadow public abstract EntityType<?> getType();

    @Overwrite
    public boolean isFireImmune(){
        if(this.getType() == EntityType.PLAYER){
            for(int i = 0; i<HeartComponent.HEART_COMPONENT.get(this).size()+1;i++){
                if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "preston_heart"){
                    return true;
                }
            }
        }
        return this.getType().isFireImmune();
    }


}
