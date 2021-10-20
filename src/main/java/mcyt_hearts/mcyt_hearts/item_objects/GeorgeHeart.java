package mcyt_hearts.mcyt_hearts.item_objects;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class GeorgeHeart extends Heart
{
    public GeorgeHeart(Settings settings)
    {
        super(settings);
    }
    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        HeartComponent.HEART_COMPONENT.get(user).addHeart(new Identifier("mod","george_heart"));
        System.out.println("eeeeeee");
        user.getActiveItem().decrement(1);
        return TypedActionResult.pass(user.getActiveItem());
    }
}
