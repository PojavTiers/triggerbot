package com.adultmod;

import com.adultmod.entity.EntityDomGirl;
import com.adultmod.entity.EntitySubGirl;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = AdultMod.MODID, name = "Adult Mod", version = "3.0.0")
public class AdultMod {
    public static final String MODID = "adultmod";

    @Mod.Instance
    public static AdultMod instance;

    public static SimpleNetworkWrapper network;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        network.registerMessage(com.adultmod.network.MessageOpenGirlMenu.Handler.class,
            com.adultmod.network.MessageOpenGirlMenu.class, 0, Side.CLIENT);
        network.registerMessage(com.adultmod.network.MessageGirlAction.Handler.class,
            com.adultmod.network.MessageGirlAction.class, 1, Side.SERVER);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        // ---- Entities ----
        register("domgirl", EntityDomGirl.class, 1);
        register("subgirl", EntitySubGirl.class, 2);

        net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(
            EntityDomGirl.class, 8, 1, 2, EnumCreatureType.CREATURE,
            Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST);
        net.minecraftforge.fml.common.registry.EntityRegistry.addSpawn(
            EntitySubGirl.class, 12, 1, 3, EnumCreatureType.CREATURE,
            Biomes.PLAINS, Biomes.FOREST, Biomes.BIRCH_FOREST);

        // ---- Spawn Eggs (black/red = dom, pink/white = sub) ----
        net.minecraftforge.fml.common.registry.EntityRegistry.registerEgg(
            new ResourceLocation(MODID, "domgirl"), 0x1A1A1A, 0xB22222);
        net.minecraftforge.fml.common.registry.EntityRegistry.registerEgg(
            new ResourceLocation(MODID, "subgirl"), 0xFFB6C1, 0xFFFFFF);

        // ---- Morph Potion recipe: awkward potion + poppy ----
        net.minecraftforge.fml.common.registry.GameRegistry.addShapelessRecipe(
            new net.minecraft.item.ItemStack(ModItems.morphPotion),
            new net.minecraft.item.ItemStack(net.minecraft.init.Items.POTIONITEM, 1, 0),
            new net.minecraft.item.ItemStack(net.minecraft.init.Items.POPPY));

        if (event.getSide().isClient()) {
            net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(
                EntityDomGirl.class, com.adultmod.client.render.RenderGirl.FACTORY_DOM);
            net.minecraftforge.fml.client.registry.RenderingRegistry.registerEntityRenderingHandler(
                EntitySubGirl.class, com.adultmod.client.render.RenderGirl.FACTORY_SUB);
            net.minecraftforge.common.MinecraftForge.EVENT_BUS.register(
                new com.adultmod.client.render.RenderPlayerMorph());
        }
    }

    private void register(String name, Class<? extends net.minecraft.entity.Entity> cls, int id) {
        net.minecraftforge.fml.common.registry.EntityRegistry.registerModEntity(
            new ResourceLocation(MODID, name), cls, name, id, this, 64, 3, true);
    }
}
