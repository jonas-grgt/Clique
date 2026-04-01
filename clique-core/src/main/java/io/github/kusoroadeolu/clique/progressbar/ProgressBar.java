package io.github.kusoroadeolu.clique.progressbar;


import io.github.kusoroadeolu.clique.config.EasingConfiguration;
import io.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import io.github.kusoroadeolu.clique.core.display.Bordered;
import io.github.kusoroadeolu.clique.core.documentation.InternalApi;
import io.github.kusoroadeolu.clique.core.documentation.Stable;
import io.github.kusoroadeolu.clique.core.utils.StringUtils;

import java.io.PrintStream;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import static io.github.kusoroadeolu.clique.core.utils.Constants.BLANK;
import static io.github.kusoroadeolu.clique.core.utils.Constants.ZERO;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseString;
import static io.github.kusoroadeolu.clique.core.utils.StringUtils.parseToCell;

/**
 * @since 3.0.0
 * */
@InternalApi(since = "3.2.1")
public class ProgressBar implements Bordered {
    final int total;
    final long creationTime;
    final ProgressBarConfiguration progressBarConfiguration;
    int currentTick;
    boolean isDone;

    private ProgressBar(
            int currentTick,
            int total,
            boolean isDone,
            long creationTime,
            ProgressBarConfiguration configuration
    ) {
        this.currentTick = currentTick;
        this.total = total;
        this.isDone = isDone;
        this.creationTime = creationTime;
        this.progressBarConfiguration = configuration;
    }

    public ProgressBar(int total, ProgressBarConfiguration configuration) {
        this(ZERO, total, false, System.currentTimeMillis(), configuration);
    }

    public ProgressBar(int total) {
        this(ZERO, total, false, System.currentTimeMillis(), ProgressBarConfiguration.DEFAULT);
    }

    public ProgressBar tick() {
        return this.tick(1);
    }


    public ProgressBar tick(int amount) {
        if (amount < 1) throw new IllegalArgumentException("Tick amount cannot be less than 1");
        currentTick = Math.clamp(currentTick + amount, ZERO, total);
        if (currentTick >= total && !isDone) isDone = true;
        this.render();
        return this;
    }


    public ProgressBar tickAnimated(int amount) {
        var config = this.progressBarConfiguration;
        if (config != null && config.getEasing().shouldEase(amount)) {
            this.easeTick(amount, config.getEasing());
            return this;
        } else {
            return this.tick(amount);
        }
    }


    private void easeTick(int amount, EasingConfiguration easingConfig) {
        int startValue = this.currentTick;
        int targetValue = Math.clamp(currentTick + amount, ZERO, total);
        int diff = targetValue - startValue;

        int frames = easingConfig.getFrames();
        long frameDelay = easingConfig.getFrameDelayMs();

        for (int i = 1; i <= frames; i++) {
            double t = i / (double) frames;
            double eased = easingConfig.getFunction().apply(t);  // Apply easing

            this.currentTick = startValue + (int) (diff * eased);

            this.render();

            try {
                TimeUnit.MILLISECONDS.sleep(frameDelay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        // Ensure we end exactly at target
        this.currentTick = targetValue;
        if (currentTick >= total && !isDone) isDone = true;
    }

    public boolean isDone() {
        return isDone;
    }


    //Modified this to first check if we can tick by an actual valid value
    public ProgressBar complete() {
        return this.tick(Math.max(1, total - currentTick));
    }

    private int percent() {
        if (total > ZERO) return (int) ((currentTick / (double) total) * 100);
        else return ZERO;
    }

    private long elapsedTime() {
        return System.currentTimeMillis() - creationTime;
    }

    private String interval(Long milliseconds) {
        if (milliseconds == null) return "--:--";
        else {
            var seconds = (milliseconds / 1000) % 60;
            var minutes = milliseconds / 60000;
            return String.format("%02d:%02d", minutes, seconds);
        }
    }

    private String barText() {
        var length = progressBarConfiguration.getLength();
        var complete = progressBarConfiguration.getComplete();
        var incomplete = progressBarConfiguration.getIncomplete();

        var completedRatio = total > ZERO ? (this.currentTick / (double) this.total) : ZERO;
        var completedLength = (int) (completedRatio * length);

        return String.valueOf(complete).repeat(completedLength)
                + String.valueOf(incomplete).repeat(Math.max(length - completedLength, ZERO));
    }

    private String alignRight(String text, int size) {
        return BLANK.repeat(size - text.length()) + text;
    }

    private Long remainingTime() {
        var elapsed = elapsedTime();
        var totalTime = (elapsed / (currentTick / (double) this.total)); // elapsed / (current tick / total ticks)
        if (currentTick > 0 && total > 0) return (long) (totalTime - elapsed);
        else return null;
    }


    public String get() {
        var currentPercent = this.percent();
        var format = progressBarConfiguration.getFormatForPercent(currentPercent);
        var bar = barText();
        format = format.replace(":bar", bar);

        var progress = alignRight(
                String.valueOf(this.currentTick),
                String.valueOf(this.total).length()
        );

        format = format.replace(":progress", progress);

        var t = String.valueOf(this.total);
        format = format.replace(":total", t);

        var percent = alignRight(String.valueOf(this.percent()), 3);
        format = format.replace(":percent", percent);

        var elapsed = interval(this.elapsedTime());
        format = format.replace(":elapsed", elapsed);

        var remaining = interval(remainingTime());
        format = format.replace(":remaining", remaining);

        return parseString(format, this.progressBarConfiguration.parser());
    }


    public void render(PrintStream printStream) {
        printStream.print("\r" + this.get());
        if (isDone) printStream.println();
        printStream.flush();
    }


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        var that = (ProgressBar) object;
        return currentTick == that.currentTick && total == that.total && isDone == that.isDone && creationTime == that.creationTime && Objects.equals(progressBarConfiguration, that.progressBarConfiguration);
    }

    public int hashCode() {
        return Objects.hash(currentTick, total, isDone, creationTime);
    }

    public String toString() {
        return "ProgressBar[" +
                "progress=" + currentTick +
                ", total=" + total +
                ", isDone=" + isDone +
                ", creationTime=" + creationTime +
                ']';
    }
}