package com.yurisuika.spawn.common;

import com.yurisuika.sky.registry.SkyDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.stats.Stats;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spawn", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class SpawnInSky
{
    @SubscribeEvent
    public static void playerLoggedInEvent(PlayerEvent.PlayerLoggedInEvent event)
    {
        PlayerEntity player = event.getPlayer();
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        int timePlayed = serverPlayer.getStats().getValue(Stats.CUSTOM.get(Stats.PLAY_ONE_MINUTE));

        if(timePlayed == 0)
        {
            spawnInSky(player);
        }
    }

    public static void spawnInSky(Entity player) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        if (player.world instanceof ServerWorld) {
            ServerWorld overworld = (ServerWorld) player.world;
            MinecraftServer minecraftserver = overworld.getServer();
            RegistryKey<World> registrykey = player.world.getDimensionKey() == SkyDimensions.SKY ? World.OVERWORLD : SkyDimensions.SKY;
            ServerWorld sky = minecraftserver.getWorld(registrykey);

            if (sky != null && !player.isPassenger()) {
                float posX = 0.5F;
                float posY = 79.0F;
                float posZ = 0.5F;

                serverPlayer.teleport(sky, posX, posY, posZ, player.getYaw(0.0f), player.getPitch(0.0f));

                player.world.getProfiler().endSection();
            }
        }
    }
}