package org.gitpod.cthis.java.posix;

import com.sun.jna.Structure;

/**
 * <p> POSIX's "pselect" takes a "timespec" struct as a timeout parameter. This is such a struct that can be
 *     passed around in JVM world </p>
 *
 * http://linux.die.net/man/2/pselect
 *
 *    struct timespec {
 *         long    tv_sec;         // seconds
 *         long    tv_nsec;        // nanoseconds
 *    };
 *
 * @author anatoly.polinsky
 */
public class TimeSpec extends Structure {

    public long tv_sec;
    public long tv_nsec;

    public TimeSpec( long seconds, long nanoseconds ) {
        this.tv_sec = seconds;
        this.tv_nsec = nanoseconds;
    }
}
