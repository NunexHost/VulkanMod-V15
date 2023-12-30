@Mixin(SingleQuadParticle.class)
public abstract class SingleQuadParticleM extends Particle {

    @Shadow protected float quadSize;

    @Shadow protected abstract float getU0();
    @Shadow protected abstract float getU1();
    @Shadow protected abstract float getV0();
    @Shadow protected abstract float getV1();

    @Shadow public abstract float getQuadSize(float f);

    protected SingleQuadParticleM(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i) {
        super(clientLevel, d, e, f, g, h, i);
        this.quadSize = 0.1F * (this.random.nextFloat() * 0.5F + 0.5F) * 2.0F;
    }

    /**
     * @author
     * @reason
     */
    @Overwrite
    public void render(VertexConsumer vertexConsumer, Camera camera, float f) {
        double lx = (Mth.lerp(f, this.xo, this.x));
        double ly = (Mth.lerp(f, this.yo, this.y));
        double lz = (Mth.lerp(f, this.zo, this.z));

        if(cull(WorldRenderer.getInstance(), (float) lx, (float) ly, (float) lz))
            return;

        float offsetX = (float) (lx - camera.getPosition().x());
        float offsetY = (float) (ly - camera.getPosition().y());
        float offsetZ = (float) (lz - camera.getPosition().z());

        Quaternionf quaternionf;
        if (this.roll != 0.0F) {
            quaternionf = new Quaternionf(camera.rotation());
            quaternionf.rotateZ(Mth.lerp(f, this.oRoll, this.roll));
        } else {
            quaternionf = camera.rotation();
        }

        Vector3f tempVector = new Vector3f();
        for (int k = 0; k < 4; ++k) {
            tempVector.set(vector3fs[k]);
            tempVector.rotate(quaternionf);
            tempVector.mul(j);
            tempVector.add(offsetX, offsetY, offsetZ);

            float u0 = this.getU0();
            float u1 = this.getU1();
            float v0 = this.getV0();
            float v1 = this.getV1();
            int light = this.getLightColor(f);

            ExtendedVertexBuilder vertexBuilder = (ExtendedVertexBuilder)vertexConsumer;
            int packedColor = ColorUtil.packColorIntRGBA(this.rCol, this.gCol, this.bCol, this.alpha);

            vertexBuilder.vertex(tempVector.x(), tempVector.y(), tempVector.z(), u1, v1, packedColor, light);
        }
    }

    protected int getLightColor(float f) {
        BlockPos blockPos = BlockPos.containing(this.x, this.y, this.z);
        return this.level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
    }

    private boolean cull(WorldRenderer worldRenderer, float x, float y, float z) {
        RenderSection section = worldRenderer.getSectionGrid().getSectionAtBlockPos((int) x, (int) y, (int) z);
        return section != null && section.getLastFrame() != worldRenderer.getLastFrame();
    }

    @Override
    public ParticleRenderType getRenderType() {
        return null;
    }
}
