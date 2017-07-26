package io.sommers.configurablelever;

import com.teamacronymcoders.base.BaseModFoundation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static io.sommers.configurablelever.ConfigurableLever.*;

@Mod(modid = MODID, name = MOD_NAME, version = VERSION, dependencies = DEPENDS)
public class ConfigurableLever extends BaseModFoundation<ConfigurableLever> {
    public final static String MODID = "configurablelever";
    public final static String MOD_NAME = "Configurable Lever";
    public final static String VERSION = "##VERSION##";
    public final static String DEPENDS = "after:base[0.0.0,)";

    public ConfigurableLever() {
        super(MODID, MOD_NAME, VERSION, CreativeTabs.MISC);
    }

    @Override
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    @EventHandler
    public void init(FMLInitializationEvent event) {
        super.init(event);
    }

    @Override
    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        super.postInit(event);
    }

    @Override
    public boolean useModAsConfigFolder() {
        return false;
    }

    @Override
    public ConfigurableLever getInstance() {
        return this;
    }
}
