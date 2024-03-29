package mcyt_hearts.mcyt_hearts.item_objects;

import mcyt_hearts.mcyt_hearts.components.player.hearts.HeartComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;

public class AphmauHeart extends Heart
{
    public AphmauHeart(Settings settings)
    {
        super(settings);
    }



    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if(HeartComponent.HEART_COMPONENT.get(user).size()<11)
        {
            HeartComponent.HEART_COMPONENT.get(user).setAphmau(true);
            HeartComponent.HEART_COMPONENT.get(user).addHeart(new Identifier("mod", "aphmau_heart"));
            System.out.println("eeeeeee");
            user.getActiveItem().decrement(1);
            return TypedActionResult.pass(user.getActiveItem());
        }else{
            return TypedActionResult.fail(user.getActiveItem());
        }
    }
}
