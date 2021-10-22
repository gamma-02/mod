package mcyt_hearts.mcyt_hearts.mixin;

import com.mojang.authlib.GameProfile;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

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







    @Inject(method = ("onSpawn()V"), at = @At("HEAD"))
    public void spawnMixin(CallbackInfo ci){
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(2);
        this.sendAbilitiesUpdate();
        this.setHealth(2f);
    }

    public int ticks;
    @Inject(method = ("tick"), at = @At("HEAD"))
    public void tickMixin(CallbackInfo ci){

        if (!(HeartComponent.HEART_COMPONENT.get(this).getHeart(1).getPath().equals("null_heart"))) {
            this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue((HeartComponent.HEART_COMPONENT.get(this).size() + 1) * 2);
        }



        if (HeartComponent.HEART_COMPONENT.get(this).getGeorge() && this.lastHearts>this.getHealth()){//george stuff
            DamageSource source = this.getRecentDamageSource();
            if(source.getAttacker() != null) {
                this.heal((this.lastHearts - this.getHealth()) / 2);
            }

        }
        if (HeartComponent.HEART_COMPONENT.get(this).getMrbeast()){//sees if the player has the mrBeast heart
            if(this.ticks >= 1200){//sees if the ticks is correct or somehow more than correct
                this.dropItem(getRandomItem());//drops random item
                this.ticks = -1;//sets ticks to -1, so that when they are increased it is 0
            }
            this.ticks++;//increments ticks
        }
        if (HeartComponent.HEART_COMPONENT.get(this).getKarl() && this.getHealth() < lastHearts)//sees if the player has been damaged and has the karl heart
        {
            world.getServer().getPlayerManager().getPlayer(this.getName().asString()).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200));//gives regen status effect for 10 seconds
        }
        if (HeartComponent.HEART_COMPONENT.get(this).getCraftee() && this.getHealth() < lastHearts)//sees if the player has been damaged and has the craftee heart
        {
            TntEntity tnt = EntityType.TNT.create(world);//creates a TntEntity
            this.setInvulnerable(true);//sets player invulnerable
            Explosion ex = world.createExplosion(tnt, this.getX(), this.getY()+1, this.getZ(), 2f, Explosion.DestructionType.DESTROY);//creates explosion
            this.setInvulnerable(false);//sets player not invulnerable
        }







        if (HeartComponent.HEART_COMPONENT.get(this).getTechno()){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 3, 1, true,false, false));//adds strength status effect
        }



        if (HeartComponent.HEART_COMPONENT.get(this).getTommy()){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);//triples walking speed
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 3,1,true,false, false));//gives haste 2
        }

        if (HeartComponent.HEART_COMPONENT.get(this).getDream()){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(1d);//increases walking speed by an order of magnitude
            if (this.getHealth() < this.getMaxHealth() && this.age % 15 == 0) {//increases heal speed
                this.heal(1.0F);
            }
        }

        if (!HeartComponent.HEART_COMPONENT.get(this).getTommy() && !HeartComponent.HEART_COMPONENT.get(this).getDream()){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1); // sets walking speed to normal
        }

        if (HeartComponent.HEART_COMPONENT.get(this).getWisp()){

            this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 3,1,true,false, false));//gives water breathing status effect
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 3, 1, true,false, false));//gives dolphin's grace status effect
        }



        this.sendAbilitiesUpdate();//sends abilites to the client

        lastHearts = this.getHealth();//sets the last health value of the player

    }



    public ItemConvertible getRandomItem(){//returns random item for MrBeast heart
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
