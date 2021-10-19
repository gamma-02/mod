package mcyt_hearts.mcyt_hearts.components.player.hearts;

import dev.onyxstudios.cca.api.v3.component.Component;
import mcyt_hearts.mcyt_hearts.item_objects.Heart;
import net.minecraft.entity.player.PlayerEntity;

public interface HeartComponentInterface extends Component {

    public static HeartComponent getHearts(PlayerEntity entity)
    {
        return null;
    }

    void addHeart(Heart heart);
    public Boolean hasHeart(Heart heart);


}
