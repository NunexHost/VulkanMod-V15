package net.vulkanmod.render.chunk.build;

import net.vulkanmod.render.chunk.util.Util;
import net.vulkanmod.render.vertex.TerrainBufferBuilder;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class UploadBuffer {

    public final int indexCount;
    public final boolean autoIndices;
    public final boolean indexOnly;
    private ByteBuffer vertexBuffer;
    private ByteBuffer indexBuffer;

    public UploadBuffer(TerrainBufferBuilder.RenderedBuffer renderedBuffer) {
        TerrainBufferBuilder.DrawState drawState = renderedBuffer.drawState();
        this.indexCount = drawState.indexCount();
        this.autoIndices = drawState.sequentialIndex();
        this.indexOnly = drawState.indexOnly();

        // Optimize buffer creation:
        if (!this.indexOnly) {
            // Use direct buffer for potential performance gains:
            vertexBuffer = MemoryUtil.memAlloc(renderedBuffer.vertexBuffer().capacity());
            vertexBuffer.put(renderedBuffer.vertexBuffer()).flip();
        }

        if (!drawState.sequentialIndex()) {
            // Use direct buffer for potential performance gains:
            indexBuffer = MemoryUtil.memAlloc(renderedBuffer.indexBuffer().capacity());
            indexBuffer.put(renderedBuffer.indexBuffer()).flip();
        }
    }

    public int indexCount() {
        return indexCount;
    }

    public ByteBuffer getVertexBuffer() {
        return vertexBuffer;
    }

    public ByteBuffer getIndexBuffer() {
        return indexBuffer;
    }

    public void release() {
        if (vertexBuffer != null) {
            MemoryUtil.memFree(vertexBuffer);
            vertexBuffer = null; // Explicitly clear reference
        }
        if (indexBuffer != null) {
            MemoryUtil.memFree(indexBuffer);
            indexBuffer = null; // Explicitly clear reference
        }
    }
}
