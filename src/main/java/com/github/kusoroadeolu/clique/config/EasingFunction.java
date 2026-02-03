package com.github.kusoroadeolu.clique.config;

/**
 * Easing functions for progress bar animations.
 * Based on easing equations from easings.net
 */
public enum EasingFunction {
    /**
     * No easing, instant jump to target value
     */
    LINEAR {
        public double apply(double t) {
            return t;
        }
    },
    
    /**
     * Slow start, accelerates towards the end
     */
    EASE_IN_SINE {
        public double apply(double t) {
            return 1 - Math.cos((t * Math.PI) / 2);
        }
    },
    
    /**
     * Fast start, decelerates towards the end
     */
    EASE_OUT_SINE {
        public double apply(double t) {
            return Math.sin((t * Math.PI) / 2);
        }
    },
    
    /**
     * Slow start and end, fast in the middle
     */
    EASE_IN_OUT_SINE {
        public double apply(double t) {
            return -(Math.cos(Math.PI * t) - 1) / 2;
        }
    },
    
    /**
     * Quadratic easing in - accelerating from zero
     */
    EASE_IN_QUAD {
        public double apply(double t) {
            return t * t;
        }
    },
    
    /**
     * Quadratic easing out - decelerating to zero
     */
    EASE_OUT_QUAD {
        public double apply(double t) {
            return 1 - (1 - t) * (1 - t);
        }
    },
    
    /**
     * Quadratic easing in and out
     */
    EASE_IN_OUT_QUAD {
        @Override
        public double apply(double t) {
            return t < 0.5 ? 2 * t * t : 1 - Math.pow(-2 * t + 2, 2) / 2;
        }
    },
    
    /**
     * Cubic easing in, accelerating from zero
     */
    EASE_IN_CUBIC {
        @Override
        public double apply(double t) {
            return t * t * t;
        }
    },
    
    /**
     * Cubic easing out, decelerating to zero
     */
    EASE_OUT_CUBIC {
        @Override
        public double apply(double t) {
            return 1 - Math.pow(1 - t, 3);
        }
    },
    
    /**
     * Cubic easing in and out
     */
    EASE_IN_OUT_CUBIC {
        @Override
        public double apply(double t) {
            return t < 0.5 ? 4 * t * t * t : 1 - Math.pow(-2 * t + 2, 3) / 2;
        }
    };

    public abstract double apply(double t);
}