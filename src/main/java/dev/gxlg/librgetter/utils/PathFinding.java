package dev.gxlg.librgetter.utils;

import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Block;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;

public class PathFinding {
    private static int manhattan(BlockPos from, BlockPos to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) + Math.abs(from.getZ() - to.getZ());
    }

    private static boolean notEnoughHeight(BlockPos pos, ClientLevel world, int minHeight) {
        for (int distance = 0; distance < minHeight; distance++) {
            if (world.getBlockState(pos.above(distance)).getBlock().getHasCollisionAccessibleField()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAirMove(BlockPos pos, ClientLevel world, Direction dir) {
        // Can always move down
        if (dir.equals(Direction.DOWN())) {
            return false;
        }
        // Beneath is air and beneath goal is also air

        Block blockBelow = world.getBlockState(pos.below()).getBlock();
        Block blockBelowGoal = world.getBlockState(pos.relative(dir).below()).getBlock();

        return !blockBelow.getHasCollisionAccessibleField() && !blockBelowGoal.getHasCollisionAccessibleField();
    }

    public static List<BlockPos> findPath(BlockPos from, BlockPos to, ClientLevel world, int minHeight) {
        BlockState original = world.getBlockState(to);
        world.setBlockAndUpdate(to, Blocks.AIR().defaultBlockState());
        List<BlockPos> path = findPathInternal(from, to, world, minHeight);
        world.setBlockAndUpdate(to, original);
        return path;
    }

    private static List<BlockPos> findPathInternal(BlockPos from, BlockPos to, ClientLevel world, int minHeight) {
        if (notEnoughHeight(from, world, minHeight)) {
            return null;
        }
        if (notEnoughHeight(to, world, minHeight)) {
            return null;
        }

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

            for (Direction dir : new Direction[]{
                Direction.UP(), Direction.DOWN(), Direction.NORTH(), Direction.SOUTH(), Direction.EAST(), Direction.WEST()
            }) {
                BlockPos nextpos = current.pos.relative(dir);
                if (notEnoughHeight(nextpos, world, minHeight) || isAirMove(nextpos, world, dir) || close.contains(nextpos) || current.gCost > 20) {
                    continue;
                }
                Optional<Tail> same = open.stream().filter(t -> t.pos.equals(nextpos)).findFirst();
                same.ifPresent(open::remove);
                Tail next = Tail.construct(current, nextpos, to);
                open.add(next);
            }
        }
        return null;
    }

    private record Tail(int gCost, int hCost, BlockPos pos, Tail parent) implements Comparable<Tail> {
        public int getFCost() {
            return gCost + hCost;
        }

        @Override
        public int hashCode() {
            return Objects.hash(gCost, hCost, pos);
        }

        @Override
        public int compareTo(@NotNull Tail tail) {
            return Integer.compare(getFCost(), tail.getFCost());
        }

        public static Tail construct(Tail parent, BlockPos pos, BlockPos to) {
            return new Tail(parent.gCost() + 1, manhattan(pos, to), pos, parent);
        }
    }
}
