package mcyt_hearts.mcyt_hearts.item_objects;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class DreamHeart extends Heart
{
    public DreamHeart(Settings settings)
    {
        super(settings);
    }


    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {

        HeartComponent.HEART_COMPONENT.get(user).addHeart(this);
        user.getInventory().removeOne(this.getDefaultStack());
        return super.use(world, user, hand);
    }
}
