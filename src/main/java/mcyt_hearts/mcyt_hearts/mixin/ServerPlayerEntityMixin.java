package mcyt_hearts.mcyt_hearts.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{
    public ServerPlayerEntityMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile)
    {
        super(world, blockPos, f, gameProfile);
    }

    private int lastHearts;


    @Inject(method = ("tick"), at = @At("HEAD"))
    public void tickMixin(){
        double health = this.getAttributeValue(EntityAttributes.GENERIC_MAX_HEALTH);

        if(HeartComponent.HEART_COMPONENT.get(this).size()>lastHearts){
            double eeee = health++;
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(eeee);
        }




        lastHearts = HeartComponent.HEART_COMPONENT.get(this).size();

    }



}
