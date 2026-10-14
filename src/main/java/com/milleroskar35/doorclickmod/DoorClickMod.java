package com.milleroskar35.doorclickmod;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

public class DoorClickMod implements ModInitializer {
    public static final String MOD_ID = "doorclickmod";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static final Random RANDOM = new Random();
    private static int clickCount = 0;

    @Override
    public void onInitialize() {
        LOGGER.info("Initializing DoorClickMod");
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (world.isClient) {
                return ActionResult.PASS;
            }

            BlockPos pos = hitResult.getBlockPos();
            if (world.getBlockState(pos).getBlock() instanceof DoorBlock) {
                clickCount++;
                if (clickCount >= 3) {
                    ItemStack itemStack;
                    if (RANDOM.nextBoolean()) {
                        itemStack = new ItemStack(Items.COOKIE);
                    } else {
                        itemStack = new ItemStack(Items.TNT);
                    }
                    player.getInventory().insertStack(itemStack);
                    clickCount = 0;
                }
            }
            return ActionResult.PASS;
        });
    }
}