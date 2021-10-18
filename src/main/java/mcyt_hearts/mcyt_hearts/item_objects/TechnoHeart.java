package mcyt_hearts.mcyt_hearts.item_objects;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TechnoHeart extends Item
{
    public TechnoHeart(Settings settings)
    {
        super(settings);
    }

    @Override public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {

        return super.use(world, user, hand);
    }
}
