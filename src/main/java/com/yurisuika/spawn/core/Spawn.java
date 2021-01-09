package com.yurisuika.spawn.core;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;

@Mod("spawn")
public class Spawn
{
    public Spawn()
    {
        MinecraftForge.EVENT_BUS.register(this);
    }
}
