package com.gxlg.librgetter.utils;

import com.gxlg.librgetter.mixin.AbstractBlockAccessor;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class PathFinding {
    private record Tail(int gCost, int hCost, BlockPos pos, PathFinding.Tail parent) implements Comparable<Tail> {
        public int getFCost() {
            return gCost + hCost;
        }

        @Override
        public int hashCode() {
            return Objects.hash(gCost, hCost, pos);
        }

        @Override
        public int compareTo(@NotNull PathFinding.Tail tail) {
            return Integer.compare(getFCost(), tail.getFCost());
        }

        public static Tail construct(Tail parent, BlockPos pos, BlockPos to) {
            return new Tail(parent.gCost() + 1, manhattan(pos, to), pos, parent);
        }
    }

    private static int manhattan(BlockPos from, BlockPos to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) + Math.abs(from.getZ() - to.getZ());
    }

    @SuppressWarnings("ReferenceToMixin")
    private static boolean notEnoughHeight(BlockPos pos, ClientWorld world, int minHeight) {
        for (int i = 0; i < minHeight; i++) {
            if (((AbstractBlockAccessor) world.getBlockState(pos.up(i)).getBlock()).getCollidable()) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("ReferenceToMixin")
    private static boolean isAirMove(BlockPos pos, ClientWorld world, Direction dir) {
        // Can always move down
        if (dir == Direction.DOWN) return false;
        // Beneath is air and beneath goal is also air
        return !((AbstractBlockAccessor) world.getBlockState(pos.down()).getBlock()).getCollidable() && !((AbstractBlockAccessor) world.getBlockState(pos.offset(dir).down()).getBlock()).getCollidable();
    }

    public static List<BlockPos> findPath(BlockPos from, BlockPos to, ClientWorld world, int minHeight) {
        BlockState original = world.getBlockState(to);
        world.setBlockState(to, Blocks.AIR.getDefaultState());
        List<BlockPos> path = findPathInternal(from, to, world, minHeight);
        world.setBlockState(to, original);
        return path;
    }

    private static List<BlockPos> findPathInternal(BlockPos from, BlockPos to, ClientWorld world, int minHeight) {
        if (notEnoughHeight(from, world, minHeight)) return null;
        if (notEnoughHeight(to, world, minHeight)) return null;

        PriorityQueue<Tail> open = new PriorityQueue<>();
        Set<BlockPos> close = new HashSet<>();
        open.add(new Tail(0, manhattan(from, to), from, null));

        while (!open.isEmpty()) {
            Tail current = open.poll();
            if (current.pos.equals(to)) {
                List<BlockPos> path = new ArrayList<>();
                for (Tail tail = current; current != null; current = current.parent) {
                    path.add(tail.pos);
                }
                Collections.reverse(path);
                return path;
            }

            close.add(current.pos);

            for (Direction dir : new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST, Direction.UP, Direction.DOWN}) {
                BlockPos nextpos = current.pos.offset(dir);
                if (notEnoughHeight(nextpos, world, minHeight) || isAirMove(nextpos, world, dir) || close.contains(nextpos) || current.gCost > 20)
                    continue;
                Optional<Tail> same = open.stream().filter(t -> t.pos.equals(nextpos)).findFirst();
                same.ifPresent(open::remove);
                Tail next = Tail.construct(current, nextpos, to);
                open.add(next);
            }
        }
        return null;
    }
}
