package mcyt_hearts.mcyt_hearts.item_objects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;

public class TechnoHeart extends Heart
{
    public TechnoHeart(Settings settings)
    {
        super(settings);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.EAT;
    }
    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 20;
    }
    @Override
    public void onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if(user instanceof PlayerEntity){
            HeartComponent.HEART_COMPONENT.get(user).addHeart(this);
            this.use(world, (PlayerEntity) user, user.getActiveHand());
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        return TypedActionResult.consume(user.getActiveItem());
    }
}
