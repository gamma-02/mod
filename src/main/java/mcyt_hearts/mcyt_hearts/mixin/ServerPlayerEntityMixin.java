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
import net.minecraft.stat.Stat;
import net.minecraft.util.Rarity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;
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

    @Shadow private boolean filterText;
    public boolean tommy = HeartComponent.HEART_COMPONENT.get(this).getTommy();
    public boolean dream = HeartComponent.HEART_COMPONENT.get(this).getDream();
    public boolean wisp = HeartComponent.HEART_COMPONENT.get(this).getWisp();
    public boolean techno = HeartComponent.HEART_COMPONENT.get(this).getTechno();

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



            if(HeartComponent.HEART_COMPONENT.get(this).getGeorge() && this.lastHearts>this.getHealth()){
                DamageSource source = this.getRecentDamageSource();
                if(source.getAttacker() != null) {
                    this.heal((this.lastHearts - this.getHealth()) / 2);
                }

            }
            if(HeartComponent.HEART_COMPONENT.get(this).getMrbeast()){
                if(this.ticks == 1200){
                    this.dropItem(getRandomItem());
                    this.ticks = -1;
                }
                this.ticks++;
            }
            if (HeartComponent.HEART_COMPONENT.get(this).getKarl() && this.getHealth() < lastHearts)
            {
                world.getServer().getPlayerManager().getPlayer(this.getName().asString()).addStatusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 200));
            }
            if (HeartComponent.HEART_COMPONENT.get(this).getCraftee() && this.getHealth() < lastHearts)
            {
                TntEntity tnt = EntityType.TNT.create(world);
                this.setInvulnerable(true);
                Explosion ex = world.createExplosion(tnt, this.getX(), this.getY()+1, this.getZ(), 2f, Explosion.DestructionType.DESTROY);
                this.setInvulnerable(false);
            }







        if(this.techno){
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.STRENGTH, 3, 1, true,true, true));
        }



        if(this.tommy){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.3);
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.HASTE, 3,1,true,true, true));
        }

        if(this.dream){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(1d);
            if (this.getHealth() < this.getMaxHealth() && this.age % 20 == 0) {
                this.heal(1.0F);
            }
        }

        if(!this.tommy && !this.dream){
            this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED).setBaseValue(0.1);
        }

        if(this.wisp){

            this.addStatusEffect(new StatusEffectInstance(StatusEffects.WATER_BREATHING, 3,1,true,true, true));
            this.addStatusEffect(new StatusEffectInstance(StatusEffects.DOLPHINS_GRACE, 3, 1, true,true, true));
        }



        this.sendAbilitiesUpdate();












        lastHearts = this.getHealth();

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
