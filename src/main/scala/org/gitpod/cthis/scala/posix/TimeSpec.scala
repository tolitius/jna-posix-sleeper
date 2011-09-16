package org.gitpod.cthis.scala.posix

import com.sun.jna.Structure

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
class TimeSpec( val tv_sec: Long,
                val tv_nsec: Long ) extends Structure {}
