package mcyt_hearts.mcyt_hearts.mixin;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.ActiveTargetGoal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(ActiveTargetGoal.class)
public abstract class HostileEntityMixin extends TrackTargetGoal
{

    @Shadow protected LivingEntity targetEntity;

    public HostileEntityMixin(MobEntity mobEntity, boolean bl, boolean bl2)
    {
        super(mobEntity, bl, bl2);
    }

    /**
     * @author gamma_02
     */
    @Overwrite
    public void start(){
        if(this.targetEntity instanceof ServerPlayerEntity){
            for (int i = 0; i < HeartComponent.HEART_COMPONENT.get(Objects.requireNonNull(this.targetEntity)).size() + 1; i++)
            {
                if(this.targetEntity != null)
                {

                    if (HeartComponent.HEART_COMPONENT.get(this.targetEntity).getHeart(i).getPath() == "aphmau_heart")
                    {
                        this.mob.setTarget(null);
                        super.start();
                    }else{
                        this.mob.setTarget(targetEntity);
                        super.start();
                    }
                }
            }
        }else{
            this.mob.setTarget(targetEntity);
            super.start();
        }



    }
}
