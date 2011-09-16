# What is "JNA POSIX Sleeper"?

When feeling sleepy, especially on those Monday mornings, snooze like a real geek! => Call into POSIX from JVM via Java Native Access 

```scala
/**
 * <p> POSIX usleep has a microsecond precision: "The  usleep()  function shall cause the calling thread to be
 *     suspended from execution until either the number of realtime microseconds" </p>
 *
 * <p> Hence picking at POSIX's "pselect", but not for its ability to "monitor multiple file descriptors", rather for
 *     its ability to handle timeouts with a nanosecond precision.</p>
 *
 * <p> Who are we kidding!? Of course this is not run against hard realtime RTOS microkernel, and of course JNA it self
 *     has a greater than several microseconds overhead, but hey, it's fun !!! </p>
 *
 * @author anatoly.polinsky
 */
object GoodNightSleeper {...}
```

### Feeling Adventurous?

Java version works like a charm. 

Scala one is ready to go, but needs a JNA Scala'образный "Structure" hook from [Scala Native Access](http://code.google.com/p/scala-native-access/)

