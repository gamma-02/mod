package mcyt_hearts.mcyt_hearts.item_objects;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class KarlHeart extends Heart
{
    public KarlHeart(Settings settings)
    {
        super(settings);
    }

    @Override
    public boolean isUsedOnRelease(ItemStack stack) {
        return true;
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
        if(remainingUseTicks<=10)
        {
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
