package dev.gxlg.librgetter.utils;

import dev.gxlg.librgetter.utils.chaining.villagers.Villagers;
import dev.gxlg.librgetter.utils.exceptions.commands.CouldNotFindLecternException;
import dev.gxlg.librgetter.utils.exceptions.commands.CouldNotFindLibrarianException;
import dev.gxlg.versiont.gen.net.minecraft.client.multiplayer.ClientLevel;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import dev.gxlg.versiont.gen.net.minecraft.core.Direction;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.Entity;
import dev.gxlg.versiont.gen.net.minecraft.world.entity.npc.villager.Villager;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Block;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.Blocks;
import dev.gxlg.versiont.gen.net.minecraft.world.level.block.SlabBlock;
import dev.gxlg.versiont.gen.net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.function.Predicate;

public class PathFinding {
    public static final Predicate<Entity> DEFAULT_LIBRARIAN_PREDICATE = e -> (e instanceof Villager v) && Villagers.isVillagerLibrarian(v);

    private static final Direction[] directions = new Direction[]{
        Direction.UP(), Direction.DOWN(), Direction.NORTH(), Direction.SOUTH(), Direction.EAST(), Direction.WEST()
    };

    public static List<BlockPos> findPathToBlock(BlockPos from, BlockPos to, ClientLevel world, int minHeight) {
        return findPathInternal(from, to, world, minHeight, pos -> pos.getY() == to.getY() && manhattan(pos, to) == 1 && isOnGround(pos, world));
    }

    public static List<BlockPos> findPathInsideBlock(BlockPos from, BlockPos to, ClientLevel world, int minHeight) {
        return findPathInternal(from, to, world, minHeight, pos -> to.equals(from));
    }

    public static List<BlockPos> humanize(List<BlockPos> path, ClientLevel world, int minHeight) {
        // make jumps and falls aim directly for the next block
        List<BlockPos> altitudeFix = new ArrayList<>();
        BlockPos lastAdded = path.get(0);
        altitudeFix.add(lastAdded);
        for (int i = 1; i < path.size(); i++) {
            BlockPos current = path.get(i);
            if (lastAdded.getX() != current.getX() || lastAdded.getZ() != current.getZ() || i == path.size() - 1) {
                altitudeFix.add(current);
                lastAdded = current;
            }
        }
        // cut corners
        List<BlockPos> cornerFix = new ArrayList<>();
        List<BlockPos> cornerBuffer = new ArrayList<>();
        for (BlockPos pos : altitudeFix) {
            cornerBuffer.add(pos);
            if (cornerBuffer.size() < 3) {
                continue;
            }
            BlockPos before = cornerBuffer.remove(0);
            cornerFix.add(before);

            BlockPos potentialCorner = cornerBuffer.get(0);
            BlockPos after = cornerBuffer.get(1);
            if (before.getY() != potentialCorner.getY() || potentialCorner.getY() != after.getY()) {
                continue;
            }
            // check for a corner
            if ((before.getX() == potentialCorner.getX()) != (potentialCorner.getX() == after.getX())) {
                // we need enough space to cut the corner
                BlockPos innerCorner = before.offset(after.getX() - potentialCorner.getX(), 0, after.getZ() - potentialCorner.getZ());
                if (hasCollision(innerCorner, world, minHeight)) {
                    continue;
                }
                // remove the corner from the buffer
                cornerBuffer.remove(0);
            }
        }
        cornerFix.addAll(cornerBuffer);
        // turn moves over gaps into jumps
        List<BlockPos> gapFix = new ArrayList<>(cornerFix);
        for (int i = 1; i < cornerFix.size() - 1; i++) {
            BlockPos last = cornerFix.get(i - 1);
            BlockPos current = cornerFix.get(i);
            BlockPos next = cornerFix.get(i + 1);
            if (last.getY() != current.getY() || current.getY() != next.getY()) {
                continue;
            }
            // check if there is a block below the current position
            if (!world.getBlockState(current.below()).getBlock().getHasCollisionAccessibleField()) {
                // we can only do the jump, if there's enough room
                if (hasCollision(last.above(1), world, minHeight) || hasCollision(current.above(1), world, minHeight) || hasCollision(next.above(1), world, minHeight)) {
                    continue;
                }
                // move the position up by one
                gapFix.set(i, current.above(1));
            }
        }
        return gapFix;
    }

    public static BlockPos searchForBlock(ClientLevel world, BlockPos center, int radius, Block block, Predicate<BlockPos> allowedPositions) {
        for (int distance = 1; distance < radius; distance++) {
            for (int deltaX = -distance; deltaX <= distance; deltaX++) {
                for (int deltaY = -distance; deltaY <= distance; deltaY++) {
                    for (int deltaZ = -distance; deltaZ <= distance; deltaZ++) {
                        if (distance != Math.abs(deltaX) && distance != Math.abs(deltaY) && distance != Math.abs(deltaZ)) {
                            continue;
                        }
                        BlockPos pos = center.offset(deltaX, deltaY, deltaZ);
                        if (world.getBlockState(pos).getBlock().equals(block) && allowedPositions.test(pos)) {
                            return pos;
                        }
                    }
                }
            }
        }
        return null;
    }

