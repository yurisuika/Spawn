package com.yurisuika.spawn.common;

import com.yurisuika.sky.registry.SkyDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "spawn", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class RespawnInSky
{
    @SubscribeEvent
    public static void playerRespawnEvent(PlayerEvent.PlayerRespawnEvent event)
    {
        PlayerEntity player = event.getPlayer();
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        BlockPos respawnPos = serverPlayer.func_241140_K_();

        if(respawnPos == null)
        {
            respawnInSky(player);
        }
    }

    public static void respawnInSky(Entity player) {
        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        if (player.world instanceof ServerWorld) {
            ServerWorld overworld = (ServerWorld) player.world;
            MinecraftServer minecraftserver = overworld.getServer();
            RegistryKey<World> registrykey = player.world.getDimensionKey() == SkyDimensions.SKY ? World.OVERWORLD : SkyDimensions.SKY;
            ServerWorld sky = minecraftserver.getWorld(registrykey);

            if (sky != null && !player.isPassenger()) {
                float posX = -92F;
                float posY = 55F;
                float posZ = -216F;

                serverPlayer.teleport(sky, posX, posY, posZ, player.getYaw(0.0f), player.getPitch(0.0f));

                player.world.getProfiler().endSection();
            }
        }
    }
}