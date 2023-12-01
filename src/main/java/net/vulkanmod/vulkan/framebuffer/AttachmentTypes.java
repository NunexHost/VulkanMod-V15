package net.vulkanmod.vulkan.framebuffer;

import static net.vulkanmod.vulkan.framebuffer.AttachmentTypes.Constants.*;
import static net.vulkanmod.vulkan.framebuffer.RenderPass2.*;
import static org.lwjgl.vulkan.KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR;
import static org.lwjgl.vulkan.VK10.*;

public enum AttachmentTypes {
        PRESENT(COLOR_LAYOUT, DEFAULT_FORMAT, COLOR_USAGE, VK_IMAGE_ASPECT_COLOR_BIT),
        COLOR(COLOR_LAYOUT, DEFAULT_FORMAT, COLOR_USAGE, VK_IMAGE_ASPECT_COLOR_BIT),
        DEPTH(DEPTH_LAYOUT, depthFormat, DEPTH_USAGE, VK_IMAGE_ASPECT_DEPTH_BIT),
        PRESENT_RESOLVE(COLOR_LAYOUT, DEFAULT_FORMAT, COLOR_USAGE, VK_IMAGE_ASPECT_COLOR_BIT),
        RESOLVE_COLOR(COLOR_LAYOUT, DEFAULT_FORMAT, COLOR_USAGE, VK_IMAGE_ASPECT_COLOR_BIT),
        RESOLVE_DEPTH(DEPTH_LAYOUT, depthFormat, DEPTH_USAGE, VK_IMAGE_ASPECT_DEPTH_BIT),
        PRESERVE(COLOR_LAYOUT, DEFAULT_FORMAT, COLOR_USAGE, VK_IMAGE_ASPECT_COLOR_BIT),
        INPUT(DEPTH_LAYOUT, DEFAULT_FORMAT, DEPTH_USAGE, VK_IMAGE_ASPECT_DEPTH_BIT);

    public final int layout, format, usage, aspect, finalLayout, initialLayout;
        public final boolean present, resolve, color, depth;

        AttachmentTypes(int layout, int format, int usage, int aspect) {
            this.layout = layout;
            this.format = format;
            this.usage = usage;
            this.aspect = aspect;
            this.color = this.aspect==1;
            this.depth = this.aspect==2;
            this.present = this.name().contains("PRESENT");
            this.resolve = this.name().contains("RESOLVE");
            this.initialLayout = this.present ? Constants.COLOR_LAYOUT : VK_IMAGE_LAYOUT_UNDEFINED;
            this.finalLayout = this.present ? VK_IMAGE_LAYOUT_PRESENT_SRC_KHR : this.layout;
        }

    record Constants() {
        static final int COLOR_LAYOUT = VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL;
        static final int COLOR_USAGE = VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT;
        static final int DEPTH_LAYOUT = VK_IMAGE_LAYOUT_DEPTH_STENCIL_ATTACHMENT_OPTIMAL;
        static final int DEPTH_USAGE = VK_IMAGE_USAGE_DEPTH_STENCIL_ATTACHMENT_BIT;
    }
}