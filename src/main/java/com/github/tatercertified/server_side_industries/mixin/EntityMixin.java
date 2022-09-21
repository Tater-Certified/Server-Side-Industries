package com.github.tatercertified.server_side_industries.mixin;

import com.github.tatercertified.server_side_industries.blocks.Conveyor;
import com.github.tatercertified.server_side_industries.blocks.Slope_Conveyor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.command.CommandOutput;
import net.minecraft.util.Nameable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.entity.EntityLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.tatercertified.server_side_industries.blocks.SSI_Blocks.*;

@Mixin(Entity.class)
public abstract class EntityMixin implements Nameable, EntityLike, CommandOutput {
    public int current_int;
    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public World world;

    @Shadow public abstract boolean isOnGround();

    @Inject(method = "baseTick", at = @At(value = "HEAD"))
    private void baseTick(CallbackInfo ci) {
        if (this.isOnGround()) {
            BlockPos.Mutable mutable = this.getBlockPos().mutableCopy();
            BlockState current;

            if (world.getBlockState(mutable).isIn(CONVEYORS)) {
                current = world.getBlockState(mutable);
            } else {
                current = world.getBlockState(mutable.down());
            }
            if (current.isIn(CONVEYORS)) {
                Block current_block = current.getBlock();
                if (current_block == SLOW_CONVEYOR || current_block == MEDIUM_CONVEYOR || current_block == FAST_CONVEYOR || current_block == INSANE_CONVEYOR) {
                    Conveyor.onSteppedUpon(current, (Entity) (Object) this);
                } else {
                    if (current_block == INSANE_INCLINED_CONVEYOR) {
                        Slope_Conveyor.onSteppedUpon((Entity) (Object) this, current);
                    } else if (current_int == 0) {
                        Slope_Conveyor.onSteppedUpon((Entity) (Object) this, current);

                        if (current_block == SLOW_INCLINED_CONVEYOR) {
                            current_int = 40;
                        } else if (current_block == MEDIUM_INCLINED_CONVEYOR) {
                            current_int = 20;
                        } else if (current_block == FAST_INCLINED_CONVEYOR) {
                            current_int = 8;
                        }
                    } else {
                        current_int--;
                    }
                }
            }
        }
    }
}
