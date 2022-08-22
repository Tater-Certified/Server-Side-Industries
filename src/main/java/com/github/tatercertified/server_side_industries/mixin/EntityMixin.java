package com.github.tatercertified.server_side_industries.mixin;

import com.github.tatercertified.server_side_industries.blocks.Conveyor;
import com.github.tatercertified.server_side_industries.blocks.Slope_Conveyor;
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
    public int slow_incline = 40;
    public int medium_incline = 20;
    public int fast_incline = 8;
    @Shadow public abstract BlockPos getBlockPos();

    @Shadow public World world;

    @Inject(method = "baseTick", at = @At(value = "HEAD"))
    private void baseTick(CallbackInfo ci) {
        BlockPos.Mutable mutable = this.getBlockPos().mutableCopy();
        BlockState current = world.getBlockState(mutable);
        BlockState down = world.getBlockState(mutable.down());
        if (current.isIn(CONVEYORS)) {
            if (current.isOf(SLOW_CONVEYOR) || current.isOf(MEDIUM_CONVEYOR) || current.isOf(FAST_CONVEYOR) || current.isOf(INSANE_CONVEYOR)) {
                Conveyor.onSteppedUpon(world, current, (Entity) (Object) this);
            } else {
                if (current.isOf(SLOW_INCLINED_CONVEYOR)) {
                    if (slow_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(current, (Entity) (Object) this);
                        slow_incline = 40;
                    } else {
                        slow_incline--;
                    }
                }

                else if (current.isOf(MEDIUM_INCLINED_CONVEYOR)) {
                    if (medium_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(current, (Entity) (Object) this);
                        medium_incline = 20;
                    } else {
                        medium_incline--;
                    }
                }

                else if (current.isOf(FAST_INCLINED_CONVEYOR)) {
                    if (fast_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(current, (Entity) (Object) this);
                        fast_incline = 8;
                    } else {
                        fast_incline--;
                    }
                }

                else if (current.isOf(INSANE_INCLINED_CONVEYOR)) {
                    Slope_Conveyor.onSteppedUpon(current, (Entity) (Object) this);
                }
            }
        } else if (down.isIn(CONVEYORS)) {
            if (down.isOf(SLOW_CONVEYOR) || down.isOf(MEDIUM_CONVEYOR) || down.isOf(FAST_CONVEYOR) || down.isOf(INSANE_CONVEYOR)) {
                Conveyor.onSteppedUpon(world, down, (Entity) (Object) this);
            } else {
                if (down.isOf(SLOW_INCLINED_CONVEYOR)) {
                    if (slow_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(down, (Entity) (Object) this);
                        slow_incline = 40;
                    } else {
                        slow_incline--;
                    }
                }

                else if (down.isOf(MEDIUM_INCLINED_CONVEYOR)) {
                    if (medium_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(down, (Entity) (Object) this);
                        medium_incline = 20;
                    } else {
                        medium_incline--;
                    }
                }

                else if (down.isOf(FAST_INCLINED_CONVEYOR)) {
                    if (fast_incline == 0) {
                        Slope_Conveyor.onSteppedUpon(down, (Entity) (Object) this);
                        fast_incline = 8;
                    } else {
                        fast_incline--;
                    }
                }

                else if (down.isOf(INSANE_INCLINED_CONVEYOR)) {
                    Slope_Conveyor.onSteppedUpon(down, (Entity) (Object) this);
                }
            }
        }
    }
}
