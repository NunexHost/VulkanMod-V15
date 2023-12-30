package net.vulkanmod.render.chunk.build;

import com.google.common.collect.ImmutableList;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.client.renderer.chunk.VisibilitySet;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.vulkanmod.render.vertex.TerrainBufferBuilder;
import net.vulkanmod.render.vertex.TerrainRenderType;

import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

public class CompiledSection {
    public static final CompiledSection UNCOMPILED = new CompiledSection() {
        @Override
        public boolean canSeeThrough(Direction dir1, Direction dir2) {
            return false;
        }
    };

    public final Set<TerrainRenderType> renderTypes = EnumSet.noneOf(TerrainRenderType.class);
    private boolean isCompletelyEmpty = true;
    private final List<BlockEntity> renderableBlockEntities = ImmutableList.of(); // Initialize as empty
    private final VisibilitySet visibilitySet = new VisibilitySet();
    @Nullable
    private TerrainBufferBuilder.SortState transparencyState;

    public boolean hasNoRenderableLayers() {
        return isCompletelyEmpty;
    }

    public boolean isEmpty(TerrainRenderType renderType) {
        return !renderTypes.contains(renderType);
    }

    public List<BlockEntity> getRenderableBlockEntities() {
        return renderableBlockEntities; // Return immutable list
    }

    public boolean canSeeThrough(Direction dir1, Direction dir2) {
        return visibilitySet.visibilityBetween(dir1, dir2);
    }
}
