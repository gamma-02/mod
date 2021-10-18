package mcyt_hearts.mcyt_hearts.components;

import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;

public class HeartComponent implements HeartComponentInterface, EntityComponentInitializer
{
    @Override public HeartComponent getHearts(PlayerEntity entity)
    {
        return null;
    }

    @Override public void addHeart(String heart)
    {

    }

    @Override public Boolean hasHeart(String heart)
    {
        return null;
    }

    @Override public void readFromNbt(NbtCompound tag)
    {

    }

    @Override public void writeToNbt(NbtCompound tag)
    {

    }

    @Override public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {

    }
}
