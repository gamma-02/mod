package mcyt_hearts.mcyt_hearts.components.player.hearts;

import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import dev.onyxstudios.cca.api.v3.entity.RespawnCopyStrategy;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class HeartComponent implements HeartComponentInterface, EntityComponentInitializer
{
    public static final ComponentKey<HeartComponent> HEART_COMPONENT = ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("mod", "heart"), HeartComponent.class);

    private final HashMap<Integer, String> hearts = new HashMap<>();
    private int numNBT;
    public int size(){
        return this.hearts.size();
    }

    @Override public void addHeart(Identifier heart)
    {

        if(hearts.size()<=9)
        {
            hearts.put((1+hearts.size()), heart.getPath());
            System.out.println("success");
        }else{
            System.out.println("fail");
        }
    }

    @Override
    public Identifier getHeart(int index) {
        if(this.hearts.get(index)!=null) {
            return new Identifier("mod",this.hearts.get(index));
        }else{
            return new Identifier("mod", "null_heart");
        }
    }

    @Override public void readFromNbt(NbtCompound tag)
    {
        for(int l = 0; l<numNBT; l++){
            String path = tag.getString("path"+l);
            hearts.put(l,path);


        }
        tag.putBoolean("aphmau", this.aphmau);
        tag.putBoolean("craftee", this.craftee);
        tag.putBoolean("techno",this.techno);
        tag.putBoolean("mrbeast", this.mrbeast);
        tag.putBoolean("karl", this.karl);
        tag.putBoolean("preston", this.preston);
        tag.putBoolean("tommy", this.tommy);
        tag.putBoolean("dream", this.dream);
        tag.putBoolean("george", this.george);
        tag.putBoolean("wisp", this.wisp);

    }

    @Override public void writeToNbt(NbtCompound tag)
    {

        int e = 0;
        for(int k = 0; k< hearts.size();k++){
            if(hearts.get(e)!=null){
                tag.putString("path"+e, hearts.get(e));
                e++;
            }

        }

        numNBT = e+1;
        tag.putBoolean("aphmau", this.aphmau);
        tag.putBoolean("craftee", this.craftee);
        tag.putBoolean("techno",this.techno);
        tag.putBoolean("mrbeast", this.mrbeast);
        tag.putBoolean("karl", this.karl);
        tag.putBoolean("preston", this.preston);
        tag.putBoolean("tommy", this.tommy);
        tag.putBoolean("dream", this.dream);
        tag.putBoolean("george", this.george);
        tag.putBoolean("wisp", this.wisp);
    }

    @Override public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry)
    {
        registry.registerForPlayers(HEART_COMPONENT, player -> new HeartComponent(), RespawnCopyStrategy.NEVER_COPY);
    }

    private boolean techno = false;
    private boolean aphmau = false;
    private boolean tommy = false;
    private boolean dream = false;
    private boolean george = false;
    private boolean karl = false;
    private boolean wisp = false;
    private boolean preston = false;
    private boolean craftee = false;
    private boolean mrbeast = false;

    @Override
    public boolean getAphmau() {
        return this.aphmau;
    }

    @Override
    public boolean getDream() {
        return this.dream;
    }

    @Override
    public boolean getKarl() {
        return this.karl;
    }

    @Override
    public boolean getMrbeast() {
        return this.mrbeast;
    }

    @Override
    public boolean getPreston() {
        return this.preston;
    }

    @Override
    public boolean getTechno() {
        return this.techno;
    }

    @Override
    public boolean getWisp() {
        return this.wisp;
    }

    @Override
    public boolean getGeorge() {
        return this.george;
    }

    @Override
    public boolean getCraftee() {
        return this.craftee;
    }

    @Override
    public boolean getTommy() {
        return this.tommy;
    }

    @Override
    public void setAphmau(boolean e) {
        this.aphmau = e;
    }

    @Override
        public void setDream(boolean e) {
            this.dream = e;
        }

    @Override
    public void setKarl(boolean e) {
        this.karl = e;

    }

    @Override
    public void setMrbeast(boolean e) {
        this.mrbeast = e;
    }

    @Override
    public void setPreston(boolean e) {
        this.preston = e;
    }

    @Override
    public void setTechno(boolean e) {
        this.techno = e;
    }

    @Override
    public void setWisp(boolean e) {
        this.wisp = e;
    }

    @Override
    public void setGeorge(boolean e) {
        this.george = e;
    }

    @Override
    public void setCraftee(boolean e) {
        this.craftee = e;
    }

    @Override
    public void setTommy(boolean e) {
        this.tommy = e;
    }

}
