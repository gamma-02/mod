package mcyt_hearts.mcyt_hearts.mixin;

import com.mojang.authlib.GameProfile;
import mcyt_hearts.mcyt_hearts.item_objects.Heart;
import mcyt_hearts.mcyt_hearts.items;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{
    @Shadow public abstract void sendAbilitiesUpdate();

    public ServerPlayerEntityMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile)
    {
        super(world, blockPos, f, gameProfile);
    }

    private int lastHearts;

    private boolean joined = false;



    @Inject(method = ("onSpawn()V"), at = @At("HEAD"))
    public void spawnMixin(CallbackInfo ci){
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(2);
        this.sendAbilitiesUpdate();
        System.out.println("Eeeeeeeerrrrgggggghh");
        this.setHealth(2f);
    }


    @Inject(method = ("tick"), at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci){


        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((HeartComponent.HEART_COMPONENT.get(this).size()+1)*2);

        for(int i = 0; i<HeartComponent.HEART_COMPONENT.get(this).size();i++){
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "george_heart" ){
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).setBaseValue(5);

            }else{
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0);

            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "dream_heart"){
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(1.5d);
                this.timeUntilRegen--;
                this.timeUntilRegen--;
                if(this.jumping){
                    this.addVelocity(0, 0.5, 0);
                    this.setJumping(false);
                }
            }else{

            }
        }


        this.sendAbilitiesUpdate();












        lastHearts = HeartComponent.HEART_COMPONENT.get(this).size();
        if(!HeartComponent.HEART_COMPONENT.get(this).hasJoined()){
            HeartComponent.HEART_COMPONENT.get(this).setJoined();
        }

    }



}
