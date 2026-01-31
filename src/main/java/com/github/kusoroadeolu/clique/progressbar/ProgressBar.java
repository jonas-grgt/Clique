package com.github.kusoroadeolu.clique.progressbar;

import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import com.github.kusoroadeolu.clique.core.display.Renderable;

import java.io.PrintStream;
import java.util.Objects;

import static com.github.kusoroadeolu.clique.core.utils.Constants.BLANK;
import static com.github.kusoroadeolu.clique.core.utils.Constants.ZERO;

public final class ProgressBar implements Renderable {
    int currentTick;
    final int total;
    boolean isDone;
    final long creationTime;
    final ProgressBarConfiguration progressBarConfiguration;

    private ProgressBar(
            int currentTick,
            int total,
            boolean isDone,
            long creationTime
    ) {
        this.currentTick = currentTick;
        this.total = total;
        this.isDone = isDone;
        this.creationTime = creationTime;
        this.progressBarConfiguration = ProgressBarConfiguration.DEFAULT;
    }


    public ProgressBar(int total) {
        this(ZERO, total, false, System.currentTimeMillis());
    }

    public ProgressBar tick() {
        return this.tick(1);
    }


    public ProgressBar tick(int amount) {
        this.currentTick = Math.max(Math.min(currentTick + amount, total), ZERO);
        return this;
    }


    public ProgressBar complete() {
        this.isDone = true;
        return this.tick(total - currentTick);
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

    private String barText(ProgressBarConfiguration configuration) {
        var progress = this.currentTick;
        var total = this.total;
        var length = configuration.length();
        var complete = configuration.complete();
        var incomplete = configuration.incomplete();

        var completedRatio = total > ZERO ? (progress / (double) total) : ZERO;
        var completedLength = (int) (completedRatio * length);

        return String.valueOf(complete).repeat(completedLength)
                + String.valueOf(incomplete).repeat(Math.max(length - completedLength, ZERO));
    }



    private String alignRight(String text, int size) {
        return BLANK.repeat(size - text.length()) + text;
    }

    private Long remainingTime() {
        var elapsed = elapsedTime();
        if (currentTick > 0 && total > 0) return (long) ((elapsed / (currentTick / (double) total)) - elapsed);
        else return null;
    }


    public String get() {
        var format = progressBarConfiguration.format();
        var bar = barText(progressBarConfiguration);
        format = format.replace(":bar", bar);

        var progress = alignRight(
                String.valueOf(this.currentTick),
                String.valueOf(this.total).length()
        );

        format = format.replace(":progress", progress);

        var total = String.valueOf(this.total);
        format = format.replace(":total", total);

        var percent = alignRight(String.valueOf(percent()), 3);
        format = format.replace(":percent", percent);

        var elapsed = interval(elapsedTime());
        format = format.replace(":elapsed", elapsed);

        var remaining = interval(remainingTime());
        format = format.replace(":remaining", remaining);

        return format;
    }


    public void render(PrintStream printStream) {
        printStream.print("\r" + get());
        if (isDone) printStream.println();
        printStream.flush();
    }


    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        ProgressBar that = (ProgressBar) object;
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