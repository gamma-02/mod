package mcyt_hearts.mcyt_hearts.components.player.hearts;

import dev.onyxstudios.cca.api.v3.component.Component;
import mcyt_hearts.mcyt_hearts.item_objects.Heart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.lwjgl.system.CallbackI;

public interface HeartComponentInterface extends Component {

    public Identifier getHeart(int index);
    void addHeart(Identifier heart);
    public Boolean hasHeart(Heart heart);
    public Boolean hasJoined();
    public void setJoined();

}
