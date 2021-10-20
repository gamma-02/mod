package mcyt_hearts.mcyt_hearts;

import mcyt_hearts.mcyt_hearts.item_objects.*;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.lwjgl.system.CallbackI;

import java.util.ArrayList;
import java.util.function.Supplier;

public class items {
    public static ArrayList<ItemRegistryHelper> items = new ArrayList<>();

    public static final ItemGroup creativeModGroup = FabricItemGroupBuilder.build(new Identifier("mod", "creative"),
            new Supplier<ItemStack>()
            {
                @Override public ItemStack get()
                {
                    return Items.TOTEM_OF_UNDYING.getDefaultStack();
                }
            });


    public static final Item DREAM_HEART = new DreamHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item TECHNO_HEART = new TechnoHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item TOMMY_HEART = new TommyHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item GEORGE_HEART = new GeorgeHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item APHMAU_HEART = new AphmauHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item PRESTON_HEART = new PrestonHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item MRBEAST_HEART = new MrbeastHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item CRAFTEE_HEART = new CrafteeHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item WISP_HEART = new WispHeart(new FabricItemSettings().group(creativeModGroup));
    public static final Item KARL_HEART = new KarlHeart(new FabricItemSettings().group(creativeModGroup));


    static {
        add(DREAM_HEART, "dream_heart");
        add(TOMMY_HEART, "tommy_heart");
        add(TECHNO_HEART, "techno_heart");
        add(GEORGE_HEART, "george_heart");
        add(APHMAU_HEART, "aphmau_heart");
        add(PRESTON_HEART, "preston_heart");
        add(MRBEAST_HEART, "mrbeast_heart");
        add(CRAFTEE_HEART, "craftee_heart");
        add(WISP_HEART, "wisp_heart");
        add(KARL_HEART, "karl_heart");


    }
    public static void add(Item item2, String name) {
        items.add(new ItemRegistryHelper(item2, new Identifier("mod", name)));
    }

    public static void registerItems() {
        for(ItemRegistryHelper item : items) {
            Registry.register(Registry.ITEM, item.identifier, item.item);
        }
    }




}
class ItemRegistryHelper {
    public final Item item;
    public final Identifier identifier;
    public ItemRegistryHelper(Item item, Identifier identifier) {
        this.item = item;
        this.identifier = identifier;
    }

}
