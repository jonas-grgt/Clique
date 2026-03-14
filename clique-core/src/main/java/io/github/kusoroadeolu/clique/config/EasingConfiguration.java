package io.github.kusoroadeolu.clique.config;


import java.util.Objects;

//Added docs here so I won't forget what each val does
public class EasingConfiguration {
    public final static EasingConfiguration DEFAULT = new EasingConfiguration();
    private final EasingFunction function;
    private final int durationMs;
    private final int frames;
    private final int threshold;


    private EasingConfiguration() {
        this(new EasingConfigurationBuilder());
    }

    private EasingConfiguration(EasingConfigurationBuilder easingConfigurationBuilder) {
        this.function = easingConfigurationBuilder.function;
        this.durationMs = easingConfigurationBuilder.durationMs;
        this.frames = easingConfigurationBuilder.frames;
        this.threshold = easingConfigurationBuilder.threshold;
    }

    public static EasingConfigurationBuilder immutableBuilder() {
        return new EasingConfigurationBuilder();
    }

    public EasingFunction getFunction() {
        return function;
    }

    public int getDurationMs() {
        return durationMs;
    }

    public int getFrames() {
        return frames;
    }

    public int getThreshold() {
        return threshold;
    }

    public long getFrameDelayMs() {
        return durationMs / frames;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;

        EasingConfiguration that = (EasingConfiguration) object;
        return durationMs == that.durationMs && frames == that.frames && threshold == that.threshold && function == that.function;
    }

    public int hashCode() {
        return Objects.hash(durationMs, frames, threshold, function);
    }

    @Override
    public String toString() {
        return "EasingConfiguration[" +
                "function=" + function +
                ", durationMs=" + durationMs +
                ", frames=" + frames +
                ", threshold=" + threshold +
                ']';
    }

    /**
     * Check if easing should be applied based on the tick amount
     *
     * @param tickAmount the amount being ticked
     * @return true if easing should be applied
     */
    public boolean shouldEase(int tickAmount) {
        if (threshold < 0) return false;
        return tickAmount >= threshold;
    }

    public static class EasingConfigurationBuilder {
        private EasingFunction function = EasingFunction.LINEAR;
        private int durationMs = 0;
        private int frames = 1;
        private int threshold = -1;


        public EasingConfigurationBuilder function(EasingFunction function) {
            Objects.requireNonNull(function, "Easing function cannot be null");
            this.function = function;
            return this;
        }

        /**
         * Set the total duration of the easing animation in milliseconds
         *
         * @param durationMs duration in milliseconds (must be positive)
         * @return this builder
         */
        public EasingConfigurationBuilder duration(int durationMs) {
            if (durationMs <= 0) throw new IllegalArgumentException("Duration must be positive");
            this.durationMs = durationMs;
            return this;
        }

        /**
         * Set the number of frames in the animation
         * Higher values = smoother but more CPU intensive
         *
         * @param frames number of frames (must be positive)
         * @return this builder
         */
        public EasingConfigurationBuilder frames(int frames) {
            if (frames < 0) throw new IllegalArgumentException("Frames must be greater than 0");
            this.frames = frames;
            return this;
        }

        /**
         * Set the minimum tick amount required to trigger easing
         * Ticks smaller than this will jump instantly to avoid unnecessary animation
         *
         * @param threshold minimum tick amount
         * @return this builder
         */
        public EasingConfigurationBuilder threshold(int threshold) {
            if (threshold < 0) throw new IllegalArgumentException("Threshold must be non-negative");
            this.threshold = threshold;
            return this;
        }

        public EasingConfiguration build() {
            return new EasingConfiguration(this);
        }
    }
}