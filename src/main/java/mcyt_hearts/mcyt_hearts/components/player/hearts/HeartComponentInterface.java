package mcyt_hearts.mcyt_hearts.components.player.hearts;

import dev.onyxstudios.cca.api.v3.component.Component;
import net.minecraft.util.Identifier;

public interface HeartComponentInterface extends Component {


    public void addHeart(Identifier heart);
    public Identifier getHeart(int index);
    public boolean getAphmau();
    public boolean getDream();
    public boolean getKarl();
    public boolean getMrbeast();
    public boolean getPreston();
    public boolean getTechno();
    public boolean getWisp();
    public boolean getGeorge();
    public boolean getCraftee();
    public boolean getTommy();

    public void setAphmau(boolean e);
    public void setDream(boolean e);
    public void setKarl(boolean e);
    public void setMrbeast(boolean e);

    public void setPreston(boolean e);

    public void setTechno(boolean e);
    public void setWisp(boolean e);
    public void setGeorge(boolean e);
    public void setCraftee(boolean e);
    public void setTommy(boolean e);

}
