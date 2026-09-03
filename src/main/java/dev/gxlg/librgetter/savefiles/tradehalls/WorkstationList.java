package dev.gxlg.librgetter.savefiles.tradehalls;

import dev.gxlg.librgetter.utils.types.EnchantmentTrade;
import dev.gxlg.versiont.gen.net.minecraft.core.BlockPos;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class WorkstationList extends ArrayList<WorkstationList.Workstation> {
    public WorkstationList() {
        super();
    }

    public WorkstationList(@NotNull Collection<? extends Workstation> c) {
        super(c);
    }

    public Workstation findWorkstation(BlockPos lecternPos) {
        Position pos = Position.fromBlockPos(lecternPos);
        return this.stream().filter(ws -> ws.position.equals(pos)).findFirst().orElse(null);
    }

    public WorkstationList filterWorkstations(BlockPos center, int radius) {
        WorkstationList filtered = new WorkstationList();
        for (Workstation ws : this) {
            if (!ws.getPosition().toBlockPos().closerThan(center, radius)) {
                continue;
            }
            filtered.add(ws);
        }
        return filtered;
    }

    public boolean workstationExists(BlockPos lecternPos) {
        return findWorkstation(lecternPos) != null;
    }

    public void addOrUpdateWorkstation(BlockPos lecternPos, List<EnchantmentTrade> trades) {
        Workstation existing = findWorkstation(lecternPos);
        if (existing == null) {
            this.add(new Workstation(Position.fromBlockPos(lecternPos), trades));
            return;
        }
        existing.trades.clear();
        existing.trades.addAll(trades);
    }

    public void addDummyWorkstation(BlockPos lecternPos) {
        Workstation existing = findWorkstation(lecternPos);
        if (existing == null) {
            this.add(new Workstation(Position.fromBlockPos(lecternPos), new ArrayList<>()));
        }
    }

    @SuppressWarnings({ "FieldMayBeFinal", "FieldCanBeLocal", "unused" })
    public static class Workstation {
        private Position position;

        private List<EnchantmentTrade> trades;

        private Workstation() {
            this(new Position(), new ArrayList<>());
        }

        private Workstation(Position position, List<EnchantmentTrade> trades) {
            this.position = position;
            this.trades = new ArrayList<>(trades);
        }

        public Position getPosition() {
            return position;
        }

        public List<EnchantmentTrade> getTrades() {
            return Collections.unmodifiableList(trades);
        }
    }

    @SuppressWarnings({ "FieldMayBeFinal", "FieldCanBeLocal", "unused" })
    public static class Position {
        private int x;

        private int y;

        private int z;

        private Position() {
            this(0, 0, 0);
        }

        private Position(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public BlockPos toBlockPos() {
            return new BlockPos(x, y, z);
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Position position = (Position) obj;
            return x == position.x && y == position.y && z == position.z;
        }

        @Override
        public String toString() {
            return x + " " + y + " " + z;
        }

        public static Position fromBlockPos(BlockPos pos) {
            return new Position(pos.getX(), pos.getY(), pos.getZ());
        }
    }
}
