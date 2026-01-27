package dev.gxlg.librgetter.utils;

import dev.gxlg.multiversion.gen.net.minecraft.client.multiplayer.ClientLevelWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.BlockPosWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.core.DirectionWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlockWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.BlocksWrapper;
import dev.gxlg.multiversion.gen.net.minecraft.world.level.block.state.BlockStateWrapper;
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
    private static int manhattan(BlockPosWrapper from, BlockPosWrapper to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) + Math.abs(from.getZ() - to.getZ());
    }

    private static boolean notEnoughHeight(BlockPosWrapper pos, ClientLevelWrapper world, int minHeight) {
        for (int distance = 0; distance < minHeight; distance++) {
            if (world.getBlockState(pos.above(distance)).getBlock().getHasCollisionAccessibleField()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isAirMove(BlockPosWrapper pos, ClientLevelWrapper world, DirectionWrapper dir) {
        // Can always move down
        if (dir.equals(DirectionWrapper.DOWN())) {
            return false;
        }
        // Beneath is air and beneath goal is also air

        BlockWrapper blockBelow = world.getBlockState(pos.below()).getBlock();
        BlockWrapper blockBelowGoal = world.getBlockState(pos.relative(dir).below()).getBlock();

        return !blockBelow.getHasCollisionAccessibleField() && !blockBelowGoal.getHasCollisionAccessibleField();
    }

    public static List<BlockPosWrapper> findPath(BlockPosWrapper from, BlockPosWrapper to, ClientLevelWrapper world, int minHeight) {
        BlockStateWrapper original = world.getBlockState(to);
        world.setBlockAndUpdate(to, BlocksWrapper.AIR().defaultBlockState());
        List<BlockPosWrapper> path = findPathInternal(from, to, world, minHeight);
        world.setBlockAndUpdate(to, original);
        return path;
    }

    private static List<BlockPosWrapper> findPathInternal(BlockPosWrapper from, BlockPosWrapper to, ClientLevelWrapper world, int minHeight) {
        if (notEnoughHeight(from, world, minHeight)) {
            return null;
        }
        if (notEnoughHeight(to, world, minHeight)) {
            return null;
        }

        PriorityQueue<Tail> open = new PriorityQueue<>();
        Set<BlockPosWrapper> close = new HashSet<>();
        open.add(new Tail(0, manhattan(from, to), from, null));

        while (!open.isEmpty()) {
            Tail current = open.poll();
            if (current.pos.equals(to)) {
                List<BlockPosWrapper> path = new ArrayList<>();
                for (Tail tail = current; current != null; current = current.parent) {
                    path.add(tail.pos);
                }
                Collections.reverse(path);
                return path;
            }

            close.add(current.pos);

            for (DirectionWrapper dir : new DirectionWrapper[]{
                DirectionWrapper.UP(), DirectionWrapper.DOWN(), DirectionWrapper.NORTH(), DirectionWrapper.SOUTH(), DirectionWrapper.EAST(), DirectionWrapper.WEST()
            }) {
                BlockPosWrapper nextpos = current.pos.relative(dir);
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

    private record Tail(int gCost, int hCost, BlockPosWrapper pos, Tail parent) implements Comparable<Tail> {
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

        public static Tail construct(Tail parent, BlockPosWrapper pos, BlockPosWrapper to) {
            return new Tail(parent.gCost() + 1, manhattan(pos, to), pos, parent);
        }
    }
}
