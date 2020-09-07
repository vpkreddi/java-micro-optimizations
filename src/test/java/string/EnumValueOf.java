package string;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@Fork(1)
@State(Scope.Thread)
@Warmup(iterations = 5, time = 1)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Measurement(iterations = 10, time = 1)
public class EnumValueOf {

    String[] names;
    int index;

    @Setup
    public void init() {
        WidgetProperty[] values = WidgetProperty.values();
        names = new Random(0).ints(4096, 0, values.length)
                .mapToObj(i -> values[i].name())
                .toArray(String[]::new);
        index = 0;
    }

    private String nextName() {
        return names[index++ & (names.length - 1)];
    }

    @Benchmark
    public WidgetProperty enumValueOf() {
        return WidgetProperty.valueOf(nextName());
    }

    @Benchmark
    public WidgetProperty enumSwitch() {
        return WidgetProperty.valueOfSwitch(nextName());
    }

    @Benchmark
    public WidgetProperty enumMap() {
        return WidgetProperty.valueOfMap(nextName());
    }

}