    public static Entity searchForEntity(ClientLevel world, BlockPos center, double radius, Predicate<Entity> allowedEntities) {
        Vec3 centerPos = Vec3.atCenterOf(center);
        Entity foundEntity = null;
        double minDistance = Double.POSITIVE_INFINITY;
        for (Entity entity : world.entitiesForRendering()) {
            if (allowedEntities.test(entity)) {
                double distance = centerPos.distanceTo(new Vec3(entity.getX(), entity.getY(), entity.getZ()));
                if (distance < minDistance && distance < radius) {
                    minDistance = distance;
                    foundEntity = entity;
                }
            }
        }
        return foundEntity;
    }

    public static Jobsite findJobsite(ClientLevel world, BlockPos center, Predicate<BlockPos> allowedLecterns) throws CouldNotFindLecternException, CouldNotFindLibrarianException {
        BlockPos foundLecternPos = PathFinding.searchForBlock(world, center, 5, Blocks.LECTERN(), allowedLecterns);
        if (foundLecternPos == null) {
            throw new CouldNotFindLecternException();
        }
        Villager foundVillager = (Villager) PathFinding.searchForEntity(world, foundLecternPos, 4.0, DEFAULT_LIBRARIAN_PREDICATE);
        if (foundVillager == null) {
            throw new CouldNotFindLibrarianException();
        }
        return new Jobsite(foundLecternPos, foundVillager);
    }

    private static int manhattan(BlockPos from, BlockPos to) {
        return Math.abs(from.getX() - to.getX()) + Math.abs(from.getY() - to.getY()) + Math.abs(from.getZ() - to.getZ());
    }

    private static boolean hasCollision(BlockPos pos, ClientLevel world, int minHeight) {
        for (int distance = 0; distance < minHeight; distance++) {
            if (world.getBlockState(pos.above(distance)).getBlock().getHasCollisionAccessibleField()) {
                return true;
            }
        }
        return false;
    }

    private static boolean isOnGround(BlockPos pos, ClientLevel world) {
        return world.getBlockState(pos.below()).getBlock().getHasCollisionAccessibleField();
    }

    private static boolean isValidMove(BlockPos from, ClientLevel world, Direction dir, int minHeight) {
        if (hasCollision(from.relative(dir), world, minHeight)) {
            return false;
        }
        // Can always move down
        if (dir.equals(Direction.DOWN())) {
            return true;
        }
        // Check if at least one of both current and next position has a collidable block under feet
        if (!isOnGround(from, world) && !isOnGround(from.relative(dir), world)) {
            return false;
        }
        // Check if moving over slab
        return !(world.getBlockState(from.below()).getBlock() instanceof SlabBlock);
    }

    private static List<BlockPos> findPathInternal(BlockPos from, BlockPos to, ClientLevel world, int minHeight, Predicate<BlockPos> finishCriteria) {
        PriorityQueue<Tail> open = new PriorityQueue<>();
        Set<BlockPos> close = new HashSet<>();
        open.add(new Tail(0, manhattan(from, to), from, null, null, 0));

        while (!open.isEmpty()) {
            Tail current = open.poll();
            if (finishCriteria.test(current.pos)) {
                List<BlockPos> path = new ArrayList<>();
                Tail tail = current;
                while (tail != null) {
                    path.add(tail.pos);
                    tail = tail.parent;
                }
                Collections.reverse(path);
                return path;
            }

            close.add(current.pos);
            if (current.isTooExpensive()) {
                continue;
            }

            for (Direction dir : directions) {
                BlockPos nextpos = current.pos().relative(dir);
                if (!isValidMove(current.pos(), world, dir, minHeight) || close.contains(nextpos)) {
                    continue;
                }

                Tail next = current.constructChild(nextpos, to, dir);
                Optional<Tail> same = open.stream().filter(t -> t.pos().equals(nextpos)).findFirst();
                if (same.map(t -> t.gCost() > next.gCost()).orElse(true)) {
                    same.ifPresent(open::remove);
                    open.add(next);
                }
            }
        }
        return null;
    }

    private record Tail(int gCost, int hCost, BlockPos pos, Tail parent, Direction direction, int fallDistance) implements Comparable<Tail> {
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

        public Tail constructChild(BlockPos pos, BlockPos to, Direction direction) {
            int fall = direction.equals(Direction.DOWN()) ? this.fallDistance + 1 : 0;
            int cost = direction.equals(Direction.DOWN()) ? 1 : 2;
            return new Tail(this.gCost + cost, manhattan(pos, to), pos, this, direction, fall);
        }

        public boolean isTooExpensive() {
            return this.gCost > 45 || this.fallDistance > 3;
        }
    }

    public record Jobsite(BlockPos lectern, Villager librarian) { }
}
