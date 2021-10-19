package mcyt_hearts.mcyt_hearts.components.player.hearts;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import mcyt_hearts.mcyt_hearts.item_objects.Heart;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.HashMap;

public class HeartComponent implements HeartComponentInterface, EntityComponentInitializer
{
    public static final ComponentKey<HeartComponent> HEART_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("mod", "heart"), HeartComponent.class);

    private HashMap<Integer, Heart> hearts = new HashMap<>();
    int numNBT;

    public static HeartComponent getHearts(PlayerEntity entity)
    {
        return HEART_COMPONENT.get(entity);
    }

    @Override public void addHeart(Heart heart)
    {

        if(hearts.size()<=10 && moreThanTwo(heart))
        {
            hearts.put((hearts.size() - 1), heart);
        }
    }

    @Override public Boolean hasHeart(Heart heart)
    {
        return hearts.isEmpty();
    }

    @Override public void readFromNbt(NbtCompound tag)
    {
        for(int l = 0; l<numNBT; l++){
            String namespace = tag.getString("namespace"+l);
            String path = tag.getString("path"+l);
            Identifier id = new Identifier(namespace, path);
            Heart heart = (Heart) Registry.ITEM.getOrEmpty(id).get();
            hearts.put(l,heart);
        }

    }

    @Override public void writeToNbt(NbtCompound tag)
    {
        int k = 0;
        for(k = 0; k< hearts.size();k++){
            Identifier id = Registry.ITEM.getId(hearts.get(k));
            tag.putString("namespace"+k, id.getNamespace());
            tag.putString("path"+k, id.getPath());
        }
        numNBT = k;

    }

    @Override public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(HEART_COMPONENT, player -> new HeartComponent(), RespawnCopyStrategy.NEVER_COPY);
    }
    private boolean moreThanTwo(Heart heart){
        int k = 0;
        for(int l = 0; l<hearts.size(); l++){
            if(hearts.get(l) == heart){
                k++;
            }
        }
        if(k>2){
            return false;
        }else{
            return true;
        }
    }
}
