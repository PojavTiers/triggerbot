package com.adultmod;

import com.adultmod.items.ItemMorphPotion;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = AdultMod.MODID)
public class ModItems {
    public static Item morphPotion;

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        morphPotion = new ItemMorphPotion()
            .setRegistryName(AdultMod.MODID, "morph_potion")
            .setUnlocalizedName("morph_potion")
            .setCreativeTab(CreativeTabs.BREWING)
            .setMaxStackSize(1);
        event.getRegistry().register(morphPotion);
    }
}
