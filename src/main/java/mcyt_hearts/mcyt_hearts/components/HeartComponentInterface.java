package mcyt_hearts.mcyt_hearts.components;

import dev.onyxstudios.cca.api.v3.component.Component;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.player.PlayerEntity;

public interface HeartComponentInterface extends Component {

    public HeartComponent getHearts(PlayerEntity entity);
    void addHeart(String heart);
    public Boolean hasHeart(String heart);


}
