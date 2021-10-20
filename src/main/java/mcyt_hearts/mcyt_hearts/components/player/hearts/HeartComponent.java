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
import org.jetbrains.annotations.NotNull;
import org.lwjgl.system.CallbackI;

import java.util.HashMap;

public class HeartComponent implements HeartComponentInterface, EntityComponentInitializer
{
    public static final ComponentKey<HeartComponent> HEART_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("mod", "heart"), HeartComponent.class);

    private HashMap<Integer, String> hearts = new HashMap<>();
    int numNBT;
    boolean joined = false;



    public Identifier getHeart(int index)
    {
        if(hearts.get(index) != null){
            return new Identifier("mod", hearts.get(index));
        }else{
            return new Identifier("mod", "null_heart");
        }
    }

    public int size(){
        return this.hearts.size();
    }

    @Override public void addHeart(Identifier heart)
    {

        if(hearts.size()<=9 && moreThanTwo(heart))
        {
            hearts.put((1+hearts.size()), heart.getPath());
            System.out.println("success");
        }else{
            System.out.println("fail");
        }
    }

    @Override public Boolean hasHeart(Heart heart)
    {
        return hearts.isEmpty();
    }

    @Override public Boolean hasJoined()
    {
        return joined;
    }

    @Override public void setJoined()
    {
        this.joined = true;
    }

    @Override public void readFromNbt(NbtCompound tag)
    {
        for(int l = 0; l<numNBT; l++){
            String path = tag.getString("path"+l);
            hearts.put(l,path);


        }
        this.joined = tag.getBoolean("joined");

    }

    @Override public void writeToNbt(NbtCompound tag)
    {
        int k = 0;
        int e = 0;
        for(k = 0; k< hearts.size();k++){
            if(hearts.get(e)!=null){
                tag.putString("path"+e, hearts.get(e));
                e++;
            }

        }

        numNBT = e+1;
        tag.putBoolean("joined", joined);

    }

    @Override public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(HEART_COMPONENT, player -> new HeartComponent(), RespawnCopyStrategy.NEVER_COPY);
    }
    private boolean moreThanTwo(Identifier heart){
        int k = 0;
        for(int l = 0; l<hearts.size(); l++){
            if(hearts.get(l) == heart.getPath()){
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
