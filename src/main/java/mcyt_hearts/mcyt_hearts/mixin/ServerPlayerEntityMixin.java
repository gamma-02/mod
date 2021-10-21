package mcyt_hearts.mcyt_hearts.mixin;

import com.mojang.authlib.GameProfile;
import mcyt_hearts.mcyt_hearts.item_objects.Heart;
import mcyt_hearts.mcyt_hearts.items;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.item.ToolItem;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin extends PlayerEntity
{
    @Shadow public abstract void sendAbilitiesUpdate();

    @Shadow public abstract boolean isInvulnerableTo(DamageSource damageSource);

    public ServerPlayerEntityMixin(World world, BlockPos blockPos, float f, GameProfile gameProfile)
    {
        super(world, blockPos, f, gameProfile);
    }

    private float lastHearts;

    private boolean joined = false;







    @Inject(method = ("onSpawn()V"), at = @At("HEAD"))
    public void spawnMixin(CallbackInfo ci){
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(2);
        this.sendAbilitiesUpdate();
        System.out.println("Eeeeeeeerrrrgggggghh");
        this.setHealth(2f);
    }

    public int ticks;
    @Inject(method = ("tick"), at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci){

        if(!(HeartComponent.HEART_COMPONENT.get(this).getHeart(1).getPath().equals("null_heart"))) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((HeartComponent.HEART_COMPONENT.get(this).size() + 1) * 2);
        }

        for(int i = 0; i<HeartComponent.HEART_COMPONENT.get(this).size()+1;i++){
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "george_heart" ){
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).setBaseValue(10);

            }else{
                this.getAttributeInstance(EntityAttributes.GENERIC_ARMOR_TOUGHNESS).setBaseValue(0);

            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "dream_heart"){
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(1d);
                if (this.getHealth() < this.getMaxHealth() && this.age % 20 == 0) {
                    this.heal(1.0F);
                }
            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "tommy_heart"){
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).getValue()+0.1);
                this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 1,1,true,false));
            }else if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() != "dream_heart"){
                this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "preston_heart" ){
                this.isFireImmune();

            }
            if(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath() == "mrbeast_heart"){
                if(ticks == 1200){
                    this.dropItem(getRandomItem());
                }
                ticks++;
            }
            if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "karl_heart") && this.getHealth() < lastHearts)
            {
                world.getServer().getPlayerManager().getPlayer(this.getName().asString()).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200));
            }
            if (Objects.equals(HeartComponent.HEART_COMPONENT.get(this).getHeart(i).getPath(), "craftee_heart") && this.getHealth() < lastHearts)
            {
                TntEntity tnt = EntityType.TNT.create(world);
                this.setInvulnerable(true);
                Explosion ex = world.createExplosion(tnt, this.getX(), this.getY()+1, this.getZ(), 2f, Explosion.DestructionType.DESTROY);
                this.setInvulnerable(false);
            }








        }


        this.sendAbilitiesUpdate();












        lastHearts = this.getHealth();
        if(!HeartComponent.HEART_COMPONENT.get(this).hasJoined()){
            HeartComponent.HEART_COMPONENT.get(this).setJoined();
        }

    }



    public ItemConvertible getRandomItem(){
        boolean yes=true;
        while(yes){
            Item item = Registry.ITEM.getRandom(new Random());
            if(item.getRarity(item.getDefaultStack()) != Rarity.COMMON || item.equals(Items.DIAMOND) || item.equals(Items.NETHERITE_INGOT) || item.equals(Items.ENDER_PEARL) || item.equals(Items.ANCIENT_DEBRIS) || item.equals(Items.GOLD_INGOT) || item.equals(Items.GILDED_BLACKSTONE)){
                return item;
            }
        }
        return null;//makes the compiler happy

    }



}
